package com.haulmont.testtask.dao;

import com.haulmont.testtask.domain.Priority;
import com.haulmont.testtask.domain.Recipe;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @see Dao
 */
public class RecipeDao implements Dao<Recipe> {

    private static RecipeDao ourInstance = new RecipeDao();

    private Connection con;

    private DoctorDao doctorDao = DoctorDao.get();
    private PatientDao patientDao = PatientDao.get();

    private RecipeDao() {
        con = DataBaseManager.get().getConnection();
    }

    public static RecipeDao get() {
        return ourInstance;
    }

    private Recipe mapRow(ResultSet rs) throws SQLException {
        Recipe recipe = new Recipe(rs.getLong("recipe_id"));
        recipe.setDescription(rs.getString("description"));

        recipe.setPatient(patientDao.mapRow(rs));
        recipe.setDoctor(doctorDao.mapRow(rs));

        recipe.setCreation(rs.getDate("creation_date"));
        recipe.setExpired(rs.getDate("expired"));
        recipe.setPriority(Priority.valueOf(
                rs.getString("priority")));
        return recipe;
    }

    @Override
    public List<Recipe> findAll() {
        List<Recipe> recipes = new ArrayList<>();
        try {
            PreparedStatement statement = con.prepareStatement(
                    "SELECT * FROM RECIPE " +
                            "JOIN PATIENT P ON RECIPE.PATIENT_ID_REF = P.PATIENT_ID " +
                            "JOIN DOCTOR D ON RECIPE.DOCTOR_ID_REF = D.DOCTOR_ID");
            statement.execute();
            ResultSet rs = statement.getResultSet();

            while (rs.next())
                recipes.add(mapRow(rs));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recipes;
    }

    @Override
    public Optional<Recipe> findById(long id) {
        try {
            PreparedStatement statement = con.prepareStatement(
                    "SELECT * FROM RECIPE " +
                            "JOIN PATIENT P ON RECIPE.PATIENT_ID_REF = P.PATIENT_ID " +
                            "JOIN DOCTOR D ON RECIPE.DOCTOR_ID_REF = D.DOCTOR_ID " +
                            "WHERE RECIPE.DOCTOR_ID_REF = ?");
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
    public void save(Recipe recipe) {
        try {
            PreparedStatement statement;
            if (existsById(recipe.getId())) {
                statement = con.prepareStatement(
                        "UPDATE PUBLIC.RECIPE SET DESCRIPTION=?, " +
                                "PATIENT_ID_REF=?, DOCTOR_ID_REF=?, " +
                                "CREATION_DATE=?, " +
                                "EXPIRED=?, PRIORITY=? WHERE RECIPE_ID=?");
                statement.setLong(7, recipe.getId());
            } else {
                statement = con.prepareStatement(
                        "INSERT INTO PUBLIC.RECIPE (DESCRIPTION, " +
                                "PATIENT_ID_REF, DOCTOR_ID_REF, " +
                                "CREATION_DATE, " +
                                "EXPIRED, PRIORITY) VALUES(?,?,?,?,?,?)");
            }
            setupStatement(statement, recipe);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Recipe recipe) {
        try {
            PreparedStatement statement = con.prepareStatement(
                    "DELETE FROM PUBLIC.RECIPE WHERE DESCRIPTION=? " +
                            "AND PATIENT_ID_REF=? AND DOCTOR_ID_REF=? AND" +
                            " CREATION_DATE=? AND EXPIRED=? AND PRIORITY=?");
            setupStatement(statement, recipe);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setupStatement(PreparedStatement ps, Recipe recipe)
            throws SQLException {
        ps.setString(1, recipe.getDescription());
        ps.setLong(2, recipe.getPatient().getId());
        ps.setLong(3, recipe.getDoctor().getId());
        ps.setDate(4, new Date(recipe.getCreation().getTime()));
        ps.setDate(5, new Date(recipe.getExpired().getTime()));
        ps.setString(6, recipe.getPriority().toString());
    }

    @Override
    public boolean existsById(long id) {
        try {
            PreparedStatement statement = con.prepareStatement(
                    "SELECT count(RECIPE_ID) FROM PUBLIC.RECIPE " +
                            "WHERE RECIPE_ID=?");
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
