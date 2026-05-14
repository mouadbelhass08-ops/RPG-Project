package RPG.engine.items;

import RPG.engine.effects.Effect;

public class Potion extends Item implements Usable {
    private String name;
    private Effect effect;

    public String getName() {return name;}
    public Effect getEffect() {return effect;}
}