package RPG.exceptions;

public class OutOfStockException extends Exception {
    public OutOfStockException(String itemName) {
        super("Item '" + itemName + "' is out of stock in the shop.");
    }
}