package Controller.Calculators;

import Model.Enum.Stars;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import Model.Planet;
import Model.Sun;
import Controller.APIReader;

public class PlanetCalculatorTest {

    @Test
    void getOrbit() {
        PlanetCalculator planetCalculator = new PlanetCalculator();
        double expected = 2 * Math.PI;
        double actual = planetCalculator.getOrbit();
        assertEquals(expected, actual);
    }

    @Test
    void getToThePowerOfThree() {
        PlanetCalculator planetCalculator = new PlanetCalculator();
        double expected = Math.pow(1000, 3);
        double actual = planetCalculator.getToThePowerOfThree(1000, 3);
        assertEquals(expected, actual);
    }

    @Test
    void calculateTotalSunMass() {
        PlanetCalculator planetCalculator = new PlanetCalculator();
        double expected = 6.67430E-11 * 1000;
        double actual = planetCalculator.calculateTotalSunMass(1000);
        assertEquals(expected, actual);
    }

    @Test
    void getSquareRoot() {
        PlanetCalculator planetCalculator = new PlanetCalculator();
        double expected = Math.sqrt(1000);
        double actual = planetCalculator.getSquareRoot(2000, 2);
        assertEquals(expected, actual);
    }

    @Test
    void convertToMeter() {
        PlanetCalculator planetCalculator = new PlanetCalculator();
        double expected = 1000 * 1000;
        double actual = planetCalculator.convertToMeter(1000);
        assertEquals(expected, actual);
    }

    @Test
    void getOrbitalPeriod() {
        APIReader reader = new APIReader();
        PlanetCalculator planetCalculator = new PlanetCalculator();
        Sun sun = new Sun(reader.readBodyFromAPI(Stars.soleil.toString()));
        assertAll(
                () -> assertEquals(3.6468545209049206E-7, planetCalculator.getOrbitalPeriod(
                        sun, new Planet(reader.readBodyFromAPI("terre")))),
                () -> assertEquals(2.2434767462004238E-7, planetCalculator.getOrbitalPeriod(
                        sun, new Planet(reader.readBodyFromAPI("venus")))),
                () -> assertEquals(8.783130149253165E-8, planetCalculator.getOrbitalPeriod(
                        sun, new Planet(reader.readBodyFromAPI("mercure")))),
                () -> assertEquals(6.858958689816498E-7, planetCalculator.getOrbitalPeriod(
                        sun, new Planet(reader.readBodyFromAPI("mars")))),
                () -> assertEquals(4.3279712767910766E-6, planetCalculator.getOrbitalPeriod(
                        sun, new Planet(reader.readBodyFromAPI("jupiter")))),
                () -> assertEquals(1.0740223460734726E-5, planetCalculator.getOrbitalPeriod(
                        sun, new Planet(reader.readBodyFromAPI("saturne")))),
                () -> assertEquals(3.065503969131521E-5, planetCalculator.getOrbitalPeriod(
                        sun, new Planet(reader.readBodyFromAPI("uranus")))),
                () -> assertEquals(6.013346757491868E-5, planetCalculator.getOrbitalPeriod(
                        sun, new Planet(reader.readBodyFromAPI("neptune"))))
        );

    }


}
