package View;

import Model.Theme;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class ColorPicker extends JFrame
{
    private JPanel north;
    private JPanel south;

    private JColorChooser colorChooser;

    private ButtonGroup buttonGroup;
    private JRadioButton mainColor1;
    private JRadioButton mainColor2;
    private JRadioButton secondaryColor1;
    private JRadioButton secondaryColor2;

    private RadioListener radioListener;
    private CreateListener createListener;
    private ColorListener colorListener;

    private JButton btnCreate;

    private JLabel lblInfo1;
    private JLabel lblInfo2;

    private int selectedIndex;

    private MainFrame mainFrame;

    private Color[] colors;


    public ColorPicker(MainFrame mainFrame)
    {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        setSize(new Dimension(800, 700));

        selectedIndex = 0;
        north = new JPanel();
        south = new JPanel();

        colorChooser = new JColorChooser();

        lblInfo1 = new JLabel("Select which color you wish to edit and then pick a color below");
        lblInfo2 = new JLabel("Press [CREATE THEME] once you have selected all colors");
        // setup Radio buttons
        mainColor1 = new JRadioButton("Menu Background");
        mainColor2 = new JRadioButton("Orbits");
        secondaryColor1 = new JRadioButton("Slider");
        secondaryColor2 = new JRadioButton("Media");
        btnCreate = new JButton("CREATE THEME");
        // setup button group
        buttonGroup = new ButtonGroup();
        buttonGroup.add(mainColor1);
        buttonGroup.add(secondaryColor1);
        buttonGroup.add(mainColor2);
        buttonGroup.add(secondaryColor2);

        mainColor1.setSelected(true); // set as default selection
        north = new JPanel();
        south = new JPanel();
        // create listeners
        radioListener = new RadioListener();
        createListener = new CreateListener();
        colorListener = new ColorListener();
        // add listeners
        btnCreate.addActionListener(createListener);
        mainColor1.addItemListener(radioListener);
        secondaryColor1.addItemListener(radioListener);
        mainColor2.addItemListener(radioListener);
        secondaryColor2.addItemListener(radioListener);
        colorChooser.getSelectionModel().addChangeListener(colorListener);
        // set default colors to white
        colors = new Color[4];
        Arrays.fill(colors, Color.WHITE);

        setupColors();

        north.setPreferredSize(new Dimension(800, 200));
        south.setPreferredSize(new Dimension(800, 500));
        mainColor1.setPreferredSize(new Dimension(150, 50));
        secondaryColor1.setPreferredSize(new Dimension(150, 50));
        mainColor2.setPreferredSize(new Dimension(150, 50));
        secondaryColor2.setPreferredSize(new Dimension(150, 50));
        btnCreate.setPreferredSize(new Dimension(700, 50));
        lblInfo1.setPreferredSize(new Dimension(700, 25));
        lblInfo2.setPreferredSize(new Dimension(700, 25));
        colorChooser.setPreferredSize(new Dimension(800, 500));

        north.add(mainColor1);
        north.add(secondaryColor1);
        north.add(mainColor2);
        north.add(secondaryColor2);
        north.add(lblInfo1);
        north.add(lblInfo2);
        north.add(btnCreate);
        south.add(colorChooser);

        add(north, BorderLayout.NORTH);
        add(south, BorderLayout.CENTER);
        setVisible(true);
    }

    private void setupColors()
    {
        mainColor1.setOpaque(false);
        mainColor2.setOpaque(false);
        secondaryColor1.setOpaque(false);
        secondaryColor2.setOpaque(false);

        mainColor1.setForeground(Color.WHITE);
        mainColor2.setForeground(Color.WHITE);
        secondaryColor1.setForeground(Color.WHITE);
        secondaryColor2.setForeground(Color.WHITE);

        mainColor1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        mainColor2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        secondaryColor1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        secondaryColor2.setBorder(BorderFactory.createLineBorder(Color.BLACK));

    }

    private class RadioListener implements ItemListener
    {
        @Override
        public void itemStateChanged(ItemEvent itemEvent)
        {
            Color tempColor = colorChooser.getColor();
            if (itemEvent.getStateChange() == ItemEvent.SELECTED)
            {
                JRadioButton tempObject = (JRadioButton)itemEvent.getItem();

               if (tempObject.equals(mainColor1))
               {
                   selectedIndex = 0;
               }

               if (tempObject.equals(secondaryColor1))
               {
                   selectedIndex = 1;
               }

               if (tempObject.equals(mainColor2))
               {
                   selectedIndex = 2;
               }

               if (tempObject.equals(secondaryColor2))
               {
                   selectedIndex = 3;
               }
               colors[selectedIndex] = tempColor;

            }

        }


    }

    private class ColorListener implements ChangeListener
    {
        @Override
        public void stateChanged(ChangeEvent changeEvent)
        {
            Color tempColor = colorChooser.getColor();

            if (selectedIndex == 0)
            {
                mainColor1.setForeground(tempColor);
            }

            if (selectedIndex == 1)
            {
                secondaryColor1.setForeground(tempColor);
            }

            if (selectedIndex == 2)
            {
                mainColor2.setForeground(tempColor);
            }

            if (selectedIndex == 3)
            {
                secondaryColor2.setForeground(tempColor);
            }
        }
    }

    private class CreateListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent actionEvent)
        {
            // converts awt color to java fx color by using rgb values
            javafx.scene.paint.Color mainColor2 = javafx.scene.paint.Color.rgb(colors[2].getRed(),
                    colors[2].getGreen(), colors[2].getBlue());

            javafx.scene.paint.Color secondaryColor2 = javafx.scene.paint.Color.rgb(colors[3].getRed(),
                    colors[3].getGreen(), colors[3].getBlue());

            String name = JOptionPane.showInputDialog("Choose a name for your theme");
            Theme tempTheme = new Theme(name, colors[0], colors[1], mainColor2, secondaryColor2);
            mainFrame.addTheme(tempTheme);
            exit();
        }
    }

    private void exit()
    {
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING)); // close window
    }

}