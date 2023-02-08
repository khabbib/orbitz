package View;

import Model.Planet;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.util.Duration;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

import java.awt.*;

/**
 * @author Manna Manojlovic
 * @author Lanna Maslo
 * @author Albin Ahlbeck
 * bottom left corner: JavaFX-panel with 3D-model of planet
 * upper left corner: textarea with general facts about the planet
 * <p>
 * upper right corner: image gallery with 4 images in miniature. Underneath is one big picture which changes after a small is clicked
 * bottom right corner: SWEN THE ALIEN AND HIS FUN FACTS!!!
 */
public class MainInfoPanel extends JPanel {
    private BorderLayout layout;

    private ImageGalleryPanel imgPanel;

    private SwenTheAlien swenPanel;

    private JFXPanel planetaryPanel;

    private Cylinder planetRings;

    private Planet planet;

    private StackPane root;

    private Sphere planetSphere; // the sphere that will be shown

    private double startDragX;
    private double startDragY;
    private double orgTransY;
    private double orgTransX;

    private Rotate rotate;

    private AnimationTimer timer = null;

    /**
     * @author Manna Manojlovic
     * @version 1.0
     * <p>
     * Constructor
     * Initializes the instances, calls method setupPanel()
     */
    public MainInfoPanel(Planet planet) {
        imgPanel = new ImageGalleryPanel(planet);
        swenPanel = new SwenTheAlien(planet);
        planetaryPanel = new JFXPanel();
        setupPanel(planet);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                initFX(planetaryPanel); // starts on the Java FX thread
            }
        });
    }

    /**
     * @author Manna Manojlovic
     * @version 1.0
     * <p>
     * This method initializes a BorderLayout and places the panels from other classes on it.
     * this is the main panel for the Information-GUI
     */
    public void setupPanel(Planet planet) {
        int width = 1000;
        int height = 600;

        this.planet = planet;

        Border border = this.getBorder();
        Border margin = BorderFactory.createEmptyBorder(12, 12, 12, 12);

        layout = new BorderLayout();
        setLayout(layout);
        setBackground(Color.black);
        setPreferredSize(new Dimension(width, height));
        setBorder(new CompoundBorder(border, margin));
        planetaryPanel.setPreferredSize(new Dimension(600, 200));

        add(imgPanel, BorderLayout.NORTH);
        add(swenPanel, BorderLayout.EAST);
        add(planetaryPanel, BorderLayout.WEST);
        setBackground(Color.BLACK);

        planetSphere = new Sphere(80);

        rotate = new Rotate();
        rotate.setPivotX(planetSphere.getRadius());
        rotate.setPivotY(planetSphere.getRadius());
    }

    /**
     * Creates a new scene from createScene and adds it to the Java FX window
     *
     * @param fxPanel The JavaFX panel to be created
     * @author Albin Ahlbeck
     * @version 1.0
     */
    private void initFX(JFXPanel fxPanel) {
        // This method is invoked on JavaFX thread
        Scene scene = createScene(); // default background
        fxPanel.setScene(scene);
    }

    /**
     * Set up the scene
     *
     * @author Albin Ahlbeck
     * @version 1.0
     */
    private Scene createScene() {
        root = new StackPane();
        Scene scene = new Scene(root, planetaryPanel.getWidth(), planetaryPanel.getHeight(), true);
        scene.setFill(javafx.scene.paint.Color.BLACK);
        root.setBackground(null);
        root.getChildren().add(planetSphere);
        root.setTranslateY(20);

        Tooltip tooltip = new Tooltip("Spin me right round, baby, right round!");
        tooltip.setStyle("-fx-font-size: 18");                   //CSS stylesheet, Oracle doc.
        tooltip.setShowDelay(Duration.millis(1));               //sets time before text appears after hovering over image

        planetSphere.setPickOnBounds(true);
        Tooltip.install(planetSphere, tooltip);
        paintPlanet();
        handleMouse();
        return scene;
    }

    /**
     * Enables moving the node with mouse presses and mouse drags
     *
     * @author Lanna Maslo
     * @author Albin Ahlbeck
     * @version 1.0
     */
    public void handleMouse() {
        root.setOnMousePressed(event ->
        {
            startDragX = event.getSceneX();
            startDragY = event.getSceneY();

            orgTransX = root.getTranslateX();
            orgTransY = root.getTranslateY();
        });

        root.setOnMouseDragged(event ->
        {
            double offsetX = event.getSceneX() - startDragX;
            double offsetY = event.getSceneY() - startDragY;

            double newTransX = orgTransX + offsetX;
            double newTransY = orgTransY + offsetY;

            matrixRotateNode(planetSphere, 0, -newTransY / 100, newTransX / 100);

            if (planet.getName().equals("Saturn")) {
                matrixRotateNode(planetRings, 0, -newTransY / 100, newTransX / 100);
            }
        });
    }

    /**
     * A calculation method that allows rotation of a sphere's axis
     *
     * @param n   the node that is rotated
     * @param alf roll rotation (front-to-back)
     * @param bet pitch rotation (side-to-side)
     * @param gam yaw rotation (vertical axis)
     * @author Albin Ahlbeck
     * @version 1.0
     */
    private void matrixRotateNode(Node n, double alf, double bet, double gam) {
        double A11 = Math.cos(alf) * Math.cos(gam);
        double A12 = Math.cos(bet) * Math.sin(alf) + Math.cos(alf) * Math.sin(bet) * Math.sin(gam);
        double A13 = Math.sin(alf) * Math.sin(bet) - Math.cos(alf) * Math.cos(bet) * Math.sin(gam);
        double A21 = -Math.cos(gam) * Math.sin(alf);
        double A22 = Math.cos(alf) * Math.cos(bet) - Math.sin(alf) * Math.sin(bet) * Math.sin(gam);
        double A23 = Math.cos(alf) * Math.sin(bet) + Math.cos(bet) * Math.sin(alf) * Math.sin(gam);
        double A31 = Math.sin(gam);
        double A32 = -Math.cos(gam) * Math.sin(bet);
        double A33 = Math.cos(bet) * Math.cos(gam);

        double d = Math.acos((A11 + A22 + A33 - 1d) / 2d);
        if (d != 0d) {
            double den = 2d * Math.sin(d);
            Point3D p = new Point3D((A32 - A23) / den, (A13 - A31) / den, (A21 - A12) / den);
            n.setRotationAxis(p);
            n.setRotate(Math.toDegrees(d));
        }
    }

    /**
     * Adds material to the planetSphere
     *
     * @author Lanna Maslo
     * @author Albin Ahlbeck
     * @version 1.0
     */
    public void paintPlanet() {
        PhongMaterial map = new PhongMaterial();
        map.setDiffuseMap(new Image(getClass().getResource("/Images/" + planet.getName() + ".jpg").toExternalForm()));
        planetSphere.setMaterial(map);

        if (planet.getName().equals("Saturn")) {
            planetRings();
        }
    }

    /**
     * Calls the method stopMp3() in ImageGalleryPanel, which stops the mediaplayer
     *
     * @author Lanna Maslo
     * @version 1.0
     */
    public void stopMp3() {
        imgPanel.stopMp3();
    }

    /**
     * Adds rings to the 3D model of Saturn
     *
     * @author Lanna Maslo
     * @version 1.0
     */
    public void planetRings() {
        planetRings = new Cylinder(180, 5);
        PhongMaterial rings = new PhongMaterial();
        rings.setDiffuseMap(new Image(getClass().getResource("/Images/saturnRings.png").toExternalForm()));
        planetRings.setMaterial(rings);
        root.getChildren().add(planetRings);

        Transform tilt = new Rotate(30, new Point3D(50, 120, 40));
        root.getTransforms().add(tilt);
        root.setTranslateY(-20);
        root.setTranslateX(40);
    }
}
