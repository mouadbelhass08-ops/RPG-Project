package RPG.gui;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import RPG.engine.characters.Player;
import RPG.engine.system.AssetManager;
import RPG.engine.system.MenuOption;
import RPG.engine.world.MenuNavigator;
import javafx.geometry.Pos;
import javafx.scene.control.Button;

public class CombatPane extends BorderPane {
    private final DialogueBox dialogueBox;

    public CombatPane(Player player) {
        // Background image
        Image combatImg = AssetManager.getImages().get("combat");
        ImageView combatView = new ImageView(combatImg);
        combatView.setPreserveRatio(true);
        combatView.fitWidthProperty().bind(widthProperty());
        combatView.fitHeightProperty().bind(heightProperty());
        setCenter(combatView);

        // DialogueBox above actions
        MenuOption combatRoot = new MenuOption("Combat", null);
        combatRoot.addChild(new MenuOption("Attack", () -> System.out.println(player.getName() + " attacks!")));
        combatRoot.addChild(new MenuOption("Act", () -> System.out.println("Act chosen")));
        combatRoot.addChild(new MenuOption("Item", () -> System.out.println("Item chosen")));

        MenuNavigator navigator = new MenuNavigator(combatRoot);
        dialogueBox = new DialogueBox(navigator);

        VBox bottomBox = new VBox(10);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.getChildren().addAll(dialogueBox, createActionButtons(player));
        setBottom(bottomBox);
    }

    private HBox createActionButtons(Player player) {
        HBox actions = new HBox(20);
        actions.setAlignment(Pos.CENTER);

        Button attackBtn = new Button("ATTACK");
        Button actBtn = new Button("ACT");
        Button itemBtn = new Button("ITEM");

        attackBtn.setOnAction(e -> System.out.println(player.getName() + " attacks!"));
        actBtn.setOnAction(e -> System.out.println("Act chosen"));
        itemBtn.setOnAction(e -> System.out.println("Item chosen"));

        actions.getChildren().addAll(attackBtn, actBtn, itemBtn);
        return actions;
    }
}