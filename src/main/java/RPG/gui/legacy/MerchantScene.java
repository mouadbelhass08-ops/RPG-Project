package RPG.gui.legacy;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * Unused alternate merchant UI; kept for reference. Prefer {@code RPG.gui.views.ShopPane}.
 */
public class MerchantScene {

    private static final Background FOND_NOIR =
            new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY));
    private static final Border BORDURE_BLANCHE =
            new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2)));
    private static final Font POLICE_TITRE = Font.font("Monospaced", FontWeight.BOLD, 30);
    private static final Font POLICE_TEXTE = Font.font("Monospaced", FontWeight.NORMAL, 16);
    private static final Color COULEUR_TEXTE = Color.WHITE;

    public Scene createScene(Stage stage, Scene menuScene) {
        BorderPane root = new BorderPane();
        root.setBackground(FOND_NOIR);
        root.setPadding(new Insets(20));

        Label title = new Label("MERCHANT SHOP");
        title.setFont(POLICE_TITRE);
        title.setTextFill(COULEUR_TEXTE);
        BorderPane.setAlignment(title, Pos.CENTER);
        root.setTop(title);

        HBox centerBox = new HBox(20);
        centerBox.setAlignment(Pos.CENTER);

        VBox shopColumn = createColumn("MERCHANT WARES");
        ListView<String> shopList = createStyledListView();
        shopList.getItems().addAll("Long Sword - 50G", "Red Potion - 10G", "Magic Gem - 100G");
        shopColumn.getChildren().add(shopList);

        VBox playerColumn = createColumn("YOUR INVENTORY");
        ListView<String> playerList = createStyledListView();
        playerList.getItems().addAll("Broken Dagger", "Apple");
        playerColumn.getChildren().add(playerList);

        HBox.setHgrow(shopColumn, Priority.ALWAYS);
        HBox.setHgrow(playerColumn, Priority.ALWAYS);
        centerBox.getChildren().addAll(shopColumn, playerColumn);

        root.setCenter(centerBox);

        HBox bottomBox = new HBox(20);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(20, 0, 0, 0));

        Button btnBuy = createRetroButton("BUY");
        Button btnSell = createRetroButton("SELL");

        Button btnBack = createRetroButton("BACK TO MENU");
        btnBack.setOnAction(e -> stage.setScene(menuScene));

        bottomBox.getChildren().addAll(btnBuy, btnSell, btnBack);
        root.setBottom(bottomBox);

        return new Scene(root, 800, 600);
    }

    private VBox createColumn(String title) {
        VBox box = new VBox(5);
        Label label = new Label(title);
        label.setFont(POLICE_TEXTE);
        label.setTextFill(Color.LIGHTGRAY);
        box.getChildren().add(label);
        return box;
    }

    private Button createRetroButton(String text) {
        Button btn = new Button(text);
        btn.setFont(POLICE_TEXTE);
        btn.setTextFill(COULEUR_TEXTE);
        btn.setBackground(FOND_NOIR);
        btn.setBorder(BORDURE_BLANCHE);

        btn.setOnMouseEntered(e -> {
            btn.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
            btn.setTextFill(Color.BLACK);
        });
        btn.setOnMouseExited(e -> {
            btn.setBackground(FOND_NOIR);
            btn.setTextFill(COULEUR_TEXTE);
        });

        return btn;
    }

    private ListView<String> createStyledListView() {
        ListView<String> list = new ListView<>();
        list.setBackground(FOND_NOIR);
        list.setBorder(BORDURE_BLANCHE);

        list.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new ListCell<String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        setBackground(FOND_NOIR);
                        setTextFill(Color.WHITE);
                        setFont(POLICE_TEXTE);
                        if (empty || item == null) {
                            setText(null);
                        } else {
                            setText(item);
                        }
                    }
                };
            }
        });
        return list;
    }
}
