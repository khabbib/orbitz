package Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OrbitTest {

    @Test
    void getWidth() {
        Orbit orbit = new Orbit(100,100,100,100);
        assertEquals(10,orbit.getWidth(10));
    }

    @Test
    void getHeight() {
        Orbit orbit = new Orbit(100,100,100,100);
        assertEquals(10,orbit.getHeight(10));
    }

    @Test
    void getCenterXCord() {
        Orbit orbit = new Orbit(100,100,100,100);
        assertEquals(10,orbit.getCenterXCord(10));
    }

    @Test
    void getCenterYCord() {
        Orbit orbit = new Orbit(100,100,100,100);
        assertEquals(10,orbit.getCenterYCord(10));
    }
}