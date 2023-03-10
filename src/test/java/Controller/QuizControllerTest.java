package Controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuizControllerTest {

    private QuizController quizController;

    @BeforeEach
    void setup() {
        quizController = new QuizController();
    }

    @Test
    void reset_checkSame() {
        QuizController resetController = quizController.reset();
        assertSame(quizController, resetController);
    }

    @Test
    void reset_getScore_afterCorrect() {
        quizController.makeGuess(quizController.getCorrectAnswer());
        quizController.reset();
        assertEquals(0, quizController.getScore());
    }

    @Test
    void reset_getWrongGuesses_afterCorrect() {
        quizController.makeGuess(quizController.getCorrectAnswer());
        quizController.reset();
        assertEquals(0, quizController.getWrongGuesses());
    }

    @Test
    void reset_getScore_afterIncorrect() {
        quizController.makeGuess("incorrect");
        quizController.reset();
        assertEquals(0, quizController.getScore());
    }

    @Test
    void reset_getWrongGuesses_afterIncorrect() {
        quizController.makeGuess("incorrect");
        quizController.reset();
        assertEquals(0, quizController.getWrongGuesses());
    }

    @Test
    void given_getCorrectAnswer_makeGuess() {
        Controller.QuizController.GuessResult result = quizController.makeGuess(quizController.getCorrectAnswer());
        assertEquals(QuizController.GuessResult.CORRECT, result);
    }

    @Test
    void givenIncorrect_makeGuess() {
        Controller.QuizController.GuessResult result = quizController.makeGuess("incorrect");
        assertEquals(QuizController.GuessResult.INCORRECT, result);
    }

    @Test
    void givenNull_makeGuess() {
        Controller.QuizController.GuessResult result = quizController.makeGuess(null);
        assertEquals(QuizController.GuessResult.GUESS_FAILED, result);
    }

    @Test
    void givenEmptyString_makeGuess() {
        Controller.QuizController.GuessResult result = quizController.makeGuess("");
        assertEquals(QuizController.GuessResult.INCORRECT, result);
    }

    @Test
    void makeGuess_afterMaxIncorrectGuesses() {
        for (int i = 0; i < quizController.getMaxWrongGuesses(); i++) {
            quizController.makeGuess("incorrect");
        }
        Controller.QuizController.GuessResult result = quizController.makeGuess(quizController.getCorrectAnswer());
        assertEquals(QuizController.GuessResult.GUESS_FAILED, result);
    }

    @Test
    void getActiveQuestion_notNull() {
        String question = quizController.getActiveQuestion();
        assertNotNull(question);
    }

    @Test
    void getActiveQuestion_notEmpty() {
        String question = quizController.getActiveQuestion();
        assertFalse(question.isEmpty());
    }

    @Test
    void getCorrectAnswer_notNull() {
        String answer = quizController.getCorrectAnswer();
        assertNotNull(answer);
    }

    @Test
    void getCorrectAnswer_notEmpty() {
        String answer = quizController.getCorrectAnswer();
        assertFalse(answer.isEmpty());
    }

    @Test
    void getScore_atStart() {
        int score = quizController.getScore();
        assertEquals(0, score);
    }

    @Test
    void getScore_afterCorrectGuess() {
        quizController.makeGuess(quizController.getCorrectAnswer());
        int score = quizController.getScore();
        assertEquals(1, score);
    }

    @Test
    void getScore_afterIncorrectGuess() {
        quizController.makeGuess("invalid");
        int score = quizController.getScore();
        assertEquals(0, score);
    }

    @Test
    void getWrongGuesses_atStart() {
        int wrongGuesses = quizController.getWrongGuesses();
        assertEquals(0, wrongGuesses);
    }

    @Test
    void getWrongGuesses_afterCorrectGuess() {
        quizController.makeGuess(quizController.getCorrectAnswer());
        int wrongGuesses = quizController.getWrongGuesses();
        assertEquals(0, wrongGuesses);
    }

    @Test
    void getWrongGuesses_afterIncorrectGuess() {
        quizController.makeGuess("invalid");
        int wrongGuesses = quizController.getWrongGuesses();
        assertEquals(1, wrongGuesses);
    }

    @Test
    void getWrongGuesses_afterMaxIncorrectGuesses() {
        for (int i = 0; i < quizController.getMaxWrongGuesses(); i++) {
            quizController.makeGuess("invalid");
        }
        int wrongGuesses = quizController.getWrongGuesses();
        assertEquals(quizController.getMaxWrongGuesses(), wrongGuesses);
    }

    @Test
    void getWrongGuesses_afterMaxIncorrectGuessesPlusOne() {
        for (int i = 0; i < quizController.getMaxWrongGuesses() + 1; i++) {
            quizController.makeGuess("invalid");
        }
        int wrongGuesses = quizController.getWrongGuesses();
        assertEquals(quizController.getMaxWrongGuesses(), wrongGuesses);
    }

    @Test
    void getMaxWrongGuesses() {
    }
}