package com.haulmont.testtask.dao;

import com.haulmont.testtask.domain.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PatientDao implements Dao<Patient> {

    private static PatientDao ourInstance = new PatientDao();

    private Connection con;

    private PatientDao() {
        con = DataBaseManager.get().getConnection();
    }

    public static PatientDao get() {
        return ourInstance;
    }

    Patient mapRow(ResultSet rs) throws SQLException {
        Patient patient = new Patient(
                rs.getString("name"),
                rs.getString("surname")
        );
        patient.setId(rs.getLong("patient_id"));
        patient.setMiddleName(rs.getString("middle_name"));
        patient.setPhoneNum(rs.getString("phone_num"));
        return patient;
    }

    @Override
    public List<Patient> findAll() {
        List<Patient> patients = new ArrayList<>();
        try {
            PreparedStatement statement = con.prepareStatement(
                    "SELECT * FROM PUBLIC.PATIENT");
            statement.execute();
            ResultSet rs = statement.getResultSet();

            while (rs.next())
                patients.add(mapRow(rs));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients;
    }

    @Override
    public Optional<Patient> findById(long id) {
        try {
            PreparedStatement statement = con.prepareStatement(
                    "SELECT * FROM PUBLIC.PATIENT WHERE PATIENT_ID=?");
            statement.setLong(1, id);
            statement.execute();
            ResultSet rs = statement.getResultSet();
            rs.next();
            return Optional.of(mapRow(rs));
        } catch (SQLException e) {
            return Optional.empty();
        }
    }

    @Override
    public void save(Patient patient) {
        try {
            PreparedStatement statement;
            if (existsById(patient.getId())) {
                statement = con.prepareStatement(
                        "UPDATE PUBLIC.PATIENT SET NAME=?, SURNAME=?, " +
                                "MIDDLE_NAME=?, PHONE_NUM=? " +
                                "WHERE PATIENT_ID=?");
                statement.setLong(5, patient.getId());
            } else {
                statement = con.prepareStatement(
                        "INSERT INTO PUBLIC.PATIENT " +
                                "(NAME, SURNAME, MIDDLE_NAME, PHONE_NUM) " +
                                "VALUES(?,?,?,?)");
            }
            setupStatement(statement, patient);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Patient patient)
            throws SQLIntegrityConstraintViolationException {
        try {
            PreparedStatement statement = con.prepareStatement(
                    "DELETE FROM PUBLIC.PATIENT WHERE NAME=? AND " +
                            "SURNAME=? AND MIDDLE_NAME=? AND PHONE_NUM=?");
            setupStatement(statement, patient);
            statement.execute();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new SQLIntegrityConstraintViolationException(e);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setupStatement(PreparedStatement ps, Patient patient)
            throws SQLException {
        ps.setString(1, patient.getName());
        ps.setString(2, patient.getSurname());
        ps.setString(3, patient.getMiddleName());
        ps.setString(4, patient.getPhoneNum());
    }

    @Override
    public boolean existsById(long id) {
        try {
            PreparedStatement statement = con.prepareStatement(
                    "SELECT count(PATIENT_ID) FROM PUBLIC.PATIENT " +
                            "WHERE PATIENT_ID=?");
            statement.setLong(1, id);
            statement.execute();
            ResultSet rs = statement.getResultSet();
            rs.next();
            return rs.getInt(1) == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
