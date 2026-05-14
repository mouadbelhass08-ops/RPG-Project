package RPG.engine.characters;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import RPG.engine.abilities.Spell;
import RPG.engine.abilities.SpellBook;
import RPG.engine.combat.BattleAction;
import RPG.engine.combat.CombatSystem;
import RPG.engine.items.Equipable;
import RPG.engine.items.Inventory;
import RPG.engine.items.Item;
import RPG.engine.items.Slot;
import RPG.engine.items.Usable;
import RPG.engine.skills.Skill;
import RPG.engine.world.ShopEntry;
import RPG.engine.world.Tile;

public class Player extends Character {
    private int lvl;
    private int EXP;
    private Inventory inventory;
    private Map<Slot,Equipable> equipment;
    private Set<Skill> skillset;
    private SpellBook spellbook;
    private int gold;
    private Tile currentPosition;

    public Player(String n,int h,int e,int m,int l,int exp,int Invcap,int gld) {
        super(n,h,e,m);
        this.lvl=l;
        this.EXP=exp;
        this.inventory=new Inventory(Invcap);
        this.equipment=new HashMap<>();
        this.skillset=new HashSet<>();
        this.spellbook=new SpellBook();
        this.gold=gld;
    }
    public Inventory getInventory() {return inventory;}
    public void equipItem(Equipable item) {
        Slot slot=item.getSlot();
        equipment.put(slot,item);
    }
    public void useItem(Usable item,Character target) {
        item.getEffect().apply(target);
        this.inventory.discard(((Item)item));
    }
    public void gainEXP(int XP) {EXP+=XP;}
    public void levelUP() {lvl++;}
    public void castSpell(String spellName,Character target) {
        Spell spell=spellbook.getSpell(spellName);
        if (spell!=null) {
            spell.activate(this,target);
        } 
    }
    public String showStatus() {return super.toString()+"Level: "+lvl+"\n"+"EXP: "+EXP+"\n"+"Inventory: "+inventory+"\n"+"Skillset: "+skillset+"";}
    public int getGold() {return gold;}
    public void gainGold(int amount) {this.gold+=amount;}
    public void spendGold(int amount) {if (this.gold>=amount) this.gold-=amount;}
    public BattleAction takeTurn(CombatSystem system) {return null;}
    public void getItem(Item item) {inventory.store(item);}
    public boolean purchase(ShopEntry entry) {
        if (gold>=entry.getPrice() && entry.getStock()>0) {
            gold-=entry.getPrice();
            inventory.store(entry.getItem());
            entry.decreaseStock();
            return true;
        }
        return false;
    }
    public ShopEntry sell(Item item,int price) {return new ShopEntry(item,price);}
    public void learnSpell(Spell spell,String description) {spellbook.learnSpell(spell,description);}
    public Tile getCurrentPosition() {return currentPosition;}
}