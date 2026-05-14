package RPG.engine.abilities;

import RPG.engine.characters.Character;
import RPG.engine.effects.Effect;

public class Ability {
    private String name;
    private String description;
    private int cooldown;
    private int currentcooldown;
    private Effect effect;
    private AbilityType type;

    public Ability(String name,String description,int cost,int cooldown,Effect effect,AbilityType type) {
        this.name=name;
        this.description=description;
        this.cooldown=cooldown;
        this.effect=effect;
        this.type=type;
    }

    public String getName() {return name;}
    public String getDescription() {return description;}
    public AbilityType getType() {return type;}
    public Effect getEffect() {return effect;}
    public void activate(Character actor,Character target) {
        if (canUse()) {
            effect.apply(target);
            currentcooldown=cooldown;
        }
    }
    public boolean canUse() {return currentcooldown==0;}
}