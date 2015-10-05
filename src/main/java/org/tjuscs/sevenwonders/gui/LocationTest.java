package org.tjuscs.sevenwonders.gui;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.tjuscs.sevenwonders.Manager;

public class LocationTest extends DropGroup {
    Rectangle box = new Rectangle(10, 10);
    Node node;

    private LocationTest(Color color, double width, double height) {
        this.setOnMouseReleased(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                DropGroup s = (DropGroup) me.getSource();
                double x = s.getTranslateX();
                double y = s.getTranslateY();
                Manager.debug(node + "(" + x + "," + y + ")");
            }
        });
        box.setFill(Color.TRANSPARENT);
        box.setStrokeWidth(10);
        box.setStroke(color);
        box.setHeight(height);
        box.setWidth(width);
        this.getChildren().add(box);
    }

    public LocationTest(Node node) {
        this(node, 50, 50);
    }

    public LocationTest(Node node, double width, double height) {
        this(node, width, height, Color.GRAY);
    }

    public LocationTest(Node node, double width, double height, Color color) {
        this(color, width, height);
        this.node = node;
        node.setLayoutX(0);
        node.setLayoutY(0);
        getChildren().add(node);
    }
}
