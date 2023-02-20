package Controller.Calculators;

import Model.Planet;
import Model.Sun;

/**
 * Calculator used to calculate planet related values
 *
 * @author Simon Mtegen
 * @author Marcus Svensson
 * @version 1
 */
public class PlanetCalculator {
    private final double generalGravitational = 6.67430E-11; //the general gravitational constant

    /**
     * Calculates the orbits period for each planet.
     *
     * @param sun The sun
     * @param planet Planet to get orbit time for
     * @return The orbit time in seconds
     */
    public double getOrbitalPeriod(Sun sun, Planet planet) {
        double planetSemiMajorAxis = planet.getSemiMajorAxis();
        double axisInMeter = this.convertToMeter(planetSemiMajorAxis);

        double a3 = this.getToThePowerOfThree(axisInMeter, 3);

        double sunMassInTotall = this.calculateTotalSunMass(sun.getMass());

        double orbitalPeriod = this.getOrbit() * (this.getSquareRoot(a3, sunMassInTotall));
        return orbitalPeriod;
    }

    public double getOrbit() {
        return 2 * Math.PI;
    }

    public double getSquareRoot(double axisToThePowerOfThree, double sunMassInTotall) {
        return Math.sqrt(axisToThePowerOfThree / sunMassInTotall);
    }

    public double getToThePowerOfThree(double semiMajorAxisInMeter, int power){
        return Math.pow(semiMajorAxisInMeter, power);
    }

    public double calculateTotalSunMass(double sunMass){
        return generalGravitational * sunMass;
    }

    public double convertToMeter(double semiMajorAxis){
        return semiMajorAxis * 1000;
    }

}
