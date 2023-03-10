package Controller;

import org.junit.jupiter.api.Test;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the ResourceHandler class.
 * @author Joel Eriksson Sinclair
 */
public class ResourceHandlerTest {
    //region getPlanetInfoTests

    @Test
    void givenValid_getPlanetInfo() {
        String[] result = ResourceHandler.getPlanetInfo("Earth");
        assertNotNull(result);
        assertTrue(result.length > 1);
    }

    @Test
    void givenNull_getPlanetInfo() {
        String[] result = ResourceHandler.getPlanetInfo(null);
        assertNull(result);
    }

    @Test
    void givenEmptyString_getPlanetInfo() {
        String[] result = ResourceHandler.getPlanetInfo("");
        assertNull(result);
    }

    @Test
    void givenMisspelled_getPlanetInfo() {
        String[] result = ResourceHandler.getPlanetInfo("Earht");
        assertNull(result);
    }

    @Test
    void givenInvalid_getPlanetInfo() {
        String[] result = ResourceHandler.getPlanetInfo(" . '¨åäö123!!#?");
        assertNull(result);
    }

    @Test
    void givenAllPlanets_getPlanetInfo() {
        String[] planets = {"Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune"};
        for (String planet : planets) {
            String[] result = ResourceHandler.getPlanetInfo(planet);
            assertNotNull(result);
            assertTrue(result.length > 1);
        }
    }

    @Test
    void givenAllPlanets_getPlanetInfo_checkInfoLength() {
        String[] planets = {"Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune"};
        for (String planet : planets) {
            String[] result = ResourceHandler.getPlanetInfo(planet);
            for (String s : result) {
                assertTrue(s.length() > 10);
                assertTrue(s.length() < 200);
            }
        }
    }

    //endregion

    //region getPlanetsMoonInfoTests

    @Test
    void givenValid_getPlanetsMoonInfo() {
        String[] result = ResourceHandler.getPlanetsMoonInfo("Earth");
        assertNotNull(result);
        assertTrue(result.length > 1);
    }

    @Test
    void givenNull_getPlanetsMoonInfo() {
        String[] result = ResourceHandler.getPlanetsMoonInfo(null);
        assertNull(result);
    }

    @Test
    void givenEmptyString_getPlanetsMoonInfo() {
        String[] result = ResourceHandler.getPlanetsMoonInfo("");
        assertNull(result);
    }

    @Test
    void givenMisspelled_getPlanetsMoonInfo() {
        String[] result = ResourceHandler.getPlanetsMoonInfo("Earht");
        assertNull(result);
    }

    @Test
    void givenInvalid_getPlanetsMoonInfo() {
        String[] result = ResourceHandler.getPlanetsMoonInfo(" . '¨åäö123!!#?");
        assertNull(result);
    }

    @Test
    void givenAllPlanetsWithMoons_getPlanetsMoonInfo() {
        String[] planetsWithMoons = {"Earth", "Jupiter", "Saturn", "Uranus", "Neptune", "Mars"};
        for (String planet : planetsWithMoons) {
            String[] result = ResourceHandler.getPlanetsMoonInfo(planet);
            assertNotNull(result);
            assertTrue(result.length > 1);
        }
    }

    @Test
    void givenNoMoons_getPlanetsMoonInfo() {
        String[] result = ResourceHandler.getPlanetsMoonInfo("Mercury");
        //assertNotNull(result);
        //assertEquals(0, result.length);
    }

    @Test
    void givenAllPlanetsWithNoMoons_getPlanetsMoonInfo() {
        String[] planetsWithNoMoons = {"Mercury", "Venus"};
        for (String planet : planetsWithNoMoons) {
            String[] result = ResourceHandler.getPlanetsMoonInfo(planet);
            //assertNotNull(result);
            //assertEquals(0, result.length);
        }
    }

    @Test
    void givenAllPlanetsWithMoons_getPlanetsMoonInfo_checkInfoLength() {
        String[] planetsWithMoons = {"Earth", "Jupiter", "Saturn", "Uranus", "Neptune", "Mars"};
        for (String planet : planetsWithMoons) {
            String[] result = ResourceHandler.getPlanetsMoonInfo(planet);
            for (String s : result) {
                assertTrue(s.length() > 10);
                assertTrue(s.length() < 200);
            }
        }
    }

    //endregion

    //region getPlanetImagesTests

    @Test
    void givenValid_getPlanetImageURLs() {
        URL[] result = ResourceHandler.getPlanetImageURLs("Earth");
        assertNotNull(result);
        assertTrue(result.length > 0);
    }

    @Test
    void givenNull_getPlanetImageURLs() {
        URL[] result = ResourceHandler.getPlanetImageURLs(null);
        assertNull(result);
    }

    @Test
    void givenEmptyString_getPlanetImageURLs() {
        URL[] result = ResourceHandler.getPlanetImageURLs("");
        assertNull(result);
    }

    @Test
    void givenMisspelled_getPlanetImageURLs() {
        URL[] result = ResourceHandler.getPlanetImageURLs("Earht");
        assertNull(result);
    }

    @Test
    void givenInvalid_getPlanetImageURLs() {
        URL[] result = ResourceHandler.getPlanetImageURLs(" . '¨åäö123!!#?");
        assertNull(result);
    }

    @Test
    void givenAllPlanets_getPlanetImageURLs() {
        String[] planets = {"Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune"};
        for (String planet : planets) {
            URL[] result = ResourceHandler.getPlanetImageURLs(planet);
            assertNotNull(result);
            assertTrue(result.length > 0);
        }
    }

    //endregion

    //region getPlanetsMoonImagesTests

    @Test
    void givenValid_getPlanetsMoonImageURLs() {
        URL[] result = ResourceHandler.getPlanetsMoonImageURLs("Earth");
        assertNotNull(result);
        assertTrue(result.length > 0);
    }

    @Test
    void givenNull_getPlanetsMoonImageURLs() {
        URL[] result = ResourceHandler.getPlanetsMoonImageURLs(null);
        assertNull(result);
    }

    @Test
    void givenEmptyString_getPlanetsMoonImageURLs() {
        URL[] result = ResourceHandler.getPlanetsMoonImageURLs("");
        assertNull(result);
    }

    @Test
    void givenMisspelled_getPlanetsMoonImageURLs() {
        URL[] result = ResourceHandler.getPlanetsMoonImageURLs("Earht");
        assertNull(result);
    }

    @Test
    void givenInvalid_getPlanetsMoonImageURLs() {
        URL[] result = ResourceHandler.getPlanetsMoonImageURLs(" . '¨åäö123!!#?");
        assertNull(result);
    }

    @Test
    void givenAllPlanetsWithMoons_getPlanetsMoonImageURLs() {
        String[] planetsWithMoons = {"Earth", "Jupiter", "Saturn", "Uranus", "Neptune", "Mars"};
        for (String planet : planetsWithMoons) {
            URL[] result = ResourceHandler.getPlanetsMoonImageURLs(planet);
            assertNotNull(result);
            assertTrue(result.length > 0);
        }
    }

    @Test
    void givenNoMoons_getPlanetsMoonImageURLs() {
        URL[] result = ResourceHandler.getPlanetsMoonImageURLs("Mercury");
        //assertNotNull(result);
        //assertEquals(0, result.length);
    }

    @Test
    void givenAllPlanetsWithNoMoons_getPlanetsMoonImageURLs() {
        String[] planetsWithNoMoons = {"Mercury", "Venus"};
        for (String planet : planetsWithNoMoons) {
            URL[] result = ResourceHandler.getPlanetsMoonImageURLs(planet);
            //assertNotNull(result);
            //assertEquals(0, result.length);
        }
    }

    //endregion
}