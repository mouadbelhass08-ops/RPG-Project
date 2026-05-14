package RPG.engine.effects;

import RPG.engine.characters.Character;;

public class EnergyBoost {
    private int energyboost;

    public void apply(Character target) {
        target.getEnergy().restore(energyboost);
    }
}