package RPG.engine.effects;

import RPG.engine.characters.Character;

public interface Effect {
    String getName();
    void apply(Character target);
    void remove(Character target);
    boolean isExpired();
}