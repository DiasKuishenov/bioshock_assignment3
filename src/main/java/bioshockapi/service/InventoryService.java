package bioshockapi.service;

import bioshockapi.exception.DuplicateResourceException;
import bioshockapi.exception.InvalidInputException;
import bioshockapi.repository.InventoryRepository;

import java.util.List;

public class InventoryService {
    private final InventoryRepository repo;
    private final PlayerService playerService;
    private final ItemService itemService;

    public InventoryService(InventoryRepository repo, PlayerService playerService, ItemService itemService) {
        this.repo = repo;
        this.playerService = playerService;
        this.itemService = itemService;
    }

    public void addToInventory(int playerId, int itemId, int quantity) throws Exception {
        if (quantity <= 0) {
            throw new InvalidInputException("Quantity must be > 0.");
        }

        playerService.checkPlayerExists(playerId);
        itemService.checkItemExists(itemId);

        if (repo.existsPair(playerId, itemId)) {
            throw new DuplicateResourceException("This item is already in inventory. Try update quantity.");
        }

        repo.addItem(playerId, itemId, quantity);
    }

    public void updateQuantity(int playerId, int itemId, int quantity) throws Exception {
        if (quantity <= 0) {
            throw new InvalidInputException("Quantity must be > 0.");
        }

        playerService.checkPlayerExists(playerId);
        itemService.checkItemExists(itemId);

        repo.updateQuantity(playerId, itemId, quantity);
    }

    public void removeFromInventory(int playerId, int itemId) throws Exception {
        playerService.checkPlayerExists(playerId);
        itemService.checkItemExists(itemId);

        repo.removeItem(playerId, itemId);
    }

    public List<String> showInventory(int playerId) throws Exception {
        playerService.checkPlayerExists(playerId);
        return repo.getPlayerInventorySimple(playerId);
    }
}