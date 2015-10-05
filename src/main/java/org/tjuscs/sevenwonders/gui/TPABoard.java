package org.tjuscs.sevenwonders.gui;

import java.util.TreeMap;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import org.tjuscs.sevenwonders.Manager;

public class TPABoard extends Group {
	public static void initialize() {
		int[] arr = { 3, 3, 2, 2, 5, 3, 4, 7, 5, 6, 8, 4, 6 };
		for (int i = 28; i <= 40; i++)
			civilPointMap.put(i, arr[i - 28]);
		int[] arr2 = { 1, 1, 1, 2, 2, 3, 3, 3, 2, 2, 3 };
		for (int i = 41; i <= 51; i++)
			armyPointMap.put(i, arr2[i - 41]);
	}

	public TPABoard() {
		Image image = ResManager.getImage("centerstat.png");
		main = new ImageView();
		main.setImage(image);
		civil = new Label("0");
		if (Manager.getGUI().enableGlowEffect)
			civil.setEffect(new Glow());
		army = new Label("0");
		if (Manager.getGUI().enableGlowEffect)
			army.setEffect(new Glow());
		civil.setFont(Font.font("Arial", 36));
		army.setFont(Font.font("Arial", 36));
		civil.setLayoutX(221);
		civil.setLayoutY(26);
		civil.setTextFill(Color.web("#EEEEEE"));
		army.setLayoutX(311);
		army.setLayoutY(26);
		army.setTextFill(Color.web("#EEEEEE"));
		getChildren().add(main);
		getChildren().add(civil);
		getChildren().add(army);
	}

	public void addCivil(int cardID) {
		int i = civilPointMap.get(cardID);
		i += Integer.parseInt(civil.getText());
		if (i >= 10)
			civil.setTranslateX(-10);
		String s = Integer.toString(i);
		civil.setText(s);
	}

	public void addArmy(int cardID) {
		int i = armyPointMap.get(cardID);
		i += Integer.parseInt(army.getText());
		if (i >= 10)
			army.setTranslateX(-10);
		String s = Integer.toString(i);
		// JOptionPane.showMessageDialog(null, cardID+"\t+" +
		// armyPointMap.get(cardID) + "\tTotal:"+i);
		army.setText(s);
	}

	public void clear() {
		civil.setText("0");
		army.setText("0");
		civil.setTranslateX(0);
		army.setTranslateX(0);
	}

	private ImageView main;
	private Label civil;
	private Label army;
	public static TreeMap<Integer, Integer> civilPointMap = new TreeMap<Integer, Integer>();
	public static TreeMap<Integer, Integer> armyPointMap = new TreeMap<Integer, Integer>();
}
