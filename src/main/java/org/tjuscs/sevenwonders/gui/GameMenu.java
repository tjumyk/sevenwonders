package org.tjuscs.sevenwonders.gui;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import org.tjuscs.sevenwonders.Manager;

public class GameMenu extends Group {
    static GameMenu gm;

    public GameMenu() {
        gm = this;
        ImageView menuBackground = new ImageView(ResManager.getImage("bg.png"));
        menuBackground.setScaleX(0.4f);
        menuBackground.setScaleY(0.15f);
        menuBackground.setLayoutX(-50.0);
        // menuBackground.setLayoutY(-255.0);
        menuBackground.setLayoutY(-335.0);
        if (GUIManager.enableDropShadowEffect)
            menuBackground.setEffect(new DropShadow());
        Rectangle mask = new Rectangle(600, 600);
        mask.setArcWidth(50);
        mask.setArcHeight(133.3);
        menuBackground.setClip(mask);
        menuBackground.setOpacity(0.85f);
        this.getChildren().add(menuBackground);

        final Button soundButton = new Button();
        soundButton.setGraphic(new ImageView(ResManager.getImage("img_sound.png")));
        soundButton.setLayoutX(150.0);
        // optionButton.setLayoutY(20.0);
        soundButton.setLayoutY(-50.0);
        setButtonEffect(soundButton);
        this.getChildren().add(soundButton);

        Button returnButton = new Button();
        returnButton.setGraphic(new ImageView(ResManager.getImage("img_return.png")));
        returnButton.setLayoutX(220.0);
        // returnButton.setLayoutY(20.0);
        returnButton.setLayoutY(-50.0);
        setButtonEffect(returnButton);
        this.getChildren().add(returnButton);

        Button exitButton = new Button();
        exitButton.setGraphic(new ImageView(ResManager.getImage("img_exit.png")));
        exitButton.setLayoutX(290.0);
        // exitButton.setLayoutY(20.0);
        exitButton.setLayoutY(-50.0);
        setButtonEffect(exitButton);
        this.getChildren().add(exitButton);

        final Timeline down = new Timeline();
        down.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO, new KeyValue(menuBackground.layoutYProperty(), -335.0), new KeyValue(
                        soundButton.layoutYProperty(), -50.0), new KeyValue(returnButton.layoutYProperty(), -50.0),
                        new KeyValue(exitButton.layoutYProperty(), -50.0)),
                new KeyFrame(new Duration(100), new KeyValue(menuBackground.layoutYProperty(), -265.0), new KeyValue(
                        soundButton.layoutYProperty(), 15.0), new KeyValue(returnButton.layoutYProperty(), 15.0),
                        new KeyValue(exitButton.layoutYProperty(), 15.0)));

        final Timeline up = new Timeline();
        up.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO, new KeyValue(menuBackground.layoutYProperty(), -265.0), new KeyValue(
                        soundButton.layoutYProperty(), 15.0), new KeyValue(returnButton.layoutYProperty(), 15.0),
                        new KeyValue(exitButton.layoutYProperty(), 15.0)),
                new KeyFrame(new Duration(100), new KeyValue(menuBackground.layoutYProperty(), -335.0), new KeyValue(
                        soundButton.layoutYProperty(), -50.0), new KeyValue(returnButton.layoutYProperty(), -50.0),
                        new KeyValue(exitButton.layoutYProperty(), -50.0)));

        this.setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                gm.toFront();
                down.play();
            }
        });

        this.setOnMouseExited(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                up.play();
            }
        });

        soundButton.setOnMouseClicked(new EventHandler<MouseEvent>() { // Sound
            public void handle(MouseEvent event) {
                if (GUIManager.bgMusic.isPlaying()) {
                    soundButton.setGraphic(new ImageView(ResManager.getImage("img_no_sound.png")));
                    GUIManager.bgMusic.stop();
                } else {
                    soundButton.setGraphic(new ImageView(ResManager.getImage("img_sound.png")));
                    GUIManager.bgMusic.play(GUIManager.volumn);
                }
            }
        });

        returnButton.setOnMouseClicked(new EventHandler<MouseEvent>() { // Return
            // to
            // main
            // menu
            public void handle(MouseEvent event) {
                Manager.getGUI().restart();
            }
        });

        exitButton.setOnMouseClicked(new EventHandler<MouseEvent>() { // Exit
            // Game
            public void handle(MouseEvent event) {
                MainBackGround.quit();
            }
        });
        if (GUIManager.enableDropShadowEffect)
            this.setEffect(new DropShadow());
    }

    public static void setButtonEffect(Button btn) {
        btn.setStyle("-fx-base: #e9cf9e;");
        if (GUIManager.enableDropShadowEffect)
            btn.setEffect(new DropShadow());
        btn.setOpacity(0.6f);
    }

}
