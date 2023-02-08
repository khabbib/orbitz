package Controller;

import Controller.Calculators.OrbitCalculator;
import Controller.Calculators.PlanetCalculator;
import Controller.Calculators.PositionCalculator;

import Enum.*;

import java.util.ArrayList;
import java.util.HashMap;

import Model.Orbit;
import javafx.animation.PathTransition;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Sphere;
import javafx.util.Duration;

import Model.Sun;
import Model.Planet;

import View.MainFrame;

/**
 * @author
 * @author Controller.java is the class that communicates with the GUI and model classes.
 * It updates the GUI based on information in the model classes.
 */

public class Controller {
    private APIReader reader = new APIReader();
    private OrbitCalculator orbitCalculator = new OrbitCalculator();
    private PlanetCalculator planetCalculator = new PlanetCalculator();
    private PositionCalculator positionCalculator = new PositionCalculator();
    private Sun sun = new Sun(reader.readBodyFromAPI(Stars.soleil.toString()));

    private HashMap<Planet, HashMap<String,Object>> planetHashMapHashMap = new HashMap<>();
    private ArrayList<Planet> planetArrayList;
    private MainFrame mainframe;

    public Controller() {
        sun.setYCord(0);
        sun.setXCord(0);
        planetArrayList = createPlanetArray(1); //No duration modifier should be added here.
        mainframe = new MainFrame(this, this.sun);
    }

    public ArrayList<Planet> getPlanetArrayList() {
        return planetArrayList;
    }

    /**
     * Creates an ArrayList with planets and their orbits generated.
     *
     * @return An ArrayList filled with newly generated planet objects
     */
    public ArrayList<Planet> createPlanetArray(double durationModifier) {
        ArrayList<Planet> newPlanets = new ArrayList<>();

        //Reads the planets from the API
        for (Planets p : Planets.values()) {
            newPlanets.add(new Planet(reader.readBodyFromAPI(p.toString())));
        }

        //Add orbits to the planets
        for (Planet p : newPlanets) {
            p.setPlanetOrbit(orbitCalculator.getPlanetSunOrbit(sun, p));//Create orbit
            Orbit orbit = p.getPlanetOrbit();
            planetHashMapHashMap.put(p,new HashMap<>());
            planetHashMapHashMap.get(p).put("ellipse",MainFrame.createElipse(orbit.getXCord(), orbit.getYCord(), orbit.getWidth(), orbit.getHeight()));
        }

        //Sets planet duration [*1000 is to make it into seconds instead of milliseconds]
        for (Planet planet : newPlanets) {
            Duration d = new Duration(((planetCalculator.calculatePlanetSunOrbitTime(sun, planet)
                    * 1000 / durationModifier) * 1000000000) * 10000);
            planet.setDuration(d); //* makes them go slower and / makes them go faster
            System.out.println(planet.getName() + "\t" + d);
        }

        return newPlanets;
    }

    public Ellipse getEllipse(Planet planet) {
        return (Ellipse) planetHashMapHashMap.get(planet).get("ellipse");
    }

    public Sphere getSphere(Planet planet) {
        return (Sphere) planetHashMapHashMap.get(planet).get("sphere");
    }

    public PathTransition getPathTransition(Planet planet) {
        return (PathTransition) planetHashMapHashMap.get(planet).get("path");
    }

    public PositionCalculator getPositionCalculator() {
        return positionCalculator;
    }

    public void putHashValue(Planet planet, String key, Object object) {
        planetHashMapHashMap.get(planet).put(key,object);
    }

    public static void main(String[] args) {
        Controller controller = new Controller();
    }
}


