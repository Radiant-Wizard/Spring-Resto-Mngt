package com.Radiant_wizard.GastroManagementApp.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
public class Datasource {
    private final String db_user = System.getenv("USERNAME");
    private final String db_user_password = System.getenv("USER_PASSWORD");
    private final String url;

    public Datasource() {
        String db_host = System.getenv("HOST");
        String db_port = System.getenv("PORT");
        String db_name = System.getenv("DB_NAME");
        this.url = "jdbc:postgresql://"+ db_host + ":" + db_port + "/" + db_name;
    }

    public Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(url, db_user, db_user_password);
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
