package RPG.gui.views;

import RPG.engine.characters.Player;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class StatBar extends HBox {
    private final Player player;
    private final Label hpLabel = new Label();
    private final Label energyLabel = new Label();
    private final Label manaLabel = new Label();
    private final Label goldLabel = new Label();

    public StatBar(Player player) {
        this.player = player;

        setSpacing(20);
        setPadding(new Insets(10));
        setAlignment(Pos.CENTER_LEFT);
        setMinHeight(50);
        setStyle("-fx-background-color: #222; -fx-border-color: #666; -fx-border-width: 0 0 2 0;");

        hpLabel.setTextFill(Color.RED);
        energyLabel.setTextFill(Color.ORANGE);
        manaLabel.setTextFill(Color.CYAN);
        goldLabel.setTextFill(Color.GOLD);

        getChildren().addAll(hpLabel, energyLabel, manaLabel, goldLabel);
        refresh();
    }

    public void refresh() {
        hpLabel.setText("HP: " + player.getHealth().getCurrentHealth());
        energyLabel.setText("Energy: " + player.getEnergy().getCurrentEnergy());
        manaLabel.setText("Mana: " + player.getMana().getCurrentMana());
        goldLabel.setText("Gold: " + player.getGold());
    }
}
