package RPG.engine.combat;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import RPG.engine.abilities.Ability;
import RPG.engine.abilities.Spell;
import RPG.engine.characters.Character;
import RPG.engine.characters.Enemy;
import RPG.engine.characters.Player;
import RPG.engine.items.Equipable;
import RPG.engine.items.Item;
import RPG.engine.items.Usable;
import RPG.engine.items.Weapon;
import RPG.engine.system.InitiativeSystem;

public class CombatSystem {
    private List<Character> participants;
    private Queue<Character> turnOrder;
    private Character pendingPlayer=null;
    private BattleLog log;

    public CombatSystem(List<Character> participants) {this.participants=participants;}

    public void startBattle() {
        InitiativeSystem.rollInitiative(participants);
        turnOrder=InitiativeSystem.getTurnOrder();
        nextTurn();
    }
    public void nextTurn() {
        if (isOver()) endBattle();
        Character actor=turnOrder.poll();
        if (!actor.isAlive()) {
            turnOrder.offer(actor);
            nextTurn();
        }
        BattleAction action=actor.takeTurn(this);
        if (action!=null) {
            execute(action);
            turnOrder.offer(actor);
            nextTurn();
        }
        else {pendingPlayer=actor;}
    }
    public void submitPlayerAction(BattleAction action) {
        if (pendingPlayer!=null && action.getActor().equals(pendingPlayer)) {
            execute(action);
            turnOrder.offer(pendingPlayer);
            pendingPlayer=null;
            nextTurn();
        }
    }
    public ActionResult execute(BattleAction action) {
        Character actor=action.getActor();
        Character target=action.getTarget();
        Item item=action.getItem();
        Ability ability=action.getAbility();
        Spell spell=action.getSpell();
        switch (action.getActionType()) {
            case ATTACK : {
                if (spell!=null) {
                    ability.activate(actor,target);
                    return new ActionResult(actor.getName()+" casts "+ability.getName()+" dealing "+spell.getDamage().getAmount()+" damage!",action);
                }
                if (item!=null && item instanceof Weapon) {
                    Weapon weapon=(Weapon)item;
                    actor.useWeapon(weapon,target);
                    return new ActionResult(actor.getName()+" swings "+weapon.getName()+" dealing "+weapon.getDamage().getAmount()+" damage!",action);
                }
                break;
            }
            case ITEM : {
                if (item instanceof Usable) ((Player)actor).useItem((Usable)item,target);
                if (item instanceof Equipable) {
                    ((Player)actor).equipItem((Equipable)item);
                    return new ActionResult("Equipped "+((Item)item).getName()+" in "+((Equipable)item).getSlot()+" slot.",action);
                }
                break;
            }
            case ACT : {
                if (ability!=null) {
                    ability.activate(actor,target);
                    return new ActionResult(actor.getName()+" used "+ability.getName()+" : "+ability.getDescription()+".",action);
                }
                break;
            }
        }
        return null;
    }
    public boolean isOver() {
        for (Character participant : turnOrder) {
            if (participant instanceof Player && participant.isAlive()) return false;
        }
        return true;
    }
    public void endBattle() {}
    public Character chooseTarget() {
        List<Player> targets=getPlayers();
        Random rd=new Random();
        int odds=rd.nextInt(targets.size());
        return targets.get(odds);
    }
    public List<Player> getPlayers() {
        List<Player> players=new ArrayList<Player>();
        for (Character participant : turnOrder) {
            if (participant instanceof Player) players.add((Player)participant);
        }
        return players;
    }
    public List<Enemy> getEnemies() {
        List<Enemy> enemies=new ArrayList<Enemy>();
        for (Character participant : turnOrder) {
            if (participant instanceof Enemy) enemies.add((Enemy)participant);
        }
        return enemies;
    }
    public BattleLog getBattleLog() {return log;}
}