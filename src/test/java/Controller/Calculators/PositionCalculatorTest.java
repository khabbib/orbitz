package Controller.Calculators;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionCalculatorTest {

    @Test
    void setDay() {
        PositionCalculator calc = new PositionCalculator();
        assertAll(() -> assertEquals("0", String.format("%.0f", calc.calculateDateDifference(2000,1,1))),
                () -> assertEquals("36552", String.format("%.0f", calc.calculateDateDifference(2100,2,28))),
                () -> assertEquals("8403", String.format("%.0f", calc.calculateDateDifference(2023,2,3))),
                () -> assertEquals(-1, calc.calculateDateDifference(1999,12,31)),
                () -> assertEquals(-1, calc.calculateDateDifference(2100, 2, 29))
        );
    }

    @Test
    void calcY() {
        PositionCalculator calc = new PositionCalculator();
        assertAll(() -> assertTrue(Math.abs(calc.calcY(30.058538388859237, 310.24671355352234, 0.00862406628576389)-
                (-22.94175718133605197806671555183756573880085314751717618162))<0.001));
    }

    @Test
    void calcX() {
        PositionCalculator calc = new PositionCalculator();
        assertAll(() -> assertTrue(Math.abs(calc.calcX(30.058538388859237, 310.24671355352234, 0.00862406628576389)
                -19.161184070362232691077664214417349304749009683942162628612)<0.001));
    }
}