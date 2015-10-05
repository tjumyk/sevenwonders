package org.tjuscs.sevenwonders.gui;

import javafx.event.EventHandler;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import org.tjuscs.sevenwonders.kernel.CardColor;

public class CardPoint extends Circle {
	public CardPoint(int k, CardColor c, Wonders w) {
		num = k;
		color = c;
		wonder = w;
		iv = new ImageView(ResManager.getImage(k + ".jpg"));
		iv.setScaleX(0.7);
		iv.setScaleY(0.7);
		setEffect(new DropShadow());
		addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				// show the information of the players
				wonder.getChildren().add(iv);
				iv.setTranslateX(getLayoutX() - 78);
				iv.setTranslateY(getLayoutY() - 160);
				toFront();
				setOpacity(0);
				isout = false;
			}
		});
		addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				// show the information of the players
				// wonder.getChildren().remove(iv);
				isout = true;
			}
		});
		iv.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				// show the information of the players
			}
		});
		iv.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				// show the information of the players
				if (isout) {
					wonder.getChildren().remove(iv);
					setOpacity(1);
				}
			}
		});
		setStrokeWidth(1);
		if (c == CardColor.YELLOW) {
			setFill(Color.YELLOW);
			setStroke(Color.YELLOW.darker());
		} else if (c == CardColor.BLUE) {
			setFill(Color.BLUE);
			setStroke(Color.BLUE.darker());
		} else if (c == CardColor.BROWN) {
			setFill(Color.BROWN);
			setStroke(Color.BROWN.darker());
		} else if (c == CardColor.GREEN) {
			setFill(Color.GREEN);
			setStroke(Color.GREEN.darker());
		} else if (c == CardColor.GREY) {
			setFill(Color.GRAY);
			setStroke(Color.GREY.darker());
		} else if (c == CardColor.PURPLE) {
			setFill(Color.PURPLE);
			setStroke(Color.PURPLE.darker());
		} else if (c == CardColor.RED) {
			setFill(Color.RED);
			setStroke(Color.RED.darker());
		}
		setRadius(4);
	}

	private boolean isin = false;
	private CardColor color;
	private int num;
	private ImageView iv = new ImageView();
	private Wonders wonder;
	private boolean isout = true;
}
