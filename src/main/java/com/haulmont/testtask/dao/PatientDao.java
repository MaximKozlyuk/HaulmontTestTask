package com.haulmont.testtask.dao;

import com.haulmont.testtask.domain.Patient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PatientDao {

    private static PatientDao ourInstance = new PatientDao();

    public static PatientDao get() {
        return ourInstance;
    }

    private Connection con;

    private PatientDao() {
        con = DataBaseManager.get().getConnection();
    }

    private Patient mapRow (ResultSet rs) throws SQLException {
        Patient p = new Patient(
                rs.getString("name"),
                rs.getString("surname")
        );
        p.setId(rs.getLong("id"));
        p.setMiddleName(rs.getString("middle_name"));
        p.setPhoneNum(rs.getString("phone_num"));
        return p;
    }

    public List<Patient> findAll () {
        List<Patient> patients = new ArrayList<>();
        try {
            PreparedStatement statement = con.prepareStatement("SELECT * FROM PUBLIC.PATIENT");
            statement.execute();
            ResultSet rs = statement.getResultSet();

            while (rs.next())
                patients.add(mapRow(rs));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients;
    }

    public Optional<Patient> findById (long id) {
        try {
            PreparedStatement statement = con.prepareStatement("SELECT * FROM PUBLIC.PATIENT WHERE id=?");
            statement.setLong(1,id);
            statement.execute();
            ResultSet rs = statement.getResultSet();
            rs.next();
            return Optional.of(mapRow(rs));
        } catch (SQLException e) {
            return Optional.empty();
        }
    }

//    public void update (Patient patient) {
//        try {
//            PreparedStatement statement = con.prepareStatement(
//                    "update PUBLIC.PATIENT set name=?, surname=?, middle_name=?, phone_num=?");
//            setupStatement(statement,patient);
//            statement.execute();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    public void save (Patient patient) {
        System.out.println("Patient dao save: " + patient);
        try {
            PreparedStatement statement;
            if (existsById(patient.getId())) {
                System.out.println("EXISTING");
                statement = con.prepareStatement(
                    "UPDATE PUBLIC.PATIENT SET name=?, surname=?, middle_name=?, phone_num=? WHERE id=?");
                statement.setLong(5,patient.getId());
            } else {
                System.out.println("NOT EXISTING");
                statement = con.prepareStatement(
                        "INSERT INTO PUBLIC.PATIENT (name, surname, middle_name, phone_num) VALUES(?,?,?,?)");
            }
            setupStatement(statement,patient);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete (Patient patient) {
        try {
            PreparedStatement statement = con.prepareStatement(
                    "DELETE FROM PUBLIC.PATIENT WHERE name=? AND surname=? AND middle_name=? AND phone_num=?");
            setupStatement(statement,patient);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Patient dao delete: " + patient);
    }

    private void setupStatement (PreparedStatement ps, Patient patient) throws SQLException {
        ps.setString(1,patient.getName());
        ps.setString(2,patient.getSurname());
        ps.setString(3,patient.getMiddleName());
        ps.setString(4,patient.getPhoneNum());
    }

    public boolean existsById (long id) {
        System.out.println("existsById " + id);
        try {
            PreparedStatement statement = con.prepareStatement("SELECT count(id) FROM PUBLIC.PATIENT WHERE id=?");
            statement.setLong(1,id);
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
