package com.haulmont.testtask;

import com.haulmont.testtask.dao.DataBaseManager;
import com.haulmont.testtask.dao.DoctorDao;
import com.haulmont.testtask.dao.PatientDao;
import com.haulmont.testtask.domain.Doctor;
import com.haulmont.testtask.domain.Patient;
import org.junit.Test;

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

        dao.delete(doctor);

        System.out.println("FIND ALL:");
        doctors = dao.findAll();
        doctors.forEach(System.out::println);

    }

}
