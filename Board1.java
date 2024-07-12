package p2;

import java.util.Arrays;
import java.util.Random;

class Board {
	// move directioms
    public static final byte UR = 0;
    public static final byte R = 1;
    public static final byte DR = 2;
    public static final byte DL = 3;
    public static final byte L = 4;
    public static final byte UL = 5;
    
    // random number generator for random moves creating
    private static final Random rand = new Random();
    // game board 2d byte array
    private byte[][] board;
    
    // bard constructor
    public Board(int moves) {
        // initial board copy
        this.board = new byte[][]{
            {0},{1, 1},{1, 1, 1},{2, 2, 2, 2},{3, 3, 3, 3, 3}
            };

        // random moves in board copy
        for (int i = 0; i < moves; i++) {
            int randomMove = rand.nextInt(6);
            move((byte) randomMove);
        }
    }

    
    public Board(byte[][] b) {
    	// copy 2d array that was given to initalize board
        this.board = new byte[b.length][];
        for (int row = 0; row < b.length; row++) {
            this.board[row] = Arrays.copyOf(b[row], b[row].length);
        }
    }


    public byte[][] getBoard() {
    	// return the board
        return board;
    }

    public boolean move(byte dir) {
        int[] zeroPos = findPosition((byte) 0);

        if (zeroPos == null) {
            return false;
        }

        int newRow, newCol;

        // case statements for each of the move directions
        switch (dir) {
            case UR:
                newRow = zeroPos[0] - 1;
                newCol = zeroPos[1];
                break;
            case R:
                newRow = zeroPos[0];
                newCol = zeroPos[1] + 1;
                break;
            case DR:
                newRow = zeroPos[0] + 1;
                newCol = zeroPos[1] + 1;
                break;
            case DL:
                newRow = zeroPos[0] + 1;
                newCol = zeroPos[1];
                break;
            case L:
                newRow = zeroPos[0];
                newCol = zeroPos[1] - 1;
                break;
            case UL:
                newRow = zeroPos[0] - 1;
                newCol = zeroPos[1] - 1;
                break;
            default:
                return false; // Invalid move
        }

        if (isValidPosition(newRow, newCol)) {
            swap(zeroPos[0], zeroPos[1], newRow, newCol);
            return true;
        }

        return false;
    }

    public byte cellContents(int row, int col) {
        return board[row][col];
    }
    
    @Override
    public boolean equals(Object obj) {
    	// cehck if objects are same instance in memory
        if (this == obj) return true;
        // check if other obj is nulll or in a different class
        if (obj == null || getClass() != obj.getClass()) return false;

        // case other object to board type for comparison
        Board otherBoard = (Board) obj;
        // num of rows in board
        int numRows = board.length;

        // loop through each row of the current board and other boards
        for (int row = 0; row < numRows; row++) {
            if (!Arrays.equals(board[row], otherBoard.board[row])) {
            	// false if any row is not equal
                return false;
            }
        }
        
        // true - board equal
        return true;
    }
    
    public int hashCode() {
        int result = 1;
        int numRows = board.length;
        
        // loop through each row of board
        // update result with combindation of multiplaction and addition
        for (int row = 0; row < numRows; row++) {
            result = 31 * result + customHashCode(board[row]);
        }

        return result;
    }
    
    private int customHashCode(byte[] arr) {
        int hash = 0;

        for (byte b : arr) {
            hash = (hash << 5) ^ b;
        }

        return hash;
    }

    private int[] findPosition(byte value) {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col] == value) {
                    return new int[]{row, col};
                }
            }
        }
        return null;
    }
    
    // if solved
    public boolean isSolved() {
        byte[][] solvedBoard = new byte[][]{
                {0},{1, 1},{1, 1, 1},{2, 2, 2, 2},{3, 3, 3, 3, 3}
        };

        return Arrays.deepEquals(board, solvedBoard);
    }

    // if valid pos
    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < board.length && col >= 0 && col < board[row].length;
    }
    
    // print the board
    public void printBoard() {
        int numRows = board.length;
        // loop through rows
        for (int row = 0; row < numRows; row++) {
            // print spaces for board design
            for (int i = 0; i < numRows - row - 1; i++) {
                System.out.print(" ");
            }
            
            // loop thorugh columns
            for (int col = 0; col < board[row].length; col++) {
                // print value of curent cell
            	System.out.print(board[row][col]);

                // print spaces
                if (col < board[row].length - 1) {
                    System.out.print(" ");
                }
            }
            // next row
            System.out.println();
        }
    }


    private void swap(int row1, int col1, int row2, int col2) {
        byte temp = board[row1][col1];
        board[row1][col1] = board[row2][col2];
        board[row2][col2] = temp;
    }
    
    
}