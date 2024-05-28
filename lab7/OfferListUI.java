import java.util.Scanner;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.time.LocalDate;


public class OfferListUI {
    private static void displayOffers(ArrayList<HousingUnit> offers) {
        if (!offers.isEmpty()) {
            System.out.println("--------------------=====--------------------");
            System.out.println("These are all the offers that meet your criteria:");
            offers.forEach((offer) -> {
                System.out.println("\n" + offer);
            });
            System.out.println("--------------------=====--------------------");
            System.out.println();
        }
        else {
            System.out.println("There are no offers that meet your criteria.\n");
        }
    }

    private static void addHouse(Scanner scan, OfferList offers, int houseNumber, String settlement, String street,
                                 String postCode, double area, double price, LocalDate expiryDate) {
        System.out.println("Please enter your house's plot area.");
        double plotArea = scan.nextDouble(); scan.nextLine();
        if (houseNumber > 0 && plotArea > 0 && area > 0) {
            House flatOffer = new House(street, houseNumber, settlement, postCode, area, price, plotArea, expiryDate);
            offers.addOffer(flatOffer);
            System.out.println("Offer for your house was added successfully.\n");
        }
        else {
            System.out.println("Invalid parameters.\n");
        }
    }


    private static void addFlat(Scanner scan, OfferList offers, int houseNumber, String settlement, String street,
                                String postCode, double area, double price, LocalDate expiryDate) {
        System.out.println("Please enter your flat's flat number.");
        int flatNumber = scan.nextInt(); scan.nextLine();
        System.out.println("Please enter your flat's floor (0 for ground floor).");
        int floor = scan.nextInt(); scan.nextLine();
        if (houseNumber > 0 && flatNumber > 0 && area > 0 && floor >= 0) {
            Flat flatOffer = new Flat(street, houseNumber, flatNumber, settlement, postCode, area, floor, price, expiryDate);
            offers.addOffer(flatOffer);
            System.out.println("Offer for your flat was added successfully.");
        }
        else {
            System.out.println("Invalid parameters.");
        }
    }


    private static void handleAdd(Scanner scan, OfferList offers) {
        System.out.println("Please enter the type of your housing unit (house/flat)");
        String type = scan.nextLine().toLowerCase();
        if (!type.equals("house") && !type.equals("flat")) {
            System.out.println("Invalid housing unit type. (Must be \"house\" or \"flat\")\n");
            return;
        }
        System.out.println("Please enter the settlement where your " + type + " is located.");
        String settlement = scan.nextLine();
        System.out.println("Please enter street where your " + type + " is located.");
        String street = scan.nextLine();
        System.out.println("Please enter your " + type + "'s postal code.");
        String postCode = scan.nextLine();
        System.out.println("Please enter your " + type + "'s house number (number of the building on the street).");
        int houseNumber = scan.nextInt(); scan.nextLine();
        System.out.println("Please enter your " + type + "'s area (in square metres).");
        double area = scan.nextDouble(); scan.nextLine();
        System.out.println("Please enter your price offer.");
        double price = scan.nextDouble(); scan.nextLine();
        LocalDate expiryDate = LocalDate.now().plusDays(OfferList.getOfferLifetime());
        if (type.equals("house")) {
            addHouse(scan, offers, houseNumber, settlement, street, postCode, area, price, expiryDate);
        }
        else {
            addFlat(scan, offers, houseNumber, settlement, street, postCode, area, price, expiryDate);
        }
    }

    private static void handleDelete(Scanner scan, OfferList offers) {
        System.out.println("Please input the offer ID to be deleted.");
        Integer id = scan.nextInt();
        scan.nextLine();
        if (offers.deleteOffer(id)) {
            System.out.println("Offer deleted successfully.\n");
        }
        else {
            System.out.println("Offer was not found.\n");
        }
    }


    private static void handleDisplay(Scanner scan, OfferList offers) {
        System.out.println("Please enter the housing unit type (house/flat). Enter anything else to not filter by type.");
        String type = scan.nextLine().strip().toLowerCase();
        Predicate<HousingUnit> matchType = unit -> true;
        if (type.equals("house")) {
            matchType = unit -> unit instanceof House;
        }
        else if (type.equals("flat")) {
            matchType = unit -> unit instanceof Flat;
        }
        Predicate<HousingUnit> matchDate = unit -> unit.getExpiryDate().isBefore(LocalDate.now());
        ArrayList<HousingUnit> filteredOffers = offers.filterOffers(matchType.and(matchDate));
        displayOffers(filteredOffers);
    }


    private static void handleDisplayHousesLocationArea(Scanner scan, OfferList offers) {
        System.out.println("Please enter the settlement where you want to buy a house.");
        String settlement = scan.nextLine().strip().toLowerCase();
        System.out.println("Please enter the area which you want your house's area to be equal or larger than.");
        double area = scan.nextDouble(); scan.nextLine();

        Predicate<HousingUnit> matchType = unit -> unit instanceof House;
        Predicate<HousingUnit> matchSettlement = unit -> unit.getSettlement().toLowerCase().equals(settlement);
        Predicate<HousingUnit> matchArea = unit -> unit.getArea() >= area;
        Predicate<HousingUnit> matchDate = unit -> unit.getExpiryDate().isBefore(LocalDate.now());

        ArrayList<HousingUnit> filteredOffers = offers.filterOffers(matchType.and(matchSettlement).and(matchArea).and(matchDate));
        displayOffers(filteredOffers);
    }


    private static void handleDisplayFlatsLocationPriceFloor(Scanner scan, OfferList offers) {
        System.out.println("Please enter the settlement where you want to buy a house.");
        String settlement = scan.nextLine().strip().toLowerCase();
        System.out.println("Please enter the price which you want your house to be cheaper than.");
        double price = scan.nextDouble(); scan.nextLine();
        System.out.println("Please enter the floor you want your flat to be at, or above.");
        int floor = scan.nextInt(); scan.nextLine();

        Predicate<HousingUnit> matchType = unit -> unit instanceof Flat; // Redundant but much clearer
        Predicate<HousingUnit> matchSettlement = unit -> unit.getSettlement().toLowerCase().equals(settlement);
        Predicate<HousingUnit> matchPrice = unit -> unit.getPrice() <= price;
        Predicate<HousingUnit> matchDate = unit -> unit.getExpiryDate().isBefore(LocalDate.now());
        Predicate<HousingUnit> matchFloor = unit -> {
            if (unit instanceof Flat) {
                return ((Flat) unit).getFloor() >= floor;
            }
            return false;
        };

        ArrayList<HousingUnit> filteredOffers = offers.filterOffers(matchType.and(matchSettlement).and(matchPrice).and(matchFloor).and(matchDate));
        displayOffers(filteredOffers);
    }


    private static void handleAddPlaceholders(OfferList offers) {
        offers.addPlaceholders();
        System.out.println("12 placeholders were added successfully.\n");
    }


    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("The program lets you create a list of house and flat purchase offers and filter them by various parameters.");

        OfferList offers = new OfferList();

        boolean continueLoop = true;
        while (continueLoop) {
            System.out.println("""
                    Options:
                    1. Add offer
                    2. Delete offer
                    3. Display all currently available offers by housing unit type (house/flat)
                    4. Display all currently available house purchase offers in a given settlement and no smaller than a given area
                    5. Display all currently available flat purchase offers in a given settlement, no more expensive than a given price and no lower than a given floor.
                    6. Add 12 placeholder offers for testing.
                    7. Exit.
                    Please type the appropriate number and press Enter.""");
            String userInput = scan.nextLine();
            switch (userInput.trim()) {
                case "7" -> continueLoop = false;

                case "1" -> handleAdd(scan, offers);
                case "2" -> handleDelete(scan, offers);
                case "3" -> handleDisplay(scan, offers);
                case "4" -> handleDisplayHousesLocationArea(scan, offers);
                case "5" -> handleDisplayFlatsLocationPriceFloor(scan, offers);
                case "6" -> handleAddPlaceholders(offers);

                default -> System.out.println("Invalid input.\n");
            }
        }
    }
}