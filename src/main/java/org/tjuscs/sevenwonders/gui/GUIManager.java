package org.tjuscs.sevenwonders.gui;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import org.tjuscs.sevenwonders.LocalMessages;
import org.tjuscs.sevenwonders.Manager;
import org.tjuscs.sevenwonders.net.NetManager;

public class GUIManager {
	public static double width = 1120;
	// public static int width = 1366,height = 768;
	public static double height = 630;
	public static boolean isFullScreen = false;
	// public static int width = 1600,height = 900;

	private static GUIManager gui = null;
	protected static double initX;
	protected static double initY;
	protected static Point2D dragAnchor;

	public static Font font, bigFont;
	public static String style;
	public static Image cursor;

	private static Stage stage;
	private static SceneContainer menuScene;
	private static SceneContainer mainScene;
	private static SceneContainer helpScene;

	public static boolean enableLightingEffect = true;
	public static boolean enableReflectionEffect = true;
	public static boolean enableGlowEffect = true;
	public static boolean enableDropShadowEffect = true;
	public static boolean enableSmooth = false;

	public static boolean restarted = false;

	public static Color bgColor;
	public static AudioClip bgMusic;
	public static double volumn = 0.5;

	public void init() {
		if (!restarted) {
			stage.setResizable(false);
			stage.setTitle("7Wonders");
			setNoFrame(stage);
			stage.getIcons().add(ResManager.getImage("icon.png"));
		}
		loadInitialScenes();
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				if (stage.getScene() == menuScene.getScene())
					menuScene.queryQuit();

				if (stage.getScene() == helpScene.getScene())
					helpScene.queryQuit();

				if (mainScene != null && stage.getScene() == mainScene.getScene())
					mainScene.queryQuit();
				event.consume();
			}
		});

		// loadMusic("menu.mp3");

		// Because of the Black-Strap problem, not really fullscreen? Oh, now I
		// chose no.
		if (isFullScreen)
			stage.setFullScreen(true);
		else
			stage.setFullScreen(false);

		stage.setScene(menuScene.getScene());
		if (!Manager.isApplet) {
			stage.setHeight(height);
			stage.setWidth(width);
			stage.centerOnScreen();
		}
		menuScene.play();
	}

	private void setNoFrame(Stage stage2) {
		// stage.initStyle(StageStyle.UNDECORATED);
		stage.initStyle(StageStyle.TRANSPARENT);
		// setDraggable(mainScene.getScene());
		// setDraggable(menuScene.getScene());
		// setDraggable(helpScene.getScene());
	}

	private void loadInitialScenes() {
		try {
			setFontCursor();
			if (bgColor == null)
				if (Manager.isApplet)
					bgColor = Color.WHITE;
				else
					bgColor = Color.TRANSPARENT;
			// else if(Manager.isApplet)
			// bgColor = Color.TRANSPARENT;
			helpScene = null;
			helpScene = new DisplayShelf();
			menuScene = null;
			menuScene = new Index();
			helpScene.load();
			menuScene.load();
		} catch (Throwable error) {
			Manager.error(error);
		}
	}

	private void loadMainScene() {
		try {
			mainScene = null;
			mainScene = new MainBackGround();
			mainScene.load();
		} catch (Throwable error) {
			Manager.error(error);
		}
	}

	private GUIManager() {
		new ResManager();
	}

	public static Scene getCurrentScene() {
		if (stage.getScene() == menuScene.getScene())
			return menuScene.getScene();

		if (stage.getScene() == helpScene.getScene())
			return helpScene.getScene();

		if (mainScene != null && stage.getScene() == mainScene.getScene())
			return mainScene.getScene();
		else
			return null;
	}

	public static GUIManager getManager() {
		if (gui == null) {
			gui = new GUIManager();
		}
		return gui;
	}

	public void start(Stage stage) {
		playMusic("menu.mp3");
		GUIManager.stage = stage;
		init();
		Manager.hidePreloader();
		stage.show();
	}

	public void restart() {
		restarted = true;
		final EventHandler<ActionEvent> h = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				playMusic("menu.mp3");
				stage.hide();
				// stage.close();
				// stage = new Stage();
				init();
				stage.show();
			}
		};
		Parent root = GUIManager.getCurrentScene().getRoot();
		Timeline t = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(bgMusic.volumeProperty(), volumn),
				new KeyValue(root.opacityProperty(), 1)), new KeyFrame(Duration.seconds(0.3), h, new KeyValue(
				bgMusic.volumeProperty(), 0), new KeyValue(root.opacityProperty(), 0)));
		t.play();
	}

	public void startAge(int age) {
		Manager.getKernel().startAge(age);
	}

	public void startTurn(int turn) {
		try {
			Manager.getKernel().startTurn(turn);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doEndOfTurn(int turn) {
		//
	}

	public void doEndOfAge(int age) {
		//
	}

	public void showGameResult() {
		//
	}

	public boolean continueGame() {
		System.out.println("Do you want to Continue");
		return false;
	}

	public void createRoom(int playerNum, String wonderType, String host, int port) {
		Manager.info("Room Created:[" + playerNum + "Players," + wonderType + "," + host + ":" + port + "]");

		Manager.getKernel().setNumOfPlayers(playerNum);
		// For Test!
		Manager.getKernel().setBoards(wonderType);
		Manager.getKernel().setAIPlayers();
		Manager.getKernel().initializeGame();

		loadMainScene();
		showRoom();
	}

	// public void createRoom(int playerNum, String wonderType, String host, int
	// port) {
	// Manager.info("Room Created:[" + playerNum + "Players," + wonderType + ","
	// + host + ":" + port + "]");
	// NetManager.isHost = true;
	// NetManager.start();
	//
	// loadMainScene();
	// showRoom();
	// }

	public void joinRoom(String host, int port) {
		Manager.info("Room Joined:[" + host + ":" + port + "]");
		NetManager.isHost = false;
		NetManager.start();

		loadMainScene();
		showRoom();
	}

	public void replayRoom(File record) {
		Manager.getKernel().loadRecord(record);
		loadMainScene();
		showRoom();
	}

	public void showRoom() {
		changeScene(mainScene);
	}

	public void changeScene(final SceneContainer newScene) {
		final Parent root = stage.getScene().getRoot();
		// stage.getScene().setFill(Color.BLACK);
		FadeTransition ft = new FadeTransition(Duration.seconds(0.3), root);
		ft.setFromValue(1);
		ft.setToValue(0);
		ft.play();
		final FadeTransition ft2 = new FadeTransition(Duration.seconds(0.3), newScene.getScene().getRoot());
		ft2.setFromValue(0);
		ft2.setToValue(1);
		EventHandler<ActionEvent> onFinished = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				stage.setScene(newScene.getScene());
				ft2.play();
				newScene.play();
				if (newScene instanceof MainBackGround) {
					playMusic("age1.mp3");
				} else {
					playMusic("menu.mp3");
				}
			}
		};

		Timeline tl = new Timeline(new KeyFrame(Duration.seconds(0.3), onFinished));
		tl.play();
	}

	public void showHelp() {
		changeScene(helpScene);
	}

	public void showMenu() {
		changeScene(menuScene);
	}

	private void setDraggable(final Scene scene) {
		scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				double dragX = me.getSceneX() - dragAnchor.getX();
				double dragY = me.getSceneY() - dragAnchor.getY();
				// calculate new position of the circle
				double newXPosition = initX + dragX;
				double newYPosition = initY + dragY;
				stage.setX(newXPosition);
				stage.setY(newYPosition);
				Manager.debug("Stage Position Changed: [" + newXPosition + "," + newYPosition + "]");
				me.consume();
			}
		});
		scene.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				initX = stage.getX();
				initY = stage.getY();
				dragAnchor = new Point2D(me.getSceneX(), me.getSceneY());
				me.consume();
			}
		});
	}

	public static Point2D simpleScale(Node node, double newX, double newY) {
		double x = node.getBoundsInLocal().getWidth();
		double y = node.getBoundsInLocal().getHeight();
		node.setScaleX(newX / x);
		node.setScaleY(newY / y);
		double lx = node.getTranslateX();
		double ly = node.getTranslateY();
		node.setTranslateX((newX - x) / 2 + lx);
		node.setTranslateY((newY - y) / 2 + ly);
		return new Point2D(newX / x, newY / y);
	}

	private void setFontCursor() {
		List<String> fontList = Font.getFamilies();
		// Manager.debug(fontList);
		// Manager.debug(Font.getDefault());
		int fontSize = Integer.parseInt(LocalMessages.getString("FontSize"));
		font = ResManager.getFont(LocalMessages.getString("FontName"), fontSize);
		bigFont = ResManager.getFont(LocalMessages.getString("FontName"), fontSize + 8);
		if (fontList.contains("Microsoft YaHei"))
			style = "-fx-font: " + (fontSize - 2) + " \"Microsoft YaHei\"";
		else if (fontList.contains("Arial Unicode MS"))
			style = "-fx-font: " + (fontSize - 2) + " \"Arial Unicode MS\"";
		else
			style = "-fx-font: " + (fontSize - 2) + " System";

		cursor = ResManager.getImage("arrow.png");
	}

	public static void playMusic(String musicFileName) {
		loadMusic(musicFileName);
		bgMusic.play();
	}

	public static void loadMusic(String musicFileName) {
		if (bgMusic != null)
			bgMusic.stop();
		bgMusic = ResManager.getAudio(musicFileName);
		bgMusic.setCycleCount(AudioClip.INDEFINITE);
		bgMusic.setVolume(volumn);
	}

	public static void setFullScreen(boolean isFullScreen) {
		if (isFullScreen) {
			Rectangle2D primaryScreenBounds = Screen.getPrimary().getBounds();
			stage.setX(primaryScreenBounds.getMinX());
			stage.setY(primaryScreenBounds.getMinY());
			stage.setWidth(primaryScreenBounds.getWidth());
			stage.setHeight(primaryScreenBounds.getHeight());
			GUIManager.isFullScreen = true;
			width = primaryScreenBounds.getWidth();
			height = primaryScreenBounds.getHeight();
		} else {
			GUIManager.isFullScreen = false;
		}
	}

	public static void disable(boolean disabled) {
		final Group root = (Group) getCurrentScene().getRoot();
		if (disabled) {
			Rectangle rec = new Rectangle(GUIManager.width, GUIManager.height, Color.BLACK);
			rec.setOpacity(0);
			root.getChildren().add(rec);
			new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(rec.opacityProperty(), 0)), new KeyFrame(
					Duration.seconds(3.0), new KeyValue(rec.opacityProperty(), 0.75), new KeyValue(
							root.disableProperty(), true))).play();
			// root.setDisable(true);
		} else {
			final Rectangle rec = (Rectangle) root.getChildren().get(root.getChildren().size() - 1);
			EventHandler<ActionEvent> act = new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					root.getChildren().remove(rec);
				}
			};
			new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(rec.opacityProperty(), 0.75)), new KeyFrame(
					Duration.seconds(0.3), act, new KeyValue(rec.opacityProperty(), 0), new KeyValue(
							root.disableProperty(), false))).play();
		}
	}

	public static void capture() {
		try {
			Robot robot = new Robot();
			Scene scene = getCurrentScene();
			java.awt.Rectangle rect = new java.awt.Rectangle((int) scene.getWindow().getX(), (int) scene.getWindow()
					.getY(), (int) scene.getWidth(), (int) scene.getHeight());
			BufferedImage image = robot.createScreenCapture(rect);

		} catch (AWTException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
