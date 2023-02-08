package View;

import Model.Planet;

import javafx.application.Platform;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.embed.swing.JFXPanel;

import javafx.geometry.Orientation;

import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;

import javafx.scene.control.Slider;

import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.media.MediaPlayer;

import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;

import java.util.ArrayList;

import javafx.scene.media.Media;

/**
 * @author Manna Manojlovic
 * @author Lanna Maslo
 * @author Albin Ahlbeck
 * @version 1.0
 * <p>
 * Panel for the gallery of images and sounds for planets.
 */
public class ImageGalleryPanel extends JPanel {
    private Planet planet;
    private JPanel panel;
    private JPanel panelBtn;

    private JLabel lblImage;

    private ArrayList<ImageIcon> imageList;

    private BasicArrowButton btnNext;
    private BasicArrowButton btnPrevious;

    private PreviousListener previousListener;
    private NextListener nextListener;

    private MediaPlayer mediaPlayer;

    private int picIndex = 0;

    private InfoBoxPanel infoPanel;

    private Slider soundSlider;

    /**
     * @author Manna Manojlovic
     * @version 1.0
     * <p>
     * Constructor
     * Calls createPanel(), addImages and creates instance of InfoBoxPanel
     */
    public ImageGalleryPanel(Planet planet) {
        infoPanel = new InfoBoxPanel(planet);
        createPanel(planet);
        addImages();
    }

    /**
     * @author Manna Manojlovic, Lanna Maslo
     * @version 1.0
     * <p>
     * Method for initializing the components and the panel for images
     * Placing all components on Jpanel.
     * <p>
     * places the table with info about planet on the panel.
     * <p>
     * This method also has a JavaFX-panel and JavaFX- sound icons,
     * which plays a planetary sound on click, by calling playSound().
     */
    public void createPanel(Planet planet) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); // makes the ui cross platform
        } catch (Exception e) {
            System.out.println(e);
        }

        setLayout(new BorderLayout());
        setBackground(Color.BLACK);

        JFXPanel jfx = new JFXPanel();

        this.planet = planet;

        panel = new JPanel(new BorderLayout());
        panelBtn = new JPanel(new BorderLayout());

        lblImage = new JLabel();

        previousListener = new PreviousListener();
        nextListener = new NextListener();

        btnNext = new BasicArrowButton(BasicArrowButton.EAST);
        btnNext.setPreferredSize(new Dimension(100, 50));
        btnNext.setMinimumSize(new Dimension(100, 50));

        btnPrevious = new BasicArrowButton(BasicArrowButton.WEST);
        btnPrevious.setPreferredSize(new Dimension(100, 50));
        btnNext.addActionListener(nextListener);
        btnPrevious.addActionListener(previousListener);
        btnNext.setFocusPainted(false);
        btnPrevious.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        btnPrevious.setBackground(Color.black);
        btnNext.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        btnNext.setBackground(Color.black);

        lblImage.setSize(336, 280);
        lblImage.setBorder(BorderFactory.createLineBorder(Color.BLACK, 8, false));

        imageList = new ArrayList<>();

        panel.setPreferredSize(new Dimension(410, 280));
        panel.setBackground(Color.black);

        panelBtn.setPreferredSize(new Dimension(550, 200));

        panel.add(btnNext, BorderLayout.EAST);
        panel.add(btnPrevious, BorderLayout.WEST);
        panel.add(lblImage, BorderLayout.CENTER);

        panelBtn.add(infoPanel, BorderLayout.CENTER);        //information table
        panelBtn.add(jfx, BorderLayout.WEST);
        panelBtn.setBackground(Color.black);

        add(panel, BorderLayout.EAST);
        add(panelBtn, BorderLayout.WEST);

        Platform.runLater(new Runnable() {
            public void run() {
                initFX(jfx);
            }
        });
    }

    /**
     * Adds images to each planet
     *
     * @author Albin Ahlbeck
     * @author Manna Manojlovic
     * @version 1.0
     */
    public void addImages() {
        System.out.println("Planet " + planet.getName());
        try {

            if (planet.getName().equals("Mercury")) {
                addImage("/Images/m1.jpg");
                addImage("/Images/m2.jpg");
                addImage("/Images/m3.jpg");
            } else if (planet.getName().equals("Venus")) {
                addImage("/Images/venus1.jpg");
                addImage("/Images/venus2.jpg");
                addImage("/Images/venus3.jpg");
                addImage("/Images/venus4.jpg");
                addImage("/Images/venus5.jpg");
            } else if (planet.getName().equals("Earth")) {
                addImage("/Images/earth1.jpg");
                addImage("/Images/earth2.jpg");
                addImage("/Images/earth3.jpg");
                addImage("/Images/earth4.jpg");
                addImage("/Images/earth5.jpg");
            } else if (planet.getName().equals("Mars")) {
                addImage("/Images/mars1.jpg");
                addImage("/Images/mars2.jpg");
                addImage("/Images/mars3.jpg");
                addImage("/Images/mars4.jpg");
                addImage("/Images/mars5.jpg");
                addImage("/Images/mars6.jpg");
            } else if (planet.getName().equals("Jupiter")) {
                addImage("/Images/jupiter1.jpg");
                addImage("/Images/jupiter2.jpg");
                addImage("/Images/jupiter3.jpg");
                addImage("/Images/jupiter4.jpg");
                addImage("/Images/jupiter5.jpg");

            } else if (planet.getName().equals("Saturn")) {
                addImage("/Images/saturn1.jpg");
                addImage("/Images/saturn2.jpeg");
                addImage("/Images/saturn3.jpg");
                addImage("/Images/saturn4.JPG");
            } else if (planet.getName().equals("Uranus")) {
                addImage("/Images/uranus1.jpg");
                addImage("/Images/uranusFront.jpg");
                addImage("/Images/uranusSide.jpg");
            } else if (planet.getName().equals("Neptune")) {
                addImage("/Images/neptune1.jpg");
                addImage("/Images/neptune2.jpg");
                addImage("/Images/neptune3.jpg");
            } else {
                System.out.println("No planet found");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        lblImage.setIcon(imageList.get(0));

    }

    public void addImage(String filePath) {
        imageList.add(new ImageIcon(getClass().getResource(filePath)));
    }

    /**
     * Creates a sound-button and adds it to a JavaFX panel
     * When btnSound is clicked, the method playSound() starts playing the sound of the chosen planet
     *
     * @param jfxPanel The JavaFX panel to be created
     * @author Lanna Maslo
     * @version 1.0
     */
    private void initFX(JFXPanel jfxPanel) {
        javafx.scene.paint.Color fxColor = new javafx.scene.paint.Color(0, 0, 0, 1);

        Group root = new Group();
        Scene scene = new Scene(root);

        soundSlider = new Slider();
        soundSlider.setOrientation(Orientation.VERTICAL);
        soundSlider.setPrefHeight(80);
        soundSlider.setMax(100);
        soundSlider.setValue(20);
        soundSlider.setLayoutY(40);

        Image soundOnImage = new Image(getClass().getResource("/Images/soundOn.png").toExternalForm());
        ImageView soundOn = new ImageView(soundOnImage);
        soundOn.setFitHeight(20);
        soundOn.setFitWidth(20);
        soundOn.setLayoutY(8);
        soundOn.setCursor(Cursor.HAND);
        Tooltip.install(soundOn, (new Tooltip("Mute the sound of " + planet.getName() + " :(")));

        Image soundOffImage = new Image(getClass().getResource("/Images/soundOff.png").toExternalForm());
        ImageView soundOff = new ImageView(soundOffImage);
        soundOff.setFitHeight(20);
        soundOff.setFitWidth(20);
        soundOff.setLayoutY(8);
        soundOff.setCursor(Cursor.HAND);
        Tooltip.install(soundOff, (new Tooltip("Listen to the sound of " + planet.getName() + "!")));

        soundOff.setOnMouseClicked(event ->
        {
            planetSounds();
            root.getChildren().remove(soundOff);
            root.getChildren().add(soundOn);
        });

        soundOn.setOnMouseClicked(event ->
        {
            mediaPlayer.stop();
            stopMp3();
            root.getChildren().remove(soundOn);
            root.getChildren().add(soundOff);
        });

        root.getChildren().add(soundSlider);
        root.getChildren().add(soundOff);

        scene.setFill(fxColor);
        jfxPanel.setPreferredSize(new Dimension(40, 200));
        jfxPanel.setBackground(Color.black);
        jfxPanel.setScene(scene);
    }

    /**
     * @author Manna Manojlovic
     * @author Lanna Maslo
     * @version 1.0
     * <p>
     * This method reads the sound files depending on which planet is selected by user.
     * Go to playSound() for the code for playing the actual sound.
     */
    public void planetSounds() {
        if (planet.getName().equals("Sun")) {
            playMp3("sound/sun.mp3");
        } else if (planet.getName().equals("Mercury")) {
            playMp3("sound/mercury.mp3");
        } else if (planet.getName().equals("Venus")) {
            playMp3("sound/venus.mp3");
        } else if (planet.getName().equals("Earth")) {
            playMp3("sound/earth.mp3");
        } else if (planet.getName().equals("Mars")) {
            playMp3("sound/mars.mp3");
        } else if (planet.getName().equals("Jupiter")) {
            playMp3("sound/jupiter.mp3");
        } else if (planet.getName().equals("Saturn")) {
            playMp3("sound/saturn.mp3");
        } else if (planet.getName().equals("Uranus")) {
            playMp3("sound/uranus.mp3");
        } else if (planet.getName().equals("Neptune")) {
            playMp3("sound/neptune.mp3");
        }
    }

    public void stopMp3() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    /**
     * @param filePath takes mp3 as parameter
     *                 <p>
     *                 This method is for the mp3-files that JavaFX can play when user selects a planet which has recordings in mp3.
     * @author Manna Manojlovic
     * @author Lanna Maslo
     */
    public void playMp3(String filePath) {
        String bip = filePath;
        Media hit = new Media(new File(bip).toURI().toString());
        mediaPlayer = new MediaPlayer(hit);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        soundSlider.setValue((int) mediaPlayer.getVolume() * 100);

        soundSlider.valueProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable observable) {
                mediaPlayer.setVolume(soundSlider.getValue() / 100);
            }
        });

        mediaPlayer.play();
    }

    /**
     * Listens to the next button
     * Iterates the gallery
     *
     * @author Albin Ahlbeck
     * @version 1.0
     */
    private class NextListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (picIndex < imageList.size() - 1) {
                picIndex++;
            } else {
                picIndex = 0;
            }
            lblImage.setIcon(imageList.get(picIndex));
        }
    }

    /**
     * Listens to the previous button
     * Iterates the gallery
     *
     * @author Albin Ahlbeck
     * @version 1.0
     */
    private class PreviousListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (picIndex > 0) {
                picIndex--;
            } else {
                picIndex = imageList.size() - 1;
            }
            lblImage.setIcon(imageList.get(picIndex));
        }
    }
}
