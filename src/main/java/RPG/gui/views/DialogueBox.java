package RPG.gui.views;

import java.util.List;

import RPG.engine.system.MenuOption;
import RPG.engine.world.MenuNavigator;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

/**
 * Narration + choices for the current {@link MenuNavigator} pointer. Beat text is revealed
 * character-by-character; choices appear only after the line finishes (shop menus use the same flow).
 */
public class DialogueBox extends VBox {
    private static final int DEFAULT_MS_PER_CHAR = 26;

    private final MenuNavigator navigator;
    private final Font titleFont = Font.font("Monospaced", FontWeight.BOLD, 18);
    private final Font itemFont = Font.font("Monospaced", FontWeight.NORMAL, 16);

    private final Label narrationLabel = new Label();
    private final Separator separator = new Separator();
    private final VBox choicesBox = new VBox(4);

    private Timeline typewriter;
    private MenuOption lastBeatPointer;
    private String revealingText = "";
    private int revealIndex;
    private boolean revealComplete = true;
    private int msPerChar = DEFAULT_MS_PER_CHAR;

    public DialogueBox(MenuNavigator navigator) {
        this.navigator = navigator;
        setSpacing(8);
        setPadding(new Insets(12));
        setStyle("-fx-background-color: black; -fx-border-color: white; -fx-border-width: 2;");

        narrationLabel.setTextFill(Color.WHITE);
        narrationLabel.setFont(titleFont);
        narrationLabel.setWrapText(true);
        narrationLabel.setMaxWidth(Double.MAX_VALUE);
        narrationLabel.prefWidthProperty().bind(widthProperty());

        choicesBox.setSpacing(4);

        getChildren().setAll(narrationLabel, separator, choicesBox);
        refresh();
    }

    /** Typing speed: delay between each character (default {@value #DEFAULT_MS_PER_CHAR} ms). */
    public void setMsPerChar(int msPerChar) {
        this.msPerChar = Math.max(4, msPerChar);
    }

    public void dispose() {
        stopTypewriter();
    }

    private void stopTypewriter() {
        if (typewriter != null) {
            typewriter.stop();
            typewriter = null;
        }
    }

    public void refresh() {
        MenuOption pointer = navigator.getPointer();
        String fullText = pointer != null ? pointer.getText() : "(No menu)";

        boolean newBeat = pointer != lastBeatPointer;
        if (newBeat) {
            stopTypewriter();
            lastBeatPointer = pointer;
            revealingText = fullText;
            revealIndex = 0;
            revealComplete = fullText.isEmpty();
            narrationLabel.setText("");
            choicesBox.getChildren().clear();
            separator.setVisible(!revealComplete);

            if (revealComplete) {
                fillChoices();
            } else {
                typewriter = new Timeline(new KeyFrame(Duration.millis(msPerChar), e -> tickTypewriter()));
                typewriter.setCycleCount(Timeline.INDEFINITE);
                typewriter.play();
            }
            return;
        }

        if (revealComplete) {
            fillChoices();
        }
    }

    private void tickTypewriter() {
        if (revealIndex < revealingText.length()) {
            revealIndex++;
        }
        narrationLabel.setText(revealingText.substring(0, Math.min(revealIndex, revealingText.length())));
        if (revealIndex >= revealingText.length()) {
            stopTypewriter();
            revealComplete = true;
            separator.setVisible(true);
            fillChoices();
        }
    }

    private void fillChoices() {
        choicesBox.getChildren().clear();
        List<MenuOption> options = navigator.getCurrentOptions();
        int selectedIndex = navigator.getSelectedIndex();

        if (options.isEmpty()) {
            Label empty = new Label("(No options)");
            empty.setTextFill(Color.GRAY);
            empty.setFont(itemFont);
            choicesBox.getChildren().add(empty);
            return;
        }

        for (int i = 0; i < options.size(); i++) {
            MenuOption opt = options.get(i);
            Label label = new Label((i == selectedIndex ? "> " : "  ") + opt.getText());
            label.setTextFill(Color.WHITE);
            label.setFont(itemFont);
            choicesBox.getChildren().add(label);
        }
    }
}
