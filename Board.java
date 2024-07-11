package hw3;

public class Board {
	// Add whatever private fields you need here.
	// Remember, only variables of type int, char, boolean, and 1D and 2D arrays
	// of these types are allowed.
	//
	// As always, you may also add private helper methods to the class.  That will
	// likely be very useful on this assignment.
	
	private int sixRows = 6;
	private int sevenColumns = 7;
	private char[][] board;
	private char currentPlayer;

	/**
	 * Constructs a new empty connect 4 game board with player X being the first player
	 * and player 'O' being the second player.
	 */
	public Board() {
		board = new char[sixRows][sevenColumns];
		for (int i = 0; i < sixRows; i++) {
			for(int n = 0; n < sevenColumns; n++) {
				board[i][n] = ' ';
			}
		}
		currentPlayer = 'X';
	}

	/**
	 * Gets the current player (either 'X' or 'O')
	 * 
	 * @return the current player
	 */
	public char currentPlayer() {
		return currentPlayer;
	}

	/**
	 * The current player makes a move in a given column if it is a valid move.
	 * Throws an exception if the game is already over.
	 * 
	 * @param column the column in which to make a move.  For the move to be valid,
	 * the column value must an {@code int} between 1 and 7 inclusive, and
	 * there must have been fewer than 6 moves already made in the given column.
	 * @return {@code true} if the move is valid and false if it is not valid.
	 * @throws RuntimeException if the game is already over.
	 */
	public boolean play(int column) {
		//throw exception if game is already over
		if (gameStatus() != 'U') {
			throw new RuntimeException("Game is already over");
		}
		//check for valid column
		if(column < 1 || column > sevenColumns) {
			return false;
		}
		//find spot in the valid column
		int row = -1;
		for (int i = sixRows - 1; i >= 0; i--) {
			if (board[i][column - 1] == ' ') {
				row = i;
				break;
			}
		}
		//return false if row is full
		if (row == -1) {
			return false;
		}
		//execute move
		board[row][column -1] = currentPlayer;
		
		//switch players
		if (currentPlayer == 'X') {
			currentPlayer = 'O';
		}else {
			currentPlayer = 'X';
		}
		
		return true;
	}

	/**
	 * Determine the status of the game.
	 * 
	 * @return {@code 'X'} or {@code 'O'} if either player has won, {@code 'D'} if
	 * the game is a draw, and {@code 'U'} if the game is still undecided.
	 */
	public char gameStatus() {
	    //check if there has been a win horizontally
	    for (int i = 0; i < sixRows; i++) {
	        for (int n = 0; n <= sevenColumns - 4; n++) {
	            if (board[i][n] != ' ' && 
	            	board[i][n] == board[i][n+1] && 
	            	board[i][n+1] == board[i][n+2] && 
	            	board[i][n+2] == board[i][n+3]) {
	                return board[i][n];
	            }
	        }
	    }
	    
	    //check if there has been a win vertically
	    for (int i = 0; i <= sixRows - 4; i++) {
	        for (int n = 0; n < sevenColumns; n++) {
	            if (board[i][n] != ' ' && 
	            	board[i][n] == board[i+1][n] && 
	            	board[i+1][n] == board[i+2][n] && 
	            	board[i+2][n] == board[i+3][n]) {
	                return board[i][n];
	            }
	        }
	    }
	    
	    //check if there has been a win diagonally (top left to bottom right -> \\\\\\)
	    for (int i = 0; i <= sixRows - 4; i++) {
	        for (int n = 0; n <= sevenColumns - 4; n++) {
	            if (board[i][n] != ' ' && 
	            	board[i][n] == board[i+1][n+1] && 
	            	board[i+1][n+1] == board[i+2][n+2] && 
	            	board[i+2][n+2] == board[i+3][n+3]) {
	                return board[i][n];
	            }
	        }
	    }
	    
	    //check if there was a win diagonally (bottom left to top right -> //////)
	    for (int i = sixRows - 1; i >= 3; i--) {
	        for (int n = 0; n <= sevenColumns - 4; n++) {
	            if (board[i][n] != ' ' && 
	            	board[i][n] == board[i-1][n+1] && 
	            	board[i-1][n+1] == board[i-2][n+2] && 
	            	board[i-2][n+2] == board[i-3][n+3]) {
	                return board[i][n];
	            }
	        }
	    }
	    
	    //check if there is a draw
	    boolean draworNaw = true;
	    for (int i = 0; i < sixRows; i++) {
	        for (int n = 0; n < sevenColumns; n++) {
	            if (board[i][n] == ' ') {
	                draworNaw = false;
	                break;
	            }
	        }
	        if (!draworNaw) {
	            break;
	        }
	    }
	    if (draworNaw) {
	        return 'D';
	    }
	    
	    //game is still not over
	    return 'U';
	}

	/**
	 * Construct a string that depicts the sate of the game.
	 * (See the writeup for what that string should look like.)
	 * 
	 * @return a string depicting the game board
	 */
	public String toString() {
	    String brd = "";
	    for (int i = 0; i < sixRows; i++) {
	        for (int n = 0; n < sevenColumns; n++) {
	            brd += board[i][n] + "  ";
	        }
	        brd += "\n";
	    }
	    brd += "-------------------\n";
	    brd += "1  2  3  4  5  6  7";
	    return brd;
	}
}
