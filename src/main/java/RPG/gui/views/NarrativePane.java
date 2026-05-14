package RPG.gui.views;

import RPG.engine.narrative.PhilosophyIntro;
import RPG.engine.system.AssetManager;
import RPG.engine.system.Game;
import RPG.engine.system.MenuOption;
import RPG.engine.world.MenuNavigator;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Map-free narrative screen: static backdrop + {@link StatBar} + {@link DialogueBox}.
 * Story content is supplied by engine-side scripts (here {@link PhilosophyIntro}) that build
 * {@link MenuOption} trees and drive {@link MenuNavigator#resetTo} between beats.
 */
public class NarrativePane extends BorderPane {
    private final DialogueBox dialogueBox;
    private final MenuNavigator navigator;

    public NarrativePane(Game game) {
        setStyle("-fx-background-color: black;");

        Image bg = AssetManager.getImages().get("backgroundimage");
        ImageView bgView = new ImageView(bg);
        bgView.setPreserveRatio(true);
        bgView.setSmooth(true);
        bgView.fitWidthProperty().bind(widthProperty());
        bgView.fitHeightProperty().bind(heightProperty());

        StackPane center = new StackPane(bgView);
        center.setMinSize(0, 0);
        setCenter(center);

        setTop(new StatBar(game.getPrimaryPlayer()));

        MenuOption placeholder = new MenuOption("(Loading…)");
        navigator = new MenuNavigator(placeholder);
        dialogueBox = new DialogueBox(navigator);

        Label hint = new Label("Click — reveal full line   ·   W / S — move   Enter / Space / D — choose   ·   A — back (when available)");
        hint.setFont(Font.font("Monospaced", FontWeight.NORMAL, 12));
        hint.setTextFill(Color.GRAY);

        VBox bottom = new VBox(8, dialogueBox, hint);
        bottom.setPadding(new Insets(0, 16, 16, 16));
        bottom.setAlignment(Pos.CENTER_LEFT);
        dialogueBox.prefWidthProperty().bind(widthProperty().subtract(32));
        setBottom(bottom);

        addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            if (!dialogueBox.isRevealComplete()) {
                dialogueBox.skipTypewriterToEnd();
            }
        });

        setFocusTraversable(true);
        setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                return;
            }
            if (!dialogueBox.isRevealComplete()) {
                e.consume();
                return;
            }
            switch (e.getCode()) {
                case W -> navigator.moveUp();
                case S -> navigator.moveDown();
                case A -> navigator.goBack();
                case D, ENTER, SPACE -> navigator.select();
                default -> {}
            }
            dialogueBox.refresh();
        });

        PhilosophyIntro.start(navigator, dialogueBox::refresh);

        Platform.runLater(() -> {
            requestFocus();
            dialogueBox.refresh();
        });
    }
}
