package mco364;

import static mco364.GameOfLife.XX;
import static mco364.GameOfLife.__;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class GameOfLifeTest {

    private GameOfLife life;

    @Before
    public void init() {
        life = new GameOfLife();
        life.seed(GameOfLife.blinker);
    }

    @Test
    public void neighborCount() {
        int count = life.neighborCount(1, 1);
        assertEquals(2, count);
        count = life.neighborCount(2, 1);
        assertEquals(3, count);
        count = life.neighborCount(3, 1);
        assertEquals(2, count);
        count = life.neighborCount(2, 2);
        assertEquals(2, count);
        
        count = life.neighborCount(1, 2);
        assertEquals(1, count);
    }
    
    
    @Test
    public void isAliveNextGeneration() {
        assertTrue(! life.isAliveNextGeneration(1,2));
        assertTrue(life.isAliveNextGeneration(2,2));
        assertTrue(life.isAliveNextGeneration(2,1));
        assertTrue(life.isAliveNextGeneration(2,3));
    }

    @Test
    public void updateToNextGeneration() {

        life.updateToNextGeneration();

        for (int i = 0; i < 2; i++) {
            assertTrue(!life.board[0][i]);
        }
        for (int i = 3; i < 5; i++) {
            assertTrue(!life.board[0][i]);
        }

        assertTrue(life.board[2][1]);
        assertTrue(life.board[2][2]);
        assertTrue(life.board[2][3]);
    }

}