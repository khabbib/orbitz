package Controller;

import Controller.Calculators.OrbitCalculator;
import Controller.Calculators.PositionCalculator;
import java.util.ArrayList;
import java.util.HashMap;

import Model.Enum.Planets;
import Model.Enum.Stars;
import Model.Orbit;
import javafx.animation.PathTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private MainFrame mainframe;
    private OrbitCalculator orbitCalculator = new OrbitCalculator();
    private PositionCalculator positionCalculator = new PositionCalculator();
    private Sun sun = new Sun(reader.readBodyFromAPI(Stars.soleil.toString()));
    private double durationModifier = 150000;
    private final int SCALE_VALUE = 37500;

    private HashMap<Planet, HashMap<String,Object>> planetHashMapHashMap = new HashMap<>();
    private ArrayList<Planet> planetArrayList;


    public Controller() {
        sun.setYCord(0);
        sun.setXCord(0);
//        planetArrayList = createPlanetArray(); //No duration modifier should be added here.
        mainframe = new MainFrame(this);
    }

    public ArrayList<Planet> getPlanetArrayList() {
        return planetArrayList;
    }

    /**
     * Creates an ArrayList with planets and their orbits generated.
     * @return An ArrayList filled with newly generated planet objects
     */
    public ArrayList<Model.Planet> createPlanetArray() {
        ArrayList<Planet> newPlanets = new ArrayList<>();

        //Reads the planets from the API
        for (Planets p : Planets.values()) {
            newPlanets.add(new Planet(reader.readBodyFromAPI(p.toString())));
        }

        //Add orbits to the planets
        for (Planet p : newPlanets) {
            p.setPlanetOrbit(orbitCalculator.getOrbit(p));//Create orbit
            Orbit orbit = p.getPlanetOrbit();
            planetHashMapHashMap.put(p, new HashMap<>());
            planetHashMapHashMap.get(p).put("ellipse", MainFrame.createEllipse(orbit.getCenterXCord(SCALE_VALUE), orbit.getCenterYCord(SCALE_VALUE), orbit.getWidth(SCALE_VALUE), orbit.getHeight(SCALE_VALUE)));
            planetHashMapHashMap.get(p).put("planet", MainFrame.createPlanetImageView(p));
        }

        //Sets planet duration [*1000 is to make it into seconds instead of milliseconds]
        for (Planet planet : newPlanets) {
            Duration duration = new Duration(((orbitCalculator.getOrbitalPeriod(planet.getSemiMajorAxis()) * 1000 / durationModifier) * 1000000000) * 10000);
            planetHashMapHashMap.get(planet).put("duration", duration);

            System.out.println(planet.getName() + "\t" + duration);
        }
        planetArrayList = newPlanets;
        return newPlanets;
    }

    public Ellipse getEllipse(Planet planet) {
        return (Ellipse) planetHashMapHashMap.get(planet).get("ellipse");
    }

    public ImageView getPlanetImageView(Planet planet) {
        return (ImageView) planetHashMapHashMap.get(planet).get("planet");
    }

    public PathTransition getPathTransition(Planet planet) {
        return (PathTransition) planetHashMapHashMap.get(planet).get("path");
    }

    public Duration getDuration(Planet planet) {
        return (Duration) planetHashMapHashMap.get(planet).get("duration");
    }
    public PositionCalculator getPositionCalculator() {
        return positionCalculator;
    }

    public void putHashValue(Planet planet, String key, Object object) {
        planetHashMapHashMap.get(planet).put(key,object);
    }

    public Sun getSun(){
        return sun;
    }

    public static void main(String[] args) {
        Controller controller = new Controller();
    }
}