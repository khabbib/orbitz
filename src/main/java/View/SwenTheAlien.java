package View;

import Model.Planet;

import java.awt.*;

import javafx.application.Platform;

import javafx.embed.swing.JFXPanel;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import javafx.scene.control.Tooltip;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.*;

import javafx.util.Duration;

/**
 * @Author: Manna Manojlovic
 * @author: Lanna Maslo
 * <p>
 * Funfact panel/area for Swen The Alien.
 * When user clicks on the alien a new fun fact about the planet/its moon/moons displays in a textarea.
 */
public class SwenTheAlien extends JPanel {
    private JPanel panel;
    private BorderLayout layout;

    private JTextArea funFactArea;

    private final Font funFactAreaFont = new Font("Nasalization Rg", Font.PLAIN, 18);

    private final Font titleFont = new Font("Nasalization Rg", Font.PLAIN, 12);

    private final Color color = Color.white;

    private int currentIndex = 0;

    private Planet planet;

    /**
     * @param planet takes planet as parameter so the current planet can be determined
     * @author Manna Manojlovic
     * <p>
     * Constructor
     */
    public SwenTheAlien(Planet planet) {
        this.planet = planet;
        createPanel();
    }

    /**
     * @author: Manna Manojlovic
     * @author: Lanna Maslo
     * <p>
     * JavaFX-panel for Swen The Alien
     * Sets the scene for Swen the Alien (which is a .png here).
     * Every button click on Swen generates a new funfact for current planet.
     * <p>
     * The user is given instructions to click on Swen using a tooltip.
     */
    private void initFX(JFXPanel swenFX) {
        Image swenImage = new Image(getClass().getResource("/Images/Swen.png").toExternalForm());
        ImageView swen = new ImageView(swenImage);

        Tooltip tooltip = new Tooltip("Click me!");
        tooltip.setStyle("-fx-font-size: 20");                   //CSS stylesheet, Oracle doc.
        tooltip.setShowDelay(Duration.millis(1));               //sets time before text appears after hovering over image

        swen.setPickOnBounds(true);
        Tooltip.install(swen, tooltip);

        swen.setFitWidth(170);
        swen.setFitHeight(220);

        Group swenRoot = new Group();

        swenRoot.getChildren().add(swen);

        Scene swenScene = new Scene(swenRoot);

        swenFX.setPreferredSize(new Dimension(171, 220));
        swenScene.setFill(new javafx.scene.paint.Color(0, 0, 0, 1));
        swen.setCursor(Cursor.HAND);
        swenFX.setScene(swenScene);

        //when user clicks on Swens image, calls the readFunFacts method
        swen.setOnMouseClicked(event ->
        {
            readFunFacts();
        });
    }

    /**
     * @Author: Manna Manojlovic
     * <p>
     * Reads funfacts from file depending on which planet user has selected
     * Go to setFunFacts() for the input code.
     */
    public void readFunFacts() {
        if (planet.getName().equals("Mercury")) {
            setFunFacts("funfacts/mercury.txt");
        } else if (planet.getName().equals("Venus")) {
            setFunFacts("funfacts/venus.txt");
        } else if (planet.getName().equals("Earth")) {
            setFunFacts("funfacts/earth.txt");
        } else if (planet.getName().equals("Mars")) {
            setFunFacts("funfacts/mars.txt");
        } else if (planet.getName().equals("Jupiter")) {
            setFunFacts("funfacts/jupiter.txt");
        } else if (planet.getName().equals("Saturn")) {
            setFunFacts("funfacts/saturn.txt");
        } else if (planet.getName().equals("Uranus")) {
            setFunFacts("funfacts/uranus.txt");
        } else if (planet.getName().equals("Neptune")) {
            setFunFacts("funfacts/neptune.txt");
        }
    }

    /**
     * @param filePath takes a path to file as parameter
     *                 buffers a file and reads it
     *                 takes each line from file and places in ArrayList
     *                 with each button click from user, a new line from the list is displayed to textarea.
     * @author Manna Manojlovic
     */
    public void setFunFacts(String filePath) {
        try (BufferedReader in = new BufferedReader(new FileReader(filePath))) {
            ArrayList<String> lines = new ArrayList<>();
            String line;    //a line in the textfile

            while ((line = in.readLine()) != null) {
                lines.add(line);
            }

            if (currentIndex >= lines.size() - 1) {
                currentIndex = 0;
            }

            currentIndex++;

            funFactArea.setText(String.valueOf(lines.get(currentIndex)));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * @Author: Manna Manojlovic
     * @author: Lanna Maslo
     * <p>
     * Panel for Swen The Alien and the fun fact textarea.
     * Creates a panel and initializes the components: image of Swen and the textArea
     * The Image is read through BufferedImage, set as an ImageIcon,
     * which in turn is set as a JLabel, a swing component.
     * In order to show an ImageIcon it has to be set as a Swing component.
     */
    public void createPanel() {
        TitledBorder titledBorder = BorderFactory.createTitledBorder(null, " DID YOU KNOW...",
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, titleFont, color);

        JFXPanel swenFX = new JFXPanel();

        JPanel pnl = new JPanel();

        panel = new JPanel(new BorderLayout());
        layout = new BorderLayout();

        setLayout(layout);
        setBackground(Color.black);
        setBorder(titledBorder);

        panel.setPreferredSize(new Dimension(402, 220));
        panel.setBackground(Color.black);

        funFactArea = new JTextArea();//(100, 100);
        funFactArea.setLineWrap(true);
        funFactArea.setWrapStyleWord(true);
        funFactArea.setEditable(false);
        funFactArea.setAlignmentX(10);
        funFactArea.setAlignmentY(120);
        funFactArea.setPreferredSize(new Dimension(245, 100));
        funFactArea.setFont(funFactAreaFont);
        funFactArea.setBackground(Color.black);
        funFactArea.setForeground(color);
        funFactArea.setForeground(Color.YELLOW);

        pnl.setPreferredSize(new Dimension(40, 100));
        pnl.setBackground(Color.black);

        panel.add(pnl, BorderLayout.WEST);
        panel.add(funFactArea, BorderLayout.CENTER);
        panel.add(swenFX, BorderLayout.EAST);

        add(panel, BorderLayout.SOUTH);

        Platform.runLater(() -> initFX(swenFX));
    }
}



