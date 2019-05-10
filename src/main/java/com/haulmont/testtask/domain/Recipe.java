package com.haulmont.testtask.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Recipe extends GenericObject implements Serializable {

    private long id;

    private String description;
    private Patient patient;
    private Doctor doctor;
    private LocalDate creation;
    private int expired;    // days amount of recipe validity
    private Priority priority;

}
