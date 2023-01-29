package Controller.Calculators;

/**
 * @author Marcus Svensson
 * @author Simon Måtegen
 * Calculates a position for the planets
 */
public class PositionCalculator
{
    private double d;

    private double N;
    private double i;
    private double w;
    private double a;
    private double e;
    private double M;

    private double E0, E1;
    private double x;
    private double y;

    private double r;
    private double v;


    /**
     * Sets the day
     * @param year the year
     * @param day the day
     * @param month the month
     * @return double
     */
    public double setDay(double year, double month, double day)
    {
        d = 367*year-(7*(year+((month+9)/12)))/4+((275+month)/9)+day-730530;
        return d;
    }
    /**
     * Calculates and sets a value for a planets position
     * @param d the variable that represents the specific date
     * @param planet Planet object to check which planet it is
     * @return angle (true anomaly + argument of perihelion + 90 or 180)
     */
    public double getValues(double d, String planet)
    {

        if (planet.equals("Mercury"))
        {
            N = 48.3313 + 3.24587E-5 * d;
            i = 7.0047 + 5.00E-8 * d;
            w = 29.1241 + 1.01444E-5 * d;
            a = 0.387098;
            e = 0.205635 + 5.59E-10 * d;
            M = 168.6562 + 4.0923344368 * d;
        }

        if (planet.equals("Venus"))
        {
            N = 76.6799 + 2.46590E-5 * d;
            i = 3.3946 + 2.75E-8 * d;
            w = 54.8910 + 1.38374E-5 * d;
            a = 0.723330;
            e = 0.006773 - 1.302E-9 * d;
            M = 48.0052 + 1.6021302244 * d;
        }

        if (planet.equals("Earth"))
        {
            w = 282.9404  + 4.70935E-5 * d;
            a = 1.000000;
            e = 0.016709 - 1.151E-9 * d;
            M = 356.0470 + 0.9856002585 * d;
        }

        if (planet.equals("Mars"))
        {
            N = 49.5574 + 2.11081E-5 * d;
            i = 1.8497 - 1.78E-8 * d;
            w = 286.5016 + 2.92961E-5 * d;
            a = 1.523688;
            e = 0.093405 + 2.516E-9 * d;
            M = 18.6021 + 0.5240207766 * d;
        }

        if (planet.equals("Jupiter"))
        {
            N = 100.4542 + 2.76854E-5 * d;
            i = 1.3030 - 1.557E-7 * d;
            w = 273.8777 + 1.64505E-5 * d;
            a = 5.20256;
            e = 0.048498 + 4.469E-9 * d;
            M = 19.8950 + 0.0830853001 * d;
        }

        if (planet.equals("Saturn"))
        {
            N = 113.6634 + 2.38980E-5 * d;
            i = 2.4886 - 1.081E-7 * d;
            w = 339.3939 + 2.97661E-5 * d;
            a = 9.55475;
            e = 0.055546 - 9.499E-9 * d;
            M = 316.9670 + 0.0334442282 * d;
        }

        if (planet.equals("Uranus"))
        {
            N = 74.0005 + 1.3978E-5 * d;
            i = 0.7733 + 1.9E-8 * d;
            w = 96.6612 + 3.0565E-5 * d;
            a = 19.18171 - 1.55E-8 * d;
            e = 0.047318 + 7.45E-9 * d;
            M = 142.5905 + 0.011725806 * d;
        }

        if (planet.equals("Neptune"))
        {
            N = 131.7806 + 3.0173E-5 * d;
            i = 1.7700 - 2.55E-7 * d;
            w = 272.8461 - 6.027E-6 * d;
            a = 30.05826 + 3.313E-8 * d;
            e = 0.008606 + 2.15E-9 * d;
            M = 260.2471 + 0.005995147 * d;
        }

        E0 = M + (180/Math.PI)*e*Math.sin(Math.PI/180*M)
                *(1+(e*Math.cos(Math.PI/180*M)));

        E1 = E0 - (E0-(180/Math.PI) * e * Math.sin(Math.PI/180*E0)-M)/
                (1-e*Math.cos(Math.PI/180*E0));

        x = a * (Math.cos(Math.PI/180*E1)-e);
        y = a * (Math.sqrt(1-(e*e)))*Math.sin(Math.PI/180*E1);

        r = Math.sqrt((x*x)+(y*y));
        v = Math.toDegrees(Math.atan2(y, x));

        if (planet.equals("Earth"))
        {
            return (v + w) + 180;
        }

        else if (planet.equals("Venus"))
        {
            return (v + w) + 180;
        }

        if (planet.equals("Mercury"))
        {

            return (v + w) + 90;
        }

        else
        {
            return v + w;
        }
    }
}

