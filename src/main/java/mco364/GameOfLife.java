package mco364;

//not finished

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameOfLife {

    public static final boolean XX = true, __ = false;

    private final int size;
    boolean board[][];

    boolean[][] boardCopy;

    GameOfLife() {
        this(20);
    }

    GameOfLife(int size) {
        this.size = size;
        board = new boolean[size][size];
        boardCopy = new boolean[size][size];
    }

    public void seed(boolean seed[][]) {
        for (int i = 0; i < seed.length; i++) {
            System.arraycopy(seed[i], 0, board[i], 0, seed[i].length);
        }
    }

    public int neighborCount(int row, int col) {
        int neighborCounter = 0;
        int colMin = Math.max(0, col - 1), colMax = Math.min(size - 1, col + 1);
        int rowMin = Math.max(0, row - 1), rowMax = Math.min(size - 1, row + 1);

        for (int i = rowMin; i <= rowMax; i++) {
            for (int j = colMin; j <= colMax; j++) {
                if (board[i][j] && !(i == row && j == col)) // ignore "center" cell
                {
                    neighborCounter++;
                }
            }
        }
        return neighborCounter;
    }

    public boolean isAliveNextGeneration(int row, int col) {
        int totalNeighborCount = neighborCount(row, col);
        if (!board[row][col]) {
            return totalNeighborCount == 3;
        }

        return (totalNeighborCount == 2 || totalNeighborCount == 3);
    }

    public void updateToNextGeneration() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                setCellBoardCopy(i, j, isAliveNextGeneration(i, j));
            }
        }
        board = boardCopy;
    }

    class LifeThread implements Runnable {

        private int min;
        private int max;

        LifeThread(int min, int max) {
            this.min = min;
            this.max = max;

        }

        @Override
        public void run() {
            for (int i = min; i < max; i++) {
                int row = i / size;
                int col = i % size;

                boolean isAlive = isAliveNextGeneration(row, col);
                setCellBoardCopy(row, col, isAlive);
            }
        }
    }

    public void updateToNextGenerationMT(int threadCount) {
        ExecutorService ex = Executors.newFixedThreadPool(threadCount);
        double averageSizePerThread = (double) size * size / threadCount;

        for (int threadNum = 0; threadNum < threadCount; threadNum++) {
            int min = (int) (threadNum * averageSizePerThread);
            int max = (int) ((threadNum + 1) * averageSizePerThread);
            LifeThread t = new LifeThread(min, max);
            ex.execute(t);
        }
        ex.shutdown();
        try {
            ex.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException ex1) {

        }

        boolean[][] temp = board;
        board = boardCopy;
        boardCopy = temp;
    }

    private synchronized void setCellBoardCopy(int i, int j, boolean value) {
        boardCopy[i][j] = value;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                sb.append(board[i][j] ? "X" : " ");
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    static final boolean[][] blinker = new boolean[][]{
        {__, __, __, __, __},
        {__, __, XX, __, __},
        {__, __, XX, __, __},
        {__, __, XX, __, __},
        {__, __, __, __, __},};

    static final boolean[][] toad = new boolean[][]{
        {__, __, __, __, __, __},
        {__, __, __, __, __, __},
        {__, __, XX, XX, XX, __},
        {__, XX, XX, XX, __, __},
        {__, __, __, __, __, __},
        {__, __, __, __, __, __},};

    static final boolean[][] beacon = new boolean[][]{
        {__, __, __, __, __, __},
        {__, XX, XX, __, __, __},
        {__, XX, XX, __, __, __},
        {__, __, __, XX, XX, __},
        {__, __, __, XX, XX, __},
        {__, __, __, __, __, __},};

    static final boolean[][] pulsar = new boolean[][]{
        {__, __, __, __, __, __, __, __, __, __, __, __, __, __, __, __, __, __},
        {__, __, __, __, __, __, __, __, __, __, __, __, __, __, __, __, __, __},
        {__, __, __, __, XX, XX, XX, __, __, __, XX, XX, XX, __, __, __, __, __},
        {__, __, __, __, __, __, __, __, __, __, __, __, __, __, __, __, __, __},
        {__, __, XX, __, __, __, __, __, __, __, __, __, __, __, XX, __, __, __},
        {__, __, XX, __, __, __, __, __, __, __, __, __, __, __, XX, __, __, __},
        {__, __, XX, __, __, __, __, __, __, __, __, __, __, __, XX, __, __, __},
        {__, __, __, __, XX, XX, XX, __, __, __, XX, XX, XX, __, __, __, __, __},
        {__, __, __, __, __, __, __, __, __, __, __, __, __, __, __, __, __, __},
        {__, __, __, __, XX, XX, XX, __, __, __, XX, XX, XX, __, __, __, __, __},
        {__, __, XX, __, __, __, __, __, __, __, __, __, __, __, XX, __, __, __},
        {__, __, XX, __, __, __, __, __, __, __, __, __, __, __, XX, __, __, __},
        {__, __, XX, __, __, __, __, __, __, __, __, __, __, __, XX, __, __, __},
        {__, __, __, __, __, __, __, __, __, __, __, __, __, __, __, __, __, __},
        {__, __, __, __, XX, XX, XX, __, __, __, XX, XX, XX, __, __, __, __, __},
        {__, __, __, __, __, __, __, __, __, __, __, __, __, __, __, __, __, __},
        {__, __, __, __, __, __, __, __, __, __, __, __, __, __, __, __, __, __},};

    static final boolean[][] pentadecathlon = new boolean[][]{
        {__, __, __, __, __, __, __, __, __, __, __},
        {__, __, __, __, __, __, __, __, __, __, __},
        {__, __, __, __, __, __, __, __, __, __, __},
        {__, __, __, __, __, XX, __, __, __, __, __},
        {__, __, __, __, XX, XX, XX, __, __, __, __},
        {__, __, __, XX, XX, XX, XX, XX, __, __, __},
        {__, __, __, __, __, __, __, __, __, __, __},
        {__, __, __, __, __, __, __, __, __, __, __},
        {__, __, __, __, __, __, __, __, __, __, __},
        {__, __, __, __, __, __, __, __, __, __, __},
        {__, __, __, __, __, __, __, __, __, __, __},
        {__, __, __, __, __, __, __, __, __, __, __},
        {__, __, __, XX, XX, XX, XX, XX, __, __, __},
        {__, __, __, __, XX, XX, XX, __, __, __, __},
        {__, __, __, __, __, XX, __, __, __, __, __},
        {__, __, __, __, __, __, __, __, __, __, __},
        {__, __, __, __, __, __, __, __, __, __, __},
        {__, __, __, __, __, __, __, __, __, __, __},};
}
