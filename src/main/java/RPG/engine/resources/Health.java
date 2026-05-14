package RPG.engine.resources;

public class Health implements Resource {
    private int currentHealth;
    private int maxHealth;

    public Health(int h) {
        this.currentHealth=h;
        this.maxHealth=h;
    }

    public int getCurrentHealth() {return currentHealth;}
    public int getMaxHealth() {return maxHealth;}

    public void consume(int damage) {currentHealth=Math.max(0,currentHealth-damage);}
    public void restore(int heal) {currentHealth=Math.min(maxHealth,currentHealth+heal);}
    public boolean isAvailable() {return this.currentHealth>0;}
    public boolean isDepleted() {return this.currentHealth==0;}
    public boolean isFull() {return this.currentHealth==this.maxHealth;}
}