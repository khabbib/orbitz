package Model;

import Controller.Calculators.PositionCalculator;
import org.json.simple.JSONObject;

/**
 * Class that represents a planet
 *
 * @author Marcus Svensson
 * @author Simon Mtegen
 * @author Albin Ahlbeck
 * @version 1.0
 */
public class Planet {
    private double SCALE_RADIUS_VALUE = 6250;
    private long semiMajorAxis;
    private double perihelion;
    private double aphelion;
    private JSONObject planetInfo;
    private double meanRadius;
    private Orbit planetOrbit;
    private PositionCalculator pos = new PositionCalculator();

    /**
     * Constructor that, when created, creates a planet-object by getting specific values
     * for a specific planet from an API.
     *
     * @param object JSONObject that holds the API information for the specific planets
     * @author Albin Ah
     */
    public Planet(JSONObject object) {
        this.planetInfo = object;
        this.perihelion = (long) planetInfo.get("perihelion");
        this.aphelion = (long) planetInfo.get("aphelion");
        this.meanRadius = (double) planetInfo.get("meanRadius");
    }

    /**
     * A get-method that gets semi-major axis value from API and returning the value.
     * The semi-major axis is half the value of the longest diameter of an ellipse.
     *
     * @return a planets semi-major axis in km
     */
    public double getSemiMajorAxis() {
        semiMajorAxis = (long) planetInfo.get("semimajorAxis");

        return semiMajorAxis;
    }

    /**
     * A get-method that retuns the value of a planets perihelion.
     * The perihelion is the distance between the sun and the specific planet
     * when the planet is at the closest position to the sun.
     *
     * @return the perihelion distance in km
     */
    public double getPerihelion() {
        return perihelion;
    }

    /**
     * A get-method that retuns the value of a planets perihelion.
     * The aphelion is the distance between the sun and the specific planet
     * when the planet is at the most distant position to the sun.
     *
     * @return the aphelion distance in km
     */
    public double getAphelion() {
        return aphelion;
    }

    /**
     * A set-method that sets the value of a planets specific aphelion.
     *
     * @param aphelion the value in km
     */
    public void setAphelion(double aphelion) {
        this.aphelion = aphelion;
    }

    /**
     * A set-method that sets the value of a planets specific perihelion.
     *
     * @param perihelion the value in km
     */
    public void setPerihelion(double perihelion) {
        this.perihelion = perihelion;
    }

    /**
     * A get-method that returns the english name of a specific planet.
     *
     * @return the english name of a planet with capital first letter
     */
    public String getName() {
        return planetInfo.get("englishName").toString();
    }

    /**
     * A get-method that returns a planets specific orbit.
     * The orbit of a planet is the curved path of a celestial object round, for example, a star.
     *
     * @return an Orbit-object for a specific planet
     */
    public Orbit getPlanetOrbit() {
        return planetOrbit;
    }

    /**
     * A set-method that sets the orbit for a specific planet.
     *
     * @param planetOrbit a planets specific orbit as an Orbit-object
     */
    public void setPlanetOrbit(Orbit planetOrbit) {
        this.planetOrbit = planetOrbit;
    }

    public double getMeanRadius() {
        if (getName().equals("Jupiter")) {
            return meanRadius/4;
        } else if (getName().equals("Saturn")) {
            return meanRadius/5;
        } else if (getName().equals("Uranus") || getName().equals("Neptune")) {
            return meanRadius/3;
        }
        return meanRadius;
    }

    public double getSCALE_RADIUS_VALUE() {
        return SCALE_RADIUS_VALUE;
    }

    /**
     * Get the swedish name of the planet.
     * @return Swedish name of the planet.
     */
    public String getSwedishName() {
        if (planetInfo.get("englishName").equals("Sun"))
            return "Solen";
        else if (planetInfo.get("englishName").equals("Mercury"))
            return "Merkurius";
        else if (planetInfo.get("englishName").equals("Venus"))
            return "Venus";
        else if (planetInfo.get("englishName").equals("Earth"))
            return "Jorden";
        else if (planetInfo.get("englishName").equals("Mars"))
            return "Mars";
        else if (planetInfo.get("englishName").equals("Jupiter"))
            return "Jupiter";
        else if (planetInfo.get("englishName").equals("Saturn"))
            return "Saturnus";
        else if (planetInfo.get("englishName").equals("Uranus"))
            return "Uranus";
        else if (planetInfo.get("englishName").equals("Neptune"))
            return "Neptunus";

        return planetInfo.get("englishName").toString();
    }
}
