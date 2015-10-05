package org.tjuscs.sevenwonders.gui;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;

public class DropGroup extends Group {
	protected double initX;
	protected double initY;
	protected Point2D dragAnchor;

	public DropGroup() {
		addListeners();
	}

	private void addListeners() {
		this.setOnMouseDragged(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				DropGroup s = (DropGroup) me.getSource();
				double dragX = me.getSceneX() - dragAnchor.getX();
				double dragY = me.getSceneY() - dragAnchor.getY();
				// calculate new position of the circle
				double newXPosition = initX + dragX;
				double newYPosition = initY + dragY;
				s.setTranslateX(newXPosition);
				s.setTranslateY(newYPosition);

				me.consume();
			}
		});
		final Group self = this;
		this.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				DropGroup s = (DropGroup) me.getSource();
				initX = s.getTranslateX();
				initY = s.getTranslateY();
				dragAnchor = new Point2D(me.getSceneX(), me.getSceneY());
				self.toFront();
			}
		});
	}

}
