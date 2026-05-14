package RPG.engine.world;

import RPG.engine.items.Item;

public class ShopEntry {
    private final Item item;
    private final int price;
    private int stock;

    public ShopEntry(Item item,int price,int stock) {
        this.item=item;
        this.price=price;
        this.stock=stock;
    }
    public ShopEntry(Item item,int price) {
        this(item,price,1);
    }

    public Item getItem() {return item;}
    public int getPrice() {return price;}
    public int getStock() {return stock;}
    public void decreaseStock() {stock--;}
}