package p2;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class Solver {

    public Solver() {
        
    }

    public byte[] solve(byte[][] start) {
        // board obj representing intitial state
        Board brd = new Board(start);
        
        // cehck if the puzzle is already solved
        if (brd.isSolved()) {
            return new byte[0];
        }
        
        // queue for BFS
        Queue<Board> Q = new ArrayDeque<>();
        Q.add(brd);
        // map to track visited boards and relevant moves
        Map<Board, LinkedList<Byte>> visited = new HashMap<>();
        visited.put(brd, new LinkedList<>());
        // set to track boards leading to dead ends
        Set<Board> dEnds = new HashSet<>();

        // Breadth-First Search (BFS)
        while (!Q.isEmpty()) {
        	// dequeue current baord from queue
            Board currentBoard = Q.poll();
            LinkedList<Byte> currentMoves = visited.get(currentBoard);

            // skip processing if current board in dEnds set
            if (dEnds.contains(currentBoard)) {
                continue;
            }

            // loop through possible moves/directons
            for (byte move = 0; move < 6; move++) {
            	// new board to sim the move
                Board newBoard = new Board(currentBoard.getBoard());
                // check if move is valid, update board
                if (newBoard.move(move)) {
                	// check if new board is solutiton
                    if (newBoard.isSolved()) {
                        // return solution path if goal is reached
                        return redoPath(currentMoves, move);
                    }

                    // cehck if board has not been visited
                    if (!visited.containsKey(newBoard)) {
                    	// make new list of moves and update visited map
                        LinkedList<Byte> newMoves = new LinkedList<>(currentMoves);
                        newMoves.add(move);
                        visited.put(newBoard, newMoves);
                        // enqueue the new board
                        Q.add(newBoard);
                    } else if (visited.get(newBoard).isEmpty()) {
                        // mark new board as dead end
                        dEnds.add(newBoard);
                    }
                }
            }
        }

        // if no solution is found, return empty byte array
        return new byte[0];
    }

    private byte[] redoPath(LinkedList<Byte> movesSoFar, byte lastMove) {
        // create array to store the solution path
        byte[] path = new byte[movesSoFar.size() + 1];
        int i = 0;
        
        // copy moves from list to the array
        for (byte move : movesSoFar) {
            path[i++] = move;
        }
        
        // add last move to the end of array
        path[i] = lastMove;
        // return solution path
        return path;
    }

    // Below is a toy example of how the Solver class would be used.
    public static void main(String[] args) {
        byte[][] solved = {{0}, {1, 1}, {1, 1, 1}, {2, 2, 2, 2}, {3, 3, 3, 3, 3}};

        byte[][] Easy50_05 = {{1}, {1, 1}, {1, 0, 1}, {2, 2, 2, 2}, {3, 3, 3, 3, 3}};

        byte[][] hard = {{1}, {1, 1}, {2, 0, 1}, {2, 1, 2, 2}, {3, 3, 3, 3, 3}};

        Solver solver = new Solver();
        byte[] sol = solver.solve(solved);
        System.out.println(Arrays.toString(sol));
        // Should print out []

        sol = solver.solve(Easy50_05);
        System.out.println(Arrays.toString(sol));
        // Should print out either [0, 5] or [5, 0]

        sol = solver.solve(hard);
        System.out.println(Arrays.toString(sol));
        // I believe the only solution here is [3, 5, 0, 0]
    }
}
