# BioShock Assignment 3 (Java + SQLite)

## Project idea
This is a small console program based on BioShock (Rapture).
It works like a simple inventory system where we can manage items (weapons / plasmids) and player inventory.

## Technologies
- Java
- SQLite
- JDBC (PreparedStatement)
- IntelliJ IDEA
- Maven (sqlite-jdbc)
- CLI (console menu)

## Database
Database file is created automatically:
`bioshock.db`

Tables:
- players
- items
- inventory (player_id -> players.id, item_id -> items.id)

Inventory table connects players and items (FK).

## OOP part
Abstract class:
- BaseEntity (id, name)

Inheritance:
- Weapon extends BaseEntity
- Plasmid extends BaseEntity

Interfaces:
- Validatable (validate())
- PricedItem (getPrice())

Composition:
- Weapon has AmmoType
- Plasmid has Effect

Polymorphism:
- In menu option 14, items are stored in List<BaseEntity> and printed using BaseEntity methods.

## Exception handling
Custom exceptions:
- InvalidInputException
- DuplicateResourceException
- ResourceNotFoundException
- DatabaseOperationException

They are used for validation and database errors.

## Project structure
- controller (Main)
- service (validation + rules)
- repository (SQL queries)
- model (OOP classes)
- utils (database connection and schema init)

## How to run
1. Open project in IntelliJ IDEA
2. Wait Maven to load dependencies
3. Run:
   `bioshockapi.controller.Main`

## Program functions (menu)
- List players
- Create / update / delete player
- List items
- Create weapon / plasmid
- Update item price
- Delete item
- Show inventory
- Add / update / remove inventory items
- Demo (polymorphism + interfaces)

## Example items
Weapons:
- Wrench
- Pistol
- Shotgun

Plasmids:
- Electro Bolt
- Incinerate!
- Telekinesis

## Notes
All SQL is done with PreparedStatement.
Schema is loaded from `schema.sql` on start.