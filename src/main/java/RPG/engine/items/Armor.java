package RPG.engine.items;

import RPG.engine.resources.Defense;
import RPG.engine.resources.Health;

public class Armor extends Item implements Equipable {
    private Health durability;
    private Defense defense;

    public String getName() {return name;}
    public Slot getSlot() {return Slot.ARMOR;}
    public Health getHealth() {return durability;}
    public Defense getDefenseBoost() {return defense;}
}