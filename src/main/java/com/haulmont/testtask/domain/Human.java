package com.haulmont.testtask.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
class Human extends GenericObject implements Serializable {

    private String name;
    private String surname;
    private String middleName;

    Human(String name, String surname) {
        super();
        this.name = name;
        this.surname = surname;
    }

}
