package com.dchasanidis.envershistory.services;

import com.dchasanidis.envershistory.entities.envers.*;
import jakarta.persistence.EntityManager;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.query.AuditEntity;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import static java.time.LocalDateTime.ofInstant;
import static java.time.ZoneId.systemDefault;
import static java.util.function.Predicate.not;
import static org.hibernate.envers.RevisionType.ADD;
import static org.hibernate.envers.RevisionType.DEL;
import static org.slf4j.LoggerFactory.getLogger;

@Service
public class RevisionDetailsService<T extends Identifiable<?>> {
    private static final Logger log = getLogger(RevisionDetailsService.class);
    private static final Set<String> EXCLUDED_FIELDS = Set.of();
    private final EntityManager entityManager;

    public RevisionDetailsService(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public RevisionDetails getRevisionDetails(final Class<T> entityClass, final Object entityId, final Long revisionId) {
        final EnversRevisionEntry<T> revEntry = getEnversRevisionEntry(entityId, revisionId, entityClass)
                .orElseThrow(() -> new RuntimeException("Revision of entity " + entityClass.getSimpleName() + " with Id " + revisionId + " not found"));

        if (revEntry.getRevisionType() == ADD) {
            return getNewRevisionDetails(revEntry);
        } else if (revEntry.getRevisionType() == DEL) {
            return getDeletedRevisionDetails(revEntry);
        } else {
            return getModifiedRevisionEntry(revEntry, entityClass);
        }
    }

    private RevisionDetails getRevisionDetails(final EnversRevisionEntry<T> enversRevisionEntry, final Set<String> properties, final T oldEntity, final T newEntity) {
        final T entity = enversRevisionEntry.getEntity();
        final MyRevision emwaRevision = enversRevisionEntry.getRevEntry();
        final List<RevisionAttribute> attributes = properties.stream()
                .map(property -> createAttributeDelta(property, oldEntity, newEntity))
                .sorted(Comparator.comparing(RevisionAttribute::getAttributeName))
                .collect(Collectors.toList());

        return new RevisionDetails()
                .setEntityName(entity.getClass().getSimpleName())
                .setEntityId(entity.getId().toString())
                .setOperationType(RevisionType.valueOf(enversRevisionEntry.getRevisionType().name()))
                .setAuthor(emwaRevision.getUsername())
                .setTimestamp(ofInstant(Instant.ofEpochMilli(emwaRevision.getRevtstmp()), systemDefault()))
                .setAttributes(attributes);
    }

    private Optional<EnversRevisionEntry<T>> getEnversRevisionEntry(final Object entityId, final Long revisionId, final Class<T> entityClass) {
        // Since entityId and revisionId are provided, then the result list is guaranteed to contain 0 or 1 items
        final List<?> enversResultList = getAuditReader().createQuery()
                .forRevisionsOfEntityWithChanges(entityClass, true)
                .add(AuditEntity.id().eq(entityId))
                .add(AuditEntity.revisionNumber().eq(revisionId))
                .getResultList();
        if (enversResultList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(EnversRevisionEntry.fromEnvers(enversResultList.get(0), entityClass));
    }

    private RevisionAttribute createAttributeDelta(final String property, final T oldEntity, final T newEntity) {
        final String oldValue = getValueForPropertyOfClass(property, oldEntity);
        final String newValue = getValueForPropertyOfClass(property, newEntity);

        return new RevisionAttribute()
                .setAttributeName(property)
                .setOldValue(oldValue)
                .setNewValue(newValue);
    }

    private String getValueForPropertyOfClass(final String propertyName, final T entity) {
        if (entity == null) {
            return "";
        }
        final Class<?> c = entity.getClass();
        try {
            final PropertyDescriptor pd = new PropertyDescriptor(propertyName, c);
            final Method getter = pd.getReadMethod();
            final Object value = getter.invoke(entity);
            if (value instanceof List<?> && ((List<?>) value).stream().anyMatch(Identifiable.class::isInstance)) { // for ManyToMany relationship
                return ((List<? extends Identifiable>) value).stream().map(i -> i.getId().toString()).collect(Collectors.joining(", "));
            }
            return Objects.toString(value, "");
        } catch (Exception e) {
            log.error("Failed to extract property {} from {} - {}", propertyName, c.getSimpleName(), e.getMessage());
            throw new RuntimeException();
        }
    }

    private RevisionDetails getNewRevisionDetails(final EnversRevisionEntry<T> revEntry) {
        return getRevisionDetails(revEntry, getAllAttributes(revEntry.getEntity().getClass()), null, revEntry.getEntity());
    }

    private RevisionDetails getDeletedRevisionDetails(final EnversRevisionEntry<T> enversRevisionEntry) {
        return getRevisionDetails(enversRevisionEntry, getAllAttributes(enversRevisionEntry.getEntity().getClass()), enversRevisionEntry.getEntity(), null);
    }

    private RevisionDetails getModifiedRevisionEntry(final EnversRevisionEntry<T> revEntry, final Class<T> entityClass) {
        final Object entityId = revEntry.getEntity().getId();
        final long revisionId = revEntry.getRevEntry().getRev();

        final T entityNewState = revEntry.getEntity();
        final T entityOldState = getAuditReader().find(entityClass, entityId, revisionId - 1);
        if (entityOldState == null) {
            return getNewRevisionDetails(revEntry);
        }

        return getRevisionDetails(revEntry, revEntry.getModifiedProperties(), entityOldState, entityNewState);
    }

    private static Set<String> getAllAttributes(final Class<?> c) {
        return Arrays.stream(c.getDeclaredFields())
                .filter(it -> it.getAnnotationsByType(NotAudited.class).length == 0)
                .map(Field::getName)
                .filter(not(EXCLUDED_FIELDS::contains))
                .collect(Collectors.toSet());
    }


    private AuditReader getAuditReader() {
        return AuditReaderFactory.get(entityManager);
    }
}
