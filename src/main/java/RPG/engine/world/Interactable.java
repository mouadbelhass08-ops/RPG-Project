package RPG.engine.world;

import RPG.engine.characters.Player;
import RPG.engine.world.Interactable;

public interface Interactable {
    String getPrompt();
    void interact(Player player);
    boolean isAvailable();
    InteractibleType getType();
}