package com.dchasanidis.envershistory.entities.envers;

import org.hibernate.envers.RevisionType;

import java.io.Serializable;
import java.util.Set;

public class EnversRevisionEntry<T extends Identifiable<? extends Serializable>> {
    private final T entity;
    private final MyRevision revEntry;
    private final RevisionType revisionType;
    private final Set<String> modifiedProperties;


    public static <T extends Identifiable<?>> EnversRevisionEntry<T> fromEnvers(final Object o, final Class<T> type) {
        final Object[] a = (Object[]) o;

        final T entity = type.cast(a[0]);
        final MyRevision revEntry = (MyRevision) a[1];
        final RevisionType revisionType = RevisionType.valueOf(((org.hibernate.envers.RevisionType) a[2]).name());
        final Set<String> modifiedProperties = (Set<String>) a[3];

        return new EnversRevisionEntry<>(entity, revEntry, revisionType, modifiedProperties);
    }

    private EnversRevisionEntry(final T entity, final MyRevision revEntry, final RevisionType revisionType, final Set<String> modifiedProperties) {
        this.entity = entity;
        this.revEntry = revEntry;
        this.revisionType = revisionType;
        this.modifiedProperties = modifiedProperties;
    }

    public T getEntity() {
        return entity;
    }

    public MyRevision getRevEntry() {
        return revEntry;
    }

    public RevisionType getRevisionType() {
        return revisionType;
    }

    public Set<String> getModifiedProperties() {
        return modifiedProperties;
    }
}