package org.tjuscs.sevenwonders.gui;

import java.io.File;
import java.util.Locale;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Duration;

import org.tjuscs.sevenwonders.LocalMessages;
import org.tjuscs.sevenwonders.Manager;

public class Index extends SceneContainer {

	private FadeTransition fadeTransition1, fadeTransition2, fadeTransition3;
	// private Group root = new Group();
	// private Font font = new Font(LocalMessages.getString("font"), 20);
	public static Font font;
	public static String style;
	private Timeline timeline;
	private InnerDialog newGameDialog = new InnerDialog(400, 300);
	private InnerDialog joinGameDialog = new InnerDialog(400, 300);
	private InnerDialog settingDialog;
	private InnerDialog replayGameDialog = new InnerDialog(400, 300);
	private InnerDialog creditsDialog;
	private static String playerName = Manager.getNet().getLocalName();
	private File record;
	private ImageView view;
	private Index self;

	// public static AudioClip bgMusic;
	private int choice;

	// Initialize label
	public void labelInit(Label label, double x, double y, boolean visible) {
		label.setTranslateX(x);
		label.setTranslateY(y);
		label.setVisible(visible);
	}

	// Set fade effect
	public void fadeSet(FadeTransition fade, double delayTime) {
		fade.setDelay(Duration.seconds(delayTime));
		fade.setFromValue(0.0f);
		fade.setToValue(1.0f);
	}

	public void setMouseEntered(final int i, Rectangle[] area, final Label label) {
		area[i].setOnMouseEntered(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				label.setVisible(true);
				Rectangle mouseArea = new Rectangle(38 - i * 5, 4 + i * 45, 173, 44);
				label.setClip(mouseArea);
				choice = i;
			}
		});
	}

	public void load() {
		choice = -1;
		self = this;
		double screenWidth = GUIManager.width;
		double screenHeight = GUIManager.height;
		// setLocale();
		scene = new Scene(root, GUIManager.width, GUIManager.height, GUIManager.bgColor);

		scene.setCursor(new ImageCursor(GUIManager.cursor));
		font = GUIManager.font;
		style = GUIManager.style;
		// Background
		Label backgroundLabel = new Label();

		if (screenWidth / screenHeight < 1.5)
			view = new ImageView(ResManager.getImage("indexbg4x3.jpg"));
		else
			view = new ImageView(ResManager.getImage("indexbg16x9.jpg"));
		if (!GUIManager.isFullScreen) {
			Rectangle rec = new Rectangle(screenWidth, screenHeight);
			// Rectangle back = new
			// Rectangle(screenWidth,screenHeight,Color.rgb(255, 255, 255,
			// 0.8));
			// root.getChildren().add(back);
			rec.setArcHeight(50);
			rec.setArcWidth(50);
			view.setClip(rec);
		}
		if (!GUIManager.isFullScreen && !Manager.isApplet)
			this.setDragBackground(root);
		// DropShadow ds = new DropShadow();
		// ds.setRadius(50);
		// ds.setColor(Color.RED);
		// ds.setOffsetX(30);
		// ds.setOffsetY(30);
		// if(GUIManager.enableDropShadowEffect)
		// view.setEffect(ds);
		GUIManager.simpleScale(view, GUIManager.width, GUIManager.height);
		backgroundLabel.setGraphic(view);
		backgroundLabel.setOpacity(0);
		root.getChildren().add(backgroundLabel);
		Text version = new Text(LocalMessages.getString("Version"));
		version.setFont(font);
		version.setFill(Color.WHITE);
		version.setTranslateX(20);
		version.setTranslateY(screenHeight - 20);
		version.setOpacity(0);
		if (GUIManager.enableDropShadowEffect)
			version.setEffect(new DropShadow());
		root.getChildren().add(version);
		FadeTransition fadeTransition0 = new FadeTransition(Duration.seconds(0.5), backgroundLabel);
		fadeTransition0.setFromValue(0);
		fadeTransition0.setToValue(1);
		// Menu Background
		Label itemBg_top = new Label();
		itemBg_top.setGraphic(new ImageView(ResManager.getImage("paper_top.png")));
		labelInit(itemBg_top, scene.getWidth() * 11 / 23.0, scene.getHeight() * 6 / 23.0, false);
		root.getChildren().add(itemBg_top);
		fadeTransition1 = new FadeTransition(Duration.seconds(0.5), itemBg_top);
		fadeSet(fadeTransition1, 1);

		Label itemBg_middle = new Label();
		ImageView middleView = new ImageView(ResManager.getImage("paper_middle.png"));
		Rectangle rect = new Rectangle(0, 0, 324, 0);
		middleView.setOpacity(0);
		middleView.setClip(rect);
		itemBg_middle.setGraphic(middleView);
		labelInit(itemBg_middle, scene.getWidth() * 11 / 23.0 - 17.0, scene.getHeight() * 6 / 23.0 + 75.0, true);
		root.getChildren().add(itemBg_middle);

		Label itemBg_foot = new Label();
		itemBg_foot.setGraphic(new ImageView(ResManager.getImage("paper_foot.png")));
		labelInit(itemBg_foot, scene.getWidth() * 11 / 23.0 - 10.0, scene.getHeight() * 6 / 23.0 + 75.0, false);
		root.getChildren().add(itemBg_foot);
		fadeTransition2 = new FadeTransition(Duration.seconds(0.5), itemBg_foot);
		fadeSet(fadeTransition2, 1);

		// Menu Item
		Label menuItem = new Label();
		menuItem.setGraphic(new ImageView(ResManager.getImage(LocalMessages.getString("NormalMenu"))));
		labelInit(menuItem, scene.getWidth() * 11 / 23.0 + 35.0, scene.getHeight() * 6 / 23.0 + 50.0, false);
		root.getChildren().add(menuItem);
		fadeTransition3 = new FadeTransition(Duration.seconds(0.5), menuItem);
		fadeSet(fadeTransition3, 2);

		final Label activeMenuItem = new Label();
		final ImageView activeMenuView = new ImageView(ResManager.getImage(LocalMessages.getString("MoveOnMenu")));
		activeMenuItem.setGraphic(activeMenuView);
		labelInit(activeMenuItem, scene.getWidth() * 11 / 23.0 + 35.0, scene.getHeight() * 6 / 23.0 + 50.0, false);
		root.getChildren().add(activeMenuItem);

		// React Mouse Area
		Rectangle[] area = new Rectangle[7];
		for (int i = 0; i < 7; ++i) {
			area[i] = new Rectangle(scene.getWidth() * 173 / 800.0, scene.getHeight() * 3 / 45.0);
			area[i].setTranslateX(scene.getWidth() * 9 / 16.0 - 5.0 * i);
			area[i].setTranslateY(scene.getHeight() * 211 / 600.0 + 45.0 * i);
			area[i].setFill(Color.TRANSPARENT);
			root.getChildren().add(area[i]);
			setMouseEntered(i, area, activeMenuItem);
			area[i].setOnMouseExited(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					activeMenuItem.setVisible(false);
				}
			});
			area[i].setOnMousePressed(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					activeMenuItem.setVisible(false);
				}
			});
			area[i].setOnMouseReleased(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					activeMenuItem.setVisible(true);
				}
			});
			area[i].setVisible(false);
		}

		// Menu function
		area[0].setOnMouseClicked(new EventHandler<MouseEvent>() { // New game
			@Override
			public void handle(MouseEvent event) {
				newGameDialog.show();
				event.consume();
			}
		});

		area[1].setOnMouseClicked(new EventHandler<MouseEvent>() { // Join game
			@Override
			public void handle(MouseEvent event) {
				// joinGameDialog.show();
				constructing();
				event.consume();
			}
		});

		area[2].setOnMouseClicked(new EventHandler<MouseEvent>() { // Game
																	// replay
			@Override
			public void handle(MouseEvent event) {
				replayGameDialog.show();
				event.consume();
			}
		});

		area[3].setOnMouseClicked(new EventHandler<MouseEvent>() { // Game rules
			@Override
			public void handle(MouseEvent event) {
				Manager.getGUI().showHelp();
				event.consume();
			}
		});

		area[4].setOnMouseClicked(new EventHandler<MouseEvent>() { // Game
																	// options
			@Override
			public void handle(MouseEvent event) {
				// if(Manager.isApplet){
				// showMessageDialog(root,
				// LocalMessages.getString("AppletNotAva"));
				// return;
				// }
				settingDialog.show();
				event.consume();
			}

		});

		area[5].setOnMouseClicked(new EventHandler<MouseEvent>() { // Game staff
			@Override
			public void handle(MouseEvent event) {
				creditsDialog.show();
				event.consume();
			}
		});

		area[6].setOnMouseClicked(new EventHandler<MouseEvent>() { // Exit game
			@Override
			public void handle(MouseEvent event) {
				queryQuit();
				event.consume();
			}
		});

		// Logo
		Label logoLabel = new Label();
		logoLabel.setTranslateX((scene.getWidth() - 400) / 2.0);
		logoLabel.setTranslateY(scene.getHeight() - 170.0);
		logoLabel.setGraphic(new ImageView(ResManager.getImage("logo.png")));
		root.getChildren().add(logoLabel);

		// Motion control
		timeline = new Timeline();
		timeline.getKeyFrames().addAll(
				new KeyFrame(Duration.ZERO,
						new KeyValue(logoLabel.translateXProperty(), (scene.getWidth() - 400) / 2.0), new KeyValue(
								logoLabel.translateYProperty(), scene.getHeight() - 170.0), new KeyValue(
								logoLabel.scaleXProperty(), 2.5), new KeyValue(logoLabel.scaleYProperty(), 2.5)),
				new KeyFrame(new Duration(300), new KeyValue(logoLabel.translateXProperty(),
						(scene.getWidth() - 1000) / 2.0), new KeyValue(logoLabel.translateYProperty(), scene
						.getHeight() - 100), new KeyValue(logoLabel.scaleXProperty(), 7.5), new KeyValue(logoLabel
						.scaleYProperty(), 7.5)),
				new KeyFrame(new Duration(1000), new KeyValue(logoLabel.translateXProperty(),
						(scene.getWidth() - 400) / 2.0), new KeyValue(logoLabel.translateYProperty(), 40),
						new KeyValue(logoLabel.scaleXProperty(), 1), new KeyValue(logoLabel.scaleYProperty(), 1),
						new KeyValue(itemBg_top.visibleProperty(), true), new KeyValue(itemBg_foot.visibleProperty(),
								true), new KeyValue(backgroundLabel.opacityProperty(), 0), new KeyValue(middleView
								.opacityProperty(), 0)),
				new KeyFrame(new Duration(1500), new KeyValue(itemBg_foot.translateXProperty(),
						scene.getWidth() * 11 / 23.0 - 10.0), new KeyValue(itemBg_foot.translateYProperty(), scene
						.getHeight() * 6 / 23.0 + 75.0), new KeyValue(rect.heightProperty(), 1), new KeyValue(version
						.opacityProperty(), 0), new KeyValue(middleView.opacityProperty(), 1)),
				new KeyFrame(new Duration(2000), new KeyValue(itemBg_foot.translateXProperty(),
						scene.getWidth() * 11 / 23.0 - 30.0), new KeyValue(itemBg_foot.translateYProperty(), scene
						.getHeight() * 6 / 23.0 + 340.0), new KeyValue(rect.heightProperty(), 265), new KeyValue(
						menuItem.visibleProperty(), true), new KeyValue(area[0].visibleProperty(), true), new KeyValue(
						area[1].visibleProperty(), true), new KeyValue(area[2].visibleProperty(), true), new KeyValue(
						area[3].visibleProperty(), true), new KeyValue(area[4].visibleProperty(), true), new KeyValue(
						area[5].visibleProperty(), true), new KeyValue(area[6].visibleProperty(), true), new KeyValue(
						backgroundLabel.opacityProperty(), 1), new KeyValue(version.opacityProperty(), 1)));

		// KeyEvents:
		root.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				// Manager.debug(event);
				if (event.getCode().equals(KeyCode.UP)) {
					if (choice >= 0) {
						choice = (choice - 1) % 7;
					} else
						choice = 6;
				} else if (event.getCode().equals(KeyCode.DOWN)) {
					if (choice >= 0) {
						choice = (choice + 1) % 7;
					} else
						choice = 0;
				} else if (choice >= 0 && event.getCode().equals(KeyCode.ENTER)) {
					switch (choice) {
					case 0:
						newGameDialog.show();
						break;
					case 1:
						constructing();
						break;
					case 2:
						replayGameDialog.show();
						break;
					case 3:
						Manager.getGUI().showHelp();
						break;
					case 4:
						settingDialog.show();
						break;
					case 5:
						creditsDialog.show();
						break;
					case 6:
						queryQuit();
						break;
					}
				}
				if (choice >= 0) {
					activeMenuItem.setVisible(true);
					Rectangle mouseArea = new Rectangle(38 - choice * 5, 4 + choice * 45, 173, 44);
					activeMenuItem.setClip(mouseArea);
				}
			}
		});
		root.requestFocus();

		// Load Dialogs:
		loadNewGameDialog();
		loadJoinGameDialog();
		loadExitGameDialog();
		loadReplayGameDialog();
		loadSettingDialog();
		loadCreditsDialog();

	}

	public void firstPlay() {
		timeline.play();
		fadeTransition1.play();
		fadeTransition2.play();
		fadeTransition3.play();
		// GUIManager.bgMusic.play();
	}

	public void loadExitGameDialog() {
		exitDialog = loadExitGameDialog(this.root);
	}

	public static InnerDialog loadExitGameDialog(final Group root) {
		final Group addRoot = root;
		final InnerDialog dg = new InnerDialog(400, 300);
		Text t1 = new Text(LocalMessages.getString("ExitYON"));
		t1.setFont(font);
		t1.setLayoutX(57);
		t1.setLayoutY(126);
		t1.setWrappingWidth(286);
		dg.getChildren().add(t1);

		final EventHandler<ActionEvent> h = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Manager.exit(0);
			}
		};

		Button btn = new Button(LocalMessages.getString("Yes"));
		btn.setFont(font);
		btn.setLayoutX(90);
		btn.setLayoutY(185);
		btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				// if (Manager.isApplet) {
				// showMessageDialog(addRoot,
				// LocalMessages.getString("AppletQuit"));
				// dg.close();
				// return;
				// }
				Parent root = GUIManager.getCurrentScene().getRoot();
				Timeline t = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(root.opacityProperty(), 1)),
						new KeyFrame(Duration.seconds(0.3), h, new KeyValue(root.opacityProperty(), 0)));
				t.play();
			}
		});
		btn.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER)) {
					// if (Manager.isApplet) {
					// showMessageDialog(addRoot,
					// LocalMessages.getString("AppletQuit"));
					// dg.close();
					// return;
					// }
					Parent root = GUIManager.getCurrentScene().getRoot();
					Timeline t = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(root.opacityProperty(), 1)),
							new KeyFrame(Duration.seconds(0.3), h, new KeyValue(root.opacityProperty(), 0)));
					t.play();
				}
			}
		});
		dg.getChildren().add(btn);

		Button btn2 = new Button(LocalMessages.getString("No"));
		btn2.setFont(font);
		btn2.setLayoutX(243);
		btn2.setLayoutY(185);
		btn2.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				dg.close();
				root.requestFocus();
			}
		});
		btn2.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER)) {
					dg.close();
				}
			}
		});
		dg.getChildren().add(btn2);

		dg.setLayoutX((root.getScene().getWidth() - 400) / 2);
		dg.setLayoutY((root.getScene().getHeight() - 300) / 2);
		root.getChildren().add(dg);
		dg.setDefaultFocus(btn2);

		return dg;
	}

	public void loadJoinGameDialog() {
		Text t1 = new Text(LocalMessages.getString("Host"));
		t1.setFont(font);
		t1.setLayoutX(70);
		t1.setLayoutY(94);
		joinGameDialog.getChildren().add(t1);

		final TextField host = new TextField("127.0.0.1");
		// port.setFont(font);
		host.setPrefColumnCount(11);
		host.setStyle(style);
		host.setLayoutX(76);
		host.setLayoutY(112);
		host.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ESCAPE)) {
					// InnerDialog s = (InnerDialog) event.getSource();
					joinGameDialog.close();
				}
			}
		});
		joinGameDialog.getChildren().add(host);

		Text t2 = new Text(LocalMessages.getString("Port"));
		t2.setFont(font);
		t2.setLayoutX(120);
		t2.setLayoutY(189);
		joinGameDialog.getChildren().add(t2);

		final TextField port = new TextField("7777");
		// port.setFont(font);
		port.setPrefColumnCount(3);
		port.setStyle(style);
		port.setLayoutX(188);
		port.setLayoutY(165);
		port.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ESCAPE)) {
					// InnerDialog s = (InnerDialog) event.getSource();
					joinGameDialog.close();
				}
			}
		});
		joinGameDialog.getChildren().add(port);

		Button btn = new Button(LocalMessages.getString("Start"));
		btn.setFont(font);
		btn.setLayoutX(165);
		btn.setLayoutY(235);
		btn.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				Manager.getGUI().joinRoom(host.getText(), Integer.parseInt(port.getText()));
			}
		});
		btn.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER)) {
					Manager.getGUI().joinRoom(host.getText(), Integer.parseInt(port.getText()));
				}
			}
		});
		joinGameDialog.getChildren().add(btn);

		joinGameDialog.setLayoutX((root.getScene().getWidth() - 400) / 2);
		joinGameDialog.setLayoutY((root.getScene().getHeight() - 300) / 2);
		joinGameDialog.setDefaultFocus(btn);
		root.getChildren().add(joinGameDialog);
	}

	public void loadNewGameDialog() {
		// Show an inner frame and ...
		Text t1 = new Text(LocalMessages.getString("PlayerNum"));
		t1.setFont(font);
		t1.setLayoutX(50);
		t1.setLayoutY(50);
		newGameDialog.getChildren().add(t1);

		final ChoiceBox<Integer> playerNum = new ChoiceBox<Integer>();
		playerNum.getItems().addAll(3, 4, 5, 6, 7);
		playerNum.getSelectionModel().selectFirst();
		playerNum.setLayoutX(253);
		playerNum.setLayoutY(23);
		playerNum.setStyle(style);
		newGameDialog.getChildren().add(playerNum);

		Text t2 = new Text(LocalMessages.getString("WonderType"));
		t2.setFont(font);
		t2.setLayoutX(50);
		t2.setLayoutY(95);
		newGameDialog.getChildren().add(t2);

		final ChoiceBox<String> wonderType = new ChoiceBox<String>();
		wonderType.getItems().addAll("A", "B", "A+B");
		wonderType.getSelectionModel().selectFirst();
		wonderType.setLayoutX(253);
		wonderType.setLayoutY(68);
		wonderType.setStyle(style);
		newGameDialog.getChildren().add(wonderType);

		Line l1 = new Line(25, 115, 375, 115);
		newGameDialog.getChildren().add(l1);

		Text t3 = new Text(LocalMessages.getString("LocalIP"));
		t3.setFont(font);
		t3.setLayoutX(50);
		t3.setLayoutY(143);
		newGameDialog.getChildren().add(t3);

		Text t4 = new Text("127.0.0.1");
		t4.setFont(font);
		t4.setLayoutX(70);
		t4.setLayoutY(168);
		newGameDialog.getChildren().add(t4);

		Text t5 = new Text(Manager.getNet().getLanIP() + "(" + LocalMessages.getString("Lan") + ")");
		t5.setFont(font);
		t5.setLayoutX(70);
		t5.setLayoutY(203);
		newGameDialog.getChildren().add(t5);

		Text t6 = new Text(LocalMessages.getString("Port"));
		t6.setFont(font);
		t6.setLayoutX(80);
		t6.setLayoutY(238);
		newGameDialog.getChildren().add(t6);

		final TextField port = new TextField("7777");
		// port.setFont(font);
		port.setPrefColumnCount(3);
		port.setStyle(style);
		port.setLayoutX(214);
		port.setLayoutY(209);
		port.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ESCAPE)) {
					// InnerDialog s = (InnerDialog) event.getSource();
					newGameDialog.close();
				}
			}
		});
		newGameDialog.getChildren().add(port);

		Button btn = new Button(LocalMessages.getString("Start"));
		btn.setFont(font);
		btn.setLayoutX(165);
		btn.setLayoutY(255);
		btn.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				Manager.getGUI()
						.createRoom(playerNum.getSelectionModel().getSelectedItem(),
								wonderType.getSelectionModel().getSelectedItem(), "127.0.0.1",
								Integer.parseInt(port.getText()));
			}
		});
		btn.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER))
					Manager.getGUI().createRoom(playerNum.getSelectionModel().getSelectedItem(),
							wonderType.getSelectionModel().getSelectedItem(), "127.0.0.1",
							Integer.parseInt(port.getText()));
			}
		});
		newGameDialog.getChildren().add(btn);

		newGameDialog.setLayoutX((root.getScene().getWidth() - 400) / 2);
		newGameDialog.setLayoutY((root.getScene().getHeight() - 300) / 2);
		root.getChildren().add(newGameDialog);
		newGameDialog.setDefaultFocus(btn);
	}

	public void loadSettingDialog() {
		// Timeline tl = new Timeline(new KeyFrame(Duration.ZERO,new
		// KeyValue(root.visibleProperty(),false)));
		settingDialog = new InnerDialog(400, 300);
		Text t1 = new Text(LocalMessages.getString("Volume"));
		t1.setFont(font);
		t1.setLayoutX(30);
		t1.setLayoutY(55);
		settingDialog.getChildren().add(t1);
		final Slider vs = new Slider(0, 10, 5);
		vs.setLayoutX(185);
		vs.setLayoutY(37);
		vs.setStyle("-fx-font: 20 arial");
		settingDialog.getChildren().add(vs);
		final Text t11 = new Text(String.valueOf((int) vs.getValue()));
		t11.setFont(font);
		t11.setLayoutX(340);
		t11.setLayoutY(55);
		settingDialog.getChildren().add(t11);
		vs.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				t11.setText(String.valueOf(((Double) new_val).intValue()));
			}
		});

		Text t2 = new Text(LocalMessages.getString("EffectLevel"));
		t2.setFont(font);
		t2.setLayoutX(30);
		t2.setLayoutY(70 + 20);
		settingDialog.getChildren().add(t2);
		final ChoiceBox<Integer> effectLevel = new ChoiceBox<Integer>();
		effectLevel.getItems().addAll(0, 1, 2, 3, 4);
		effectLevel.getSelectionModel().selectFirst();
		effectLevel.setLayoutX(194);
		effectLevel.setLayoutY(62);
		effectLevel.setStyle(style);
		effectLevel.getSelectionModel().selectLast();
		settingDialog.getChildren().add(effectLevel);

		Text t3 = new Text(LocalMessages.getString("ScreenSize"));
		t3.setFont(font);
		t3.setLayoutX(30);
		t3.setLayoutY(25 + 50 * 2);
		settingDialog.getChildren().add(t3);
		final ChoiceBox<String> screenSize = new ChoiceBox<String>();
		screenSize.getItems().addAll("800 * 600", "960 * 720", "1028 * 771", "1280 * 960", "960 * 540", "1120 * 630",
				"1280 * 720", "1366 * 768");
		screenSize.getSelectionModel().selectFirst();
		screenSize.setLayoutX(194);
		screenSize.setLayoutY(100);
		screenSize.setStyle(style);
		screenSize.getSelectionModel().select(5);
		if (Manager.isApplet)
			screenSize.setDisable(true);
		settingDialog.getChildren().add(screenSize);

		Text t4 = new Text(LocalMessages.getString("FullScreen"));
		t4.setFont(font);
		t4.setLayoutX(237);
		t4.setLayoutY(160);
		settingDialog.getChildren().add(t4);
		final CheckBox isFullScreen = new CheckBox();
		isFullScreen.setLayoutX(195);
		isFullScreen.setLayoutY(140);
		isFullScreen.setStyle(style);
		isFullScreen.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (!Manager.isApplet) {
					if (screenSize.isDisable())
						screenSize.setDisable(false);
					else
						screenSize.setDisable(true);
				}
			}
		});
		if (Manager.isApplet)
			isFullScreen.setDisable(true);
		settingDialog.getChildren().add(isFullScreen);

		String temp = LocalMessages.getString("Language");
		Text t5 = new Text(temp);
		t5.setFont(font);
		t5.setLayoutX(30);
		t5.setLayoutY(45 + 50 * 3);
		settingDialog.getChildren().add(t5);
		final ChoiceBox<String> language = new ChoiceBox<String>();
		if (temp.equals("Language"))
			language.getItems().addAll("Chinese", "English");
		else
			language.getItems().addAll("中文", "英文");
		language.getSelectionModel().selectFirst();
		language.setLayoutX(194);
		language.setLayoutY(175);
		language.setStyle(style);
		language.getSelectionModel().select(6);
		settingDialog.getChildren().add(language);

		Text t6 = new Text(LocalMessages.getString("LanName"));
		t6.setFont(font);
		t6.setLayoutX(30);
		t6.setLayoutY(40 + 50 * 4);
		settingDialog.getChildren().add(t6);

		final TextField lanName = new TextField(playerName);
		lanName.setPrefColumnCount(7);
		lanName.setStyle(style);
		lanName.setLayoutX(195);
		lanName.setLayoutY(215);
		lanName.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ESCAPE)) {
					// InnerDialog s = (InnerDialog) event.getSource();
					settingDialog.close();
				}
			}
		});
		settingDialog.getChildren().add(lanName);

		Button btn = new Button(LocalMessages.getString("Save"));
		btn.setFont(font);
		btn.setLayoutX(165);
		btn.setLayoutY(255);
		btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				playerName = lanName.getText();
				if (language.getSelectionModel().getSelectedIndex() == 0)
					LocalMessages.setLocale(new Locale("zh", "CN"));
				else
					LocalMessages.setLocale(new Locale("en", "US"));
				String screen = screenSize.getSelectionModel().getSelectedItem();
				String[] s = screen.split("\\*");
				GUIManager.width = Integer.parseInt(s[0].trim());
				GUIManager.height = Integer.parseInt(s[1].trim());
				// GUIManager.isFullScreen = isFullScreen.isSelected();
				if (!Manager.isApplet) {
					GUIManager.setFullScreen(isFullScreen.isSelected());
					// self.setDraggable(false);
					// view.setClip(null);

					// else{
					// self.setDraggable(true);
					// Rectangle rec = new Rectangle(GUIManager.width
					// ,GUIManager.height );
					// Rectangle back = new
					// Rectangle(screenWidth,screenHeight,Color.rgb(255, 255,
					// 255, 0.8));
					// root.getChildren().add(back);
					// rec.setArcHeight(50);
					// rec.setArcWidth(50);
					// view.setClip(rec);
					// }
				}
				int effect = effectLevel.getSelectionModel().getSelectedItem();
				GUIManager.enableLightingEffect = true;
				GUIManager.enableReflectionEffect = true;
				GUIManager.enableGlowEffect = true;
				GUIManager.enableDropShadowEffect = true;
				if (effect < 4)
					GUIManager.enableReflectionEffect = false;
				if (effect < 3)
					GUIManager.enableGlowEffect = false;
				if (effect < 2)
					GUIManager.enableDropShadowEffect = false;
				if (effect < 1)
					GUIManager.enableLightingEffect = false;
				GUIManager.volumn = vs.getValue() / 10.0;

				Manager.getGUI().restart();
			}
		});
		btn.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER)) {
					playerName = lanName.getText();
					if (language.getSelectionModel().getSelectedIndex() == 0)
						LocalMessages.setLocale(new Locale("zh", "CN"));
					else
						LocalMessages.setLocale(new Locale("en", "US"));
					String screen = screenSize.getSelectionModel().getSelectedItem();
					String[] s = screen.split("\\*");
					GUIManager.width = Integer.parseInt(s[0].trim());
					GUIManager.height = Integer.parseInt(s[1].trim());
					// GUIManager.isFullScreen = isFullScreen.isSelected();
					if (!Manager.isApplet) {
						GUIManager.setFullScreen(isFullScreen.isSelected());
					}
					int effect = effectLevel.getSelectionModel().getSelectedItem();
					GUIManager.enableLightingEffect = true;
					GUIManager.enableReflectionEffect = true;
					GUIManager.enableGlowEffect = true;
					GUIManager.enableDropShadowEffect = true;
					if (effect < 4)
						GUIManager.enableReflectionEffect = false;
					if (effect < 3)
						GUIManager.enableGlowEffect = false;
					if (effect < 2)
						GUIManager.enableDropShadowEffect = false;
					if (effect < 1)
						GUIManager.enableLightingEffect = false;
					GUIManager.volumn = vs.getValue() / 10.0;

					Manager.getGUI().restart();
				}
			}
		});
		settingDialog.getChildren().add(btn);

		// final EventHandler<ActionEvent> act = new EventHandler<ActionEvent>()
		// {
		// @Override
		// public void handle(ActionEvent event) {
		// //screenSize.getSelectionModel().select(GUIManager.width + " * "+
		// GUIManager.height);
		// //JOptionPane.showMessageDialog(null,
		// screenSize.getSelectionModel().getSelectedItem());
		// Manager.debug(12313432);
		// }
		// };
		// tl.getKeyFrames().add(new KeyFrame(Duration.ZERO, act));

		settingDialog.setLayoutX((root.getScene().getWidth() - 400) / 2);
		settingDialog.setLayoutY((root.getScene().getHeight() - 300) / 2);
		// joinGameDialog.setDefaultFocus(btn);
		root.getChildren().add(settingDialog);
		settingDialog.setDefaultFocus(lanName);
	}

	public void loadReplayGameDialog() {
		Text t1 = new Text(LocalMessages.getString("ReplayFile"));
		t1.setFont(font);
		t1.setLayoutX(50);
		t1.setLayoutY(94);
		t1.setWrappingWidth(300);
		replayGameDialog.getChildren().add(t1);

		final TextField recordFile = new TextField();
		recordFile.setPrefColumnCount(9);
		recordFile.setStyle(style);
		recordFile.setLayoutX(53);
		recordFile.setLayoutY(138);
		recordFile.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ESCAPE)) {
					// InnerDialog s = (InnerDialog) event.getSource();
					replayGameDialog.close();
				}
			}
		});
		replayGameDialog.getChildren().add(recordFile);

		Button open = new Button(LocalMessages.getString("Browse"));
		open.setFont(font);
		open.setLayoutX(258);
		open.setLayoutY(137);
		final FileChooser fileChoose = new FileChooser();
		open.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				fileChoose.setTitle(LocalMessages.getString("ReplayFile"));
				File file = new File(System.getProperty("user.dir") + "/rec");
				if (file.exists())
					fileChoose.setInitialDirectory(file);
				else {
					if (Manager.isApplet)
						file = new File(System.getProperty("user.home"));
					else
						file = new File(System.getProperty("user.dir"));
					fileChoose.setInitialDirectory(file);
				}
				fileChoose.getExtensionFilters().add(new ExtensionFilter(LocalMessages.getString("RecFile"), "*.xml"));
				record = fileChoose.showOpenDialog(null);
				if (record != null) {
					recordFile.setText(record.getName());

				}
			}
		});
		open.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER)) {
					fileChoose.setTitle(LocalMessages.getString("ReplayFile"));
					File file = new File(System.getProperty("user.dir") + "/rec");
					if (file.exists())
						fileChoose.setInitialDirectory(file);
					else {
						if (Manager.isApplet)
							file = new File(System.getProperty("user.home"));
						else
							file = new File(System.getProperty("user.dir"));
						fileChoose.setInitialDirectory(file);
					}
					fileChoose.getExtensionFilters().add(
							new ExtensionFilter(LocalMessages.getString("RecFile"), "*.rec"));
					record = fileChoose.showOpenDialog(null);
					if (record != null) {
						recordFile.setText(record.getName());
						// Manager.getKernel().loadRecord(record);//Load
						// instantly...
					}
				}
			}
		});
		replayGameDialog.getChildren().add(open);

		Button btn = new Button(LocalMessages.getString("Start"));
		btn.setFont(font);
		btn.setLayoutX(155);
		btn.setLayoutY(200);
		btn.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				replayGameDialog.close();
				if (record != null)
					Manager.getGUI().replayRoom(record);
			}
		});
		btn.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER)) {
					replayGameDialog.close();
					if (record != null)
						Manager.getGUI().replayRoom(record);

				}
			}
		});
		replayGameDialog.getChildren().add(btn);

		replayGameDialog.setLayoutX((root.getScene().getWidth() - 400) / 2);
		replayGameDialog.setLayoutY((root.getScene().getHeight() - 300) / 2);

		root.getChildren().add(replayGameDialog);
		replayGameDialog.setDefaultFocus(open);
	}

	public void loadCreditsDialog() {

		int fontSize = Integer.parseInt(LocalMessages.getString("FontSizeForCreditsList"));
		String fontName = LocalMessages.getString("FontForCreditsList");
		Font font = ResManager.getFont(fontName, fontSize);
		Font bigFont = ResManager.getFont(fontName, fontSize + 8);
		Text head = new Text();
		head.setFont(font);
		int headX = 150;
		head.setLayoutY(230 + 100);
		head.setLayoutX(headX);
		ImageView logo = new ImageView();
		Image logoImage = ResManager.getImage("tju.png");
		logo.setImage(logoImage);
		logo.setLayoutX((700 - logoImage.getWidth()) / 2);
		logo.setLayoutY((500 - logoImage.getWidth()) / 2 - 50);
		Text text = new Text(
				"Team Leader:\t\t\t戴玮(Dai Wei)\n\n"
						+ "Advisor:\t\t\t\t罗凯(Lonnie Heinke)\n\n"
						+ "GUI Leader:\t\t\t包扬(Bao Yang)\n\n"
						+ "Kernel Leader:\t\t\t蒋星韬(Jiang Xingtao)\n\n"
						+ "Design Leader:\t\t秦星耀(Qin Xingyao)\n\n"
						+ "Network Leader:\t\t初显奇(Chu Xianqi)\n\n"
						+ "Art Designer:\t\t\t高峰(Gao Feng)\n\n"
						+ "Technology Support:\t苗宇锴(Miao Yukai)\n\n"
						+ "\n\t\t\t    GUI Group\n\t\t\t     图形界面组\n\n\t\t\t孙丽婷(Sun Liting)\n\t\t\t刘科含(Liu Kehan)\n\t\t\t林舒婷(Lin Shuting)\n\t\t\t路冬英(Lu Dongying)\n\t\t\t杨艳阳(Yang Yanyang)\n\t\t\t赵雅慧(Zhao Yahui)\n\n"
						+ "\n\t\t\t    Kernel Group\n\t\t\t         内核组\n\n\t\t\t樊向宇(Fan Xiangyu)\n\t\t\t唐新亮(Tang Xinliang)\n\t\t\t王旸(Wang Yang)\n\n"
						+ "\n\t\t\t    Design Group\n\t\t\t         设计组\n\n\t\t\t高晓妮(Gao Xiaoni)\n\t\t\t李智宇(Li Zhiyu)\n\t\t\t王雅芳(Wang Yafang)\n\t\t\t张梦迪(Zhang Mengdi)\n\n"
						+ "\n\t\t\t    Network Group\n\t\t\t         网络组\n\n\t\t\t吴岩(Wu Yan)\n\t\t\t杨天培(Yang Tianpei)\n\t\t\t谢丽娟(Xie Lijuan)\n\n\n");
		text.setLayoutX(50);
		text.setLayoutY(100);
		text.setFont(font);
		text.setWrappingWidth(700 - 100);
		Rectangle clip = new Rectangle(0, -50, 600, 400);
		text.setClip(clip);
		double height = text.prefHeight(700 - 100) + 50;
		// System.out.println(height);

		// EventHandler<ActionEvent> setMusic = new EventHandler<ActionEvent>()
		// {
		// @Override
		// public void handle(ActionEvent event) {
		// GUIManager.playMusic("credits.mp3");
		// }
		// };
		//
		// EventHandler<ActionEvent> setBackMusic = new
		// EventHandler<ActionEvent>() {
		// @Override
		// public void handle(ActionEvent event) {
		// GUIManager.playMusic("menu.mp3");
		// }
		// };

		// Timeline setBack = new Timeline(new
		// KeyFrame(Duration.ZERO,setBackMusic));

		Timeline scroll = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(clip.layoutYProperty(), 0),
				new KeyValue(text.layoutYProperty(), 100), new KeyValue(text.opacityProperty(), 0), new KeyValue(
						head.opacityProperty(), 0), new KeyValue(head.textProperty(),
						"\t天大10软工 7Wonders小组   出品\n\nTJU-SCS-2010 7Wonders Team   Presents"), new KeyValue(
						head.layoutXProperty(), headX - 50), new KeyValue(head.fontProperty(), font), new KeyValue(
						logo.opacityProperty(), 0), new KeyValue(head.layoutYProperty(), 230 + 100)), new KeyFrame(
				Duration.seconds(1), new KeyValue(head.opacityProperty(), 1), new KeyValue(logo.opacityProperty(), 1)),
				new KeyFrame(Duration.seconds(2), new KeyValue(head.opacityProperty(), 1), new KeyValue(logo
						.opacityProperty(), 1)), new KeyFrame(Duration.seconds(3), new KeyValue(head.opacityProperty(),
						0), new KeyValue(logo.opacityProperty(), 0), new KeyValue(head.textProperty(),
						"    队长\tTeam Leader\n\n    戴玮\t(Dai Wei)"), new KeyValue(head.fontProperty(), bigFont),
						new KeyValue(head.layoutYProperty(), 230 + 100), new KeyValue(head.layoutXProperty(),
								headX - 50)), new KeyFrame(Duration.seconds(3), new KeyValue(head.layoutYProperty(),
						230), new KeyValue(head.layoutXProperty(), headX + 30)), new KeyFrame(Duration.seconds(4),
						new KeyValue(head.opacityProperty(), 1)), new KeyFrame(Duration.seconds(5), new KeyValue(
						head.opacityProperty(), 1), new KeyValue(head.layoutXProperty(), headX)), new KeyFrame(
						Duration.seconds(6), new KeyValue(head.opacityProperty(), 0), new KeyValue(
								head.layoutXProperty(), headX + 30), new KeyValue(head.textProperty(),
								"    指导老师\tAdvisor\n\n    Lonnie Heinke\t(罗凯)")), new KeyFrame(Duration.seconds(7),
						new KeyValue(head.opacityProperty(), 1)), new KeyFrame(Duration.seconds(8), new KeyValue(
						head.opacityProperty(), 1), new KeyValue(head.layoutXProperty(), headX)), new KeyFrame(
						Duration.seconds(9), new KeyValue(head.opacityProperty(), 0), new KeyValue(
								head.layoutXProperty(), headX + 30), new KeyValue(head.textProperty(),
								"  图形界面组领队\tGUI Leader\n\n  包扬\t\t(Bao Yang)")), new KeyFrame(Duration.seconds(10),
						new KeyValue(head.opacityProperty(), 1)), new KeyFrame(Duration.seconds(11), new KeyValue(
						head.opacityProperty(), 1), new KeyValue(head.layoutXProperty(), headX)), new KeyFrame(
						Duration.seconds(12), new KeyValue(head.opacityProperty(), 0), new KeyValue(
								head.layoutXProperty(), headX + 30), new KeyValue(head.textProperty(),
								"内核组领队\tKernel Leader\n\n蒋星韬\t(Jiang Xingtao)")), new KeyFrame(Duration.seconds(13),
						new KeyValue(head.opacityProperty(), 1)), new KeyFrame(Duration.seconds(14), new KeyValue(
						head.opacityProperty(), 1), new KeyValue(head.layoutXProperty(), headX)), new KeyFrame(
						Duration.seconds(15), new KeyValue(head.opacityProperty(), 0), new KeyValue(
								head.layoutXProperty(), headX + 30), new KeyValue(head.textProperty(),
								"设计组领队\tDesign Leader\n\n秦星耀\t(Qin Xingyao)")), new KeyFrame(Duration.seconds(16),
						new KeyValue(head.opacityProperty(), 1)), new KeyFrame(Duration.seconds(17), new KeyValue(
						head.opacityProperty(), 1), new KeyValue(head.layoutXProperty(), headX)), new KeyFrame(
						Duration.seconds(18), new KeyValue(head.opacityProperty(), 0), new KeyValue(
								head.layoutXProperty(), headX + 30), new KeyValue(head.textProperty(),
								"网络组领队\tNetwork Leader\n\n初显奇\t(Chu Xianqi)")), new KeyFrame(Duration.seconds(19),
						new KeyValue(head.opacityProperty(), 1)), new KeyFrame(Duration.seconds(20), new KeyValue(
						head.opacityProperty(), 1), new KeyValue(head.layoutXProperty(), headX)), new KeyFrame(
						Duration.seconds(21), new KeyValue(head.opacityProperty(), 0), new KeyValue(
								head.layoutXProperty(), headX + 30), new KeyValue(head.textProperty(),
								"美工设计\tArt Designer\n\n高峰\t\t(Gao Feng)")), new KeyFrame(Duration.seconds(22),
						new KeyValue(head.opacityProperty(), 1)), new KeyFrame(Duration.seconds(23), new KeyValue(
						head.opacityProperty(), 1), new KeyValue(head.layoutXProperty(), headX)), new KeyFrame(
						Duration.seconds(24), new KeyValue(head.opacityProperty(), 0), new KeyValue(
								head.layoutXProperty(), headX + 30), new KeyValue(head.textProperty(),
								"技术支持\tTech. Support\n\n苗宇锴\t(Miao Yukai)")), new KeyFrame(Duration.seconds(25),
						new KeyValue(head.opacityProperty(), 1)), new KeyFrame(Duration.seconds(26), new KeyValue(
						head.opacityProperty(), 1), new KeyValue(head.layoutXProperty(), headX)), new KeyFrame(
						Duration.seconds(27), new KeyValue(head.opacityProperty(), 0), new KeyValue(
								head.layoutXProperty(), headX + 30)),

				new KeyFrame(Duration.seconds(27), new KeyValue(clip.layoutYProperty(), 0), new KeyValue(
						text.layoutYProperty(), 100), new KeyValue(text.opacityProperty(), 0)), new KeyFrame(
						Duration.seconds(27.3), new KeyValue(text.opacityProperty(), 1)), new KeyFrame(
						Duration.seconds(30), new KeyValue(clip.layoutYProperty(), 0), new KeyValue(
								text.layoutYProperty(), 100)), new KeyFrame(Duration.seconds(57), new KeyValue(
						clip.layoutYProperty(), height - 400), new KeyValue(text.layoutYProperty(),
						100 - (height - 400)), new KeyValue(text.opacityProperty(), 1)), new KeyFrame(
						Duration.seconds(57.3), new KeyValue(text.opacityProperty(), 0)));
		scroll.setCycleCount(Timeline.INDEFINITE);
		scroll.setAutoReverse(false);

		creditsDialog = new InnerDialog(700, 500, scroll);
		creditsDialog.getChildren().addAll(head, text, logo);
		creditsDialog.setLayoutX((root.getScene().getWidth() - 700) / 2);
		creditsDialog.setLayoutY((root.getScene().getHeight() - 500) / 2);
		root.getChildren().add(creditsDialog);
	}

	public void constructing() {
		showMessageDialog(root, LocalMessages.getString("Constructing"));
	}

	public static void showMessageDialog(Group root, String message) {
		InnerDialog dg = new InnerDialog(400, 300);
		Text t1 = new Text(message);
		t1.setFont(font);
		t1.setLayoutX(80);
		t1.setLayoutY(94);
		t1.setWrappingWidth(240);
		dg.getChildren().add(t1);
		Scene scene = root.getScene();
		dg.setLayoutX((scene.getWidth() - 400) / 2);
		dg.setLayoutY((scene.getHeight() - 300) / 2);

		root.getChildren().add(dg);
		dg.show();
	}

	// Main method
	// public static void main(String[] args) {
	// launch(args);
	// }
}
