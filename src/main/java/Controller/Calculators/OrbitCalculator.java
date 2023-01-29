package Controller.Calculators;

import Model.Orbit;
import Model.Planet;
import Model.Sun;

/**
 * Calculator used to calculate orbits.
 * @author Simon Måtegen
 * @author Marcus Svensson
 * @version 1
 */
public class OrbitCalculator
{
    long orbitWidth;
    long orbitHeight;

    double orbitOffsetFromSun;

    /**
     * Creates an orbit object based on the planet given.
     * @param sun    Sun object the planet is orbiting around
     * @param planet Planet object to calculate orbit for.
     * @return Orbit object.
     */
    public Orbit getPlanetSunOrbit(Sun sun, Planet planet)
    {
        double orbitXCord;
        double orbitYCord;

        Orbit planetOrbit;

        /*
            Aphelion and Perihelion values in the API was wrong for Uranus and Neptune.
            They get new values when their orbit is getting calculated
        */
        if (planet.getName().equals("Uranus"))
        {
            planet.setAphelion(3003.62E6);
            planet.setPerihelion(2741.30E6);
        }
        else if (planet.getName().equals("Neptune"))
        {
            planet.setAphelion(4545.67E6);
            planet.setPerihelion(4444.45E6);
        }

        calculateOrbit(planet);

        orbitXCord = sun.getXCord() + orbitOffsetFromSun;
        orbitYCord = sun.getYCord();

        planetOrbit = new Orbit(orbitWidth, orbitHeight, orbitXCord, orbitYCord);

        return planetOrbit;
    }
    /**
     * Calculates the planets orbit
     * @param planet Planet object to calculate orbit for.
     */
    private void calculateOrbit(Planet planet)
    {
        orbitWidth = (long)(planet.getSemiMajorAxis()) * 2;
        orbitHeight = (long) ((Math.sqrt((planet.getAphelion() * planet.getPerihelion())))) * 2;

        orbitOffsetFromSun = planet.getAphelion() - planet.getSemiMajorAxis();
    }
}

