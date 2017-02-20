package mco364;

import java.util.Scanner;
import mco364.GameOfLife.Oscillation;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        Grid myGrid = null;
        boolean stopExec = false;
        Scanner kb = new Scanner(System.in);

        System.out.println("Select an Oscillation:\n"
                + "BLINKER, TOAD, BEACON, PULSAR, PENTADECATHOLON (copy & paste)");

        Oscillation osc = Oscillation.valueOf(kb.nextLine());

        System.out.println("Select how to go through the gereations:\n"
                + "A to automate \n"
                + "S for step through manually");
        String method = kb.nextLine();

        if (osc == Oscillation.PULSAR || osc == Oscillation.PENTADECATHOLON) {
            myGrid = new Grid(20);
        } else {
            myGrid = new Grid(5);
        }

        GameOfLife game = new GameOfLife(myGrid, myGrid.size - 1, osc);
        game.seedOscillation();

        if (method.equals("A")) {
            while (!stopExec) {
                game.play();
                sleep(500);
                clearConsole();
            }
        } else {
            while (!stopExec) {
                game.play();
                System.out.println("Enter 1 to continue, 0 to exit");
                int input = kb.nextInt();
                clearConsole();

                if (input == 0) {
                    stopExec = true;
                }
            }
        }

    }

    public final static void clearConsole() {
        for (int i = 0; i < 100; i++) { // safety net since next code only works on console not Netbeans output
            System.out.println();
        }
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                Runtime.getRuntime().exec("cls");
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (final Exception e) {
            //  Handle any exceptions.
        }
    }

    private static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
// ignore
        }
    }
}
