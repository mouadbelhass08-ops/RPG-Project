Notes d’Abstraction pour Jeu RPG
================================

1. Idée Centrale
----------------
Un jeu est un système d’entités, de règles et d’interactions.
L’abstraction définit le « quoi » (concepts, comportements, relations)
sans se soucier du « comment » (détails d’implémentation).

2. Entités (Classes)
--------------------
- Personnage (Joueur, Ennemi, Boss)
- Objet (Arme, Armure, Potion)
- Monde (Lieux, Quêtes)

3. Comportements (Interfaces)
-----------------------------
- BattleAction : attaquer, défendre, utiliser un objet
- AIBehavior : prise de décision des ennemis
- Tradeable : acheter/vendre des objets
- Equipable : équiper/déséquiper des objets
- QuestObjective : isCompleted()

4. Règles (Méthodes & Exceptions)
---------------------------------
- Résolution de combat : jet d’attaque vs. seuil de défense
- Calcul des dégâts : jets de dés + modificateurs
- Contraintes de ressources : mana, santé, taille d’inventaire
- Exceptions : InvalidMoveException, InsufficientManaException, QuestNotCompletedException

5. Types de Données
-------------------
- Enums : rareté des objets, type de compétence, type de lieu
- Collections : List<Item> pour l’inventaire, Map<Quest, Status> pour le journal de quêtes
- Primitives : HP, mana, or
- Date/Heure : LocalDateTime pour les délais de quêtes

6. Pourquoi l’Abstraction est Importante
----------------------------------------
- Clarté : voir le jeu comme règles + entités, pas comme détails désordonnés
- Scalabilité : ajouter de nouvelles fonctionnalités sans casser le noyau
- Flexibilité : changer l’UI (console → GUI → web) sans réécrire la logique
- Réutilisabilité : les contrats abstraits permettent d’intégrer de nouvelles implémentations

7. Énoncé Simple d’Abstraction
------------------------------
« Un jeu est un système abstrait où les joueurs, régis par des règles,
interagissent avec des entités et des ressources pour atteindre des objectifs. »

Pour le RPG :
Les personnages interagissent par le combat, les objets et les quêtes,
régis par des règles basées sur des jets de dés.