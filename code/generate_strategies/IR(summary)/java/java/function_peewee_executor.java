package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PeeweeExecutor {
    public String execute_query(String dbPath, String query) {
        try {
            
            Class.forName("org.sqlite.JDBC");

            
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath)) {
                
                try (Statement createTableStmt = connection.createStatement()) {
                    createTableStmt.execute(
                            "CREATE TABLE IF NOT EXISTS testmodel (" +
                                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                    "name TEXT" +
                                    ")"
                    );
                }

                
                try (Statement statement = connection.createStatement()) {
                    boolean isSelect = query.trim().toUpperCase().startsWith("SELECT");

                    if (isSelect) {
                        try (ResultSet resultSet = statement.executeQuery(query)) {
                            return resultSet.next() ? String.valueOf(resultSet.getObject(1)) : "0";
                        }
                    } else {
                        statement.executeUpdate(query);
                        return "0";
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}