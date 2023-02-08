package View;

import Controller.Controller;

import Model.Planet;
import Model.Sun;
import Model.Theme;

import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.image.Image;
import javafx.util.Duration;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;

/**
 * @author Albin Ahlbeck
 * @author Simon M�tegen
 * @author Lanna Maslo
 * @author Manna Manojlovic
 * @author Marcus Svensson
 * MainFrame is the main window which contains various graphical components
 */
public class MainFrame extends JFrame {
    private final int WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
    private final int HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
    private final int MAX_SLIDER_VALUE = 30;

    private JLabel lblTitle;
    private JLabel lblTheme;


    private ArrayList<Planet> guiPlanetList;
    private ArrayList<Planet> newPlanets;

    private Theme currentTheme;

    private JFXPanel orbitPanel;
    private JFXPanel mediaPanel;

    private JPanel overheadPanel;

    private MainInfoFrame mainInfoFrame;

    private LoadingScreen loadingScreen = new LoadingScreen();

    private StackPane root;

    private JSlider timeSlider;
    private JSlider musicSlider;

    private MediaBar mediaBar;

    private SliderListener sliderListener;

    private Controller controller;

    private Sun sun;

    private double startDragX;
    private double startDragY;
    private double orgTransX;
    private double orgTransY;

    private int moveSpeed = 20;
    private int zoomSpeed = 10;

    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();


    /**
     * @param inController gains a reference to controller in order to fetch the planet list
     * @author Albin Ahlbeck
     * @author Simon M�tegen
     * Constructs the GUI components and starts the Java-FX window.
     */
    public MainFrame(Controller inController, Sun inSun) {
        this.controller = inController;
        this.sun = inSun;
        initFonts();
        guiPlanetList = controller.getPlanetArrayList(); // copy the planet list from controller
        orbitPanel = new JFXPanel();
        mediaPanel = new JFXPanel();
        overheadPanel = new JPanel();

        sliderListener = new SliderListener();
        timeSlider = new JSlider();
        musicSlider = new JSlider();

        //btnCreateTheme = new JButton("Create theme");
        //btnCreateTheme.addActionListener(createThemeListener);
        lblTheme = new JLabel("Select theme");
        lblTheme.setFont(new Font("Nasalization Rg", Font.PLAIN, 16));

        lblTitle = new JLabel();
        lblTitle.setPreferredSize(new Dimension(700, 80));
        lblTitle.setText("Orbitz");
        lblTitle.setFont(new Font("Earth Orbiter", Font.PLAIN, 55));
        lblTitle.setOpaque(true);

        // Sets up the JFrame
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setTitle("Orbitz");

        ImageIcon solarSystem = new ImageIcon(getClass().getResource("/Images/orbitz.png").toExternalForm());
        setIconImage(solarSystem.getImage());

        orbitPanel.setPreferredSize(new Dimension(getWidth(), getHeight() - 160));

        Font f = new Font("Arial", Font.BOLD, 8);
        JLabel labelMin = new JLabel("MIN");
        JLabel labelMax = new JLabel("MAX");
        labelMin.setFont(f);
        labelMax.setFont(f);

        Hashtable<Integer, JLabel> labelTableM = new Hashtable<>();
        labelTableM.put(2, labelMin);
        labelTableM.put(19, labelMax);

        //comboBoxThemeListener = new ComboBoxThemeListener();

        musicSlider.setOrientation(JSlider.VERTICAL);
        musicSlider.setPreferredSize(new Dimension(10, 20));
        musicSlider.setMinimum(0);
        musicSlider.setMaximum(20);
        musicSlider.setValue(10);
        musicSlider.setSnapToTicks(true);
        musicSlider.setLabelTable(labelTableM);
        musicSlider.setPaintLabels(true);

        // Sets up the JSlider and components related to it

        timeSlider.setValue(0);
        timeSlider.setMaximum(MAX_SLIDER_VALUE);
        timeSlider.setPaintLabels(true);

        timeSlider.setPreferredSize(new Dimension(700, 70));
        timeSlider.setPaintTicks(true);
        timeSlider.setMajorTickSpacing(10);
        timeSlider.setSnapToTicks(true);
        timeSlider.addMouseListener(sliderListener);


        lblTheme.setPreferredSize(new Dimension(100, 70));
        mediaPanel.setPreferredSize(new Dimension(800, 70));
        mediaPanel.setBackground(null);
        // Sets up overheadPanel
        overheadPanel.setLayout(new FlowLayout());
        // set opaque
        overheadPanel.setOpaque(true);
        timeSlider.setOpaque(true);

        lblTitle.setOpaque(false);
        mediaPanel.setOpaque(true);
        overheadPanel.setPreferredSize(new Dimension(1400, 160));
        overheadPanel.add(lblTitle);
        overheadPanel.add(timeSlider);
        overheadPanel.add(mediaPanel);

        add(orbitPanel, BorderLayout.CENTER);

        add(overheadPanel, BorderLayout.NORTH);


        currentTheme = new Theme("Black and White", Color.BLACK, Color.WHITE, javafx.scene.paint.Color.BLACK, javafx.scene.paint.Color.WHITE);
        setColors(currentTheme);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                initFXMedia(mediaPanel);
                initFxOrbit(orbitPanel);
            }
        });
        setVisible(true);
    }

    /**
     * @param fxPanel The JavaFX panel to be created
     * @author Albin Ahlbeck
     * @author Lanna Maslo
     * Creates a new scene from createScene and adds it to the Java FX window
     * Sets background music
     * @version 1.0
     */
    private void initFxOrbit(JFXPanel fxPanel) {
        // This method is invoked on JavaFX thread
        Scene scene = createScene(guiPlanetList); // default background
        fxPanel.setScene(scene);
    }

    /**
     * @param fxPanel The JavaFX panel to be created
     * @author Albin Ahlbeck
     * Creates a new scene from createScene and adds it to the Java FX window
     * Sets background music
     */
    private void initFXMedia(JFXPanel fxPanel) {
        // This method is invoked on JavaFX thread
        Scene scene = createMedia(); // default background
        fxPanel.setScene(scene);
    }

    /**
     * @author Albin Ahlbeck
     * @author Lanna Maslo
     * @author Manna Manojlovic
     * Creates the Java-FX scene
     */
    private Scene createScene(ArrayList<Planet> planetArrayList) {
        root = new StackPane();
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        root.setBackground(null);
        scene.setFill(javafx.scene.paint.Color.BLACK);
        setupCamera(scene);
        handleMouse(root);

        System.out.println("Scene: Width: " + scene.getWidth() + "Height: " + scene.getHeight());
        System.out.println("Root: Width: " + root.getWidth() + "Height: " + root.getHeight());
        placePlanets(root, planetArrayList);
        paintPlanets();
        startOrbits(planetArrayList);
        EventHandler<javafx.scene.input.MouseEvent> eventHandler = new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent mouseEvent) {
                openInfoWindow(determinePlanet((Sphere) mouseEvent.getSource()));
            }

        };

        for (int i = 0; i < planetArrayList.size(); i++) {
            planetArrayList.get(i).getSphereFromPlanet().addEventHandler
                    (javafx.scene.input.MouseEvent.MOUSE_CLICKED, eventHandler);
            planetArrayList.get(i).getSphereFromPlanet().setCursor(Cursor.HAND);
        }

        return scene;
    }

    public Scene createMedia() {
        StackPane mediaPane = new StackPane();
        Scene scene = new Scene(mediaPane, mediaPanel.getWidth(), mediaPanel.getHeight());
        scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
        mediaPane.setBackground(Background.EMPTY);

        mediaBar = new MediaBar(currentTheme);
        mediaPane.getChildren().add(mediaBar);
        return scene;
    }


    /**
     * Finds which planet the the sphere is connected to
     *
     * @author Albin Ahlbeck
     * @version 1.0
     */
    public Planet determinePlanet(Sphere sphere) {
        for (int i = 0; i < guiPlanetList.size(); i++) {
            if (sphere.getId().equals(guiPlanetList.get(i).getName())) {
                return guiPlanetList.get(i);
            }
        }
        return null;
    }

    /**
     * Paints the surface of the planets by calling their individual mappings
     *
     * @author Lanna Maslo
     * @version 1.0
     */
    public void paintPlanets() {
        for (int i = 0; i < guiPlanetList.size(); i++) {
            PhongMaterial map = new PhongMaterial();
            map.setDiffuseMap(new Image(getClass().getResource("/Images/" + guiPlanetList.get(i).getName() + ".jpg").toExternalForm()));
            guiPlanetList.get(i).getSphereFromPlanet().setMaterial(map);
        }
    }

    /**
     * Places all the planets and their orbits in the Java-FX scene
     *
     * @author Albin Ahlbeck
     * @author Lanna Maslo
     * @author Manna Manojlovic
     * @author Marcus Svensson
     * @author Simon M�tegen
     * @version 1.0
     */
    public void placePlanets(Pane root, ArrayList<Planet> planetArrayList) {

        for (Planet planet : planetArrayList) {
            root.getChildren().add(planet.getSphereFromPlanet());
            root.getChildren().add(planet.getPlanetOrbit().getEllipseFromOrbit());
            planet.getPlanetOrbit().getEllipseFromOrbit().toBack();
            planet.getPlanetOrbit().getEllipseFromOrbit().setStroke(currentTheme.getSecondaryPaint());
            System.out.println(planet.getName() + " | X: " + planet.getPlanetOrbit().getXCord());
            //StackPane.setMargin(planet.getPlanetOrbit().getEllipseFromOrbit(), new javafx.geometry.Insets(0, 0, 0, planet.getPlanetOrbit().getXCord() * 2));
            for (Node child : root.getChildren()) {
                if (child.equals(planet.getSphereFromPlanet())) {
                    //child.setLayoutX(planet.getPlanetOrbit().getXCord() * 2);
                    child.setTranslateX(planet.getPlanetOrbit().getHeight());
                    child.setCache(true);
                    Animation animation = new PathTransition(new Duration(40000), planet.getPlanetOrbit().getEllipseFromOrbit(), child);
                    animation.setCycleCount(Animation.INDEFINITE);
                    animation.play();
                    //child.setScaleX(planet.getPlanetOrbit().getXCord() * 2);
                }
            }
            planet.setTooltip();
        }

        /*for (int i = 0; i < planetArrayList.size(); i++)
        {
            root.getChildren().add(planetArrayList.get(i).getSphereFromPlanet()); //Adds planets
            root.getChildren().add(planetArrayList.get(i).getPlanetOrbit().getEllipseFromOrbit());//Add orbits
            planetArrayList.get(i).getPlanetOrbit().getEllipseFromOrbit().toBack();//Moves orbits behind planets
            planetArrayList.get(i).getPlanetOrbit().getEllipseFromOrbit().setStroke(currentTheme.getSecondaryPaint()); // Paint ellipse based on theme
            StackPane.setMargin(planetArrayList.get(i).getPlanetOrbit().getEllipseFromOrbit(),
                    new javafx.geometry.Insets(0, 0, 0, planetArrayList.get(i).getPlanetOrbit().getXCord() * 2));
            planetArrayList.get(i).setTooltip();
            System.out.println(currentTheme.toString());
        }*/

        root.getChildren().add(sun.getSphereFromSun());
    }

    /**
     * @author Lanna Maslo
     * @author Simon M�tegen
     * Sets the viewing perspective and enables a zoom-function
     */
    public void setupCamera(Scene scene) {
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setTranslateZ(-40600);
        camera.setNearClip(0.001);
        camera.setFarClip(2000000000);
        camera.setFieldOfView(35);
        camera.setTranslateX((float) orbitPanel.getSize().width / 2);
        camera.setTranslateY((float) orbitPanel.getSize().height / 2);
        scene.setCamera(camera);

        scene.addEventHandler(ScrollEvent.SCROLL, event ->
        {
            double delta = event.getDeltaY() * zoomSpeed;
            camera.setTranslateZ(camera.getTranslateZ() + delta);

            System.out.println(camera.getTranslateZ());
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    if (newPlanets == null) {
                        if (camera.getTranslateZ() >= -12600) {
                            for (int i = 0; i < 4; i++) {
                                guiPlanetList.get(i).getPlanetOrbit().getEllipseFromOrbit().setStrokeWidth(10);
                            }
                            zoomSpeed = 10;
                            moveSpeed = 10;

                            System.out.println(10);
                        } else if (camera.getTranslateZ() >= -103000) {
                            for (int i = 0; i < guiPlanetList.size(); i++) {
                                guiPlanetList.get(i).getPlanetOrbit().getEllipseFromOrbit().setStrokeWidth(40);
                            }
                            zoomSpeed = 50;
                            moveSpeed = 40;


                            System.out.println(40);
                        } else if (camera.getTranslateZ() >= -247800) {
                            for (int i = 0; i < guiPlanetList.size(); i++) {
                                guiPlanetList.get(i).getPlanetOrbit().getEllipseFromOrbit().setStrokeWidth(180);
                            }
                            zoomSpeed = 80;
                            moveSpeed = 150;

                            System.out.println(180);
                        } else if (camera.getTranslateZ() >= -600000) {
                            for (int i = 0; i < guiPlanetList.size(); i++) {
                                guiPlanetList.get(i).getPlanetOrbit().getEllipseFromOrbit().setStrokeWidth(600);
                            }
                            zoomSpeed = 120;
                            moveSpeed = 220;

                            System.out.println(180);
                        }
                        if (camera.getTranslateZ() < -795322) {
                            camera.setTranslateZ(-795322);
                        }

                        if (camera.getTranslateZ() >= -8000) {
                            camera.setTranslateZ(-8000);
                            System.out.println("Sun");
                        }
                    } else {
                        for (int i = 0; i < newPlanets.size(); i++) {
                            if (camera.getTranslateZ() <= 12600) {
                                newPlanets.get(i).getPlanetOrbit().getEllipseFromOrbit().setStrokeWidth(10);
                            } else if (camera.getTranslateZ() > 12600 && camera.getTranslateZ() <= 53000) {
                                newPlanets.get(i).getPlanetOrbit().getEllipseFromOrbit().setStrokeWidth(40);
                            } else if (camera.getTranslateZ() > 53000 && camera.getTranslateZ() <= 247800) {
                                newPlanets.get(i).getPlanetOrbit().getEllipseFromOrbit().setStrokeWidth(180);
                            }
                        }
                    }
                }
            });

        });
    }


    /**
     * @author Lanna Maslo
     * Allows the user to move the solar system by dragging it
     */
    public void handleMouse(Node root) {
        root.setOnMousePressed(event ->
        {
            startDragX = event.getSceneX();
            startDragY = event.getSceneY();

            orgTransX = root.getTranslateX();
            orgTransY = root.getTranslateY();
        });

        root.setOnMouseDragged(event ->
        {
            double offsetX = (event.getSceneX() - startDragX) * moveSpeed;
            double offsetY = (event.getSceneY() - startDragY) * moveSpeed;

            double newTransX = orgTransX + offsetX;
            double newTransY = orgTransY + offsetY;

            root.setTranslateX(newTransX);
            root.setTranslateY(newTransY);
        });
    }

    /**
     * @author Albin Ahlbeck
     * Starts the the planets movement
     */
    public void startOrbits(ArrayList<Planet> planetArrayList) {
        for (Planet planet : planetArrayList) {
            planet.getPathTransiton().play(); // starts orbits
        }
    }

    /**
     * @author Albin Ahlbeck
     * @author Simon M�tegen
     * Changes speed of planets
     */
    private void speedChangeScene(double inDurationModifier) {
        Platform.runLater(new Runnable() {
            /**
             @author Albin Ahlbeck
             @author Simon M�tegen
              * Runs on the Java FX thread
             */
            @Override
            public void run() {
                SwingUtilities.invokeLater(new Runnable() {
                    /**
                     @author Albin Ahlbeck
                     @author Simon M�tegen
                      * Runs on Swing thread
                     */
                    @Override
                    public void run() {
                        loadingScreen.setVisible(true);
                    }
                });


                //Planets that move 10 times slower for every click on the button
                newPlanets = controller.createPlanetArray(inDurationModifier);
                orbitPanel.setScene(createScene(newPlanets));

                for (int i = 0; i < newPlanets.size(); i++) {
                    PhongMaterial map = new PhongMaterial();
                    map.setDiffuseMap(new Image(getClass().getResource("/Images/") + newPlanets.get(i).getName() + ".jpg"));
                    newPlanets.get(i).getSphereFromPlanet().setMaterial(map);
                }
                SwingUtilities.invokeLater(new Runnable() {
                    /**
                     @author Albin Ahlbeck
                     @author Simon M�tegen
                      * Runs on the Swing Thread
                     */
                    @Override
                    public void run() {
                        loadingScreen.setVisible(false);
                    }
                });

            }
        });


    }


    /**
     * Listens to change in timeSlider and then changes the text in timeLabel
     *
     * @author Albin Ahlbeck
     * @author Simon M�tegen
     * @version 1.0
     */
    private class SliderListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
            // not used
        }


        @Override
        public void mousePressed(MouseEvent mouseEvent) {
            // not used
        }

        /**
         * @author Albin Ahlbeck
         * @author Simon M�tegen
         * Sets the values after the mouse is released from the slider
         */
        @Override
        public void mouseReleased(MouseEvent mouseEvent) {
            if (timeSlider.getValue() == 0) {
                speedChangeScene(1);
            } else if (timeSlider.getValue() == 10) {
                speedChangeScene(10000);
            } else if (timeSlider.getValue() == 20) {
                speedChangeScene(1000000);
            } else if (timeSlider.getValue() == 30) {
                speedChangeScene(10000000);
            }
        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {
            // not used
        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {
            // not used
        }
    }


    /**
     * @param planet The planet to showcase
     * @author Albin Ahlbeck
     * Opens an information window
     */
    public void openInfoWindow(Planet planet) {
        mainInfoFrame = new MainInfoFrame(planet, currentTheme);
    }


    /**
     * @author Albin Ahlbeck
     * Adds fonts to the GraphicsEnviroment for later use
     */
    public void initFonts() {
        try {
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/earth_orbiter/earthorbiter.ttf")));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/earth_orbiter/earthorbiterbold.ttf")));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/earth_orbiter/earthorbitertitleital.ttf")));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/nasalization/nasalization-rg.ttf")));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/revolution_saji/REVOLUTION.ttf")));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/Starjedi.ttf")));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/Starjhol.ttf")));


        } catch (Exception e) {
            System.out.println(e);
        }

    }

    /**
     * @param theme the Theme to be used for selection of colors
     * @author Albin Ahlbeck
     * Set the colors on graphical componenets from the theme
     */
    public void setColors(Theme theme) {
        currentTheme = theme;
        lblTitle.setForeground(theme.getSecondaryColor());
        timeSlider.setForeground(theme.getSecondaryColor());
        overheadPanel.setBackground(theme.getMainColor());
        lblTitle.setOpaque(true);
        lblTitle.setBackground(null);
        timeSlider.setBackground(null);
        timeSlider.setOpaque(true);
        lblTheme.setForeground(theme.getMainColor());
        overheadPanel.setBorder(BorderFactory.createLineBorder(theme.getSecondaryColor(), 2));

        // Time slider configuration
        JLabel lbl1 = new JLabel("Real speed");
        JLabel lbl2 = new JLabel("x10000");
        JLabel lbl3 = new JLabel("x1000000");
        JLabel lbl4 = new JLabel("x10000000");

        lbl1.setForeground(theme.getSecondaryColor());
        lbl2.setForeground(theme.getSecondaryColor());
        lbl3.setForeground(theme.getSecondaryColor());
        lbl4.setForeground(theme.getSecondaryColor());

        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        labelTable.put(0, lbl1);
        labelTable.put(10, lbl2);
        labelTable.put(20, lbl3);
        labelTable.put(30, lbl4);
        timeSlider.setLabelTable(labelTable);

        Platform.runLater(new Runnable() {
            /**
             @author Albin Ahlbeck
              * Runs on the Java FX thread, changes the color on of the orbit ellipses and changes color of the mediabar.
             */
            @Override
            public void run() {

                for (int i = 0; i < guiPlanetList.size(); i++) {
                    guiPlanetList.get(i).getPlanetOrbit().getEllipseFromOrbit().setStroke(theme.getSecondaryPaint());
                    if (newPlanets != null) // if the scene never have been changed newPlanets will throw nullpointer
                    {
                        newPlanets.get(i).getPlanetOrbit().getEllipseFromOrbit().setStroke(theme.getSecondaryPaint());
                    }
                }
                if (mediaBar != null) {
                    mediaBar.addTheme(theme);
                    if (theme.toString().equals("Star Wars")) {
                        mediaBar.changeSong(3);
                    }

                    if (theme.toString().equals("Stranger Things")) {
                        mediaBar.changeSong(2);
                    }
                }


            }
        });

    }
}
