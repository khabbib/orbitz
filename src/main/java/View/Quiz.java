package View;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Quiz {
    @FXML
    private AnchorPane rootAnchorPane;
    @FXML
    private AnchorPane planetspane;
    @FXML
    private AnchorPane status_box;
    @FXML
    private Text status;
    @FXML
    private TextArea info_textarea;
    @FXML
    private Button close_status;
    @FXML
    private Rectangle shadow_behind_status;

    // Buttons
    @FXML
    private Button prev;
    @FXML
    private HBox prev_box;
    @FXML
    private Button next;
    @FXML
    private HBox next_box;
    @FXML
    private Button close;
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

    private class Question {
        private String question;
        private String answer;
        private String description;

        public Question(String question, String answer, String description) {
            this.question = question;
            this.answer = answer;
            this.description = description;
        }

        public String getQuestion() {
            return question;
        }

        public String getAnswer() {
            return answer;
        }

        public String getDescription() {
            return description;
        }
    }

    private final List<Question> questions = List.of(
            new Question("Which planet is known as the (Red Planet)?", "Mars", "Mars is known as the Red Planet because of its reddish appearance, which is caused by iron oxide (rust) on its surface."),
            new Question("Which planet is closest to the Sun?", "Mercury", "Mercury is the closest planet to the Sun, with an average distance of 36 million miles (58 million kilometers)."),
            new Question("Which planet is known for having a set of rings around it?", "Saturn", "Saturn is known for its extensive ring system, which is made up of millions of individual ice particles."),
            new Question("Which planet is known for having the largest moon in the solar system?", "Jupiter", "Jupiter's moon Ganymede is the largest moon in the solar system, and it is even larger than the planet Mercury."),
            new Question("Which planet is known for being the largest planet in the solar system?", "Jupiter", "Jupiter is the largest planet in the solar system, with a diameter of 86,881 miles (139,822 kilometers)."),
            new Question("Which planet is known for having a tilted axis, causing extreme seasons?", "Uranus", "Uranus has a tilted axis that is almost parallel to its orbit, which causes extreme seasonal changes on the planet."),
            new Question("Which planet is known for having a thick atmosphere of carbon dioxide?", "Venus", "Venus has a thick atmosphere of carbon dioxide that traps heat, making it the hottest planet in the solar system."),
            new Question("Which planet is known for having the most volcanoes in the solar system?", "Mars", "Mars has the largest volcano in the solar system, Olympus Mons, and it is also known for having a lot of other volcanoes."),
            new Question("Which planet is known for having the largest canyon in the solar system?", "Mars", "Mars has the largest canyon in the solar system, Valles Marineris, which is over 4,000 kilometers long."),
            new Question("Which planet is known for having the longest day in the solar system?", "Venus", "Venus has a very slow rotation, and it takes 243 Earth days to complete one rotation, making it the planet with the longest day in the solar system.")
    );

    public void initialize() {
        AtomicInteger score = new AtomicInteger();
        final int[] questionNumber = {0};
        // Set question
        question.setText(questions.get(questionNumber[0]).question);

        // Set status box invisible after x amount of seconds
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
            if(status_box.isVisible()) {
                closeStatus(null);
            }
        }));

        // Listen for answer from the user
        planetspane.addEventFilter(MouseEvent.MOUSE_CLICKED, (event) -> {
            if (event.getTarget() instanceof Button) {
                Button clickedButton = (Button) event.getTarget();
                String answerId = clickedButton.getId();
                String answer = questions.get(questionNumber[0]).answer.toLowerCase();
                if(answerId.equals(answer)) {
                    score.getAndIncrement();
                    System.out.println(answerId + " is correct " + score);
                    status.setText("Correct!");
                    info_textarea.setText(questions.get(questionNumber[0]).description);
                    status_box.setVisible(true);
                    questionNumber[0]++;
                    question.setText(questions.get(questionNumber[0]).question);
                } else {
                    System.out.println(answerId + " is wrong " + score);
                    status.setText("Wrong!");
                    info_textarea.setText("Try again!");
                    status_box.setVisible(true);
                    // Wrong answer
                }
                // Set
                timeline.play();

            }
        });


        next.setOnAction(e -> {
            if(questionNumber[0] < questions.size() - 1) {
                next_box.setOpacity(1);
                questionNumber[0]++;
                question.setText(questions.get(questionNumber[0]).question);
                if(status_box.isVisible()){
                    closeStatus(null);
                }
                if(questionNumber[0] == questions.size() - 1) {
                    next_box.setOpacity(0.5);
                }
            }
        });

        prev.setOnAction(e -> {
            if(questionNumber[0] > 0) {
                next_box.setOpacity(1);
                questionNumber[0]--;
                question.setText(questions.get(questionNumber[0]).question);
                if(status_box.isVisible()){
                    closeStatus(null);
                }
                if(questionNumber[0] == 0) {
                    prev_box.setOpacity(0.5);
                }
            }
        });

        close.setOnAction(e -> {
            MainFrame.changeScene("Orbit");
        });

        close_status.setOnAction(e -> {
            closeStatus(null);
        });
    }

    public void closeStatus(MouseEvent mouseEvent) {
        status_box.setVisible(false);
    }

}
