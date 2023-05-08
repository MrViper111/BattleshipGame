package dev.mrviper111.utils;

import java.util.Scanner;

public class InputHandler {

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

}
