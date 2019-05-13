package com.haulmont.testtask.dao;

import org.hsqldb.jdbc.JDBCDataSource;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Setting up database connection and executes creation script
 */
public class DataBaseManager {

    private static DataBaseManager ourInstance = new DataBaseManager();

    /**
     * This path needs to be set correctly according to the file system
     **/
    private final String pathToCreationScript = "/Users/max/Desktop/" +
            "test-task-master/src/main/resources/creation.sql";

    private Connection conn;

    private DataBaseManager() {
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        JDBCDataSource ds = new JDBCDataSource();
        ds.setDatabaseName("testdb");
        ds.setPassword("");
        ds.setURL("jdbc:hsqldb:mem:testdb");
        ds.setUser("SA");
        try {
            conn = ds.getConnection();
            executeCreationScript();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DataBaseManager get() {
        return ourInstance;
    }

    private void executeCreationScript() {
        InputStream in;
        try {
            in = new FileInputStream(pathToCreationScript);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(in));

            String str = reader.readLine();
            StringBuilder script = new StringBuilder(str);
            while ((str = reader.readLine()) != null) {
                script.append(str);
            }
            String[] statements = script.toString().split(";");

            for (String s : statements) {
                PreparedStatement statement = conn.prepareStatement(s);
                statement.execute();
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    Connection getConnection() {
        return conn;
    }

}
