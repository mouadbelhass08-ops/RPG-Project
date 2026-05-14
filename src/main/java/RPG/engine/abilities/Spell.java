package RPG.engine.abilities;

import RPG.engine.characters.Character;
import RPG.engine.combat.Damage;
import RPG.engine.effects.Effect;
import RPG.engine.resources.Defense;

public class Spell {
    private String name;
    private int manacost;
    private Damage damage;
    private Defense defense;
    private Effect effect;
    private String description;

    Spell(String name,int manacost,Damage damage,Effect effect) {
        this.name=name;
        this.manacost=manacost;
        this.damage=damage;
        this.effect=effect;
    }

    public String getName() {return this.name;}
    public String getDescription() {return description;}
    public int getCost() {return this.manacost;}
    public Defense getDefense() {return defense;}
    public void activate(Character user,Character target) {
        if (canUse(user)) {
            target.takeDamage(this.damage);
            this.effect.apply(target);
            user.getMana().consume(this.manacost);
        }
    }
    public Damage getDamage() {return damage;}
    public boolean canUse(Character user) {return user.getMana().getCurrentMana()>=this.manacost;}
}