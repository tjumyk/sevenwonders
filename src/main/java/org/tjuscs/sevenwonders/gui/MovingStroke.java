package org.tjuscs.sevenwonders.gui;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CircleBuilder;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.RectangleBuilder;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.shape.StrokeType;
import javafx.util.Duration;

public class MovingStroke {
	public static Shape set(Shape s, Paint color, double width, double lineLength, double spaceLength,
			double secendsPerFrame) {
		if (s instanceof Rectangle)
			RectangleBuilder.create().strokeDashArray(lineLength, spaceLength).applyTo(s);
		else if (s instanceof Circle)
			CircleBuilder.create().strokeDashArray(lineLength, spaceLength).applyTo(s);

		s.setStroke(color);
		s.setStrokeDashOffset(0);
		s.setStrokeLineCap(StrokeLineCap.ROUND);
		s.setStrokeLineJoin(StrokeLineJoin.ROUND);
		s.setStrokeType(StrokeType.CENTERED);
		s.setStrokeWidth(width);
		if (secendsPerFrame > 0) {
			Timeline tl = new Timeline(new KeyFrame(Duration.seconds(secendsPerFrame), new KeyValue(
					s.strokeDashOffsetProperty(), lineLength + spaceLength)));
			tl.setCycleCount(Timeline.INDEFINITE);
			tl.play();
		} else if (secendsPerFrame < 0) {
			Timeline tl = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(s.strokeDashOffsetProperty(),
					lineLength + spaceLength)), new KeyFrame(Duration.seconds(-secendsPerFrame), new KeyValue(
					s.strokeDashOffsetProperty(), 0)));
			tl.setCycleCount(Timeline.INDEFINITE);
			tl.play();
		}
		return s;
	}
}
