package Controller.Calculators;

import Model.Planet;
import Model.Sun;

/**
 * Calculator used to calculate planet related values
 *
 * @author Simon Måtegen
 * @author Marcus Svensson
 * @version 1
 */
public class PlanetCalculator
{
    private final double G = 6.67430E-11; //the general gravitational constant

    /**
     *Calculates the orbit time for the given planet.
     * @param sun The sun
     * @param planet Planet to get orbit time for
     * @return The orbit time in seconds
     */
    public double calculatePlanetSunOrbitTime(Sun sun, Planet planet)
    {
        double mu = -1; //The greek letter
        double time = -1;

        mu = G*sun.getMass();

        time = ((2*Math.PI)*(Math.sqrt((Math.pow((planet.getSemiMajorAxis()*1000), 3))/mu)));

        return time;
    }
}
