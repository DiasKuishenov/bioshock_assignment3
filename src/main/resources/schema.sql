PRAGMA foreign_keys = ON;

CREATE TABLE IF NOT EXISTS players (
                                       id INTEGER PRIMARY KEY AUTOINCREMENT,
                                       name TEXT NOT NULL UNIQUE,
                                       money INTEGER NOT NULL CHECK (money >= 0)
    );

CREATE TABLE IF NOT EXISTS items (
                                     id INTEGER PRIMARY KEY AUTOINCREMENT,
                                     name TEXT NOT NULL UNIQUE,
                                     item_type TEXT NOT NULL,
                                     price INTEGER NOT NULL CHECK (price >= 0),

    damage INTEGER CHECK (damage IS NULL OR damage >= 0),
    ammo_name TEXT,
    ammo_capacity INTEGER CHECK (ammo_capacity IS NULL OR ammo_capacity >= 0),

    effect_name TEXT,
    effect_description TEXT
    );

CREATE TABLE IF NOT EXISTS inventory (
                                         id INTEGER PRIMARY KEY AUTOINCREMENT,
                                         player_id INTEGER NOT NULL,
                                         item_id INTEGER NOT NULL,
                                         quantity INTEGER NOT NULL CHECK (quantity > 0),
    UNIQUE(player_id, item_id),
    FOREIGN KEY (player_id) REFERENCES players(id) ON DELETE CASCADE,
    FOREIGN KEY (item_id) REFERENCES items(id) ON DELETE CASCADE
    );

INSERT OR IGNORE INTO players (name, money) VALUES
('Jack', 120),
('Atlas', 50);

INSERT OR IGNORE INTO items (name, item_type, price, damage, ammo_name, ammo_capacity, effect_name, effect_description) VALUES
('Wrench', 'WEAPON', 0, 15, NULL, NULL, NULL, NULL),
('Pistol', 'WEAPON', 25, 20, '.38 Caliber', 12, NULL, NULL),
('Shotgun', 'WEAPON', 50, 45, '00 Buck', 4, NULL, NULL);

INSERT OR IGNORE INTO items (name, item_type, price, damage, ammo_name, ammo_capacity, effect_name, effect_description) VALUES
('Electro Bolt', 'PLASMID', 80, NULL, NULL, NULL, 'Shock', 'Stuns enemies and can zap water.'),
('Incinerate!', 'PLASMID', 90, NULL, NULL, NULL, 'Fire', 'Burns enemies and can ignite oil.'),
('Telekinesis', 'PLASMID', 75, NULL, NULL, NULL, 'Throw', 'Lets you grab and throw objects.');

INSERT OR IGNORE INTO inventory (player_id, item_id, quantity)
SELECT p.id, i.id, 1
FROM players p, items i
WHERE p.name = 'Jack' AND i.name = 'Wrench';