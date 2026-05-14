package RPG.gui.views;

import RPG.engine.characters.Player;
import RPG.engine.system.AssetManager;
import RPG.engine.system.MenuOption;
import RPG.engine.world.MenuNavigator;
import RPG.engine.world.Shop;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ShopPane extends BorderPane {
    private final DialogueBox dialogueBox;
    private final MenuNavigator navigator;
    private final Label goldLabel;

    public ShopPane(Player player, Shop shop, Runnable onExit) {
        setStyle("-fx-background-color: black;");
        setPadding(new Insets(20));
        setFocusTraversable(true);

        Font titleFont = Font.font("Monospaced", FontWeight.BOLD, 28);
        Font textFont = Font.font("Monospaced", FontWeight.NORMAL, 16);

        HBox topBar = new HBox(20);
        topBar.setAlignment(Pos.CENTER_LEFT);
        Label title = new Label("MERCHANT SHOP");
        title.setFont(titleFont);
        title.setTextFill(Color.WHITE);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        goldLabel = new Label("Gold: " + player.getGold());
        goldLabel.setFont(textFont);
        goldLabel.setTextFill(Color.GOLD);
        topBar.getChildren().addAll(title, spacer, goldLabel);
        setTop(topBar);

        shop.createShopMenu(player, onExit);
        MenuOption root = shop.getMenuOption();
        navigator = new MenuNavigator(root);
        dialogueBox = new DialogueBox(navigator);
        dialogueBox.setMinHeight(150);
        setBottom(dialogueBox);

        ImageView merchantImage = new ImageView(AssetManager.getImages().get("shop"));
        merchantImage.setPreserveRatio(true);
        merchantImage.setFitHeight(500);

        StackPane centerPane = new StackPane(merchantImage);
        centerPane.setAlignment(Pos.CENTER);
        setCenter(centerPane);

        addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            if (!dialogueBox.isRevealComplete()) {
                dialogueBox.skipTypewriterToEnd();
            }
        });

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
            goldLabel.setText("Gold: " + player.getGold());
        });
        Platform.runLater(this::requestFocus);
    }
}
