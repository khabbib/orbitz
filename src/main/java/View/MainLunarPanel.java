package View;

import Model.Planet;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Manna Manojlovic
 * @author Lanna Maslo
 * <p>
 * Main panel for all other lunar panels. They are placed on this panel,
 * which is used by MainLunarFrame.java
 * <p>
 * This class also has a
 */
public class MainLunarPanel extends JPanel implements ActionListener {
    private JButton returnBtn = new JButton("Stop sound");

    private JPanel btnPanel;

    private JLabel headline;

    private LunarTextPanel lunarTextPanel;

    private LunarGalleryPanel lunarGalleryPanel;

    private LunarPanelCenter lunarPanelCenter;

    private Planet planet;


    public MainLunarPanel(Planet planet) {
        this.planet = planet;

        lunarPanelCenter = new LunarPanelCenter(planet);
        lunarTextPanel = new LunarTextPanel(planet);
        lunarGalleryPanel = new LunarGalleryPanel(planet);

        setupPanel();
    }

    public void setupPanel() {
        setLayout(new BorderLayout());      //sets layout to JPanel which is extendedd
        setBackground(Color.black);         //sets background color to JPanel which is extended

        FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT);
        flowLayout.setHgap(70);

        btnPanel = new JPanel(flowLayout);
        btnPanel.setPreferredSize(new Dimension(1000, 100));
        btnPanel.setBackground(Color.black);

        returnBtn.setPreferredSize(new Dimension(120, 25));
        returnBtn.addActionListener(this);

        initializeHeadLine();

        btnPanel.add(returnBtn);
        btnPanel.add(headline);

        add(btnPanel, BorderLayout.NORTH);
        add(lunarGalleryPanel, BorderLayout.EAST);
        add(lunarTextPanel, BorderLayout.WEST);
        add(lunarPanelCenter, BorderLayout.CENTER);
    }

    /**
     * @author Manna Manojlovic
     * This is the headline title for each of the moon windows
     */
    public void initializeHeadLine() {
        headline = new JLabel();
        headline.setFont(new Font("Earth Orbiter", Font.BOLD, 55));
        headline.setForeground(Color.YELLOW);

        if (planet.getName().equals("Earth")) {
            headline.setText("THE MOON");
        } else if (planet.getName().equals("Mars")) {
            headline.setText("PHOBOS & DEIMOS");
        } else if (planet.getName().equals("Jupiter")) {
            headline.setText("JUPITER'S MOONS");
        } else if (planet.getName().equals("Saturn")) {
            headline.setText("SATURN'S MOONS");
        } else if (planet.getName().equals("Uranus")) {
            headline.setText("URANUS'S MOONS");
        } else if (planet.getName().equals("Neptune")) {
            headline.setText("NEPTUNE'S MOONS");
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == returnBtn) {
            lunarPanelCenter.stopSound();
        }
    }

    public void playSound(String filePath) {
        lunarPanelCenter.playSound(filePath);
    }

    public void stopSound() {
        lunarPanelCenter.stopSound();
    }
}
