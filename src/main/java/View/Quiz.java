package View;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.util.List;

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
    private AnchorPane planetScreen;
    @FXML
    private Button Merkurius;
    @FXML
    private Button Venus;
    @FXML
    private Button Jorden;
    @FXML
    private Button Mars;
    @FXML
    private Button Jupiter;
    @FXML
    private Button Saturnus;
    @FXML
    private Button Uranus;
    @FXML
    private Button Neptunus;
    @FXML
    private Button Solen;

    // Questions
    private final List<Question> questions = List.of(
            new Question("Klicka på Uranus", "Uranus"),
            new Question("Klicka på Mars", "Mars"),
            new Question("Klicka på Saturn", "Saturn"),
            new Question("Klicka på Jupiter", "Jupiter"),
            new Question("Klicka på Neptunus", "Neptunus"),
            new Question("Klicka på Venus", "Venus"),
            new Question("Klicka på Jorden", "Jorden"),
            new Question("Klicka på Merkurius", "Merkurius"),
            new Question("Klicka på Solen", "Solen")
    );


    public void initialize() {
        /**
         * Start button - start quiz
         */
        startQuiz.setOnAction(e -> {
            startScreen.setVisible(false);
            runTheQuiz();
            question.setText(questions.get(0).question);
        });
    }



    /**
     * Run the quiz
     */
    private void runTheQuiz() {
        final int[] questionNumber = {0};
        planetScreen.addEventFilter(MouseEvent.MOUSE_CLICKED, (event) -> {
            if (event.getTarget() instanceof Button) {
                Button clickEvent = (Button) event.getTarget();
                String userAnswer = clickEvent.getId();
                String answer = questions.get(questionNumber[0]).answer;
                if(userAnswer.equals(answer)) {
                    questionNumber[0]++;
                    if (questionNumber[0] < questions.size()) {
                        question.setText(questions.get(questionNumber[0]).question);
                    } else {
                        question.setText("Grattis! Du klarade quizet!");
                    }
                } else {
                    question.setText("Fel svar! Försök igen!");
                }

            }
        });
    }



    /**
     * Close button - return to orbit scene
     */
    public void closeQuiz() {
        closeQuiz.setOnAction(e -> {
            MainFrame.changeScene("Orbit");
        });
    }


    /**
     * Question class
     */
    private class Question {
        private String question;
        private String answer;

        public Question(String question, String answer) {
            this.question = question;
            this.answer = answer;
        }

        public String getQuestion() {
            return question;
        }
        public String getAnswer() {
            return answer;
        }
    }
}
