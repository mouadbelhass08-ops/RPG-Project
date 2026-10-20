RPG Game Abstraction Notes
==========================

1. Core Idea
------------
A game is a system of entities, rules, and interactions.
Abstraction defines the "what" (concepts, behaviors, relationships)
without worrying about the "how" (implementation details).

2. Entities (Classes)
---------------------
- Character (Player, Enemy, Boss)
- Item (Weapon, Armor, Potion)
- World (Locations, Quests)

3. Behaviors (Interfaces)
-------------------------
- BattleAction: attack, defend, use item
- AIBehavior: enemy decision-making
- Tradeable: buy/sell items
- Equipable: equip/unequip items
- QuestObjective: isCompleted()

4. Rules (Methods & Exceptions)
-------------------------------
- Combat resolution: attack roll vs. defense threshold
- Damage calculation: dice rolls + modifiers
- Resource constraints: mana, health, inventory size
- Exceptions: InvalidMoveException, InsufficientManaException, QuestNotCompletedException

5. Data Types
-------------
- Enums: item rarity, skill type, location type
- Collections: List<Item> for inventory, Map<Quest, Status> for quest log
- Primitives: HP, mana, gold
- Date/Time: LocalDateTime for quest deadlines

6. Why Abstraction Matters
--------------------------
- Clarity: see the game as rules + entities, not messy details
- Scalability: add new features without breaking the core
- Flexibility: change UI (console → GUI → web) without rewriting logic
- Reusability: abstract contracts let you plug in new implementations

7. Simple Abstraction Statement
-------------------------------
"A game is an abstract system where players, governed by rules,
interact with entities and resources to achieve objectives."

For the RPG:
Characters interact through combat, items, and quests,
governed by dice-based rules.