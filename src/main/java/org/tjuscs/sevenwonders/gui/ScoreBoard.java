package org.tjuscs.sevenwonders.gui;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.tjuscs.sevenwonders.kernel.Board;

public class ScoreBoard extends DropGroup {
    private ScoreBoard sb;

    public ScoreBoard(Board[] boards) {
        sb = this;
        ImageView frameTop = new ImageView(ResManager.getImage("frametop.png"));
        getChildren().add(frameTop);
        int part = 30 * (boards.length + 1) / 74;
        for (int i = 0; i < part; i++) {
            ImageView frameBottom = new ImageView(ResManager.getImage("framebottom.jpg"));
            frameBottom.setLayoutY(i * 74 + 73);
            getChildren().add(frameBottom);
        }
        ImageView frameBottom = new ImageView(ResManager.getImage("framebottom.jpg"));
        frameBottom.setLayoutY((boards.length) * 30 + 73 - (74 - 30));
        getChildren().add(frameBottom);
        ImageView head = new ImageView(ResManager.getImage("scorehead.jpg"));
        head.setLayoutX(16);
        head.setLayoutY(73);
        getChildren().add(head);
        ImageView ok = new ImageView(ResManager.getImage("ok.png"));
        ok.setLayoutX(270);
        ok.setLayoutY(20);
        getChildren().add(ok);
        ok.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                EventHandler<ActionEvent> act = new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        sb.setVisible(false);
                    }
                };
                Timeline tl = new Timeline(new KeyFrame(Duration.seconds(0.5), act, new KeyValue(sb.opacityProperty(),
                        0), new KeyValue(sb.layoutYProperty(), -300)));
                tl.play();
            }
        });

        int[] x = {15, 115, 145, 175, 205, 235, 265, 295, 325};
        for (int i = 0; i < x.length; i++)
            x[i] += 10;// 5 offset for text
        int[] y = new int[boards.length];
        for (int i = 0; i < boards.length; i++) {
            y[i] = (i + 1) * 30 + 73 + 20;// 30 height per line; 73 head height;
            // 20 text baseline offset
        }
        for (int i = 0; i < boards.length; i++) {
            // Name
            Text name = new Text(boards[i].getName() + (boards[i].isBSide() ? "(B)" : "(A)"));
            name.setFill(Color.GOLD);
            name.setLayoutX(x[0] - 6);
            name.setLayoutY(y[i]);
            getChildren().add(name);

            // MilitaryVPS
            int[][] mVPSInfo = boards[i].getMilitaryVPS();
            int totalMilVPs = 0;
            for (int side = 0; side < 2; side++)
                for (int ii = 1; ii < 4; ii++)
                    totalMilVPs += mVPSInfo[side][ii];
            Text mVPS = new Text(String.valueOf(totalMilVPs));
            mVPS.setFill(Color.GOLD);
            mVPS.setLayoutX(x[1]);
            mVPS.setLayoutY(y[i]);
            getChildren().add(mVPS);

            // CoinVPS
            Text coin = new Text(String.valueOf(boards[i].cscoreVPs()));
            coin.setFill(Color.GOLD);
            coin.setLayoutX(x[2]);
            coin.setLayoutY(y[i]);
            getChildren().add(coin);

            // StageVPS
            Text stageVPS = new Text(String.valueOf(boards[i].getStageCompleteVPS()));
            stageVPS.setFill(Color.GOLD);
            stageVPS.setLayoutX(x[3]);
            stageVPS.setLayoutY(y[i]);
            getChildren().add(stageVPS);

            // CivilVPS
            Text civilVPS = new Text(String.valueOf(boards[i].getCivilVPS()));
            civilVPS.setFill(Color.GOLD);
            civilVPS.setLayoutX(x[4]);
            civilVPS.setLayoutY(y[i]);
            getChildren().add(civilVPS);

            // CommerceVPS
            Text comVPS = new Text(String.valueOf(boards[i].getCommerceVPS()));
            comVPS.setFill(Color.GOLD);
            comVPS.setLayoutX(x[5]);
            comVPS.setLayoutY(y[i]);
            getChildren().add(comVPS);

            // CommerceVPS
            Text guildVPS = new Text(String.valueOf(boards[i].getGuildVPS()));
            guildVPS.setFill(Color.GOLD);
            guildVPS.setLayoutX(x[6]);
            guildVPS.setLayoutY(y[i]);
            getChildren().add(guildVPS);

            // ScienceVPS
            Text sciVPS = new Text(String.valueOf(boards[i].scoreVPs()));
            sciVPS.setFill(Color.GOLD);
            sciVPS.setLayoutX(x[7]);
            sciVPS.setLayoutY(y[i]);
            getChildren().add(sciVPS);

            // TotalVPS
            Text total = new Text(String.valueOf(boards[i].getTotalVPS()));
            total.setFill(Color.GOLD);
            total.setLayoutX(x[8]);
            total.setLayoutY(y[i]);
            getChildren().add(total);
        }

    }
}
