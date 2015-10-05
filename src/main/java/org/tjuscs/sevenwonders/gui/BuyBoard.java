package org.tjuscs.sevenwonders.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.tjuscs.sevenwonders.Manager;
import org.tjuscs.sevenwonders.kernel.Board;
import org.tjuscs.sevenwonders.kernel.Card;
import org.tjuscs.sevenwonders.kernel.SimpleResList;

import java.util.ArrayList;

public class BuyBoard extends DropGroup {

    private static SimpleResList buyFromSelf;
    private static SimpleResList buyFromLeft;
    private static SimpleResList buyFromRight;
    private static SimpleResList needList;
    private static boolean[][] jugClicked;
    private static int[] needListByInt;

    private static Image frame1;
    private static Image frame2;

    private static Image pic1;
    private static Image pic2;
    private static Image pic3;

    private static Image[] res;

    private static Image down;
    private static Image left;
    private static Image right;

    private static Group goldBoard;
    private static ImageView[] iv;
    private static Image ok;
    private static Image notok;
    private static ImageView[] rs;
    private static ImageView[] dir1;
    private static ImageView[] dir2;
    private static ImageView[] dir3;
    private static ImageView[] frame;
    private static ImageView[] back;
    private static ImageView isFinish;
    private static Label[] label;
    private int numOfRsNeeded;
    private int gold;
    private int[] from;
    private boolean over;
    private EventHandler<ActionEvent> buildAction;

    public BuyBoard() {
        res = new Image[8];
        frame1 = ResManager.getImage("frametop.png");
        frame2 = ResManager.getImage("framebottom.jpg");

        pic1 = ResManager.getImage("dist.jpg");
        pic2 = ResManager.getImage("dist2.jpg");
        pic3 = ResManager.getImage("dist3.jpg");

        res[1] = ResManager.getImage("rs1.png");
        res[2] = ResManager.getImage("rs2.png");
        res[3] = ResManager.getImage("rs3.png");
        res[4] = ResManager.getImage("rs4.png");
        res[5] = ResManager.getImage("rs5.png");
        res[6] = ResManager.getImage("rs6.png");
        res[7] = ResManager.getImage("rs7.png");

        down = ResManager.getImage("down.png");
        left = ResManager.getImage("left.png");
        right = ResManager.getImage("right.png");

        ok = ResManager.getImage("ok.png");
        notok = ResManager.getImage("notok.png");

        iv = new ImageView[20];
    }

    private void unload() {
        getChildren().removeAll(dir1);
        getChildren().removeAll(dir2);
        getChildren().removeAll(dir3);
        getChildren().removeAll(frame);
        getChildren().removeAll(label);
        getChildren().removeAll(back);
        getChildren().remove(goldBoard);
        getChildren().removeAll(rs);
        getChildren().removeAll(isFinish);
        setVisible(false);
    }

    public void load(Card cd, final Board board) {
        try {
            needList = SimpleResList.buildCostList(cd);
            gold = board.getTotalCoins();
            numOfRsNeeded = needList.getTotalRes();
            needListByInt = new int[numOfRsNeeded];
            jugClicked = new boolean[3][numOfRsNeeded];
            for (int i = 0; i < 2; i++) {
                System.out.println("26");
                for (int j = 0; j < numOfRsNeeded; j++) {
                    System.out.println("27");
                    jugClicked[i][j] = false;
                }
            }
            buyFromSelf = new SimpleResList();
            buyFromLeft = new SimpleResList();
            buyFromRight = new SimpleResList();
            int targ = 0;
            for (int i = 1; i <= 7; i++) {
                System.out.println("28");
                for (int j = 0; j < needList.numAt(i); j++) {
                    System.out.println("29");
                    needListByInt[targ++] = i;
                }
            }

            from = new int[numOfRsNeeded];
            label = new Label[numOfRsNeeded];
            rs = new ImageView[numOfRsNeeded];
            dir1 = new ImageView[numOfRsNeeded];
            dir2 = new ImageView[numOfRsNeeded];
            dir3 = new ImageView[numOfRsNeeded];
            frame = new ImageView[numOfRsNeeded + 2];
            back = new ImageView[numOfRsNeeded];
            isFinish = new ImageView(notok);
            isFinish.setLayoutX(265);
            isFinish.setLayoutY(33);
            for (int i = 0; i < numOfRsNeeded; i++) {
                System.out.println("30");
                from[i] = 0;
            }
            for (int i = 0; i < numOfRsNeeded + 2; i++) {
                System.out.println("31");
                if (i == 0) {
                    frame[i] = new ImageView();
                    frame[i].setImage(frame1);
                    getChildren().add(frame[i]);
                } else if (i == 1) {
                    frame[i] = new ImageView();
                    frame[i].setImage(frame2);
                    frame[i].setLayoutY(69);
                    getChildren().add(frame[i]);
                } else {
                    label[i - 2] = new Label();
                    label[i - 2].setText(SimpleResList.resourceAt(needListByInt[i - 2]).toString());
                    frame[i] = new ImageView(frame2);

                    dir1[i - 2] = new ImageView(left);
                    buyFromLeft.srl[0]++;
                    buyFromLeft.srl[needListByInt[i - 2]]++;
                    if (this.canAfford(buyFromLeft, board.getLeftNeighbor().getResourceList(), board.getLeftNeighbor()
                            .getSellOrList())) {
                        dir1[i - 2].setVisible(true);
                    } else {
                        dir1[i - 2].setVisible(false);
                    }
                    buyFromLeft.srl[0]--;
                    buyFromLeft.srl[needListByInt[i - 2]]--;

                    dir2[i - 2] = new ImageView(down);
                    buyFromSelf.srl[0]++;
                    buyFromSelf.srl[needListByInt[i - 2]]++;
                    if (this.canAfford(buyFromSelf, board.getResourceList(), board.getOrList())) {
                        dir2[i - 2].setVisible(true);
                    } else {
                        dir2[i - 2].setVisible(false);
                    }
                    buyFromSelf.srl[0]--;
                    buyFromSelf.srl[needListByInt[i - 2]]--;

                    dir3[i - 2] = new ImageView(right);
                    buyFromRight.srl[0]++;
                    buyFromRight.srl[needListByInt[i - 2]]++;
                    if (this.canAfford(buyFromRight, board.getRightNeighbor().getResourceList(), board
                            .getRightNeighbor().getSellOrList())) {
                        dir3[i - 2].setVisible(true);
                    } else {
                        dir3[i - 2].setVisible(false);
                    }
                    buyFromRight.srl[0]--;
                    buyFromRight.srl[needListByInt[i - 2]]--;

                    back[i - 2] = new ImageView();
                    if (needListByInt[i - 2] < 5) // judge the kind of resource
                        back[i - 2].setImage(pic1);
                    else
                        back[i - 2].setImage(pic2);

                    // get type of resource
                    rs[i - 2] = new ImageView(res[needListByInt[i - 2]]);

                    frame[i].setLayoutY(69 * i);
                    label[i - 2].setLayoutX(180);
                    label[i - 2].setLayoutY(69 * i + 10);
                    label[i - 2].setFont(Font.font("Arial", 40));
                    label[i - 2].setTextFill(Color.LIGHTBLUE);
                    back[i - 2].setLayoutX(16);
                    back[i - 2].setLayoutY(69 * i);
                    dir1[i - 2].setLayoutX(20);
                    dir1[i - 2].setLayoutY(69 * i + 9);
                    dir1[i - 2].setScaleX(0.9);
                    dir1[i - 2].setScaleY(0.9);

                    dir2[i - 2].setLayoutX(69);
                    dir2[i - 2].setLayoutY(69 * i + 40);
                    dir2[i - 2].setScaleX(0.9);
                    dir2[i - 2].setScaleY(0.9);

                    dir3[i - 2].setLayoutX(117);
                    dir3[i - 2].setLayoutY(69 * i + 9);
                    dir3[i - 2].setScaleX(0.9);
                    dir3[i - 2].setScaleY(0.9);
                    final int i1 = i;
                    dir1[i - 2].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                        public void handle(MouseEvent e) {
                            System.out.println("Test.." + 1);
                            if (jugClicked[0][i1 - 2]) {
                                jugClicked[0][i1 - 2] = false;
                                buyFromLeft.srl[0]--;
                                buyFromLeft.srl[needListByInt[i1 - 2]]--;
                                for (int i = 0; i < numOfRsNeeded; i++) {
                                    System.out.println("1");
                                    if (!jugClicked[0][i]) {
                                        buyFromLeft.srl[0]++;
                                        buyFromLeft.srl[needListByInt[i]]++;
                                        if (canAfford(buyFromLeft, board.getLeftNeighbor().getResourceList(), board
                                                .getLeftNeighbor().getSellOrList())) {
                                            dir1[i].setVisible(true);
                                        } else {
                                            dir1[i].setVisible(false);
                                        }
                                        buyFromLeft.srl[0]--;
                                        buyFromLeft.srl[needListByInt[i]]--;
                                    }
                                }

                                dir1[i1 - 2].setOpacity(1);
                                dir2[i1 - 2].setOpacity(1);
                                dir3[i1 - 2].setOpacity(1);
                                gold = gold + 1;
                                getChildren().remove(goldBoard);
                                showGold();
                                from[i1 - 2] = 0;
                                over = true;
                                for (int j = 0; j < numOfRsNeeded; j++) {
                                    System.out.println("2");
                                    if (from[j] == 0) {
                                        over = false;
                                        break;
                                    }
                                }
                                if (over)
                                    isFinish.setImage(ok);
                                else {
                                    isFinish.setImage(notok);
                                }
                                return;
                            }
                            System.out.println("Test.." + 2);
                            if (jugClicked[1][i1 - 2]) {
                                buyFromSelf.srl[0]--;
                                buyFromSelf.srl[needListByInt[i1 - 2]]--;
                                jugClicked[1][i1 - 2] = false;
                                for (int i = 0; i < numOfRsNeeded; i++) {
                                    System.out.println("3");
                                    if (!jugClicked[1][i]) {
                                        buyFromSelf.srl[0]++;
                                        buyFromSelf.srl[needListByInt[i]]++;
                                        if (canAfford(buyFromSelf, board.getResourceList(), board.getOrList())) {
                                            dir2[i].setVisible(true);
                                        } else {
                                            dir2[i].setVisible(false);
                                        }
                                        buyFromSelf.srl[0]--;
                                        buyFromSelf.srl[needListByInt[i]]--;
                                    }
                                }
                            } else if (jugClicked[2][i1 - 2]) {
                                buyFromRight.srl[0]--;
                                buyFromRight.srl[needListByInt[i1 - 2]]--;
                                jugClicked[2][i1 - 2] = false;
                                for (int i = 0; i < numOfRsNeeded; i++) {
                                    System.out.println("4");
                                    if (!jugClicked[2][i]) {
                                        buyFromRight.srl[0]++;
                                        buyFromRight.srl[needListByInt[i]]++;
                                        if (canAfford(buyFromRight, board.getRightNeighbor().getResourceList(), board
                                                .getRightNeighbor().getSellOrList())) {
                                            dir3[i].setVisible(true);
                                        } else {
                                            dir3[i].setVisible(false);
                                        }
                                        buyFromRight.srl[0]--;
                                        buyFromRight.srl[needListByInt[i]]--;
                                    }
                                }
                            }
                            jugClicked[0][i1 - 2] = true;
                            buyFromLeft.srl[0]++;
                            buyFromLeft.srl[needListByInt[i1 - 2]]++;
                            for (int i = 0; i < numOfRsNeeded; i++) {
                                System.out.println("5");
                                if (!jugClicked[0][i]) {
                                    buyFromLeft.srl[0]++;
                                    buyFromLeft.srl[needListByInt[i]]++;
                                    if (canAfford(buyFromLeft, board.getLeftNeighbor().getResourceList(), board
                                            .getLeftNeighbor().getSellOrList())) {
                                        dir1[i].setVisible(true);
                                    } else {
                                        dir1[i].setVisible(false);
                                    }
                                    buyFromLeft.srl[0]--;
                                    buyFromLeft.srl[needListByInt[i]]--;
                                }
                            }

                            System.out.println("Test.." + 3);
                            dir1[i1 - 2].setOpacity(0.3);
                            dir2[i1 - 2].setOpacity(1);
                            dir3[i1 - 2].setOpacity(1);
                            gold = gold - 1;
                            getChildren().remove(goldBoard);
                            showGold();
                            System.out.println("Test.." + 4);
                            from[i1 - 2] = 1;
                            over = true;
                            for (int j = 0; j < numOfRsNeeded; j++) {
                                System.out.println("6");
                                if (from[j] == 0) {
                                    over = false;
                                    break;
                                }
                            }
                            System.out.println("Test.." + 5);
                            if (over) {
                                isFinish.setImage(ok);
                            }
                            System.out.println("Test.." + 6);
                            // JOptionPane.showMessageDialog(null,
                            // "Judge Over");
                        }
                    });
                    dir2[i - 2].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                        public void handle(MouseEvent e) {
                            if (jugClicked[1][i1 - 2]) {
                                jugClicked[1][i1 - 2] = false;
                                buyFromSelf.srl[0]--;
                                buyFromSelf.srl[needListByInt[i1 - 2]]--;
                                for (int i = 0; i < numOfRsNeeded; i++) {
                                    System.out.println("7");
                                    if (!jugClicked[1][i]) {
                                        buyFromSelf.srl[0]++;
                                        buyFromSelf.srl[needListByInt[i]]++;
                                        if (canAfford(buyFromSelf, board.getResourceList(), board.getOrList())) {
                                            dir2[i].setVisible(true);
                                        } else {
                                            dir2[i].setVisible(false);
                                        }
                                        buyFromSelf.srl[0]--;
                                        buyFromSelf.srl[needListByInt[i]]--;
                                    }
                                }

                                dir1[i1 - 2].setOpacity(1);
                                dir2[i1 - 2].setOpacity(1);
                                dir3[i1 - 2].setOpacity(1);
                                from[i1 - 2] = 0;
                                over = true;
                                for (int j = 0; j < numOfRsNeeded; j++) {
                                    System.out.println("8");
                                    if (from[j] == 0) {
                                        over = false;
                                        break;
                                    }
                                }
                                if (over)
                                    isFinish.setImage(ok);
                                else {
                                    isFinish.setImage(notok);
                                }
                                return;
                            }

                            if (jugClicked[0][i1 - 2]) {
                                buyFromLeft.srl[0]--;
                                buyFromLeft.srl[needListByInt[i1 - 2]]--;
                                jugClicked[0][i1 - 2] = false;
                                for (int i = 0; i < numOfRsNeeded; i++) {
                                    System.out.println("9");
                                    if (!jugClicked[0][i]) {
                                        buyFromLeft.srl[0]++;
                                        buyFromLeft.srl[needListByInt[i]]++;
                                        if (canAfford(buyFromLeft, board.getLeftNeighbor().getResourceList(), board
                                                .getLeftNeighbor().getSellOrList())) {
                                            dir1[i].setVisible(true);
                                        } else {
                                            dir1[i].setVisible(false);
                                        }
                                        buyFromLeft.srl[0]--;
                                        buyFromLeft.srl[needListByInt[i]]--;
                                    }
                                }
                            } else if (jugClicked[2][i1 - 2]) {
                                buyFromRight.srl[0]--;
                                buyFromRight.srl[needListByInt[i1 - 2]]--;
                                jugClicked[2][i1 - 2] = false;
                                for (int i = 0; i < numOfRsNeeded; i++) {
                                    System.out.println("10");
                                    if (!jugClicked[2][i]) {
                                        buyFromRight.srl[0]++;
                                        buyFromRight.srl[needListByInt[i]]++;
                                        if (canAfford(buyFromRight, board.getRightNeighbor().getResourceList(), board
                                                .getRightNeighbor().getSellOrList())) {
                                            dir3[i].setVisible(true);
                                        } else {
                                            dir3[i].setVisible(false);
                                        }
                                        buyFromRight.srl[0]--;
                                        buyFromRight.srl[needListByInt[i]]--;
                                    }
                                }
                            }
                            jugClicked[1][i1 - 2] = true;
                            buyFromSelf.srl[0]++;
                            buyFromSelf.srl[needListByInt[i1 - 2]]++;
                            for (int i = 0; i < numOfRsNeeded; i++) {
                                System.out.println("11");
                                if (!jugClicked[1][i]) {
                                    buyFromSelf.srl[0]++;
                                    buyFromSelf.srl[needListByInt[i]]++;
                                    if (canAfford(buyFromSelf, board.getResourceList(), board.getOrList())) {
                                        dir2[i].setVisible(true);
                                    } else {
                                        dir2[i].setVisible(false);
                                    }
                                    buyFromSelf.srl[0]--;
                                    buyFromSelf.srl[needListByInt[i]]--;
                                }
                            }

                            dir1[i1 - 2].setOpacity(1);
                            dir2[i1 - 2].setOpacity(0.3);
                            dir3[i1 - 2].setOpacity(1);
                            from[i1 - 2] = 2;
                            over = true;
                            for (int j = 0; j < numOfRsNeeded; j++) {
                                System.out.println("12");
                                if (from[j] == 0) {
                                    over = false;
                                    break;
                                }
                            }
                            if (over)
                                isFinish.setImage(ok);
                            // JOptionPane.showMessageDialog(null,
                            // "Judge Over");
                        }
                    });
                    dir3[i - 2].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                        public void handle(MouseEvent e) {

                            if (jugClicked[2][i1 - 2]) {
                                jugClicked[2][i1 - 2] = false;
                                buyFromRight.srl[0]--;
                                buyFromRight.srl[needListByInt[i1 - 2]]--;
                                for (int i = 0; i < numOfRsNeeded; i++) {
                                    System.out.println("13");
                                    if (!jugClicked[2][i]) {
                                        buyFromRight.srl[0]++;
                                        buyFromRight.srl[needListByInt[i]]++;
                                        if (canAfford(buyFromRight, board.getRightNeighbor().getResourceList(), board
                                                .getRightNeighbor().getSellOrList())) {
                                            dir3[i].setVisible(true);
                                        } else {
                                            dir3[i].setVisible(false);
                                        }
                                        buyFromRight.srl[0]--;
                                        buyFromRight.srl[needListByInt[i]]--;
                                    }
                                }

                                dir1[i1 - 2].setOpacity(1);
                                dir2[i1 - 2].setOpacity(1);
                                dir3[i1 - 2].setOpacity(1);
                                gold = gold + 1;
                                getChildren().remove(goldBoard);
                                showGold();
                                from[i1 - 2] = 0;
                                over = true;
                                for (int j = 0; j < numOfRsNeeded; j++) {
                                    System.out.println("14");
                                    if (from[j] == 0) {
                                        over = false;
                                        break;
                                    }
                                }
                                if (over)
                                    isFinish.setImage(ok);
                                else {
                                    isFinish.setImage(notok);
                                }
                                return;
                            }

                            if (jugClicked[0][i1 - 2]) {
                                buyFromLeft.srl[0]--;
                                buyFromLeft.srl[needListByInt[i1 - 2]]--;
                                jugClicked[0][i1 - 2] = false;
                                for (int i = 0; i < numOfRsNeeded; i++) {
                                    System.out.println("15");
                                    if (!jugClicked[0][i]) {
                                        buyFromLeft.srl[0]++;
                                        buyFromLeft.srl[needListByInt[i]]++;
                                        if (canAfford(buyFromLeft, board.getLeftNeighbor().getResourceList(), board
                                                .getLeftNeighbor().getSellOrList())) {
                                            dir1[i].setVisible(true);
                                        } else {
                                            dir1[i].setVisible(false);
                                        }
                                        buyFromLeft.srl[0]--;
                                        buyFromLeft.srl[needListByInt[i]]--;
                                    }
                                }
                            } else if (jugClicked[1][i1 - 2]) {
                                buyFromSelf.srl[0]--;
                                buyFromSelf.srl[needListByInt[i1 - 2]]--;
                                jugClicked[1][i1 - 2] = false;
                                for (int i = 0; i < numOfRsNeeded; i++) {
                                    System.out.println("16");
                                    if (!jugClicked[1][i]) {
                                        buyFromSelf.srl[0]++;
                                        buyFromSelf.srl[needListByInt[i]]++;
                                        if (canAfford(buyFromSelf, board.getResourceList(), board.getOrList())) {
                                            dir2[i].setVisible(true);
                                        } else {
                                            dir2[i].setVisible(false);
                                        }
                                        buyFromSelf.srl[0]--;
                                        buyFromSelf.srl[needListByInt[i]]--;
                                    }
                                }
                            }
                            jugClicked[2][i1 - 2] = true;
                            buyFromRight.srl[0]++;
                            buyFromRight.srl[needListByInt[i1 - 2]]++;
                            for (int i = 0; i < numOfRsNeeded; i++) {
                                System.out.println("17");
                                if (!jugClicked[2][i]) {
                                    buyFromRight.srl[0]++;
                                    buyFromRight.srl[needListByInt[i]]++;
                                    if (canAfford(buyFromRight, board.getRightNeighbor().getResourceList(), board
                                            .getRightNeighbor().getSellOrList())) {
                                        dir3[i].setVisible(true);
                                    } else {
                                        dir3[i].setVisible(false);
                                    }
                                    buyFromRight.srl[0]--;
                                    buyFromRight.srl[needListByInt[i]]--;
                                }
                            }

                            dir1[i1 - 2].setOpacity(1);
                            dir2[i1 - 2].setOpacity(1);
                            dir3[i1 - 2].setOpacity(0.3);
                            gold = gold - 1;
                            getChildren().remove(goldBoard);
                            showGold();
                            from[i1 - 2] = 3;
                            over = true;
                            for (int j = 0; j < numOfRsNeeded; j++) {
                                System.out.println("18");
                                if (from[j] == 0) {
                                    over = false;
                                    break;
                                }
                            }
                            if (over) {
                                isFinish.setImage(ok);
                            }
                            // JOptionPane.showMessageDialog(null,
                            // "Judge Over");
                        }
                    });
                    rs[i - 2].setLayoutX(64);
                    rs[i - 2].setLayoutY(69 * i);
                    rs[i - 2].setScaleX(0.9);
                    rs[i - 2].setScaleY(0.9);
                    getChildren().add(frame[i]);
                    getChildren().add(back[i - 2]);
                    getChildren().add(dir1[i - 2]);
                    getChildren().add(dir2[i - 2]);
                    getChildren().add(dir3[i - 2]);
                    getChildren().add(rs[i - 2]);
                    getChildren().add(label[i - 2]);
                }

            }
            getChildren().add(isFinish);
            isFinish.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                public void handle(MouseEvent e) {
                    if (over) {
                        System.out.println("is over");
                        buildAction.handle(new ActionEvent());
                        unload();
                    } else {
                        System.out.println("unload");
                        unload();
                    }
                    // JOptionPane.showMessageDialog(null, "Judge Over");
                }
            });
            for (int i = 0; i < numOfRsNeeded; i++) {
                System.out.println("19");
                dir1[i].toFront();
                dir2[i].toFront();
                dir3[i].toFront();
                rs[i].toFront();

            }
            showGold();
            setVisible(true);
        } catch (Throwable error) {
            Manager.error(error);
        }
    }

    private void showGold() {
        goldBoard = new Group();
        int k = 0;
        for (int j = 0; j < gold / 3; j++) {
            System.out.println("20");
            Image image = ResManager.getImage("geld3.png");
            iv[k] = new ImageView();
            iv[k].setImage(image);
            Reflection ref = new Reflection();
            ref.setTopOffset(-14);
            iv[k].setEffect(ref);
            iv[k].setScaleX(0.9);
            iv[k].setScaleY(0.9);
            iv[k].setLayoutY(60);
            iv[k].setLayoutX(k * 48);
            k++;
        }
        for (int j = 0; j < gold % 3; j++) {
            System.out.println("21");
            Image image = ResManager.getImage("geld1.png");
            iv[k] = new ImageView();
            iv[k].setImage(image);
            Reflection ref = new Reflection();
            ref.setTopOffset(-14);
            iv[k].setEffect(ref);
            iv[k].setScaleX(0.9);
            iv[k].setScaleY(0.9);
            iv[k].setLayoutY(60);
            iv[k].setLayoutX(k * 48);
            k++;
        }
        for (int j = 0; j < k; j++) {
            System.out.println("22");
            goldBoard.getChildren().add(iv[j]);
        }
        goldBoard.setLayoutX(16);
        goldBoard.setLayoutY(7);
        getChildren().add(goldBoard);
    }

    public boolean canAfford(SimpleResList need, SimpleResList simple, ArrayList<SimpleResList> orList) {
        need = SimpleResList.subtract(need, simple);
        ArrayList<SimpleResList> newOrList = (ArrayList<SimpleResList>) orList.clone();
        boolean res, jug;
        if (need.getTotalRes() == 0) {
            return true;
        }
        for (SimpleResList smpList : orList) {
            System.out.println("23");
            res = false;
            jug = false;
            for (int i = 1; i <= 7; i++) {
                System.out.println("24");
                if (smpList.numAt(i) > 0 && need.numAt(i) > 0) {
                    if (jug == false) {
                        jug = true;
                        res = true;
                    } else {
                        res = false;
                    }
                }
            }
            if (res) {
                for (int i = 1; i <= 7; i++) {
                    System.out.println("25");
                    if (smpList.numAt(i) > 0 && need.numAt(i) > 0) {
                        need.srl[0]--;
                        need.srl[i]--;
                        newOrList.remove(smpList);
                    }
                }
            }
            if (!jug) {
                newOrList.remove(smpList);
            }
        }
        if (need.getTotalRes() == 0) {
            return true;
        }
        return SimpleResList.jugOrListCanAfford(need, newOrList);
    }

    public void setBuildAction(EventHandler<ActionEvent> buildAction) {
        this.buildAction = buildAction;
    }

}