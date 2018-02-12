package app.a3_in_1_game; /**
 * Created by ritzbitz on 2/9/18.
 */


// TODO: Code this class in IntelliJ and copy the code over
// TODO: Use STDIN and STDOUT until we meet and implement the GUI
// TODO: Make sure code is commented and readable
// TODO: Make helper functions
// TODO: Use 2D array to store the board

import java.util.HashMap;
import java.util.Scanner;


public class Connect_4 {

    //instance variables
    char[][] gameBoard;
    int count;
    HashMap<String, Integer> moves;

    //variable constants
    private static final char CROSS = 'X';
    private static final char CIRCLE = 'O';
    private static final char EMPTY = ' ';



    /**
     * creates gameBoard
     */
    public Connect_4() {
        count = 0;
        gameBoard = new char[6][7];
        moves = new HashMap<String, Integer>();
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
        //TODO IMPLEMENT METHOD
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
     * adds the players moves onto the gameboard
     * @return
     */
    public boolean playerMove(int location, char player) {
        //remember to subtract 1 from location when adding
        if (player != CROSS && player != CIRCLE)
            return false;

        if (location < 0 || location > 7)
            return false;

        for (int i = gameBoard.length - 1; i >= 0; i--) {
            if (gameBoard[i][location] == EMPTY) {
                gameBoard[i][location] = player;
                return true;
            }
        }
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

        printBoard();
    }

    public static void main(String[] args) {
        new Connect_4().play();
    }
}