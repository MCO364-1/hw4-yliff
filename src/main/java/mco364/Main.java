package mco364;

import java.awt.Robot;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import static mco364.GameOfLife.XX;
import static mco364.GameOfLife.__;

public class Main {

    public static void main(String[] args) {
        clearConsole();

        GameOfLife life = new GameOfLife(18);
        Scanner kb = new Scanner(System.in);

        System.out.println("Select which oscillation you wish to see:\n"
                + "1 for Blinker, 2 for Toad, 3 for Beacon, "
                + "4 for Pulsar, 5 for Pentadecathlon ");

        int selection = kb.nextInt();
        if (selection == 1) {
            life.seed(GameOfLife.blinker);
        }
        if (selection == 2) {
            life.seed(GameOfLife.toad);
        }
        if (selection == 3) {
            life.seed(GameOfLife.beacon);
        }
        if (selection == 4) {
            life.seed(GameOfLife.pulsar);
        }
        if (selection == 5) {
            life.seed(GameOfLife.pentadecathlon);
        } else {
            System.out.println("Invalid Selection");
            System.exit(0);
        }

        for (int i = 0; i < 10; i++) {
            System.out.println(life);
            life.updateToNextGenerationMT(7);
            sleep(1000);
            clearConsole();
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
