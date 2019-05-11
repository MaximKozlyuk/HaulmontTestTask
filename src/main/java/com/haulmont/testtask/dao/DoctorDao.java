package com.haulmont.testtask.dao;

import com.haulmont.testtask.domain.Doctor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DoctorDao {

    private static DoctorDao ourInstance = new DoctorDao();

    public static DoctorDao get() {
        return ourInstance;
    }

    private Connection con;

    private DoctorDao() {
        con = DataBaseManager.get().getConnection();
    }

    private Doctor mapRow (ResultSet rs) throws SQLException {
        Doctor doc = new Doctor(
                rs.getString("name"),
                rs.getString("surname")
        );
        doc.setId(rs.getLong("id"));
        doc.setMiddleName(rs.getString("middle_name"));
        doc.setSpecialization(rs.getString("specialization"));
        return doc;
    }

    private Doctor mapRowStats (ResultSet rs) throws SQLException {
        Doctor doc = mapRow(rs);
        doc.setRecipeAmount(rs.getInt("RECIPE_COUNT"));
        return doc;
    }

    public List<Doctor> findAll () {
        List<Doctor> doctors = new ArrayList<>();
        try {
            PreparedStatement statement = con.prepareStatement("SELECT * FROM PUBLIC.DOCTOR");
            statement.execute();
            ResultSet rs = statement.getResultSet();

            while (rs.next())
                doctors.add(mapRow(rs));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doctors;
    }

    public List<Doctor> findAllWithStats () {
        List<Doctor> doctors = new ArrayList<>();
        try {
            PreparedStatement statement = con.prepareStatement("SELECT DOCTOR.*, COUNT(DOCTOR_ID) AS RECIPE_COUNT " +
                    "FROM DOCTOR JOIN RECIPE ON DOCTOR.ID = RECIPE.DOCTOR_ID GROUP BY DOCTOR.ID;");
            statement.execute();
            ResultSet rs = statement.getResultSet();

            while (rs.next())
                doctors.add(mapRowStats(rs));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doctors;
    }

    public Optional<Doctor> findById (long id) {
        try {
            PreparedStatement statement = con.prepareStatement("SELECT * FROM PUBLIC.DOCTOR WHERE id=?");
            statement.setLong(1,id);
            statement.execute();
            ResultSet rs = statement.getResultSet();
            rs.next();
            return Optional.of(mapRow(rs));
        } catch (SQLException e) {
            return Optional.empty();
        }
    }

    public void save (Doctor doctor) {
        System.out.println("Doctor dao save: " + doctor);
        try {
            PreparedStatement statement;
            if (existsById(doctor.getId())) {
                System.out.println("EXISTING");
                statement = con.prepareStatement(
                        "UPDATE PUBLIC.DOCTOR SET name=?, surname=?, middle_name=?, specialization=? WHERE id=?");
                statement.setLong(5,doctor.getId());
            } else {
                System.out.println("NOT EXISTING");
                statement = con.prepareStatement(
                        "INSERT INTO PUBLIC.DOCTOR (name, surname, middle_name, specialization) VALUES(?,?,?,?)");
            }
            setupStatement(statement,doctor);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete (Doctor doctor) {
        try {
            PreparedStatement statement = con.prepareStatement(
                    "DELETE FROM PUBLIC.DOCTOR WHERE name=? AND surname=? AND middle_name=? AND specialization=?");
            setupStatement(statement,doctor);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Doctor dao delete: " + doctor);
    }

    private void setupStatement (PreparedStatement ps, Doctor doctor) throws SQLException {
        ps.setString(1,doctor.getName());
        ps.setString(2,doctor.getSurname());
        ps.setString(3,doctor.getMiddleName());
        ps.setString(4,doctor.getSpecialization());
    }

    public boolean existsById (long id) {
        System.out.println("existsById " + id);
        try {
            PreparedStatement statement = con.prepareStatement("SELECT count(id) FROM PUBLIC.DOCTOR WHERE id=?");
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
