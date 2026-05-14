package RPG.gui.scenes;

import RPG.engine.system.AssetManager;
import RPG.engine.system.GameState;
import RPG.engine.system.SoundManager;
import RPG.gui.SceneManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MainMenu {
    private static final String NOM_DU_JEU = "PIXEL LEGACY";
    private static final Background FOND_NOIR =
            new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY));
    private static final Font POLICE_TITRE = Font.font("Monospaced", FontWeight.BOLD, 40);
    private static final Font POLICE_BOUTON = Font.font("Monospaced", FontWeight.NORMAL, 20);
    private static final Color COULEUR_TEXTE = Color.WHITE;
    private static final Color COULEUR_HOVER = Color.GRAY;

    public Parent createRoot(Scene scene) {
        StackPane root = new StackPane();
        root.setBackground(FOND_NOIR);

        Image bgImg = AssetManager.getImages().get("backgroundimage");
        ImageView bgView = new ImageView(bgImg);
        bgView.setPreserveRatio(true);
        bgView.setSmooth(true);
        bgView.setCache(true);

        bgView.fitWidthProperty().bind(scene.widthProperty());
        bgView.fitHeightProperty().bind(scene.heightProperty());

        VBox menuBox = new VBox(30);
        menuBox.setAlignment(Pos.CENTER);

        Label title = new Label(NOM_DU_JEU);
        title.setFont(POLICE_TITRE);
        title.setTextFill(COULEUR_TEXTE);

        Button btnNew = createMenuButton("NEW GAME");
        Button btnContinue = createMenuButton("CONTINUE");
        Button btnOptions = createMenuButton("OPTIONS");
        Button btnExit = createMenuButton("EXIT");

        btnNew.setOnAction(e -> SceneManager.switchTo(GameState.INGAME));
        btnContinue.setOnAction(e -> { /* TODO: implement continue logic */ });
        btnOptions.setOnAction(e -> { /* TODO: implement options scene */ });
        btnExit.setOnAction(e -> SceneManager.closeGame());

        menuBox.getChildren().addAll(title, btnNew, btnContinue, btnOptions, btnExit);

        root.getChildren().addAll(bgView, menuBox);
        return root;
    }

    private Button createMenuButton(String text) {
        Button btn = new Button(text);
        btn.setFont(POLICE_BOUTON);
        btn.setTextFill(COULEUR_TEXTE);
        btn.setBackground(Background.EMPTY);
        btn.setBorder(Border.EMPTY);
        btn.setOnMouseEntered(e -> {
            btn.setTextFill(COULEUR_HOVER);
            btn.setText("> " + text + " <");
            SoundManager.playCue("button");
        });
        btn.setOnMouseExited(e -> {
            btn.setTextFill(COULEUR_TEXTE);
            btn.setText(text);
        });
        return btn;
    }
}
