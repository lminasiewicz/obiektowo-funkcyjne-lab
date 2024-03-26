package lab1;

import java.util.Objects;
import java.util.Scanner;

public class Silnia {
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        System.out.println("The program calculates the factorial of the number n. You may enter a new n anytime you like." +
                "\nEnter \"T\" to terminate program. Please enter n:");

        boolean continueLoop = true;
        while (continueLoop) {
            String userInput = scan.nextLine();
            if (Objects.equals(userInput, "T")) {
                continueLoop = false;
            }
            else {
                Integer n = Integer.parseInt(userInput);
                System.out.println(n + "! = " + calculateFactorial(n));
            }
        }
    }

    static long calculateFactorial(Integer n) {
        long result = 1;
        for (int i = 1; i < n+1; i++) {
            result *= i;
        }
        return result;
    }
}
