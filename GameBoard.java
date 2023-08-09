package org.cis120.othello;

/**
 * CIS 120 HW09 - TicTacToe Demo
 * (c) University of Pennsylvania
 * Created by Bayley Tuch, Sabrina Green, and Nicolas Corona in Fall 2020.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * This class instantiates a TicTacToe object, which is the model for the game.
 * As the user clicks the game board, the model is updated. Whenever the model
 * is updated, the game board repaints itself and updates its status JLabel to
 * reflect the current state of the model.
 * 
 * This game adheres to a Model-View-Controller design framework. This
 * framework is very effective for turn-based games. We STRONGLY
 * recommend you review these lecture slides, starting at slide 8,
 * for more details on Model-View-Controller:
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec37.pdf
 * 
 * In a Model-View-Controller framework, GameBoard stores the model as a field
 * and acts as both the controller (with a MouseListener) and the view (with
 * its paintComponent method and the status JLabel).
 */
@SuppressWarnings("serial")
public class GameBoard extends JPanel {

    private Othello ttt; // model for the game
    private JLabel status;// current status text
    private JTextArea moves; //curent moves text
    private JLabel blackScore;
    private JLabel whiteScore;
    private JLabel numTurns;

    // Game constants
    public static final int BOARD_WIDTH = 475;
    public static final int BOARD_HEIGHT = 400;

    /**
     * Initializes the game board.
     */
    public GameBoard(JLabel statusInit, JTextArea movesInit, JLabel blackScoreInit,
                     JLabel whiteScoreInit, JLabel numTurnsInit) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Enable keyboard focus on the court area. When this component has the
        // keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        ttt = new Othello(); // initializes model for the game
        status = statusInit; // initializes the status JLabel
        moves = movesInit;
        blackScore = blackScoreInit;
        whiteScore = whiteScoreInit;
        numTurns = numTurnsInit;


        /*
         * Listens for mouseclicks. Updates the model, then updates the game
         * board based off of the updated model.
         */
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                Point p = e.getPoint();

                // updates the model given the coordinates of the mouseclick
                ttt.playTurn(p.x / 50, p.y / 50);

                updateStatus();// updates the status JLabel
                updateMoves();//updates the moves JLabel
                updateWhiteScore();//updates the white score JLabel
                updateBlackScore();//updates the black score JLabel
                updateNumTurns();//updates the NumTurns JLabel
                repaint(); // repaints the game board
            }
        });
    }

    /**
     * (Re-)sets the game to its initial state.
     */
    public void reset() {
        ttt.reset();
        updateStatus();// updates the status JLabel
        updateMoves();//updates the moves JLabel
        updateWhiteScore();//updates the white score JLabel
        updateBlackScore();//updates the black score JLabel
        updateNumTurns();//updates the NumTurns JLabel
        status.setText("White's Turn!");
        repaint();

        // Makes sure this component has keyboard/mouse focus
        requestFocusInWindow();
    }


    /**
     * Updates the JLabel to reflect the current state of the game.
     */
    private void updateStatus() {
        if (ttt.getCurrentPlayer()) {
            status.setText("White's Turn!");
        } else {
            status.setText("Black's turn!");
        }

        int winner = ttt.checkWinner();

        if (winner == 1) {
            status.setText("Black Wins!");
        } else if (winner == 2) {
            status.setText("White Wins");
        } else if (winner == 0) {
            status.setText("Its a tie");
        }

    }

    public void loadGame(String file) {
        ttt.loadFile(file);
        updateStatus();// updates the status JLabel
        updateMoves();//updates the moves JLabel
        updateWhiteScore();//updates the white score JLabel
        updateBlackScore();//updates the black score JLabel
        updateNumTurns();//updates the NumTurns JLabel
        repaint();
    }

    public void updateMoves() {
        String placer = "   Moves:   \n";
        int count = ttt.getStateMoves().size();
        for (Move x : ttt.getStateMoves()) {
            placer += "   #" + count + ": " + x.toString() + " \n";
            count -= 1;
        }
        moves.setText(placer);
    }

    public void updateBlackScore() {
        blackScore.setText("   Black Score: " + ttt.getBlackCount() + "   ");
    }

    public void updateWhiteScore() {
        whiteScore.setText("   White Score: " + ttt.getWhiteCount() + "  ");
    }

    public void updateNumTurns() {
        numTurns.setText("   # of Turns: " + ttt.getNumTurns() + "   ");
    }

    public static void main (String[] args) {
        Object o = "hi";
        System.out.println(o);
    }

    public String getInstructions() {
        return "Othello: " +
                "The board starts with 2 black and white pieces \n" +
                "\n" +
                "The goal is to get the majority of " +
                "colour discs on the board at the end of the game." +
                "Then the game alternates between white and black until:\n" +
                "one player can not make a valid move\n" +
                "both players have no valid moves.\n" +
                "When a player has no valid moves, he pass his turn and the opponent continues.\n" +
                "A player can not voluntarily forfeit his turn.\n" +
                "When both players can not make a valid move the game ends.\n" +
                "The winner is the player with the most pieces at the end of the game";
    }


    /**
     * Draws the game board.
     * 
     * There are many ways to draw a game board. This approach
     * will not be sufficient for most games, because it is not
     * modular. All of the logic for drawing the game board is
     * in this method, and it does not take advantage of helper
     * methods. Consider breaking up your paintComponent logic
     * into multiple methods or classes, like Mushroom of Doom.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Color othelloGreen = new Color(72, 93, 63);

        setBackground(othelloGreen);
        // Draws board grid
        Graphics2D g2d = (Graphics2D) g;
        BasicStroke bs = new BasicStroke(2);
        g2d.setStroke(bs);

        g2d.drawLine(50, 0, 50, 400);
        g2d.drawLine(100, 0, 100, 400);
        g2d.drawLine(150, 0, 150, 400);
        g2d.drawLine(200, 0, 200, 400);
        g2d.drawLine(250, 0, 250, 400);
        g2d.drawLine(300, 0, 300, 400);
        g2d.drawLine(350, 0, 350, 400);
        g2d.drawLine(400, 0, 400, 400);

        g2d.drawLine(0, 50, 400, 50);
        g2d.drawLine(0, 100, 400, 100);
        g2d.drawLine(0, 150, 400, 150);
        g2d.drawLine(0, 200, 400, 200);
        g2d.drawLine(0, 250, 400, 250);
        g2d.drawLine(0, 300, 400, 300);
        g2d.drawLine(0, 350, 400, 350);
        g2d.drawLine(0, 400, 400, 400);

        Color othelloWhite = new Color(230, 220, 210);
        Color othelloWhite2 = new Color(150, 140, 130);

        Color mutedBlack = new Color(25,25,25);
        Color mutedBlack2 = new Color(70,70,70);

        // Draws X's and O's
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int state = ttt.getValue(j, i);
                if (state == 1) {
                    g2d.setColor(mutedBlack2);
                    g2d.fillOval(11 + 50 * j, 10 + 50 * i, 28, 28);
                    g2d.setColor(othelloWhite);
                    g2d.fillOval(11 + 50 * j, 11 + 50 * i, 28, 28);
                } else if (state == 2) {
                    g2d.setColor(othelloWhite2);
                    g2d.fillOval(11 + 50 * j, 10 + 50 * i, 28, 28);
                    g2d.setColor(mutedBlack);
                    g2d.fillOval(11 + 50 * j, 11 + 50 * i, 28, 28);
                }
            }
        }
        //do I need to repaint?
    }

    /**
     * Returns the size of the game board.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }
}
