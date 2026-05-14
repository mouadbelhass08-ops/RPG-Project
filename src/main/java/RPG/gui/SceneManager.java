package RPG.gui;

import RPG.engine.system.Game;
import RPG.engine.system.GameState;
import RPG.engine.system.SoundManager;
import RPG.gui.menus.PauseMenu;
import RPG.gui.scenes.GameScene;
import RPG.gui.scenes.LoadingScene;
import RPG.gui.scenes.MainMenu;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Macro-level presentation: main menu vs in-game root, transitions, pause overlay.
 * {@link Game} owns micro-phases; call {@link Game#setPhase} for exploration / combat / shop.
 */
public class SceneManager {
    private static Stage stage;
    private static Scene scene;
    private static Game game;
    private static final MainMenu mainmenu = new MainMenu();
    private static final GameScene gamescene = new GameScene();
    private static final LoadingScene loadingscene = new LoadingScene();
    private static PauseMenu pausemenu;
    private static boolean paused;

    public static void initiate(Stage primaryStage, Game gameSession) {
        stage = primaryStage;
        game = gameSession;
        stage.setTitle("RPG Game");

        Parent loadingRoot = loadingscene.createRoot();
        scene = new Scene(loadingRoot, 1280, 720, Color.BLACK);

        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.setResizable(false);
        stage.show();

        pausemenu = new PauseMenu(40, 100);
    }

    public static Game getGame() {
        return game;
    }

    public static void switchTo(GameState state) {
        fadeOut(scene.getRoot(), 500, () -> {
            StackPane blackScreen = new StackPane();
            blackScreen.setStyle("-fx-background-color: black;");
            scene.setRoot(blackScreen);

            PauseTransition pause = new PauseTransition(Duration.millis(100));
            pause.setOnFinished(ev -> {
                if (game != null) {
                    game.setMacroState(state);
                    if (state == GameState.MAINMENU) {
                        game.setOnPhaseChanged(null);
                    }
                }

                Parent newRoot = switch (state) {
                    case MAINMENU -> mainmenu.createRoot(scene);
                    case INGAME -> gamescene.createRoot(scene, game);
                };

                newRoot.setOpacity(0);
                scene.setRoot(newRoot);
                fadeIn(newRoot, 500);

                switch (state) {
                    case MAINMENU -> SoundManager.playOst("mainmenu");
                    case INGAME -> SoundManager.playOst("ingame");
                }

                newRoot.requestFocus();
            });
            pause.play();
        });
    }

    private static void fadeIn(Parent root, int duration) {
        FadeTransition fadeIn = new FadeTransition(Duration.millis(duration), root);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();
    }

    private static void fadeOut(Parent root, int duration, Runnable onFinished) {
        FadeTransition fadeOut = new FadeTransition(Duration.millis(duration), root);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(e -> {
            if (onFinished != null) {
                onFinished.run();
            }
        });
        fadeOut.play();
    }

    public static void togglePause(boolean value) {
        StackPane root = (StackPane) scene.getRoot();
        paused = value;
        if (paused) {
            pausemenu.show(root);
        } else {
            pausemenu.hide();
        }
    }

    public static boolean isPaused() {
        return paused;
    }

    public static void closeGame() {
        SoundManager.stopOst();
        stage.close();
        System.exit(0);
    }

    public static MainMenu getMainMenu() {
        return mainmenu;
    }

    public static Parent getRoot() {
        return scene.getRoot();
    }
}
