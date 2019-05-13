package com.haulmont.testtask;

import com.haulmont.testtask.dao.*;
import com.haulmont.testtask.domain.Doctor;
import com.haulmont.testtask.domain.Patient;
import com.haulmont.testtask.domain.Priority;
import com.haulmont.testtask.domain.Recipe;
import org.junit.Test;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class Tests {

    @Test
    public void test () {
        DataBaseManager.get();
    }

    @Test
    public void daoTest () {
        List<Patient> patients = PatientDao.get().findAll();
        patients.forEach(System.out::println);
    }

    @Test
    public void patientSaveTest () {
        Patient patient = new Patient("p1name", "p1sur");
        patient.setId(0);
        patient.setPhoneNum("+79093633203");
        patient.setMiddleName("newMiddleName");

        System.out.println(PatientDao.get().existsById(1));

    }

    @Test
    public void findPatientByIdTest () {
        Optional<Patient> optionalPatient = PatientDao.get().findById(2);
        System.out.println(optionalPatient);
    }

    @Test
    public void doctorDaoTest () {
        DoctorDao dao = DoctorDao.get();

        List<Doctor> doctors = dao.findAll();
        doctors.forEach(System.out::println);

        System.out.println(dao.existsById(1));
        System.out.println(dao.existsById(100));
        System.out.println(dao.existsById(-1));

        Doctor doctor = dao.findById(1).get();
        System.out.println(doctor);

        try {
            dao.delete(doctor);
        } catch (SQLIntegrityConstraintViolationException e) {
            e.printStackTrace();
        }

        System.out.println("FIND ALL:");
        doctors = dao.findAll();
        doctors.forEach(System.out::println);

        System.out.println("stats:");
        List<Doctor> docs = dao.findAllWithStats();
        docs.forEach(System.out::println);

    }

    @Test
    public void recipeDaoTest () {
        RecipeDao dao = RecipeDao.get();
        PatientDao patientDao = PatientDao.get();
        DoctorDao doctorDao = DoctorDao.get();

        System.out.println(dao.findById(2).get());

        List<Recipe> recipes = dao.findAll();
        recipes.forEach(System.out::println);

        System.out.println("SAVE TEST:");
        Doctor doc = doctorDao.findById(0).get();
        Patient patient = patientDao.findById(0).get();

        Recipe recipe = new Recipe();
        recipe.setPriority(Priority.STATIM);
//        recipe.setExpired(1234);
//        recipe.setCreation(Date.from());
        recipe.setDoctor(doc);
        recipe.setPatient(patient);
        recipe.setDescription("test");
        dao.save(recipe);

        recipes = dao.findAll();
        recipes.forEach(System.out::println);

        System.out.println("DELETE");
        dao.delete(recipe);

        recipes = dao.findAll();
        recipes.forEach(System.out::println);

    }

    @Test
    public void recipeDaoTest2 () {
        RecipeDao dao = RecipeDao.get();
        List<Recipe> recipes = dao.findAll();
        recipes.forEach(System.out::println);

    }


}
