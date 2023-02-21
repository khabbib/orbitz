package View;

import Controller.Controller;
import Controller.MusicPlayer;


import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.Cursor;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.Lighting;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.effect.Lighting;
import javafx.scene.effect.Light;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Sphere;
import javafx.scene.image.Image;
import javafx.util.Duration;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
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

    private ArrayList<Model.Planet> newPlanets;

    private Theme currentTheme;

    private JFXPanel orbitPanel;

    private JPanel overheadPanel;

    private MainInfoFrame mainInfoFrame;

    private StackPane root;

    private JSlider timeSlider;

    private SliderListener sliderListener;

    private Controller controller;
    private double startDragX;
    private double startDragY;
    private double orgTransX;
    private double orgTransY;

    private int moveSpeed = 20;
    private int zoomSpeed = 10;

    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

    private MusicPlayer musicPlayer;

    /**
     * @param inController gains a reference to controller in order to fetch the planet list
     * @author Albin Ahlbeck
     * @author Simon M�tegen
     * Constructs the GUI components and starts the Java-FX window.
     */
    public MainFrame(Controller inController) {
        this.controller = inController;
        initFonts();
        orbitPanel = new JFXPanel();
        overheadPanel = new JPanel();

        sliderListener = new SliderListener();
        timeSlider = new JSlider();

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


        // Sets up the JSlider and components related to it

        timeSlider.setValue(0);
        timeSlider.setMaximum(MAX_SLIDER_VALUE);
        timeSlider.setPaintLabels(true);

        timeSlider.setPreferredSize(new Dimension(700, 70));
        timeSlider.setPaintTicks(true);
        timeSlider.setMajorTickSpacing(10);
        timeSlider.setSnapToTicks(true);
        timeSlider.addMouseListener(sliderListener);

        // Sets up overheadPanel
        overheadPanel.setLayout(new FlowLayout());
        // set opaque
        overheadPanel.setOpaque(true);
        timeSlider.setOpaque(true);

        lblTitle.setOpaque(false);
        overheadPanel.setPreferredSize(new Dimension(1400, 160));
        overheadPanel.add(lblTitle);
        overheadPanel.add(timeSlider);

        // MUSIC PLAYBACK STUFF
        setupMusicPlayer();

        ImageIcon soundOff = new ImageIcon("src/main/resources/Icons/sound-off.png");
        ImageIcon soundOn = new ImageIcon("src/main/resources/Icons/sound-on.png");

        // Stupid java swing, resizing icons to match button
        java.awt.Image img;
        java.awt.Image newImg;

        img = soundOff.getImage();
        newImg = img.getScaledInstance( 100, 100,  java.awt.Image.SCALE_SMOOTH );
        ImageIcon soundOffScaled = new ImageIcon(newImg);

        img = soundOn.getImage();
        newImg = img.getScaledInstance( 100, 100, java.awt.Image.SCALE_SMOOTH);
        ImageIcon soundOnScaled = new ImageIcon(newImg);

        JButton btnMuteMusic = musicPlayer.getPlaybackState() ? new JButton(soundOnScaled) : new JButton(soundOffScaled);
        musicPlayer.getPlaybackStateProperty().addListener((observableValue, oldValue, newValue) -> {
            if(newValue) {
                btnMuteMusic.setIcon(soundOnScaled);
            }
            else {
                btnMuteMusic.setIcon(soundOffScaled);
            }
        });
        btnMuteMusic.setPreferredSize(new Dimension(100, 100));
        btnMuteMusic.setBackground(Color.black);
        btnMuteMusic.setBorderPainted(false);
        btnMuteMusic.addActionListener(e -> musicPlayer.togglePlayback());
        // MUTE BUTTON FINISHED

        overheadPanel.add(btnMuteMusic);

        add(orbitPanel, BorderLayout.CENTER);

        add(overheadPanel, BorderLayout.NORTH);


        currentTheme = new Theme("Black and White", Color.BLACK, Color.WHITE, javafx.scene.paint.Color.BLACK, javafx.scene.paint.Color.WHITE);
        setColors(currentTheme);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
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
     */
    private void initFxOrbit(JFXPanel fxPanel) {
        // This method is invoked on JavaFX thread
        Scene scene = createScene(controller.getPlanetArrayList()); // default background
        fxPanel.setScene(scene);
    }
    /**
     * @author Albin Ahlbeck
     * @author Lanna Maslo
     * @author Manna Manojlovic
     * Creates the Java-FX scene
     */
    private Scene createScene(ArrayList<Model.Planet> planetArrayList) {
        root = new StackPane();
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        root.setBackground(null);
        scene.setFill(javafx.scene.paint.Color.BLACK);
        setupCamera(scene);
        handleMouse(root);
        placePlanets(root, planetArrayList);
        paintPlanets(planetArrayList);
        startOrbits(planetArrayList);

        // Event handler for planet clicks
        EventHandler<javafx.scene.input.MouseEvent> eventHandler = new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent mouseEvent) {
                openInfoWindow(determinePlanet((Sphere) mouseEvent.getSource()));
            }

        };

        // Sphere click events
        for (Model.Planet planet : planetArrayList) {
            Sphere sphere = controller.getSphere(planet);
            sphere.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, eventHandler);
            sphere.setCursor(Cursor.HAND);
        }

        return scene;
    }

    private void setupMusicPlayer() {
        try {
            musicPlayer = new MusicPlayer(0.5f);
            //musicPlayer.play();
        } catch (IOException e) {
            System.err.println("Music Player could not load songs!");
            e.printStackTrace();
        }
    }

    /**
     * Finds which planet the the sphere is connected to
     *
     * @author Albin Ahlbeck
     */
    public Model.Planet determinePlanet(Sphere sphere) {
        for (int i = 0; i < controller.getPlanetArrayList().size(); i++) {
            if (sphere.getId().equals(controller.getPlanetArrayList().get(i).getName())) {
                return controller.getPlanetArrayList().get(i);
            }
        }
        return null;
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
    public void placePlanets(Pane root, ArrayList<Model.Planet> planetArrayList) {
        root.getChildren().clear();
        for (Model.Planet planet : planetArrayList) {
            Ellipse ellipse = controller.getEllipse(planet); // ellipse is the line behind the planets.
            root.getChildren().add(controller.getSphere(planet));
            root.getChildren().add(ellipse);
            ellipse.toBack();
            ellipse.setStroke(currentTheme.getSecondaryPaint());
            //System.out.println(planet.getName() + " | X: " + planet.getPlanetOrbit().getCenterXCord(37500));

            for (Node child : root.getChildren()) {
                if (child.equals(controller.getSphere(planet))) {
                    child.setTranslateX(planet.getPlanetOrbit().getHeight(37500));
                    child.setCache(true);
                    PathTransition animation = createPathTransition(child, planet);
                    controller.putHashValue(planet, "path", animation);
                    animation.setCycleCount(Animation.INDEFINITE);
                }
            }
            setTooltip(planet);
        }
        root.getChildren().add(createSunSphere(2000));
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
                                controller.getEllipse(controller.getPlanetArrayList().get(i)).setStrokeWidth(10);
                            }
                            zoomSpeed = 10;
                            moveSpeed = 10;

                            System.out.println(10);
                        } else if (camera.getTranslateZ() >= -103000) {
                            for (int i = 0; i < controller.getPlanetArrayList().size(); i++) {
                                controller.getEllipse(controller.getPlanetArrayList().get(i)).setStrokeWidth(40);
                            }
                            zoomSpeed = 50;
                            moveSpeed = 40;

                            System.out.println(40);
                        } else if (camera.getTranslateZ() >= -247800) {
                            for (int i = 0; i < controller.getPlanetArrayList().size(); i++) {
                                controller.getEllipse(controller.getPlanetArrayList().get(i)).setStrokeWidth(180);
                            }
                            zoomSpeed = 80;
                            moveSpeed = 150;

                            System.out.println(180);
                        } else if (camera.getTranslateZ() >= -600000) {
                            for (int i = 0; i < controller.getPlanetArrayList().size(); i++) {
                                controller.getEllipse(controller.getPlanetArrayList().get(i)).setStrokeWidth(600);
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
                        }
                    } else {
                        for (int i = 0; i < newPlanets.size(); i++) {
                            if (camera.getTranslateZ() <= 12600) {
                                controller.getEllipse(newPlanets.get(i)).setStrokeWidth(10);
                            } else if (camera.getTranslateZ() > 12600 && camera.getTranslateZ() <= 53000) {
                                controller.getEllipse(newPlanets.get(i)).setStrokeWidth(40);
                            } else if (camera.getTranslateZ() > 53000 && camera.getTranslateZ() <= 247800) {
                                controller.getEllipse(newPlanets.get(i)).setStrokeWidth(180);
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

    public void startOrbits(ArrayList<Model.Planet> planetArrayList) {
        for (Model.Planet planet : planetArrayList) {
            controller.getPathTransition(planet).play(); // starts orbits
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
                    }
                });

                newPlanets = controller.setDuration(inDurationModifier);
                placePlanets(root, newPlanets);
                paintPlanets(newPlanets);
                startOrbits(newPlanets);
                }
        });


    }

    /**
     * Paints the surface of the planets by calling their individual mappings
     *
     * @author Lanna Maslo
     */
    public void paintPlanets(ArrayList<Model.Planet> planets) {
        for (Model.Planet planet : planets) {
            PhongMaterial map = new PhongMaterial();
            map.setDiffuseMap(new Image(getClass().getResource("/Images/planets/" + planet.getName() + ".png").toExternalForm()));
            controller.getSphere(planet).setMaterial(map);
        }
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
    public void openInfoWindow(Model.Planet planet) {
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

                for (int i = 0; i < controller.getPlanetArrayList().size(); i++) {
                    controller.getEllipse(controller.getPlanetArrayList().get(i)).setStroke(theme.getSecondaryPaint());
                    if (newPlanets != null) // if the scene never have been changed newPlanets will throw nullpointer
                    {
                        controller.getEllipse(controller.getPlanetArrayList().get(i)).setStroke(theme.getSecondaryPaint());
                    }
                }
            }
        });
    }

    public static Ellipse createElipse(double x, double y, double w, double h) {
        Ellipse ellipse = new Ellipse(x, y, w, h);
        ellipse.setStrokeWidth(40);
        ellipse.setStroke(javafx.scene.paint.Color.GRAY);
        ellipse.setFill(javafx.scene.paint.Color.TRANSPARENT);
        return ellipse;
    }

    public static Sphere createSphere(Model.Planet planet) {
        Sphere sphere = new Sphere((double) planet.getMeanRadius() * 1000 / planet.getSCALE_RADIUS_VALUE());
        sphere.setId(planet.getName());
        return sphere;
    }

    /**
     * Set a tooltip which informs the planets name
     *
     * @author Albin Ahlbeck
     */
    public void setTooltip(Model.Planet planet) {
        Tooltip tooltip = new Tooltip(planet.getName());
        tooltip.setStyle("-fx-font-size: 20");                   //CSS stylesheet, Oracle doc.
        tooltip.setShowDelay(Duration.millis(0));//sets time before text appears after hovering over image


        controller.getSphere(planet).setPickOnBounds(true);
        Tooltip.install(controller.getSphere(planet), tooltip);
    }

    /**
     * A method that creates a path transition for a specific planet
     */
    public PathTransition createPathTransition(Node node, Model.Planet planet) {
        LocalDate currentDate = LocalDate.now();
        PathTransition pathTransition = new PathTransition();
        double day = controller.getPositionCalculator().calculateDateDifference(currentDate.getYear(), currentDate.getMonthValue(), currentDate.getDayOfMonth());
        controller.getEllipse(planet).setRotate(-controller.getPositionCalculator().calculatePlanetPosition(day, planet.getName()));
        pathTransition.setPath(controller.getEllipse(planet));
        pathTransition.setNode(node);
        pathTransition.setDuration(controller.getDuration(planet));
        pathTransition.setCycleCount(Animation.INDEFINITE);
        pathTransition.setInterpolator(Interpolator.LINEAR);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        return pathTransition;
    }

    /**
     * @return returns a string of the path
     * @author Simon Mtegen
     * @author Lanna Maslo
     * Creates a sphere graphical object for the sun
     */
    public ImageView createSunSphere(int radius) {
        Image image = new Image(getClass().getResource("/Images/planets/Sun.png").toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setTranslateX(0);
        imageView.setTranslateY(0);

        return imageView;
    }
}
