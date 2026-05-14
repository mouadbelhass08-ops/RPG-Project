package RPG;

import javafx.application.Application;
import javafx.stage.Stage;
import RPG.engine.system.AssetManager;
import RPG.gui.SceneManager;

public class GameApp extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        AssetManager.loadAssets();
        SceneManager.initiate(primaryStage);
    }
}