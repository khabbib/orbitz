package Model;

/**
 * Class that represents an orbit of a planet
 *
 * @author Simon M?tegen
 * @author Marcus Svensson
 * @version 1
 */

public class Orbit {
    private static final long SCALE_VALUE = 37500;
    private double width;
    private double height;
    private double xCord;
    private double yCord;

    /**
     * Conctructor that creates an ellipse with given arguments
     *
     * @param inWidth  the width of the orbit in km
     * @param inHeight the height of the orbit in km
     * @param inXCord  the x-value of the orbit on the screen
     * @param inYCord  the y-value of the orbit on the screen
     */
    public Orbit(double inWidth, double inHeight, double inXCord, double inYCord) {
        this.width = inWidth;
        this.height = inHeight;
        this.xCord = inXCord;
        this.yCord = inYCord;
    }

    /**
     * Get-method that returns a reasonable value of the orbits width
     *
     * @return width of the orbit divided by a scale factor
     */
    public double getWidth() {
        return width / SCALE_VALUE;
    }

    /**
     * Get-method that returns a reasonable value of the orbits height
     *
     * @return height of the orbit divided by a scale factor
     */
    public double getHeight() {
        return height / SCALE_VALUE;
    }

    /**
     * Get-method that returns a reasonable value of the orbits x-coordinate
     *
     * @return x-coordinate of the orbit divided by a scale factor
     */
    public double getXCord() {
        return xCord / SCALE_VALUE;
    }

    /**
     * Get-method that returns a reasonable value of the orbits y-coordinate
     *
     * @return y-coordinate of the orbit divided by a scale factor
     */
    public double getYCord() {
        return yCord / SCALE_VALUE;
    }
}
