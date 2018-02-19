package app.a3_in_1_game;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class Tic_Tac_ToeTest {

    private Tic_Tac_Toe t;


    @Before
    public void setUp() throws Exception {
        t = new app.a3_in_1_game.Tic_Tac_Toe();
    }


    @Test(timeout = 100)
        public void testPlaceTokenX() {
            t.placeToken(0, 0, 0);
            char expected = 'X';
            char actual = t.board[0][0];
            String message = "testPlaceTokenX, test whether an x is placed in the correct place";
            assertEquals(message, expected, actual);
        }


    @Test(timeout = 100)
        public void testPlaceTokenO() {
            t.placeToken(1, 1, 1);
            char expected = 'O';
            char actual = t.board[1][1];
            String message = "testPlaceTokenO, test whether an O is placed in the correct place";
            assertEquals(message, expected, actual);
        }

    @Test(timeout = 100)
        public void testCheckSpaceOpen() {
            boolean expected = true;
            boolean actual = t.checkSpace(0, 0, 0);
            String message = "testCheckSpaceOpen, test if trying to place a token on an open space returns true";
            assertEquals(message, expected, actual);
        }


    @Test(timeout = 100)
        public void testCheckSpaceTaken() {
            boolean expected = false;
            t.placeToken(0, 0, 0);
            boolean actual = t.checkSpace(0, 0, 0);
            String message = "testCheckSpaceTaken, test if trying to place a token on a taken space returns false";
            assertEquals(message, expected, actual);
        }


    @Test(timeout = 100)
        public void testBlockTrue() {
            String expected = "22";
            t.placeToken(0, 0, 0);
            t.placeToken(0, 1, 1);
            String actual = t.checkBlock();
            String message = "testBlockTrue, test if the computer returns the correct location to block the player";
            assertEquals(message, expected, actual);
        }

    @Test(timeout = 100)
        public void testBlockFalse() {
            String expected = null;
            t.placeToken(0, 0, 0);
            t.placeToken(0, 1, 2);
            String actual = t.checkBlock();
            String message = "testBlockFalse, test if the computer returns a null value because no block exists";
            assertEquals(message, expected, actual);
        }

    @Test(timeout = 100)
        public void testWinTrue() {
            String expected = "22";
            t.placeToken(1, 0, 0);
            t.placeToken(1, 1, 1);
            String actual = t.checkWin();
            String message = "testWinTrue, test if the computer returns the correct location to win the game";
            assertEquals(message, expected, actual);
        }

    @Test(timeout = 100)
        public void testWinFalse() {
            String expected = null;
            t.placeToken(1, 0, 0);
            t.placeToken(1, 1, 2);
            String actual = t.checkWin();
            String message = "testWinFalse, test if the computer returns a null value because no win exists";
            assertEquals(message, expected, actual);
        }

    @Test(timeout = 100)
        public void testGameOverFalse() {
            Boolean expected = false;
            t.placeToken(0, 0, 0);
            t.placeToken(1, 0, 1);
            t.placeToken(0, 1, 1);
            t.placeToken(1, 2, 2);
            Boolean actual = t.gameOver();
            String message = "testGameOverFalse, test if the gameOver function returns false when game is ongoing";
            assertEquals(message, expected, actual);
        }

    @Test(timeout = 100)
        public void testGameOverTrue() {
            Boolean expected = true;
            t.placeToken(0, 0, 0);
            t.placeToken(0, 1, 1);
            t.placeToken(0, 2, 2);
            Boolean actual = t.gameOver();
            String message = "testGameOverTrue, test if the gameOver function returns true when someone wins";
            assertEquals(message, expected, actual);
        }

    @Test(timeout = 100)
        public void testGameOverBoardFull() {
            Boolean expected = true;
            t.placeToken(0, 0, 0);
            t.placeToken(1, 0, 1);
            t.placeToken(0, 0, 2);
            t.placeToken(1, 1, 0);
            t.placeToken(0, 1, 1);
            t.placeToken(1, 1, 2);
            t.placeToken(0, 2, 0);
            t.placeToken(1, 2, 1);
            t.placeToken(1, 2, 2);
            Boolean actual = t.gameOver();
            String message = "testGameOverBoardFull, test if the gameOver function returns true when the board is full";
            assertEquals(message, expected, actual);
        }
}