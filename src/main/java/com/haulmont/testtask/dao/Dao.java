package com.haulmont.testtask.dao;

import com.haulmont.testtask.domain.Doctor;
import com.haulmont.testtask.domain.GenericObject;
import com.haulmont.testtask.domain.Patient;
import com.haulmont.testtask.domain.Recipe;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Simple dao operations
 * @param <T> any of domain object type
 */
public interface Dao<T extends GenericObject> {

    List<T> findAll();

    Optional<T> findById(long id);

    void save(T obj);

    /**
     * deletes object from DB
     * @param obj which will be deleted
     * @throws SQLIntegrityConstraintViolationException if {@link Patient} or
     * {@link Doctor} have some related {@link Recipe} in database
     */
    void delete(T obj) throws SQLIntegrityConstraintViolationException;

    boolean existsById(long id);

}
