package org.tjuscs.sevenwonders.gui;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.TreeMap;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;

import org.tjuscs.sevenwonders.Manager;
import org.tjuscs.sevenwonders.kernel.Board;
import org.tjuscs.sevenwonders.kernel.Command;
import org.tjuscs.sevenwonders.kernel.CommandOption;
import org.tjuscs.sevenwonders.kernel.Hand;
import org.tjuscs.sevenwonders.kernel.KernelManager;
import org.tjuscs.sevenwonders.kernel.SimpleResList;

//provide:
//public static void dicCounterClockwise() 背景箭头逆时针
//public static void dicClockwise() 背景箭头顺时针
//public static void changeCardGivenup(int i)
//public static void waiting(int i)
//public static void shopping(int i)
//public static void looking(int i)

public class MainBackGround extends SceneContainer {
	private static boolean remable = false;
	private static int signrec;
	private static MainBackGround mbg;
	// private static Group root;
	private static boolean wonderBorderEffect;
	private static boolean wonderReflectEffect;
	private static TPABoard[] tpa;
	private static double xrate;
	private static double yrate;
	static double screenX;
	static double screenY;
	private static double Ox;
	private static double Oy;
	private static int numOfPlayers;
	private static Wonders[] wonder;
	private static ImageView background;
	private static double R;
	private static double r;
	private static double beta;
	private static double theta;
	private static double alpha;
	private static ImageView counterClockwise0;
	private static ImageView counterClockwise1;
	private static ImageView counterClockwise2;
	private static ImageView counterClockwise3;
	private static Circle[] player;
	private static boolean[] isTwice;
	private static Group playerBoard;
	private static Block b;
	private static boolean handlerEnable;
	private static Label numOfCardGivenup;
	private static int CardsGivenup;
	private static int observeIndex;

	public static int getCardsGivenup() {
		return CardsGivenup;
	}

	// private static Circle[] look;
	// private static Circle[] shop;
	private static Circle[] wait;
	private static Group cardBoard;
	private static boolean moveable;
	private static boolean isDragging;
	private static int targetWonderIndex;
	private static CardGroup handCard;
	private static BuyBoard buy;
	private static ImageView stageLabel;

	private static Board[] boards;
	private static Hand[] hands;
	private static CommandOption[] cms;
	private static SimpleResList[] resList;
	private static int[] orListNum;
	// private static ArrayList<SimpleResList>[] orList = new ArrayList[7];
	private static int[] coins;
	public static int age, turn;
	private static boolean isIncard;
	private static Group bigCircle;
	private static Timeline[] judgeStateTimeline;
	private static Circle[] buyStateCircle;
	private static Timeline[] wonderFadeTimeline;
	private static ReplayControl replayControl;

	public MainBackGround() {
		mbg = this;
	}

	public void load() {

		age = 1;
		turn = 1;
		Manager.getKernel().startAge(1);
		boards = Manager.getKernel().getBoards();
		hands = Manager.getKernel().getHands();
		screenX = GUIManager.width;// get the width of the screen
		screenY = GUIManager.height;// get the height of the screen
		// root = new Group();
		scene = new Scene(root, screenX, screenY, GUIManager.bgColor);
		root.setCursor(new ImageCursor(GUIManager.cursor));
		R = screenX / 2.8;//
		r = screenY / 3.1;//
		Ox = screenX / 2 - 140;//
		Oy = screenY / 2 - 98;//
		numOfPlayers = boards.length; // get the amount of players
		moveable = false;
		isDragging = false;
		resList = new SimpleResList[numOfPlayers];
		isIncard = false;
		orListNum = new int[numOfPlayers];
		coins = new int[numOfPlayers];
		for (int i = 0; i < numOfPlayers; i++)
			coins[i] = 3;
		judgeStateTimeline = new Timeline[numOfPlayers];
		buyStateCircle = new Circle[numOfPlayers];
		// wonderBorderEffect = false;
		// wonderReflectEffect = false;
		if (!GUIManager.enableReflectionEffect) {
			Ox = screenX / 2 - 140;//
			Oy = screenY / 2 - 68;
			r = screenY / 3.5;
		}
		observeIndex = 0;
		isTwice = new boolean[numOfPlayers];
		// primaryStage.setResizable(false);
		for (int i = 0; i < numOfPlayers; i++)
			isTwice[i] = false;
		handlerEnable = true;
		player = new Circle[numOfPlayers];
		wait = new Circle[numOfPlayers];
		// shop = new Circle[numOfPlayers];
		// look = new Circle[numOfPlayers];
		numOfCardGivenup = new Label();
		numOfCardGivenup.setVisible(true);
		CardsGivenup = 0;
		String s = String.valueOf(CardsGivenup);
		numOfCardGivenup.setText(s);
		numOfCardGivenup.setFont(Font.font("Arial", 45));

		if (GUIManager.enableGlowEffect)
			numOfCardGivenup.setEffect(new Glow());
		numOfCardGivenup.setTextFill(Color.web("#c88d15"));
		b = new Block(screenX, screenY);
		b.setVisible(false);
		root.getChildren().add(b);
		TPABoard.initialize();
		tpa = new TPABoard[numOfPlayers];

		final Paint[] color = new Paint[7];
		color[0] = Color.RED;
		color[1] = Color.ORANGE;
		color[2] = Color.YELLOW;
		color[3] = Color.YELLOWGREEN;
		color[4] = Color.GREEN;
		color[5] = Color.BLUE;
		color[6] = Color.PURPLE;
		for (int i = 0; i < 7; i++) {
			Paint temp = color[i];
			int newIndex = (int) (Math.random() * 7);
			color[i] = color[newIndex];
			color[newIndex] = temp;
		}

		playerBoard = new Group();
		// primaryStage.setFullScreen(true);
		// primaryStage.initStyle(StageStyle.UNDECORATED);
		Image bg = ResManager.getImage("bg.jpg");
		background = new ImageView(bg);
		if (!GUIManager.isFullScreen && !Manager.isApplet)
			this.setDragBackground(background);
		xrate = screenX / 1220.0;
		yrate = screenY / 784.0;
		background.setScaleX(xrate);
		background.setScaleY(yrate);
		numOfCardGivenup.setLayoutX(1106 * xrate);
		numOfCardGivenup.setLayoutY(94 * yrate);
		background.setTranslateX((scene.getWidth() - 1220.0) / 2.0);
		background.setTranslateY((scene.getHeight() - 784.0) / 2.0);
		// Rectangle clip = new Rectangle(0,0,1220,784);
		// clip.setArcHeight(50);
		// clip.setArcWidth(50);
		// background.setClip(clip);
		theta = Math.PI / 2;
		root.getChildren().add(background);
		root.getChildren().add(numOfCardGivenup);
		updateAge(1);

		Image im1 = ResManager.getImage("dir0.jpg");
		counterClockwise0 = new ImageView(im1);
		counterClockwise0.setScaleX(xrate);
		counterClockwise0.setScaleY(yrate);
		counterClockwise0.setTranslateX((im1.getWidth() * xrate - 107.0) / 2 + 271.23 * xrate);
		counterClockwise0.setTranslateY((im1.getHeight() * yrate - 86.0) / 2 + 560 * yrate);
		counterClockwise0.setVisible(false);

		Image im2 = ResManager.getImage("dir1.jpg");
		counterClockwise1 = new ImageView(im2);
		counterClockwise1.setScaleX(xrate);
		counterClockwise1.setScaleY(yrate);
		counterClockwise1.setTranslateX((im2.getWidth() * xrate - 112.0) / 2 + 826 * xrate);
		counterClockwise1.setTranslateY((im2.getHeight() * yrate - 86.0) / 2 + 560 * yrate);
		counterClockwise1.setVisible(false);

		Image im3 = ResManager.getImage("dir2.jpg");
		counterClockwise2 = new ImageView(im3);
		counterClockwise2.setScaleX(xrate);
		counterClockwise2.setScaleY(yrate);
		counterClockwise2.setTranslateX((im3.getWidth() * xrate - 52.0) / 2 + 846.4 * xrate);
		counterClockwise2.setTranslateY((im3.getHeight() * yrate - 39.0) / 2 + 223 * yrate);
		counterClockwise2.setVisible(false);

		Image im4 = ResManager.getImage("dir3.jpg");
		counterClockwise3 = new ImageView(im4);
		counterClockwise3.setScaleX(xrate);
		counterClockwise3.setScaleY(yrate);
		counterClockwise3.setTranslateX((im4.getWidth() * xrate - 52.0) / 2 + 302.6 * xrate);
		counterClockwise3.setTranslateY((im4.getHeight() * yrate - 39.0) / 2 + 222.8 * yrate);
		counterClockwise3.setVisible(false);
		root.getChildren().add(counterClockwise0);
		root.getChildren().add(counterClockwise1);
		root.getChildren().add(counterClockwise2);
		root.getChildren().add(counterClockwise3);

		Image[] image = new Image[numOfPlayers];
		wonder = new Wonders[numOfPlayers];
		Rectangle[] rec = new Rectangle[numOfPlayers];

		TreeMap<String, Integer> boardNameMap = new TreeMap<String, Integer>();
		boardNameMap.put("Rhodes", 0);
		boardNameMap.put("Alexandria", 1);
		boardNameMap.put("Ephesus", 2);
		boardNameMap.put("Babylon", 3);
		boardNameMap.put("Olympia", 4);
		boardNameMap.put("Halicarnassus", 5);
		boardNameMap.put("Giza", 6);

		TreeMap<String, Integer> resNameMap = new TreeMap<String, Integer>();
		// resNameMap.put("", value)

		for (int i = 0; i < numOfPlayers; i++) {
			// Manager.debug(boards[i].getResourceList());
			int ii = boardNameMap.get(boards[i].getName());
			if (boards[i].isBSide())
				ii += 7;
			// Manager.debug("" + ii + boards[i].isBSide());
			image[i] = ResManager.getImage("board" + ii + ".jpg");
			// }
			rec[i] = new Rectangle(image[i].getWidth(), image[i].getHeight(), Color.TRANSPARENT);
			rec[i].setX(0);
			rec[i].setY(0);
			rec[i].setArcWidth(20);
			rec[i].setArcHeight(20);
			// if (i > 0) {
			rec[i].setStroke(color[i]);
			rec[i].setStrokeWidth(8);
			// } else {
			// MovingStroke.set(rec[i], (Color) color[i], 8.0, 20.0, 15.0, 0);
			// rec[i].setStroke(color[i]);
			// rec[i].setStrokeWidth(14);
			// }
			// tpa[i] = new TPABoard();
			if (GUIManager.enableLightingEffect)
				rec[i].setEffect(new Lighting());
			tpa[i] = new TPABoard();
			wonder[i] = new Wonders(image[i], screenX, screenY, GUIManager.enableReflectionEffect, ii, tpa[i], root);

			SimpleResList list = boards[i].getResourceList();
			for (int index = 1; index <= 7; index++) {
				for (int am = 0; am < list.numAt(index); am++)
					wonder[i].addResource(index);
				// Manager.debug(i + "Adding Res..." +
				// SimpleResList.resourceAt(index).toString());
			}
			wonder[i].addGoldSign(3);
			double percent = (workoutY1(Ox, Oy + r, i, numOfPlayers) - Oy + r) / (2 * r);
			wonder[i].setLayoutX(workoutX1(Ox, Oy + r, i, numOfPlayers) - (0.6 * percent + 0.4) * 160);
			wonder[i].setLayoutY(workoutY1(Ox, Oy + r, i, numOfPlayers) - (0.6 * percent + 0.4) * 90);
			wonder[i].setScaleX(0.6 * percent + 0.4);
			wonder[i].setScaleY(0.6 * percent + 0.4);
			wonder[i].getChildren().add(rec[i]);
			final int index = i;
			wonder[i].addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					moveable = true;
					targetWonderIndex = index;
					for (int i = 0; i < numOfPlayers; i++) {
						Timeline tl = new Timeline(new KeyFrame(Duration.seconds(0.3), new KeyValue(wonder[i]
								.opacityProperty(), 1)));
						tl.play();
					}
				}

			});
			wonder[i].addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					if (!isDragging)
						moveable = false;

				}

			});

			wonder[i].getChildren().add(tpa[i]);
			tpa[i].setLayoutY(-80);
			tpa[i].setLayoutX(65);
			tpa[i].setScaleX(1 / (0.6 * percent + 0.4));
			tpa[i].setScaleY(1 / (0.6 * percent + 0.4));
		}

		dicClockwise();
		bigCircle = new Group();
		bigCircle.getChildren().add(
				MovingStroke.set(new Circle(29, Color.web("white", 0)), Color.web("white", 0.7), 6, 5, 10,
						0.3 * (age != 2 ? -1 : 1)));

		// bigCircle.setStroke(Color.web("white", 0.3));
		if (GUIManager.enableLightingEffect)
			bigCircle.setEffect(new Lighting());
		// bigCircle.setStrokeWidth(4);
		playerBoard.getChildren().add(bigCircle);

		for (int i = 0; i < numOfPlayers; i++) {
			wait[i] = new Circle(8);
			wait[i].setFill(color[i]);
			// look[i] = new Circle(4);
			// look[i].setFill(color[i]);
			// shop[i] = new Circle(5, Color.web("white", 0));
			// shop[i].setStroke(color[i]);
			// shop[i].setStrokeWidth(4);
			player[i] = wait[i];
			player[i].setVisible(true);
			if (GUIManager.enableLightingEffect)
				player[i].setEffect(new Lighting());
			player[i].setLayoutX(xForPlayer(i));
			player[i].setLayoutY(yForPlayer(i));
			player[i].addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent e) {
					// show the information of the players
				}
			});
			final int i1 = i;
			player[i].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent e) {
					if (!isTwice[i1]) {
						if (remable) {
							remable = false;
						}
						isTwice[i1] = true;
						for (int i = 0; i < numOfPlayers; i++)
							if (i != i1)
								isTwice[i] = false;
						handlerEnable = true;
						EventHandler<ActionEvent> act1 = new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								wonder[signrec].hideInfo();
							}
						};
						EventHandler<ActionEvent> act2 = new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								b.setVisible(false);
								// cardBoard.toFront();
								player[i1].setDisable(false);
							}
						};
						Timeline pre_tl = new Timeline(new KeyFrame(Duration.ZERO, act1), new KeyFrame(Duration
								.seconds(0.3), new KeyValue(b.opacityProperty(), 0.7), new KeyValue(cardBoard
								.layoutYProperty(), screenY - 105)), new KeyFrame(Duration.seconds(0.6), new KeyValue(b
								.opacityProperty(), 0)), new KeyFrame(Duration.seconds(0.6), act2));
						if (b.isVisible()) {
							player[i1].setDisable(true);
							pre_tl.play();
						}
						double[] forsort = new double[numOfPlayers];
						int[] sign = new int[numOfPlayers];
						for (int j = 0; j < numOfPlayers; j++) {
							double percent = (workoutY1(Ox, Oy + r, j - i1, numOfPlayers) - Oy + r) / (2 * r);
							Timeline tl = new Timeline(new KeyFrame(Duration.seconds(0.3), new KeyValue(wonder[j]
									.layoutXProperty(), workoutX1(Ox, Oy + r, j - i1, numOfPlayers)
									- (0.6 * percent + 0.4) * 160), new KeyValue(wonder[j].layoutYProperty(),
									workoutY1(Ox, Oy + r, j - i1, numOfPlayers) - (0.6 * percent + 0.4) * 90),
									new KeyValue(tpa[j].scaleXProperty(), 1 / (0.6 * percent + 0.4)), new KeyValue(
											tpa[j].scaleYProperty(), 1 / (0.6 * percent + 0.4)), new KeyValue(wonder[j]
											.scaleXProperty(), (0.6 * percent + 0.4)), new KeyValue(wonder[j]
											.scaleYProperty(), (0.6 * percent + 0.4))));
							// wonder[j].setLayoutX();
							// wonder[j].setLayoutY();
							// tpa[j].setScaleX();
							// tpa[j].setScaleY();
							// wonder[j].setScaleX();
							// wonder[j].setScaleY();
							// Path path = new Path();
							// Manager.debug(wonder[j].getLayoutX() +
							// ":" +
							// wonder[j].getLayoutY() + " " +
							// wonder[j].getTranslateX() + ":" +
							// wonder[j].getTranslateY());
							// double x = wonder[j].getLayoutX();
							// double y = wonder[j].getLayoutY();
							// path.getElements().addAll(new
							// MoveTo(screenX/2,screenY/2),
							// new ArcTo(R/2,r/2,0,workoutX1(Ox, Oy + r,
							// j - i1,
							// numOfPlayers) - x, workoutY1(Ox, Oy + r,
							// j - i1,
							// numOfPlayers) - y,true,true)
							// );
							// path.setStroke(color[j]);
							// path.setStrokeWidth(10);
							// root.getChildren().add(path);
							// PathTransition pt = new
							// PathTransition(Duration.seconds(8),path,wonder[j]);
							//
							// pt.setCycleCount(PathTransition.INDEFINITE);
							// pt.setAutoReverse(true);
							// pt.play();

							tl.play();
							forsort[j] = wonder[j].getLayoutY();
							sign[j] = j;
						}
						// wonder[i1].toFront();
						for (int i = 0; i < numOfPlayers - 1; i++) {
							for (int j = 0; j < numOfPlayers - i - 1; j++) {
								if (forsort[j] > forsort[j + 1]) {
									double temp = forsort[j + 1];
									forsort[j + 1] = forsort[j];
									forsort[j] = temp;
									int temp1 = sign[j + 1];
									sign[j + 1] = sign[j];
									sign[j] = temp1;
								}
							}
						}
						for (int i = 0; i < numOfPlayers; i++) {
							if (sign[i] == i1) {

								b.toFront();
							}
							wonder[sign[i]].toFront();
						}
						// if(!b.isVisible())
						cardBoard.toFront();
						playerBoard.toFront();
						// turn the wonderBoard
						if (Manager.getKernel().isReplayMode()) {
							int index = 0;
							if (age == 2)
								index = (2 * numOfPlayers - turn + i1 + 1) % numOfPlayers;
							else
								index = (turn + i1 - 1) % numOfPlayers;
							handCard.changeRole(boards[i1], hands[index]);
							observeIndex = i1;
						}
					} else {
						EventHandler<ActionEvent> act = new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								wonder[i1].showInfo();
								remable = true;
								signrec = i1;
								playerBoard.toFront();
								// show the game information of player
							}
						};
						for (int i = 0; i < numOfPlayers; i++)
							isTwice[i] = false;
						b.setOpacity(0);
						b.toFront();
						playerBoard.toFront();
						wonder[i1].toFront();
						b.setVisible(true);

						handlerEnable = false;
						Timeline tl = new Timeline(new KeyFrame(Duration.seconds(0.3), new KeyValue(
								b.opacityProperty(), 0.7), new KeyValue(cardBoard.layoutYProperty(), screenY)),
								new KeyFrame(Duration.seconds(0.3), act));
						tl.play();
					}
					theta = Math.PI / 2 - 2 * Math.PI / numOfPlayers * i1;
				}
			});
			playerBoard.getChildren().add(player[i]);
			judgeStateTimeline[i] = new Timeline(new KeyFrame(Duration.seconds(0.3), new KeyValue(
					player[i].opacityProperty(), 0)));
			judgeStateTimeline[i].setCycleCount(Timeline.INDEFINITE);
			judgeStateTimeline[i].setAutoReverse(true);
			buyStateCircle[i] = (Circle) MovingStroke.set(new Circle(xForPlayer(i), yForPlayer(i), 11,
					Color.TRANSPARENT), Color.GOLDENROD, 3, 3, 4, 0.3);
			playerBoard.getChildren().add(buyStateCircle[i]);
			buyStateCircle[i].setVisible(false);
		}
		buy = new BuyBoard();
		buy.setScaleX(0.7);
		buy.setScaleY(0.7);
		buy.setLayoutY(0);
		buy.setVisible(false);
		root.getChildren().add(buy);
		CardGroup.initializeData(screenX, buy, boards[0]);
		handCard = new CardGroup();
		handCard.setplayer(wonder[0]);
		for (int i = 0; i < numOfPlayers; i++) {
			resList[i] = new SimpleResList(boards[i].getResourceList());
		}
		/*
		 * primaryStage.addEventHandler(WindowEvent.RESIZE, new
		 * EventHandler<MouseEvent>(){
		 * 
		 * @Override public void handle(MouseEvent event) {
		 * System.out.println("OK");
		 * 
		 * }
		 * 
		 * }); primaryStage.
		 */

		scene.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (handlerEnable && moveable) {
					double x = e.getX(), y = e.getY();
					double tana = (y - Oy) / (x - Ox) * R / r;
					beta = Math.atan(tana);
					if (x <= Ox)
						beta = beta + Math.PI;
				}
			}
		});

		scene.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				isDragging = false;
				if (handlerEnable && moveable)
					theta = alpha - beta + theta;
			}

		});

		scene.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				isDragging = true;
				if (handlerEnable && moveable) {
					for (int i = 0; i < numOfPlayers; i++)
						isTwice[i] = false;
					double x = e.getX() - 70, y = e.getY();
					double[] forsort = new double[numOfPlayers];
					int[] sign = new int[numOfPlayers];
					for (int i = 0; i < numOfPlayers; i++) {
						// int index = (i +
						// numOfPlayers+targetWonderIndex+1) %
						// numOfPlayers;
						int index = i;
						double percent = (workoutY(x, y, i, numOfPlayers) - Oy + r) / (2 * r);
						tpa[index].setScaleX(1 / (0.6 * percent + 0.4));
						tpa[index].setScaleY(1 / (0.6 * percent + 0.4));
						wonder[index].setScaleX((0.6 * percent + 0.4));
						wonder[index].setScaleY((0.6 * percent + 0.4));
						wonder[index].setLayoutX(workoutX(x, y, i, numOfPlayers) - (0.6 * percent + 0.4) * 160);
						wonder[index].setLayoutY(workoutY(x, y, i, numOfPlayers) - (0.6 * percent + 0.4) * 90);
						forsort[i] = wonder[i].getLayoutY();
						sign[i] = i;
					}
					for (int i = 0; i < numOfPlayers - 1; i++) {
						for (int j = 0; j < numOfPlayers - i - 1; j++) {
							if (forsort[j] > forsort[j + 1]) {
								double temp = forsort[j + 1];
								forsort[j + 1] = forsort[j];
								forsort[j] = temp;
								int temp1 = sign[j + 1];
								sign[j + 1] = sign[j];
								sign[j] = temp1;
							}
						}
					}
					for (int i = 0; i < numOfPlayers; i++)
						wonder[sign[i]].toFront();
					playerBoard.toFront();
					cardBoard.toFront();
				}
			}
		});

		scene.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (handlerEnable) {
					double y = e.getX();
					// if( y > screenY - 牌宽)
					// 牌.show();
				}
			}

		});
		switch (numOfPlayers) {
		case 3:
			root.getChildren().add(wonder[2]);
			break;
		case 4:
			root.getChildren().add(wonder[3]);
			root.getChildren().add(wonder[2]);
			break;
		case 5:
			root.getChildren().add(wonder[3]);
			root.getChildren().add(wonder[2]);
			root.getChildren().add(wonder[4]);
			break;
		case 6:
			root.getChildren().add(wonder[3]);
			root.getChildren().add(wonder[2]);
			root.getChildren().add(wonder[4]);
			root.getChildren().add(wonder[5]);
			break;
		case 7:
			root.getChildren().add(wonder[3]);
			root.getChildren().add(wonder[2]);
			root.getChildren().add(wonder[4]);
			root.getChildren().add(wonder[5]);
			root.getChildren().add(wonder[6]);
		}

		root.getChildren().add(wonder[1]);
		root.getChildren().add(wonder[0]);

		playerBoard.setLayoutX(scene.getWidth() - 60);
		playerBoard.setLayoutY(50);
		root.getChildren().add(playerBoard);
		handCard.nextHand(hands[0], true);
		cardBoard = new Group();
		cardBoard.getChildren().add(handCard);
		root.getChildren().add(cardBoard);
		cardBoard.setLayoutX(0);
		cardBoard.setLayoutY(screenY - 105);
		cardBoard.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				if (wonderFadeTimeline != null)
					for (Timeline tl : wonderFadeTimeline)
						if (tl != null)
							tl.stop();
				wonderFadeTimeline = new Timeline[numOfPlayers];
				wonderFadeTimeline[observeIndex] = new Timeline(new KeyFrame(Duration.seconds(0.1), new KeyValue(
						cardBoard.layoutYProperty(), screenY - 270)));
				for (int i = 0; i < numOfPlayers; i++) {
					if (i == observeIndex)
						continue;
					if (wonder[i].getOpacity() > 0.99)
						wonderFadeTimeline[i] = new Timeline(new KeyFrame(Duration.seconds(0), new KeyValue(wonder[i]
								.opacityProperty(), 1)), new KeyFrame(Duration.seconds(1.5), new KeyValue(wonder[i]
								.opacityProperty(), 1)), new KeyFrame(Duration.seconds(2), new KeyValue(wonder[i]
								.opacityProperty(), i == (1 + observeIndex) % numOfPlayers
								|| i == (numOfPlayers - 1 + observeIndex) % numOfPlayers ? 0.65 : 0.35,
								Interpolator.EASE_IN)));
				}
				for (Timeline tl : wonderFadeTimeline)
					if (tl != null)
						tl.play();

				cardBoard.toFront();
				isIncard = true;
			}

		});
		cardBoard.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (wonderFadeTimeline != null)
					for (Timeline tl : wonderFadeTimeline)
						if (tl != null)
							tl.stop();
				wonderFadeTimeline = new Timeline[numOfPlayers];
				wonderFadeTimeline[0] = new Timeline(new KeyFrame(Duration.seconds(0.15), new KeyValue(cardBoard
						.layoutYProperty(), screenY - 105)));
				for (int i = 1; i < numOfPlayers; i++) {
					if (wonder[i].getOpacity() < 0.99)
						wonderFadeTimeline[i] = new Timeline(new KeyFrame(Duration.seconds(0), new KeyValue(wonder[i]
								.opacityProperty(), 0.5)), new KeyFrame(Duration.seconds(0.3), new KeyValue(wonder[i]
								.opacityProperty(), 1)));
				}
				for (Timeline tl : wonderFadeTimeline)
					if (tl != null)
						tl.play();
				isIncard = true;
			}

		});
		this.exitDialog = Index.loadExitGameDialog(root);
		root.getChildren().add(new GameMenu());

		// For Test
		for (int i = 0; i < numOfPlayers; i++) {
			updatePlayerState(i, 1);
		}
		// ScoreBoard sb = new ScoreBoard(boards);
		// root.getChildren().add(sb);
		// JOptionPane.showMessageDialog(null,
		// boards[0].getLeftNeighbor().getName() + ""
		// +boards[0].getRightNeighbor().getName());
		if (Manager.getKernel().isReplayMode()) {
			// Button nextBtn = new Button("Next!");
			// nextBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			// @Override
			// public void handle(MouseEvent event) {
			// nextTurn();
			// }
			// });
			// root.getChildren().add(nextBtn);
			root.getChildren().add(replayControl = new ReplayControl());
		}
	}

	private static double workoutX(double x, double y, int i, int allnum) {
		if (x != Ox) {
			double tana = (y - Oy) / (x - Ox) * R / r;
			alpha = Math.atan(tana);
			if (x <= Ox)
				alpha = alpha + Math.PI;
		} else
			alpha = Math.PI / 2;
		return (R * Math.cos(alpha - 2 * Math.PI * i / (allnum) + theta - beta) + Ox);
	}

	private static double workoutX1(double x, double y, int i, int allnum) {
		if (x != Ox) {
			double tana = (y - Oy) / (x - Ox) * R / r;
			alpha = Math.atan(tana);
			if (x <= Ox)
				alpha = alpha + Math.PI;
			System.out.println(alpha);
		} else
			alpha = Math.PI / 2;
		return (R * Math.cos(alpha - 2 * Math.PI * i / (allnum)) + Ox);
	}

	private static double workoutY(double x, double y, int i, int allnum) {
		if (x != Ox) {
			double tana = (y - Oy) / (x - Ox) * R / r;
			alpha = Math.atan(tana);
			if (x <= Ox)
				alpha = alpha + Math.PI;
		} else {
			alpha = Math.PI / 2;
		}
		return (r * Math.sin(alpha - 2 * Math.PI * i / (allnum) + theta - beta) + Oy);
	}

	private static double workoutY1(double x, double y, int i, int allnum) {
		if (x != Ox) {
			double tana = (y - Oy) / (x - Ox) * R / r;
			alpha = Math.atan(tana);
			if (x <= Ox)
				alpha = alpha + Math.PI;
		} else
			alpha = Math.PI / 2;
		return (r * Math.sin(alpha - 2 * Math.PI * i / (allnum)) + Oy);
	}

	private static double xForPlayer(int i) {
		return (30 * Math.cos(Math.PI / 2 - 2 * Math.PI * i / numOfPlayers));
	}

	private static double yForPlayer(int i) {
		return (30 * Math.sin(Math.PI / 2 - 2 * Math.PI * i / numOfPlayers));
	}

	public static void dicCounterClockwise() {
		counterClockwise0.setVisible(true);
		counterClockwise1.setVisible(true);
		counterClockwise2.setVisible(true);
		counterClockwise3.setVisible(true);
	}

	public static void dicClockwise() {
		counterClockwise0.setVisible(false);
		counterClockwise1.setVisible(false);
		counterClockwise2.setVisible(false);
		counterClockwise3.setVisible(false);
	}

	// public static void looking(int i) {
	// player[i] = look[i];
	// }
	//
	// public static void shopping(int i) {
	// player[i] = shop[i];
	// }
	//
	// public static void waiting(int i) {
	// player[i] = wait[i];
	// }

	public static void changeCardGivenUp(int i) {
		CardsGivenup = i;
		String s = String.valueOf(CardsGivenup);
		numOfCardGivenup.setText(s);
	}

	public static void updateAge(int age) {
		double rate = xrate;
		if (yrate < xrate)
			rate = yrate;
		if (age == 1) {
			Image stageI = ResManager.getImage("ph1.png");
			stageLabel = new ImageView();
			stageLabel.setImage(stageI);
			stageLabel.setScaleX(rate);
			stageLabel.setScaleY(rate);
			stageLabel.setTranslateX((stageI.getWidth() * xrate - 134) / 2.0 + 15);
			stageLabel.setTranslateY((stageI.getHeight() * yrate - 128) / 2.0);
			stageLabel.toBack();
			background.toBack();
			mbg.root.getChildren().add(stageLabel);
			stageLabel.setX(0);
			stageLabel.setY(0);
			Timeline tl = new Timeline(new KeyFrame(Duration.seconds(0), new KeyValue(stageLabel.opacityProperty(), 0),
					new KeyValue(stageLabel.scaleXProperty(), 0)), new KeyFrame(Duration.seconds(1), new KeyValue(
					stageLabel.opacityProperty(), 1), new KeyValue(stageLabel.scaleXProperty(), rate,
					Interpolator.EASE_OUT)));
			tl.play();
		} else if (age == 2) {
			GUIManager.bgMusic.stop();
			GUIManager.bgMusic = ResManager.getAudio("age2.mp3");
			GUIManager.bgMusic.setCycleCount(AudioClip.INDEFINITE);
			GUIManager.bgMusic.setVolume(GUIManager.volumn);
			GUIManager.bgMusic.play();
			final Image stageII = ResManager.getImage("ph2.png");
			EventHandler<ActionEvent> act = new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					stageLabel.setImage(stageII);
				}
			};
			Timeline tl = new Timeline(new KeyFrame(Duration.seconds(1), new KeyValue(stageLabel.opacityProperty(), 0),
					new KeyValue(stageLabel.scaleXProperty(), 0, Interpolator.EASE_IN)), new KeyFrame(
					Duration.seconds(1), act), new KeyFrame(Duration.seconds(2), new KeyValue(
					stageLabel.opacityProperty(), 1), new KeyValue(stageLabel.scaleXProperty(), rate,
					Interpolator.EASE_OUT)));
			tl.play();

		} else if (age == 3) {
			GUIManager.bgMusic.stop();
			GUIManager.bgMusic = ResManager.getAudio("age3.mp3");
			GUIManager.bgMusic.setCycleCount(AudioClip.INDEFINITE);
			GUIManager.bgMusic.setVolume(GUIManager.volumn);
			GUIManager.bgMusic.play();
			final Image stageIII = ResManager.getImage("ph3.png");
			EventHandler<ActionEvent> act = new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					stageLabel.setImage(stageIII);
				}
			};
			Timeline tl = new Timeline(new KeyFrame(Duration.seconds(1), new KeyValue(stageLabel.opacityProperty(), 0),
					new KeyValue(stageLabel.scaleXProperty(), 0, Interpolator.EASE_IN)), new KeyFrame(
					Duration.seconds(1), act), new KeyFrame(Duration.seconds(2), new KeyValue(
					stageLabel.opacityProperty(), 1), new KeyValue(stageLabel.scaleXProperty(), rate,
					Interpolator.EASE_OUT)));
			tl.play();
		}
	}

	public static void nextTurn() {
		try {
			// System.gc();//Maybe good or maybe very bad idea...-_-|||
			AITakeTurn(turn);
			// if(age >= 2)
			// JOptionPane.showMessageDialog(null,hands,"Hand Card List",JOptionPane.INFORMATION_MESSAGE);
			Manager.getKernel().doEndOfTurn(turn);
			updateBoard();
			// handCard.unload();
			if (turn < 6) {
				if (age == 2) {
					handCard.nextHand(hands[(2 * numOfPlayers - turn + observeIndex) % numOfPlayers], false);
					Manager.debug("Player is taking hands[" + (2 * numOfPlayers - turn) % numOfPlayers + "]");
				} else {
					handCard.nextHand(hands[(turn + observeIndex) % numOfPlayers], false);
					Manager.debug("Player is taking hands[" + (turn) % numOfPlayers + "]");
				}
				turn++;
			} else {
				changeCardGivenUp(CardsGivenup + numOfPlayers);
				Manager.getKernel().doEndOfAge(age);
				for (int i = 0; i < numOfPlayers; i++) {
					int[][] mvps = boards[i].getMilitaryVPS();
					// if(i == 0 )
					// JOptionPane.showMessageDialog(null,
					// "Left:"+mvps[KernelManager.LEFT][age] + "  Right:" +
					// mvps[KernelManager.RIGHT][age]);
					if (mvps[KernelManager.LEFT][age] > 0)
						wonder[i].addBattleSign(age, true);
					else if (mvps[KernelManager.LEFT][age] < 0)
						wonder[i].addBattleSign(0, true);
					if (mvps[KernelManager.RIGHT][age] > 0)
						wonder[i].addBattleSign(age, false);
					else if (mvps[KernelManager.RIGHT][age] < 0)
						wonder[i].addBattleSign(0, false);
					// JOptionPane.showMessageDialog(null,
					// mvps[KernelManager.LEFT][age] + "\t" +
					// mvps[KernelManager.RIGHT][age]);
				}
				if (age < 3) {
					age++;
					turn = 1;
					Manager.getKernel().startAge(age);
					bigCircle.getChildren().clear();
					bigCircle.getChildren().add(
							MovingStroke.set(new Circle(29, Color.web("white", 0)), Color.web("white", 0.7), 6, 5, 10,
									0.3 * (age != 2 ? -1 : 1)));
					hands = Manager.getKernel().getHands();
					handCard.nextHand(hands[0], true);
					if (age == 2) {
						updateAge(2);
						dicCounterClockwise();
					} else {
						updateAge(3);
						dicClockwise();
					}
				} else {// Game finished
					if (Manager.getKernel().isReplayMode())
						replayControl.stop();
					handCard.nextHand(null, true);
					Manager.getKernel().doEndOfGame();
					ScoreBoard sb = new ScoreBoard(boards);
					sb.setLayoutX((screenX - sb.getBoundsInLocal().getWidth()) / 2);
					sb.setLayoutY(-300);
					mbg.root.getChildren().add(sb);
					Manager.getKernel().saveRec(true);
					Timeline tl = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(sb.opacityProperty(), 0),
							new KeyValue(sb.layoutYProperty(), -300)), new KeyFrame(Duration.seconds(0.5),
							new KeyValue(sb.opacityProperty(), 1), new KeyValue(sb.layoutYProperty(), (screenY - sb
									.getBoundsInLocal().getHeight()) / 2)));
					tl.play();
					// quit();//Only for test
				}
			}
		} catch (Throwable e) {
			StringWriter str = new StringWriter();
			PrintWriter writer = new PrintWriter(str);
			e.printStackTrace(writer);
			Manager.error(e);
		}
	}

	private static void updateBoard() {
		for (int ii = 0; ii < numOfPlayers; ii++) {
			SimpleResList list = new SimpleResList(boards[ii].getResourceList());
			list.subtract(resList[ii]);
			for (int i = 1; i < 8; i++) {
				for (int j = 0; j < list.numAt(i); j++)
					wonder[ii].addResource(i);
			}
			resList[ii] = boards[ii].getResourceList();

			// ArrayList<SimpleResList> or = new
			// ArrayList<SimpleResList>(boards[ii].getOrList());
			// if(orList[ii] != null)
			// or.removeAll(orList[ii]);
			ArrayList<SimpleResList> or = boards[ii].getOrList();
			for (int i = orListNum[ii]; i < or.size(); i++) {
				// JOptionPane.showMessageDialog(null, or.get(i));
				SimpleResList oneOrList = or.get(i);
				int kind;
				if (oneOrList.numAt(1) == 1 && oneOrList.numAt(4) == 1 && oneOrList.numAt(2) == 0)
					kind = 8;
				else if (oneOrList.numAt(1) == 1 && oneOrList.numAt(3) == 1 && oneOrList.numAt(2) == 0)
					kind = 9;
				else if (oneOrList.numAt(1) == 1 && oneOrList.numAt(2) == 1 && oneOrList.numAt(3) == 0)
					kind = 10;
				else if (oneOrList.numAt(3) == 1 && oneOrList.numAt(4) == 1 && oneOrList.numAt(2) == 0)
					kind = 11;
				else if (oneOrList.numAt(2) == 1 && oneOrList.numAt(4) == 1 && oneOrList.numAt(3) == 0)
					kind = 12;
				else if (oneOrList.numAt(2) == 1 && oneOrList.numAt(3) == 1 && oneOrList.numAt(1) == 0)
					kind = 13;
				else if (oneOrList.numAt(5) == 1 && oneOrList.numAt(6) == 1 && oneOrList.numAt(7) == 1)
					kind = 14;
				else if (oneOrList.numAt(1) == 1 && oneOrList.numAt(2) == 1 && oneOrList.numAt(3) == 1
						&& oneOrList.numAt(4) == 1)
					kind = 15;
				else
					continue;
				wonder[ii].addResource(kind);
				orListNum[ii]++;
			}
			// SimpleResList or = new SimpleResList(sl)
			// if(ii == 0)
			// JOptionPane.showMessageDialog(null,
			// boards[ii].getTotalCoins()+":" + coins[ii]);
			wonder[ii].addGoldSign(boards[ii].getTotalCoins() - coins[ii]);
			coins[ii] = boards[ii].getTotalCoins();

			if (boards[ii].getStagesCompleted() > wonder[ii].getHasbuild()) {
				wonder[ii].stageCompleted(age);
			}

			// wonder[ii].toFront();// Must be wrong, but must be here!
			MainBackGround.order();
		}
		cardBoard.toFront();
		playerBoard.toFront();
	}

	public static void AITakeTurn(int turn) {
		int ind = 0;
		int start = 1;
		if (Manager.getKernel().isReplayMode()) {
			start = 0;
			ind = -1;
		}
		for (int i = start; i < numOfPlayers; i++) {
			Board board = boards[i];
			if (age == 2) {
				board.takeTurn(hands[(2 - turn + ind + 2 * numOfPlayers) % numOfPlayers], turn);
				Manager.debug("AI " + i + " takes hand[" + (2 - turn + ind + 2 * numOfPlayers) % numOfPlayers + "]");
			} else {
				// JOptionPane.showMessageDialog(null, hands[(ind + turn) %
				// numOfPlayers]);
				board.takeTurn(hands[(ind + turn) % numOfPlayers], turn);
				Manager.debug("AI " + i + " takes hand[" + (ind + turn) % numOfPlayers + "]");
			} // Q:
				// What
				// if
				// Age 2
				// ?

			ind++;
			// out[ind].append("\n" + board );
		}
	}

	public static void quit() {
		mbg.queryQuit();
	}

	public static void updatePlayerState(int index, int state) {
		judgeStateTimeline[index].stop();
		buyStateCircle[index].setVisible(false);
		if (state == 1)// Judging
			judgeStateTimeline[index].play();
		else if (state == 2) { // Buying
			buyStateCircle[index].setVisible(true);
			// Manager.debug(buyStateCircle[index].getLayoutX() +":"+
			// buyStateCircle[index].getLayoutX());
		} else {// Waiting
			// Do nothing
		}
	}

	public static void implementPlayerCommandOption(int index, CommandOption com) {

		if (com.getCommand().equals(Command.BUILD_CARD)) {
			wonder[index].addCard(CardGroup.cardNameMap.get(com.getCard().getName()), com.getCard().getColor());
		} else if (com.getCommand().equals(Command.SELL_CARD)) {
			wonder[index].discardCard(CardGroup.cardNameMap.get(com.getCard().getName()));
			addCardGivenUp(1);
		}
	}

	//
	// public static void markCard(int index, CommandOption com){
	// if(index == observeIndex){
	// handCard.markCard(com.getCard());
	// }
	// }

	public static void addCardGivenUp(int i) {
		changeCardGivenUp(getCardsGivenup() + i);
	}

	public static void order() {
		MyPair[] r = new MyPair[numOfPlayers];
		for (int i = 0; i < numOfPlayers; i++) {
			r[i] = new MyPair(i, wonder[i].getLayoutY());
		}
		for (int i = 0; i < numOfPlayers; i++) {
			for (int j = 0; j < numOfPlayers - i - 1; j++) {
				if (r[j].cooy > r[j + 1].cooy) {
					MyPair temp = r[j];
					r[j] = r[j + 1];
					r[j + 1] = temp;
				}
			}
		}
		switch (numOfPlayers) {
		case 3:
			wonder[r[2].num].toFront();
			break;
		case 4:
			wonder[r[3].num].toFront();
			wonder[r[2].num].toFront();
			break;
		case 5:
			wonder[r[3].num].toFront();
			wonder[r[2].num].toFront();
			wonder[r[4].num].toFront();
			break;
		case 6:
			wonder[r[3].num].toFront();
			wonder[r[2].num].toFront();
			wonder[r[4].num].toFront();
			wonder[r[5].num].toFront();
			break;
		case 7:
			wonder[r[3].num].toFront();
			wonder[r[2].num].toFront();
			wonder[r[4].num].toFront();
			wonder[r[5].num].toFront();
			wonder[r[6].num].toFront();
		}

		wonder[r[1].num].toFront();
		wonder[r[0].num].toFront();
	}

}

class MyPair {
	public int num;
	public double cooy;

	public MyPair(int i, double j) {
		num = i;
		cooy = j;
	}
}
