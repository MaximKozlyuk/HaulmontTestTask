package com.haulmont.testtask.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Doctor extends Human implements Serializable {

    private String specialization;

    @Getter
    private static final int UNKNOWN_RECIPE_AMOUNT = -1;
    private int recipeAmount = UNKNOWN_RECIPE_AMOUNT;

    public Doctor(String name, String surname) {
        super(name, surname);
    }

    public Doctor() {
        super("", "");
        setMiddleName("");
        specialization = "";
    }

}
