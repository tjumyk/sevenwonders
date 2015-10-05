package org.tjuscs.sevenwonders.gui;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import org.tjuscs.sevenwonders.kernel.CommandOption;

public class GUICard extends Group {
    public GUICard(BuyBoard buy, CommandOption com1, int i) {
        BI = buy;
        com = com1;

        org.tjuscs.sevenwonders.kernel.Card cardItem = com.getCard();
        ok = new ImageView(ResManager.getImage("ok.png"));
        notok = new ImageView(ResManager.getImage("notok.png"));
        free = new ImageView(ResManager.getImage("free.png"));
        brown = new ImageView(ResManager.getImage("pfeil.png"));
        brown1 = new ImageView(ResManager.getImage("pfeil.png"));
        chooser = new ImageView(ResManager.getImage("chooser.png"));
        green = new ImageView(ResManager.getImage("pfeilgreen.png"));
        green1 = new ImageView(ResManager.getImage("pfeilgreen.png"));
        green2 = new ImageView(ResManager.getImage("pfeilgreen.png"));
        chooser.setVisible(false);
        green.setOpacity(0);
        brown.setOpacity(0);
        green1.setOpacity(0);
        brown1.setOpacity(0);
        green2.setOpacity(0);
        green.setVisible(false);
        brown.setVisible(false);
        green1.setVisible(false);
        brown1.setVisible(false);
        green2.setVisible(false);
        card = new ImageView(ResManager.getImage(i + ".jpg"));
        Rectangle rec = new Rectangle(208, 320);
        rec.setArcHeight(30);
        rec.setArcWidth(30);
        card.setClip(rec);
        getChildren().add(card);
        freeBuild = com.isAvailableFree();
        affordBuild = com.isBuildable();
        affordWonder = com.canBuildStage();
        final boolean isFree = freeBuild;
        if (freeBuild)
            setSign(0);
        else {
            if (affordBuild)
                setSign(1);
            else
                setSign(2);
        }

        addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                chooser.setVisible(true);
                chooser.setLayoutX(35);
                chooser.setLayoutY(80);
                green.setLayoutX(35);
                green.setLayoutY(84);
                green1.setLayoutX(35);
                green1.setLayoutY(147);
                green2.setLayoutX(35);
                green2.setLayoutY(210);
                brown.setLayoutX(35);
                brown.setLayoutY(84);
                brown1.setLayoutX(35);
                brown1.setLayoutY(147);
                // workout whether it could be afford
                System.out.println(freeBuild);
                if (freeBuild) {
                    brown.setVisible(false);
                    green.setVisible(true);
                    System.out.println("ok1");
                } else {
                    if (affordBuild) {
                        brown.setVisible(false);
                        green.setVisible(true);
                        System.out.println("ok2");
                    } else {
                        green.setVisible(false);
                        brown.setVisible(true);
                        System.out.println("ok3");
                    }
                }
                if (affordWonder) {
                    brown1.setVisible(false);
                    green1.setVisible(true);
                } else {
                    green1.setVisible(false);
                    brown1.setVisible(true);
                }
                green2.setVisible(true);
            }
        });

        green.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                green.setOpacity(0);
            }
        });
        green.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                /*
				 * if(isFree){ com.setCommand(Command.BUILD_CARD);
				 * board.implementCommand(com); } else{
				 */

                // TODO changed by QinXingyao
                // BI.load(10, 7);
                // BI.toFront();
                // }
            }
        });
        brown.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                brown.setOpacity(0);
            }
        });
        green.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                green.setOpacity(1);
            }
        });
        brown.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                brown.setOpacity(1);
            }
        });
        green1.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                green1.setOpacity(0);
            }
        });
        green1.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {

                // TODO changed by QinXingyao
                // BI.load(10, 7);
                // BI.toFront();
            }
        });
        brown1.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                brown1.setOpacity(0);
            }
        });
        green1.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                green1.setOpacity(1);
            }
        });
        brown1.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                brown1.setOpacity(1);
            }
        });
        green2.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                green2.setOpacity(0);
            }
        });
        green2.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                green2.setOpacity(1);
            }
        });

        addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                setLayoutY(getLayoutY() - 10);
                toFront();
                chooser.toFront();
                green.toFront();
                brown.toFront();
                green1.toFront();
                brown1.toFront();
                green2.toFront();
            }
        });
        addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                if (e.getX() > 208 || e.getY() > 320 || e.getX() < 0 || e.getY() < 0) {
                    setLayoutY(getLayoutY() + 10);
                    chooser.setVisible(false);
                    green.setVisible(false);
                    brown.setVisible(false);
                    green1.setVisible(false);
                    brown1.setVisible(false);
                    green2.setVisible(false);
                }
            }
        });
        setLayoutY(10);
        getChildren().add(chooser);
        getChildren().add(green);
        getChildren().add(brown);
        getChildren().add(green1);
        getChildren().add(brown1);
        getChildren().add(green2);

    }

    public void setSign(int signal) {
        ImageView sign = new ImageView();
        if (signal == 0) // free
            sign.setImage(ResManager.getImage("freeok.png"));
        else if (signal == 1)
            sign.setImage(ResManager.getImage("ok.png"));
        else if (signal == 2)
            sign.setImage(ResManager.getImage("notok.png"));
        getChildren().add(sign);
    }

    private ImageView card;
    private ImageView green;
    private ImageView brown;
    private ImageView green1;
    private ImageView brown1;
    private ImageView green2;
    private ImageView chooser;
    private ImageView notok;
    private ImageView ok;
    private ImageView free;
    private boolean affordBuild;
    private boolean affordWonder;
    private boolean freeBuild;
    private static BuyBoard BI;
    private CommandOption com;
}
