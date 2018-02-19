/**
 * Created by ritzbitz on 2/18/18.
 */

import org.junit.*;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;
import static org.junit.Assert.*;

public class Connect_4Test {

    private static final char CROSS = 'X';
    private static final char CIRCLE = 'O';
    private static final char EMPTY = ' ';


    /* Boards for check3Winner */

    private static final char[][] boardInit = {
            {' ', ' ', ' ',' ', ' ', ' ', ' '},
            {' ', ' ', ' ',' ', ' ', ' ', ' '},
            {' ', ' ', ' ',' ', ' ', ' ', ' '},
            {' ', ' ', ' ',' ', ' ', ' ', ' '},
            {' ', ' ', ' ',' ', ' ', ' ', ' '},
            {' ', ' ', ' ',' ', ' ', ' ', ' '},
    };

    private static final char[][] horizontal3Left = {
            {' ', ' ', ' ',' ', ' ', ' ', ' '},
            {' ', ' ', ' ',' ', ' ', ' ', ' '},
            {' ', ' ', ' ',' ', ' ', ' ', ' '},
            {' ', ' ', ' ',' ', ' ', ' ', ' '},
            {'O', 'O', ' ',' ', ' ', ' ', ' '},
            {'X', 'X', 'X',' ', ' ', ' ', ' '},
    };

    private static final char[][] horizontal3Right = {
            {' ', ' ', ' ',' ', ' ', ' ', ' '},
            {' ', ' ', ' ',' ', ' ', ' ', ' '},
            {' ', ' ', ' ',' ', ' ', ' ', ' '},
            {' ', ' ', ' ',' ', ' ', ' ', ' '},
            {' ', ' ', ' ',' ', ' ', 'O', 'O'},
            {' ', ' ', ' ',' ', 'X', 'X', 'X'},
    };

    private static final char[][] vertical3Right = {
            {' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', 'X'},
            {' ', ' ', ' ', ' ', ' ', ' ', 'X'},
            {' ', ' ', ' ', ' ', ' ', ' ', 'X'},
    };

    private static final char[][] vertical3Left = {
            {' ', ' ', ' ',' ', ' ', ' ', ' '},
            {' ', ' ', ' ',' ', ' ', ' ', ' '},
            {' ', ' ', ' ',' ', ' ', ' ', ' '},
            {'X', ' ', ' ',' ', ' ', ' ', ' '},
            {'X', ' ', ' ',' ', ' ', ' ', ' '},
            {'X', ' ', ' ',' ', ' ', ' ', ' '},
    };

    private static final char[][] diagonal3LeftCorner = {
            {' ', ' ', ' ',' ', ' ', ' ', ' '},
            {' ', ' ', ' ',' ', ' ', ' ', ' '},
            {' ', ' ', ' ',' ', ' ', ' ', ' '},
            {' ', ' ', 'X',' ', ' ', ' ', ' '},
            {' ', 'X', ' ',' ', ' ', ' ', ' '},
            {'X', ' ', ' ',' ', ' ', ' ', ' '},
    };

    private static final char[][] diagonal3RightCorner = {
            {' ', ' ', ' ',' ', ' ', ' ', ' '},
            {' ', ' ', ' ',' ', ' ', ' ', ' '},
            {' ', ' ', ' ',' ', ' ', ' ', ' '},
            {' ', ' ', ' ',' ', ' ', 'X', ' '},
            {' ', ' ', ' ',' ', 'X', ' ', ' '},
            {' ', ' ', ' ','X', ' ', ' ', ' '},
    };

    private static final char[][] inverseDiagonal3LeftCorner = {
            {' ', ' ', ' ',' ', ' ', ' ', ' '},
            {' ', ' ', ' ',' ', ' ', ' ', ' '},
            {'X', ' ', ' ',' ', ' ', ' ', ' '},
            {' ', 'X', ' ',' ', ' ', ' ', ' '},
            {' ', ' ', 'X',' ', ' ', ' ', ' '},
            {' ', ' ', ' ',' ', ' ', ' ', ' '},
    };

    private static final char[][] inverseDiagonal3RightCorner = {
            {' ', ' ', ' ',' ', ' ', ' ', ' '},
            {' ', ' ', ' ',' ', ' ', ' ', ' '},
            {' ', ' ', ' ','X', ' ', ' ', ' '},
            {' ', ' ', ' ',' ', 'X', ' ', ' '},
            {' ', ' ', ' ',' ', ' ', 'X', ' '},
            {' ', ' ', ' ',' ', ' ', ' ', ' '},
    };

    /* Boards for checkWinner */

    private static final char[][] horizontalLeftWin = {
            {' ', ' ', ' ',' ', ' ', ' ', ' '},
            {' ', ' ', ' ',' ', ' ', ' ', ' '},
            {' ', ' ', ' ',' ', ' ', ' ', ' '},
            {' ', ' ', ' ',' ', ' ', ' ', ' '},
            {'O', 'O', ' ',' ', ' ', ' ', ' '},
            {'X', 'X', 'X','X', ' ', ' ', ' '},
    };

    private static final char[][] horizontalRightWin = {
            {' ', ' ', ' ',' ', ' ', ' ', ' '},
            {' ', ' ', ' ',' ', ' ', ' ', ' '},
            {' ', ' ', ' ',' ', ' ', ' ', ' '},
            {' ', ' ', ' ',' ', ' ', ' ', ' '},
            {' ', ' ', ' ',' ', ' ', 'O', 'O'},
            {' ', ' ', ' ','X', 'X', 'X', 'X'},
    };

    private static final char[][] verticalRightWin = {
            {' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', 'X'},
            {' ', ' ', ' ', ' ', ' ', ' ', 'X'},
            {' ', ' ', ' ', ' ', ' ', ' ', 'X'},
            {' ', ' ', ' ', ' ', ' ', ' ', 'X'},
    };

    private static final char[][] verticalLeftWin = {
            {' ', ' ', ' ',' ', ' ', ' ', ' '},
            {' ', ' ', ' ',' ', ' ', ' ', ' '},
            {'X', ' ', ' ',' ', ' ', ' ', ' '},
            {'X', ' ', ' ',' ', ' ', ' ', ' '},
            {'X', ' ', ' ',' ', ' ', ' ', ' '},
            {'X', ' ', ' ',' ', ' ', ' ', ' '},
    };

    private static final char[][] diagonalLeftCornerWin = {
            {' ', ' ', ' ',' ', ' ', ' ', ' '},
            {' ', ' ', ' ',' ', ' ', ' ', ' '},
            {' ', ' ', ' ','X', ' ', ' ', ' '},
            {' ', ' ', 'X',' ', ' ', ' ', ' '},
            {' ', 'X', ' ',' ', ' ', ' ', ' '},
            {'X', ' ', ' ',' ', ' ', ' ', ' '},
    };

    private static final char[][] diagonalRightCornerWin = {
            {' ', ' ', ' ',' ', ' ', ' ', ' '},
            {' ', ' ', ' ',' ', ' ', ' ', ' '},
            {' ', ' ', ' ',' ', ' ', ' ', 'X'},
            {' ', ' ', ' ',' ', ' ', 'X', ' '},
            {' ', ' ', ' ',' ', 'X', ' ', ' '},
            {' ', ' ', ' ','X', ' ', ' ', ' '},
    };

    private static final char[][] inverseDiagonalLeftCornerWin = {
            {' ', ' ', ' ',' ', ' ', ' ', ' '},
            {' ', ' ', ' ',' ', ' ', ' ', ' '},
            {'X', ' ', ' ',' ', ' ', ' ', ' '},
            {' ', 'X', ' ',' ', ' ', ' ', ' '},
            {' ', ' ', 'X',' ', ' ', ' ', ' '},
            {' ', ' ', ' ','X', ' ', ' ', ' '},
    };

    private static final char[][] inverseDiagonalRightCornerWin = {
            {' ', ' ', ' ',' ', ' ', ' ', ' '},
            {' ', ' ', ' ',' ', ' ', ' ', ' '},
            {' ', ' ', ' ','X', ' ', ' ', ' '},
            {' ', ' ', ' ',' ', 'X', ' ', ' '},
            {' ', ' ', ' ',' ', ' ', 'X', ' '},
            {' ', ' ', ' ',' ', ' ', ' ', 'X'},
    };

    /* Humza test */

    private static final char[][] humzaTest = {
            {' ', ' ', ' ','X', ' ', ' ', ' '},
            {' ', ' ', ' ','O', ' ', ' ', ' '},
            {' ', ' ', ' ','O', ' ', ' ', ' '},
            {' ', ' ', ' ','O', ' ', ' ', ' '},
            {' ', ' ', ' ','X', 'X', 'X', ' '},
            {'X', 'X', 'X','O', 'O', 'O', ' '},
    };


    @Test
    public void humzaTest() {
        Connect_4 connect_4 = new Connect_4(humzaTest);
        int expected = 6;
        int value = connect_4.check3Winner(CROSS);

        assertEquals(expected, value);
    }

    /* Test cases for check3Winner() */

    @Test
    public void horizontalCheckLeft() {
        Connect_4 connect_4 = new Connect_4(horizontal3Left);
        int expected = 3;
        int value = connect_4.check3Winner(CROSS);

        assertEquals(expected, value);
    }

    @Test
    public void horizontalCheckRight() {
        Connect_4 connect_4 = new Connect_4(horizontal3Right);
        int expected = 3;
        int value = connect_4.check3Winner(CROSS);

        assertEquals(expected, value);
    }

    @Test
    public void verticalCheckRight() {
        Connect_4 connect_4 = new Connect_4(vertical3Right);
        int expected = 6;
        int value = connect_4.check3Winner(CROSS);

        assertEquals(expected, value);
    }

    @Test
    public void verticalCheckLeft() {
        Connect_4 connect_4 = new Connect_4(vertical3Left);
        int expected = 0;
        int value = connect_4.check3Winner(CROSS);

        assertEquals(expected, value);
    }

    public void diagonal3LeftCorner() {
        Connect_4 connect_4 = new Connect_4(diagonal3LeftCorner);
        int expected = 4;
        int value = connect_4.check3Winner(CROSS);

        assertEquals(expected, value);
    }

    @Test
    public void diagonal3RightCorner() {
        Connect_4 connect_4 = new Connect_4(diagonal3RightCorner);
        int expected = 6;
        int value = connect_4.check3Winner(CROSS);

        assertEquals(expected, value);
    }

    public void inverseDiagonal3LeftCorner() {
        Connect_4 connect_4 = new Connect_4(inverseDiagonal3LeftCorner);
        int expected = 3;
        int value = connect_4.check3Winner(CROSS);

        assertEquals(expected, value);
    }

    @Test
    public void inverseDiagonal3RightCorner() {
        Connect_4 connect_4 = new Connect_4(inverseDiagonal3RightCorner);
        int expected = 6;
        int value = connect_4.check3Winner(CROSS);

        assertEquals(expected, value);
    }

    /* Test cases for checkWinner() */

    @Test
    public void horizontalLeftWin() {
        Connect_4 connect_4 = new Connect_4(horizontalLeftWin);
        int expected = 3;
        boolean value = connect_4.checkWinner(CROSS);

        assertTrue(value);
    }

    @Test
    public void horizontalRightWin() {
        Connect_4 connect_4 = new Connect_4(horizontalRightWin);
        int expected = 3;
        boolean value = connect_4.checkWinner(CROSS);

        assertTrue(value);
    }

    @Test
    public void verticalRightWin() {
        Connect_4 connect_4 = new Connect_4(verticalRightWin);
        int expected = 6;
        boolean value = connect_4.checkWinner(CROSS);

        assertTrue(value);
    }

    @Test
    public void verticalLeftWin() {
        Connect_4 connect_4 = new Connect_4(verticalLeftWin);
        int expected = 0;
        boolean value = connect_4.checkWinner(CROSS);

        assertTrue(value);
    }

    public void diagonalLeftCornerWin() {
        Connect_4 connect_4 = new Connect_4(diagonalLeftCornerWin);
        int expected = 4;
        boolean value = connect_4.checkWinner(CROSS);

        assertTrue(value);
    }

    @Test
    public void diagonalRightCornerWin() {
        Connect_4 connect_4 = new Connect_4(diagonalRightCornerWin);
        int expected = 6;
        boolean value = connect_4.checkWinner(CROSS);

        assertTrue(value);
    }

    public void inverseDiagonalLeftCornerWin() {
        Connect_4 connect_4 = new Connect_4(inverseDiagonalLeftCornerWin);
        int expected = 3;
        boolean value = connect_4.checkWinner(CROSS);

        assertTrue(value);
    }

    @Test
    public void inverseDiagonalRightCornerWin() {
        Connect_4 connect_4 = new Connect_4(inverseDiagonalRightCornerWin);
        int expected = 6;
        boolean value = connect_4.checkWinner(CROSS);

        assertTrue(value);
    }

}
