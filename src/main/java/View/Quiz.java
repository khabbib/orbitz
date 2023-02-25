package View;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.RadialGradient;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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
    private Circle Merkurius_highlighter;
    @FXML
    private Button Venus;
    @FXML
    private Circle Venus_highlighter;
    @FXML
    private Button Jorden;
    @FXML
    private Circle Jorden_highlighter;
    @FXML
    private Button Mars;
    @FXML
    private Circle Mars_highlighter;
    @FXML
    private Button Jupiter;
    @FXML
    private Circle Jupiter_highlighter;
    @FXML
    private Button Saturnus;
    @FXML
    private Circle Saturn_highlighter;
    @FXML
    private Button Uranus;
    @FXML
    private Circle Uranus_highlighter;
    @FXML
    private Button Neptunus;
    @FXML
    private Circle Neptunus_highlighter;
    @FXML
    private Button Solen;
    @FXML
    private Circle Solen_highlighter;

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

    /**
     * Initialize the quiz
     */
    public void initialize() {
        this.startQuiz();
        this.closeQuiz();
    }

    /**
     * Start button - start the quiz
     */
    public void startQuiz() {
        startQuiz.setOnAction(e -> {
            startScreen.setVisible(false);
            question.setText(questions.get(0).question);
            runTheQuiz();
        });
    }

    /**
     * Run the quiz
     */
    private void runTheQuiz() {
        final int[] questionNumber = {0};
        AtomicInteger chances = new AtomicInteger(2);
        planetScreen.addEventFilter(MouseEvent.MOUSE_CLICKED, (event) -> {
            if (event.getTarget() instanceof Button) {
                Button clickEvent = (Button) event.getTarget();
                String userAnswer = clickEvent.getId();
                String answer = "";
                if(questionNumber[0] < questions.size()) {
                    answer = questions.get(questionNumber[0]).answer;
                }

                if(userAnswer.equals(answer)) {
                    questionNumber[0]++;
                    chances.set(2);
                    this.highlightPlanet(userAnswer, true);
                    if (questionNumber[0] < questions.size()) {
                        question.setText(questions.get(questionNumber[0]).question);
                    } else {
                        // TODO: statusMessage.setText("Grattis! Du klarade quizet!");
                    }
                } else {
                    chances.decrementAndGet();
                    this.highlightPlanet(userAnswer, false);
                    if(chances.get() == 0) {
                        // TODO: stop the game
                    }else {
                        // TODO: statusMessage.setText("Fel svar! Försök igen!");
                    }
                }

            }
        });
    }

    /**
     * Highlight the planet
     * @param planet
     * @param answer
     */
    private void highlightPlanet(String planet, Boolean answer) {
        System.out.println("Planet: " + planet + " - Answer: " + answer);
        switch (planet) {
            case "Merkurius":
                this.setHighLighterColor(Merkurius_highlighter, answer);
                break;
            case "Venus":
                this.setHighLighterColor(Venus_highlighter, answer);
                break;
            case "Jorden":
                this.setHighLighterColor(Jorden_highlighter, answer);
                break;
            case "Mars":
                this.setHighLighterColor(Mars_highlighter, answer);
                break;
            case "Jupiter":
                this.setHighLighterColor(Jupiter_highlighter, answer);
                break;
            case "Saturn":
                this.setHighLighterColor(Saturn_highlighter, answer);
                break;
            case "Uranus":
                this.setHighLighterColor(Uranus_highlighter, answer);
                break;
            case "Neptunus":
                this.setHighLighterColor(Neptunus_highlighter, answer);
                break;
            case "Solen":
                this.setHighLighterColor(Solen_highlighter, answer);
                break;
            default:
                System.out.println("Not valid planet name.");
        }

    }

    private void setHighLighterColor(Circle highLighter, Boolean answer) {
        if (answer) {
            RadialGradient gradient = new RadialGradient(0, 0, 0.5, 0.5, 0.5, true, null, new javafx.scene.paint.Stop(0, Color.TRANSPARENT), new javafx.scene.paint.Stop(1, Color.GREENYELLOW));
            highLighter.setFill(gradient);
        } else {
            RadialGradient gradient = new RadialGradient(0, 0, 0.5, 0.5, 0.5, true, null, new javafx.scene.paint.Stop(0, Color.TRANSPARENT), new javafx.scene.paint.Stop(1, Color.RED));
            highLighter.setFill(gradient);
        }
        highLighter.setOpacity(1);
        this.removeHighLighter(highLighter);
    }

    private void removeHighLighter(Circle highLighter) {
        Timeline highlightTimer = new Timeline(new KeyFrame(Duration.seconds(1)));
        highlightTimer.setOnFinished(e -> {
            highLighter.setFill(javafx.scene.paint.Color.TRANSPARENT);
        });

        highlightTimer.play();
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
