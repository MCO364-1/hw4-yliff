package mco364;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;

class Grid {

    int size;
    boolean b[][];

    public int getSize() {
        return size;
    }

    Grid(int size) {
        this.size = size;
        b = new boolean[size][size];
    }

    public void print() {
        String horizontalLine = new String(new char[size * 4]).replace("\0", "-");
        System.out.println(horizontalLine);

        for (int i = 0; i < size; i++) {
            System.out.print("|");
            for (int j = 0; j < size; j++) {
                System.out.print(b[i][j] ? " O |" : "   |");
            }
            System.out.println();
            System.out.println(horizontalLine);
        }
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                b[i][j] = false;
            }
        }
    }
}

public class GameOfLife {

    public enum Oscillation {

        BLINKER, TOAD, BEACON, PULSAR, PENTADECATHOLON
    }

    private static class Cell {

        private final int row;
        private final int col;

        public Cell(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    private Grid grid;
    private int size;
    private Oscillation osc;
    private List<Cell> aliveCells;

    public GameOfLife(Grid grd, int grdSize, Oscillation o) {
        this.grid = grd;
        this.size = grdSize;
        osc = o;
        aliveCells = new ArrayList<>();
    }

    public void seedOscillation() {
        if (osc == Oscillation.BLINKER) {
            grid.b[1][2] = true;
            grid.b[2][2] = true;
            grid.b[3][2] = true;
        }
        if (osc == Oscillation.TOAD) {
            grid.b[2][1] = true;
            grid.b[1][2] = true;
            grid.b[2][2] = true;
            grid.b[1][3] = true;
            grid.b[1][4] = true;
            grid.b[2][3] = true;
        }
        if (osc == Oscillation.BEACON) {
            grid.b[1][1] = true;
            grid.b[1][2] = true;
            grid.b[2][1] = true;
            grid.b[2][2] = true;
            grid.b[3][3] = true;
            grid.b[3][4] = true;
            grid.b[4][3] = true;
            grid.b[4][4] = true;
        }
        if (osc == Oscillation.PULSAR) {
            grid.b[6][8] = true;
            grid.b[6][9] = true;
            grid.b[6][10] = true;
            grid.b[6][14] = true;
            grid.b[6][15] = true;
            grid.b[6][16] = true;
            grid.b[8][6] = true;
            grid.b[8][11] = true;
            grid.b[8][13] = true;
            grid.b[8][18] = true;
            grid.b[9][6] = true;
            grid.b[9][11] = true;
            grid.b[9][13] = true;
            grid.b[9][18] = true;
            grid.b[10][6] = true;
            grid.b[10][11] = true;
            grid.b[10][13] = true;
            grid.b[10][18] = true;
            grid.b[11][8] = true;
            grid.b[11][9] = true;
            grid.b[11][10] = true;
            grid.b[11][14] = true;
            grid.b[11][15] = true;
            grid.b[11][16] = true;
            grid.b[13][8] = true;
            grid.b[13][9] = true;
            grid.b[13][10] = true;
            grid.b[13][14] = true;
            grid.b[13][15] = true;
            grid.b[13][16] = true;
            grid.b[14][6] = true;
            grid.b[14][11] = true;
            grid.b[14][13] = true;
            grid.b[14][18] = true;
            grid.b[15][6] = true;
            grid.b[15][11] = true;
            grid.b[15][13] = true;
            grid.b[15][18] = true;
            grid.b[16][6] = true;
            grid.b[16][11] = true;
            grid.b[16][13] = true;
            grid.b[16][18] = true;
            grid.b[18][8] = true;
            grid.b[18][9] = true;
            grid.b[18][10] = true;
            grid.b[18][14] = true;
            grid.b[18][15] = true;
            grid.b[18][16] = true;
        }
        if (osc == Oscillation.PENTADECATHOLON) {
            grid.b[4][5] = true;
            grid.b[5][5] = true;
            grid.b[6][4] = true;
            grid.b[6][6] = true;
            grid.b[7][5] = true;
            grid.b[8][5] = true;
            grid.b[9][5] = true;
            grid.b[10][5] = true;
            grid.b[11][4] = true;
            grid.b[11][6] = true;
            grid.b[12][5] = true;
            grid.b[13][5] = true;
        }
        grid.print();
    }

    public void play() {
        ScheduledThreadPoolExecutor pool = new ScheduledThreadPoolExecutor(10);
        for (int threadNum = 0; threadNum < 25; threadNum++) {
            Thread t = new Thread(new Gen(threadNum));
            pool.execute(t);
        }
        pool.shutdown();

        while (!pool.isTerminated()) {
            //purpose is so thread will have time to complete
        }
        grid.clear();

        for (Cell c : aliveCells) {
            grid.b[c.row][c.col] = true;
        }

        grid.print();
        aliveCells.clear();
    }

    class Gen implements Runnable {

        private int startPt;
        private int endPt;

        public Gen(int startPt) {
            this.startPt = startPt;
            this.endPt = size;
        }

        @Override
        public void run() {
            if (startPt <= size) {
                for (int i = 0; i <= endPt; i++) {
                    if (isAliveNextGeneration(startPt, i)) {
                        aliveCells.add(new Cell(startPt, i));
                    }
                }
            }
        }

        public int neighborCount(int row, int col) {
            int count = 0;

            if (isOnGrid(row, col + 1)) {
                if (grid.b[row][col + 1]) {
                    count++;
                }
            }
            if (isOnGrid(row, col - 1)) {
                if (grid.b[row][col - 1]) {
                    count++;
                }
            }
            if (isOnGrid(row + 1, col)) {
                if (grid.b[row + 1][col]) {
                    count++;
                }
            }
            if (isOnGrid(row - 1, col)) {
                if (grid.b[row - 1][col]) {
                    count++;
                }
            }
            if (isOnGrid(row - 1, col - 1)) {
                if (grid.b[row - 1][col - 1]) {
                    count++;
                }
            }
            if (isOnGrid(row - 1, col + 1)) {
                if (grid.b[row - 1][col + 1]) {
                    count++;
                }
            }
            if (isOnGrid(row + 1, col - 1)) {
                if (grid.b[row + 1][col - 1]) {
                    count++;
                }
            }
            if (isOnGrid(row + 1, col + 1)) {
                if (grid.b[row + 1][col + 1]) {
                    count++;
                }
            }

            return count;
        }

        public boolean isOnGrid(int row, int col) {
            return (row >= 0 && row <= size && col >= 0 && col <= size);
        }

        public boolean isAliveNextGeneration(int row, int col) {

            if (!grid.b[row][col]) {
                return neighborCount(row, col) == 3;
            }

            return neighborCount(row, col) == 2 || neighborCount(row, col) == 3;
        }

    }

}
