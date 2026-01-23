package bioshockapi.utils;

import bioshockapi.exception.DatabaseOperationException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private final String url;

    public DatabaseConnection(String url) {
        this.url = url;
    }

    public Connection getConnection() throws DatabaseOperationException {
        try {
            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to connect to database.", e);
        }
    }
}