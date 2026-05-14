package RPG.engine.items;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    int capacity; 
    List<Item> items;

    public Inventory(int cp) {
        this.capacity=cp;
        this.items=new ArrayList<>(cp);
    }

    public void store(Item item) {
        if (this.items.size()<this.capacity) {
            this.items.add(item);
        }
    }
    public List<Item> getItems() {return items;}
    public void discard(Item item) {this.items.remove(item);}
}