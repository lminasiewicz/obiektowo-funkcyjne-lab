package lab3;

import java.util.Scanner;

public class GradeListUI {

    private static void handleAdd(Scanner scan, GradeList grades) {
        System.out.println("Please enter the grade to be added.");
        float grade = Float.parseFloat(scan.nextLine());
        if (grade > 0) {
            grades.addGrade(grade);
            System.out.println("Grade added successfully.\n");
        }
        else {
            System.out.println("The grade must be a positive number.\n");
        }
    }

    private static void handleAvg(GradeList grades) {
        if (!grades.isEmpty()) {
            System.out.println("Your grade average is " + grades.calculateAverage() + ".\n");
        }
        else {
            System.out.println("The grade list is empty.\n");
        }
    }

    private static void handleMax(GradeList grades) {
        if (!grades.isEmpty()) {
            System.out.println("Your highest grade is " + grades.findMax() + ".\n");
        }
        else {
            System.out.println("The grade list is empty.\n");
        }
    }

    private static void handleMin(GradeList grades) {
        if (!grades.isEmpty()) {
            System.out.println("Your lowest grade is " + grades.findMin() + ".\n");
        }
        else {
            System.out.println("The grade list is empty.\n");
        }
    }

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        System.out.println("The program lets you create a list of grades and perform basic operations such as " +
                "adding new grades, calculating the average, as well as maximum and minimum.");

        GradeList grades = new GradeList();

        boolean continueLoop = true;
        while (continueLoop) {
            System.out.println("""
                    Options:
                    1. Add grade
                    2. Calculate average
                    3. Find highest
                    4. Find lowest
                    5. Exit.
                    Please type the appropriate number and press Enter.""");
            String userInput = scan.nextLine();
            switch (userInput.trim()) {
                case "5" -> continueLoop = false;

                case "1" -> handleAdd(scan, grades);

                case "2" -> handleAvg(grades);

                case "3" -> handleMax(grades);

                case "4" -> handleMin(grades);

                default -> System.out.println("Invalid input.\n");
            }
        }
    }
}