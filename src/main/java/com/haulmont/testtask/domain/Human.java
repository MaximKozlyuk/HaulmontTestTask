package com.haulmont.testtask.domain;

import lombok.Data;

import java.io.Serializable;

@Data
abstract class Human implements Serializable {

    private long id;

    private String name;
    private String surname;
    private String middleName;

    public Human(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

}
