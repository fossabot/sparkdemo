package com.gehc.apps.fmk.sparkdemo;

import java.util.ResourceBundle;

import org.flywaydb.core.Flyway;
import org.sql2o.Sql2o;

public class DatabaseService {

    protected Sql2o sql2o;
    protected ResourceBundle props;

    protected DatabaseService() {
        props = ResourceBundle.getBundle("db");
        try {
            // create or update schema if needed
            Flyway flyway = new Flyway();
            flyway.setDataSource(
                    props.getString("db.url"), 
                    props.getString("db.username"),
                    props.getString("db.password"));
            flyway.setLocations("classpath:db/scripts");
            flyway.clean();
            flyway.migrate();
            // create database connection.
            sql2o = new Sql2o(
                    props.getString("db.url"), 
                    props.getString("db.username"),
                    props.getString("db.password"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}