package com.example.newyorktimesspellingbee.components;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.Cursor;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class BeehiveCell extends StackPane {
    private String cellValue;
    private Polygon polygon;
    private final double edgeWidth;
    private final Color backgroundColor;
    private final Color outputColor;
    private Text cellText;
    private ScaleTransition shrinkTransition;
    private ScaleTransition growTransition;

    private FadeTransition fadeInTransition;
    private FadeTransition fadeOutTransition;
    private SequentialTransition clickAnimation;
    private SequentialTransition shuffleAnimation;
    private boolean clickAnimationFlag = true;

    /**
     * Constructs a BeehiveCell with specified value, edge width, background color, and output color.
     *
     * @param cellValue The value (letter) of the cell.
     * @param edgeWidth The width of the edges of the hexagonal cell.
     * @param backgroundColor The background color of the cell.
     * @param outputColor The color used for output (text) in the cell.
     */
    public BeehiveCell(String cellValue, double edgeWidth, Color backgroundColor, Color outputColor) {
        this.cellValue = cellValue;
        this.edgeWidth = edgeWidth;
        this.backgroundColor = backgroundColor;
        this.outputColor = outputColor;

        init();
        initAnimations();
    }

    /**
     * Initializes the cell's shape, color, and text.
     */
    private void init() {
        polygon = new Polygon(0.0, 0.0,
                edgeWidth, 0.0,
                1.5 * edgeWidth, 0.5 * edgeWidth * Math.sqrt(3),
                edgeWidth, edgeWidth * Math.sqrt(3),
                0.0, edgeWidth * Math.sqrt(3),
                -0.5 * edgeWidth, 0.5 * edgeWidth * Math.sqrt(3));

        setPickOnBounds(false);

        cellText = new Text(cellValue);
        cellText.setPickOnBounds(false);
        cellText.getStyleClass().add("cell-text");

        polygon.setStroke(Color.WHITE);
        polygon.setStrokeWidth(7.5);
        polygon.setFill(backgroundColor);
        polygon.setCursor(Cursor.HAND);
        getChildren().addAll(polygon, cellText);
    }

    /**
     * Initializes animations for the cell, including click and shuffle animations.
     */
    private void initAnimations() {
        fadeInTransition = new FadeTransition(Duration.millis(300), cellText);
        fadeInTransition.setToValue(1);

        fadeOutTransition = new FadeTransition(Duration.millis(10), cellText);
        fadeOutTransition.setToValue(0);

        shuffleAnimation = new SequentialTransition(fadeOutTransition, fadeInTransition);

        shrinkTransition = new ScaleTransition(Duration.millis(100), polygon);
        shrinkTransition.setToX(0.8);
        shrinkTransition.setToY(0.8);

        growTransition = new ScaleTransition(Duration.millis(100), polygon);
        growTransition.setToX(1);
        growTransition.setToY(1);

        clickAnimation = new SequentialTransition(shrinkTransition, growTransition);
        clickAnimation.setOnFinished(e -> clickAnimationFlag = true);
    }


    /**
     * Plays the click animation for the cell.
     * The animation involves a shrinking and growing effect.
     */
    public void playClickAnimation() {
        if (clickAnimationFlag) {
            clickAnimationFlag = false;
            clickAnimation.play();
        }
    }

    /**
     * Plays the shuffle animation for the cell.
     * The animation involves a fade-out and fade-in effect.
     */
    public void playShuffleAnimation() {
        shuffleAnimation.play();
    }

    /**
     * Gets the output color of the cell. This is the color used for the text displayed in the cell.
     *
     * @return The Color object representing the output color of the cell.
     */
    public Color getOutputColor() {
        return outputColor;
    }

    /**
     * Gets the value of the cell. This value typically represents a letter.
     *
     * @return A String representing the cell's value.
     */
    public String getCellValue() {
        return cellValue;
    }

    /**
     * Gets the background color of the cell.
     *
     * @return The Color object representing the background color of the cell.
     */
    public Color getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * Sets the value of the cell and updates the displayed text accordingly.
     *
     * @param val The new value (letter) to set for the cell.
     */
    public void setCellValue(String val) {
        this.cellValue = val;
        cellText.setText(cellValue);
    }

    /**
     * Gets the Polygon shape of the cell. This shape represents the hexagonal outline of the cell.
     *
     * @return The Polygon object representing the shape of the cell.
     */
    public Polygon getPolygon() {
        return polygon;
    }
}
