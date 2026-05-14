package RPG.gui.views;

import java.util.List;

import RPG.engine.system.MenuOption;
import RPG.engine.world.MenuNavigator;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class DialogueBox extends VBox {
    private final MenuNavigator navigator;
    private final Font titleFont = Font.font("Monospaced", FontWeight.BOLD, 18);
    private final Font itemFont = Font.font("Monospaced", FontWeight.NORMAL, 16);

    public DialogueBox(MenuNavigator navigator) {
        this.navigator = navigator;
        setSpacing(8);
        setPadding(new Insets(12));
        setStyle("-fx-background-color: black; -fx-border-color: white; -fx-border-width: 2;");
        refresh();
    }

    public void refresh() {
        getChildren().clear();
        MenuOption pointer = navigator.getPointer();
        String headerText = (pointer != null) ? pointer.getText() : "(No menu)";
        Label header = new Label(headerText);
        header.setTextFill(Color.WHITE);
        header.setFont(titleFont);
        getChildren().add(header);

        getChildren().add(new Separator());

        List<MenuOption> options = navigator.getCurrentOptions();
        int selectedIndex = navigator.getSelectedIndex();

        if (options.isEmpty()) {
            Label empty = new Label("(No options)");
            empty.setTextFill(Color.GRAY);
            empty.setFont(itemFont);
            getChildren().add(empty);
            return;
        }

        for (int i = 0; i < options.size(); i++) {
            MenuOption opt = options.get(i);
            Label label = new Label((i == selectedIndex ? "> " : "  ") + opt.getText());
            label.setTextFill(Color.WHITE);
            label.setFont(itemFont);
            getChildren().add(label);
        }
    }
}
