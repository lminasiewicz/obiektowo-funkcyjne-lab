import java.util.Scanner;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.function.Predicate;

public class KalendarzUI {

    private static Integer[] inputSplitToInt(Scanner scan, String splitter) {
        String line = scan.nextLine();
        String[] args = line.split(splitter);
        Integer[] result = new Integer[args.length];
        for (int i = 0; i < args.length; i++) {
            result[i] = Integer.parseInt(args[i]);
        }
        return result;
    }

    private static Priority inputPriority(Scanner scan) {
        String line = scan.nextLine();
        switch (line.trim().toUpperCase()) {
            case "LOW" -> {return Priority.LOW;}
            case "HIGH" -> {return Priority.HIGH;}
            case "HIGHEST" -> {return Priority.HIGHEST;}
            default -> {return Priority.ERROR;}
        }
    }

    private static void displayMeetings(ArrayList<Spotkanie> meetings) {
        if (!meetings.isEmpty()) {
            System.out.println("--------------------=====--------------------");
            System.out.println("Here are the meetings for your desired weekday:");
            meetings.forEach((meeting) -> System.out.println("\n" + meeting));
            System.out.println("--------------------=====--------------------");
            System.out.println();
        }
        else {
            System.out.println("The grade list is empty.\n");
        }
    }

    private static void handleAdd(Scanner scan, Kalendarz calendar) {
        System.out.println("Please enter the weekday (1-7)");
        int day = scan.nextInt() - 1;
        System.out.println("Please enter the meeting start time (format HH:MM)");
        scan.nextLine();
        Integer[] startTimeArgs = inputSplitToInt(scan, ":");
        System.out.println("Please enter the meeting end time (format HH:MM)");
        Integer[] endTimeArgs = inputSplitToInt(scan, ":");
        System.out.println("Please enter the priority (LOW, HIGH, HIGHEST)");
        Priority priority = inputPriority(scan);
        System.out.println("Please name your meeting, or give it a short description.");
        String desc = scan.nextLine();
        int startHour = startTimeArgs[0];
        int startMinute = startTimeArgs[1];
        int endHour = endTimeArgs[0];
        int endMinute = endTimeArgs[1];

        if (startHour >= 0 && startMinute >= 0 && endHour >= 0 && endMinute >= 0 &&
        startHour < 24 && startMinute < 60 && endHour < 24 && endMinute < 60 && day >= 0 && day <= 6
                && priority != Priority.ERROR) {
            if (startHour < Spotkanie.getEarliestTime().getHour() || (startHour ==  Spotkanie.getEarliestTime().getHour()
                    && startMinute < Spotkanie.getEarliestTime().getMinute())) {
                System.out.println("Meeting starts before earliest allowed starting time. Please try again.\n");
            }
            else {
                LocalDate meetingDate = calendar.getStartDate().plusDays(day);
                Spotkanie meeting = new Spotkanie(meetingDate.getDayOfMonth(), meetingDate.getMonthValue(),
                        meetingDate.getYear(), startHour, startMinute, endHour, endMinute, priority, desc, day);
                calendar.addMeeting(meeting);
                System.out.println("Meeting added successfully.\n");
            }
        }
        else {
            System.out.println("Invalid arguments. Please try again.\n");
        }
    }

    private static void handleDel(Scanner scan, Kalendarz calendar) {
        System.out.println("Please input the meeting ID to be deleted.");
        int id = scan.nextInt();
        scan.nextLine();
        calendar.deleteMeeting(id);
        System.out.println("Meeting deleted successfully.\n");
    }

    private static void handleDisplay(Scanner scan, Kalendarz calendar) {
        System.out.println("Please enter the weekday (1-7) you wish to display meetings for.");
        int day = scan.nextInt() - 1;
        scan.nextLine();
        if (day >= 0 && day <= 6) {
            Predicate<Spotkanie> matchWeekday = meeting -> meeting.getWeekday() == day;
            ArrayList<Spotkanie> meetings = calendar.filterMeetings(matchWeekday);
            displayMeetings(meetings);
        }
        else {
            System.out.println("Invalid weekday.\n");
        }
    }

    private static void handleDisplayPriority(Scanner scan, Kalendarz calendar) {
        System.out.println("Please enter the weekday (1-7) you wish to display meetings for.");
        int day = scan.nextInt() - 1;
        scan.nextLine();
        System.out.println("Please enter the priority (LOW, HIGH, HIGHEST) you wish to display meetings for.");
        Priority priority = inputPriority(scan);
        if (day >= 0 && day <= 6 && priority != Priority.ERROR) {
            Predicate<Spotkanie> matchWeekday = meeting -> meeting.getWeekday() == day;
            Predicate<Spotkanie> matchPriority = meeting -> meeting.getPriority() == priority;
            ArrayList<Spotkanie> meetings = calendar.filterMeetings(matchWeekday.and(matchPriority));
            displayMeetings(meetings);
        }
        else {
            System.out.println("Invalid weekday or priority.\n");
        }
    }

    private static void handleDisplayAfter(Scanner scan, Kalendarz calendar) {
        System.out.println("Please enter the weekday (1-7) you wish to display meetings for.");
        int day = scan.nextInt() - 1;
        scan.nextLine();
        System.out.println("Please enter the hour (format: HH:MM) after which you wish to display meetings for.");
        Integer[] afterArgs = inputSplitToInt(scan, ":");
        if (day >= 0 && day <= 6 && afterArgs[0] >= 0 && afterArgs[1] >= 0 && afterArgs[0] < 24 && afterArgs[1] < 60) {

            Predicate<Spotkanie> matchWeekday = meeting -> meeting.getWeekday() == day;
            Predicate<Spotkanie> afterTime = meeting -> {

                if (meeting.getStartTime().getHour() == afterArgs[0]) {
                    return meeting.getStartTime().getMinute() >= afterArgs[1];
                }
                return meeting.getStartTime().getHour() >= afterArgs[0];
            };

            ArrayList<Spotkanie> meetings = calendar.filterMeetings(matchWeekday.and(afterTime));
            displayMeetings(meetings);
        }
        else {
            System.out.println("Invalid weekday or time constraint.\n");
        }
    }

    private static void handleDisplayBetween(Scanner scan, Kalendarz calendar) {
        System.out.println("Please enter the weekday (1-7) you wish to display meetings for.");
        int day = scan.nextInt() - 1;
        scan.nextLine();
        System.out.println("Please enter the hour (format: HH:MM) AFTER which you wish to display meetings for.");
        Integer[] afterArgs = inputSplitToInt(scan, ":");
        System.out.println("Please enter the hour (format: HH:MM) BEFORE which you wish to display meetings for.");
        Integer[] beforeArgs = inputSplitToInt(scan, ":");
        if (day >= 0 && day <= 6) {
            if ((beforeArgs[0] >= 0 && beforeArgs[1] >= 0 && beforeArgs[0] < 24 && beforeArgs[1] < 60) &&
                    afterArgs[0] >= 0 && afterArgs[1] >= 0 && afterArgs[0] < 24 && afterArgs[1] < 60) {

                Predicate<Spotkanie> matchWeekday = meeting -> meeting.getWeekday() == day;

                Predicate<Spotkanie> afterTime = meeting -> {
                    if (meeting.getStartTime().getHour() == afterArgs[0]) {
                        return meeting.getStartTime().getMinute() >= afterArgs[1];
                    }
                    return meeting.getStartTime().getHour() >= afterArgs[0];
                };

                Predicate<Spotkanie> beforeTime = meeting -> {
                    if (meeting.getEndTime().getHour() == beforeArgs[0]) {
                        return meeting.getEndTime().getMinute() <= beforeArgs[1];
                    }
                    return meeting.getEndTime().getHour() <= beforeArgs[0];
                };

                ArrayList<Spotkanie> meetings = calendar.filterMeetings(matchWeekday.and(beforeTime).and(afterTime));
                displayMeetings(meetings);
            }
            else {
                System.out.println("Invalid timeframe.\n");
            }
        }
        else {
            System.out.println("Invalid weekday.\n");
        }
    }

    private static void handleDisplayPriorityAfter(Scanner scan, Kalendarz calendar) {
        System.out.println("Please enter the weekday (1-7) you wish to display meetings for.");
        int day = scan.nextInt() - 1;
        scan.nextLine();
        System.out.println("Please enter the priority (LOW, HIGH, HIGHEST) you wish to display meetings for.");
        Priority priority = inputPriority(scan);
        System.out.println("Please enter the hour (format: HH:MM) after which you wish to display meetings for.");
        Integer[] afterArgs = inputSplitToInt(scan, ":");
        if (day >= 0 && day <= 6 && priority != Priority.ERROR) {
            if (afterArgs[0] >= 0 && afterArgs[1] >= 0 && afterArgs[0] < 24 && afterArgs[1] < 60) {
                Predicate<Spotkanie> matchWeekday = meeting -> meeting.getWeekday() == day;
                Predicate<Spotkanie> matchPriority = meeting -> meeting.getPriority() == priority;
                Predicate<Spotkanie> afterTime = meeting -> {
                    if (meeting.getStartTime().getHour() == afterArgs[0]) {
                        return meeting.getStartTime().getMinute() >= afterArgs[1];
                    }
                    return meeting.getStartTime().getHour() >= afterArgs[0];
                };
                ArrayList<Spotkanie> meetings = calendar.filterMeetings(matchWeekday.and(matchPriority).and(afterTime));
                displayMeetings(meetings);
            }
            else {
                System.out.println("Invalid time contraint.\n");
            }
        }
        else {
            System.out.println("Invalid weekday or priority.\n");
        }
    }

    private static void handleAddSeven(Kalendarz calendar) {
        calendar.addSevenPlaceholders();
        System.out.println("Added 7 placeholders.\n");
    }


    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        System.out.println("The program lets you create a list of meetings for the coming week, and lets you " +
                "create, delete, and filter them.");

        Kalendarz calendar = new Kalendarz();

        boolean continueLoop = true;
        while (continueLoop) {
            System.out.println("""
                    Options:
                    1. Add meeting
                    2. Delete meeting by ID
                    3. Display all meetings by weekday (1-7)
                    4. Display all meetings of a given priority by weekday (1-7)
                    5. Display all meetings after a given hour by weekday (1-7)
                    6. Display all meetings between two given hours by weekday (1-7)
                    7. Display all meetings of a given priority before a given hour by weekday (1-7)
                    8. Add 7 placeholder meetings.
                    9. Exit.
                    Please type the appropriate number and press Enter.""");
            String userInput = scan.nextLine();
            switch (userInput.trim()) {
                case "9" -> continueLoop = false;
                case "1" -> handleAdd(scan, calendar);
                case "2" -> handleDel(scan, calendar);
                case "3" -> handleDisplay(scan, calendar);
                case "4" -> handleDisplayPriority(scan, calendar);
                case "5" -> handleDisplayAfter(scan, calendar);
                case "6" -> handleDisplayBetween(scan, calendar);
                case "7" -> handleDisplayPriorityAfter(scan, calendar);
                case "8" -> handleAddSeven(calendar);

                default -> System.out.println("Invalid input.\n");
            }
        }
    }
}
