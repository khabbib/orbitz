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
    private AnchorPane root;
    @FXML
    private Button startQuiz;
    @FXML
    private AnchorPane startScreen;
    @FXML
    private Button closeQuiz;
    @FXML
    private Text question, startText;


    @FXML
    private AnchorPane planetScreen;
    // Planets highlighters

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
            this.start();
        });
    }

    /**
     * Run the quiz
     */
    private void start() {
        question.setText(questions.get(0).question);
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
                    }
                } else {
                    chances.decrementAndGet();
                    this.highlightPlanet(userAnswer, false);
                    if(chances.get() == 0) {
                        this.stop();
                    }
                }

            }
        });
    }

    /**
     * Finish the quiz
     */
    private void stop() {
        startScreen.setVisible(true);
        startText.setText("Du klarade inte quizet!");
        startQuiz.setText("Restart");
    }

    /**
     * Highlight the planet
     * @param planet
     * @param answer
     */
    private void highlightPlanet(String planet, Boolean answer) {
        Circle highLighter = (Circle) root.lookup("#" + planet + "_highlighter");
        Text label = (Text) root.lookup("#" + planet + "_label");

        RadialGradient gradient;
        if (answer) {
            gradient = new RadialGradient(0, 0, 0.5, 0.5, 0.5, true, null, new javafx.scene.paint.Stop(0, Color.TRANSPARENT), new javafx.scene.paint.Stop(1, Color.GREENYELLOW));
        } else {
            label.setVisible(true);
            gradient = new RadialGradient(0, 0, 0.5, 0.5, 0.5, true, null, new javafx.scene.paint.Stop(0, Color.TRANSPARENT), new javafx.scene.paint.Stop(1, Color.RED));
        }

        highLighter.setFill(gradient);
        highLighter.setOpacity(1);

        this.removeHighLighter(highLighter);
        this.removeLabel(label);
    }

    private void removeHighLighter(Circle highLighter) {
        Timeline highlightTimer = new Timeline(new KeyFrame(Duration.seconds(1)));
        highlightTimer.setOnFinished(e -> {
            highLighter.setFill(javafx.scene.paint.Color.TRANSPARENT);
        });
        highlightTimer.play();
    }

    private void removeLabel(Text label) {
        Timeline labelTimer = new Timeline(new KeyFrame(Duration.seconds(1)));
        labelTimer.setOnFinished(e -> {
            label.setVisible(false);
        });
        labelTimer.play();
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
