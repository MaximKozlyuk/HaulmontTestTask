package com.haulmont.testtask.dao;

import com.haulmont.testtask.domain.Doctor;
import com.haulmont.testtask.domain.GenericObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DoctorDao implements Dao<Doctor> {

    private static DoctorDao ourInstance = new DoctorDao();

    private Connection con;

    private DoctorDao() {
        con = DataBaseManager.get().getConnection();
    }

    public static DoctorDao get() {
        return ourInstance;
    }

    Doctor mapRow(ResultSet rs) throws SQLException {
        Doctor doc = new Doctor(
                rs.getString("name"),
                rs.getString("surname")
        );
        doc.setId(rs.getLong("doctor_id"));
        doc.setMiddleName(rs.getString("middle_name"));
        doc.setSpecialization(rs.getString("specialization"));
        return doc;
    }

    private Doctor mapRowStats(ResultSet rs) throws SQLException {
        Doctor doc = mapRow(rs);
        doc.setRecipeAmount(rs.getInt("RECIPE_COUNT"));
        return doc;
    }

    @Override
    public List<Doctor> findAll() {
        List<Doctor> doctors = new ArrayList<>();
        try {
            PreparedStatement statement = con.prepareStatement(
                    "SELECT * FROM PUBLIC.DOCTOR");
            statement.execute();
            ResultSet rs = statement.getResultSet();

            while (rs.next())
                doctors.add(mapRow(rs));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doctors;
    }

    public List<Doctor> findAllWithStats() {
        List<Doctor> doctors = new ArrayList<>();
        try {
            PreparedStatement statement = con.prepareStatement(
                    "SELECT DOCTOR.*, count(RECIPE_ID) AS recipe_count " +
                            "FROM DOCTOR LEFT JOIN RECIPE ON DOCTOR.DOCTOR_ID " +
                            "= RECIPE.DOCTOR_ID_REF GROUP BY DOCTOR_ID;");
            statement.execute();
            ResultSet rs = statement.getResultSet();

            while (rs.next())
                doctors.add(mapRowStats(rs));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doctors;
    }

    @Override
    public Optional<Doctor> findById(long id) {
        try {
            PreparedStatement statement = con.prepareStatement(
                    "SELECT * FROM PUBLIC.DOCTOR WHERE DOCTOR_ID=?");
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
    public void save(Doctor doctor) {
        try {
            PreparedStatement statement;
            if (doctor.getId() == GenericObject.getDEFAULT_ID()
                    || !existsById(doctor.getId())) {
                statement = con.prepareStatement(
                        "INSERT INTO PUBLIC.DOCTOR (NAME, SURNAME, " +
                                "MIDDLE_NAME, SPECIALIZATION) VALUES(?,?,?,?)");
            } else {
                statement = con.prepareStatement(
                        "UPDATE PUBLIC.DOCTOR SET NAME=?, SURNAME=?, " +
                                "MIDDLE_NAME=?, SPECIALIZATION=? " +
                                "WHERE DOCTOR_ID=?");
                statement.setLong(5, doctor.getId());
            }
            setupStatement(statement, doctor);
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Doctor doctor)
            throws SQLIntegrityConstraintViolationException {
        try {
            PreparedStatement statement = con.prepareStatement(
                    "DELETE FROM PUBLIC.DOCTOR WHERE NAME=? AND " +
                            "SURNAME=? AND MIDDLE_NAME=? AND SPECIALIZATION=?");
            setupStatement(statement, doctor);
            statement.execute();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new SQLIntegrityConstraintViolationException(e);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setupStatement(PreparedStatement ps, Doctor doctor)
            throws SQLException {
        ps.setString(1, doctor.getName());
        ps.setString(2, doctor.getSurname());
        ps.setString(3, doctor.getMiddleName());
        ps.setString(4, doctor.getSpecialization());
    }

    @Override
    public boolean existsById(long id) {
        try {
            PreparedStatement statement = con.prepareStatement(
                    "SELECT count(DOCTOR_ID) FROM PUBLIC.DOCTOR " +
                            "WHERE DOCTOR_ID=?");
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
