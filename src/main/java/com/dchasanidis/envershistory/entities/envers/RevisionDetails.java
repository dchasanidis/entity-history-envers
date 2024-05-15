package com.dchasanidis.envershistory.entities.envers;

import java.time.LocalDateTime;
import java.util.List;


public class RevisionDetails {
    private String entityId;
    private String entityName;
    private RevisionType operationType;
    private List<RevisionAttribute> attributes;
    private String author;
    private LocalDateTime timestamp;

    public String getEntityId() {
        return entityId;
    }

    public RevisionDetails setEntityId(final String entityId) {
        this.entityId = entityId;
        return this;
    }

    public String getEntityName() {
        return entityName;
    }

    public RevisionDetails setEntityName(final String entityName) {
        this.entityName = entityName;
        return this;
    }

    public RevisionType getOperationType() {
        return operationType;
    }

    public RevisionDetails setOperationType(final RevisionType operationType) {
        this.operationType = operationType;
        return this;
    }

    public List<RevisionAttribute> getAttributes() {
        return attributes;
    }

    public RevisionDetails setAttributes(final List<RevisionAttribute> attributes) {
        this.attributes = attributes;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public RevisionDetails setAuthor(final String author) {
        this.author = author;
        return this;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public RevisionDetails setTimestamp(final LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }
}

