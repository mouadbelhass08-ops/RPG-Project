package RPG.engine.resources;

public class Defense implements Resource {
    private int currentDefense;
    private int maxDefense;

    public Defense(int d) {
        this.currentDefense=d;
        this.maxDefense=d;
    }

    public int getCurrentDefense() {return currentDefense;}
    public int getMaxDefense() {return maxDefense;}
    
    public void consume(int amount) {currentDefense=Math.max(0,currentDefense-amount);}
    public void restore(int amount) {currentDefense=Math.min(maxDefense,currentDefense+amount);}
    public boolean isAvailable() {return currentDefense>0;}
    public boolean isFull() {return currentDefense==maxDefense;}
    public boolean isDepleted() {return currentDefense<=0;}
}