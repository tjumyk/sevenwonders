package org.tjuscs.sevenwonders.gui;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class ReplayControl extends Group {
    static ReplayControl rc;
    private Timeline play;
    private double speed;
    private int rate;
    private boolean stopped;
    private Text speedText;

    public ReplayControl() {
        rc = this;
        stopped = false;
        EventHandler<ActionEvent> act = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                MainBackGround.nextTurn();
            }
        };
        play = new Timeline(new KeyFrame(Duration.seconds(5.0), act));
        play.setCycleCount(Timeline.INDEFINITE);
        play.setAutoReverse(false);
        speed = play.getRate();
        rate = 1;
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

        final Button playButton = new Button();
        playButton.setGraphic(new ImageView(ResManager.getImage("img_pause.png")));
        playButton.setLayoutX(150.0);
        // optionButton.setLayoutY(20.0);
        playButton.setLayoutY(-50.0);
        setButtonEffect(playButton);
        this.getChildren().add(playButton);

        Button fastForwardButton = new Button();
        fastForwardButton.setGraphic(new ImageView(ResManager.getImage("img_fastForward.png")));
        fastForwardButton.setLayoutX(220.0);
        // returnButton.setLayoutY(20.0);
        fastForwardButton.setLayoutY(-50.0);
        setButtonEffect(fastForwardButton);
        speedText = new Text("X1");
        speedText.setFont(GUIManager.font);
        speedText.setFill(Color.WHITESMOKE);
        speedText.setLayoutX(220.0 + 25);
        speedText.setLayoutY(-50.0 + 45);
        this.getChildren().add(fastForwardButton);
        this.getChildren().add(speedText);

        Button singleStepButton = new Button();
        singleStepButton.setGraphic(new ImageView(ResManager.getImage("img_singleStep.png")));
        singleStepButton.setLayoutX(290.0);
        // exitButton.setLayoutY(20.0);
        singleStepButton.setLayoutY(-50.0);
        setButtonEffect(singleStepButton);
        this.getChildren().add(singleStepButton);

        final Timeline down = new Timeline();
        down.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO, new KeyValue(menuBackground.layoutYProperty(), -335.0), new KeyValue(
                        playButton.layoutYProperty(), -50.0), new KeyValue(fastForwardButton.layoutYProperty(), -50.0),
                        new KeyValue(singleStepButton.layoutYProperty(), -50.0), new KeyValue(
                        speedText.layoutYProperty(), -50.0 + 45)),
                new KeyFrame(new Duration(100), new KeyValue(menuBackground.layoutYProperty(), -265.0), new KeyValue(
                        playButton.layoutYProperty(), 15.0), new KeyValue(fastForwardButton.layoutYProperty(), 15.0),
                        new KeyValue(singleStepButton.layoutYProperty(), 15.0), new KeyValue(speedText
                        .layoutYProperty(), 15.0 + 45)));

        final Timeline up = new Timeline();
        up.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO, new KeyValue(menuBackground.layoutYProperty(), -265.0), new KeyValue(
                        playButton.layoutYProperty(), 15.0), new KeyValue(fastForwardButton.layoutYProperty(), 15.0),
                        new KeyValue(singleStepButton.layoutYProperty(), 15.0), new KeyValue(
                        speedText.layoutYProperty(), 15.0 + 45)),
                new KeyFrame(new Duration(100), new KeyValue(menuBackground.layoutYProperty(), -335.0), new KeyValue(
                        playButton.layoutYProperty(), -50.0), new KeyValue(fastForwardButton.layoutYProperty(), -50.0),
                        new KeyValue(singleStepButton.layoutYProperty(), -50.0), new KeyValue(speedText
                        .layoutYProperty(), -50.0 + 45)));

        this.setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                rc.toFront();
                down.play();
            }
        });

        this.setOnMouseExited(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                up.play();
            }
        });

        playButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                if (!stopped && play.getStatus().equals(Animation.Status.PAUSED)) {
                    play.play();
                    playButton.setGraphic(new ImageView(ResManager.getImage("img_pause.png")));
                } else if (play.getStatus().equals(Animation.Status.RUNNING)) {
                    play.pause();
                    playButton.setGraphic(new ImageView(ResManager.getImage("img_play.png")));
                }
            }
        });

        fastForwardButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                if (rate > 7.9)
                    rate = 1;
                else
                    rate *= 2;
                changeSpeed(rate * speed);
                speedText.setText("X" + rate);
            }
        });

        speedText.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                if (rate > 7.9)
                    rate = 1;
                else
                    rate *= 2;
                changeSpeed(rate * speed);
                speedText.setText("X" + rate);
            }
        });

        singleStepButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                if (play.getStatus().equals(Animation.Status.RUNNING)) {
                    play.pause();
                    playButton.setGraphic(new ImageView(ResManager.getImage("img_play.png")));
                }
                if (!stopped)
                    MainBackGround.nextTurn();
            }
        });
        if (GUIManager.enableDropShadowEffect)
            this.setEffect(new DropShadow());
        this.setLayoutX(MainBackGround.screenX - 500);
        play.play();
    }

    public static void setButtonEffect(Button btn) {
        btn.setStyle("-fx-base: #e9cf9e;");
        if (GUIManager.enableDropShadowEffect)
            btn.setEffect(new DropShadow());
        btn.setOpacity(0.6f);
    }

    public void pause() {
        play.pause();
    }

    public void play() {
        if (!play.getStatus().equals(Animation.Status.PAUSED))
            play.play();
    }

    public void changeSpeed(double newSpeed) {
        play.setRate(newSpeed);
    }

    public void stop() {
        play.stop();
        stopped = true;
    }
}
