package org.tjuscs.sevenwonders.gui;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class InnerDialog extends DropGroup {
    private final DropShadow dropShadow = new DropShadow();
    private Node focusNode;
    Timeline startAct, endAct;

    public InnerDialog(double width, double height, Timeline startAct, Timeline endAct) {
        this(width, height, startAct);
        this.endAct = endAct;
    }

    public InnerDialog(double width, double height, Timeline startAct) {
        load(width, height);
        this.startAct = startAct;
        this.setVisible(false);
    }

    public InnerDialog(double width, double height) {
        this(width, height, null);
    }

    public InnerDialog() {
        this(400, 300, null);
    }

    private void load(double width, double height) {
        ImageView bg = new ImageView(ResManager.getImage("bg.png"));
        // bg.setOpacity(0.85);
        bg.setScaleX(width / 600.0);
        bg.setScaleY(height / 600.0);
        bg.setLayoutX((width - 600) / 2.0);
        bg.setLayoutY((height - 600) / 2.0);
        Rectangle rec = new Rectangle(600, 600);
        rec.setArcWidth(20 * 600.0 / width);
        rec.setArcHeight(20 * 600.0 / height);
        bg.setClip(rec);
        this.getChildren().add(bg);
        // dropShadow.setColor(Color.web("#000000", 0.8));
        if (GUIManager.enableDropShadowEffect)
            this.setEffect(dropShadow);

        this.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ESCAPE)) {
                    InnerDialog s = (InnerDialog) event.getSource();
                    s.close();
                    event.consume();
                }
            }
        });

        final Text close = new Text("X");
        close.setStyle("-fx-font: 20 arial");
        close.setLayoutX(width - 20);
        close.setLayoutY(20);
        close.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                close();
            }
        });

        close.setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                if (GUIManager.enableDropShadowEffect)
                    close.setEffect(new DropShadow());
            }
        });

        close.setOnMouseExited(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                if (GUIManager.enableDropShadowEffect)
                    close.setEffect(null);
            }
        });

        this.getChildren().add(close);
    }

    public void show() {
        this.toFront();
        setVisible(true);
        this.getLayoutX();
        double ly = getLayoutY();
        EventHandler<ActionEvent> action = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                if (startAct != null) {
                    startAct.play();
                    // Manager.debug(flash.getKeyFrames().get(0).getOnFinished().toString());
                }
            }
        };

        Timeline tl = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(this.opacityProperty(), 0.0), new KeyValue(
                dropShadow.colorProperty(), Color.TRANSPARENT), new KeyValue(this.layoutYProperty(), ly - 30)),
                new KeyFrame(Duration.seconds(0.3), new KeyValue(this.opacityProperty(), 0.85), new KeyValue(this
                        .layoutYProperty(), ly), new KeyValue(dropShadow.colorProperty(), Color.web("#000000", 0.7))),
                new KeyFrame(Duration.seconds(0), action));
        tl.play();
        if (focusNode == null)
            this.requestFocus();
        else
            focusNode.requestFocus();
    }

    public void close() {
        // FadeTransition ft = new FadeTransition(Duration.seconds(0.4), this);
        // ft.setFromValue(0);
        // ft.setByValue(0.85);
        if (startAct != null)
            startAct.stop();
        final Group root = this;

        EventHandler<ActionEvent> act = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                root.getParent().requestFocus();
            }
        };
        this.getLayoutX();
        double ly = getLayoutY();
        Timeline tl = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(this.opacityProperty(), 0.85),
                new KeyValue(this.layoutYProperty(), ly), new KeyValue(dropShadow.colorProperty(), Color.web("#000000",
                0.7))), new KeyFrame(Duration.seconds(0.3), new KeyValue(this.opacityProperty(), 0.0),
                new KeyValue(dropShadow.colorProperty(), Color.TRANSPARENT), new KeyValue(this.layoutYProperty(),
                ly - 30), new KeyValue(this.visibleProperty(), false)),
                new KeyFrame(Duration.seconds(0.3), act), new KeyFrame(Duration.seconds(0.31), new KeyValue(
                this.layoutYProperty(), ly)));
        tl.play();
    }

    public void setDefaultFocus(Node node) {
        this.focusNode = node;
    }
}
