package RPG;

import RPG.engine.characters.Player;
import RPG.engine.system.AssetManager;
import RPG.engine.system.Game;
import RPG.gui.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class GameApp extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        AssetManager.loadAssets();

        Player hero = new Player("Hero", 50, 20, 10, 0, 0, 5, 200);
        Game game = new Game(hero);

        SceneManager.initiate(primaryStage, game);
    }
}
