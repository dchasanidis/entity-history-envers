package com.dchasanidis.envershistory.entities.envers;

import java.io.Serializable;

public interface Identifiable<T extends Serializable> {
    T getId();
}