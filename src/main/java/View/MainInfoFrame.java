package View;

import Model.Planet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


/**
 * @author Manna Manojlovic
 * @author Albin Ahlbeck
 * @author Lanna Maslo
 * @version 1.0
 * <p>
 * This is the main frame for the planetary information GUI.
 * It has the MainInfoPanel main panel placed on it and the FX panel for lunar orbit.
 */
public class MainInfoFrame extends JFrame {
    private MainInfoPanel panel;
    private JPanel pnlTitle;

    private JLabel lblTitle;

    private Planet planet;

    /**
     * Adds a planet to be displayed when creating a MainInfoFrame
     **/
    public MainInfoFrame(Planet planet, Theme theme) {
        this.planet = planet;
        frame(planet, theme);

    }

    /**
     * @author Manna Manolojvic
     * @author Lanna Maslo
     * @author Albin Ahlbeck
     * Sets up the frame and adds all the componenets
     **/
    public void frame(Planet planet, Theme theme) {
        setLayout(new BorderLayout());

        panel = new MainInfoPanel(planet);

        setSize(1000, 700);
        setBackground(Color.BLACK);
        setResizable(false);
        setTitle(planet.getName());
        setIconImage(new ImageIcon(getClass().getResource("/Images/" + planet.getName() + "Icon.png").toExternalForm()).getImage());

        pnlTitle = new JPanel();
        pnlTitle.setPreferredSize(new Dimension(getWidth(), 100));
        pnlTitle.setBackground(theme.getMainColor());

        lblTitle = new JLabel(planet.getName());
        lblTitle.setFont(new Font("Earth Orbiter", Font.PLAIN, 40));
        lblTitle.setForeground(theme.getSecondaryColor());
        lblTitle.setPreferredSize(new Dimension(getWidth() / 2, 100));

        pnlTitle.add(lblTitle);
        add(pnlTitle);
        add(panel, BorderLayout.NORTH);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            /**
             * @author Lanna Maslo
             * Stop the sound when the window is closing
             **/
            public void windowClosing(WindowEvent e) {
                panel.stopMp3();
            }
        });
        setVisible(true);
    }
}
