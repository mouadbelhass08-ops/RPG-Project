package RPG.engine.world;

import java.util.ArrayList;
import java.util.List;

import RPG.engine.characters.Player;
import RPG.engine.items.Armor;
import RPG.engine.items.Item;
import RPG.engine.items.Weapon;
import RPG.engine.system.GamePhase;
import RPG.engine.system.MenuOption;
import RPG.exceptions.OutOfStockException;
import RPG.gui.SceneManager;

public class Shop extends Location {
    private final List<ShopEntry> storage=new ArrayList<>();
    private MenuOption options;

    public Shop(Player player) {
        super("Shop",new Tile(18,18));
    }

    public void createShopMenu(Player player) {
        MenuOption greeting=new MenuOption("Greetings, traveler!");
        MenuOption choosePath=new MenuOption("What do you seek?");
        greeting.addChild(choosePath);

        // Buy branch
        MenuOption buyMenu=new MenuOption("Buy");
        MenuOption armorMenu=new MenuOption("Armor");
        MenuOption weaponMenu=new MenuOption("Weapons");

        for (ShopEntry entry : storage) {
            Item item=entry.getItem();
            MenuOption pick=new MenuOption(item.getName() + " - " + entry.getPrice() + "G");
            pick.setAction(() -> {
                try {
                    if (entry.getStock()<=0) throw new OutOfStockException(entry.getItem().getName());
                    if (player.purchase(entry)) {
                        pick.addChild(new MenuOption("You purchased " + item.getName()));
                    } else {
                        pick.addChild(new MenuOption("Not enough gold!"));
                    }
                }
                catch (OutOfStockException e) {pick.addChild(new MenuOption(e.getMessage()));}
            });

            if (item instanceof Armor) {
                armorMenu.addChild(pick);
            } else if (item instanceof Weapon) {
                weaponMenu.addChild(pick);
            }
        }

        buyMenu.addChild(armorMenu);
        buyMenu.addChild(weaponMenu);

        // Sell branch
        MenuOption sellMenu=new MenuOption("Sell");
        MenuOption sellArmor=new MenuOption("Armor");
        MenuOption sellWeapon=new MenuOption("Weapons");

        for (Item item : player.getInventory().getItems()) {
            int sellValue=10;
            MenuOption sellOpt=new MenuOption("Sell " + item.getName() + " (+ " + sellValue + "G)");
            sellOpt.setAction(() -> {
                player.sell(item, sellValue);
                sellOpt.addChild(new MenuOption("You sold " + item.getName()));
            });

            if (item instanceof Armor) {
                sellArmor.addChild(sellOpt);
            } else if (item instanceof Weapon) {
                sellWeapon.addChild(sellOpt);
            }
        }

        sellMenu.addChild(sellArmor);
        sellMenu.addChild(sellWeapon);

        choosePath.addChild(buyMenu);
        choosePath.addChild(sellMenu);

        // Exit
        MenuOption exit=new MenuOption("Exit", () -> {
            SceneManager.getGameScene().switchPhase(GamePhase.EXPLORATION);
        });
        greeting.addChild(exit);

        this.options=greeting;
    }

    public List<ShopEntry> getStorage() { return storage; }
    public MenuOption getMenuOption() { return options; }
    public void addShopEntry(ShopEntry entry) { storage.add(entry); }
}