package bioshockapi.model;

import bioshockapi.exception.InvalidInputException;

public class Effect {
    private String name;
    private String description;

    public Effect(String name, String description) throws InvalidInputException {
        setName(name);
        setDescription(description);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws InvalidInputException {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidInputException("Effect name must not be empty.");
        }
        this.name = name.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) throws InvalidInputException {
        if (description == null) {
            description = "";
        }
        this.description = description.trim();
    }
}