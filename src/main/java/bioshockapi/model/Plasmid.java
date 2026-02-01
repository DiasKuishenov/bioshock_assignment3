package bioshockapi.model;

import bioshockapi.exception.InvalidInputException;
import bioshockapi.interfaces.PricedItem;

public class Plasmid extends BaseEntity implements PricedItem {

    private int price;
    private Effect effect;

    public Plasmid(int id, String name, int price, Effect effect) {
        super(id, name);
        this.price = price;
        this.effect = effect;
    }

    @Override
    public void validate() throws InvalidInputException {
        if (name == null || name.trim().isEmpty()) {
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

    @Override
    public String printInfo() {
        return baseInfo()
                + " | price=" + price
                + " | effect=" + effect.getName();
    }
}