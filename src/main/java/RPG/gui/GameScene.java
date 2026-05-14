package RPG.gui;

import RPG.engine.characters.Player;
import RPG.engine.system.GamePhase;
import RPG.engine.world.Shop;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;

public class GameScene {
    private final StackPane root = new StackPane();
    private GamePhase currentPhase;
    private Player player;
    private Shop shop;
    private ExplorationPane exploration;
    private ShopPane shopPane;
    private CombatPane combat;

    public Parent createRoot(Scene scene) {
        root.setStyle("-fx-background-color: black;");
        // Core game objects
        player=new Player("Hero", 50, 20, 10, 0, 0, 5, 200);
        shop=new Shop(player);
        // Panes
        try {
            exploration=new ExplorationPane(player);
        } catch (Exception e) {
            e.printStackTrace();
        }
        shopPane = new ShopPane(player, shop);
        combat = new CombatPane(player);
        // ESC toggles pause via Scene-level handler
        scene.setOnKeyPressed(e -> {
            if (e.getCode()==KeyCode.ESCAPE) {
                e.consume();
                SceneManager.togglePause(!SceneManager.isPaused());
            }
        });
        switchPhase(GamePhase.EXPLORATION);
        return root;
    }
    public void switchPhase(GamePhase newPhase) {
        root.getChildren().clear();
        switch (newPhase) {
            case EXPLORATION -> {
                root.getChildren().add(exploration);
                exploration.requestFocus();
            }
            case SHOP -> {
                root.getChildren().add(shopPane);
                shopPane.requestFocus();
            }
            case COMBAT -> {
                root.getChildren().add(combat);
                combat.requestFocus();
            }
        }
        currentPhase=newPhase;
    }
    public Player getPlayer() {return player;}
}