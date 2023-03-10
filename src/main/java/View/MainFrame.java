package View;

import Controller.Controller;
import Controller.MusicPlayer;

import Model.Planet;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.Cursor;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Ellipse;
import javafx.scene.image.Image;
import javafx.util.Duration;
import org.controlsfx.control.PopOver;
import Controller.InfoPopoverBuilder;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicSliderUI;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Albin Ahlbeck
 * @author Simon M�tegen
 * @author Lanna Maslo
 * @author Manna Manojlovic
 * @author Marcus Svensson
 * MainFrame is the main window which contains various graphical components
 */
public class MainFrame extends JFrame {

    public static final int WIDTH = 1408;
    public static final int HEIGHT = 820;

    private final int MAX_SLIDER_VALUE = 70;
    private final int MIN_SLIDER_VALUE = 3;

    private JLabel lblTitle;

    private ArrayList<Model.Planet> newPlanets;

    private Theme currentTheme;

    private static JFXPanel orbitPanel;

    private StackPane root;

    private JSlider zoomSlider;

    private Controller controller;
    private double startDragX;
    private double startDragY;
    private double orgTransX;
    private double orgTransY;

    private int moveSpeed = 20;
    private int zoomSpeed = 10;

    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

    private MusicPlayer musicPlayer;

    static private JPanel overheadPanel;
    private JPanel buttonPanel;
    private JPanel zoomPanel;
    private static Scene orbitScene;
    private static Scene quizScene;
    private static JFrame mainFrame;

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

        lblTitle = new JLabel();
        lblTitle.setPreferredSize(new Dimension(300, 80));
        lblTitle.setText("  Orbitz");
        lblTitle.setFont(new Font("Earth Orbiter", Font.PLAIN, 55));
        lblTitle.setOpaque(true);

        // Sets up the JFrame
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setTitle("Orbitz");
        setResizable(false);
        ImageIcon solarSystem = new ImageIcon(getClass().getResource("/Images/orbitz.png").toExternalForm());
        setIconImage(solarSystem.getImage());

        orbitPanel.setPreferredSize(new Dimension(getWidth(), getHeight() - 100));

        // Sets up the zoomSlider
        zoomSlider = new JSlider() {
            @Override
            public void updateUI() {
                setUI(new CustomSliderUI(this));
            }
        };
        zoomSlider.setValue(35);
        zoomSlider.setMaximum(MAX_SLIDER_VALUE);
        zoomSlider.setMinimum(MIN_SLIDER_VALUE);
        zoomSlider.setPreferredSize(new Dimension(300, 50));

        // Set up the zoomPanel
        zoomPanel = new JPanel();
        zoomPanel.setPreferredSize(new Dimension(300, 50));
        ImageIcon zoomIn = new ImageIcon("src/main/resources/Icons/zoom+.png");
        zoomIn = new ImageIcon(zoomIn.getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH));
        ImageIcon zoomOut = new ImageIcon("src/main/resources/Icons/zoom-.png");
        zoomOut = new ImageIcon(zoomOut.getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH));

        zoomPanel.setLayout(new BorderLayout());
        zoomPanel.setBorder(new EmptyBorder(10,50,10,50));
        zoomPanel.add(zoomSlider, BorderLayout.CENTER);
        zoomPanel.add(new JLabel(zoomIn), BorderLayout.WEST);
        zoomPanel.add(new JLabel(zoomOut), BorderLayout.EAST);

        // MUSIC PLAYBACK STUFF
        setupMusicPlayer();

        ImageIcon soundOff = new ImageIcon("src/main/resources/Icons/Unmuted1.png");
        ImageIcon soundOn = new ImageIcon("src/main/resources/Icons/Muted1.png");

        // Stupid java swing, resizing icons to match button
        java.awt.Image img;
        java.awt.Image newImg;

        img = soundOff.getImage();
        newImg = img.getScaledInstance( 72, 72,  java.awt.Image.SCALE_SMOOTH );
        ImageIcon soundOffScaled = new ImageIcon(newImg);

        img = soundOn.getImage();
        newImg = img.getScaledInstance( 72, 72, java.awt.Image.SCALE_SMOOTH);
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
        btnMuteMusic.setPreferredSize(new Dimension(72, 72));
        btnMuteMusic.setFocusable(false);
        btnMuteMusic.setBorderPainted(false);
        btnMuteMusic.setOpaque(false);
        btnMuteMusic.setBackground(new Color(36,18,70));
        btnMuteMusic.addActionListener(e -> musicPlayer.togglePlayback());
        // MUTE BUTTON FINISHED

        // Quiz button
        ImageIcon icon = new ImageIcon("src/main/resources/Icons/quizbuttonB.png");
        ImageIcon quizButtonIcon = new ImageIcon(icon.getImage().getScaledInstance(120, 72, java.awt.Image.SCALE_SMOOTH));
        JButton quizButton = new JButton(quizButtonIcon);
        quizButton.addActionListener(e -> changeScene("Quiz"));
        quizButton.setPreferredSize(new Dimension(120, 72));
        quizButton.setOpaque(false);
        quizButton.setFocusable(false);
        quizButton.setBorderPainted(false);
        quizButton.setBackground(new Color(36,18,70));
        
        // Quiz button panel
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setPreferredSize(new Dimension(300, 80));
        buttonPanel.setBorder(new EmptyBorder(10,0,10,0));
        buttonPanel.add(quizButton);
        buttonPanel.add(new JLabel("   "));
        buttonPanel.add(btnMuteMusic);

        // Sets up overheadPanel
        overheadPanel.setLayout(new BorderLayout());
        overheadPanel.setPreferredSize(new Dimension(1400, 100));
        overheadPanel.add(zoomPanel, BorderLayout.CENTER);
        overheadPanel.add(lblTitle, BorderLayout.WEST);
        overheadPanel.add(buttonPanel, BorderLayout.EAST);

        mainFrame = this;
        mainFrame.add(orbitPanel, BorderLayout.CENTER);
        mainFrame.add(overheadPanel, BorderLayout.NORTH);

        currentTheme = new Theme("Black and White", Color.BLACK, new Color(36,18,70), javafx.scene.paint.Color.BLACK, javafx.scene.paint.Color.WHITE);
        setColors(currentTheme);


        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                initFxOrbit();
            }
        });
        setVisible(true);
    }


    static void changeScene(String name) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                switch (name) {
                    case "Quiz":
                        Scene scene = null;
                        try {
                            scene = getQuizScene();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        mainFrame.remove(overheadPanel);
                        orbitPanel.setScene(scene);
                        break;
                    case "Orbit":

                        orbitPanel.setScene(orbitScene);
                        mainFrame.add(overheadPanel, BorderLayout.NORTH);
                        break;
                }
            }
        });
    }


    // Create quiz scene
    private static Scene getQuizScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(MainFrame.class.getResource("/View/quiz.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        quizScene = scene;
        return scene;
    }

    /**
     * @author Albin Ahlbeck
     * @author Lanna Maslo
     * Creates a new scene from createScene and adds it to the Java FX window
     * Sets background music
     */
    private void initFxOrbit() {
        // This method is invoked on JavaFX thread
        orbitScene = createScene(controller.createPlanetArray()); // default background
        orbitPanel.setScene(orbitScene);
    }

    /**
     * @author Albin Ahlbeck
     * @author Lanna Maslo
     * @author Manna Manojlovic
     * Creates the Java-FX scene
     */
    private Scene createScene(ArrayList<Planet> planets) {
        root = new StackPane();
        Scene scene = new Scene(root, WIDTH, HEIGHT, true, SceneAntialiasing.BALANCED);

        //Background orbitscene
        ImagePattern pattern = new ImagePattern(new Image(getClass().getResource("/Images/Stars.png").toExternalForm()));
        scene.setFill(pattern);

        handleMouse(root);
        placePlanets(root, planets);
        setupCamera(scene);
        startOrbits(planets);
        EventHandler<javafx.scene.input.MouseEvent> eventHandler = mouseEvent -> {
            try {
                openInfoWindow(determinePlanet((ImageView) mouseEvent.getSource()));
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Could not load fxml!");
            }
        };

        for (Model.Planet planet : planets) {
            ImageView planetImageView = controller.getPlanetImageView(planet);
            planetImageView.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, eventHandler);
            planetImageView.setCursor(Cursor.HAND);
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
     * Finds which planet the sphere is connected to
     *
     * @author Albin Ahlbeck
     */
    public Model.Planet determinePlanet(ImageView planetImageView) {
        for (int i = 0; i < controller.getPlanetArrayList().size(); i++) {
            if (planetImageView.getId().equals(controller.getPlanetArrayList().get(i).getName())) {
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

        for (Model.Planet planet : planetArrayList) {
            Ellipse ellipse = controller.getEllipse(planet);
            root.getChildren().add(controller.getPlanetImageView(planet));
            root.getChildren().add(ellipse);
            ellipse.toBack();
            ellipse.setStroke(currentTheme.getSecondaryPaint());

            for (Node child : root.getChildren()) {
                if (child.equals(controller.getPlanetImageView(planet))) {
                    PathTransition animation = createPathTransition(child, planet);
                    controller.putHashValue(planet, "path", animation);
                    animation.setCycleCount(Animation.INDEFINITE);
                }
            }
            setTooltip(planet);
        }
        root.getChildren().add(createSunSphere(3000));
    }

    public static Ellipse createEllipse(double x, double y, double w, double h) {
        Ellipse ellipse = new Ellipse(x, y, w, h);
        ellipse.setStrokeWidth(80);
        ellipse.setStroke(javafx.scene.paint.Color.GRAY);
        ellipse.setFill(javafx.scene.paint.Color.TRANSPARENT);
        return ellipse;
    }

    /**
     * Creates an imageview with a planet image. 2D representation of a planet.
     * @param planet
     * @return
     */
    public static ImageView createPlanetImageView(Model.Planet planet) {
        Image image = new Image(MainFrame.class.getResource("/Images/planets/" + planet.getName() + ".png").toExternalForm());
        ImageView planetImageView = new ImageView(image);
        planetImageView.setId(planet.getName());
        planetImageView.setPreserveRatio(true);
        planetImageView.setFitWidth((planet.getMeanRadius() * 1000 / planet.getSCALE_RADIUS_VALUE()) *2);
        return planetImageView;
    }

    /**
     * @author Lanna Maslo
     * @author Simon M�tegen
     * Sets the viewing perspective and enables a zoom-function
     */
    public void setupCamera(Scene scene) {
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setTranslateZ(-87500);
        camera.setNearClip(0.001);
        camera.setFarClip(2000000000);
        camera.setFieldOfView(35);
        camera.setTranslateX((float) orbitPanel.getSize().width / 2);
        camera.setTranslateY((float) orbitPanel.getSize().height / 2);
        scene.setCamera(camera);

        zoomSlider.addChangeListener(e -> {
            int value = zoomSlider.getValue();
            double delta = value * zoomSpeed;

            camera.setTranslateZ(-25000  * delta * 0.01);

            double transZ = camera.getTranslateZ();

            for (int i = 0; i < controller.getPlanetArrayList().size(); i++) {
                controller.getEllipse(controller.getPlanetArrayList().get(i)).setStrokeWidth(Math.abs(transZ/1000));
            }

            moveSpeed = (int) Math.abs(transZ/1260);

            if (camera.getTranslateZ() < -795322) {
                camera.setTranslateZ(-795322);
            }

            if (camera.getTranslateZ() >= -8000) {
                camera.setTranslateZ(-8000);
            }
        });

        scene.addEventHandler(ScrollEvent.SCROLL, event ->
        {
            zoomSlider.setValue((int) ( zoomSlider.getValue() - (event.getDeltaY() * 0.1)));

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
     * Opens a popup with information about the specified planet.
     * @param planet The planet to showcase
     * @author Joel Eriksson Sinclair
     */
    public void openInfoWindow(Model.Planet planet) throws IOException {
        PopOver popOver = new InfoPopoverBuilder().createInfoPopover(planet);
        popOver.setDetachable(false);
        popOver.setArrowLocation(PopOver.ArrowLocation.RIGHT_CENTER);
        popOver.setHeaderAlwaysVisible(true);
        popOver.show(controller.getPlanetImageView(planet));

        ((Parent)popOver.getSkin().getNode()).getStylesheets().add(getClass().getResource("/View/popOverStyle.css").toExternalForm());

        // Hide the popover when switching windows.
        controller.getPathTransition(planet).pause();

        popOver.setOnHidden(windowEvent -> {
            controller.getPathTransition(planet).play();
        });

        this.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                Platform.runLater(() -> {
                });
            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                Platform.runLater(popOver::hide);
            }
        });
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
        lblTitle.setForeground(Color.WHITE);
        overheadPanel.setBackground(theme.getSecondaryColor());
        buttonPanel.setBackground(theme.getSecondaryColor());
        zoomPanel.setBackground(theme.getSecondaryColor());
        lblTitle.setBackground(null);
        zoomSlider.setBackground(null);
    }

    /**
     * Set a tooltip which informs the planets name
     *
     * @author Albin Ahlbeck
     */
    public void setTooltip(Model.Planet planet) {
        Tooltip tooltip = new Tooltip(planet.getSwedishName());
        tooltip.setStyle("-fx-font-size: 20");                   //CSS stylesheet, Oracle doc.
        tooltip.setShowDelay(Duration.millis(0));//sets time before text appears after hovering over image

        controller.getPlanetImageView(planet).setPickOnBounds(true);
        Tooltip.install(controller.getPlanetImageView(planet), tooltip);
    }

    /**
     * A method that creates a path transition for a specific planet
     */
    public PathTransition createPathTransition(Node node, Model.Planet planet) {
        LocalDate currentDate = LocalDate.now();
        PathTransition pathTransition = new PathTransition();
        double day = controller.getPositionCalculator().calculateDateDifference(currentDate.getYear(), currentDate.getMonthValue(), currentDate.getDayOfMonth());
        controller.getEllipse(planet).setRotate(-controller.getPositionCalculator().calculatePlanetPosition(day, planet.getName()));
        Ellipse ellipse = controller.getEllipse(planet);
        ellipse.setRotate(-controller.getPositionCalculator().calculatePlanetPosition(day, planet.getName()));
        ellipse.setCenterX(planet.getPlanetOrbit().getCenterXCord(37500) + controller.getPlanetImageView(planet).getFitWidth()/2);
        ellipse.setCenterY(planet.getPlanetOrbit().getCenterYCord(37500) + controller.getPlanetImageView(planet).getFitWidth()/2);
        pathTransition.setPath(ellipse);
        pathTransition.setNode(node);
        pathTransition.setDuration(controller.getDuration(planet));
        pathTransition.setCycleCount(Animation.INDEFINITE);
        pathTransition.setInterpolator(Interpolator.LINEAR);
        pathTransition.setOrientation(PathTransition.OrientationType.NONE);
        return pathTransition;
    }

    /**
     * @return returns a string of the path
     * @author Simon Mtegen
     * @author Lanna Maslo
     * Creates a sphere graphical object for the sun
     */
    public ImageView createSunSphere(int width) {
        Image image = new Image(getClass().getResource("/Images/planets/Sun.png").toExternalForm());
        ImageView sunImageView = new ImageView(image);
        sunImageView.setPreserveRatio(true);
        sunImageView.setFitWidth(width);

        return sunImageView;
    }

    private class CustomSliderUI extends BasicSliderUI {

        private static final int TRACK_HEIGHT = 8;
        private static final int TRACK_WIDTH = 8;
        private static final int TRACK_ARC = 5;
        private static final Dimension THUMB_SIZE = new Dimension(20, 20);
        private final RoundRectangle2D.Float trackShape = new RoundRectangle2D.Float();

        public CustomSliderUI(final JSlider b) {
            super(b);
        }

        @Override
        protected void calculateTrackRect() {
            super.calculateTrackRect();
            if (isHorizontal()) {
                trackRect.y = trackRect.y + (trackRect.height - TRACK_HEIGHT) / 2;
                trackRect.height = TRACK_HEIGHT;
            } else {
                trackRect.x = trackRect.x + (trackRect.width - TRACK_WIDTH) / 2;
                trackRect.width = TRACK_WIDTH;
            }
            trackShape.setRoundRect(trackRect.x, trackRect.y, trackRect.width, trackRect.height, TRACK_ARC, TRACK_ARC);
        }

        @Override
        protected void calculateThumbLocation() {
            super.calculateThumbLocation();
            if (isHorizontal()) {
                thumbRect.y = trackRect.y + (trackRect.height - thumbRect.height) / 2;
            } else {
                thumbRect.x = trackRect.x + (trackRect.width - thumbRect.width) / 2;
            }
        }

        @Override
        protected Dimension getThumbSize() {
            return THUMB_SIZE;
        }

        private boolean isHorizontal() {
            return slider.getOrientation() == JSlider.HORIZONTAL;
        }

        @Override
        public void paint(final Graphics g, final JComponent c) {
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            super.paint(g, c);
        }

        @Override
        public void paintTrack(final Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            Shape clip = g2.getClip();

            boolean horizontal = isHorizontal();
            boolean inverted = slider.getInverted();

            // Paint shadow.
            g2.setColor(new Color(170, 170 ,170));
            g2.fill(trackShape);

            // Paint track background.
            g2.setColor(new Color(200, 200 ,200));
            g2.setClip(trackShape);
            trackShape.y += 1;
            g2.fill(trackShape);
            trackShape.y = trackRect.y;

            g2.setClip(clip);

            // Paint selected track.
            if (horizontal) {
                boolean ltr = slider.getComponentOrientation().isLeftToRight();
                if (ltr) inverted = !inverted;
                int thumbPos = thumbRect.x + thumbRect.width / 2;
                if (inverted) {
                    g2.clipRect(0, 0, thumbPos, slider.getHeight());
                } else {
                    g2.clipRect(thumbPos, 0, slider.getWidth() - thumbPos, slider.getHeight());
                }

            } else {
                int thumbPos = thumbRect.y + thumbRect.height / 2;
                if (inverted) {
                    g2.clipRect(0, 0, slider.getHeight(), thumbPos);
                } else {
                    g2.clipRect(0, thumbPos, slider.getWidth(), slider.getHeight() - thumbPos);
                }
            }
            g2.setColor(Color.ORANGE);
            g2.fill(trackShape);
            g2.setClip(clip);
        }

        @Override
        public void paintThumb(final Graphics g) {
            g.setColor(new Color(246, 146, 36));
            g.fillOval(thumbRect.x, thumbRect.y, thumbRect.width, thumbRect.height);
        }

        @Override
        public void paintFocus(final Graphics g) {}
    }
}
