package bioshockapi.repository;

import bioshockapi.exception.DatabaseOperationException;
import bioshockapi.model.Player;
import bioshockapi.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PlayerRepository {

    private final DatabaseConnection db;

    public PlayerRepository(DatabaseConnection db) {
        this.db = db;
    }

    public boolean existsByName(String name) throws DatabaseOperationException {
        String sql = "SELECT id FROM players WHERE name = ?";

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to check player name.", e);
        }
    }

    public boolean existsById(int id) throws DatabaseOperationException {
        String sql = "SELECT id FROM players WHERE id = ?";

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to check player id.", e);
        }
    }

    public int create(String name, int money) throws DatabaseOperationException {
        String sql = "INSERT INTO players (name, money) VALUES (?, ?)";

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setInt(2, money);
            ps.executeUpdate();
            return getIdByName(name);
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to create player.", e);
        }
    }

    public List<String> getAllSimple() throws DatabaseOperationException {
        String sql = "SELECT id, name, money FROM players ORDER BY id";
        List<String> list = new ArrayList<>();

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(
                        rs.getInt("id") + " | " +
                                rs.getString("name") + " | money=" +
                                rs.getInt("money")
                );
            }
            return list;
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to list players.", e);
        }
    }

    public void updateMoney(int id, int money) throws DatabaseOperationException {
        String sql = "UPDATE players SET money = ? WHERE id = ?";

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, money);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to update player.", e);
        }
    }

    public void delete(int id) throws DatabaseOperationException {
        String sql = "DELETE FROM players WHERE id = ?";

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to delete player.", e);
        }
    }

    private int getIdByName(String name) throws Exception {
        String sql = "SELECT id FROM players WHERE name = ?";

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }
        return -1;
    }
}