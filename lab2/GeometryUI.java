package lab2;

import java.util.Scanner;

public class GeometryUI {

    private static void handleParamInputs(Scanner scan, Walec cylinder) {
        System.out.println("Please enter the radius of the base of " +
                "the cylinder.");
        double radius = Double.parseDouble(scan.nextLine());
        System.out.println("Please enter the height of the cylinder.");
        double height = Double.parseDouble(scan.nextLine());

        if (radius > 0 && height > 0) {
            cylinder.setRadius(radius);
            cylinder.setHeight(height);
            System.out.println("Parameters set successfully.\n");
        }
        else {
            System.out.println("Invalid parameters. Both parameters must be real numbers larger than 0.\n");
        }
    }

    private static void handleAreas(Walec cylinder) {
        if (cylinder.getHeight() > 0 && cylinder.getRadius() > 0) {
            System.out.println("Base Area: " + cylinder.baseArea());
            System.out.println("Side Area: " + cylinder.sideArea());
            System.out.println("Total Area: " + cylinder.totalArea() + "\n");
        }
        else {
            System.out.println("Please set the parameters of the cylinder first.\n");
        }
    }

    private static void handleVolume(Walec cylinder) {
        if (cylinder.getHeight() > 0 && cylinder.getRadius() > 0) {
            System.out.println("Volume: " + cylinder.volume() + "\n");
        }
        else {
            System.out.println("Please set the parameters of the cylinder first.\n");
        }
    }

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        System.out.println("The program lets you create a cylinder with custom parameters " +
                "and calculate its areas and volume.");

        Walec cylinder = new Walec();

        boolean continueLoop = true;
        while (continueLoop) {
            System.out.println("""
                    Options:
                    1. Set parameters of the cylinder
                    2. Calculate areas
                    3. Calculate volume
                    4. Exit.
                    Please type the appropriate number and press Enter.""");
            String userInput = scan.nextLine();
            switch (userInput.trim()) {
                case "4" -> continueLoop = false;

                case "1" -> handleParamInputs(scan, cylinder);

                case "2" -> handleAreas(cylinder);

                case "3" -> handleVolume(cylinder);

                default -> System.out.println("Invalid input.\n");
            }
        }
    }
}