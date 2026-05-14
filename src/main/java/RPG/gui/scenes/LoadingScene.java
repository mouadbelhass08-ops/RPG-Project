package RPG.gui.scenes;

import RPG.engine.system.GameState;
import RPG.gui.SceneManager;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

public class LoadingScene {
    private static final Font POLICE = Font.font("Monospaced", FontWeight.NORMAL, 20);
    private StackPane root;
    private Label loadingLabel;
    private Timeline timeline;

    public Parent createRoot() {
        root = new StackPane();
        root.setStyle("-fx-background-color: black;");
        loadingLabel = new Label("Loading");
        loadingLabel.setTextFill(Color.WHITE);
        loadingLabel.setFont(POLICE);
        root.getChildren().add(loadingLabel);
        startAnimation();
        scheduleEnd();
        return root;
    }

    private void startAnimation() {
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.5), e -> updateDots(1)),
                new KeyFrame(Duration.seconds(1.0), e -> updateDots(2)),
                new KeyFrame(Duration.seconds(1.5), e -> updateDots(3)),
                new KeyFrame(Duration.seconds(2.0), e -> updateDots(0)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void updateDots(int count) {
        String dots = ".".repeat(count);
        loadingLabel.setText("Loading" + dots);
    }

    private void scheduleEnd() {
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(e -> {
            timeline.stop();
            SceneManager.switchTo(GameState.MAINMENU);
        });
        pause.play();
    }
}
