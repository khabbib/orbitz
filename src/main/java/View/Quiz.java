package View;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Quiz {
    @FXML
    private Button closeQuiz;


    public void initialize() {





        /**
         * Close button - return to orbit scene
         */
        closeQuiz.setOnAction(e -> {
            MainFrame.changeScene("Orbit");
        });
    }
}
