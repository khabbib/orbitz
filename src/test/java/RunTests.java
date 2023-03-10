import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import Model.SunTest;
import Model.PlanetTest;
import Model.OrbitTest;
import Controller.Calculators.OrbitCalculator;
import Controller.Calculators.PositionCalculator;
import Controller.InfoPopoverBuilderTest;
import Controller.QuizControllerTest;
import Controller.ResourceHandlerTest;


@RunWith(Suite.class)
@SuiteClasses({
        OrbitTest.class,
        PlanetTest.class,
        SunTest.class,
        OrbitCalculator.class,
        PositionCalculator.class,
        InfoPopoverBuilderTest.class,
        QuizControllerTest.class,
        ResourceHandlerTest.class
})
public class RunTests {
}
