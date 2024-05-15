package com.dchasanidis.envershistory.entities.envers;


public class RevisionAttribute {
    private String attributeName;
    private String oldValue;
    private String newValue;

    public String getAttributeName() {
        return attributeName;
    }

    public RevisionAttribute setAttributeName(final String attributeName) {
        this.attributeName = attributeName;
        return this;
    }

    public String getOldValue() {
        return oldValue;
    }

    public RevisionAttribute setOldValue(final String oldValue) {
        this.oldValue = oldValue;
        return this;
    }

    public String getNewValue() {
        return newValue;
    }

    public RevisionAttribute setNewValue(final String newValue) {
        this.newValue = newValue;
        return this;
    }
}

