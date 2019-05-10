package com.haulmont.testtask.dao;

import com.haulmont.testtask.domain.Patient;

import java.util.ArrayList;
import java.util.List;

public class PatientDaoImitation {

    private static List<Patient> patients;

    static {
        patients = new ArrayList<>(1000);
        for (int i = 0; i < 1000; i++) {
            Patient patient = new Patient("Name" + i, "Surname" + i);
            patient.setPhoneNum("+79093633203");
            patients.add(patient);
        }
    }

    public static List<Patient> getNPatients (int n) {
        return patients.subList(0,n);
    }

}
