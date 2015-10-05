package org.tjuscs.sevenwonders.gui;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;
import org.tjuscs.sevenwonders.kernel.CardColor;

import java.util.ArrayList;

public class Wonders extends Group {
    Wonders(Image image, double screenX2, double screenY2, boolean abled, int Num, TPABoard t, Group r) {
        tpa = t;
        root = r;
        for (int i = 0; i < 15; i++) {
            hasRes[i] = 0;
        }
        pic = image;
        NUM = Num;
        screenX = screenX2;
        screenY = screenY2;
        numOfLeftBattle = 0;
        numOfRightBattle = 0;
        hasbuild = 0;
        gold = 0;
        army = 0;
        bigX = image.getWidth();
        bigY = image.getHeight();
        imageView = new ImageView();
        imageView.setImage(image);
        getChildren().add(imageView);
        setCache(true);
        coinView = new Group();
        getChildren().add(coinView);
        if (abled)
            imageView.setEffect(new Reflection());
        resSign = new Group();
        resSign.setLayoutY(60);
        resSign.setLayoutX(-9);
        getChildren().add(resSign);
    }

    /**
     * @param age    if win, pass in age, otherwise just give 0
     * @param isLeft
     */
    public void addBattleSign(int age, boolean isLeft) {
        if (age == 0)
            army--;
        else if (age == 1)
            army++;
        else if (age == 2)
            army = army + 3;
        else if (age == 3)
            army = army + 5;
        if (age > 1) {
            Image imm = ResManager.getImage("fight1.png");
            ImageView battle1 = new ImageView();
            battle1.setImage(imm);
            // if (GUIManager.enableReflectionEffect)
            // battle1.setEffect(new Reflection());
            if (GUIManager.enableDropShadowEffect)
                battle1.setEffect(new DropShadow());
            battle1.setScaleX(0.7);
            battle1.setScaleY(0.7);
            battle1.setLayoutY(120);
            if (isLeft)
                battle1.setLayoutX(numOfLeftBattle * 40);
            else
                battle1.setLayoutX(bigX - 55 - numOfRightBattle * 40);
            getChildren().add(battle1);
        }
        Image im = ResManager.getImage("fight" + age + ".png"); // 0 1 2 3
        battle = new ImageView();
        battle.setImage(im);
        // if (GUIManager.enableReflectionEffect)
        // battle.setEffect(new Reflection());
        if (GUIManager.enableDropShadowEffect)
            battle.setEffect(new DropShadow());
        battle.setScaleX(0.7);
        battle.setScaleY(0.7);
        battle.setLayoutY(120);
        if (isLeft) {
            battle.setLayoutX(numOfLeftBattle * 40);
            numOfLeftBattle++;
        } else {
            battle.setLayoutX(bigX - 55 - numOfRightBattle * 40);
            numOfRightBattle++;
        }
        getChildren().add(battle);

    }

    public void addGoldSign(int i) {
        gold = gold + i;
        // if(iv != null)
        // for(ImageView iv1 : iv){
        // iv1.setVisible(false);
        // }
        // JOptionPane.showMessageDialog(null, gold);
        coinView.getChildren().clear();
        ImageView[] iv = new ImageView[gold / 3 + gold % 3];
        int k = 0;
        for (int j = 0; j < gold / 3; j++) {
            Image image = ResManager.getImage("geld3.png");
            iv[k] = new ImageView();
            iv[k].setImage(image);
            // if (GUIManager.enableReflectionEffect)
            // iv[k].setEffect(new Reflection());
            if (GUIManager.enableDropShadowEffect)
                iv[k].setEffect(new DropShadow());
            iv[k].setScaleX(0.7);
            iv[k].setScaleY(0.7);
            iv[k].setLayoutY(60);
            iv[k].setLayoutX(k * 40);
            k++;
        }
        for (int j = 0; j < gold % 3; j++) {
            Image image = ResManager.getImage("geld1.png");
            iv[k] = new ImageView();
            iv[k].setImage(image);
            // if (GUIManager.enableReflectionEffect)
            // iv[k].setEffect(new Reflection());
            if (GUIManager.enableDropShadowEffect)
                iv[k].setEffect(new DropShadow());
            iv[k].setScaleX(0.7);
            iv[k].setScaleY(0.7);
            iv[k].setLayoutY(60);
            iv[k].setLayoutX(k * 40);
            k++;
        }
        for (int j = 0; j < k; j++) {
            coinView.getChildren().add(iv[j]);
        }
        this.toBack();
    }

    public void stageCompleted(int i) {
        hasbuild++;
        Image III = ResManager.getImage("build" + i + ".png");
        ImageView sta = new ImageView();
        sta.setImage(III);
        sta.setScaleX(0.75);
        sta.setScaleY(0.75);
        if (NUM == 7) {
            if (hasbuild == 1) {
                sta.setLayoutX(162);
                sta.setLayoutY(165);
            } else if (hasbuild == 2) {
                sta.setLayoutX(320);
                sta.setLayoutY(165);
            }
        } else if (NUM == 13) {
            if (hasbuild == 1) {
                sta.setLayoutX(-8);
                sta.setLayoutY(165);
            } else if (hasbuild == 2) {
                sta.setLayoutX(108);
                sta.setLayoutY(165);
            } else if (hasbuild == 3) {
                sta.setLayoutX(245);
                sta.setLayoutY(165);
            } else if (hasbuild == 4) {
                sta.setLayoutX(377);
                sta.setLayoutY(165);
            }
        } else {
            if (hasbuild == 1) {
                sta.setLayoutX(8);
                sta.setLayoutY(165);
            } else if (hasbuild == 2) {
                sta.setLayoutX(163);
                sta.setLayoutY(165);
            } else if (hasbuild == 3) {
                sta.setLayoutX(318);
                sta.setLayoutY(165);
            }
        }
        sta.setOpacity(0);
        if (GUIManager.enableDropShadowEffect)
            sta.setEffect(new DropShadow());
        getChildren().add(sta);
        double sx = this.getScaleX();
        double sy = this.getScaleY();
        Timeline tl = new Timeline(
                // new KeyFrame(Duration.seconds(0.3), new
                // KeyValue(this.translateYProperty(), 30)),
                // new KeyFrame(Duration.seconds(0.6), new
                // KeyValue(this.translateYProperty(), -20)),
                // new KeyFrame(Duration.seconds(0.9), new
                // KeyValue(this.translateYProperty(), 0), new
                // KeyValue(sta.opacityProperty(), 1)));
                new KeyFrame(Duration.seconds(0.2), new KeyValue(this.scaleXProperty(), 0.75 * sx), new KeyValue(
                        this.scaleYProperty(), 0.75 * sy)), new KeyFrame(Duration.seconds(0.4), new KeyValue(
                this.scaleXProperty(), 1.10 * sx), new KeyValue(this.scaleYProperty(), 1.10 * sy),
                new KeyValue(sta.opacityProperty(), 1)), new KeyFrame(Duration.seconds(0.6), new KeyValue(
                this.scaleXProperty(), 0.90 * sx), new KeyValue(this.scaleYProperty(), 0.90 * sy)),
                new KeyFrame(Duration.seconds(0.8), new KeyValue(this.scaleXProperty(), 1.00 * sx), new KeyValue(this
                        .scaleYProperty(), 1.00 * sy)));
        tl.play();
    }

    /**
     * @param kind <br>
     *             1 - left raw<br>
     *             2 - right raw<br>
     *             3 - mal<br>
     */
    public void addTradeCard(int kind) {
        // Image image = ResManager.getImage("kontor.png");
        // trade1 = new ImageView();
        // trade3 = new ImageView();
        // trade1.setImage(image);
        // trade3.setImage(image);
        //
        // trade2 = new ImageView();
        // trade2.setImage(image);
        // trade4 = new ImageView();
        // sign = new ImageView();
        // trade4.setImage(image);
        ImageView sign = new ImageView();
        if (GUIManager.enableDropShadowEffect)
            sign.setEffect(new DropShadow());
        if (kind == 1) {
            sign.setImage(ResManager.getImage("kontor_left.png"));
            sign.setScaleX(0.7);
            sign.setScaleY(0.7);
            sign.setLayoutY(15);
            sign.setLayoutX(310 - 30);
            getChildren().add(sign);
        } else if (kind == 2) {
            sign.setImage(ResManager.getImage("kontor_right.png"));
            // if (GUIManager.enableReflectionEffect)
            // sign.setEffect(new Reflection());
            sign.setScaleX(0.7);
            sign.setScaleY(0.7);
            sign.setLayoutY(55);
            sign.setLayoutX(325 - 30);
            getChildren().add(sign);
        } else if (kind == 3) {
            sign.setImage(ResManager.getImage("markt.png"));
            // if (GUIManager.enableReflectionEffect)
            // sign.setEffect(new Reflection());
            sign.setScaleX(0.7);
            sign.setScaleY(0.7);
            sign.setLayoutY(95);
            sign.setLayoutX(318 - 30);
            getChildren().add(sign);
        }
    }

    /**
     * @param kind ResourceID (defined in the SimpleResList)
     * @see org.tjuscs.sevenwonders.kernel.SimpleResList
     */
    public void addResource(int kind) {
        hasRes[kind - 1]++;
        // System.out.println("\n\n\n\n\nIIIIIIIIIIIIIII" + kind);
        int t = 0;
        int tt = 0;
        resSign.getChildren().clear();
        for (int i = 0; i < 15; i++) {
            if (hasRes[i] == 1) {
                resource = new ImageView(ResManager.getImage("rs" + (i + 1) + ".png"));
                if (i < 7)
                    resource.setLayoutX(-42);
                else
                    resource.setLayoutX(-104);
                resource.setLayoutY(-68 + t * 32);
                resource.setScaleX(0.65);
                resource.setScaleY(0.65);
                resSign.getChildren().add(resource);
                t++;
            } else if (hasRes[i] > 1) {
                numOfRes[tt] = new Label();
                numOfRes[tt].setText("x" + hasRes[i]);
                numOfRes[tt].setFont(Font.font("Arial", 20));
                numOfRes[tt].setTextFill(Color.WHITE);
                numOfRes[tt].setEffect(new DropShadow());
                resource = new ImageView(ResManager.getImage("rs" + (i + 1) + ".png"));
                resource.setLayoutX(-42);
                resource.setLayoutY(-68 + t * 32);
                resource.setScaleX(0.65);
                resource.setScaleY(0.65);
                resSign.getChildren().add(resource);
                numOfRes[tt].setLayoutX(-15);
                numOfRes[tt].setLayoutY(-44 + t * 32);
                resSign.getChildren().add(numOfRes[tt]);
                t++;
                tt++;
            }
        }
        for (int i = 0; i < tt; i++) {
            numOfRes[i].toFront();
        }
        // Manager.debug("Added Res:" + kind);
    }

    /**
     * @param cardID
     * @param cardColor
     */
    public void addCard(final int cardID, final CardColor cardColor) {
        list.add(cardID);
        color.add(cardColor);
        // tpa.clear();
        // for (int i = list.size() - 1; i < list.size(); i++) {
        // int i = list.size() - 1;

        cardImage = new ImageView(ResManager.getImage(cardID + ".jpg"));
        Rectangle rec = new Rectangle(208, 320);
        rec.setArcHeight(30);
        rec.setArcWidth(30);
        cardImage.setClip(rec);
        final Wonders self = this;
        getChildren().add(cardImage);
        // cardImage.toBack();
        cardImage.setScaleX(0.2);
        cardImage.setScaleY(0.2);
        EventHandler<ActionEvent> act = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                cir = new CardPoint(cardID, cardColor, self);
                if (cardColor == CardColor.YELLOW) {
                    cir.setLayoutX(bigX - 10 - i1 * 10);
                    cir.setLayoutY(45);
                    getChildren().add(cir);
                    i1++;
                    switch (cardID) {
                        case 53:
                            addTradeCard(2);
                            break;
                        case 54:
                            addTradeCard(1);
                            break;
                        case 55:
                            addTradeCard(3);
                            break;
                    }
                } else if (cardColor == CardColor.BLUE) {
                    cir.setLayoutX(200 + i2 * 10);
                    cir.setLayoutY(74);

                    // JOptionPane.showMessageDialog(null, cir);
                    tpa.addCivil(cardID);
                    tpa.getChildren().add(cir);
                    i2++;
                } else if (cardColor == CardColor.BROWN) {
                    cir.setLayoutX(10 + i3 * 10);
                    cir.setLayoutY(35);
                    getChildren().add(cir);
                    i3++;
                } else if (cardColor == CardColor.GREEN) {
                    if (cardID == 65 || cardID == 68 || cardID == 72 || cardID == 75) {
                        cir.setLayoutX(25 + 10 * i4);
                        cir.setLayoutY(74);
                        i4++;
                    } else if (cardID == 66 || cardID == 69 || cardID == 70 || cardID == 73) {
                        cir.setLayoutX(75 + 10 * i8);
                        cir.setLayoutY(74);
                        i8++;
                    } else {
                        cir.setLayoutX(125 + 10 * i9);
                        cir.setLayoutY(74);
                        i9++;
                    }
                    tpa.getChildren().add(cir);
                } else if (cardColor == CardColor.GREY) {
                    cir.setLayoutX(10 + i5 * 10);
                    cir.setLayoutY(50);
                    getChildren().add(cir);
                    i5++;
                } else if (cardColor == CardColor.PURPLE) {
                    cir.setLayoutX(bigX - 10 - i6 * 10);
                    cir.setLayoutY(70);
                    getChildren().add(cir);
                    i6++;
                } else if (cardColor == CardColor.RED) {
                    cir.setLayoutX(292 + i7 * 10);
                    cir.setLayoutY(74);
                    tpa.addArmy(cardID);
                    tpa.getChildren().add(cir);
                    i7++;
                }
                // cardImage.toFront();
            }
        };
        Timeline tl = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(cardImage.layoutXProperty(), 250),
                new KeyValue(cardImage.layoutYProperty(), -80), new KeyValue(cardImage.opacityProperty(), 0),
                new KeyValue(cardImage.scaleXProperty(), 0.2), new KeyValue(cardImage.scaleYProperty(), 0.2)),
                new KeyFrame(Duration.seconds(0.5), new KeyValue(cardImage.layoutXProperty(), 150), new KeyValue(
                        cardImage.layoutYProperty(), -180), new KeyValue(cardImage.opacityProperty(), 1), new KeyValue(
                        cardImage.scaleXProperty(), 0.45), new KeyValue(cardImage.scaleYProperty(), 0.45)),
                new KeyFrame(Duration.seconds(0.5), act), new KeyFrame(Duration.seconds(1.0), new KeyValue(
                cardImage.layoutXProperty(), 50), new KeyValue(cardImage.layoutYProperty(), -80), new KeyValue(
                cardImage.opacityProperty(), 1), new KeyValue(cardImage.scaleXProperty(), 0.6), new KeyValue(
                cardImage.scaleYProperty(), 0.6)), new KeyFrame(Duration.seconds(3.0), new KeyValue(
                cardImage.opacityProperty(), 1)), new KeyFrame(Duration.seconds(3.3), new KeyValue(
                cardImage.opacityProperty(), 0)));
        tl.play();
        // }
    }

    public void showInfo() {
        int i1 = 0, i2 = 0, i3 = 0, i4 = 0, i5 = 0, i6 = 0, i7 = 0;
        imv = new ImageView[list.size()];
        for (int i = 0; i < list.size(); i++) {
            imv[i] = new ImageView(ResManager.getImage(list.get(i) + ".jpg"));
            imv[i].setScaleX(0.6);
            imv[i].setScaleY(0.6);
            imv[i].setOpacity(0);
            root.getChildren().add(imv[i]);
            double targetX = 0, targetY = 0;
            if (color.get(i) == CardColor.YELLOW) {
                targetX = (screenX * 0.8 + 25 * i1);
                targetY = (45 * i1);
                i1++;
            } else if (color.get(i) == CardColor.BLUE) {
                targetX = (screenX * 0.4 + 25 * i2);
                targetY = (45 * i2);
                i2++;
            } else if (color.get(i) == CardColor.BROWN) {
                targetX = (25 * i3);
                targetY = (45 * i3);
                i3++;
            } else if (color.get(i) == CardColor.GREEN) {
                targetX = (screenX * 0.2 + 25 * i4);
                targetY = (45 * i4);
                i4++;
            } else if (color.get(i) == CardColor.GREY) {
                targetX = (25 * i5);
                targetY = (screenY / 2 - 45 + 45 * i5);
                imv[i].toFront();
                i5++;
            } else if (color.get(i) == CardColor.PURPLE) {
                targetX = (screenX - 160 - 25 * i6);
                targetY = (screenY / 2 - 45 + 45 * i6);
                imv[i].toFront();
                i6++;
            } else if (color.get(i) == CardColor.RED) {
                targetX = (screenX * 0.6 + 25 * i7);
                targetY = (45 * i7);
                i7++;
            }
            Timeline tl = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(imv[i].translateXProperty(),
                    this.getLayoutX()), new KeyValue(imv[i].translateYProperty(), this.getLayoutY())), new KeyFrame(
                    Duration.seconds(0.3), new KeyValue(imv[i].translateXProperty(), targetX), new KeyValue(
                    imv[i].translateYProperty(), targetY), new KeyValue(imv[i].opacityProperty(), 1)));
            tl.play();
        }
    }

    public void hideInfo() {
        EventHandler<ActionEvent> act = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                root.getChildren().removeAll(imv);
            }
        };

        for (int i = 0; i < list.size(); i++) {
            Timeline tl = new Timeline(new KeyFrame(Duration.seconds(0.3), new KeyValue(imv[i].translateXProperty(),
                    this.getLayoutX()), new KeyValue(imv[i].translateYProperty(), this.getLayoutY()), new KeyValue(
                    imv[i].opacityProperty(), 0)), new KeyFrame(Duration.seconds(0.3), act));
            tl.play();
        }
    }

    public int getHasbuild() {
        return hasbuild;
    }

    public void discardCard(int cardID) {
        cardImage = new ImageView(ResManager.getImage(cardID + ".jpg"));
        Rectangle rec = new Rectangle(208, 320);
        rec.setArcHeight(30);
        rec.setArcWidth(30);
        cardImage.setClip(rec);
        cardImage.toFront();
        final Wonders self = this;
        getChildren().add(cardImage);
        // cardImage.toBack();
        cardImage.setScaleX(0.2);
        cardImage.setScaleY(0.2);

        RotateTransition rt = new RotateTransition(Duration.seconds(1.0), cardImage);
        rt.setFromAngle(0);
        rt.setToAngle(390);
        // rt.setCycleCount(RotateTransition.INDEFINITE);
        rt.play();
        Timeline tl = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(cardImage.layoutXProperty(), 250),
                new KeyValue(cardImage.layoutYProperty(), -80), new KeyValue(cardImage.opacityProperty(), 0),
                new KeyValue(cardImage.scaleXProperty(), 0.2), new KeyValue(cardImage.scaleYProperty(), 0.2)),
                new KeyFrame(Duration.seconds(0.5), new KeyValue(cardImage.layoutXProperty(), 250), new KeyValue(
                        cardImage.layoutYProperty(), -180), new KeyValue(cardImage.opacityProperty(), 1), new KeyValue(
                        cardImage.scaleXProperty(), 0.45), new KeyValue(cardImage.scaleYProperty(), 0.45)),
                // new KeyFrame(Duration.seconds(0.5),act),
                new KeyFrame(Duration.seconds(1.0), new KeyValue(cardImage.layoutXProperty(), 250), new KeyValue(
                        cardImage.layoutYProperty(), -80), new KeyValue(cardImage.opacityProperty(), 1), new KeyValue(
                        cardImage.scaleXProperty(), 0.6), new KeyValue(cardImage.scaleYProperty(), 0.6)), new KeyFrame(
                Duration.seconds(3.0), new KeyValue(cardImage.opacityProperty(), 1), new KeyValue(
                cardImage.layoutXProperty(), 290), new KeyValue(cardImage.layoutYProperty(), 0),
                new KeyValue(rt.rateProperty(), 0.2)), new KeyFrame(Duration.seconds(3.3), new KeyValue(
                cardImage.opacityProperty(), 0), new KeyValue(cardImage.layoutXProperty(), 300), new KeyValue(
                cardImage.layoutYProperty(), 80), new KeyValue(cardImage.visibleProperty(), false)));
        tl.play();
    }

    public int NUM;
    public static Image pic;
    private int hasbuild;
    // private int kontorKind; // 0 1 2 3
    // private boolean hasMarkt;
    // private static ImageView trade1;
    // private static ImageView trade2;
    // private static ImageView trade3;
    // private static ImageView trade4;
    // private ImageView[] iv;
    private Group coinView;
    private static ImageView imageView;
    private static ImageView battle;
    private ImageView cardImage;
    private int gold;
    private int army;
    private int numOfLeftBattle;
    private int numOfRightBattle;
    private double bigX;
    private double bigY;
    private static double screenX;
    private static double screenY;
    private static ImageView resource;
    private int[] hasRes = new int[15];
    private Label[] numOfRes = new Label[4];
    private ArrayList<Integer> list = new ArrayList<Integer>();
    private ArrayList<CardColor> color = new ArrayList<CardColor>();
    private TPABoard tpa;
    private CardPoint cir;
    private static Group root;
    private ImageView[] imv;
    private Group resSign;
    private int i1 = 0, i2 = 0, i3 = 0, i4 = 0, i5 = 0, i6 = 0, i7 = 0, i8 = 0, i9 = 0;
}
