package RPG.engine.effects;

import RPG.engine.characters.Character;

public class ManaCharge {
    private int amount;

    public void apply(Character target) {
        target.getMana().restore(amount);
    }
}