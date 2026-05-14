package RPG.gui;

import RPG.engine.characters.Player;
import RPG.engine.system.AssetManager;
import RPG.engine.system.GamePhase;
import RPG.engine.world.Chart;
import RPG.engine.world.Shop;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class ExplorationPane extends BorderPane {
    private final Circle dot;
    private int dotTileX, dotTileY;
    private final int tileSize;
    private final Chart chart;
    private final ScrollPane scroll;
    private final Pane worldLayer;

    // Example shop coordinates (row, col)
    private Shop shop=new Shop(SceneManager.getGameScene().getPlayer());
    private final Rectangle shopMarker;

    public ExplorationPane(Player player) throws Exception {
        setStyle("-fx-background-color: black;");

        // Load map image
        Image mapImage = AssetManager.getImages().get("map0");
        ImageView mapView = new ImageView(mapImage);
        mapView.setPreserveRatio(true);
        mapView.setSmooth(true);

        // Chart logic
        chart = new Chart("map0", 32); // logical grid
        tileSize = (int)(mapImage.getWidth() / chart.getWidth());

        // Dot setup
        dotTileX = chart.getWidth() / 2;
        dotTileY = chart.getHeight() / 2;
        dot = new Circle(tileSize / 4.0, Color.RED);
        updateDotPosition();

        // Shop marker (gold square)
        shopMarker = new Rectangle(tileSize, tileSize, Color.GOLD);
        shopMarker.setOpacity(0.5); // semi-transparent
        updateShopMarker();

        // World layer: image + dot + shop marker
        worldLayer = new Pane(mapView, shopMarker, dot);
        worldLayer.setMinSize(mapImage.getWidth(), mapImage.getHeight());
        worldLayer.setPrefSize(mapImage.getWidth(), mapImage.getHeight());

        // Scrollable wrapper
        scroll = new ScrollPane(worldLayer);
        scroll.setPannable(true);
        scroll.setFitToWidth(false);
        scroll.setFitToHeight(false);
        scroll.setStyle("-fx-background: black;");
        scroll.setFocusTraversable(false);

        setCenter(scroll);
        setTop(new StatBar(player));

        // Arrow + WASD key movement
        setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case W    -> moveDot(0, -1, player);
                case S  -> moveDot(0, 1, player);
                case A  -> moveDot(-1, 0, player);
                case D -> moveDot(1, 0, player);
                default -> {}
            }
        });

        setFocusTraversable(true);
        requestFocus();
    }

    private void moveDot(int dx, int dy, Player player) {
        int nx = dotTileX + dx;
        int ny = dotTileY + dy;
        int shopRow = shop.getPosition().getX();
        int shopCol = shop.getPosition().getY();
        if (nx >= 0 && nx < chart.getWidth() && ny >= 0 && ny < chart.getHeight()) {
            dotTileX = nx;
            dotTileY = ny;
            updateDotPosition();
            centerScrollOnDot();

            // Check if dot is on shop tile
            if (dotTileY == shopRow && dotTileX == shopCol) {
                System.out.println("Entered shop at (" + shopRow + "," + shopCol + ")");
                SceneManager.getGameScene().switchPhase(GamePhase.SHOP);
            }
        }
    }

    private void updateDotPosition() {
        dot.setCenterX(dotTileX * tileSize + tileSize / 2.0);
        dot.setCenterY(dotTileY * tileSize + tileSize / 2.0);
    }

    private void updateShopMarker() {
        int shopRow = shop.getPosition().getX();
        int shopCol = shop.getPosition().getY();
        shopMarker.setX(shopCol * tileSize);
        shopMarker.setY(shopRow * tileSize);
    }

    private void centerScrollOnDot() {
        double contentWidth = worldLayer.getWidth();
        double contentHeight = worldLayer.getHeight();

        double dotX = dot.getCenterX();
        double dotY = dot.getCenterY();

        double hValue = (dotX - scroll.getViewportBounds().getWidth() / 2)
                        / (contentWidth - scroll.getViewportBounds().getWidth());
        double vValue = (dotY - scroll.getViewportBounds().getHeight() / 2)
                        / (contentHeight - scroll.getViewportBounds().getHeight());

        scroll.setHvalue(clamp(hValue));
        scroll.setVvalue(clamp(vValue));
    }

    private double clamp(double value) {
        return Math.max(0, Math.min(1, value));
    }
}