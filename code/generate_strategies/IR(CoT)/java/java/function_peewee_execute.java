package com.example;

import java.sql.*;

public class PeeweeExecutor {
    public String execute_query(String dbPath, String query) {
        Connection connection = null;
        Statement statement = null;
        int rowCount = 0;

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            statement = connection.createStatement();

            boolean isResultSet = statement.execute(query);

            if (query.toUpperCase().startsWith("SELECT")) {
                ResultSet resultSet = statement.getResultSet();
                int count = 0;
                while (resultSet.next()) {
                    count++;
                }
                rowCount = count;
            } else {
                rowCount = statement.getUpdateCount();
            }


        } catch (ClassNotFoundException e) {
            System.err.println("JDBC driver not found: " + e.getMessage());
            return "-1"; 
        } catch (SQLException e) {
            System.err.println("SQL exception occurred: " + e.getMessage());
            return "-1"; 
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }

        return String.valueOf(rowCount);
    }
}