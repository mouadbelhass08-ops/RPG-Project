package RPG.engine.resources;

public class Mana implements Resource {
    private int currentMana;
    private int maxMana;

    public Mana(int m) {
        this.currentMana=m;
        this.maxMana=m;
    }

    public int getCurrentMana() {return currentMana;}
    public int getMaxMana() {return maxMana;}

    public void consume(int amount) {currentMana=Math.max(0,currentMana-amount);}
    public void restore(int amount) {currentMana=Math.min(maxMana,currentMana+amount);}
    public boolean isFull() {return this.currentMana==this.maxMana;}
    public boolean isAvailable() {return this.currentMana>0;}
    public boolean isDepleted() {return this.currentMana==0;}
}