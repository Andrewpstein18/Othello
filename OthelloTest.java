package org.cis120.othello;

import org.junit.jupiter.api.*;

import javax.swing.*;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class OthelloTest {

    @Test
    public void testEastExists() {
        Othello o = new Othello();
        o.alterBoard(2,3,1);
        o.alterBoard(3,3,2);
        o.alterBoard(4,3,2);
        o.alterBoard(5,3,1);
        o.printGameState();
        boolean exists = o.eastExists(true,2,3);
        assertTrue(exists);
        boolean s = o.northExists(true, 2, 3);
        assertFalse(s);
        assertEquals(1,o.getValue(3,3));
        assertEquals(1,o.getValue(4,3));
        o.printGameState();
    }

    @Test
    public void testWestExists() {
        Othello o = new Othello();
        o.alterBoard(2,3,1);
        o.alterBoard(1,3,2);
        o.alterBoard(0,3,1);
        o.printGameState();
        boolean exists = o.westExists(true,2,3);
        assertTrue(exists);
        assertEquals(1,o.getValue(1,3));
        o.printGameState();
    }

    @Test
    public void testSouthExists() {
        Othello o = new Othello();
        o.alterBoard(2,3,1);
        o.alterBoard(2,4,2);
        o.alterBoard(2,5,1);
        o.printGameState();
        boolean exists = o.southExists(true,2,3);
        assertTrue(exists);
        assertEquals(1,o.getValue(2,4));
        o.printGameState();
    }

    @Test
    public void testNorthExists() {
        Othello o = new Othello();
        o.alterBoard(2,3,1);
        o.alterBoard(2,2,2);
        o.alterBoard(2,1,1);
        o.printGameState();
        boolean exists = o.northExists(true,2,3);
        assertTrue(exists);
        assertEquals(1,o.getValue(2,2));
        o.printGameState();
    }

    @Test
    public void testNorthEastExists() {
        Othello o = new Othello();
        o.alterBoard(2,3,1);
        o.alterBoard(3,2,2);
        o.alterBoard(4,1,2);
        o.alterBoard(5,0,1);

        o.printGameState();
        boolean exists = o.northeastExists(true,2,3);
        assertTrue(exists);
        assertEquals(1,o.getValue(3,2));
        assertEquals(1,o.getValue(4,1));
        o.printGameState();
    }

    @Test
    public void testNorthEastWithNothingInBetween() {
        Othello o = new Othello();
        o.alterBoard(2,3,1);
        o.alterBoard(3,2,2);
        o.alterBoard(4,1,0);
        o.alterBoard(5,0,1);

        o.printGameState();
        boolean exists = o.northeastExists(true,2,3);
        assertFalse(exists);
        assertEquals(2,o.getValue(3,2));
        assertEquals(0,o.getValue(4,1));
        o.printGameState();
    }

    @Test
    public void testNorthWestExists() {
        Othello o = new Othello();
        o.alterBoard(2,3,1);
        o.alterBoard(1,2,2);
        o.alterBoard(0,1,1);
        o.printGameState();
        boolean exists = o.northwestExists(true,2,3);
        assertFalse(exists);
        assertEquals(1,o.getValue(1,2));
        o.printGameState();
    }

    @Test
    public void testSouthWestExists() {
        Othello o = new Othello();
        o.alterBoard(2,3,1);
        o.alterBoard(1,4,2);
        o.alterBoard(0,5,1);
        o.printGameState();
        boolean exists = o.southwestExists(true,2,3);
        assertTrue(exists);
        assertEquals(1,o.getValue(1,4));
        o.printGameState();
    }

    @Test
    public void testSouthEastExists() {
        Othello o = new Othello();
        o.alterBoard(2,3,1);
        o.alterBoard(3,4,2);
        o.alterBoard(4,5,1);
        o.printGameState();
        boolean exists = o.southeastExists(true,2,3);
        assertTrue(exists);
        assertEquals(1,o.getValue(3,4));
        o.printGameState();
    }

    @Test
    public void testNorthEastExistsBorders() {
        Othello o = new Othello();
        o.alterBoard(0,7,1);
        o.printGameState();
        boolean exists = o.northeastExists(false,0,7);
        assertFalse(exists);
        o.printGameState();
    }

    @Test
    public void testNorthWestExistsBorders() {
        Othello o = new Othello();
        o.alterBoard(0,0,1);
        o.printGameState();
        boolean exists = o.northwestExists(false,0,0);
        assertFalse(exists);
        o.printGameState();
    }

    @Test
    public void testSouthWestBorders() {
        Othello o = new Othello();
        o.alterBoard(0,7,1);
        o.printGameState();
        boolean exists = o.southwestExists(false,0,7);
        assertFalse(exists);
        o.printGameState();
    }

    @Test
    public void testSouthEastBorders() {
        Othello o = new Othello();
        o.alterBoard(7,7,1);
        o.printGameState();
        boolean exists = o.southeastExists(false,7,7);
        assertFalse(exists);
        o.printGameState();
    }

    @Test
    public void testflipAll() {
        Othello o = new Othello();
        o.alterBoard(1,0,1);
        o.alterBoard(1,1,2);
        o.alterBoard(1,2,1);
        o.alterBoard(1,3,2);
        o.alterBoard(1,4,1);

        o.alterBoard(2,2,2);
        o.alterBoard(3,2,2);
        o.alterBoard(4,2,1);

        o.alterBoard(2,3,2);
        o.alterBoard(3,4,1);

        o.printGameState();
        o.flipAll(1,2);

        assertEquals(1,o.getValue(1,1));
        assertEquals(1,o.getValue(1,3));
        assertEquals(1,o.getValue(2,2));
        assertEquals(1,o.getValue(3,2));
        assertEquals(1,o.getValue(2,3));

        o.printGameState();
    }

    @Test
    public void possibleMoves() {
        Othello o = new Othello();
        o.alterBoard(3,3,1);
        o.alterBoard(3,4,2);
        o.alterBoard(4,3,2);
        o.alterBoard(4,4,1);

        o.printGameState();

        assertTrue(o.possibleMoves());
    }

    @Test
    public void saveToFile() {
        Othello o = new Othello();
        Othello b = new Othello();
        b.reset();
        b.printGameState();
        b.playTurn(3,5);
        b.reset();
        b.playTurn(3,5);
        o.reset();
        o.playTurn(3,5);
    }

    @Test
    public void getNumTurns() {
        Othello o = new Othello();
        o.playTurn(3,5);
        o.playTurn(2,5);
        assertEquals(2, o.getNumTurns());
    }

    @Test
    public void reset() {
        Othello o = new Othello();
        o.playTurn(3,5);
        o.playTurn(2,5);
        o.reset();
        assertEquals(0, o.getNumTurns());
        assertTrue(o.getCurrentPlayer());
        assertEquals(2, o.getBlackCount());
        assertEquals(2, o.getWhiteCount());
        assertEquals(1, o.getValue(3,3));
        assertEquals(1, o.getValue(4,4));
        assertEquals(2, o.getValue(3,4));
        assertEquals(2, o.getValue(4,3));
    }

    @Test
    public void getCurrentPlayerFalse() {
        Othello o = new Othello();
        o.playTurn(3,5);
        assertFalse(o.getCurrentPlayer());
    }

    @Test
    public void getCurrentPlayerTrue() {
        Othello o = new Othello();
        assertTrue(o.getCurrentPlayer());
    }

    @Test
    public void getBlackCount() {
        Othello o = new Othello();
        assertEquals(2,o.getBlackCount());
    }

    @Test
    public void getWhiteCount() {
        Othello o = new Othello();
        assertEquals(2,o.getWhiteCount());
    }

    @Test
    public void getNumTurnsTest() {
        Othello o = new Othello();
        assertEquals(0,o.getNumTurns());
    }

    @Test
    public void getNumTurnsTest2() {
        Othello o = new Othello();
        o.playTurn(5,3);
        assertEquals(1,o.getNumTurns());
    }

    @Test
    public void clearStateMoves() {
        Othello o = new Othello();
        o.playTurn(5,3);
        assertEquals(1, o.getStateMoves().size());
        o.clearStateMoves();
        assertEquals(0,o.getStateMoves().size());
    }

    @Test
    public void playTurn() {
        Othello o = new Othello();
        o.playTurn(5,3);
        assertEquals(1, o.getStateMoves().size());
        assertEquals(1, o.getNumTurns());
        assertFalse(o.getCurrentPlayer());
        assertEquals(4, o.getBlackCount());
        assertEquals(1, o.getWhiteCount());
    }

    @Test
    public void playTurnEdge() {
        Othello o = new Othello();
        o.playTurn(0,0);
        assertEquals(0, o.getStateMoves().size());
        assertEquals(0, o.getNumTurns());
        assertTrue(o.getCurrentPlayer());
        assertEquals(2, o.getBlackCount());
        assertEquals(2, o.getWhiteCount());
    }
    @Test
    public void playTurnEdge2() {
        Othello o = new Othello();
        o.playTurn(3,4);
        assertEquals(0, o.getStateMoves().size());
        assertEquals(0, o.getNumTurns());
        assertTrue(o.getCurrentPlayer());
        assertEquals(2, o.getBlackCount());
        assertEquals(2, o.getWhiteCount());
    }

    @Test
    public void encapsulation() {
        Othello o = new Othello();
        o.playTurn(3,4);
        int i = o.getStateMoves().size();
        LinkedList<Move> x = o.getStateMoves();
        x.add(new Move(0,0));
        LinkedList<Move> y = o.getStateMoves();
        assertEquals(0,i);
        assertEquals(1, x.size());
        assertEquals(0,y.size());
    }


}