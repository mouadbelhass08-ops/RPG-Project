package RPG.gui; // Vérifie que c'est le même package que ton MainMenu !

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Callback;

public class MerchantScene {

    // --- CONSTANTES DE STYLE (Identiques au Menu) ---
    private static final Background FOND_NOIR = new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY));
    private static final Border BORDURE_BLANCHE = new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2)));
    private static final Font POLICE_TITRE = Font.font("Monospaced", FontWeight.BOLD, 30);
    private static final Font POLICE_TEXTE = Font.font("Monospaced", FontWeight.NORMAL, 16);
    private static final Color COULEUR_TEXTE = Color.WHITE;

    // Cette méthode crée et renvoie la Scène du magasin
    public Scene createScene(Stage stage, Scene menuScene) {
        
        // 1. Structure Principale (BorderPane)
        BorderPane root = new BorderPane();
        root.setBackground(FOND_NOIR);
        root.setPadding(new Insets(20));

        // 2. HAUT : Le Titre
        Label title = new Label("MERCHANT SHOP");
        title.setFont(POLICE_TITRE);
        title.setTextFill(COULEUR_TEXTE);
        BorderPane.setAlignment(title, Pos.CENTER);
        root.setTop(title);

        // 3. CENTRE : Les Listes (Vendeur vs Joueur)
        HBox centerBox = new HBox(20); // 20px d'espace au milieu
        centerBox.setAlignment(Pos.CENTER);
        
        // Colonne Vendeur
        VBox shopColumn = createColumn("MERCHANT WARES");
        ListView<String> shopList = createStyledListView();
        shopList.getItems().addAll("Long Sword - 50G", "Red Potion - 10G", "Magic Gem - 100G");
        shopColumn.getChildren().add(shopList);

        // Colonne Joueur
        VBox playerColumn = createColumn("YOUR INVENTORY");
        ListView<String> playerList = createStyledListView();
        playerList.getItems().addAll("Broken Dagger", "Apple");
        playerColumn.getChildren().add(playerList);

        // On dit aux colonnes de prendre toute la largeur
        HBox.setHgrow(shopColumn, Priority.ALWAYS);
        HBox.setHgrow(playerColumn, Priority.ALWAYS);
        centerBox.getChildren().addAll(shopColumn, playerColumn);

        root.setCenter(centerBox);

        // 4. BAS : Boutons et Retour
        HBox bottomBox = new HBox(20);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(20, 0, 0, 0));

        Button btnBuy = createRetroButton("BUY");
        Button btnSell = createRetroButton("SELL");
        
        // Bouton Important : RETOUR AU MENU
        Button btnBack = createRetroButton("BACK TO MENU");
        btnBack.setOnAction(e -> {
            stage.setScene(menuScene); // On remet la scène du menu !
        });

        bottomBox.getChildren().addAll(btnBuy, btnSell, btnBack);
        root.setBottom(bottomBox);

        return new Scene(root, 800, 600);
    }

    // --- OUTILS DE STYLE ---

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
        
        // Effet Survol simple
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

    // Création d'une liste noire et blanche (compliqué sans CSS, mais ça marche !)
    private ListView<String> createStyledListView() {
        ListView<String> list = new ListView<>();
        list.setBackground(FOND_NOIR);
        list.setBorder(BORDURE_BLANCHE);
        
        // Cette partie magique colorie chaque ligne en noir
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