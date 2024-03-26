package lab1;

import java.util.Scanner;

public class CalcUI {
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        System.out.println("The program can calculate the factorial of the number n, or the sum of integers <a; b>.");

        boolean continueLoop = true;
        while (continueLoop) {
            System.out.println("Enter \"Factorial\" / \"f\" to calculate factorial, \"Sum\" / \"s\" " +
                    "to calculate sum, or \"Exit\" / \"e\" to exit");
            String userInput = scan.nextLine();
            switch (userInput.toLowerCase()) {
                case "e", "exit" -> continueLoop = false;
                case "f", "factorial" -> {
                    System.out.println("Please enter the number n:");
                    Integer n = scan.nextInt();
                    scan.nextLine();
                    System.out.println(n + "! = " + Calculations.calculateFactorial(n) + "\n");
                }
                case "s", "sum" -> {
                    System.out.println("Please enter the numbers a and b (separated by space):");
                    int[] bounds = new int[2];
                    for (int i = 0; i < bounds.length; i++) {
                        bounds[i] = scan.nextInt();
                    }
                    scan.nextLine();
                    System.out.println("Sum from " + bounds[0] + " to " + bounds[1] +
                            " = " + Calculations.calculateSum(bounds[0], bounds[1]) + "\n");
                }
                default -> System.out.println("Invalid input.\n");
            }
        }
    }
}
