package Model;

import java.awt.*;

/**
 @author Albin Ahlbeck
  * Contains two swing colors and two java fx colors
 */

public class Theme
{
    private String name;

    private Color mainColor;
    private Color secondaryColor;

    private javafx.scene.paint.Paint mainPaint;
    private javafx.scene.paint.Paint secondaryPaint;

    /**
     @author Albin Ahlbeck
      * Changes the colour on components
     @param name The name of the theme
     @param mainColor the main color (SWING)
     @param secondaryColor the secondary color (SWING)
     @param mainPaint the main paint (FX)
     @param secondaryPaint the secondary paint (FX)
     */
    public Theme(String name, Color mainColor, Color secondaryColor,
                 javafx.scene.paint.Paint mainPaint, javafx.scene.paint.Paint secondaryPaint)
    {
        this.name = name;
        this.mainColor = mainColor;
        this.secondaryColor = secondaryColor;
        this.mainPaint = mainPaint;
        this.secondaryPaint = secondaryPaint;
    }

    /**
     @author Albin Ahlbeck
     @return returns the current main color
     */
    public Color getMainColor() {
        return mainColor;
    }

    /**
     @author Albin Ahlbeck
     @return returns the current secondary color
     */
    public Color getSecondaryColor() {
        return secondaryColor;
    }

    /**
     @author Albin Ahlbeck
     @return returns the current main paint
     */
    public javafx.scene.paint.Paint getMainPaint() {
        return mainPaint;
    }

    /**
     @author Albin Ahlbeck
     @return returns the current secondary paint
     */
    public javafx.scene.paint.Paint getSecondaryPaint() {
        return secondaryPaint;
    }

    /**
     @author Albin Ahlbeck
     @return returns the name of the theme
     */
    @Override
    public String toString()
    {
        return name;
    }
}
