package com.haulmont.testtask.dao;

import com.haulmont.testtask.domain.GenericObject;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;

public interface Dao<T extends GenericObject> {

    List<T> findAll();

    Optional<T> findById(long id);

    void save(T obj);

    void delete(T obj) throws SQLIntegrityConstraintViolationException;

    boolean existsById(long id);

}
