package com.dchasanidis.envershistory.entities.envers;

import com.fasterxml.jackson.annotation.JsonValue;

public enum RevisionType {

    ADD("add"),

    MOD("mod"),

    DEL("del");

    private final String value;

    RevisionType(final String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public static RevisionType fromValue(final String value) {
        for (RevisionType b : RevisionType.values()) {
            if (b.value.equalsIgnoreCase(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}