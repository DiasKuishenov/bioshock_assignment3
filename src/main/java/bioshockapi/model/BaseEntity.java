package bioshockapi.model;

import bioshockapi.exception.InvalidInputException;

public abstract class BaseEntity {
    private int id;
    private String name;

    public BaseEntity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public abstract void validate() throws InvalidInputException;

    public abstract String getEntityType();

    public String printInfo() {
        return "[" + getEntityType() + "] " + id + " - " + name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) throws InvalidInputException {
        if (id < 0) {
            throw new InvalidInputException("Id must be >= 0.");
        }
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws InvalidInputException {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidInputException("Name must not be empty.");
        }
        this.name = name.trim();
    }
}