package mco364;

import static junit.framework.Assert.assertEquals;
import mco364.GameOfLife.Gen;
import org.junit.Test;
import static org.junit.Assert.*;

public class GameOfLifeTest {

    @Test
    public void neighborCountTest() {
        Grid grid = new Grid(5);
        GameOfLife gol = new GameOfLife(grid, grid.size - 1, GameOfLife.Oscillation.BLINKER);
        gol.seedOscillation();
        Gen nextGen = gol.new Gen(1);

        assertEquals(3, nextGen.neighborCount(2, 3));
    }

    @Test
    public void testIsAliveNextGeneration() {
        Grid board = new Grid(5);
        GameOfLife game = new GameOfLife(board, board.size - 1, GameOfLife.Oscillation.BLINKER);
        game.seedOscillation();
        GameOfLife.Gen generation = game.new Gen(1);

        assertEquals(true, generation.isAliveNextGeneration(2, 3));
    }
}
