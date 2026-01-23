package bioshockapi.service;

import bioshockapi.exception.DuplicateResourceException;
import bioshockapi.exception.InvalidInputException;
import bioshockapi.exception.ResourceNotFoundException;
import bioshockapi.repository.PlayerRepository;

import java.util.List;

public class PlayerService {
    private final PlayerRepository repo;

    public PlayerService(PlayerRepository repo) {
        this.repo = repo;
    }

    public int createPlayer(String name, int money) throws Exception {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidInputException("Player name must not be empty.");
        }
        if (money < 0) {
            throw new InvalidInputException("Money must be >= 0.");
        }
        if (repo.existsByName(name.trim())) {
            throw new DuplicateResourceException("Player with this name already exists.");
        }
        return repo.create(name.trim(), money);
    }

    public List<String> listPlayers() throws Exception {
        return repo.getAllSimple();
    }

    public void updateMoney(int id, int money) throws Exception {
        if (money < 0) {
            throw new InvalidInputException("Money must be >= 0.");
        }
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Player not found.");
        }
        repo.updateMoney(id, money);
    }

    public void deletePlayer(int id) throws Exception {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Player not found.");
        }
        repo.delete(id);
    }

    public void checkPlayerExists(int id) throws Exception {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Player not found.");
        }
    }
}