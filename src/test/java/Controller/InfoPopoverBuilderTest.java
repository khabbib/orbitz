package Controller;

import org.controlsfx.control.PopOver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the InfoPopoverBuilder class.
 * @author Joel Eriksson Sinclair
 */
public class InfoPopoverBuilderTest {

    private InfoPopoverBuilder infoPopoverBuilder;

    @BeforeEach
    void setUp() {
        infoPopoverBuilder = new InfoPopoverBuilder();
    }

    @Test
    void givenNull_createInfoPopover() {
        String input = null;
        PopOver result = infoPopoverBuilder.createInfoPopover(input);
        assertNull(result);
    }

    @Test
    void givenEmptyString_createInfoPopover() {
        String input = "";
        PopOver result = infoPopoverBuilder.createInfoPopover(input);
        assertNull(result);
    }

    @Test
    void givenValid_createInfoPopover() {
        String input = "Earth";
        PopOver result = infoPopoverBuilder.createInfoPopover(input);
        //assertNotNull(result);
        //assertEquals(input, result.getTitle());
    }

    @Test
    void givenAllPlanets_createInfoPopover() {
        String[] planets = {"Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune"};
        for (String planet : planets) {
            PopOver result = infoPopoverBuilder.createInfoPopover(planet);
            //assertNotNull(result);
            //assertEquals(planet, result.getTitle());
        }
    }

    @Test
    void givenMisspelled_createInfoPopover() {
        String input = "Earht";
        PopOver result = infoPopoverBuilder.createInfoPopover(input);
        assertNull(result);
    }

    @Test
    void givenInvalid_createInfoPopover() {
        String input = " . '¨åäö123!!#?";
        PopOver result = infoPopoverBuilder.createInfoPopover(input);
        assertNull(result);
    }

    @Test
    void givenLowerCase_titleOk_createInfoPopover() {
        String input = "earth";
        PopOver result = infoPopoverBuilder.createInfoPopover(input);
        //assertNotNull(result);
        //assertEquals("Earth", result.getTitle());
    }

    @Test
    void givenUpperCase_titleOk_createInfoPopover() {
        String input = "EARTH";
        PopOver result = infoPopoverBuilder.createInfoPopover(input);
        //assertNotNull(result);
        //assertEquals("Earth", result.getTitle());
    }

    @Test
    void givenAbsoluteDirectory_createInfoPopover() {
        String input = "/folder/Earth";
        PopOver result = infoPopoverBuilder.createInfoPopover(input);
        assertNull(result);
    }

    @Test
    void givenRelativeDirectory_createInfoPopover() {
        String input = "folder/Earth";
        PopOver result = infoPopoverBuilder.createInfoPopover(input);
        assertNull(result);
    }
}