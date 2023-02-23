package View;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class Quiz {
    @FXML
    private Button startQuiz;
    @FXML
    private AnchorPane startScreen;
    @FXML
    private Button closeQuiz;
    @FXML
    private Text question;


    // Planets
    @FXML
    private Button neptune;
    @FXML
    private Button uranus;
    @FXML
    private Button saturn;
    @FXML
    private Button jupiter;
    @FXML
    private Button mars;
    @FXML
    private Button earth;
    @FXML
    private Button venus;
    @FXML
    private Button mercury;
    @FXML
    private Button sun;

    public void initialize() {
        question.setText("Klicka på Uranus.");


        /**
         * Close button - return to orbit scene
         */
        closeQuiz.setOnAction(e -> {
            MainFrame.changeScene("Orbit");
        });
    }
}
