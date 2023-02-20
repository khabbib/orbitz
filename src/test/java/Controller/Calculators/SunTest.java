package Controller.Calculators;

import Model.Enum.Stars;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import Model.Sun;
import Controller.APIReader;
public class SunTest {

    @Test
    void getSunMass() {
        APIReader reader = new APIReader();
        Sun sun = new Sun(reader.readBodyFromAPI(Stars.soleil.toString()));
        double expected = 1.489E58;
        double actual = sun.getMass();
        assertEquals(expected, actual);
    }
}
