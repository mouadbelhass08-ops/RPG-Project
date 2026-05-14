package RPG.engine.effects;

import RPG.engine.characters.Character;

public class HealEffect implements Effect {
    int duration;
    int healing;
    
    public String getName() {return "Healing";}
    public void apply(Character target) {
        target.getHealth().restore(healing);
    }
    public void remove(Character target) {
        if (isExpired()) System.out.println("Healing effect expired");
    }
    public boolean isExpired() {return duration<=0;}
}