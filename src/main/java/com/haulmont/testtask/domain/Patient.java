package com.haulmont.testtask.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Patient extends Human implements Serializable {

    private String phoneNum = DEFAULT_PHONE_NUMBER;

    @Getter
    private static final String DEFAULT_PHONE_NUMBER = "null";

    public Patient (String name, String surname) {
        super(name,surname);
    }

    public Patient () {
        super("","");
        setMiddleName("");
        phoneNum = "";
    }

}
