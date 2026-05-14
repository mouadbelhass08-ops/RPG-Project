package RPG.engine.resources;

public class Energy implements Resource {
    private int currentEnergy;
    private int maxEnergy;

    public Energy(int e) {
        this.currentEnergy=e;
        this.maxEnergy=e;
    }

    public int getCurrentEnergy() {return currentEnergy;}
    public int getMaxEnergy() {return maxEnergy;}

    public void consume(int amount) {currentEnergy=Math.max(0,currentEnergy-amount);}
    public void restore(int amount) {currentEnergy=Math.min(maxEnergy,currentEnergy+amount);}
    public boolean isFull() {return this.currentEnergy==this.maxEnergy;}
    public boolean isAvailable() {return this.currentEnergy>0;}
    public boolean isDepleted() {return this.currentEnergy==0;}
}