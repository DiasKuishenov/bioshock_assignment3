package bioshockapi.model;

import bioshockapi.exception.InvalidInputException;
import bioshockapi.interfaces.PricedItem;

public class Weapon extends BaseEntity implements PricedItem {

    private int price;
    private int damage;
    private AmmoType ammoType;

    public Weapon(int id, String name, int price, int damage, AmmoType ammoType) {
        super(id, name);
        this.price = price;
        this.damage = damage;
        this.ammoType = ammoType;
    }

    @Override
    public void validate() throws InvalidInputException {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidInputException("Weapon name must not be empty.");
        }
        if (price < 0) {
            throw new InvalidInputException("Price must be >= 0.");
        }
        if (damage < 0) {
            throw new InvalidInputException("Damage must be >= 0.");
        }
    }

    @Override
    public String getEntityType() {
        return "Weapon";
    }

    @Override
    public int getPrice() {
        return price;
    }

    public int getDamage() {
        return damage;
    }

    public AmmoType getAmmoType() {
        return ammoType;
    }

    @Override
    public String printInfo() {
        String ammo = (ammoType == null)
                ? "No ammo"
                : ammoType.getName() + " (" + ammoType.getCapacity() + ")";
        return baseInfo()
                + " | price=" + price
                + " | dmg=" + damage
                + " | ammo=" + ammo;
    }
}