package View;

import Model.Planet;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;

import javafx.scene.Group;
import javafx.scene.Scene;

import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * @author Manna Manojlovic
 * @author Lanna Maslo
 * @author Albin Ahlbeck
 * <p>
 * This class is the image gallery class for moon pictures
 * It's called by MainLunarPanel.java and placed on the east side of BorderLayout
 * <p>
 * This class sets images for the moons as JLabels
 * The user can scroll through the images by using two buttons: previous and next
 * The buttons are illustrated as arrows in the GUI.
 * <p>
 * Some planets don't have any moons and won't therefore have anything here.
 * Those planets that have MANY moons will only have the 4-5 innermost moons displayed here.
 */
public class LunarGalleryPanel extends JPanel {
    private Planet planet;
    private ArrayList<ImageIcon> imageList;

    private JPanel panel;
    private JPanel panelBtn;

    private JLabel lblImage;

    private BasicArrowButton btnNext;
    private BasicArrowButton btnPrevious;

    private PreviousListener previousListener;
    private NextListener nextListener;

    private int picIndex = 0;

    public LunarGalleryPanel(Planet planet) {
        this.planet = planet;

        createPanel(planet);

        addImages();
    }

    /**
     * @param planet takes planet as parameter to determine current planet/current moon
     *               <p>
     *               This method creates a panel which also has a JavaFX-panel for the gallery of images.
     *               It also creates a panel for the buttons - previous and next and their listeners.
     * @author Manna Manojlovic
     * @author lanna Maslo
     * @author Albin Ahlbeck
     */
    public void createPanel(Planet planet) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); // makes the ui cross platform
        } catch (Exception e) {
            System.out.println(e);
        }

        JFXPanel jfx = new JFXPanel();

        this.planet = planet;

        setLayout(new BorderLayout());
        setBackground(Color.BLACK);

        panel = new JPanel(new BorderLayout());
        panelBtn = new JPanel(new BorderLayout());

        lblImage = new JLabel();

        previousListener = new PreviousListener();
        nextListener = new NextListener();

        btnNext = new BasicArrowButton(BasicArrowButton.EAST);
        btnNext.setPreferredSize(new Dimension(100, 50));
        btnNext.setMinimumSize(new Dimension(100, 50));
        btnNext.addActionListener(nextListener);
        btnNext.setFocusPainted(false);
        btnNext.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        btnNext.setBackground(Color.black);

        btnPrevious = new BasicArrowButton(BasicArrowButton.WEST);
        btnPrevious.setPreferredSize(new Dimension(100, 50));
        btnPrevious.addActionListener(previousListener);
        btnPrevious.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        btnPrevious.setBackground(Color.black);

        lblImage.setSize(336, 280);
        lblImage.setBorder(BorderFactory.createLineBorder(Color.BLACK, 8, false));

        imageList = new ArrayList<>();

        panel.setPreferredSize(new Dimension(350, 280));
        panel.setBackground(Color.black);
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 30, 10));

        panel.add(btnNext, BorderLayout.EAST);
        panel.add(btnPrevious, BorderLayout.WEST);
        panel.add(lblImage, BorderLayout.CENTER);

        panelBtn.setBackground(Color.black);
        panelBtn.setPreferredSize(new Dimension(550, 200));
        panelBtn.add(jfx, BorderLayout.WEST);

        add(panel);

        Platform.runLater(() -> initFX(jfx));
    }

    /**
     * @param jfxPanel javaFX panel as parameter
     *                 <p>
     *                 Creates and sets up a JavaFX-panel to be placed on Swing's JPanel
     * @author Lanna Maslo
     */
    private void initFX(JFXPanel jfxPanel) {
        javafx.scene.paint.Color fxColor = new javafx.scene.paint.Color(0, 0, 0, 1);

        Group root = new Group();
        Scene scene = new Scene(root);

        scene.setFill(fxColor);
        jfxPanel.setPreferredSize(new Dimension(40, 200));
        jfxPanel.setBackground(Color.black);
        jfxPanel.setScene(scene);
    }

    /**
     * @author Albin Ahlbeck
     * @author Manna Manojlovic
     * <p>
     * Adds images to each planet
     */
    public void addImages() {
        if (planet.getName().equals("Mercury")) {
            readImages("/Images/nothing.gif");
        } else if (planet.getName().equals("Venus")) {
            readImages("/Images/nothing.gif");
        } else if (planet.getName().equals("Earth")) {
            readImages("/Images/moon1.jpg");
            readImages("/Images/moon2.jpg");
            readImages("/Images/moon3.jpg");
            readImages("/Images/moon4.jpg");
            readImages("/Images/moon5.jpg");
        } else if (planet.getName().equals("Mars")) {
            readImages("/Images/phobos1.jpg");
            readImages("/Images/phobos2.jpg");
            readImages("/Images/phobosdeimos.jpg");
            readImages("/Images/deimos1.jpg");
            readImages("/Images/deimos2.jpg");

        } else if (planet.getName().equals("Jupiter")) {
            readImages("/Images/io1.jpg");
            readImages("/Images/europa1.jpg");
            readImages("/Images/jupiterMoons2.jpg");
            readImages("/Images/ganymedes1.jpg");
            readImages("/Images/jupiterMoons3.jpg");
            readImages("/Images/callisto1.jpg");
        } else if (planet.getName().equals("Saturn")) {
            readImages("/Images/titan1.jpg");
            readImages("/Images/dione1.jpg");
            readImages("/Images/iapetus1.jpg");
            readImages("/Images/enceladus1.jpg");
            readImages("/Images/enceladus2.jpeg");
            readImages("/Images/tethys1.jpg");
            readImages("/Images/titandione.jpg");
            readImages("/Images/rhea1.jpg");
        } else if (planet.getName().equals("Uranus")) {
            readImages("/Images/ariel1.jpg");
            readImages("/Images/miranda1.jpg");
            readImages("/Images/titania1.jpg");

        } else if (planet.getName().equals("Neptune")) {
            readImages("/Images/triton1.jpg");
            readImages("/Images/proteus1.jpg");
        }

        lblImage.setIcon(imageList.get(0));
    }

    /**
     * @param filePath takes an image file path as parameter
     *                 filepath is set in the addImages() method.
     * @author Manna Manojlovic
     * @author Albin Ahlbeck
     */
    public void readImages(String filePath) {
        imageList.add(new ImageIcon(getClass().getResource(filePath)));
    }

    /**
     * @author Albin Ahlbeck
     * <p>
     * Listens to the next button
     * Iterates the gallery
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
     * @author Albin Ahlbeck
     * Listens to the previous button
     * Iterates the gallery
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
