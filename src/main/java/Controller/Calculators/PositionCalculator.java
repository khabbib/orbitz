package Controller.Calculators;

/**
 * @author Marcus Svensson
 * @author Simon Mtegen
 * Calculates a position for the planets
 */
public class PositionCalculator {
    /**
     * Sets the day
     *
     * @param year  the year
     * @param day   the day
     * @param month the month
     * @return double
     */
    public double setDay(double year, double month, double day) {
        if (year < 2000 || year > 2100) return -1;
        if (month < 1 || month > 12) return -1;
        if (day < 1 || day > 31) return -1;
        if (month == 2 && day > 28) return -1;
        double days = 367 * year - (7 * (year + ((month + 9) / 12))) / 4 + ((275 + month) / 9) + day - 730530;
        return days;
    }

    /**
     * Calculates and sets a value for a planets position
     *
     * @param d      the variable that represents the specific date
     * @param planet Planet object to check which planet it is
     * @return angle (true anomaly + argument of perihelion + 90 or 180)
     */
    public double getValues(double d, String planet) {
        double capitalOmega;
        double inclination;
        double smallOmega;
        double semiMajorAxis;
        double eccentricity;
        double meanAnomaly;
        double eccentricAnomalyApproximation, eccentricAnomaly;
        double rectangularCoordinateX;
        double rectangularCoordinateY;
        double heliocentricDistance;
        double trueAnomaly;

        if (planet.equals("Mercury")) {
            capitalOmega = 48.3313 + 3.24587E-5 * d;
            inclination = 7.0047 + 5.00E-8 * d;
            smallOmega = 29.1241 + 1.01444E-5 * d;
            semiMajorAxis = 0.387098;
            eccentricity = 0.205635 + 5.59E-10 * d;
            meanAnomaly = 168.6562 + 4.0923344368 * d;
        }

        if (planet.equals("Venus")) {
            capitalOmega = 76.6799 + 2.46590E-5 * d;
            inclination = 3.3946 + 2.75E-8 * d;
            smallOmega = 54.8910 + 1.38374E-5 * d;
            semiMajorAxis = 0.723330;
            eccentricity = 0.006773 - 1.302E-9 * d;
            meanAnomaly = 48.0052 + 1.6021302244 * d;
        }

        if (planet.equals("Earth")) {
            smallOmega = 282.9404 + 4.70935E-5 * d;
            semiMajorAxis = 1.000000;
            eccentricity = 0.016709 - 1.151E-9 * d;
            meanAnomaly = 356.0470 + 0.9856002585 * d;
        }

        if (planet.equals("Mars")) {
            capitalOmega = 49.5574 + 2.11081E-5 * d;
            inclination = 1.8497 - 1.78E-8 * d;
            smallOmega = 286.5016 + 2.92961E-5 * d;
            semiMajorAxis = 1.523688;
            eccentricity = 0.093405 + 2.516E-9 * d;
            meanAnomaly = 18.6021 + 0.5240207766 * d;
        }

        if (planet.equals("Jupiter")) {
            capitalOmega = 100.4542 + 2.76854E-5 * d;
            inclination = 1.3030 - 1.557E-7 * d;
            smallOmega = 273.8777 + 1.64505E-5 * d;
            semiMajorAxis = 5.20256;
            eccentricity = 0.048498 + 4.469E-9 * d;
            meanAnomaly = 19.8950 + 0.0830853001 * d;
        }

        if (planet.equals("Saturn")) {
            capitalOmega = 113.6634 + 2.38980E-5 * d;
            inclination = 2.4886 - 1.081E-7 * d;
            smallOmega = 339.3939 + 2.97661E-5 * d;
            semiMajorAxis = 9.55475;
            eccentricity = 0.055546 - 9.499E-9 * d;
            meanAnomaly = 316.9670 + 0.0334442282 * d;
        }

        if (planet.equals("Uranus")) {
            capitalOmega = 74.0005 + 1.3978E-5 * d;
            inclination = 0.7733 + 1.9E-8 * d;
            smallOmega = 96.6612 + 3.0565E-5 * d;
            semiMajorAxis = 19.18171 - 1.55E-8 * d;
            eccentricity = 0.047318 + 7.45E-9 * d;
            meanAnomaly = 142.5905 + 0.011725806 * d;
        }

        if (planet.equals("Neptune")) {
            capitalOmega = 131.7806 + 3.0173E-5 * d;
            inclination = 1.7700 - 2.55E-7 * d;
            smallOmega = 272.8461 - 6.027E-6 * d;
            semiMajorAxis = 30.05826 + 3.313E-8 * d;
            eccentricity = 0.008606 + 2.15E-9 * d;
            meanAnomaly = 260.2471 + 0.005995147 * d;
        }

        eccentricAnomalyApproximation = meanAnomaly + (180 / Math.PI) * eccentricity * Math.sin(Math.PI / 180 * meanAnomaly) * (1 + (eccentricity * Math.cos(Math.PI / 180 * meanAnomaly)));

        eccentricAnomaly = eccentricAnomalyApproximation - (eccentricAnomalyApproximation - (180 / Math.PI) * eccentricity * Math.sin(Math.PI / 180 * eccentricAnomalyApproximation) - meanAnomaly) / (1 - eccentricity * Math.cos(Math.PI / 180 * eccentricAnomalyApproximation));

        rectangularCoordinateX = calcX(semiMajorAxis, eccentricAnomaly, eccentricity);
        rectangularCoordinateY = calcY(semiMajorAxis, eccentricAnomaly, eccentricity);

        heliocentricDistance = Math.sqrt((rectangularCoordinateX * rectangularCoordinateX) + (rectangularCoordinateY * rectangularCoordinateY));
        trueAnomaly = Math.toDegrees(Math.atan2(rectangularCoordinateY, rectangularCoordinateX));

        if (planet.equals("Earth")) {
            return (trueAnomaly + smallOmega) + 180;
        } else if (planet.equals("Venus")) {
            return (trueAnomaly + smallOmega) + 180;
        }

        if (planet.equals("Mercury")) {

            return (trueAnomaly + smallOmega) + 90;
        } else {
            return trueAnomaly + smallOmega;
        }
    }

    public double calcY(double a, double E1, double e) {
        return a * (Math.sqrt(1 - (e * e))) * Math.sin(Math.PI / 180 * E1);

    }

    public double calcX(double a, double E1, double e) {
        return a * (Math.cos(Math.PI / 180 * E1) - e);
    }

}

