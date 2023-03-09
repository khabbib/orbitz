package Controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Stores the questions and answers for the quiz, and keeps track of the score and wrong guesses.
 * @author Joel Eriksson Sinclair
 */
public class QuizController {
    private int score;
    private int wrongGuesses;
    private final int maxWrongGuesses = 3;

    public enum GuessResult {
        CORRECT,
        INCORRECT,
        GUESS_FAILED
    }

    int activeQuestionIdx = 0;
    private final QuizQuestion[] questions;

    /**
     * Creates a new QuizController with a shuffled array of questions.
     */
    public QuizController() {
        this.questions = createQuestions();
    }

    /**
     * Creates a shuffled array of questions.
     * @return A shuffled array of questions.
     */
    private QuizQuestion[] createQuestions() {
        QuizQuestion[] questions = new QuizQuestion[] {
                new QuizQuestion("Klicka på Uranus", "Uranus"),
                new QuizQuestion("Klicka på Mars", "Mars"),
                new QuizQuestion("Klicka på Saturn", "Saturn"),
                new QuizQuestion("Klicka på Jupiter", "Jupiter"),
                new QuizQuestion("Klicka på Neptunus", "Neptunus"),
                new QuizQuestion("Klicka på Venus", "Venus"),
                new QuizQuestion("Klicka på Jorden", "Jorden"),
                new QuizQuestion("Klicka på Merkurius", "Merkurius"),
                new QuizQuestion("Klicka på Solen", "Solen")
        };

        List<QuizQuestion> list = Arrays.asList(questions);
        Collections.shuffle(list);
        return list.toArray(new QuizQuestion[0]);
    }

    /**
     * Resets the quiz to its initial state.
     * @return The QuizController instance.
     */
    public QuizController reset() {
        this.score = 0;
        this.wrongGuesses = 0;
        this.activeQuestionIdx = 0;
        return this;
    }

    /**
     * Checks if the given guess is correct, and updates the score and active question accordingly.
     * @param guess A guess for the active question.
     * @return Result of the attempted guess.
     */
    public GuessResult makeGuess(String guess) {
        if (this.wrongGuesses >= this.maxWrongGuesses) {
            return GuessResult.GUESS_FAILED;
        }
        if(guess == null) {
            return GuessResult.GUESS_FAILED;
        }

        if (this.questions[this.activeQuestionIdx].Answer.equals(guess)) {
            this.score++;
            this.activeQuestionIdx++;
            return GuessResult.CORRECT;
        } else {
            this.wrongGuesses++;
            return GuessResult.INCORRECT;
        }
    }

    /**
     * Get the currently active question.
     * @return The currently active question.
     */
    public String getActiveQuestion() {
        return this.questions[this.activeQuestionIdx].Question;
    }

    /**
     * Get the correct answer for the currently active question.
     * @return The correct answer for the currently active question.
     */
    public String getCorrectAnswer() {
        return this.questions[this.activeQuestionIdx].Answer;
    }

    public int getScore() {
        return this.score;
    }

    public int getWrongGuesses() {
        return this.wrongGuesses;
    }

    public int getMaxWrongGuesses() {
        return this.maxWrongGuesses;
    }

    private static class QuizQuestion {
        public final String Question;
        public final String Answer;

        public QuizQuestion(String question, String answer) {
            this.Question = question;
            this.Answer = answer;
        }
    }
}
