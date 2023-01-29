package View;

import Model.Planet;

import javax.swing.*;

import java.awt.*;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author Manna Manojlovic
 *
 * This class is the main frame for the Lunar panel. It extens JFrame and creates the frame.
 * MainLunarPanel is the contentpane for this
 * This class also has a listener for the closing of the window: when closed, sound stops playing.
 */
public class MainLunarFrame extends JFrame
{
    private MainLunarPanel panel;
    private Planet planet;

    /**
     * @author Manna Manojlovic
     *
     * Constructor
     * Calls setupFrame() which has everything needed for creating the basic frame
     */
    public MainLunarFrame(Planet planet)
    {
        this.planet = planet;
        setupFrame();
    }

    /**
     * @author Manna Manojlovic
     *
     * Sets up the frame with size, location and adds MainLunarPanel as conent.
     * Calls addListeners() for the window closing event,
     * which stops playing the sound when window is closed
     */
    public void setupFrame()
    {
        panel = new MainLunarPanel(planet);

        setSize(new Dimension(1000, 500));
        setLocation(100,120);
        setResizable(false);
        add(panel);

        addListeners();
    }

    /**
     * @author Lanna Maslo
     *
     * Listenes to the closing of the frame.
     * When this occurs the sound stops playing.
     */
    public void addListeners()
    {
        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                panel.stopSound();
            }
        });
    }

    /**
     * @author Lanna Maslo
     *
     * Plays a sound file when lunar info window opens.
     *
     * @param filePath takes a sound file as parameter
     */
    public void playSound(String filePath)
    {
        panel.playSound(filePath);
    }
}
