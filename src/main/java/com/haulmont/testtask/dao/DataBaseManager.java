package com.haulmont.testtask.dao;

import org.hsqldb.jdbc.JDBCDataSource;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataBaseManager {

    private static DataBaseManager ourInstance = new DataBaseManager();

    public static DataBaseManager get() {
        return ourInstance;
    }

    private JDBCDataSource ds;
    private Connection conn;

    private DataBaseManager() {
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ds = new JDBCDataSource();
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

    private void executeCreationScript() {
        InputStream in;
        try {
            in = new FileInputStream("/Users/max/Desktop/test-task-master/src/main/resources/creation.sql");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

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

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Connection getConnection () {
        return conn;
    }

}
