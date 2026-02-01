package bioshockapi.repository;

import bioshockapi.exception.DatabaseOperationException;
import bioshockapi.model.*;
import bioshockapi.types.ItemType;
import bioshockapi.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ItemRepository {

    private final DatabaseConnection db;

    public ItemRepository(DatabaseConnection db) {
        this.db = db;
    }

    public boolean existsByName(String name) throws DatabaseOperationException {
        String sql = "SELECT id FROM items WHERE name = ?";

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to check item name.", e);
        }
    }

    public boolean existsById(int id) throws DatabaseOperationException {
        String sql = "SELECT id FROM items WHERE id = ?";

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to check item id.", e);
        }
    }

    public int createWeapon(Weapon w) throws DatabaseOperationException {
        String sql = "INSERT INTO items (name, item_type, price, damage, ammo_name, ammo_capacity) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, w.getName());
            ps.setString(2, ItemType.WEAPON.name());
            ps.setInt(3, w.getPrice());
            ps.setInt(4, w.getDamage());

            if (w.getAmmoType() == null) {
                ps.setString(5, null);
                ps.setObject(6, null);
            } else {
                ps.setString(5, w.getAmmoType().getName());
                ps.setInt(6, w.getAmmoType().getCapacity());
            }

            ps.executeUpdate();
            return findIdByName(w.getName());
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to create weapon.", e);
        }
    }

    public int createPlasmid(Plasmid p) throws DatabaseOperationException {
        String sql = "INSERT INTO items (name, item_type, price, effect_name, effect_description) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getName());
            ps.setString(2, ItemType.PLASMID.name());
            ps.setInt(3, p.getPrice());
            ps.setString(4, p.getEffect().getName());
            ps.setString(5, p.getEffect().getDescription());

            ps.executeUpdate();
            return findIdByName(p.getName());
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to create plasmid.", e);
        }
    }

    public List<BaseEntity> getAllAsEntities() throws DatabaseOperationException {
        String sql = "SELECT * FROM items ORDER BY id";
        List<BaseEntity> list = new ArrayList<>();

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }
            return list;
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to read items.", e);
        }
    }

    public BaseEntity getById(int id) throws DatabaseOperationException {
        String sql = "SELECT * FROM items WHERE id = ?";

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
                return null;
            }
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to get item.", e);
        }
    }

    public void updatePrice(int id, int price) throws DatabaseOperationException {
        String sql = "UPDATE items SET price = ? WHERE id = ?";

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, price);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to update price.", e);
        }
    }

    public void delete(int id) throws DatabaseOperationException {
        String sql = "DELETE FROM items WHERE id = ?";

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to delete item.", e);
        }
    }

    private int findIdByName(String name) throws Exception {
        String sql = "SELECT id FROM items WHERE name = ?";

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

    private BaseEntity mapRow(ResultSet rs) throws Exception {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String type = rs.getString("item_type");
        int price = rs.getInt("price");

        if (ItemType.WEAPON.name().equals(type)) {
            int dmg = rs.getInt("damage");
            String ammoName = rs.getString("ammo_name");
            Integer cap = (Integer) rs.getObject("ammo_capacity");

            AmmoType ammo = (ammoName == null || cap == null)
                    ? null
                    : new AmmoType(ammoName, cap);

            return new Weapon(id, name, price, dmg, ammo);
        }

        Effect effect = new Effect(
                rs.getString("effect_name"),
                rs.getString("effect_description")
        );
        return new Plasmid(id, name, price, effect);
    }
}