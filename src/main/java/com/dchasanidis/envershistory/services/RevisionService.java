package com.dchasanidis.envershistory.services;

import com.dchasanidis.envershistory.entities.envers.*;
import com.dchasanidis.envershistory.entities.model.UserEntity;
import jakarta.persistence.EntityManager;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class RevisionService {
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
