package bioshockapi.model;

import bioshockapi.exception.InvalidInputException;

public class AmmoType {
    private String name;
    private int capacity;

    public AmmoType(String name, int capacity) throws InvalidInputException {
        setName(name);
        setCapacity(capacity);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws InvalidInputException {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidInputException("Ammo name must not be empty.");
        }
        this.name = name.trim();
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) throws InvalidInputException {
        if (capacity < 0) {
            throw new InvalidInputException("Ammo capacity must be >= 0.");
        }
        this.capacity = capacity;
    }
}