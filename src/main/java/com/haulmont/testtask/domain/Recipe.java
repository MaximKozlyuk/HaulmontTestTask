package com.haulmont.testtask.domain;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class Recipe implements Serializable {

    private long id;

    private String descrition;
    private Patient patient;
    private Doctor doctor;
    private LocalDate creation;
    private int expired;    // days amount of recipe validity
    private Priority priority;

}
