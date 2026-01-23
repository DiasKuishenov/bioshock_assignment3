package bioshockapi.model;

import bioshockapi.exception.InvalidInputException;
import bioshockapi.interfaces.PricedItem;
import bioshockapi.interfaces.Validatable;

public class Weapon extends BaseEntity implements Validatable, PricedItem {
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
        if (getName() == null || getName().trim().isEmpty()) {
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

    public void setPrice(int price) throws InvalidInputException {
        if (price < 0) {
            throw new InvalidInputException("Price must be >= 0.");
        }
        this.price = price;
    }

    public void setDamage(int damage) throws InvalidInputException {
        if (damage < 0) {
            throw new InvalidInputException("Damage must be >= 0.");
        }
        this.damage = damage;
    }

    public void setAmmoType(AmmoType ammoType) {
        this.ammoType = ammoType;
    }

    @Override
    public String printInfo() {
        String ammo = (ammoType == null) ? "No ammo" : ammoType.getName() + " (" + ammoType.getCapacity() + ")";
        return super.printInfo() + " | price=" + price + " | dmg=" + damage + " | ammo=" + ammo;
    }
}