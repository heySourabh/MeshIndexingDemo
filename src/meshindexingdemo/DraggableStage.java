package meshindexingdemo;

import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class DraggableStage extends Pane {

    private static Point2D mouseLocation;

    public static void addDragablility(Stage stage) {
        Scene scene = stage.getScene();
        scene.setOnMousePressed(e -> mousePressed(e, stage));
        scene.setOnMouseDragged(e -> mouseDragged(e, stage));
    }

    private static void mousePressed(MouseEvent e, Stage stage) {
        mouseLocation = new Point2D(e.getScreenX() - stage.getX(), e.getScreenY() - stage.getY());
    }

    private static void mouseDragged(MouseEvent e, Stage stage) {
        Point2D dragLocation = new Point2D(e.getScreenX() - stage.getX(), e.getScreenY() - stage.getY());
        Point2D diff = dragLocation.subtract(mouseLocation);
        stage.setX(stage.getX() + diff.getX());
        stage.setY(stage.getY() + diff.getY());
    }
}
