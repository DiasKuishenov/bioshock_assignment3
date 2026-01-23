package bioshockapi.repository;

import bioshockapi.exception.DatabaseOperationException;
import bioshockapi.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class InventoryRepository {
    private final DatabaseConnection db;

    public InventoryRepository(DatabaseConnection db) {
        this.db = db;
    }

    public void addItem(int playerId, int itemId, int quantity) throws DatabaseOperationException {
        String sql = "INSERT INTO inventory (player_id, item_id, quantity) VALUES (?, ?, ?)";

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, playerId);
            ps.setInt(2, itemId);
            ps.setInt(3, quantity);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to add item to inventory.", e);
        }
    }

    public void updateQuantity(int playerId, int itemId, int quantity) throws DatabaseOperationException {
        String sql = "UPDATE inventory SET quantity = ? WHERE player_id = ? AND item_id = ?";

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, quantity);
            ps.setInt(2, playerId);
            ps.setInt(3, itemId);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to update inventory quantity.", e);
        }
    }

    public void removeItem(int playerId, int itemId) throws DatabaseOperationException {
        String sql = "DELETE FROM inventory WHERE player_id = ? AND item_id = ?";

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, playerId);
            ps.setInt(2, itemId);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to remove inventory item.", e);
        }
    }

    public boolean existsPair(int playerId, int itemId) throws DatabaseOperationException {
        String sql = "SELECT id FROM inventory WHERE player_id = ? AND item_id = ?";

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, playerId);
            ps.setInt(2, itemId);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to check inventory pair.", e);
        }
    }

    public List<String> getPlayerInventorySimple(int playerId) throws DatabaseOperationException {
        String sql =
                "SELECT i.name, i.item_type, inv.quantity " +
                        "FROM inventory inv " +
                        "JOIN items i ON i.id = inv.item_id " +
                        "WHERE inv.player_id = ? " +
                        "ORDER BY i.name";

        List<String> list = new ArrayList<>();

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, playerId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(rs.getString("name") + " | " + rs.getString("item_type") + " | qty=" + rs.getInt("quantity"));
                }
            }

            return list;
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to read inventory.", e);
        }
    }
}