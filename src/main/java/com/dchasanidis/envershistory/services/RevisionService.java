package com.dchasanidis.envershistory.services;

import com.dchasanidis.envershistory.entities.envers.*;
import com.dchasanidis.envershistory.entities.model.UserEntity;
import jakarta.persistence.EntityManager;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class RevisionService<T extends Identifiable<? extends Serializable>> {
    private final EntityManager entityManager;

    public RevisionService(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<RevisionEntry> getRevisions(final UUID userId) {
        final List<?> revisions = getAuditReader().createQuery()
                .forRevisionsOfEntityWithChanges(UserEntity.class, true)
                .add(AuditEntity.id().eq(userId))
                .getResultList();

        return revisions.stream()
                .map(r -> EnversRevisionEntry.fromEnvers(r, UserEntity.class))
                .map(RevisionService::enversRevisionToDto)
                .collect(Collectors.toList());
    }

    public Object getRevisions(final UUID entityId, final Long revisionId, final Class<T> entityClass) {
        final EnversRevisionEntry<T> revEntry = getEnversRevisionEntry(entityId, revisionId, entityClass)
                .orElseThrow(() -> new RuntimeException("shit happened"));
        return revEntry;
    }

    private Optional<EnversRevisionEntry<T>> getEnversRevisionEntry(final UUID entityId, final Long revisionId, final Class<T> entityClass) {
        // Since entityId and revisionId are provided, then the result list is guaranteed to contain 0 or 1 items
        final List<?> enversResultList = getAuditReader().createQuery()
                .forRevisionsOfEntityWithChanges(entityClass, true)
                .add(AuditEntity.id().eq(entityId))
                .add(AuditEntity.revisionNumber().eq(revisionId))
                .getResultList();
        if (enversResultList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(EnversRevisionEntry.fromEnvers(enversResultList.getFirst(), entityClass));
    }

    private static <T extends Identifiable<?>> RevisionEntry enversRevisionToDto(final EnversRevisionEntry<T> enversRevision) {
        final List<String> properties = new ArrayList<>(enversRevision.getModifiedProperties());
        return new RevisionEntry(
                enversRevision.getRevEntry().getRev(),
                (UUID) enversRevision.getEntity().getId(),
                enversRevision.getEntity().getClass().getSimpleName(),
                RevisionType.fromValue(enversRevision.getRevisionType().toString()),
                properties
        );
    }

    private AuditReader getAuditReader() {
        return AuditReaderFactory.get(entityManager);
    }
}
