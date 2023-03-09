package View;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.RadialGradient;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Quiz implements Initializable {
    @FXML private AnchorPane root;
    @FXML private AnchorPane startScreen;
    @FXML private AnchorPane planetScreen;
    @FXML private Text question, startText, result_text;
    @FXML private Button startQuiz;
    @FXML private ImageView confetti;
    @FXML private Label timerLbl;

    private Timer timer;
    private int seconds;

    Map<String, String> userAnswers = new LinkedHashMap<>();

    // Questions
    private ArrayList<Question> questions = new ArrayList<>(List.of(
            new Question("Klicka på Uranus", "Uranus"),
            new Question("Klicka på Mars", "Mars"),
            new Question("Klicka på Saturnus", "Saturnus"),
            new Question("Klicka på Jupiter", "Jupiter"),
            new Question("Klicka på Neptunus", "Neptunus"),
            new Question("Klicka på Venus", "Venus"),
            new Question("Klicka på Jorden", "Jorden"),
            new Question("Klicka på Merkurius", "Merkurius"),
            new Question("Klicka på Solen", "Solen")
    ));

    private AtomicInteger score = new AtomicInteger(0);
    private AtomicInteger questionNumber = new AtomicInteger();
    private AtomicInteger chances = new AtomicInteger(2);

    /**
     * Initialize the quiz
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        planetScreen.addEventFilter(MouseEvent.MOUSE_CLICKED, (event) -> {
            if (event.getTarget() instanceof Button) {
                Button clickEvent = (Button) event.getTarget();
                String userAnswer = clickEvent.getId();

                String answer = "";
                if(questionNumber.get() < questions.size()) {
                    answer = questions.get(questionNumber.get()).answer;
                }

                if(userAnswer.equals(answer)) {
                    score.incrementAndGet();
                    questionNumber.getAndIncrement();
                    chances.set(2);
                    this.highlightPlanet(userAnswer, true);
                    if (questionNumber.get() < questions.size()) {
                        String quizQuestion = questions.get(questionNumber.get()).question;
                        userAnswers.put(quizQuestion, userAnswer);
                        question.setText(quizQuestion);
                    } else {
                        this.stop(score.get());
                    }
                } else {
                    chances.decrementAndGet();
                    this.highlightPlanet(userAnswer, false);
                    if(chances.get() == 0) {
                        this.stop(score.get());
                    }
                }
            }
        });
    }

    /**
     * Start button - start the quiz
     */
    @FXML
    public void startQuiz() {
        startScreen.setVisible(false);
        confetti.setVisible(false);
        userAnswers.clear();
        Collections.shuffle(questions);
        question.setText(questions.get(0).question);
        timerLbl.setTextFill(Color.GREENYELLOW);
        timer = new Timer();
        seconds = 30;
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                seconds -= 1;
                if (seconds > 0)
                    Platform.runLater(() -> timerLbl.setText(String.valueOf(seconds)));
                else
                    Platform.runLater(() ->stop(score.get()));

                if (seconds == 5)
                    Platform.runLater(() -> timerLbl.setTextFill(Color.RED));
                if (seconds == 10)
                    Platform.runLater(() -> timerLbl.setTextFill(Color.ORANGE));
            }
        }, 0, 1000);
    }

    /**
     * Finish the quiz
     */
    private void stop(Integer score) {
        timer.cancel();
        startScreen.setVisible(true);
        if(score == questions.size()) {
            confetti.setVisible(true);
            startText.setText("Grattis, du klarade quizet!");
            result_text.setText(String.format("Din supersnabba tid blev %d sekunder!\n", 30-seconds));
        } else if (seconds > 0) {
            startText.setText("Attans, \n bättre lycka nästa gång!");
            result_text.setText("Du fick " + score + " poäng!\n");
        } else {
            startText.setText("Attans, \n tiden tog slut. \n Bättre lycka nästa gång!");
            result_text.setText("Du fick " + score + " poäng!\n");
        }
        startQuiz.setText("Testa igen");
        question.setText("");

        this.score.set(0);
        questionNumber.set(0);
        chances.set(2);
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
    @FXML
    public void closeQuiz() {
        MainFrame.changeScene("Orbit");
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
