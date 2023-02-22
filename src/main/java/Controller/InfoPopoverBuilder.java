package Controller;

import Model.Planet;
import View.InfoPopover;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import org.controlsfx.control.PopOver;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * Handles the creation of PopOver elements that display planetary information.
 * @author Joel Eriksson Sinclair
 */
public class InfoPopoverBuilder {
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
        planetTab.setContent(createPlanetPane(planet.getName()));

        Tab moonTab = new Tab("Moons");
        moonTab.setContent(createMoonPane(planet.getName()));
        tabPane.getTabs().add(moonTab);

        popOver.setContentNode(tabPane);

        return popOver;
    }

    /**
     * Creates a Pane populated with information about the given planet.
     * @param planetName
     * @return The root node of the created UI element
     * @throws IOException Upon errors when loading the fxml file
     */
    private Node createPlanetPane(String planetName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/InfoPopover.fxml"));
        Parent rootNode = fxmlLoader.load();
        InfoPopover fxmlController = fxmlLoader.getController();

        // Stupid javafx work-around.. FxController
        checkChildrenForIDs(rootNode, fxmlController);

        URL imgURL = getClass().getResource("/Images/planets/" + planetName + ".png");
        Image planetImage = new Image(imgURL.toExternalForm());
        fxmlController.planetImage.setImage(planetImage);
        fxmlController.title.setText(planetName);
        fxmlController.infoText.setText(fetchPlanetInfo(planetName));

        return rootNode;
    }

    /**
     * Creates a Pane populated with information about the moons of the given planet.
     * @param planetName
     * @return The root node of the created UI element
     * @throws IOException Upon errors when loading the fxml file
     */
    private Node createMoonPane(String planetName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/InfoPopover.fxml"));
        Parent rootFxml = fxmlLoader.load();
        InfoPopover fxmlController = fxmlLoader.getController();

        // Stupid javafx work-around.. FxController
        checkChildrenForIDs(rootFxml, fxmlController);

        fxmlController.infoText.setText(fetchMoonInfo(planetName));
        fxmlController.title.setText("Moons");

        return rootFxml;
    }

    /**
     * Searches through a parents children to bind UI-elements to a given controller.
     * @param parent Parent node to find matching UI-elements within.
     * @param fxmlController Controller to be bound.
     */
    private void checkChildrenForIDs(Parent parent, InfoPopover fxmlController) {
        for (Node child : parent.getChildrenUnmodifiable()) {
            if (child instanceof ScrollPane) {
                Node scrollChild = ((ScrollPane) child).getContent();
                if (Objects.equals(scrollChild.getId(), "infoText")) {
                    fxmlController.infoText = (Text) scrollChild;
                }
            }
            if(Objects.equals(child.getId(), "planetImage")) {
                fxmlController.planetImage = (ImageView) child;
            } else if (Objects.equals(child.getId(), "infoText")) {
                fxmlController.infoText = (Text) child;
            } else if (Objects.equals(child.getId(), "title")) {
                fxmlController.title = (Label) child;
            }
        }
    }

    // TODO: Use an API for this?
    private String fetchPlanetInfo(String planetName) {
        String filePath = "src/main/resources/Info/" + planetName.toLowerCase() + ".txt";
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            System.err.println("InfoPopoverBuilder.fetchPlanetInfo() --- File not found: " + filePath);
            return planetName + " is a mystery to us.";
        }
    }

    private String fetchMoonInfo(String planetName) {
        String filePath = "src/main/resources/Info/" + planetName.toLowerCase() + "_moons.txt";
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            System.err.println("File not found: " + filePath);
            return planetName + " has no moons.";
        }
    }
}
