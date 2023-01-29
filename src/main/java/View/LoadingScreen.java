package View;

import javax.swing.*;

import java.awt.*;

/**
 * Loading screen that appears when the program is buffering
 *
 * @author Simon Måtegen
 * @version 1.0
 */
public class LoadingScreen extends JFrame
{
    private JPanel panel = new JPanel();

    public LoadingScreen()
    {
        GridBagConstraints gbc = new GridBagConstraints();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setSize(new Dimension(240, 150));
        setResizable(false);

        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.BLACK);
        JLabel label = new JLabel();
        label.setIcon(new ImageIcon("src/Images/loading.gif"));

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(label, gbc);

        add(panel);
    }
}
