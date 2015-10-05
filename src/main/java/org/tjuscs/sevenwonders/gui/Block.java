package org.tjuscs.sevenwonders.gui;

import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Block extends Parent {
	Block(double screenX, double screenY) {
		Rectangle rec = new Rectangle(screenX, screenY, Color.web("black", 0.7));
		rec.setX(0);
		rec.setY(0);
		getChildren().add(rec);
		setCache(true);
	}
}
