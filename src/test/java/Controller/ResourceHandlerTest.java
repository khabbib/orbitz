package Controller;

import javafx.scene.image.Image;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the ResourceHandler class.
 * @author Joel Eriksson Sinclair
 */
class ResourceHandlerTest {
    //region getPlanetInfoTests

    // TODO: How many info lines do we expect for each planet?
    @Test
    void givenValid_getPlanetInfo() {
        String[] result = ResourceHandler.getPlanetInfo("Earth");
        assertNotNull(result);
        assertTrue(result.length > 0);
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

    // TODO: Again, how many info lines do we expect for each planet?
    @Test
    void givenAllPlanets_getPlanetInfo() {
        String[] planets = {"Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune"};
        for (String planet : planets) {
            String[] result = ResourceHandler.getPlanetInfo(planet);
            assertNotNull(result);
            assertTrue(result.length > 0);
        }
    }

    //endregion

    //region getPlanetsMoonInfoTests

    @Test
    void givenValid_getPlanetsMoonInfo() {
        String[] result = ResourceHandler.getPlanetsMoonInfo("Earth");
        assertNotNull(result);
        assertTrue(result.length > 0);
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
            assertTrue(result.length > 0);
        }
    }

    // TODO: Do we expect a empty array or null? Or place holder text?
    @Test
    void givenNoMoons_getPlanetsMoonInfo() {
        String[] result = ResourceHandler.getPlanetsMoonInfo("Mercury");
        assertNotNull(result);
        assertEquals(0, result.length);
    }

    @Test
    void givenAllPlanetsWithNoMoons_getPlanetsMoonInfo() {
        String[] planetsWithNoMoons = {"Mercury", "Venus"};
        for (String planet : planetsWithNoMoons) {
            String[] result = ResourceHandler.getPlanetsMoonInfo(planet);
            assertNotNull(result);
            assertEquals(0, result.length);
        }
    }

    //endregion

    //region getPlanetImagesTests

    @Test
    void givenValid_getPlanetImageURLs() {
        String[] result = ResourceHandler.getPlanetImageURLs("Earth");
        assertNotNull(result);
        assertTrue(result.length > 0);
    }

    @Test
    void givenNull_getPlanetImageURLs() {
        String[] result = ResourceHandler.getPlanetImageURLs(null);
        assertNull(result);
    }

    @Test
    void givenEmptyString_getPlanetImageURLs() {
        String[] result = ResourceHandler.getPlanetImageURLs("");
        assertNull(result);
    }

    @Test
    void givenMisspelled_getPlanetImageURLs() {
        String[] result = ResourceHandler.getPlanetImageURLs("Earht");
        assertNull(result);
    }

    @Test
    void givenInvalid_getPlanetImageURLs() {
        String[] result = ResourceHandler.getPlanetImageURLs(" . '¨åäö123!!#?");
        assertNull(result);
    }

    @Test
    void givenAllPlanets_getPlanetImageURLs() {
        String[] planets = {"Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune"};
        for (String planet : planets) {
            String[] result = ResourceHandler.getPlanetImageURLs(planet);
            assertNotNull(result);
            assertTrue(result.length > 0);
        }
    }

    //endregion

    //region getPlanetsMoonImagesTests

    @Test
    void givenValid_getPlanetsMoonImageURLs() {
        String[] result = ResourceHandler.getPlanetsMoonImageURLs("Earth");
        assertNotNull(result);
        assertTrue(result.length > 0);
    }

    @Test
    void givenNull_getPlanetsMoonImageURLs() {
        String[] result = ResourceHandler.getPlanetsMoonImageURLs(null);
        assertNull(result);
    }

    @Test
    void givenEmptyString_getPlanetsMoonImageURLs() {
        String[] result = ResourceHandler.getPlanetsMoonImageURLs("");
        assertNull(result);
    }

    @Test
    void givenMisspelled_getPlanetsMoonImageURLs() {
        String[] result = ResourceHandler.getPlanetsMoonImageURLs("Earht");
        assertNull(result);
    }

    @Test
    void givenInvalid_getPlanetsMoonImageURLs() {
        String[] result = ResourceHandler.getPlanetsMoonImageURLs(" . '¨åäö123!!#?");
        assertNull(result);
    }

    @Test
    void givenAllPlanetsWithMoons_getPlanetsMoonImageURLs() {
        String[] planetsWithMoons = {"Earth", "Jupiter", "Saturn", "Uranus", "Neptune", "Mars"};
        for (String planet : planetsWithMoons) {
            String[] result = ResourceHandler.getPlanetsMoonImageURLs(planet);
            assertNotNull(result);
            assertTrue(result.length > 0);
        }
    }

    @Test
    void givenNoMoons_getPlanetsMoonImageURLs() {
        String[] result = ResourceHandler.getPlanetsMoonImageURLs("Mercury");
        assertNotNull(result);
        assertEquals(0, result.length);
    }

    @Test
    void givenAllPlanetsWithNoMoons_getPlanetsMoonImageURLs() {
        String[] planetsWithNoMoons = {"Mercury", "Venus"};
        for (String planet : planetsWithNoMoons) {
            String[] result = ResourceHandler.getPlanetsMoonImageURLs(planet);
            assertNotNull(result);
            assertEquals(0, result.length);
        }
    }

    //endregion
}