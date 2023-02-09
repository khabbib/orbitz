package View;

import Model.Planet;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Point3D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * @author Lanna Maslo
 * A JPanel which contains a JFXPanel with moon models and/or images
 */
public class LunarPanelCenter extends JPanel {
    private Planet planet;

    private Sphere moon = new Sphere();

    private Group moonRoot = new Group();

    private Scene moonScene = new Scene(moonRoot);

    private JFXPanel lunarModel = new JFXPanel();

    private PhongMaterial moonMaterial;

    private Rotate rotate;

    private Image moonGIF;
    private Image phobosImg;
    private Image deimosImg;
    private Image jupiterImg;
    private Image saturnImg;
    private Image uranusImg;
    private Image neptuneImg;

    private ImageView phobos;
    private ImageView deimos;
    private ImageView moonLanding = new ImageView();
    private ImageView jupiterMoons = new ImageView();
    private ImageView saturnMoons = new ImageView();
    private ImageView uranusMoons = new ImageView();
    private ImageView neptuneMoons = new ImageView();

    private MediaPlayer player;

    private double startDragX;
    private double startDragY;
    private double orgTransY;
    private double orgTransX;

    /**
     * Constructs the panel and runs the JavaFX thread
     *
     * @param planet the planet that is currently being displayed
     */
    public LunarPanelCenter(Planet planet) {
        this.planet = planet;
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(280, 390));
        add(lunarModel);

        Platform.runLater(new Runnable() {
            public void run() {
                initFX(lunarModel);
                handleMouse();
            }
        });
    }

    /**
     * @author Lanna Maslo
     * Creates the JFXPanel
     */
    public void initFX(JFXPanel lunarModel) {
        moonScene.setFill(javafx.scene.paint.Color.BLACK);
        moon.setTranslateX(113);
        moon.setTranslateY(70);
        moon.setRadius(70);

        moonMaterial = new PhongMaterial();
        moonMaterial.setDiffuseMap(new Image(getClass().getResource("/Images/moon.jpg").toExternalForm()));

        moon.setMaterial(moonMaterial);
        moon.setOnMouseClicked(event -> playSound("sound/earthMoon.mp3"));
        moon.setCursor(Cursor.HAND);
        Tooltip.install(moon, (new Tooltip("Spin me right round, baby, right round!")));

        phobosImg = new Image(getClass().getResource("/Images/phobos.png").toExternalForm());
        deimosImg = new Image(getClass().getResource("/Images/deimos.png").toExternalForm());

        phobos = new ImageView(phobosImg);
        deimos = new ImageView(deimosImg);

        phobos.setPreserveRatio(true);
        phobos.setFitWidth(130);
        deimos.setPreserveRatio(true);
        deimos.setFitWidth(130);
        phobos.setTranslateX(0);
        phobos.setTranslateY(30);
        deimos.setTranslateX(0);
        deimos.setTranslateY(190);

        phobos.setOnMouseClicked(event -> playSound("sound/marsPhobos.mp3"));
        deimos.setOnMouseClicked(event -> playSound("sound/marsDeimos.mp3"));

        phobos.setCursor(javafx.scene.Cursor.HAND);
        deimos.setCursor(Cursor.HAND);

        Tooltip phobosT = new Tooltip("Phobos (click me)");
        Tooltip deimosT = new Tooltip("Deimos (click me)");
        Tooltip.install(phobos, phobosT);
        Tooltip.install(deimos, deimosT);

        moonGIF = new Image("https://data.whicdn.com/images/293111167/original.gif");
        moonLanding = new ImageView(moonGIF);
        moonLanding.setFitWidth(210);
        moonLanding.setPreserveRatio(true);
        moonLanding.setTranslateY(170);
        moonLanding.setTranslateX(10);

        jupiterImg = new Image(getClass().getResource("/Images/jupiterMoons.png").toExternalForm());
        jupiterMoons = new ImageView(jupiterImg);
        jupiterMoons.setFitWidth(282);
        jupiterMoons.setPreserveRatio(true);
        jupiterMoons.setTranslateX(5);
        jupiterMoons.setTranslateY(60);

        saturnImg = new Image(getClass().getResource("/Images/saturnMoons.jpg").toExternalForm());
        saturnMoons = new ImageView(saturnImg);
        saturnMoons.setFitWidth(260);
        saturnMoons.setPreserveRatio(true);
        saturnMoons.setTranslateX(10);
        saturnMoons.setTranslateY(30);

        uranusImg = new Image(getClass().getResource("/Images/uranusMoons.jpg").toExternalForm());
        uranusMoons = new ImageView(uranusImg);
        uranusMoons.setFitWidth(260);
        uranusMoons.setPreserveRatio(true);
        uranusMoons.setTranslateX(10);
        uranusMoons.setTranslateY(85);

        neptuneImg = new Image(getClass().getResource("/Images/neptuneMoons.png").toExternalForm());
        neptuneMoons = new ImageView(neptuneImg);
        neptuneMoons.setFitWidth(280);
        neptuneMoons.setPreserveRatio(true);
        neptuneMoons.setTranslateX(5);
        neptuneMoons.setTranslateY(10);

        switch (planet.getName()) {
            case "Earth":
                moonRoot.getChildren().add(moon);
                moonRoot.getChildren().add(moonLanding);
                break;
            case "Mars":
                moonRoot.getChildren().add(phobos);
                moonRoot.getChildren().add(deimos);
                break;
            case "Jupiter":
                moonRoot.getChildren().add(jupiterMoons);
                break;
            case "Saturn":
                moonRoot.getChildren().add(saturnMoons);
                break;
            case "Uranus":
                moonRoot.getChildren().add(uranusMoons);
                break;
            case "Neptune":
                moonRoot.getChildren().add(neptuneMoons);
                break;
        }

        lunarModel.setScene(moonScene);

        rotate = new Rotate();
        rotate.setPivotX(moon.getRadius());
        rotate.setPivotY(moon.getRadius());
    }

    /**
     * @author Albin Ahlbeck
     * Enables rotation by mouse
     */
    public void handleMouse() {
        moonRoot.setOnMousePressed(event -> {
            startDragX = event.getSceneX();
            startDragY = event.getSceneY();

            orgTransX = moonRoot.getTranslateX();
            orgTransY = moonRoot.getTranslateY();
        });

        moonRoot.setOnMouseDragged(event -> {
            double offsetX = event.getSceneX() - startDragX;
            double offsetY = event.getSceneY() - startDragY;

            double newTransX = orgTransX + offsetX;
            double newTransY = orgTransY + offsetY;
            matrixRotateNode(moon, 0, -newTransY / 100, newTransX / 100);
        });
    }

    /**
     * @author Albin Ahlbeck
     * Sets rotation values
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

    public void playSound(String filePath) {
        Media media = new Media(new File(filePath).toURI().toString());
        player = new MediaPlayer(media);
        player.setCycleCount(1);
        player.play();
    }

    public void stopSound() {
        if (player != null) {
            player.stop();
        }
    }
}
