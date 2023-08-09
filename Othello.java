package org.cis120.othello;


/**
 * CIS 120 HW09 - TicTacToe Demo
 * (c) University of Pennsylvania
 * Created by Bayley Tuch, Sabrina Green, and Nicolas Corona in Fall 2020.
 */




import java.io.*;
import java.nio.file.Paths;
import java.util.*;

/**
 * This class is a model for TicTacToe.
 * 
 * This game adheres to a Model-View-Controller design framework.
 * This framework is very effective for turn-based games. We
 * STRONGLY recommend you review these lecture slides, starting at
 * slide 8, for more details on Model-View-Controller:
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec36.pdf
 * 
 * This model is completely independent of the view and controller.
 * This is in keeping with the concept of modularity! We can play
 * the whole game from start to finish without ever drawing anything
 * on a screen or instantiating a Java Swing object.
 * 
 * Run this file to see the main method play a game of TicTacToe,
 * visualized with Strings printed to the console.
 */
public class Othello {

    private int[][] board;
    private int numTurns;
    private boolean player1;
    private boolean gameOver;
    private LinkedList<Move> stateMoves;
    private int blackCount;
    private int whiteCount;
    private String currentFileLoc;
    private FileLineIterator reader;
    private static int fileNumberCount = 0;





    /**
     * Constructor sets up game state.
     */
    public Othello() {
        reset();
        stateMoves = new LinkedList<>();
    }


    /***********************************************************************
     ***********************************************************************/

    /**
     * playTurn allows players to play a turn. Returns true if the move is
     * successful and false if a player tries to play in a location that is
     * taken or after the game has ended. If the turn is successful and the game
     * has not ended, the player is changed. If the turn is unsuccessful or the
     * game has ended, the player is not changed.
     *
     * @param c column to play in
     * @param r row to play in
     * @return whether the turn was successful
     */
    public boolean playTurn(int c, int r) {
        //check for invalid moves which include
        // no impact
        // if there is a piece there
        // the game is over
        if (!islegal(c,r)) {
            return false;
        }

        //place the piece
        board[r][c] = player1 ? 1 : 2;

        //change board
        flipAll(c,r);

        //update count
        updateCount();

        //update moves
        addMove(c,r);

        //increase num turns
        numTurns++;

        //check for winner
        checkWinner();

        //if no win, flip the turn
        changeTurn();

        //if there are no possible moves move the turn back
        if (!possibleMoves()) {
            changeTurn();
        }

        writeToFile(r,c);

        return true;
    }

    /**
     *
     * checks if the move has any possible flipovers
     */
    private boolean islegal(int x, int y) {
        //check that it is an empty space
        if (board[y][x] != 0 || gameOver) {
            return false;
        }

        boolean e = eastExists(false, x, y);
        boolean n = northExists(false, x, y);
        boolean s = southExists(false, x, y);
        boolean w = westExists(false, x, y);
        boolean ne = northeastExists(false, x, y);
        boolean nw = northwestExists(false, x, y);
        boolean se = southeastExists(false, x ,y);
        boolean sw = southwestExists(false, x, y);

        return (e || n || s || w || ne || nw || se || sw);
    }

    public void flipAll(int x, int y) {
        eastExists(true, x, y);
        northExists(true, x, y);
        southExists(true, x, y);
        westExists(true, x, y);
        northeastExists(true, x, y);
        northwestExists(true, x, y);
        southeastExists(true, x ,y);
        southwestExists(true, x, y);
    }


    //invariant is coords are starting from 0
    // Would it be better to create an abstract class with method exists() and dynamically
    //implement the methods? Is that enough distinction?
    /**
     *
     * checks if the move has a possible flipover in the specific direction
     * and flips it over if the boolean flip is true.
     */
    public boolean eastExists(boolean flip, int x, int y) {
        // check which player is performing the move
        int color = 0;
        int opponentColor = 0;

        color = player1 ? 1 : 2;
        opponentColor = player1 ? 2 : 1;

        //keep track of if we are right next to the OG piece
        int count = 0;

        // keep track of X coord at the end
        int targetX = 0;

        for (int i = x + 1 ; i <= 7; i ++) {
            int placer = board[y][i];

            // check if the space has other color
            if (placer == opponentColor) {
                count += 1;
            }

            //if the same color
            if (placer == color) {
                //if not right next to it we found a legal move
                targetX = i;
                // if piece right next to it
                if (count == 0) {
                    return false;
                }

                //check if we should not flip the board
                if (!flip) {
                    return true;
                }

                // flip and then return true
                for (int j = x + 1; j < targetX; j ++) {
                    board [y][j] = color;
                }

                return true;
            }

            //if there are no pieces to the right return false
            if (placer == 0) {
                return false;
            }

        }

        return false;
    }

    public boolean northExists(boolean flip, int x, int y) {
        // check which player is performing the move
        int color = 0;
        int opponentColor = 0;

        color = player1 ? 1 : 2;
        opponentColor = player1 ? 2 : 1;

        //keep track of if we are right next to the OG piece
        int count = 0;

        // keep track of Y coord at the end
        int targetY = 0;

        for (int i = y - 1 ; i >= 0; i --) {
            int placer = board[i][x];

            // check if the space has other color
            if (placer == opponentColor) {
                count += 1;
            }

            //if the same color
            if (placer == color) {
                //if not right next to it we found a legal move
                targetY = i;
                // if piece right next to it
                if (count == 0) {
                    return false;
                }

                //check if we should not flip the board
                if (!flip) {
                    return true;
                }

                // flip and then return true
                for (int j = y - 1; j > targetY; j --) {
                    board [j][x] = color;
                }

                return true;

            }

            //if there are no pieces to the right return false
            if (placer == 0) {
                return false;
            }

        }

        return false;
    }

    public boolean southExists(boolean flip, int x, int y) {
        // check which player is performing the move
        int color = 0;
        int opponentColor = 0;

        color = player1 ? 1 : 2;
        opponentColor = player1 ? 2 : 1;

        //keep track of if we are right next to the OG piece
        int count = 0;

        // keep track of Y coord at the end
        int targetY = 0;

        for (int i = y + 1 ; i <= 7; i ++) {
            int placer = board[i][x];

            // check if the space has other color
            if (placer == opponentColor) {
                count += 1;
            }

            //if the same color
            if (placer == color) {
                //if not right next to it we found a legal move
                targetY = i;
                // if piece right next to it
                if (count == 0) {
                    return false;
                }

                //check if we should not flip the board
                if (!flip) {
                    return true;
                }

                // flip and then return true
                for (int j = y + 1; j < targetY; j ++) {
                    board [j][x] = color;
                }

                return true;

            }

            //if there are no pieces to the right return false
            if (placer == 0) {
                return false;
            }

        }

        return false;
    }

    public boolean westExists(boolean flip, int x, int y) {
// check which player is performing the move
        int color = 0;
        int opponentColor = 0;

        color = player1 ? 1 : 2;
        opponentColor = player1 ? 2 : 1;

        //keep track of if we are right next to the OG piece
        int count = 0;

        // keep track of Y coord at the end
        int targetX = 0;

        for (int i = x - 1 ; i >= 0; i --) {
            int placer = board[y][i];

            // check if the space has other color
            if (placer == opponentColor) {
                count += 1;
            }

            //if the same color
            if (placer == color) {
                //if not right next to it we found a legal move
                targetX = i;
                // if piece right next to it
                if (count == 0) {
                    return false;
                }

                //check if we should not flip the board
                if (!flip) {
                    return true;
                }

                // flip and then return true
                for (int j = x - 1; j > targetX; j --) {
                    board [y][j] = color;
                }

                return true;

            }

            //if there are no pieces to the right return false
            if (placer == 0) {
                return false;
            }

        }

        return false;
    }

    public boolean northeastExists(boolean flip, int x, int y) {

        // check which player is performing the move
        int color = 0;
        int opponentColor = 0;

        color = player1 ? 1 : 2;
        opponentColor = player1 ? 2 : 1;

        //keep track of if we are right next to the OG piece
        int count = 0;

        // keep track of X and Y coord at the end
        int targetX = 0;
        int targetY = 0;

        for (int i = x + 1, j = y - 1; i <= 7 && j >= 0 ; i ++, j --) {
            int placer = board[j][i];

            // check if the space has other color
            if (placer == opponentColor) {
                count += 1;
            }
            //if there are no pieces to the right return false

            if (placer == 0) {
                return false;
            }

            //if the same color
            if (placer == color) {
                targetX = i;
                targetY = j;

                // if piece right next to it
                if (count == 0) {
                    return false;
                }

                //check if we should not flip the board

                if (!flip) {
                    return true;
                }

                // flip and then return true
                for (int a = x + 1, b = y - 1; b > targetY && a < targetX; a ++, b --) {
                    board [b][a] = color;
                }
                return true;
            }
        }
        return false;
    }

    public boolean northwestExists(boolean flip, int x, int y) {
        // check which player is performing the move
        int color = 0;
        int opponentColor = 0;

        color = player1 ? 1 : 2;
        opponentColor = player1 ? 2 : 1;

        //keep track of if we are right next to the OG piece
        int count = 0;

        // keep track of X and Y coord at the end
        int targetX = 0;
        int targetY = 0;

        for (int i = x - 1, j = y - 1; i >= 0 && j >= 0 ; i --, j --) {
            int placer = board[j][i];

            // check if the space has other color
            if (placer == opponentColor) {
                count += 1;
            }
            //if there are no pieces to the right return false

            if (placer == 0) {
                return false;
            }

            //if the same color
            if (placer == color) {

                //if not right next to it we found a legal move
                targetX = i;
                targetY = j;

                // if piece right next to it
                if (count == 0) {
                    return false;
                }

                //check if we should not flip the board

                if (!flip) {
                    return true;
                }

                // flip and then return true
                for (int a = x - 1, b = y - 1; b > targetY && a > targetX; a --, b --) {
                    board [b][a] = color;
                }
            }
        }
        return false;
    }

    public boolean southwestExists(boolean flip, int x, int y) {
        // check which player is performing the move
        int color = 0;
        int opponentColor = 0;

        color = player1 ? 1 : 2;
        opponentColor = player1 ? 2 : 1;

        //keep track of if we are right next to the OG piece
        int count = 0;

        // keep track of X and Y coord at the end
        int targetX = 0;
        int targetY = 0;

        for (int i = x - 1, j = y + 1; i >= 0 && j <= 7 ; i --, j ++) {
            int placer = board[j][i];

            // check if the space has other color
            if (placer == opponentColor) {
                count += 1;
            }
            //if there are no pieces to the right return false

            if (placer == 0) {
                return false;
            }

            //if the same color
            if (placer == color) {
                //if not right next to it we found a legal move
                targetX = i;
                targetY = j;

                // if piece right next to it
                if (count == 0) {
                    return false;
                }
                //check if we should not flip the board

                if (!flip) {
                    return true;
                }

                // flip and then return true
                for (int a = x - 1, b = y + 1; b < targetY && a > targetX; a --, b ++) {
                    board [b][a] = color;
                }
                return true;
            }
        }
        return false;
    }

    public boolean southeastExists(boolean flip, int x, int y) {
        // check which player is performing the move
        int color = 0;
        int opponentColor = 0;

        color = player1 ? 1 : 2;
        opponentColor = player1 ? 2 : 1;

        //keep track of if we are right next to the OG piece
        int count = 0;

        // keep track of X and Y coord at the end
        int targetX = 0;
        int targetY = 0;

        for (int i = x + 1, j = y + 1; i <= 7 && j <= 7 ; i ++, j ++) {
            int placer = board[j][i];

            // check if the space has other color
            if (placer == opponentColor) {
                count += 1;
            }
            //if there are no pieces to the right return false

            if (placer == 0) {
                return false;
            }

            //if the same color
            if (placer == color) {

                //if not right next to it we found a legal move
                targetX = i;
                targetY = j;

                // if piece right next to it
                if (count == 0) {
                    return false;
                }

                //check if we should not flip the board

                if (!flip) {
                    return true;
                }

                // flip and then return true
                for (int a = x + 1, b = y + 1; b < targetY && a < targetX; a ++, b ++) {
                    board [b][a] = color;
                }
                return true;
            }
        }
        return false;
    }


    public void changeTurn() {
        player1 = !player1;
    }


    public void addMove(int x, int y) {
        Move newMove = new Move(x,y);
        stateMoves.add(0,newMove);
    }

    public void updateCount() {
        blackCount = 0;
        whiteCount = 0;
        for (int i = 0; i <= 7; i ++) {
            for (int j = 0; j <= 7; j++) {
                int placer = board[i][j];

                //update counts
                blackCount = placer == 2 ? blackCount + 1 : blackCount;
                whiteCount = placer == 1 ? whiteCount + 1 : whiteCount;
            }
        }
    }

    /**
     * checkWinner checks whether the game has reached a win condition.
     * checkWinner only looks for horizontal wins.
     *
     * @return 0 if nobody has won yet, 1 if player 1 has won, and 2 if player 2
     *         has won, 3 if the game hits stalemate
     */
    public int checkWinner() {
        //check if this player has any possible moves on the board
        boolean thisPlayerPossibleMoves = possibleMoves();

        //flip the state to be able to check the possible moves of
        // the opponent
        // since the isLegal() method depends on which player is going
        player1 = !player1;

        // check other players possible moves
        boolean otherPlayerPossibleMoves = possibleMoves();

        //if both can't move then game is over
        if (!thisPlayerPossibleMoves && !otherPlayerPossibleMoves) {
            gameOver = true;
            int placer = 0;

            //check who the winner is
            if (!(blackCount == whiteCount)) {
                placer = blackCount > whiteCount ? 1 : 2;
            }
            //return 0 if tied, 1 if black wins, 2 if white wins
            return placer;
        }

        // if game is still playing switch the state back and return false
        player1 = !player1;

        //return -1 if the game is still playing
        return -1;
    }

    public boolean possibleMoves() {
        //check if there are any possible moves left
        for (int i = 0; i <= 7; i ++) {
            for (int j = 0; j <= 7; j++) {
                if (islegal(j,i)) {
                    return true;
                }
            }
        }

        return false;
    }

    /***********************************************************************
     ***********************************************************************/

    public void writeToFile(int r, int c) {
        //find file
        File file = Paths.get(this.currentFileLoc).toFile();
        BufferedWriter bw = null;

        //process int values to strings
        //keep invariant form of 3,4
        // 3 = x; 4 = y
        String moveToWrite = c + "," + r;

        try {
            //create a bufferWriter for that file
            bw = new BufferedWriter(new FileWriter(file,true));
            bw.flush();

        } catch (IOException e) {
            System.out.println("Exception handling the FileWriter");
        }

        // check if we should add on
        try {
            // write this word to the next line in file
            bw.write(moveToWrite);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Problem handling writing a new line");
            return;
        } finally {
            try {
                bw.close();
            } catch (IOException e) {
                System.out.println("Problem closing");
            }
        }
    }

    public String createNewFile() {
        fileNumberCount += 1;
        return "files/game_Save" + fileNumberCount + ".csv";
    }

    public void loadFile(String fileLoc) {
        try {
            reader = new FileLineIterator(fileLoc);
            while (reader.hasNext()) {
                String thisMoveString = reader.next();
                String[] thisMove = thisMoveString.split(",");
                System.out.println(this.currentFileLoc);
                int xValue = Integer.valueOf(thisMove[1]);
                int yValue = Integer.valueOf(thisMove[0]);
                playTurn(yValue, xValue);
            }

        } catch (Exception e) {
            System.out.println("Invalid file");
        }

    }

    /***********************************************************************
     ***********************************************************************/

    //public void undo() {
       //clearMostRecentLine();
       //loadFile(this.currentFileLoc);
   // }

    /**
     * reset (re-)sets the game state to start a new game.
     */
    public void reset() {
        board = new int[8][8];
        board[3][3] = 1;
        board[4][4] = 1;
        board[3][4] = 2;
        board[4][3] = 2;
        numTurns = 0;
        player1 = true;
        gameOver = false;
        blackCount = 2;
        whiteCount = 2;
        clearStateMoves();
        this.currentFileLoc = createNewFile();
    }


    /***********************************************************************
     ***********************************************************************/

    //for testing
    public void alterBoard(int x, int y, int value) {
        board [y][x] = value;
    }

    /**
     * getCurrentPlayer is a getter for the player
     * whose turn it is in the game.
     * 
     * @return true if it's Player 1's turn,
     *         false if it's Player 2's turn.
     */
    public boolean getCurrentPlayer() {
        return player1;
    }

    public int getBlackCount() {
        return this.blackCount;
    }

    public int getWhiteCount() {
        return this.whiteCount;
    }


    /**
     * getValue is a getter for the contents of the cell specified by the method
     * arguments.
     *
     * @param x column to retrieve
     * @param y row to retrieve
     * @return an integer denoting the contents of the corresponding cell on the
     *         game board. 0 = empty, 1 = Player 1, 2 = Player 2
     */
    public int getValue(int x, int y) {
        return board[y][x];
    }

    /**
     * printGameState prints the current game state
     * for debugging.
     */
    public void printGameState() {
        System.out.println("\n\nTurn " + numTurns + ":\n");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j]);
                if (j < 7) {
                    System.out.print(" | ");
                }
            }
            if (i < 7) {
                System.out.println("\n------------------------------------");
            }
        }
    }


    //accessor method for stateMoves
    public LinkedList<Move> getStateMoves() {
        return (LinkedList<Move>) stateMoves.clone();
    }

    //accessor method for numMoves
    public int getNumTurns() {
        return numTurns;
    }

    public void clearStateMoves() {
        stateMoves = new LinkedList<>();
    }





    /**
     * This main method illustrates how the model is completely independent of
     * the view and controller. We can play the game from start to finish
     * without ever creating a Java Swing object.
     *
     * This is modularity in action, and modularity is the bedrock of the
     * Model-View-Controller design framework.
     *
     * Run this file to see the output of this method in your console.
     */
    public static void main(String[] args) {
        Othello t = new Othello();
        t.printGameState();

        t.playTurn(3, 5);
        t.printGameState();

        t.playTurn(4, 5);
        t.printGameState();

        t.playTurn(5, 3);
        t.printGameState();

        t.playTurn(4,2);
        t.printGameState();

        t.printGameState();

        LinkedList placer = t.getStateMoves();
        System.out.println(placer);


        placer = t.getStateMoves();
        System.out.println(placer);


        System.out.println();
        System.out.println();
        System.out.println("Winner is: " + t.checkWinner());
    }
}
