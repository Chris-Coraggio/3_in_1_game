/**
 * Created by ritzbitz on 2/9/18.
 */

package app.a3_in_1_game;

import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class Connect_4 {

    //instance variables
    char[][] gameBoard;
    int count;
    HashMap<String, Integer> moves;

    //variable constants
    protected final char CROSS = 'X';
    protected final char CIRCLE = 'O';
    private final char EMPTY = ' ';

    protected final int NUM_ROWS = 6;
    protected final int NUM_COLS = 7;

    protected int lastRow = -1;
    protected int lastCol = -1;
    /**
     * creates gameBoard
     */
    public Connect_4() {
        count = 0;
        gameBoard = new char[6][7];
        moves = new HashMap<String, Integer>();
        System.out.println("gameBoard.length: " + gameBoard.length);
        System.out.println("gameBoard[0].length: " + gameBoard[0].length);
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[0].length; j++) {
                gameBoard[i][j] = EMPTY;
            }
        }
    }

    public Connect_4(char[][] gameBoard) {
        this();
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[0].length; j++) {
                this.gameBoard[i][j] = gameBoard[i][j];
            }
        }
    }

    public char[][] getGameBoard() {
        char[][] arr = new char[6][7];

        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[0].length; j++) {
                arr[i][j] = gameBoard[i][j];
            }
        }
        return arr;
    }

    /**
     * Method checks the gameBoard to see if anyone won
     * @return
     */
    public boolean checkWinner(char currentPlayer) {
        //checks vertical
        for (int j = 0; j < gameBoard[0].length; j++) {
            for (int i = 0; i < 3; i++) {
                if (gameBoard[i][j]== currentPlayer && gameBoard[i + 1][j] == currentPlayer
                        && gameBoard[i + 2][j] == currentPlayer && gameBoard[i + 3][j] == currentPlayer) {
                    System.out.println(currentPlayer + " has won!");
                    return true;
                }
            }
        }

        //checks horizontal starting at left corner
        for (int i = gameBoard.length - 1; i >= 0; i--) {
            for (int j = 0; j < gameBoard[0].length - 3; j++) {
                if (gameBoard[i][j] == currentPlayer && gameBoard[i][j + 1] == currentPlayer
                        && gameBoard[i][j + 2] == currentPlayer && gameBoard[i][j + 3] == currentPlayer) {
                    System.out.println(currentPlayer + " has won!");
                    return true;
                }
            }
        }

        //checks diagonally
        for (int i = gameBoard.length - 1; i > 2; i--) {
            for (int j = 0; j < gameBoard[0].length - 3; j++) {
                if (gameBoard[i][j] == currentPlayer && gameBoard[i - 1][j + 1] == currentPlayer
                        && gameBoard[i - 2][j + 2] == currentPlayer && gameBoard[i - 3][j + 3] == currentPlayer) {
                    System.out.println(currentPlayer + " has won!");
                    return true;
                }
            }
        }

        //checks diagonally inversely
        for (int i = gameBoard.length - 1; i > 2; i--) {
            for (int j = gameBoard[0].length - 1; j > 3; j--) {
                if (gameBoard[i][j] == currentPlayer && gameBoard[i - 1][j -1] == currentPlayer
                        && gameBoard[i - 2][j - 2] == currentPlayer && gameBoard[i - 3][j - 3] == currentPlayer) {
                    System.out.println(currentPlayer + " has won!");
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Method checks to see if there is someone that has 3 in a row
     * @param currentPlayer
     * @return
     */
    public int check3Winner(char currentPlayer) {
        //checks vertical
        for (int j = 0; j < gameBoard[0].length; j++) {
            for (int i = gameBoard.length - 1; i > 2; i--) {
                if (gameBoard[i][j]== currentPlayer && gameBoard[i - 1][j] == currentPlayer
                        && gameBoard[i - 2][j] == currentPlayer && gameBoard[i - 3][j] == EMPTY) {
                    System.out.println("checking vert");
                    return j;
                }
            }
        }

        //checks diagonally from bottom to top
        for (int i = gameBoard.length - 1; i > 2; i--) {
            for (int j = 0; j < 4; j++) {
                if (gameBoard[i][j] == currentPlayer && gameBoard[i - 1][j + 1] == currentPlayer
                        && gameBoard[i - 2][j + 2] == currentPlayer && gameBoard[i - 3][j + 3] == EMPTY) {
                    return j + 3;
                }
            }
        }

        //checks diagonally from top to bottom
        for (int i = 0; i < 3; i++) {
            for (int j = gameBoard[0].length - 1; j > 2; j--) {
                if (gameBoard[i][j] == currentPlayer && gameBoard[i + 1][j - 1] == currentPlayer
                        && gameBoard[i + 2][j - 2] == currentPlayer && gameBoard[i + 3][j - 3] == EMPTY) {
                    return j - 3;
                }
            }
        }

        //check diagonally inversely bottom right to top left
        for (int i = gameBoard.length - 1; i > 2; i--) {
            for (int j = gameBoard[0].length - 1; j > 2; j--) {
                //System.out.printf("i: %d j: %d\n", i, j);
                if (gameBoard[i][j] == currentPlayer && gameBoard[i - 1][j - 1] == currentPlayer
                        && gameBoard[i - 2][j - 2] == currentPlayer && gameBoard[i - 3][j - 3] == EMPTY) {
                    return j - 3;
                }
            }
        }

        //check diagonally inversely top left to bottom right
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.printf("i: %d j: %d\n", i, j);
                if (gameBoard[i][j] == currentPlayer && gameBoard[i + 1][j + 1] == currentPlayer
                        && gameBoard[i + 2][j + 2] == currentPlayer && gameBoard[i + 3][j + 3] == EMPTY) {
                    return j + 3;
                }
            }
        }

        //check horizontally row - works
        for (int i = gameBoard.length - 1; i >= 0; i--) {
            for (int j = 0; j < gameBoard[0].length - 3; j++) {
                if (gameBoard[i][j] == currentPlayer && gameBoard[i][j + 1] == currentPlayer
                        && gameBoard[i][j + 2] == currentPlayer && gameBoard[i][j + 3] == EMPTY) {
                    System.out.println("horizontal");
                    return j + 3;
                }
            }

            if (gameBoard[i][gameBoard[0].length - 1] == currentPlayer && gameBoard[i][gameBoard[0].length - 2] == currentPlayer
                    && gameBoard[i][gameBoard[0].length - 3] == currentPlayer && gameBoard[i][gameBoard[0].length - 4] == EMPTY)
                return gameBoard[0].length - 4;
        }

        return -1;
    }

    /**
     * adds the players moves onto the gameboard
     * @return
     */
    public boolean playerMove(int location, char player) {
        //remember to subtract 1 from location when adding
        if (player != CROSS && player != CIRCLE)
            return false;

        if (location < 0 || location > 6)
            return false;

        for (int i = gameBoard.length - 1; i >= 0; i--) {
            if (gameBoard[i][location] == EMPTY) {
                gameBoard[i][location] = player;
                lastCol = location;
                lastRow = i;
                return true;
            }
        }
        return false;
    }

    public boolean randomMove(char currentPlayer) {
        Random rand = new Random();
        boolean worked = false;

        if (anyMovesPossible()) {
            do {
                int n = rand.nextInt(14);
                System.out.println("random number: " + n + "\n");
                worked = playerMove(n, currentPlayer);
            } while (!worked);
        }

        return false;
    }

    /**
     *
     */
    public boolean AImove(char currentPlayer) {
        System.out.println("AI playing");
        int possible3Win = check3Winner(CROSS);

        if (possible3Win != -1) {
            playerMove(possible3Win, currentPlayer);
            System.out.println("inside if: "  + possible3Win);
            return true;
        }

        if (randomMove(currentPlayer))
            return true;

        //playerMove(0, currentPlayer);



        return false;
    }

    /**
     * this is going to be the hardest of them all because I have no idea what I'm doing.
     * @param
     * @return true if the board is complete.
     */
    public boolean undo() {
        gameBoard = new char[6][7];
        char player = CROSS;

        for (int i = 0; i < gameBoard.length; i++) {        //for loop initializes the array with EMPTY char's
            for (int j = 0; j < gameBoard[0].length; j++) {
                gameBoard[i][j] = EMPTY;
            }
        }

        for (int i = 0; i < count - 1; i++) {           //goes through the number of moves that have been made.
            resetUndoneBoard(gameBoard, moves.get("" + i), player);
            if (player == CROSS)
                player = CIRCLE;
            else
                player = CROSS;
        }
//
//        for (int i = 0; i < gameBoard.length; i++) {
//            for (int j = 0; j < gameBoard[0].length; j++) {
//                gameBoard[i][j] = undoGameBoard[i][j];
//            }
//        }
        count--;
        return true;
    }

    public boolean resetUndoneBoard(char[][] undoGameBoard, int location, char currentPlayer) {
        for (int i = undoGameBoard.length - 1; i >= 0; i--) {
            if (undoGameBoard[i][location] == EMPTY) {
                undoGameBoard[i][location] = currentPlayer;
                return true;
            }
        }
        return false;
    }

    /**
     * checks to see if anyone is a winner and whether there are open spots on board
     * @return
     */
    public boolean anyMovesPossible() {
        for (int i = gameBoard.length - 1; i >= 0; i--) {
            for (int j = 0; j < gameBoard[0].length; j++) {
                if (gameBoard[i][j] == EMPTY)
                    return true;
            }
        }
        return false;
    }

    public boolean anyMovesPossible(int location) {
        for (int i = gameBoard.length - 1; i >= 0; i--) {
            if (gameBoard[i][location] == EMPTY)
                return true;
        }
        return false;
    }

    /**
     * Method prints the board
     */
    public void printBoard() {
        char[][] board = getGameBoard();
        //print the header and the numbers of the board
        System.out.printf("Connect 4\n");
        for (int i = 0; i < board[0].length; ++i)
            System.out.printf("   %d", i + 1);
        System.out.println();

        //don't know what this is for
        System.out.printf("   ");
        for (int i = 0; i < board.length; ++i)
            System.out.printf("----");
        System.out.println("--");

        //printing board contents along with each row.
        for (int i = 0; i < board.length; ++i) {
//            System.out.printf("%c", 'A' + i);
            System.out.printf(" ");
            for (int a = 0; a < board[0].length; a++)
                System.out.printf("| %c ", board[i][a]);
            System.out.println("|");

            //print the line between each row
            System.out.printf("  ");
            for (int j = 0; j < board[0].length; ++j)
                System.out.printf("----");
            System.out.println("-");
        }
    }

    public void play() {
        char currentPlayer = CROSS;
        Scanner input = new Scanner(System.in);
        boolean validAI = false;

        //determine if you're using AI or not
        do {
            System.out.println("Do you want to play single player? (yes/no)");
            String str1 = input.nextLine();

            if (str1.equals("yes")) {
                validAI = true;
            }
        } while (!validAI);

        if (validAI) {
            // AI is playing
            System.out.println("AI is playing");

            do {
                if (checkWinner(currentPlayer)) {
                    System.out.println(currentPlayer + " is the winner!");
                    return;
                }

                if (!anyMovesPossible()) {
                    System.out.println("The game is a Tie!");
                    return;
                }

                printBoard();

                boolean valid = true;

                do {
                    if (currentPlayer == CROSS) {
                        System.out.printf("Player: " + currentPlayer + "\nWhere do you want to place your move?\n");
                        try {
                            String str = input.nextLine(); //gets input from the user
                            str = str.toLowerCase();

                            if (str.equals("undo")) {
                                undo();
                                printBoard();
                            } else {
                                int moveLocation = Integer.parseInt(str);

                                if (moveLocation > 0 && moveLocation <= 7) { //checks to see if number value is valid
                                    if (anyMovesPossible(moveLocation - 1)) { //checks if moves are possible in the row

                                        playerMove(moveLocation - 1, currentPlayer); //makes the move
                                        moves.put("" + count, moveLocation - 1);
                                        count++;

                                        if (checkWinner(currentPlayer)) //checks if the move caused player to win
                                            break; //breaks if player won

                                        if (currentPlayer == CROSS)  //changes currentPlayer.
                                            currentPlayer = CIRCLE;
                                        else
                                            currentPlayer = CROSS;

                                        valid = false;
                                    }
                                }
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Not a number. Try Again");
                        }
                    } else {
                        AImove(currentPlayer);

                        if (checkWinner(currentPlayer)) //checks if the move caused player to win
                            break; //breaks if player won

                        if (currentPlayer == CROSS)  //changes currentPlayer.
                            currentPlayer = CIRCLE;
                        else
                            currentPlayer = CROSS;

                        valid = false;
                    }
                    //checks if any moves are possible in the location specified

                } while (valid);
            } while (!checkWinner(currentPlayer));

        } else {

            do {
                if (checkWinner(currentPlayer)) {
                    System.out.println(currentPlayer + " is the winner!");
                    return;
                }

                if (!anyMovesPossible()) {
                    System.out.println("The game is a Tie!");
                    return;
                }

                printBoard();

                boolean valid = true;

                do {
                    System.out.printf("Player: " + currentPlayer + "\nWhere do you want to place your move?");
                    try {
                        String str = input.nextLine(); //gets input from the user
                        str = str.toLowerCase();

                        if (str.equals("undo")) {
                            undo();
                            printBoard();
                        } else {
                            int moveLocation = Integer.parseInt(str);

                            if (moveLocation > 0 && moveLocation <= 7) { //checks to see if number value is valid
                                if (anyMovesPossible(moveLocation - 1)) { //checks if moves are possible in the row

                                    playerMove(moveLocation - 1, currentPlayer); //makes the move
                                    moves.put("" + count, moveLocation - 1);
                                    count++;

                                    if (checkWinner(currentPlayer)) //checks if the move caused player to win
                                        break; //breaks if player won

                                    if (currentPlayer == CROSS)  //changes currentPlayer.
                                        currentPlayer = CIRCLE;
                                    else
                                        currentPlayer = CROSS;

                                    valid = false;
                                }
                            }
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Not a number. Try Again");
                    }
                    //checks if any moves are possible in the location specified

                } while (valid);
            } while (!checkWinner(currentPlayer));
        }
        printBoard();
    }

    public static void main(String[] args) {
        new Connect_4().play();
    }
}