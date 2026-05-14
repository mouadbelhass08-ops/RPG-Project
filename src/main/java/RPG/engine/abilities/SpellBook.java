package RPG.engine.abilities;

import java.util.*;

public class SpellBook {
    private Map<Spell,String> spells=new HashMap<>();

    public void learnSpell(Spell spell,String description) {spells.put(spell,description);}
    public String getDescription(Spell spell) {return spells.get(spell);}
    public Spell getSpell(String name) {
        for (Spell spell : spells.keySet()) {
            if (spell.getName().equals(name)) {
                return spell;
            }
        }
        return null;
    }
    public Set<Spell> getAllSpells() {return spells.keySet();}
    public void showSpellbook() {
        for (Map.Entry<Spell,String> entry : spells.entrySet()) {
            System.out.println(entry.getKey().getName()+": "+entry.getValue());
        }
    }
}