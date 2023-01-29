package Controller;

import Controller.Calculators.OrbitCalculator;
import Controller.Calculators.PlanetCalculator;
import Controller.Calculators.PositionCalculator;

import Enum.*;

import java.util.ArrayList;
import javafx.util.Duration;

import Model.Sun;
import Model.Planet;

import View.MainFrame;

/**
 * @author
 * @author
 *
 * Controller.java is the class that communicates with the GUI and model classes.
 * It updates the GUI based on information in the model classes.
 */

public class Controller
{
    private APIReader reader;
    private OrbitCalculator orbitCalculator;
    private PlanetCalculator planetCalculator;
    private PositionCalculator positionCalculator;
    private MainFrame mainframe;
    private Sun sun;

    private ArrayList<Planet> planetArrayList;

    public Controller()
    {
        reader = new APIReader();
        orbitCalculator = new OrbitCalculator();
        planetCalculator = new PlanetCalculator();
        positionCalculator = new PositionCalculator();
        this.sun = new Sun(reader.readBodyFromAPI(Stars.soleil.toString()));
        sun.setYCord(0);
        sun.setXCord(0);

        planetArrayList = createPlanetArray(1); //No duration modifier should be added here.

        mainframe = new MainFrame(this, this.sun);
    }

    public ArrayList<Planet> getPlanetArrayList()
    {
        return planetArrayList;
    }

    /**
     * Creates an ArrayList with planets and their orbits generated.
     * @return An ArrayList filled with newly generated planet objects
     */
    public ArrayList<Planet> createPlanetArray(double durationModifier)
    {
        ArrayList<Planet> newPlanets = new ArrayList<>();

        //Reads the planets from the API
        for(Planets p : Planets.values())
        {
            newPlanets.add(new Planet(reader.readBodyFromAPI(p.toString())));
        }

        //Add orbits to the planets
        for (Planet p : newPlanets)
        {
            p.setPlanetOrbit(orbitCalculator.getPlanetSunOrbit(sun, p));//Create orbit
        }

        //Sets planet duration [*1000 is to make it into seconds instead of milliseconds]
        for (Planet planet: newPlanets)
        {
            Duration d = new Duration((planetCalculator.calculatePlanetSunOrbitTime(sun, planet)
                    *1000)/durationModifier);

            planet.setDuration(d); //* makes them go slower and / makes them go faster

            System.out.println(planet.getName() + "\t" + d);
        }

        //Set the PathTransition
        for (int i = 0; i < newPlanets.size() ; i++)
        {
            newPlanets.get(i).createPathTransition();
        }

        return newPlanets;
    }
    
    public static void main(String[] args)
{
    Controller controller = new Controller();
}
}


