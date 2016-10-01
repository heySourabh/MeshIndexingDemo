package meshindexingdemo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

public class MeshIndexingDemo extends Application {

    private static final int NUM_X_CELLS = 20;
    private static final int NUM_Y_CELLS = 12;
    private static final int NUM_GHOST_CELLS = 2;
    final static Image ICON_IMAGE;

    static {
        ICON_IMAGE = new Image(MeshIndexingDemo.class
                .getResourceAsStream("/images/mesh_64x64.png"));
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        showAboutDialog(ICON_IMAGE);
        primaryStage.getIcons().add(ICON_IMAGE);
        primaryStage.setAlwaysOnTop(true);

        TilePane tiles = new TilePane(5, 5);
        tiles.setEffect(new DropShadow(5, Color.BLACK));
        tiles.setPadding(new Insets(10));
        //tiles.setCache(true);

        tiles.setBackground(Background.EMPTY);
        tiles.setPrefColumns(NUM_X_CELLS);
        tiles.setPrefRows(NUM_Y_CELLS);
        tiles.setOrientation(Orientation.VERTICAL);
        Rectangle[][] cells = new Rectangle[NUM_X_CELLS][NUM_Y_CELLS];
        for (int i = 0; i < NUM_X_CELLS; i++) {
            for (int j = NUM_Y_CELLS - 1; j >= 0; j--) {
                Color color = i < NUM_GHOST_CELLS || j < NUM_GHOST_CELLS
                        || i >= NUM_X_CELLS - NUM_GHOST_CELLS
                        || j >= NUM_Y_CELLS - NUM_GHOST_CELLS
                                ? Color.GRAY : Color.GREEN;
                cells[i][j] = new Rectangle(50, 50, color);
                tiles.getChildren().add(cells[i][j]);
                int ii = i;
                int jj = j;
                cells[i][j].setOnMouseClicked(e -> showToolTip(e, ii, jj));
                cells[i][j].setOnMouseEntered(e -> mouseEntered(e));
                cells[i][j].setOnMouseExited(e -> mouseExited(e));
            }
        }
        Scene scene = new Scene(tiles, Color.TRANSPARENT);
        scene.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.SECONDARY) {
                hideToolTip();
                showAboutDialog(ICON_IMAGE);
            }
            if (e.getClickCount() == 2) {
                hideToolTip();
                primaryStage.setIconified(true);
            }
        });

        primaryStage.setScene(scene);
        DraggableStage.addDragablility(primaryStage);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setTitle("Cartesian Mesh Indexing Demo");

        primaryStage.show();
        primaryStage.setMinWidth(primaryStage.getWidth());
        primaryStage.setMinHeight(primaryStage.getHeight());
        primaryStage.setMaxWidth(primaryStage.getWidth());
        primaryStage.setMaxHeight(primaryStage.getHeight());
    }
    Tooltip indexTip = null;

    private void showToolTip(MouseEvent e, int i, int j) {
        hideToolTip();
        indexTip = new Tooltip(i + ", " + j);
        indexTip.setFont(new Font(15));
        indexTip.show((Node) e.getSource(), e.getScreenX() + 2, e.getScreenY() + 2);
    }

    private void hideToolTip() {
        if (indexTip != null) {
            indexTip.hide();
        }
    }

    private void mouseEntered(MouseEvent e) {
        ((Node) e.getSource()).setEffect(new Glow(0.5));
    }

    private void mouseExited(MouseEvent e) {
        ((Node) e.getSource()).setEffect(new Glow(0.0));
    }
    
    private static void showAboutDialog(Image ICON_IMAGE) {
        new AboutDialog(ICON_IMAGE).showAndWait();
    }
}
