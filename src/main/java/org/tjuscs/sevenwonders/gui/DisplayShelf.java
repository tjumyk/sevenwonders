package org.tjuscs.sevenwonders.gui;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.PerspectiveTransform;
import javafx.scene.effect.ReflectionBuilder;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.tjuscs.sevenwonders.LocalMessages;
import org.tjuscs.sevenwonders.Manager;

/**
 * A display shelf of images using the PerspectiveTransform effect.
 */
public class DisplayShelf extends SceneContainer {

    public void load() {
        try {
            // Group root = new Group();
            if (!GUIManager.isFullScreen && !Manager.isApplet)
                setDragBackground(root);
            // load images
            Image[] images = new Image[12];
            // images[0] = new Image("file:res/image/demo.png");

            for (int i = 0; i < 12; i++) {
                images[i] = ResManager.getImage("manual (" + (i + 1) + ").png");
            }
            // create display shelf
            Shelf displayShelf = new Shelf(images);
            displayShelf.setPrefSize(GUIManager.width, GUIManager.height);
            root.getChildren().add(displayShelf);
            scene = new Scene(root, GUIManager.width, GUIManager.height, GUIManager.bgColor);
            scene.setCursor(new ImageCursor(GUIManager.cursor));
            scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                public void handle(KeyEvent event) {
                    if (event.getCode().equals(KeyCode.ESCAPE)) {
                        Manager.getGUI().showMenu();
                    }
                }
            });
            scene.setOnMousePressed(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    if (event.getButton().equals(MouseButton.SECONDARY)) {
                        Manager.getGUI().showMenu();
                    }
                }
            });

            this.exitDialog = Index.loadExitGameDialog(root);

            Text text = new Text(LocalMessages.getString("ExitRuleScene"));
            text.setFont(GUIManager.font);
            if (GUIManager.enableDropShadowEffect)
                text.setEffect(new DropShadow());
            Timeline tl = new Timeline(new KeyFrame(Duration.seconds(0.8), new KeyValue(text.opacityProperty(), 0)));
            tl.setAutoReverse(true);
            tl.setCycleCount(Timeline.INDEFINITE);
            text.setLayoutX(GUIManager.width / 2 - 100);
            text.setLayoutY(GUIManager.height - 30);
            root.getChildren().add(text);
            tl.play();
        } catch (Throwable error) {
            Manager.error(error);
        }
    }

    /**
     * A ui control which displays a browseble display shlef of images
     */
    public static class Shelf extends Region {
        private static final Duration DURATION = Duration.millis(500);
        private static final Interpolator INTERPOLATOR = Interpolator.EASE_BOTH;
        private static final double SPACING = 50;
        private static final double LEFT_OFFSET = -110;
        private static final double RIGHT_OFFSET = 110;
        private static final double SCALE_SMALL = 0.7;
        private PerspectiveImage[] items;
        private Group centered = new Group();
        private Group left = new Group();
        private Group center = new Group();
        private Group right = new Group();
        private int centerIndex = 0;
        private Timeline timeline;
        private ScrollBar scrollBar = new ScrollBar();
        // private Text text;
        private boolean localChange = false;
        private Rectangle clip = new Rectangle();

        public Shelf(Image[] images) {
            // set clip
            setClip(clip);
            // set background gradient using css
            // setStyle("-fx-background-color: linear-gradient(to bottom," +
            // " black 60, #141414 60.1%, black 100%);");
            // style scroll bar color
            scrollBar.setId("DisplayShelfScrollBar");
            // create items
            items = new PerspectiveImage[images.length];
            for (int i = 0; i < images.length; i++) {
                final PerspectiveImage item = items[i] = new PerspectiveImage(images[i]);
                final double index = i;
                item.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent me) {
                        localChange = true;
                        scrollBar.setValue(index);
                        localChange = false;
                        shiftToCenter(item);
                    }
                });
            }
            // text = new Text(LocalMessages.getString("ESCToQuit"));
            // text.setFont(GUIManager.font);
            // this.getChildren().add(text);
            // text.setLayoutX((GUIManager.width - 200) /2);
            // text.setLayoutY(GUIManager.height);
            // setup scroll bar
            scrollBar.setMax(items.length - 1);
            scrollBar.setVisibleAmount(1);
            scrollBar.setUnitIncrement(1);
            scrollBar.setBlockIncrement(1);
            scrollBar.setOpacity(0.3);
            // final Timeline appear = new Timeline(new
            // KeyFrame(Duration.seconds(0.3),new
            // KeyValue(scrollBar.opacityProperty(),1)));
            // final Timeline disappear = new Timeline(new
            // KeyFrame(Duration.seconds(0.3),new
            // KeyValue(scrollBar.opacityProperty(),0.3)));
            scrollBar.setOnMouseEntered(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    // appear.play();
                    scrollBar.setOpacity(1);
                }
            });
            scrollBar.setOnMouseExited(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    // disappear.play();
                    scrollBar.setOpacity(0.3);
                }
            });

            scrollBar.valueProperty().addListener(new InvalidationListener() {
                public void invalidated(Observable ov) {
                    if (!localChange)
                        shiftToCenter(items[(int) scrollBar.getValue()]);
                }
            });
            // create content
            centered.getChildren().addAll(left, right, center);
            getChildren().addAll(centered, scrollBar);
            // listen for keyboard events
            setFocusTraversable(true);
            setOnKeyPressed(new EventHandler<KeyEvent>() {
                public void handle(KeyEvent ke) {
                    if (ke.getCode() == KeyCode.LEFT) {
                        shift(1);
                        localChange = true;
                        scrollBar.setValue(centerIndex);
                        localChange = false;
                    } else if (ke.getCode() == KeyCode.RIGHT) {
                        shift(-1);
                        localChange = true;
                        scrollBar.setValue(centerIndex);
                        localChange = false;
                    }
                }
            });
            // update
            update();
        }

        @Override
        protected void layoutChildren() {
            // update clip to our size
            clip.setWidth(getWidth());
            clip.setHeight(getHeight());
            // keep centered centered
            centered.setLayoutY((getHeight() - PerspectiveImage.HEIGHT) / 2);
            centered.setLayoutX((getWidth() - PerspectiveImage.WIDTH) / 2);
            // position scroll bar at bottom
            scrollBar.setLayoutX(10);
            scrollBar.setLayoutY(getHeight() - 25);
            scrollBar.resize(getWidth() - 20, 15);
        }

        private void update() {
            // move items to new homes in groups
            left.getChildren().clear();
            center.getChildren().clear();
            right.getChildren().clear();
            for (int i = 0; i < centerIndex; i++) {
                left.getChildren().add(items[i]);
            }
            center.getChildren().add(items[centerIndex]);
            for (int i = items.length - 1; i > centerIndex; i--) {
                right.getChildren().add(items[i]);
            }
            // stop old timeline if there is one running
            if (timeline != null)
                timeline.stop();
            // create timeline to animate to new positions
            timeline = new Timeline();
            // add keyframes for left items
            final ObservableList<KeyFrame> keyFrames = timeline.getKeyFrames();
            for (int i = 0; i < left.getChildren().size(); i++) {
                final PerspectiveImage it = items[i];
                double newX = -left.getChildren().size() * SPACING + SPACING * i + LEFT_OFFSET;
                keyFrames.add(new KeyFrame(DURATION, new KeyValue(it.translateXProperty(), newX, INTERPOLATOR),
                        new KeyValue(it.scaleXProperty(), SCALE_SMALL, INTERPOLATOR), new KeyValue(it.scaleYProperty(),
                        SCALE_SMALL, INTERPOLATOR), new KeyValue(it.angle, 45.0, INTERPOLATOR)));
            }
            // add keyframe for center item
            final PerspectiveImage centerItem = items[centerIndex];
            keyFrames.add(new KeyFrame(DURATION, new KeyValue(centerItem.translateXProperty(), 0, INTERPOLATOR),
                    new KeyValue(centerItem.scaleXProperty(), 1.0, INTERPOLATOR), new KeyValue(centerItem
                    .scaleYProperty(), 1.0, INTERPOLATOR), new KeyValue(centerItem.angle, 90.0, INTERPOLATOR)));
            // add keyframes for right items
            for (int i = 0; i < right.getChildren().size(); i++) {
                final PerspectiveImage it = items[items.length - i - 1];
                final double newX = right.getChildren().size() * SPACING - SPACING * i + RIGHT_OFFSET;
                keyFrames.add(new KeyFrame(DURATION, new KeyValue(it.translateXProperty(), newX, INTERPOLATOR),
                        new KeyValue(it.scaleXProperty(), SCALE_SMALL, INTERPOLATOR), new KeyValue(it.scaleYProperty(),
                        SCALE_SMALL, INTERPOLATOR), new KeyValue(it.angle, 135.0, INTERPOLATOR)));
            }
            // play animation
            timeline.play();
        }

        private void shiftToCenter(PerspectiveImage item) {
            for (int i = 0; i < left.getChildren().size(); i++) {
                if (left.getChildren().get(i) == item) {
                    int shiftAmount = left.getChildren().size() - i;
                    shift(shiftAmount);
                    return;
                }
            }
            if (center.getChildren().get(0) == item) {
                return;
            }
            for (int i = 0; i < right.getChildren().size(); i++) {
                if (right.getChildren().get(i) == item) {
                    int shiftAmount = -(right.getChildren().size() - i);
                    shift(shiftAmount);
                    return;
                }
            }
        }

        public void shift(int shiftAmount) {
            if (centerIndex <= 0 && shiftAmount > 0)
                return;
            if (centerIndex >= items.length - 1 && shiftAmount < 0)
                return;
            centerIndex -= shiftAmount;
            update();
        }
    }

    /**
     * A Node that displays a image with some 2.5D perspective rotation around
     * the Y axis.
     */
    public static class PerspectiveImage extends Parent {
        private static final double REFLECTION_SIZE = 0.05;
        private static final double WIDTH = GUIManager.height;
        private static final double HEIGHT = GUIManager.height;
        private static final double RADIUS_H = WIDTH / 2;
        private static final double BACK = WIDTH / 10;
        private PerspectiveTransform transform = new PerspectiveTransform();
        /**
         * Angle Property
         */
        private final DoubleProperty angle = new SimpleDoubleProperty(45) {
            @Override
            protected void invalidated() {
                // when angle changes calculate new transform
                double lx = (RADIUS_H - Math.sin(Math.toRadians(angle.get())) * RADIUS_H - 1);
                double rx = (RADIUS_H + Math.sin(Math.toRadians(angle.get())) * RADIUS_H + 1);
                double uly = (-Math.cos(Math.toRadians(angle.get())) * BACK);
                double ury = -uly;
                transform.setUlx(lx);
                transform.setUly(uly);
                transform.setUrx(rx);
                transform.setUry(ury);
                transform.setLrx(rx);
                transform.setLry(HEIGHT + uly);
                transform.setLlx(lx);
                transform.setLly(HEIGHT + ury);
            }
        };

        public final double getAngle() {
            return angle.getValue();
        }

        public final void setAngle(double value) {
            angle.setValue(value);
        }

        public final DoubleProperty angleModel() {
            return angle;
        }

        public PerspectiveImage(Image image) {
            ImageView imageView = new ImageView(image);
            imageView.setEffect(ReflectionBuilder.create().fraction(REFLECTION_SIZE).build());
            setEffect(transform);
            Rectangle rec = new Rectangle(image.getWidth(), image.getHeight());
            rec.setArcHeight(150);
            rec.setArcWidth(150);
            imageView.setClip(rec);
            getChildren().addAll(imageView);
        }
    }

}
