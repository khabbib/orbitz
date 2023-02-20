package Controller.Calculators;

import java.time.Duration;
import java.time.LocalDate;

/**
 * @author Marcus Svensson
 * @author Simon Mtegen
 * Calculates a position for the planets
 */
public class PositionCalculator {
    private LocalDate startDate = LocalDate.of(2000, 1, 1);
    private double capitalOmega; // Not used somewhere
    private double inclination; // Not used somewhere
    private double heliocentricDistance; // Not used somewhere
    private double smallOmega;
    private double semiMajorAxis;
    private double eccentricity;
    private double meanAnomaly;
    private double E0, E1; // E0 = eccentricAnomalyApproximation, E1 = eccentricAnomaly
    private double rectangularCoordinateX;
    private double rectangularCoordinateY;
    private double trueAnomaly;
    private double days;
    /**
     * Does the calculation of date difference
     *
     * @param year  the year
     * @param day   the day
     * @param month the month
     * @return double
     */
    public double calculateDateDifference(int year, int month, int day) {
        if (year < 2000 || year > 2100) return -1;
        if (month < 1 || month > 12) return -1;
        if (day < 1 || day > 31) return -1;
        if (month == 2 && day > 28) return -1;

        LocalDate todayDate = LocalDate.of(year, month, day);
        Duration duration = Duration.between(startDate.atStartOfDay(), todayDate.atStartOfDay());
        days = duration.toDays();

        return days;
    }

    /**
     * Calculates and sets a value for a planets position
     *
     * @param date      the variable that represents the specific date
     * @param planetName Planet object to check which planet it is
     * @return angle (true anomaly + argument of perihelion + 90 or 180)
     */
    public double calculatePlanetPosition(double date, String planetName) {

        switch (planetName) {
            case "Mercury":
                capitalOmega = 48.3313 + 3.24587E-5 * date; // Not used somewhere
                inclination = 7.0047 + 5.00E-8 * date; // Not used somewhere
                smallOmega = 29.1241 + 1.01444E-5 * date;
                semiMajorAxis = 0.387098;
                eccentricity = 0.205635 + 5.59E-10 * date;
                meanAnomaly = 168.6562 + 4.0923344368 * date;
                break;
            case "Venus":
                capitalOmega = 76.6799 + 2.46590E-5 * date; // Not used somewhere
                inclination = 3.3946 + 2.75E-8 * date; // Not used somewhere
                smallOmega = 54.8910 + 1.38374E-5 * date;
                semiMajorAxis = 0.723330;
                eccentricity = 0.006773 - 1.302E-9 * date;
                meanAnomaly = 48.0052 + 1.6021302244 * date;
                break;
            case "Earth":
                smallOmega = 282.9404 + 4.70935E-5 * date;
                semiMajorAxis = 1.000000;
                eccentricity = 0.016709 - 1.151E-9 * date;
                meanAnomaly = 356.0470 + 0.9856002585 * date;
                break;
            case "Mars":
                capitalOmega = 49.5574 + 2.11081E-5 * date; // Not used somewhere
                inclination = 1.8497 - 1.78E-8 * date; // Not used somewhere
                smallOmega = 286.5016 + 2.92961E-5 * date;
                semiMajorAxis = 1.523688;
                eccentricity = 0.093405 + 2.516E-9 * date;
                meanAnomaly = 18.6021 + 0.5240207766 * date;
                break;
            case "Jupiter":
                capitalOmega = 100.4542 + 2.76854E-5 * date; // Not used somewhere
                inclination = 1.3030 - 1.557E-7 * date; // Not used somewhere
                smallOmega = 273.8777 + 1.64505E-5 * date;
                semiMajorAxis = 5.20256;
                eccentricity = 0.048498 + 4.469E-9 * date;
                meanAnomaly = 19.8950 + 0.0830853001 * date;
                break;
            case "Saturn":
                capitalOmega = 113.6634 + 2.38980E-5 * date; // Not used somewhere
                inclination = 2.4886 - 1.081E-7 * date; // Not used somewhere
                smallOmega = 339.3939 + 2.97661E-5 * date;
                semiMajorAxis = 9.55475;
                eccentricity = 0.055546 - 9.499E-9 * date;
                meanAnomaly = 316.9670 + 0.0334442282 * date;
                break;
            case "Uranus":
                capitalOmega = 74.0005 + 1.3978E-5 * date; // Not used somewhere
                inclination = 0.7733 + 1.9E-8 * date; // Not used somewhere
                smallOmega = 96.6612 + 3.0565E-5 * date;
                semiMajorAxis = 19.18171 - 1.55E-8 * date;
                eccentricity = 0.047318 + 7.45E-9 * date;
                meanAnomaly = 142.5905 + 0.011725806 * date;
                break;
            case "Neptune":
                capitalOmega = 131.7806 + 3.0173E-5 * date; // Not used somewhere
                inclination = 1.7700 - 2.55E-7 * date; // Not used somewhere
                smallOmega = 272.8461 - 6.027E-6 * date;
                semiMajorAxis = 30.05826 + 3.313E-8 * date;
                eccentricity = 0.008606 + 2.15E-9 * date;
                meanAnomaly = 260.2471 + 0.005995147 * date;
                break;
            default:
                System.out.println("Not valid planet name.");
                return -1;
        }


        E0 = meanAnomaly + (180 / Math.PI) * eccentricity * Math.sin(Math.PI / 180 * meanAnomaly) * (1 + (eccentricity * Math.cos(Math.PI / 180 * meanAnomaly)));

        E1 = E0 - (E0 - (180 / Math.PI) * eccentricity * Math.sin(Math.PI / 180 * E0) - meanAnomaly) / (1 - eccentricity * Math.cos(Math.PI / 180 * E0));

        // This function count to meddle difference between E0 and E1 until it reaches 0.005
        E1 = this.calculateEccentricAnomaly();

        rectangularCoordinateX = calcX(semiMajorAxis, E1, eccentricity);
        rectangularCoordinateY = calcY(semiMajorAxis, E1, eccentricity);

        // Not used somewhere in the code
        heliocentricDistance = Math.sqrt((rectangularCoordinateX * rectangularCoordinateX) + (rectangularCoordinateY * rectangularCoordinateY));
        trueAnomaly = Math.toDegrees(Math.atan2(rectangularCoordinateY, rectangularCoordinateX));


        if (planetName.equals("Earth")) {
            return (trueAnomaly + smallOmega) + 180;
        }
        if (planetName.equals("Venus")) {
            return (trueAnomaly + smallOmega) + 180;
        }
        if (planetName.equals("Mercury")) {
            return (trueAnomaly + smallOmega) + 90;
        }

        return trueAnomaly + smallOmega;
    }

    private double calculateEccentricAnomaly() {
        // The while loop operation check if meddle difference between E0 and E1 and make them to reach minimum 0.005
        while (Math.abs(E0 - E1) > 0.005) {
            E0 = E1;
            E1 = E0 - (E0 - (180 / Math.PI) * eccentricity * Math.sin(Math.PI / 180 * E0) - meanAnomaly) / (1 - eccentricity * Math.cos(Math.PI / 180 * E0));
        }
        System.out.println("calculateEccentricAnomaly: E1: " + E1);
        return E1;
    }

    public double calcY(double a, double E1, double e) {
        return a * (Math.sqrt(1 - (e * e))) * Math.sin(Math.PI / 180 * E1);

    }

    public double calcX(double a, double E1, double e) {
        return a * (Math.cos(Math.PI / 180 * E1) - e);
    }

}

