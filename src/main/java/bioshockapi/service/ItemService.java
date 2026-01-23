package bioshockapi.service;

import bioshockapi.exception.DuplicateResourceException;
import bioshockapi.exception.InvalidInputException;
import bioshockapi.exception.ResourceNotFoundException;
import bioshockapi.model.BaseEntity;
import bioshockapi.model.Plasmid;
import bioshockapi.model.Weapon;
import bioshockapi.repository.ItemRepository;

import java.util.List;

public class ItemService {
    private final ItemRepository repo;

    public ItemService(ItemRepository repo) {
        this.repo = repo;
    }

    public int createWeapon(Weapon w) throws Exception {
        if (repo.existsByName(w.getName())) {
            throw new DuplicateResourceException("Item with this name already exists.");
        }
        w.validate();
        return repo.createWeapon(w);
    }

    public int createPlasmid(Plasmid p) throws Exception {
        if (repo.existsByName(p.getName())) {
            throw new DuplicateResourceException("Item with this name already exists.");
        }
        p.validate();
        return repo.createPlasmid(p);
    }

    public List<BaseEntity> listItems() throws Exception {
        return repo.getAllAsEntities();
    }

    public BaseEntity getItem(int id) throws Exception {
        BaseEntity e = repo.getById(id);
        if (e == null) {
            throw new ResourceNotFoundException("Item not found.");
        }
        return e;
    }

    public void updatePrice(int id, int newPrice) throws Exception {
        if (newPrice < 0) {
            throw new InvalidInputException("Price must be >= 0.");
        }
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Item not found.");
        }
        repo.updatePrice(id, newPrice);
    }

    public void deleteItem(int id) throws Exception {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Item not found.");
        }
        repo.delete(id);
    }

    public void checkItemExists(int id) throws Exception {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Item not found.");
        }
    }
}