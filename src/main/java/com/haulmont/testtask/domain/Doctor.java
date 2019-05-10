package com.haulmont.testtask.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Doctor extends Human implements Serializable {

    private String specialization;

    public Doctor(String name, String surname) {
        super(name, surname);
    }

    public Doctor () {
        super("", "");
        setMiddleName("");
        specialization = "";
    }

}
