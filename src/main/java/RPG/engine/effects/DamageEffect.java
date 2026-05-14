package RPG.engine.effects;

import RPG.engine.characters.Character;
import RPG.engine.combat.Damage;

public class DamageEffect implements Effect {
    private String name;
    private int duration;
    private Damage damage;

    public String getName() {return name;}
    public void apply(Character target) {
        target.takeDamage(damage);
        duration--;
    }
    public void remove(Character target) {
        if (isExpired()) System.out.println(target.getName()+" is no longer being damaged by "+name);
    }
    public boolean isExpired() {return duration<=0;}
}