package dev.mrviper111.utils;

import java.util.Scanner;
import java.io.Console;

public class CLIHandler {

    public static String promptString(String message) {
        Scanner input = new Scanner(System.in);
        System.out.print(message);

        return input.next();
    }

    public static int promptInt(String message) {
        Scanner input = new Scanner(System.in);
        System.out.print(message);

        return input.nextInt();
    }

    public static void clear() {
        for (int i = 0; i < 100; i++) {
            System.out.println();
        }
    }

}
