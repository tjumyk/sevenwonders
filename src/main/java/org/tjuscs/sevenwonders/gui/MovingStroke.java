package org.tjuscs.sevenwonders.gui;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;
import javafx.util.Duration;

public class MovingStroke {
    public static Shape set(Shape s, Paint color, double width, double lineLength, double spaceLength,
                            double secendsPerFrame) {
        if (s instanceof Rectangle || s instanceof Circle) {
            ObservableList<Double> dashArray = s.getStrokeDashArray();
            dashArray.clear();
            dashArray.addAll(lineLength, spaceLength);
        }

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
