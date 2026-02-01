package bioshockapi.model;

import bioshockapi.interfaces.Validatable;

public abstract class BaseEntity implements Validatable {

    protected int id;
    protected String name;

    public BaseEntity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    protected String baseInfo() {
        return getEntityType() + " | id=" + id + " | name=" + name;
    }

    public abstract String getEntityType();

    public abstract String printInfo();
}