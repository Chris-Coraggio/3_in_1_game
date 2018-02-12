package app.a3_in_1_game;
import java.util.*;
/**
 * Created by hamza on 2/2/2018.
 */
// TODO: Code this class in IntelliJ and copy the code over
// TODO: Use STDIN and STDOUT until we meet and implement the GUI
// TODO: Make sure code is commented and readable
// TODO: Make helper functions
// TODO: Use 2D array to store the board

public class Tic_Tac_Toe {
public static int AiRow;
	public static int AiCol;


	private static final char OPEN = ' ';
	private static final char USERX = 'X';
	private static final char COMPUTERO = 'O';
	private static final int USER = 0;
	private static final int AI = 1;

	
	private char[][] board; //game board
	private int currentUser; //currentUser, 0 for player, 1 for AI
	private int spacesOccupied; //the number of spaces that are occupied
	private String[] userSpaces; //the spaces the user has occupied
	private String[] comSpaces; //the spaces the AI has occupied
	private int userIndex; //index of userSpaces
	private int comIndex; //index of comSpaces

	//constructor
	public Tic_Tac_Toe() {
		newGame();
	}

	//creates a new game of tic tac toe
	public void newGame() {
		//initialize the 3 x 3 game board
		this.board = new char[3][3];

		//initialize the number of open spaces
		this.spacesOccupied = 0;

		//set every value to OPEN
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				this.board[i][j] = OPEN;
			}
		}

		//initialize tokenArrays and indices
		this.userSpaces = new String[5];
		this.comSpaces = new String[5];
		this.userIndex = 0;
		this.comIndex = 0;

		//randomly generate who plays first, user or AIs
		Random r = new Random();
		//this.currentUser = r.nextInt(2);
		this.currentUser = 0;
	}

	//plays the game
	public int playGame() {
		newGame();
		boolean forfeit = false;

		//while loop here
		//while (!gameOver()) {
		//	printBoard();

			//if it's the players turn
			if (currentUser == USER) {
			//	Scanner s = new Scanner(System.in);
				int option = 0;

				//select to play or forfeit
				do {
					System.out.println("Select an action: ");
					System.out.println("1) Take a turn");
					System.out.println("2) Forfeit the game");
					//option = s.nextInt();
					option = 1;
				} while (option < 1 || option > 2);

				if (option == 1) {
					//playerTurn(int );
				}

				else {
					forfeit = true;
				}
				
			}

			// it's the AI's turn
		//	else {
			//	computerTurn();
			//}

			//if player forfeits, end this shit.
			//if (forfeit) {
			//	break;
			//}

			//update the current user
			this.currentUser = (++currentUser % 2);
		//}
		//printBoard();
		//System.out.println("The Game has reached its conclusion");
		return 1;
	}

	//place a token 
	public void placeToken(int player, int row, int col) {

		//if its the user, place x
		if (player == USER) {
			this.board[row][col] = USERX;

			//update the userSpaces array with the move made
			//this.userSpaces[userIndex++] = "" + row + col;
		}

		//player = 1, AI, palce o
		else {
			this.board[row][col] = COMPUTERO;

			//update the comSpaces array with the move made
			this.comSpaces[comIndex++] = "" + row + col;


		}

		//update the number of spaces occupied
		this.spacesOccupied++;
	}

	//check to see if a space has been taken by the other player, return false for occupied, return true for available
	public boolean checkSpace(int player, int row, int col) {
		//if its the user, check for o
		if (player == USER) {
			//return occupied if computer has taken the spot
			if (this.board[row][col] != OPEN) {
				return false;
			}
		}
		//if its the AI, check for x
		else if (player == AI) {
			//return occupied if player has taken the spot
			if (this.board[row][col] != OPEN) {
				return false;
			}
		}
		return true;
	}

	//make the User's move
	public void playerTurn(int row, int col) {
		//int row = 0;
		//int col = 0;
		//Scanner s = new Scanner(System.in);
		//do {

			//select a row
		//	do {
		//		System.out.println("Select a row: ");
		//		row = s.nextInt();
		//	} while (row < 0 || row > 2);

			//select a col
		//	do {
		//		System.out.println("Select a column: ");
		//		col = s.nextInt();
		//	} while (col < 0 || col > 2);

	//	}
	while (!checkSpace(USER, row, col));

		//place the token
		placeToken(USER, row, col);
	}

	//make the AI's move
	public boolean computerTurn() {
		if (spacesOccupied == 9)
			return true;

		String victory = checkWin();
		String block = checkBlock();

		//if you can win, win
		if (victory != null) {
			int row = Character.getNumericValue(victory.charAt(0));
			int col = Character.getNumericValue(victory.charAt(1));

			placeToken(AI, row, col);
			AiRow = row;
			AiCol = col;
		}

		//if you need to block, block
		else if (block != null) {
			int row = Character.getNumericValue(block.charAt(0));
			int col = Character.getNumericValue(block.charAt(1));
			AiRow = row;
			AiCol = col;
			placeToken(AI, row, col);
		}

		//else pick a random spot
		else {

			boolean keepPicking = false;
			int row = 0;
			int col = 0;

			while(!keepPicking) {

				//select random number from 0-8
				Random r = new Random();
				int move = r.nextInt(9);

				//figure out the move
				switch (move) {
					case 0: keepPicking = checkSpace(AI, 0, 0);
						row = 0;
						col = 0;
						break;
					case 1: keepPicking = checkSpace(AI, 0, 1);
						row = 0;
						col = 1;
						break;
					case 2: keepPicking = checkSpace(AI, 0, 2);
						row = 0;
						col = 2;
						break;
					case 3: keepPicking = checkSpace(AI, 1, 0);
						row = 1;
						col = 0;
						break;
					case 4: keepPicking = checkSpace(AI, 1, 1);
						row = 1;
						col = 1;
						break;
					case 5: keepPicking = checkSpace(AI, 1, 2);
						row = 1;
						col = 2;
						break;
					case 6: keepPicking = checkSpace(AI, 2, 0);
						row = 2;
						col = 0;
						break;
					case 7: keepPicking = checkSpace(AI, 2, 1);
						row = 2;
						col = 1;
						break;
					case 8: keepPicking = checkSpace(AI, 2, 2);
						row = 2;
						col = 2;
						break;
				}
			}

			//make the move
			placeToken(AI, row, col);
			AiRow = row;
			AiCol = col;
		}
		return false;
	}

	//check to see if player is aout to win, returns a concatenation of the row and column where the block needs to occur
	public String checkBlock() {
		//the row and column where a block needs to happen, null if no block necessary
		String block = null;

		//figure out if and where a block needs to occur

		//check rows
		if (this.board[0][0] == this.board[0][1] && this.board[0][0] == USERX && this.board[0][2] == OPEN) {
			block = "02";
		}

		else if (this.board[0][1] == this.board[0][2] && this.board[0][1] == USERX && this.board[0][0] == OPEN) {
			block = "00";
		}

		else if (this.board[0][0] == this.board[0][2] && this.board[0][0] == USERX && this.board[0][1] == OPEN) {
			block = "01";
		}

		else if (this.board[1][0] == this.board[1][1] && this.board[1][0] == USERX && this.board[1][2] == OPEN) {
			block = "12";
		}

		else if (this.board[1][1] == this.board[1][2] && this.board[1][1] == USERX && this.board[1][0] == OPEN) {
			block = "10";
		}

		else if (this.board[1][0] == this.board[1][2] && this.board[1][0] == USERX && this.board[1][1] == OPEN) {
			block = "11";
		}

		else if (this.board[2][0] == this.board[2][1] && this.board[2][0] == USERX && this.board[2][2] == OPEN) {
			block = "22";
		}

		else if (this.board[2][1] == this.board[2][2] && this.board[2][1] == USERX && this.board[2][0] == OPEN) {
			block = "20";
		}

		else if (this.board[2][0] == this.board[2][2] && this.board[2][0] == USERX && this.board[2][1] == OPEN) {
			block = "21";
		}

		//check columns
		else if (this.board[0][0] == this.board[1][0] && this.board[0][0] == USERX && this.board[2][0] == OPEN) {
			block = "20";
		}

		else if (this.board[1][0] == this.board[2][0] && this.board[1][0] == USERX && this.board[0][0] == OPEN) {
			block = "00";
		}

		else if (this.board[0][0] == this.board[2][0] && this.board[0][0] == USERX && this.board[1][0] == OPEN) {
			block = "10";
		}

		else if (this.board[0][1] == this.board[1][1] && this.board[0][1] == USERX && this.board[2][1] == OPEN) {
			block = "21";
		}

		else if (this.board[1][1] == this.board[2][1] && this.board[1][1] == USERX && this.board[0][1] == OPEN) {
			block = "01";
		}

		else if (this.board[0][1] == this.board[2][1] && this.board[0][1] == USERX && this.board[1][1] == OPEN) {
			block = "11";
		}

		else if (this.board[0][2] == this.board[1][2] && this.board[0][2] == USERX && this.board[2][2] == OPEN) {
			block = "22";
		}

		else if (this.board[1][2] == this.board[2][2] && this.board[1][2] == USERX && this.board[0][2] == OPEN) {
			block = "02";
		}

		else if (this.board[0][2] == this.board[2][2] && this.board[0][2] == USERX && this.board[1][2] == OPEN) {
			block = "12";
		}

		//check diagonals
		else if (this.board[0][0] == this.board[1][1] && this.board[0][0] == USERX && this.board[2][2] == OPEN) {
			block = "22";
		}

		else if (this.board[1][1] == this.board[2][2] && this.board[1][1] == USERX && this.board[0][0] == OPEN) {
			block = "00";
		}

		else if (this.board[0][0] == this.board[2][2] && this.board[0][0] == USERX && this.board[1][1] == OPEN) {
			block = "11";
		}

		else if (this.board[0][2] == this.board[1][1] && this.board[0][2] == USERX && this.board[2][0] == OPEN) {
			block = "20";
		}

		else if (this.board[1][1] == this.board[2][0] && this.board[1][1] == USERX && this.board[0][2] == OPEN) {
			block = "02";
		}

		else if (this.board[0][2] == this.board[2][0] && this.board[0][2] == USERX && this.board[1][1] == OPEN) {
			block = "11";
		}
		return block;
	}

	//check to see if AI is aout to win, returns a concatenation of the row and column where the victory occurs
	public String checkWin() {
		//the row and column where a victory happens, null if no victory available
		String victory = null;

		//figure out if and where a victory can occur

		//check rows
		if (this.board[0][0] == this.board[0][1] && this.board[0][0] == COMPUTERO && this.board[0][2] == OPEN) {
			victory = "02";
		}

		else if (this.board[0][1] == this.board[0][2] && this.board[0][1] == COMPUTERO && this.board[0][0] == OPEN) {
			victory = "00";
		}

		else if (this.board[0][0] == this.board[0][2] && this.board[0][0] == COMPUTERO && this.board[0][1] == OPEN) {
			victory = "01";
		}

		else if (this.board[1][0] == this.board[1][1] && this.board[1][0] == COMPUTERO && this.board[1][2] == OPEN) {
			victory = "12";
		}

		else if (this.board[1][1] == this.board[1][2] && this.board[1][1] == COMPUTERO && this.board[1][0] == OPEN) {
			victory = "10";
		}

		else if (this.board[1][0] == this.board[1][2] && this.board[1][0] == COMPUTERO && this.board[1][1] == OPEN) {
			victory = "11";
		}

		else if (this.board[2][0] == this.board[2][1] && this.board[2][0] == COMPUTERO && this.board[2][2] == OPEN) {
			victory = "22";
		}

		else if (this.board[2][1] == this.board[2][2] && this.board[2][1] == COMPUTERO && this.board[2][0] == OPEN) {
			victory = "20";
		}

		else if (this.board[2][0] == this.board[2][2] && this.board[2][0] == COMPUTERO && this.board[2][1] == OPEN) {
			victory = "21";
		}

		//check columns
		else if (this.board[0][0] == this.board[1][0] && this.board[0][0] == COMPUTERO && this.board[2][0] == OPEN) {
			victory = "20";
		}

		else if (this.board[1][0] == this.board[2][0] && this.board[1][0] == COMPUTERO && this.board[0][0] == OPEN) {
			victory = "00";
		}

		else if (this.board[0][0] == this.board[2][0] && this.board[0][0] == COMPUTERO && this.board[1][0] == OPEN) {
			victory = "10";
		}

		else if (this.board[0][1] == this.board[1][1] && this.board[0][1] == COMPUTERO && this.board[2][1] == OPEN) {
			victory = "21";
		}

		else if (this.board[1][1] == this.board[2][1] && this.board[1][1] == COMPUTERO && this.board[0][1] == OPEN) {
			victory = "01";
		}

		else if (this.board[0][1] == this.board[2][1] && this.board[0][1] == COMPUTERO && this.board[1][1] == OPEN) {
			victory = "11";
		}

		else if (this.board[0][2] == this.board[1][2] && this.board[0][2] == COMPUTERO && this.board[2][2] == OPEN) {
			victory = "22";
		}

		else if (this.board[1][2] == this.board[2][2] && this.board[1][2] == COMPUTERO && this.board[0][2] == OPEN) {
			victory = "02";
		}

		else if (this.board[0][2] == this.board[2][2] && this.board[0][2] == COMPUTERO && this.board[1][2] == OPEN) {
			victory = "12";
		}

		//check diagonals
		else if (this.board[0][0] == this.board[1][1] && this.board[0][0] == COMPUTERO && this.board[2][2] == OPEN) {
			victory = "22";
		}

		else if (this.board[1][1] == this.board[2][2] && this.board[1][1] == COMPUTERO && this.board[0][0] == OPEN) {
			victory = "00";
		}

		else if (this.board[0][0] == this.board[2][2] && this.board[0][0] == COMPUTERO && this.board[1][1] == OPEN) {
			victory = "11";
		}

		else if (this.board[0][2] == this.board[1][1] && this.board[0][2] == COMPUTERO && this.board[2][0] == OPEN) {
			victory = "20";
		}

		else if (this.board[1][1] == this.board[2][0] && this.board[1][1] == COMPUTERO && this.board[0][2] == OPEN) {
			victory = "02";
		}

		else if (this.board[0][2] == this.board[2][0] && this.board[0][2] == COMPUTERO && this.board[1][1] == OPEN) {
			victory = "11";
		}
		return victory;
	}

	//print the board
	public void printBoard() {
		System.out.println(" --- ");
		for (int i = 0; i < 3; i++) {
			System.out.print('|');
			for (int j = 0; j < 3; j++) {
				System.out.print(board[i][j]);
				if (j == 2) {
					System.out.print("|\n");
				}
			}
		}
		System.out.println(" --- ");
	}

	//calculates whether the game has ended
	public boolean gameOver() {

		//see if board is full
	//printBoard();

		//see if someone won on the first row
		if ((board[0][0] == board[0][1]) && (board[0][0] == board[0][2]) && (board[0][0] != OPEN)) {
			return true;
		}

		//see if someone won on the second row
		if ((board[1][0] == board[1][1]) && (board[1][0] == board[1][2]) && (board[1][0] != OPEN)) {
			return true;
		}

		//see if someone won on the third row
		if ((board[2][0] == board[2][1]) && (board[2][0] == board[2][2]) && (board[2][0] != OPEN)) {
			return true;
		}

		//see if someone won on the first column
		if ((board[0][0] == board[1][0]) && (board[0][0] == board[2][0]) && (board[0][0] != OPEN)) {
			return true;
		}

		//see if someone won on the second column
		if ((board[0][1] == board[1][1]) && (board[0][1] == board[2][1]) && (board[0][1] != OPEN)) {
			return true;
		}

		//see if someone won on the first column
		if ((board[0][2] == board[1][2]) && (board[0][2] == board[2][2]) && (board[0][2] != OPEN)) {
			return true;
		}

		//see if someone won on the first diagonal
		if ((board[0][0] == board[1][1]) && (board[0][0] == board[2][2]) && (board[0][0] != OPEN)) {
			return true;
		}

		//see if someone won on the second diagonal
		if ((board[0][2] == board[1][1]) && (board[0][2] == board[2][0]) && (board[0][2] != OPEN)) {
			return true;
		}

		//if (spacesOccupied == 9) {
		//	return true;
		//}

		return false;
	}


}
