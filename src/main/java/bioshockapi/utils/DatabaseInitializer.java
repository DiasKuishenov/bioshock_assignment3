package bioshockapi.utils;

import bioshockapi.exception.DatabaseOperationException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class DatabaseInitializer {

    public void init(DatabaseConnection db) throws DatabaseOperationException {
        try (Connection conn = db.getConnection()) {
            String sqlText = readResource("schema.sql");
            String[] parts = sqlText.split(";");

            for (String raw : parts) {
                String s = raw.trim();
                if (s.isEmpty()) continue;

                try (PreparedStatement ps = conn.prepareStatement(s)) {
                    ps.executeUpdate();
                }
            }
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to initialize schema.", e);
        }
    }

    private String readResource(String fileName) throws Exception {
        InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);
        if (is == null) {
            throw new Exception("Resource not found: " + fileName);
        }

        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
        }
        return sb.toString();
    }
}