package Controller;

import Model.Planet;
import View.InfoPopover;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import org.controlsfx.control.PopOver;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;

/**
 * Handles the creation of PopOver elements that display planetary information.
 * @author Joel Eriksson Sinclair
 */
public class InfoPopoverBuilder {
    private final static String MOON_SUFFIX = "_moons";
    /**
     * Creates a popover element populated with information about a given planet and its moons.
     * @param planet Planet to display information about.
     * @return A popover with information about a given planet and its moons
     * @throws IOException Upon errors loading FXML files.
     */
    public PopOver createInfoPopover(Planet planet) throws IOException {
        PopOver popOver = new PopOver();

        // Create TabPane
        TabPane tabPane = new TabPane();

        Tab planetTab = new Tab(planet.getName());
        tabPane.getTabs().add(planetTab);
        Node planetPane = createPlanetPane(planet.getName());
        planetTab.setContent(planetPane);

        Tab moonTab = new Tab("Moons");
        Node moonPane = createMoonPane(planet.getName());
        if(moonPane != null) {
            moonTab.setContent(moonPane);
            tabPane.getTabs().add(moonTab);

            popOver.setContentNode(tabPane);
        } else {
            popOver.setContentNode(planetPane);
        }

        return popOver;
    }

    /**
     * Creates a Pane populated with information about the given planet.
     * @param planetName
     * @return The root node of the created UI element
     * @throws IOException Upon errors when loading the fxml file
     */
    private Node createPlanetPane(String planetName) throws IOException {
        // Load FXML
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/InfoPopover.fxml"));
        Parent rootNode = fxmlLoader.load();
        InfoPopover fxmlController = fxmlLoader.getController();

        // Fetch the cartoon image
        URL imgURL = getClass().getResource("/Images/planets/" + planetName + ".png");
        Image planetCartoonImage = new Image(imgURL.toExternalForm());

        // Planet URL
        String planetImagesURL = "src/main/resources/Images/planets/" + planetName;

        // Fetch resources
        Image[] planetImages = getImagesFromDirectory(planetImagesURL);
        String[] planetInfoList = fetchInfoList(planetName);

        // Update UI content
        fxmlController.title.setText(planetName);
        if(planetImages != null && planetImages.length > 0) {
            fxmlController.setImages(planetImages);
            fxmlController.planetImage.setImage(planetImages[0]);
        } else {
            fxmlController.planetImage.setImage(planetCartoonImage);
        }
        if(planetInfoList != null && planetInfoList.length > 0) {
            fxmlController.setInfoList(planetInfoList);
            fxmlController.infoText.setText(planetInfoList[0]);
        } else {
            fxmlController.infoText.setText("No information found.");
        }

        return rootNode;
    }

    private Node createMoonPane(String planetName) throws IOException {
        // Load FXML
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/InfoPopover.fxml"));
        Parent rootNode = fxmlLoader.load();
        InfoPopover fxmlController = fxmlLoader.getController();

        // Planet URL
        String planetImagesURL = "src/main/resources/Images/planets/" + planetName;
        String moonImagesURL = planetImagesURL + "/moons";

        // Fetch resources
        Image[] images = getImagesFromDirectory(moonImagesURL);
        String[] infoList = fetchInfoList(planetName + MOON_SUFFIX);
        if(images == null || infoList == null) {
            return null;
        }

        // Update UI content
        fxmlController.title.setText("Moons");
        if(images.length > 0) {
            fxmlController.setImages(images);
            fxmlController.planetImage.setImage(images[0]);
        } else {
            fxmlController.planetImage.setImage(null);
        }
        if(infoList.length > 0) {
            fxmlController.setInfoList(infoList);
            fxmlController.infoText.setText(infoList[0]);
        } else {
            fxmlController.infoText.setText("No information found.");
        }

        return rootNode;
    }

    /**
     * Returns a list of all found .jpg and .png files in a given directory.
     * @param directoryPath Path to the directory to search.
     * @return A list of the found images. Returns null if the given path is not a directory.
     */
    private Image[] getImagesFromDirectory(String directoryPath) {
        ArrayList<Image> imageList = new ArrayList<>();

        try {
            File directoy = new File(directoryPath);
            File[] imgFiles = directoy.listFiles((dir, name) -> name.toLowerCase().endsWith(".jpg") || name.toLowerCase().endsWith(".png"));
            if(imgFiles == null) {
                System.err.println("getImagesFromDirectory: No images found in directory: " + directoryPath);
                return null;
            }
            if(imgFiles.length > 0) {
                for (File imgFile : imgFiles) {
                    String filePath = imgFile.getAbsolutePath();
                    Image img = new Image(filePath);
                    imageList.add(img);
                }
            }
            return imageList.toArray(new Image[0]);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param fileName Name of a planet. Suffix with _moons to get moon info for specified planet.
     * @return
     */
    private String[] fetchInfoList(String fileName) {
        String filePath = "src/main/resources/Info/" + fileName.toLowerCase() + ".txt";
        ArrayList<String> infoList = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));

            while (br.ready()) {
                String line = br.readLine();
                // check if line is ok
                if(line.length() > 10 && line.length() < 200) infoList.add(line);
            }

            return infoList.toArray(new String[0]);
        } catch (IOException e) {
            return null;
        }
    }
}
