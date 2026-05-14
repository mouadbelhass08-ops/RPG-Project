package RPG.engine.combat;

import RPG.engine.abilities.Ability;
import RPG.engine.abilities.Spell;
import RPG.engine.characters.Character;
import RPG.engine.items.Item;

public class BattleAction {
    Character actor;
    Character target;
    ActionType action;
    Item item;
    Spell spell;
    Ability ability;

    public BattleAction(Character actor,Character target,ActionType action) {
        this.actor=actor;
        this.target=target;
        this.action=action;  
    }

    public BattleAction(Character actor,Character target,ActionType action,Item item) {
        this.actor=actor;
        this.target=target;
        this.action=action;
        this.item=item;
    }

    public Character getActor() {return actor;}
    public Character getTarget() {return target;}
    public ActionType getActionType() {return action;}
    public Item getItem() {return item;}
    public Spell getSpell() {return spell;}
    public Ability getAbility() {return ability;}
    void describe() {};
}