package View;

import Model.Planet;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @Author Manna Manojlovic
 * <p>
 * This class has the JTextArea for the informative text for each of the moons
 * It's called my MainLunarPanel.java and placed on the west side of the BorderLayout
 */

public class LunarTextPanel extends JPanel {
    private JTextArea textArea;
    private JScrollPane scrollPane;

    private Planet planet;


    /**
     * @Author Manna Manojlovic
     * <p>
     * Constructor, calls setupPanel()
     */
    public LunarTextPanel(Planet planet) {
        this.planet = planet;
        setupPanel();
    }

    /**
     * @Author Manna Manojlovic
     * <p>
     * This method sets background to the extended JPanel as black
     * Initializes the textarea and sets size, color, textcolor and linewrap
     * Initializes scrollpane, which the textArea is passed to
     * <p>
     * Adds scrollpanel to the extended JPanel
     */
    public void setupPanel() {
        setBackground(Color.black);

        textArea = new JTextArea(20, 30);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBackground(Color.black);
        textArea.setForeground(Color.LIGHT_GRAY);
        textArea.setFont(new Font("Nasalization Rg", Font.PLAIN, 11));
        textArea.setEditable(false);

        scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(330, 330));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        setTextToTextArea();

        add(scrollPane);
    }

    /**
     * @Author Manna Manojlovic
     * This method passes a filepath to readFile(String filePath)
     * depending on which planet is selected different text will be set about the moons
     * to the textArea.
     */
    public void setTextToTextArea() {

        if (planet.getName().equals("Earth")) {
            readFile("lunarFacts/earth_moon.txt");
        } else if (planet.getName().equals("Mars")) {
            readFile("lunarFacts/mars_moons.txt");
        } else if (planet.getName().equals("Jupiter")) {
            readFile("lunarFacts/jupiter_moons.txt");
        } else if (planet.getName().equals("Saturn")) {
            readFile("lunarFacts/saturn_moons.txt");
        } else if (planet.getName().equals("Uranus")) {
            readFile("lunarFacts/uranus_moons.txt");
        } else if (planet.getName().equals("Neptune")) {
            readFile("lunarFacts/neptune_moons.txt");
        }
    }

    /**
     * @param filePath takes a filepath as parameter from setTextToTextArea
     *                 the filepath is to a textfile which has info about the moons
     * @Author Manna Manojlovic
     */
    public void readFile(String filePath) {
        String content;
        try {
            content = new String(Files.readAllBytes(Paths.get(filePath)));
            textArea.setText(content);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
