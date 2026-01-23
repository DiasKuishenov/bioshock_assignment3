package bioshockapi.model;

import bioshockapi.exception.InvalidInputException;
import bioshockapi.interfaces.PricedItem;
import bioshockapi.interfaces.Validatable;

public class Plasmid extends BaseEntity implements Validatable, PricedItem {
    private int price;
    private Effect effect;

    public Plasmid(int id, String name, int price, Effect effect) {
        super(id, name);
        this.price = price;
        this.effect = effect;
    }

    @Override
    public void validate() throws InvalidInputException {
        if (getName() == null || getName().trim().isEmpty()) {
            throw new InvalidInputException("Plasmid name must not be empty.");
        }
        if (price < 0) {
            throw new InvalidInputException("Price must be >= 0.");
        }
        if (effect == null) {
            throw new InvalidInputException("Effect must exist.");
        }
    }

    @Override
    public String getEntityType() {
        return "Plasmid";
    }

    @Override
    public int getPrice() {
        return price;
    }

    public Effect getEffect() {
        return effect;
    }

    public void setPrice(int price) throws InvalidInputException {
        if (price < 0) {
            throw new InvalidInputException("Price must be >= 0.");
        }
        this.price = price;
    }

    public void setEffect(Effect effect) throws InvalidInputException {
        if (effect == null) {
            throw new InvalidInputException("Effect must exist.");
        }
        this.effect = effect;
    }

    @Override
    public String printInfo() {
        String e = (effect == null) ? "No effect" : effect.getName();
        return super.printInfo() + " | price=" + price + " | effect=" + e;
    }
}