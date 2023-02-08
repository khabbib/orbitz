package Model;

import org.json.simple.JSONObject;

/**
 * @author Simon Mtegen
 * @author Marcus Svensson
 * An object that contains information about the sun
 */
public class Sun {
    private JSONObject sunInfo;

    private int xCord;
    private int yCord;

    /**
     * @param object a JSONObject that contains the information from the api
     * @author Simon Mtegen
     * @author Marcus Svensson
     * Constructs the sun
     */
    public Sun(JSONObject object) {
        this.sunInfo = object;
    }

    /**
     * @return returns an integer of the x position
     * @author Simon Mtegen
     * @author Marcus Svensson
     */
    public int getXCord() {
        return xCord;
    }

    /**
     * @param xCord an int of the X cord to be set
     * @author Simon Mtegen
     * @author Marcus Svensson
     * Sets the value of the x cord
     */
    public void setXCord(int xCord) {
        this.xCord = xCord;
    }

    /**
     * @return an integer of the Y cord
     * @author Simon Mtegen
     * @author Marcus Svensson
     */
    public int getYCord() {
        return yCord;
    }

    /**
     * @param yCord the cordinte to be used for y
     * @author Simon Mtegen
     * @author Marcus Svensson
     * Sets the Y Cord
     */
    public void setYCord(int yCord) {
        this.yCord = yCord;
    }

    /**
     * @return a double with the mass value
     * @author Simon Mtegen
     * @author Marcus Svensson
     * Gets the mass from the sun
     */
    public double getMass() {
        JSONObject sunMass = (JSONObject) sunInfo.get("mass");

        double massValue = ((double) sunMass.get("massValue")) - 0.5;
        long massExponent = 2 * (((long) sunMass.get("massExponent")) - 1);

        return massValue * (Math.pow(10, massExponent));
    }

    /**
     * @return returns a string from the JSONObject
     * @author Simon Mtegen
     * @author Marcus Svensson
     */
    @Override
    public String toString() {
        return sunInfo.get("mass").toString();
    }
}
