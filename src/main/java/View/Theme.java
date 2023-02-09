package View;

import java.awt.*;

/**
 * @author Albin Ahlbeck
 * Contains two swing colors and two java fx colors
 */

public class Theme {
    private String name;

    private Color mainColor;
    private Color secondaryColor;

    private javafx.scene.paint.Paint mainPaint;
    private javafx.scene.paint.Paint secondaryPaint;

    /**
     * @param name           The name of the theme
     * @param mainColor      the main color (SWING)
     * @param secondaryColor the secondary color (SWING)
     * @param mainPaint      the main paint (FX)
     * @param secondaryPaint the secondary paint (FX)
     * @author Albin Ahlbeck
     * Changes the colour on components
     */
    public Theme(String name, Color mainColor, Color secondaryColor,
                 javafx.scene.paint.Paint mainPaint, javafx.scene.paint.Paint secondaryPaint) {
        this.name = name;
        this.mainColor = mainColor;
        this.secondaryColor = secondaryColor;
        this.mainPaint = mainPaint;
        this.secondaryPaint = secondaryPaint;
    }

    /**
     * @return returns the current main color
     * @author Albin Ahlbeck
     */
    public Color getMainColor() {
        return mainColor;
    }

    /**
     * @return returns the current secondary color
     * @author Albin Ahlbeck
     */
    public Color getSecondaryColor() {
        return secondaryColor;
    }

    /**
     * @return returns the current main paint
     * @author Albin Ahlbeck
     */
    public javafx.scene.paint.Paint getMainPaint() {
        return mainPaint;
    }

    /**
     * @return returns the current secondary paint
     * @author Albin Ahlbeck
     */
    public javafx.scene.paint.Paint getSecondaryPaint() {
        return secondaryPaint;
    }

    /**
     * @return returns the name of the theme
     * @author Albin Ahlbeck
     */
    @Override
    public String toString() {
        return name;
    }
}
