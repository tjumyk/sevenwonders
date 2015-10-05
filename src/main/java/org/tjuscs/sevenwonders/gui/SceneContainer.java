package org.tjuscs.sevenwonders.gui;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;

public abstract class SceneContainer {
    private double initX;
    private double initY;
    private Point2D dragAnchor;
    protected Group root;
    protected Scene scene;
    protected InnerDialog exitDialog;
    private boolean draggable;
    private Node dragBg;
    private boolean first = true;
    private EventHandler<MouseEvent> anchor, drag;

    public SceneContainer() {
        root = new Group();
        draggable = true;
        anchor = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                // Node s = (Node) me.getSource();
                initX = scene.getWindow().getX();
                initY = scene.getWindow().getY();
                dragAnchor = new Point2D(me.getScreenX(), me.getScreenY());
                // self.toFront();
            }
        };
        drag = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                // Node s = (Node) me.getSource();
                double dragX = me.getScreenX() - dragAnchor.getX();
                double dragY = me.getScreenY() - dragAnchor.getY();
                // calculate new position of the circle
                double newXPosition = initX + dragX;
                double newYPosition = initY + dragY;
                scene.getWindow().setX(newXPosition);
                scene.getWindow().setY(newYPosition);

                me.consume();
            }
        };
    }

    // @Override
    // public final void start(Stage primaryStage) throws Exception {
    // load();
    // primaryStage.setScene(scene);
    // primaryStage.show();
    // replay();
    // }

    // public final static void main(String[] args) {
    // launch(args);
    // }

    public final void queryQuit() {
        if (exitDialog != null)
            exitDialog.show();
    }

    public Scene getScene() {
        return scene;
    }

    public abstract void load();

    public final void play() {
        if (!first) {
            replay();
        } else {
            firstPlay();
            first = false;
        }
    }

    public void firstPlay() {
    }

    public void replay() {
    }

    public void setDragBackground(Node bg) {
        dragBg = bg;
        // setDraggable(true);
        dragBg.addEventHandler(MouseEvent.MOUSE_PRESSED, anchor);
        dragBg.addEventHandler(MouseEvent.MOUSE_DRAGGED, drag);
    }

    // public void setDraggable(boolean isDraggable){
    // if(!isDraggable){
    // dragBg.removeEventHandler(MouseEvent.MOUSE_PRESSED, anchor);
    // dragBg.removeEventHandler(MouseEvent.MOUSE_DRAGGED, drag);
    // }else{
    // dragBg.addEventHandler(MouseEvent.MOUSE_PRESSED, anchor);
    // dragBg.addEventHandler(MouseEvent.MOUSE_DRAGGED, drag);
    // }
    // }
}
