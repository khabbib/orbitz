package View;

import Model.Planet;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Manna Manojlovic
 * <p>
 * This class is the table with information about the selected planet.
 * Shows information about travel time from earth, last visit, next visit and possibility of life
 * for each planet in the solar system
 */
public class InfoBoxPanel extends JPanel implements ActionListener {
    private final Font font1 = new Font("Nasalization Rg", Font.BOLD, 14);
    private final Font font2 = new Font("Nasalization Rg", Font.PLAIN, 11);

    private JTable table;
    private Object[] cols;

    private Planet planet;

    private JButton lunarTest;

    private MainLunarFrame mainLunarFrame;

    /**
     * @param planet - takes Planet as parameter so each planet's individual data can be set
     * @author Manna Manojlovic
     * Constructor
     */
    public InfoBoxPanel(Planet planet) {
        this.planet = planet;
        setupTable();
        mainLunarFrame = new MainLunarFrame(planet);
    }

    /**
     * @author Manna Manojlovic
     * <p>
     * Setting up the table with columns for travel time, latest visit, next visit and possibility of life.
     */
    public void setupTable() {
        lunarTest = new JButton("Moons");
        lunarTest.setPreferredSize(new Dimension(75, 25));
        lunarTest.addActionListener(this);

        setBackground(Color.black);     //background to panel

        table = new JTable();

        JScrollPane s = new JScrollPane(table);

        cols = new String[]{"TRAVEL TIME", "LATEST VISIT", "NEXT VISIT", "POSSIBLE LIFE"};

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setColumnIdentifiers(cols);

        //setting the column width for each column
        table.getColumnModel().getColumn(0).setPreferredWidth(18);
        table.getColumnModel().getColumn(1).setPreferredWidth(32);
        table.getColumnModel().getColumn(2).setPreferredWidth(12);
        table.getColumnModel().getColumn(3).setPreferredWidth(20);

        //hiding gridlines between rows
        table.setShowGrid(false);
        table.setRowMargin(0);
        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(false);

        //setting the margin for where the data begins to display
        table.setIntercellSpacing(new Dimension(10, 0));

        //setting size for the table
        table.setPreferredScrollableViewportSize(new Dimension(500, 50));
        table.setFillsViewportHeight(true);

        //setting colors and fonts to the columns
        table.getTableHeader().setFont(font1);
        table.getTableHeader().setOpaque(false);
        table.getTableHeader().setBackground(Color.black);
        table.getTableHeader().setForeground(Color.WHITE);      //text color

        //setting fonts and color to the rows
        table.setFont(font2);
        table.setBackground(Color.black);
        table.setForeground(Color.yellow);  //text color
        table.setRowHeight(25);

        addRows();                         //adding the rows to the columns respectively

        add(s, BorderLayout.CENTER);        //placing the table on the layout

        if (!planet.getName().equals("Mercury") && !planet.getName().equals("Venus")) { //Mercury and Venus do not have moons
            add(lunarTest, BorderLayout.SOUTH);//placing the lunar button on the layout
        }
    }

    /**
     * @Author Manna Manojlovic
     * <p>
     * Method for adding rows to table under respective column.
     * Hard coded info for each planet.
     * <p>
     * LIMIT: The data for the rows can be a MAX of 18 chars long.
     */
    public void addRows() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        var colArr = new String[5];

        if (planet.getName().equals("Mercury")) {
            colArr[0] = "3.4 years";
            colArr[1] = "2013, Mariner10";
            colArr[2] = "2029: ESA+Japan";
            colArr[3] = "No";
        } else if (planet.getName().equals("Venus")) {
            colArr[0] = "1.3 years";
            colArr[1] = "2010, IKAROS";
            colArr[2] = "2021: NASA";
            colArr[3] = "Maybe";
        } else if (planet.getName().equals("Earth")) {
            colArr[0] = "3 days (Moon)";
            colArr[1] = "2019, to moon";
            colArr[2] = "2024, NASA";
            colArr[3] = "Yes";
        } else if (planet.getName().equals("Mars")) {
            colArr[0] = "7-9 months";
            colArr[1] = "2018, InSight";
            colArr[2] = "2020, NASA";
            colArr[3] = "Maybe";
        } else if (planet.getName().equals("Jupiter")) {
            colArr[0] = "2-6 years";
            colArr[1] = "Now: Juno orbits";
            colArr[2] = "2022, ESA";
            colArr[3] = "Maybe on moons";
        } else if (planet.getName().equals("Saturn")) {
            colArr[0] = "3-6 years";
            colArr[1] = "2017, Cassini";
            colArr[2] = "2034 to Titan";
            colArr[3] = "Maybe on moons";
        } else if (planet.getName().equals("Uranus")) {
            colArr[0] = "9.5 years";
            colArr[1] = "1986, Voyager2";
            colArr[2] = "None";
            colArr[3] = "No";
        } else if (planet.getName().equals("Neptune")) {
            colArr[0] = "12 years";
            colArr[1] = "1989, Voyager2";
            colArr[2] = "None";
            colArr[3] = "No";
        }

        model.addRow(colArr);       //add the rows above to the table model
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == lunarTest) {
            System.out.println("testar lunarknappen");
            mainLunarFrame.setVisible(true);

            if (planet.getName().equals("Earth")) {
                mainLunarFrame.playSound("sound/oneSmallStep.mp3");
            }
        }
    }
}
