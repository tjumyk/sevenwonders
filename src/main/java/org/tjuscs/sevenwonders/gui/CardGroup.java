package org.tjuscs.sevenwonders.gui;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Lighting;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.tjuscs.sevenwonders.LocalMessages;
import org.tjuscs.sevenwonders.Manager;
import org.tjuscs.sevenwonders.kernel.*;
import org.tjuscs.sevenwonders.kernel.RecManager.TurnInfo;

import java.util.ArrayList;
import java.util.Locale;
import java.util.TreeMap;

public class CardGroup extends Parent {
    public static TreeMap<String, Integer> cardNameMap = new TreeMap<String, Integer>();
    private Group hoverBox;
    private Text text;
    private Rectangle bg;
    private Timeline hoverTimeline;
    private double deltaH;
    private boolean available = !Manager.getKernel().isReplayMode();

    public static void initializeData(double screenX2, BuyBoard buy, Board board) {
        CardGroup.board = board;
        screenX = screenX2;
        CardGroup.BI = buy;
        cardNameMap.put("Lumber Yard", 1);
        cardNameMap.put("Stone Pit", 2);
        cardNameMap.put("Clay Pool", 3);
        cardNameMap.put("Ore Vein", 4);
        cardNameMap.put("Tree Farm", 5);
        cardNameMap.put("Excavation", 6);
        cardNameMap.put("Clay Pit", 7);
        cardNameMap.put("Timber Yard", 8);
        cardNameMap.put("Forest Cave", 9);
        cardNameMap.put("Mine", 10);
        cardNameMap.put("Saw Mill", 11);
        cardNameMap.put("Quarry", 12);
        cardNameMap.put("Brick Yard", 13);
        cardNameMap.put("Foundry", 14);
        cardNameMap.put("Loom", 15);
        cardNameMap.put("Glass Works", 16);
        cardNameMap.put("Press", 17);

        cardNameMap.put("Workers Guild", 18);
        cardNameMap.put("Craftsmens Guild", 19);
        cardNameMap.put("Traders Guild", 20);
        cardNameMap.put("Philosophers Guild", 21);
        cardNameMap.put("Spies Guild", 22);
        cardNameMap.put("Strategists Guild", 23);
        cardNameMap.put("Shipowners Guild", 24);
        cardNameMap.put("Scientists Guild", 25);
        cardNameMap.put("Magistrates Guild", 26);
        cardNameMap.put("Builders Guild", 27);

        cardNameMap.put("Pawnshop", 28);
        cardNameMap.put("Baths", 29);
        cardNameMap.put("Altar", 30);
        cardNameMap.put("Theater", 31);
        cardNameMap.put("Aqueduct", 32);
        cardNameMap.put("Temple", 33);
        cardNameMap.put("Statue", 34);
        cardNameMap.put("Pantheon", 35);
        cardNameMap.put("Gardens", 36);
        cardNameMap.put("Town Hall", 37);
        cardNameMap.put("Palace", 38);
        cardNameMap.put("Courthouse", 39);
        cardNameMap.put("Senate", 40);

        cardNameMap.put("Stockade", 41);
        cardNameMap.put("Barracks", 42);
        cardNameMap.put("Guard Tower", 43);
        cardNameMap.put("Walls", 44);
        cardNameMap.put("Training Ground", 45);
        cardNameMap.put("Fortifications", 46);
        cardNameMap.put("Circus", 47);
        cardNameMap.put("Arsenal", 48);
        cardNameMap.put("Stables", 49);
        cardNameMap.put("Archery Range", 50);
        cardNameMap.put("Siege Workshop", 51);

        cardNameMap.put("Tavern", 52);
        cardNameMap.put("East Trading Post", 53);
        cardNameMap.put("West Trading Post", 54);
        cardNameMap.put("Marketplace", 55);
        cardNameMap.put("Forum", 56);
        cardNameMap.put("Caravansery", 57);
        cardNameMap.put("Vineyard", 58);
        cardNameMap.put("Bazar", 59);
        cardNameMap.put("Haven", 60);
        cardNameMap.put("Lighthouse", 61);
        cardNameMap.put("Chamber of Commerce", 62);
        cardNameMap.put("Arena", 63);

        cardNameMap.put("Apothecary", 64);
        cardNameMap.put("Workshop", 65);
        cardNameMap.put("Scriptorium", 66);
        cardNameMap.put("Dispensary", 67);
        cardNameMap.put("Laboratory", 68);
        cardNameMap.put("Library", 69);
        cardNameMap.put("School", 70);
        cardNameMap.put("Lodge", 71);
        cardNameMap.put("Observatory", 72);
        cardNameMap.put("University", 73);
        cardNameMap.put("Academy", 74);
        cardNameMap.put("Study", 75);
    }

    public CardGroup() {
        hoverBox = new Group();
        hoverBox.setLayoutX(10);
        hoverBox.setLayoutY(0);
        text = new Text();
        text.setFont(GUIManager.font);
        text.setFill(Color.BLACK);
        text.setWrappingWidth(300);
        text.setLayoutY(34);
        text.setLayoutX(10);
        if (GUIManager.enableDropShadowEffect)
            text.setEffect(new DropShadow());
        bg = new Rectangle(300, 30, Color.GOLD);
        bg.setArcHeight(10);
        bg.setArcWidth(10);
        hoverBox.getChildren().add(bg);
        hoverBox.getChildren().add(text);
        if (GUIManager.enableLightingEffect)
            bg.setEffect(new Lighting());
        hoverBox.setOpacity(0);
        getChildren().add(hoverBox);
        deltaH = 20;
        if (!LocalMessages.getLocale().equals(new Locale("zh", "CN")))
            deltaH = 35;
        // hoverBox.setOpacity(0);
    }

    public void load(Hand hand) {
        try {
            if (hoverTimeline != null) {
                hoverTimeline.stop();
                hoverBox.setVisible(false);
            }
            CardGroup.hand = hand;
            CommandOption[] commandOptions = board.buildCommandOptions(hand);
            cardLeft = commandOptions.length;
            g = new Group[cardLeft];
            com = new CommandOption[cardLeft];

            for (int i = 0; i < cardLeft; i++) {
                com[i] = commandOptions[i];
                // System.out.println("\n\nCommand:\n" + com);
                final Card cardItem = com[i].getCard();
                g[i] = new Group();
                ok[i] = new ImageView(ResManager.getImage("ok.png"));
                notok[i] = new ImageView(ResManager.getImage("notok.png"));
                free[i] = new ImageView(ResManager.getImage("freeok.png"));
                brown[i] = new ImageView(ResManager.getImage("pfeil.png"));
                brown1[i] = new ImageView(ResManager.getImage("pfeil.png"));
                chooser[i] = new ImageView(ResManager.getImage("chooser.png"));
                green[i] = new ImageView(ResManager.getImage("pfeilgreen.png"));
                green1[i] = new ImageView(ResManager.getImage("pfeilgreen.png"));
                green2[i] = new ImageView(ResManager.getImage("pfeilgreen.png"));
                chooser[i].setVisible(false);
                green[i].setOpacity(0);
                brown[i].setOpacity(0);
                green1[i].setOpacity(0);
                brown1[i].setOpacity(0);
                green2[i].setOpacity(0);
                green[i].setVisible(false);
                brown[i].setVisible(false);
                green1[i].setVisible(false);
                brown1[i].setVisible(false);
                green2[i].setVisible(false);
                card[i] = new ImageView(ResManager.getImage(cardNameMap.get(cardItem.getName()) + ".jpg"));
                if (!com[i].isAvailableFree() && com[i].isCanBuild() && com[i].getNeedToBuild() > 0)
                    needCoins[i] = new Text(String.valueOf(com[i].getNeedToBuild()));
                else
                    needCoins[i] = new Text();
                needCoins[i].setFill(Color.WHITE);
                needCoins[i].setFont(GUIManager.font);
                needCoins[i].setLayoutX(10);
                needCoins[i].setLayoutY(20);

                // if(card[i].getImage() == null)
                // Manager.warn("Image: " + cardNameMap.get(cardItem.getName())
                // +
                // ".jpg is lost.");
                Rectangle rec = new Rectangle(208, 320);
                rec.setArcHeight(30);
                rec.setArcWidth(30);
                card[i].setClip(rec);
                g[i].setScaleX(0.75);
                g[i].setScaleY(0.75);
                if (GUIManager.enableDropShadowEffect)
                    g[i].setEffect(new DropShadow());
                g[i].getChildren().add(card[i]);
                freeBuild = com[i].isAvailableFree();
                affordBuild = com[i].isBuildable();
                affordWonder = com[i].canBuildStage();

                final boolean isFree = freeBuild;
                final boolean isAB = affordBuild;
                final boolean isAW = affordWonder;
                if (freeBuild)
                    setSign(0, i);
                else {
                    if (affordBuild)
                        setSign(1, i);
                    else
                        setSign(2, i);
                }
                final int i1 = i;
                card[i].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent e) {
                        e.consume();
                        chooser[i1].setVisible(true);
                        chooser[i1].setLayoutX(35);
                        chooser[i1].setLayoutY(80);
                        green[i1].setLayoutX(35);
                        green[i1].setLayoutY(84);
                        green1[i1].setLayoutX(35);
                        green1[i1].setLayoutY(147);
                        green2[i1].setLayoutX(35);
                        green2[i1].setLayoutY(210);
                        brown[i1].setLayoutX(35);
                        brown[i1].setLayoutY(84);
                        brown1[i1].setLayoutX(35);
                        brown1[i1].setLayoutY(147);
                        // workout whether it could be afford
                        if (isFree) {
                            brown[i1].setVisible(false);
                            green[i1].setVisible(true);
                        } else {
                            if (isAB) {
                                brown[i1].setVisible(false);
                                green[i1].setVisible(true);
                            } else {
                                green[i1].setVisible(false);
                                brown[i1].setVisible(true);
                            }
                        }
                        if (isAW) {
                            brown1[i1].setVisible(false);
                            green1[i1].setVisible(true);
                        } else {
                            green1[i1].setVisible(false);
                            brown1[i1].setVisible(true);
                        }
                        green2[i1].setVisible(true);
                    }
                });

                card[i].addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent event) {
                        // TODO Auto-generated method stub
                        event.consume();

                    }
                });

                card[i].addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent event) {
                        // TODO Auto-generated method stub
                        event.consume();

                    }
                });

                green[i].addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent e) {
                        e.consume();
                        green[i1].setOpacity(0);
                    }
                });

                if (available)
                    green[i].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                        public void handle(MouseEvent e) {
                            e.consume();
                            markCard(i1);

                            EventHandler<ActionEvent> buildAction = new EventHandler<ActionEvent>() {
                                public void handle(ActionEvent event) {
                                    com[i1].setCommand(Command.BUILD_CARD);
                                    implementCommand(com[i1]);
                                    // JOptionPane.showMessageDialog(null,
                                    // com[i1]
                                    // .getCard());
                                    player.addCard(cardNameMap.get(com[i1].getCard().getName()), com[i1].getCard()
                                            .getColor());
                                    TurnInfo info = new TurnInfo();
                                    info.playerIndex = 0;
                                    info.chosenBuyDecision = null;
                                    info.chosenCardIndex = i1;
                                    info.chosenCommandType = Command.BUILD_CARD;
                                    info.turnNum = MainBackGround.turn;
                                    Manager.getKernel().recordTurnInfo(info);

                                    EventHandler<ActionEvent> act = new EventHandler<ActionEvent>() {
                                        public void handle(ActionEvent event) {
                                            MainBackGround.nextTurn();
                                        }
                                    };
                                    Timeline tl = new Timeline(new KeyFrame(Duration.seconds(0.3), new KeyValue(g[i1]
                                            .opacityProperty(), 0)), new KeyFrame(Duration.seconds(0.3), act));
                                    tl.play();
                                }
                            };
                            if (!freeBuild && com[i1].isOptions()) {

                                // TODO changed by QinXingyao
                                BI.load(com[i1].getCard(), board);
                                BI.setBuildAction(buildAction);
                                BI.toFront();
                            } else
                                buildAction.handle(new ActionEvent());
                        }
                    });
                brown[i].addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent e) {
                        e.consume();
                        brown[i1].setOpacity(0);
                    }
                });
                green[i].addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent e) {
                        e.consume();
                        green[i1].setOpacity(1);
                    }
                });
                brown[i].addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent e) {
                        e.consume();
                        brown[i1].setOpacity(1);
                    }
                });
                green1[i].addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent e) {
                        e.consume();
                        green1[i1].setOpacity(0);
                    }
                });

                if (available)
                    green1[i].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                        public void handle(MouseEvent e) {
                            e.consume();
                            markCard(i1);

                            // BI.load(10, 7);
                            // BI.toFront();
                            com[i1].setCommand(Command.BUILD_STAGE);
                            implementCommand(com[i1]);
                            TurnInfo info = new TurnInfo();
                            info.playerIndex = 0;
                            info.chosenBuyDecision = null;
                            info.chosenCardIndex = i1;
                            info.chosenCommandType = Command.BUILD_STAGE;
                            info.turnNum = MainBackGround.turn;
                            Manager.getKernel().recordTurnInfo(info);
                            EventHandler<ActionEvent> act = new EventHandler<ActionEvent>() {
                                public void handle(ActionEvent event) {
                                    MainBackGround.nextTurn();
                                }
                            };
                            Timeline tl = new Timeline(new KeyFrame(Duration.seconds(0.3), new KeyValue(g[i1]
                                    .opacityProperty(), 0)), new KeyFrame(Duration.seconds(0.3), act));
                            tl.play();
                        }
                    });
                brown1[i].addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent e) {
                        e.consume();
                        brown1[i1].setOpacity(0);
                    }
                });
                green1[i].addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent e) {
                        e.consume();
                        green1[i1].setOpacity(1);
                    }
                });
                brown1[i].addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent e) {
                        e.consume();
                        brown1[i1].setOpacity(1);
                    }
                });
                green2[i].addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent e) {
                        e.consume();
                        green2[i1].setOpacity(0);
                    }
                });
                green2[i].addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent e) {
                        e.consume();
                        green2[i1].setOpacity(1);
                    }
                });

                if (available)
                    green2[i].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                        public void handle(MouseEvent e) {
                            e.consume();
                            markCard(i1);

                            com[i1].setCommand(Command.SELL_CARD);
                            implementCommand(com[i1]);
                            TurnInfo info = new TurnInfo();
                            info.playerIndex = 0;
                            info.chosenBuyDecision = null;
                            info.chosenCardIndex = i1;
                            info.chosenCommandType = Command.SELL_CARD;
                            info.turnNum = MainBackGround.turn;
                            Manager.getKernel().recordTurnInfo(info);
                            EventHandler<ActionEvent> act = new EventHandler<ActionEvent>() {
                                public void handle(ActionEvent event) {
                                    MainBackGround.addCardGivenUp(1);
                                    MainBackGround.nextTurn();
                                }
                            };
                            player.discardCard(cardNameMap.get(com[i1].getCard().getName()));
                            Timeline tl = new Timeline(new KeyFrame(Duration.seconds(0.3), new KeyValue(g[i1]
                                    .opacityProperty(), 0)), new KeyFrame(Duration.seconds(0.3), act));
                            tl.play();
                        }
                    });

                // final double x = card[i].getLayoutX();
                // final double y = card[i].getLayoutY();
                card[i].addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent e) {
                        e.consume();
                        for (int j = 0; j < cardLeft; j++) {
                            if (j != i1) {
                                g[j].setLayoutY(getLayoutY() + 10);
                                chooser[j].setVisible(false);
                                green[j].setVisible(false);
                                brown[j].setVisible(false);
                                green1[j].setVisible(false);
                                brown1[j].setVisible(false);
                                green2[j].setVisible(false);
                            }
                        }
                        g[i1].setLayoutY(getLayoutY() - 20);
                        g[i1].toFront();
                        chooser[i1].toFront();
                        green[i1].toFront();
                        brown[i1].toFront();
                        green1[i1].toFront();
                        brown1[i1].toFront();
                        green2[i1].toFront();
                        needCoins[i1].toFront();

                        EventHandler<ActionEvent> act = new EventHandler<ActionEvent>() {
                            public void handle(ActionEvent event) {
                                hoverBox.setVisible(true);
                                final StringBuilder str = new StringBuilder();
                                str.append(cardItem.getName() + "\n");
                                double h = 50;

                                ArrayList<String> freeList = cardItem.getFreeList();
                                if (freeList != null && freeList.size() > 0) {
                                    // str.append("Freelist:\n");
                                    // h+=20;
                                    for (String s : freeList) {
                                        str.append("--->" + s + "\n");
                                        h += deltaH;

                                    }
                                }
                                EventHandler<ActionEvent> act = new EventHandler<ActionEvent>() {
                                    public void handle(ActionEvent event) {
                                        text.setText(str.toString());
                                    }
                                };

                                double lx = 10;
                                if (i1 < cardLeft / 2)
                                    lx = screenX - 310;
                                hoverBox.toFront();
                                Timeline tl = new Timeline(new KeyFrame(Duration.seconds(0.2), new KeyValue(hoverBox
                                        .opacityProperty(), 0.85), new KeyValue(hoverBox.layoutXProperty(), lx),
                                        new KeyValue(bg.heightProperty(), h), new KeyValue(text.opacityProperty(), 0)),
                                        new KeyFrame(Duration.seconds(0.2), act), new KeyFrame(Duration.seconds(0.3),
                                        new KeyValue(text.opacityProperty(), 1)), new KeyFrame(Duration
                                        .seconds(2), new KeyValue(hoverBox.opacityProperty(), 0.85),
                                        new KeyValue(text.opacityProperty(), 1)), new KeyFrame(Duration
                                        .seconds(2.3), new KeyValue(hoverBox.opacityProperty(), 0),
                                        new KeyValue(text.opacityProperty(), 0)));
                                tl.play();
                                // JOptionPane.showMessageDialog(null,
                                // freeList);
                            }
                        };
                        if (hoverTimeline != null)
                            hoverTimeline.stop();
                        hoverTimeline = new Timeline(new KeyFrame(Duration.seconds(0.5), act));
                        hoverTimeline.play();
                    }
                });

                card[i].addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent e) {
                        e.consume();
                        if (e.getX() > 208 || e.getY() > 320 || e.getX() < 0 || e.getY() < 0) {
                            g[i1].setLayoutY(getLayoutY() + 10);
                            chooser[i1].setVisible(false);
                            green[i1].setVisible(false);
                            brown[i1].setVisible(false);
                            green1[i1].setVisible(false);
                            brown1[i1].setVisible(false);
                            green2[i1].setVisible(false);
                        }
                    }

                });

                g[i].setLayoutY(10);

                g[i].getChildren().add(chooser[i]);
                g[i].getChildren().add(green[i]);
                g[i].getChildren().add(brown[i]);
                g[i].getChildren().add(green1[i]);
                g[i].getChildren().add(brown1[i]);
                g[i].getChildren().add(green2[i]);
                g[i].getChildren().add(needCoins[i]);
                if (cardLeft * 208 * 0.75 + 280 * 0.25 > screenX) {
                    int perWidth = (int) ((screenX - 208 * 0.25) / cardLeft);
                    g[i].setLayoutX(perWidth * i);
                } else {
                    int longLeft = (int) (screenX - cardLeft * 208 * 0.75);
                    g[i].setLayoutX(208 * 0.75 * i + longLeft / 2 - 208 * 0.125);
                }
            }
            getChildren().addAll(g);

            for (int j = 0; j < cardLeft; j++) {
                final int current = j;
                g[j].setOnMouseEntered(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent event) {
                        for (int j = 0; j < cardLeft; j++) {
                            if (j < current) {
                                Timeline tl = new Timeline(new KeyFrame(Duration.seconds(0.1), new KeyValue(g[j]
                                        .scaleXProperty(), 0.75), new KeyValue(g[j].scaleYProperty(), 0.75)));
                                tl.play();
                            } else if (j > current) {
                                Timeline tl = new Timeline(new KeyFrame(Duration.seconds(0.1), new KeyValue(g[j]
                                        .scaleXProperty(), 0.75), new KeyValue(g[j].scaleYProperty(), 0.75)));
                                tl.play();
                            } else {
                                Timeline tl = new Timeline(new KeyFrame(Duration.seconds(0.1), new KeyValue(g[j]
                                        .scaleXProperty(), 1), new KeyValue(g[j].scaleYProperty(), 1)));
                                tl.play();
                            }

                        }
                    }
                });
            }

            this.setOnMouseExited(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    for (int j = 0; j < cardLeft; j++) {
                        Timeline tl = new Timeline(new KeyFrame(Duration.seconds(0.1), new KeyValue(g[j]
                                .scaleXProperty(), 0.75), new KeyValue(g[j].scaleYProperty(), 0.75)));
                        tl.play();
                        // if(card[j].getLayoutY() < card[0].getLayoutY()){
                        // card[0].setLayoutY(card[j].getLayoutY());
                        // }else if(card[j].getLayoutY() >
                        // card[0].getLayoutY()){
                        // card[j].setLayoutY(card[j].getLayoutY());
                        // }
                    }
                    // hoverBox.setVisible(false);
                }
            });

            // if (available) {
            // this.setOnKeyPressed(new EventHandler<KeyEvent>() {
            // @Override
            // public void handle(KeyEvent event) {
            // MainBackGround.nextTurn();
            // }
            // });
            // }
            // this.requestFocus();
        } catch (Throwable error) {
            Manager.error(error);
        }
    }

    public void unload() {
        if (CardGroup.hand != null)
            getChildren().removeAll(g);
    }

    public void nextHand(Hand hand, boolean isFirstTurnInOneAge) {
        final CardGroup self = this;
        final Hand newHand = hand;
        EventHandler<ActionEvent> act = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                unload();
                if (newHand != null)
                    load(newHand);
                else
                    self.setVisible(false);
            }
        };
        if (CardGroup.hand != null) {
            if (!isFirstTurnInOneAge) {
                Timeline tl = new Timeline(new KeyFrame(Duration.seconds(0.3), new KeyValue(this.layoutXProperty(),
                        MainBackGround.age == 2 ? screenX : -screenX)), new KeyFrame(Duration.seconds(0.3),
                        new KeyValue(this.layoutXProperty(), MainBackGround.age == 2 ? -screenX : screenX)),
                        new KeyFrame(Duration.seconds(0.3), act), new KeyFrame(Duration.seconds(0.6), new KeyValue(
                        this.layoutXProperty(), 0)));
                tl.play();
            } else {
                Timeline tl = new Timeline(new KeyFrame(Duration.seconds(0.3),
                        new KeyValue(this.layoutYProperty(), 150), new KeyValue(this.layoutXProperty(),
                        this.getLayoutX())), new KeyFrame(Duration.seconds(0.31), new KeyValue(
                        this.layoutXProperty(), MainBackGround.age == 2 ? -screenX : screenX), new KeyValue(
                        this.layoutYProperty(), 0)), new KeyFrame(Duration.seconds(0.31), act), new KeyFrame(
                        Duration.seconds(1), new KeyValue(this.layoutXProperty(), 0)));
                tl.play();
            }
        } else {// Only when Age 1 Turn 1
            Timeline tl = new Timeline(new KeyFrame(Duration.seconds(0), new KeyValue(this.layoutXProperty(),
                    MainBackGround.age == 2 ? -screenX : screenX)), new KeyFrame(Duration.seconds(0), act),
                    new KeyFrame(Duration.seconds(1), new KeyValue(this.layoutXProperty(), 0)));
            tl.play();
        }
    }

    public void setSign(int signal, int i) {
        ImageView sign = new ImageView();
        if (signal == 0) // free
            sign.setImage(ResManager.getImage("freeok.png"));
        else if (signal == 1)
            sign.setImage(ResManager.getImage("ok.png"));
        else if (signal == 2)
            sign.setImage(ResManager.getImage("notok.png"));
        sign.setScaleX(0.7);
        sign.setScaleY(0.7);
        sign.setLayoutX(-1.5);
        final ImageView s = sign;
        sign.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                s.setOpacity(0);
            }
        });
        sign.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                s.setOpacity(1);
            }
        });
        g[i].getChildren().add(sign);
    }

    private void implementCommand(CommandOption com) {
        board.implementCommand(com);
        Card card = com.getCard();
        hand.remove(card.getName());
    }

    public void setplayer(Wonders w) {
        player = w;
    }

    public void changeRole(final Board board, final Hand hand) {
        final CardGroup cg = this;
        EventHandler<ActionEvent> act = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                cg.unload();
                CardGroup.board = board;
                cg.load(hand);
            }
        };
        Timeline tl = new Timeline(new KeyFrame(Duration.seconds(0.3), new KeyValue(this.layoutYProperty(), 105)),
                new KeyFrame(Duration.seconds(0.3), act), new KeyFrame(Duration.seconds(0.6), new KeyValue(
                this.layoutYProperty(), 0)));
        tl.play();
    }

    public void markCard(final int index) {
        Rectangle rec = new Rectangle(-1.5, -1.5, 208 + 3, 320 + 3);
        rec.setFill(Color.TRANSPARENT);
        Rectangle mstk = (Rectangle) MovingStroke.set(rec, Color.GOLDENROD, 6, 20, 20, 0.3);
        g[index].getChildren().add(mstk);
        mstk.toBack();
    }

    public void markCard(Card card) {
        for (int i = 0; i < hand.size(); i++)
            if (hand.get(i).getName().equals(card.getName())) {
                markCard(i);
                break;
            }
    }

    private ImageView[] card = new ImageView[7];
    private ImageView[] green = new ImageView[7];
    private ImageView[] brown = new ImageView[7];
    private ImageView[] green1 = new ImageView[7];
    private ImageView[] brown1 = new ImageView[7];
    private ImageView[] green2 = new ImageView[7];
    private ImageView[] chooser = new ImageView[7];
    private ImageView[] notok = new ImageView[7];
    private ImageView[] ok = new ImageView[7];
    private ImageView[] free = new ImageView[7];
    private Text[] needCoins = new Text[7];
    private int cardLeft;
    private boolean affordBuild;
    private boolean affordWonder;
    private boolean freeBuild;
    private static Group[] g;
    private static double screenX;
    private static BuyBoard BI;
    private CommandOption[] com;
    private static Hand hand;
    private static Board board;
    private static Wonders player;

}
