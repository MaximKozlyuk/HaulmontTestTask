package com.haulmont.testtask.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Recipe extends GenericObject implements Serializable {

    private String description;
    private Patient patient;
    private Doctor doctor;
    private Date creation;
    private Date expired;
    private Priority priority;

    public Recipe(long id) {
        super(id);
    }

    public Recipe() {
        super();
    }

}
