import app.a3_in_1_game.Hangman;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class HangmanTest{
    @org.junit.jupiter.api.Test
    void init() {
        Hangman h = new Hangman();
        h.init();
        assertEquals(h.numWrongWordGuesses,0, "wrong init of numWrongWordGuesses");
        assertEquals(h.lettersGuessed.length, 26, "wrong init of lettersGuessed");
        assertEquals(h.lettersWrong.length, 26, "wrong init of lettersWrong");
    }

    @org.junit.jupiter.api.Test
    void inLettersGuessed() {
        Hangman h = new Hangman();
        char [] test = {'A', 'B', 'C'};
        h.lettersGuessed = test;
        assertEquals(h.inLettersGuessed('c'), true, "value not found");
        assertEquals(h.inLettersGuessed('d'), false, "value not in array");
    }

    @org.junit.jupiter.api.Test
    void getRandomWord() {
        Hangman h = new Hangman();
        h.getRandomWord();
        assertNotEquals(h.word, "", "Failed to change value of word");
    }

    @org.junit.jupiter.api.Test
    void addToLettersGuessed() {
        Hangman h = new Hangman();
        char [] test = new char[2];
        test[0] = 'a';
        h.lettersGuessed = test;
        h.addToLettersGuessed('b');
        assertNotNull(h.lettersGuessed[1], "char not added to list");
        assertEquals(h.lettersGuessed[1], 'B', "char not uppercased");
    }

    @org.junit.jupiter.api.Test
    void addToLettersWrong() {
        Hangman h = new Hangman();
        char [] test = new char[2];
        test[0] = 'a';
        h.lettersWrong = test;
        h.addToLettersWrong('b');
        assertNotNull(h.lettersWrong[1], "char not added to list");
        assertEquals(h.lettersWrong[1], 'B', "char not uppercased");
    }

    @org.junit.jupiter.api.Test
    void getInstancesOfLetter() {
        Hangman h = new Hangman();
        char [] testWord = {'t', 'e', 's', 't'};
        h.word = testWord;
        assertEquals(h.getInstancesOfLetter('t'), 2, "incorrect count of letters in word");
    }

    @org.junit.jupiter.api.Test
    void guessLetter() {
        Hangman h = new Hangman();
        char [] word = {'t', 'e', 's', 't'};
        h.word = word;
        boolean right = h.guessLetter('e');
        assertEquals(h.inLettersGuessed('e'), true, "letter not added to lettersGuessed");
        assertEquals(right, true, "incorrect return for guess");
        right = h.guessLetter('q');
        assertEquals(right, false, "incorrect return for guess");
        assertEquals(h.getNumAppendages(), 1, "failed to penalize wrong guess");
        assertEquals(h.lettersWrong[0], 'Q', "didn't add it to lettersWrong");
    }

    @org.junit.jupiter.api.Test
    void guessWord() {
        Hangman h = new Hangman();
        char [] word = {'t', 'e', 's', 't'};
        assertEquals(h.guessWord("test"), true, "failed to recognize a correct guess");
        assertEquals(h.guessWord("qqqq"), false, "failed to recognize an incorrect guess");
        assertEquals(h.guessWord("qqqqqqqqqq"), false, "failed to handle larger input");
    }

    @org.junit.jupiter.api.Test
    void getNumAppendages() {
        Hangman h = new Hangman();
        h.numWrongWordGuesses = 2;
        char [] word = {'t', 'e', 's', 't'};
        h.lettersWrong = word;
        assertEquals(h.getNumAppendages(), 6, "didn't add up wrong guesses correctly");
    }

    @org.junit.jupiter.api.Test
    void wordIsComplete() {
        Hangman h = new Hangman();
        boolean [] test = {true, true, true};
        h.guessed = test;
        assertEquals(h.wordIsComplete(), true, "word should be complete");
        test[0] = false;
        h.guessed = test;
        assertEquals(h.wordIsComplete(), false, "word should be incomplete");

    }

}