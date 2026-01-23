package bioshockapi.controller;

import bioshockapi.model.AmmoType;
import bioshockapi.model.BaseEntity;
import bioshockapi.model.Effect;
import bioshockapi.model.Plasmid;
import bioshockapi.model.Weapon;
import bioshockapi.repository.InventoryRepository;
import bioshockapi.repository.ItemRepository;
import bioshockapi.repository.PlayerRepository;
import bioshockapi.service.InventoryService;
import bioshockapi.service.ItemService;
import bioshockapi.service.PlayerService;
import bioshockapi.utils.DatabaseConnection;
import bioshockapi.utils.DatabaseInitializer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String dbUrl = "jdbc:sqlite:./bioshock.db";
        DatabaseConnection db = new DatabaseConnection(dbUrl);

        try {
            new DatabaseInitializer().init(db);
        } catch (Exception e) {
            System.out.println("DB init error: " + e.getMessage());
            return;
        }

        PlayerRepository playerRepo = new PlayerRepository(db);
        ItemRepository itemRepo = new ItemRepository(db);
        InventoryRepository invRepo = new InventoryRepository(db);

        PlayerService playerService = new PlayerService(playerRepo);
        ItemService itemService = new ItemService(itemRepo);
        InventoryService invService = new InventoryService(invRepo, playerService, itemService);

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println();
            System.out.println("=== BioShock Rapture CLI ===");
            System.out.println("1) List players");
            System.out.println("2) Create player");
            System.out.println("3) Update player money");
            System.out.println("4) Delete player");
            System.out.println("5) List items");
            System.out.println("6) Create weapon");
            System.out.println("7) Create plasmid");
            System.out.println("8) Update item price");
            System.out.println("9) Delete item");
            System.out.println("10) Show player inventory");
            System.out.println("11) Add to inventory");
            System.out.println("12) Update inventory quantity");
            System.out.println("13) Remove from inventory");
            System.out.println("14) Demo: polymorphism + interfaces");
            System.out.println("0) Exit");
            System.out.print("Choose: ");

            String choice = sc.nextLine().trim();

            try {
                if (choice.equals("0")) break;

                if (choice.equals("1")) {
                    List<String> players = playerService.listPlayers();
                    for (String p : players) System.out.println(p);

                } else if (choice.equals("2")) {
                    System.out.print("Player name: ");
                    String name = sc.nextLine();
                    System.out.print("Money: ");
                    int money = Integer.parseInt(sc.nextLine());
                    int id = playerService.createPlayer(name, money);
                    System.out.println("Created player with id=" + id);

                } else if (choice.equals("3")) {
                    System.out.print("Player id: ");
                    int id = Integer.parseInt(sc.nextLine());
                    System.out.print("New money: ");
                    int money = Integer.parseInt(sc.nextLine());
                    playerService.updateMoney(id, money);
                    System.out.println("Updated.");

                } else if (choice.equals("4")) {
                    System.out.print("Player id: ");
                    int id = Integer.parseInt(sc.nextLine());
                    playerService.deletePlayer(id);
                    System.out.println("Deleted.");

                } else if (choice.equals("5")) {
                    List<BaseEntity> items = itemService.listItems();
                    for (BaseEntity e : items) System.out.println(e.printInfo());

                } else if (choice.equals("6")) {
                    Weapon w = readWeapon(sc);
                    int id = itemService.createWeapon(w);
                    System.out.println("Created weapon with id=" + id);

                } else if (choice.equals("7")) {
                    Plasmid p = readPlasmid(sc);
                    int id = itemService.createPlasmid(p);
                    System.out.println("Created plasmid with id=" + id);

                } else if (choice.equals("8")) {
                    System.out.print("Item id: ");
                    int id = Integer.parseInt(sc.nextLine());
                    System.out.print("New price: ");
                    int price = Integer.parseInt(sc.nextLine());
                    itemService.updatePrice(id, price);
                    System.out.println("Updated.");

                } else if (choice.equals("9")) {
                    System.out.print("Item id: ");
                    int id = Integer.parseInt(sc.nextLine());
                    itemService.deleteItem(id);
                    System.out.println("Deleted.");

                } else if (choice.equals("10")) {
                    System.out.print("Player id: ");
                    int playerId = Integer.parseInt(sc.nextLine());
                    List<String> inv = invService.showInventory(playerId);

                    if (inv.isEmpty()) {
                        System.out.println("Inventory is empty.");
                    } else {
                        for (String line : inv) System.out.println(line);
                    }

                } else if (choice.equals("11")) {
                    System.out.print("Player id: ");
                    int playerId = Integer.parseInt(sc.nextLine());
                    System.out.print("Item id: ");
                    int itemId = Integer.parseInt(sc.nextLine());
                    System.out.print("Quantity: ");
                    int qty = Integer.parseInt(sc.nextLine());
                    invService.addToInventory(playerId, itemId, qty);
                    System.out.println("Added.");

                } else if (choice.equals("12")) {
                    System.out.print("Player id: ");
                    int playerId = Integer.parseInt(sc.nextLine());
                    System.out.print("Item id: ");
                    int itemId = Integer.parseInt(sc.nextLine());
                    System.out.print("New quantity: ");
                    int qty = Integer.parseInt(sc.nextLine());
                    invService.updateQuantity(playerId, itemId, qty);
                    System.out.println("Updated.");

                } else if (choice.equals("13")) {
                    System.out.print("Player id: ");
                    int playerId = Integer.parseInt(sc.nextLine());
                    System.out.print("Item id: ");
                    int itemId = Integer.parseInt(sc.nextLine());
                    invService.removeFromInventory(playerId, itemId);
                    System.out.println("Removed.");

                } else if (choice.equals("14")) {
                    demoPolymorphismAndInterfaces();

                } else {
                    System.out.println("Unknown option.");
                }

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        System.out.println("Bye.");
    }

    private static Weapon readWeapon(Scanner sc) throws Exception {
        System.out.print("Weapon name (example: Shotgun): ");
        String name = sc.nextLine();

        System.out.print("Price: ");
        int price = Integer.parseInt(sc.nextLine());

        System.out.print("Damage: ");
        int dmg = Integer.parseInt(sc.nextLine());

        System.out.print("Ammo name (or empty): ");
        String ammoName = sc.nextLine().trim();

        AmmoType ammo = null;
        if (!ammoName.isEmpty()) {
            System.out.print("Ammo capacity: ");
            int cap = Integer.parseInt(sc.nextLine());
            ammo = new AmmoType(ammoName, cap);
        }

        return new Weapon(0, name, price, dmg, ammo);
    }

    private static Plasmid readPlasmid(Scanner sc) throws Exception {
        System.out.print("Plasmid name (example: Telekinesis): ");
        String name = sc.nextLine();

        System.out.print("Price: ");
        int price = Integer.parseInt(sc.nextLine());

        System.out.print("Effect name (example: Freeze): ");
        String effName = sc.nextLine();

        System.out.print("Effect description (short): ");
        String effDesc = sc.nextLine();

        Effect effect = new Effect(effName, effDesc);

        return new Plasmid(0, name, price, effect);
    }

    private static void demoPolymorphismAndInterfaces() throws Exception {
        List<BaseEntity> list = new ArrayList<>();

        Weapon w = new Weapon(0, "Wrench", 0, 15, null);
        Plasmid p = new Plasmid(0, "Electro Bolt", 80, new Effect("Shock", "Stuns enemies."));
        list.add(w);
        list.add(p);

        System.out.println("Polymorphism list (BaseEntity):");
        for (BaseEntity e : list) {
            System.out.println(e.printInfo());
        }

        System.out.println("Validation calls:");
        for (BaseEntity e : list) {
            e.validate();
            System.out.println(e.getName() + " ok");
        }

        System.out.println("Done.");
    }
}