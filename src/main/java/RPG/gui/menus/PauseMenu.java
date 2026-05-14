package RPG.gui.menus;

import RPG.engine.system.GameState;
import RPG.gui.SceneManager;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class PauseMenu {
    private final Rectangle dimOverlay;
    private final VBox buttonBox;

    public PauseMenu(double width, double height) {
        dimOverlay = new Rectangle(width, height, Color.BLACK);
        dimOverlay.setOpacity(0.5);
        dimOverlay.setVisible(false);

        buttonBox = new VBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setVisible(false);

        Button resumeButton = createPauseButton("Resume");
        Button mainMenuButton = createPauseButton("Main Menu");
        Button optionsButton = createPauseButton("Options");
        Button quitButton = createPauseButton("Quit");

        resumeButton.setOnAction(e -> {
            hide();
            SceneManager.togglePause(false);
        });
        mainMenuButton.setOnAction(e -> {
            hide();
            SceneManager.togglePause(false);
            SceneManager.switchTo(GameState.MAINMENU);
        });
        quitButton.setOnAction(e -> System.exit(0));

        buttonBox.getChildren().addAll(resumeButton, mainMenuButton, optionsButton, quitButton);
    }

    private Button createPauseButton(String text) {
        Button btn = new Button(text);
        btn.setFont(Font.font("Monospaced", FontWeight.BOLD, 18));
        btn.setTextFill(Color.WHITE);
        btn.setStyle("-fx-background-color: black; -fx-border-color: white; -fx-border-width: 2;");

        btn.setOnMouseEntered(e -> {
            btn.setStyle("-fx-background-color: white; -fx-border-color: white; -fx-border-width: 2;");
            btn.setTextFill(Color.BLACK);
        });
        btn.setOnMouseExited(e -> {
            btn.setStyle("-fx-background-color: black; -fx-border-color: white; -fx-border-width: 2;");
            btn.setTextFill(Color.WHITE);
        });

        return btn;
    }

    public void show(StackPane root) {
        if (!root.getChildren().contains(dimOverlay)) {
            root.getChildren().add(dimOverlay);
        }
        if (!root.getChildren().contains(buttonBox)) {
            root.getChildren().add(buttonBox);
        }
        dimOverlay.setVisible(true);
        buttonBox.setVisible(true);
    }

    public void hide() {
        dimOverlay.setVisible(false);
        buttonBox.setVisible(false);
    }
}
