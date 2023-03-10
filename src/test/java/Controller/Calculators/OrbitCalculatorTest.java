package Controller.Calculators;

import Controller.APIReader;
import Model.Orbit;
import Model.Planet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrbitCalculatorTest {

    @Test
    void getOrbitMercure() {
        OrbitCalculator orbitCalculator = new OrbitCalculator();
        APIReader reader = new APIReader();
        Planet planet = new Planet(reader.readBodyFromAPI("mercure"));
        long scale = (long) planet.getSCALE_RADIUS_VALUE();
        assertAll(
                () -> assertEquals(18530.896, orbitCalculator.getOrbit(planet).getWidth(scale)),
                () -> assertEquals(18134.8864, orbitCalculator.getOrbit(planet).getHeight(scale)),
                () -> assertEquals(0.0, orbitCalculator.getOrbit(planet).getCenterXCord(scale)),
                () -> assertEquals(0.0, orbitCalculator.getOrbit(planet).getCenterYCord(scale)));
    }

    @Test
    void getOrbitVenus() {
        OrbitCalculator orbitCalculator = new OrbitCalculator();
        APIReader reader = new APIReader();
        Planet planet = new Planet(reader.readBodyFromAPI("venus"));
        long scale = (long) planet.getSCALE_RADIUS_VALUE();
        assertAll(
                () -> assertEquals(34626.712, orbitCalculator.getOrbit(planet).getWidth(scale)),
                () -> assertEquals(34625.7696, orbitCalculator.getOrbit(planet).getHeight(scale)),
                () -> assertEquals(0.0, orbitCalculator.getOrbit(planet).getCenterXCord(scale)),
                () -> assertEquals(0.0, orbitCalculator.getOrbit(planet).getCenterYCord(scale)));
    }

    @Test
    void getOrbitTerre() {
        OrbitCalculator orbitCalculator = new OrbitCalculator();
        APIReader reader = new APIReader();
        Planet planet = new Planet(reader.readBodyFromAPI("terre"));
        long scale = (long) planet.getSCALE_RADIUS_VALUE();
        assertAll(
                () -> assertEquals(47871.36736, orbitCalculator.getOrbit(planet).getWidth(scale)),
                () -> assertEquals(47864.50144, orbitCalculator.getOrbit(planet).getHeight(scale)),
                () -> assertEquals(0.0, orbitCalculator.getOrbit(planet).getCenterXCord(scale)),
                () -> assertEquals(0.0, orbitCalculator.getOrbit(planet).getCenterYCord(scale)));
    }

    @Test
    void getOrbitMars() {
        OrbitCalculator orbitCalculator = new OrbitCalculator();
        APIReader reader = new APIReader();
        Planet planet = new Planet(reader.readBodyFromAPI("mars"));
        long scale = (long) planet.getSCALE_RADIUS_VALUE();
        assertAll(
                () -> assertEquals(72940.544, orbitCalculator.getOrbit(planet).getWidth(scale)),
                () -> assertEquals(72626.35264, orbitCalculator.getOrbit(planet).getHeight(scale)),
                () -> assertEquals(0.0, orbitCalculator.getOrbit(planet).getCenterXCord(scale)),
                () -> assertEquals(0.0, orbitCalculator.getOrbit(planet).getCenterYCord(scale)));
    }

    @Test
    void getOrbitJupiter() {
        OrbitCalculator orbitCalculator = new OrbitCalculator();
        APIReader reader = new APIReader();
        Planet planet = new Planet(reader.readBodyFromAPI("jupiter"));
        long scale = (long) planet.getSCALE_RADIUS_VALUE();
        assertAll(
                () -> assertEquals(99627.62496, orbitCalculator.getOrbit(planet).getWidth(scale)),
                () -> assertEquals(99528.45584, orbitCalculator.getOrbit(planet).getHeight(scale)),
                () -> assertEquals(0.0, orbitCalculator.getOrbit(planet).getCenterXCord(scale)),
                () -> assertEquals(0.0, orbitCalculator.getOrbit(planet).getCenterYCord(scale)));
    }

    @Test
    void getOrbitSaturne() {
        OrbitCalculator orbitCalculator = new OrbitCalculator();
        APIReader reader = new APIReader();
        Planet planet = new Planet(reader.readBodyFromAPI("saturne"));
        long scale = (long) planet.getSCALE_RADIUS_VALUE();
        assertAll(
                () -> assertEquals(130438.0728, orbitCalculator.getOrbit(planet).getWidth(scale)),
                () -> assertEquals(130248.72896, orbitCalculator.getOrbit(planet).getHeight(scale)),
                () -> assertEquals(0.0, orbitCalculator.getOrbit(planet).getCenterXCord(scale)),
                () -> assertEquals(0.0, orbitCalculator.getOrbit(planet).getCenterYCord(scale)));
    }

    @Test
    void getOrbitUranus() {
        OrbitCalculator orbitCalculator = new OrbitCalculator();
        APIReader reader = new APIReader();
        Planet planet = new Planet(reader.readBodyFromAPI("uranus"));
        long scale = (long) planet.getSCALE_RADIUS_VALUE();
        assertAll(
                () -> assertEquals(183722.12384, orbitCalculator.getOrbit(planet).getWidth(scale)),
                () -> assertEquals(183645.6944, orbitCalculator.getOrbit(planet).getHeight(scale)),
                () -> assertEquals(0.0, orbitCalculator.getOrbit(planet).getCenterXCord(scale)),
                () -> assertEquals(0.0, orbitCalculator.getOrbit(planet).getCenterYCord(scale)));
    }

    @Test
    void getOrbitNeptune() {
        OrbitCalculator orbitCalculator = new OrbitCalculator();
        APIReader reader = new APIReader();
        Planet planet = new Planet(reader.readBodyFromAPI("neptune"));
        long scale = (long) planet.getSCALE_RADIUS_VALUE();
        assertAll(
                () -> assertEquals(239914.4768, orbitCalculator.getOrbit(planet).getWidth(scale)),
                () -> assertEquals(239721.3376, orbitCalculator.getOrbit(planet).getHeight(scale)),
                () -> assertEquals(0.0, orbitCalculator.getOrbit(planet).getCenterXCord(scale)),
                () -> assertEquals(0.0, orbitCalculator.getOrbit(planet).getCenterYCord(scale)));
    }

    @Test
    void getOrbitalMercure() {
        OrbitCalculator orbitCalculator = new OrbitCalculator();
        APIReader reader = new APIReader();
        Planet planet = new Planet(reader.readBodyFromAPI("mercure"));
        assertEquals(8.783130149253165E-8,orbitCalculator.getOrbitalPeriod(planet.getSemiMajorAxis()));
    }

    @Test
    void getOrbitalVenus() {
          OrbitCalculator orbitCalculator = new OrbitCalculator();
        APIReader reader = new APIReader();
        Planet planet = new Planet(reader.readBodyFromAPI("venus"));
        assertEquals(2.2434767462004238E-7,orbitCalculator.getOrbitalPeriod(planet.getSemiMajorAxis()));
    }

    @Test
    void getOrbitalTerre() {
          OrbitCalculator orbitCalculator = new OrbitCalculator();
        APIReader reader = new APIReader();
        Planet planet = new Planet(reader.readBodyFromAPI("terre"));
        assertEquals(3.6468545209049206E-7,orbitCalculator.getOrbitalPeriod(planet.getSemiMajorAxis()));
    }

    @Test
    void getOrbitalMars() {
          OrbitCalculator orbitCalculator = new OrbitCalculator();
        APIReader reader = new APIReader();
        Planet planet = new Planet(reader.readBodyFromAPI("mars"));
        assertEquals(6.858958689816497E-7,orbitCalculator.getOrbitalPeriod(planet.getSemiMajorAxis()));
    }

    @Test
    void getOrbitalJupiter() {
          OrbitCalculator orbitCalculator = new OrbitCalculator();
        APIReader reader = new APIReader();
        Planet planet = new Planet(reader.readBodyFromAPI("jupiter"));
        assertEquals(4.327971276791076E-6,orbitCalculator.getOrbitalPeriod(planet.getSemiMajorAxis()));
    }

    @Test
    void getOrbitalSaturne() {
          OrbitCalculator orbitCalculator = new OrbitCalculator();
        APIReader reader = new APIReader();
        Planet planet = new Planet(reader.readBodyFromAPI("saturne"));
        assertEquals(1.0740223460734726E-5,orbitCalculator.getOrbitalPeriod(planet.getSemiMajorAxis()));
    }

    @Test
    void getOrbitalUranus() {
          OrbitCalculator orbitCalculator = new OrbitCalculator();
        APIReader reader = new APIReader();
        Planet planet = new Planet(reader.readBodyFromAPI("uranus"));
        assertEquals(3.06550396913152E-5,orbitCalculator.getOrbitalPeriod(planet.getSemiMajorAxis()));
    }

    @Test
    void getOrbitalNeptune() {
          OrbitCalculator orbitCalculator = new OrbitCalculator();
        APIReader reader = new APIReader();
        Planet planet = new Planet(reader.readBodyFromAPI("neptune"));
        assertEquals(6.0133467574918665E-5,orbitCalculator.getOrbitalPeriod(planet.getSemiMajorAxis()));
    }
}