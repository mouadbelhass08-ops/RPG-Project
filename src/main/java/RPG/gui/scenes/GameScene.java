package RPG.gui.scenes;

import RPG.engine.system.Game;
import RPG.engine.system.GamePhase;
import RPG.gui.SceneManager;
import RPG.gui.views.CombatPane;
import RPG.gui.views.ExplorationPane;
import RPG.gui.views.ShopPane;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;

/**
 * In-game root: registers with {@link Game} for micro-phase changes and swaps child views.
 */
public class GameScene {
    private final StackPane root = new StackPane();
    private ExplorationPane exploration;
    private ShopPane shopPane;
    private CombatPane combat;

    public Parent createRoot(Scene scene, Game session) {
        root.setStyle("-fx-background-color: black;");

        session.setOnPhaseChanged(phase -> Platform.runLater(() -> applyPhaseLayout(phase)));

        try {
            exploration = new ExplorationPane(session);
        } catch (Exception e) {
            e.printStackTrace();
        }
        shopPane = new ShopPane(session.getPrimaryPlayer(), session.getShop(),
                () -> session.setPhase(GamePhase.EXPLORATION));
        combat = new CombatPane(session.getPrimaryPlayer());

        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                e.consume();
                SceneManager.togglePause(!SceneManager.isPaused());
            }
        });

        applyPhaseLayout(session.getPhase());
        return root;
    }

    private void applyPhaseLayout(GamePhase newPhase) {
        root.getChildren().clear();
        switch (newPhase) {
            case EXPLORATION -> {
                if (exploration != null) {
                    root.getChildren().add(exploration);
                    exploration.requestFocus();
                }
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
    }
}
