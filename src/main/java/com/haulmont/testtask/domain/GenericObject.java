package com.haulmont.testtask.domain;

import lombok.Data;
import lombok.Getter;

import java.io.Serializable;

@Data
public abstract class GenericObject implements Serializable {

    private long id;

    @Getter
    private static final long DEFAULT_ID = -1;

    public GenericObject(long id) {
        this.id = id;
    }

    public GenericObject() {
        this.id = DEFAULT_ID;
    }

}
