package Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import Controller.APIReader;

public class PlanetTest {
    @Test
    void testTerre() {
        APIReader reader = new APIReader();
        Planet terre = new Planet(reader.readBodyFromAPI("terre"));
        assertAll(
                () -> assertEquals(1.49598023E8, terre.getSemiMajorAxis()),
                () -> assertEquals(147095000, terre.getPerihelion()),
                () -> assertEquals(152100000, terre.getAphelion()),
                () -> assertEquals(6371.0084, terre.getMeanRadius()),
                () -> assertEquals("Earth", terre.getName()),
                () -> assertEquals(6250, terre.getSCALE_RADIUS_VALUE())
        );
    }

    @Test
    void testMercure() {
        APIReader reader = new APIReader();
        Planet mercure = new Planet(reader.readBodyFromAPI("mercure"));
        assertAll(
                () -> assertEquals(5.790905E7, mercure.getSemiMajorAxis()),
                () -> assertEquals(46001200, mercure.getPerihelion()),
                () -> assertEquals(69816900, mercure.getAphelion()),
                () -> assertEquals(2439.4, mercure.getMeanRadius()),
                () -> assertEquals("Mercury", mercure.getName()),
                () -> assertEquals(6250, mercure.getSCALE_RADIUS_VALUE())
        );
    }

    @Test
    void testVenus() {
        APIReader reader = new APIReader();
        Planet venus = new Planet(reader.readBodyFromAPI("venus"));
        assertAll(
                () -> assertEquals(1.08208475E8, venus.getSemiMajorAxis()),
                () -> assertEquals(107477000, venus.getPerihelion()),
                () -> assertEquals(108939000, venus.getAphelion()),
                () -> assertEquals(6051.8, venus.getMeanRadius()),
                () -> assertEquals("Venus", venus.getName()),
                () -> assertEquals(6250, venus.getSCALE_RADIUS_VALUE())
        );
    }

    @Test
    void testMars() {
        APIReader reader = new APIReader();
        Planet mars = new Planet(reader.readBodyFromAPI("mars"));
        assertAll(
                () -> assertEquals(2.279392E8, mars.getSemiMajorAxis()),
                () -> assertEquals(206700000, mars.getPerihelion()),
                () -> assertEquals(249200000, mars.getAphelion()),
                () -> assertEquals(3389.5, mars.getMeanRadius()),
                () -> assertEquals("Mars", mars.getName()),
                () -> assertEquals(6250, mars.getSCALE_RADIUS_VALUE())
        );
    }

    @Test
    void testJupiter() {
        APIReader reader = new APIReader();
        Planet jupiter = new Planet(reader.readBodyFromAPI("jupiter"));
        assertAll(
                () -> assertEquals(7.78340821E8, jupiter.getSemiMajorAxis()),
                () -> assertEquals(7.40379835E8, jupiter.getPerihelion()),
                () -> assertEquals(816620000, jupiter.getAphelion()),
                () -> assertEquals(69911, jupiter.getMeanRadius()),
                () -> assertEquals("Jupiter", jupiter.getName()),
                () -> assertEquals(6250, jupiter.getSCALE_RADIUS_VALUE())
        );
    }

    @Test
    void testSaturne() {
        APIReader reader = new APIReader();
        Planet saturne = new Planet(reader.readBodyFromAPI("saturne"));
        assertAll(
                () -> assertEquals(1.426666422E9, saturne.getSemiMajorAxis()),
                () -> assertEquals(1.349823615E9, saturne.getPerihelion()),
                () -> assertEquals(1.503509229E9, saturne.getAphelion()),
                () -> assertEquals(58232, saturne.getMeanRadius()),
                () -> assertEquals("Saturn", saturne.getName()),
                () -> assertEquals(6250, saturne.getSCALE_RADIUS_VALUE())
        );
    }

    @Test
    void testUranus() {
        APIReader reader = new APIReader();
        Planet uranus = new Planet(reader.readBodyFromAPI("uranus"));
        assertAll(
                () -> assertEquals(2.870658186E9, uranus.getSemiMajorAxis()),
                () -> assertEquals(2.734998229E9, uranus.getPerihelion()),
                () -> assertEquals(3.006318143E9, uranus.getAphelion()),
                () -> assertEquals(25362, uranus.getMeanRadius()),
                () -> assertEquals("Uranus", uranus.getName()),
                () -> assertEquals(6250, uranus.getSCALE_RADIUS_VALUE())
        );
    }

    @Test
    void testNeptune() {
        APIReader reader = new APIReader();
        Planet neptune = new Planet(reader.readBodyFromAPI("neptune"));
        assertAll(
                () -> assertEquals(4.498396441E9, neptune.getSemiMajorAxis()),
                () -> assertEquals(4.459753056E9, neptune.getPerihelion()),
                () -> assertEquals(4.537039826E9, neptune.getAphelion()),
                () -> assertEquals(24622, neptune.getMeanRadius()),
                () -> assertEquals("Neptune", neptune.getName()),
                () -> assertEquals(6250, neptune.getSCALE_RADIUS_VALUE())
        );
    }
}
