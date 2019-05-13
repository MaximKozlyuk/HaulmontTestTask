package com.haulmont.testtask.domain;

import lombok.Data;
import lombok.Getter;

import java.io.Serializable;

/**
 * Represents any object, that might be persisted.
 * All domain objects extends this
 */
@Data
public abstract class GenericObject implements Serializable {

    private long id;

    /**
     * Means that object ain't persisted in DB
     */
    @Getter
    private static final long DEFAULT_ID = -1;

    GenericObject(long id) {
        this.id = id;
    }

    GenericObject() {
        this.id = DEFAULT_ID;
    }

}
