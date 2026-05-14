package RPG.engine.items;

import RPG.engine.combat.Damage;

public class Weapon extends Item implements Equipable {
    private Damage damage;

    public Weapon(String name,int amount) {
        this.name=name;
        this.damage=new Damage(amount);
    }

    public Slot getSlot() {return Slot.WEAPON;}
    public String getName() {return "Weapon: "+name+"\n"+"Damage: "+damage.getAmount();}
    public Damage getDamage() {return damage;}
}