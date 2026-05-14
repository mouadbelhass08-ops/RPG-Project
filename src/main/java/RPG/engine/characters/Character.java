package RPG.engine.characters;

import RPG.engine.combat.BattleAction;
import RPG.engine.combat.CombatSystem;
import RPG.engine.combat.Damage;
import RPG.engine.items.Weapon;
import RPG.engine.resources.Energy;
import RPG.engine.resources.Health;
import RPG.engine.resources.Mana;

public abstract class Character {
    private String name;
    private Health HP;
    private Energy energy;
    private Mana mana;

    Character() {}

    Character(String n,int h,int e,int m) {
        this.name=n;
        this.HP=new Health(h);
        this.energy=new Energy(e);
        this.mana=new Mana(m);
    }

    public String getName() {return name;}
    public Health getHealth() {return HP;}
    public Mana getMana() {return mana;}
    public Energy getEnergy() {return energy;}

    public void takeDamage(Damage damage) {this.getHealth().consume(damage.getAmount());}
    public String toString() {
        return "Name: "+name+"\n"+
        "HP: "+HP.getCurrentHealth()+"/"+HP.getMaxHealth()+"\n"+
        "Energy: "+energy.getCurrentEnergy()+"/"+energy.getMaxEnergy()+"\n"+
        "Mana: "+mana.getCurrentMana()+"/"+mana.getMaxMana();}
    public abstract BattleAction takeTurn(CombatSystem system);
    public boolean isAlive() {return this.getHealth().isDepleted();}
    public void useWeapon(Weapon weapon,Character target) {target.takeDamage(weapon.getDamage());}
}