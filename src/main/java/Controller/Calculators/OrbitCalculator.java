package Controller.Calculators;

import Model.Orbit;
import Model.Planet;
import Model.Sun;

/**
 * Calculator used to calculate orbits.
 *
 * @author Simon Måtegen
 * @author Marcus Svensson
 * @version 1
 */
public class OrbitCalculator {
    /**
     * Creates an orbit object based on the planet given.
     *

     * @param planet Planet object to calculate orbit for.
     * @return Orbit object.
     */
    public Orbit getOrbit(Planet planet) {

        /*
            Aphelion and Perihelion values in the API was wrong for Uranus and Neptune.
            They get new values when their orbit is getting calculated
        */
        if (planet.getName().equals("Uranus")) {
            planet.setAphelion(3003.62E6);
            planet.setPerihelion(2741.30E6);
        } else if (planet.getName().equals("Neptune")) {
            planet.setAphelion(4545.67E6);
            planet.setPerihelion(4444.45E6);
        }
        
        long orbitWidth = calculateOrbitWidth(planet.getSemiMajorAxis());
        long orbitHeight = calculateOrbitHeight(planet.getAphelion(), planet.getPerihelion());
        double orbitOffsetFromSun = calculateOrbitOffsetFromSun(planet.getAphelion(), planet.getSemiMajorAxis());
        
        
        return new Orbit(orbitWidth, orbitHeight, orbitOffsetFromSun, 0);
    }


    private long calculateOrbitWidth(double semiMajorAxis){
        return (long) (semiMajorAxis) * 2;
    }
    private long calculateOrbitHeight(double aphelion, double perihelion){
        return (long) ((Math.sqrt((aphelion * perihelion)))) * 2;
    }
    private double calculateOrbitOffsetFromSun(double aphelion, double semiMajorAxis){
        return aphelion - semiMajorAxis;
    }

    /**
     * Calculates the orbits period for each planet.
     *
     * @param axisSemiMajorAxis The semi major axis of the planet
     * @return The orbit time in seconds
     */
    public double getOrbitalPeriod(double axisSemiMajorAxis) {
        double a3 = Math.pow((axisSemiMajorAxis * 1000), 3);
        //sun standard gravitational parameter
        double mu = 9.938032700000002E47;
        double orbitalConstant = 2 * Math.PI;
        double squareRoot = Math.sqrt(a3 / mu);

        return orbitalConstant * squareRoot;
    }
}

