package Model;

import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import org.json.simple.JSONObject;

/**
 @author Simon Måtegen
 @author Marcus Svensson
 * An object that contains information about the sun
 */
public class Sun
{
    private JSONObject sunInfo;

    private int xCord;
    private int yCord;
    private int radius = 2000;

    private Sphere sunSphere;

    /**
     @author Simon Måtegen
     @author Marcus Svensson
     * Constructs the sun
     @param object a JSONObject that contains the information from the api
     */
    public Sun(JSONObject object)
    {
        this.sunInfo = object;
    }

    /**
     @author Simon Måtegen
     @author Marcus Svensson
     @return returns an integer of the x position
     */
    public int getXCord()
    {
        return xCord;
    }

    /**
     @author Simon Måtegen
     @author Marcus Svensson
     * Sets the value of the x cord
     @param xCord an int of the X cord to be set
     */
    public void setXCord(int xCord)
    {
        this.xCord = xCord;
    }
    /**
     @author Simon Måtegen
     @author Marcus Svensson
     @return an integer of the Y cord
     */
    public int getYCord()
    {
        return yCord;
    }

    /**
     @author Simon Måtegen
     @author Marcus Svensson
     * Sets the Y Cord
     @param yCord the cordinte to be used for y
     */
    public void setYCord(int yCord)
    {
        this.yCord = yCord;
    }

    /**
     @author Simon Måtegen
     @author Marcus Svensson
     * Gets the mass from the sun
     @return a double with the mass value
     */
    public double getMass()
    {
        JSONObject sunMass = (JSONObject)sunInfo.get("mass");

        double massValue = ((double) sunMass.get("massValue"))-0.5;
        long massExponent = 2* (((long) sunMass.get("massExponent"))-1);

        return massValue * (Math.pow(10, massExponent));
    }

    /**
     @author Simon Måtegen
     @author Lanna Maslo
     * Creates a sphere graphical object for the sun
     @return returns a string of the path
     */
    public Sphere getSphereFromSun()
    {
        sunSphere = new Sphere(radius);
        PhongMaterial sunMap = new PhongMaterial();
        sunMap.setDiffuseMap(new Image("Images/Sun.jpg"));
        sunSphere.setMaterial(sunMap);

        return sunSphere;
    }

    /**
     @author Simon Måtegen
     @author Marcus Svensson
     @return returns a string from the JSONObject
     */
    @Override
    public String toString()
    {
        return sunInfo.get("mass").toString();
    }
}
