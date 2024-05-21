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

    private static Status inputStatus(Scanner scan) {
        String line = scan.nextLine();
        switch (line.trim().toUpperCase()) {
            case "PLANNED" -> { return Status.PLANNED; }
            case "CONFIRMED" -> { return Status.CONFIRMED; }
            case "IN_PROGRESS" -> { return Status.IN_PROGRESS; }
            case "FINISHED" -> { return Status.FINISHED; }
            default -> { return Status.ERROR; }
        }
    }

    private static void addMeeting(Scanner scan, Kalendarz calendar, LocalDate meetingDate, int startHour, int startMinute,
                              int endHour, int endMinute, String desc, int day) {
        System.out.println("Please enter the event priority (LOW, HIGH, HIGHEST)");
        Priority priority = inputPriority(scan);
        Spotkanie meeting = new Spotkanie(meetingDate.getDayOfMonth(), meetingDate.getMonthValue(),
                meetingDate.getYear(), startHour, startMinute, endHour, endMinute, priority, desc, day);
        calendar.addEvent(meeting);
        System.out.println("Meeting added successfully.\n");
    }

    private static void addTask(Scanner scan, Kalendarz calendar, LocalDate taskDate, int startHour, int startMinute,
                                   int endHour, int endMinute, String desc, int day) {
        System.out.println("Please enter the task status (PLANNED, CONFIRMED, IN_PROGRESS, FINISHED)");
        Status status = inputStatus(scan);
        Zadanie task = new Zadanie(taskDate.getDayOfMonth(), taskDate.getMonthValue(),
                taskDate.getYear(), startHour, startMinute, endHour, endMinute, status, desc, day);
        calendar.addEvent(task);
        System.out.println("Meeting added successfully.\n");
    }

    private static void displayEvents(ArrayList<Wydarzenie> events) {
        if (!events.isEmpty()) {
            System.out.println("--------------------=====--------------------");
            System.out.println("Here are the tasks for your desired weekday:");
            events.forEach((event) -> {
                System.out.println("\n" + event);
            });
            System.out.println("--------------------=====--------------------");
            System.out.println();
        }
        else {
            System.out.println("The events list is empty.\n");
        }
    }

    private static void handleAdd(Scanner scan, Kalendarz calendar) {
        System.out.println("Please enter the weekday (1-7)");
        int day = scan.nextInt() - 1;
        System.out.println("Please enter the event type (meeting/task)");
        scan.nextLine();
        String eventType = scan.nextLine();
        eventType = eventType.toLowerCase();
        System.out.println("Please enter the event start time (format HH:MM)");
        Integer[] startTimeArgs = inputSplitToInt(scan, ":");
        System.out.println("Please enter the event end time (format HH:MM)");
        Integer[] endTimeArgs = inputSplitToInt(scan, ":");
        System.out.println("Please give your event a short description.");
        String desc = scan.nextLine();
        int startHour = startTimeArgs[0];
        int startMinute = startTimeArgs[1];
        int endHour = endTimeArgs[0];
        int endMinute = endTimeArgs[1];

        if (startHour >= 0 && startMinute >= 0 && endHour >= 0 && endMinute >= 0 &&
        startHour < 24 && startMinute < 60 && endHour < 24 && endMinute < 60 && day >= 0 && day <= 6) {

            if (startHour < Wydarzenie.getEarliestTime().getHour() || (startHour == Wydarzenie.getEarliestTime().getHour()
                    && startMinute < Wydarzenie.getEarliestTime().getMinute())) {
                System.out.println("Event starts before earliest allowed starting time. Please try again.\n");
            }
            else {
                LocalDate meetingDate = calendar.getStartDate().plusDays(day);
                if (eventType.equals("meeting")) {
                    addMeeting(scan, calendar, meetingDate, startHour, startMinute, endHour, endMinute, desc, day);
                }
                else if (eventType.equals("task")) {
                    addTask(scan, calendar, meetingDate, startHour, startMinute, endHour, endMinute, desc, day);
                }
                else {
                    System.out.println("Meeting type must be \"meeting\" or \"task\"\n.");
                }
            }
        }
        else {
            System.out.println("Invalid arguments. Please try again.\n");
        }
    }

    private static void handleDel(Scanner scan, Kalendarz calendar) {
        System.out.println("Please input the event ID to be deleted.");
        int id = scan.nextInt();
        scan.nextLine();
        if (calendar.deleteEvent(id)) {
            System.out.println("Meeting deleted successfully.\n");
        }
        else {
            System.out.println("Meeting not found.\n");
        }
    }

    private static void handleDisplay(Scanner scan, Kalendarz calendar) {
        System.out.println("Please enter the weekday (1-7) you wish to display meetings for.");
        int day = scan.nextInt() - 1;
        scan.nextLine();
        System.out.println("Please enter the meeting type (meeting/task). Enter anything else to not filter by meeting type.");
        String eventType = scan.nextLine().strip().toLowerCase();

        if (day >= 0 && day <= 6) {
            Predicate<Wydarzenie> matchType = event -> true;
            if (eventType.equals("meeting")) {
                matchType = meeting -> meeting instanceof Spotkanie;
            }
            else if (eventType.equals("task")) {
                matchType = task -> task instanceof Zadanie;
            }
            Predicate<Wydarzenie> matchWeekday = event -> event.getWeekday() == day;
            ArrayList<Wydarzenie> meetings = calendar.filterEvents(matchWeekday.and(matchType));
            displayEvents(meetings);
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
            Predicate<Wydarzenie> matchWeekday = meeting -> meeting.getWeekday() == day;
            Predicate<Wydarzenie> matchPriority = meeting -> {
                if (meeting instanceof Spotkanie) {
                    return ((Spotkanie) meeting).getPriority() == priority;
                }
                else {
                    return false;
                }
            };
            ArrayList<Wydarzenie> meetings = calendar.filterEvents(matchWeekday.and(matchPriority));
            displayEvents(meetings);
        }
        else {
            System.out.println("Invalid weekday or priority.\n");
        }
    }

    private static void handleDisplayStatus(Scanner scan, Kalendarz calendar) {
        System.out.println("Please enter the weekday (1-7) you wish to display meetings for.");
        int day = scan.nextInt() - 1;
        scan.nextLine();
        System.out.println("Please enter the task status (PLANNED, CONFIRMED, IN_PROGRESS, FINISHED)");
        Status status = inputStatus(scan);
        if (day >= 0 && day <= 6 && status != Status.ERROR) {
            Predicate<Wydarzenie> matchWeekday = task -> task.getWeekday() == day;
            Predicate<Wydarzenie> matchStatus = task -> {
                if (task instanceof Zadanie) {
                    return ((Zadanie) task).getStatus() == status;
                }
                else {
                    return false;
                }
            };
            ArrayList<Wydarzenie> tasks = calendar.filterEvents(matchWeekday.and(matchStatus));
            displayEvents(tasks);
        }
        else {
            System.out.println("Invalid weekday or priority.\n");
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
                Predicate<Wydarzenie> matchWeekday = meeting -> meeting.getWeekday() == day;
                Predicate<Wydarzenie> matchPriority = meeting -> {
                    if (meeting instanceof Spotkanie) {
                        return ((Spotkanie) meeting).getPriority() == priority;
                    }
                    else {
                        return false;
                    }
                };
                Predicate<Wydarzenie> afterTime = meeting -> {
                    if (meeting.getStartTime().getHour() == afterArgs[0]) {
                        return meeting.getStartTime().getMinute() >= afterArgs[1];
                    }
                    return meeting.getStartTime().getHour() >= afterArgs[0];
                };
                ArrayList<Wydarzenie> meetings = calendar.filterEvents(matchWeekday.and(matchPriority).and(afterTime));
                displayEvents(meetings);
            }
            else {
                System.out.println("Invalid time constraint.\n");
            }
        }
        else {
            System.out.println("Invalid weekday or priority.\n");
        }
    }

    private static void handleDisplayStatusBefore(Scanner scan, Kalendarz calendar) {
        System.out.println("Please enter the weekday (1-7) you wish to display meetings for.");
        int day = scan.nextInt() - 1;
        scan.nextLine();
        System.out.println("Please enter the task status (PLANNED, CONFIRMED, IN_PROGRESS, FINISHED)");
        Status status = inputStatus(scan);
        System.out.println("Please enter the hour (format: HH:MM) after which you wish to display meetings for.");
        Integer[] beforeArgs = inputSplitToInt(scan, ":");
        if (day >= 0 && day <= 6 && status != Status.ERROR) {
            if (beforeArgs[0] >= 0 && beforeArgs[1] >= 0 && beforeArgs[0] < 24 && beforeArgs[1] < 60) {
                Predicate<Wydarzenie> matchWeekday = task -> task.getWeekday() == day;
                Predicate<Wydarzenie> matchPriority = task -> {
                    if (task instanceof Zadanie) {
                        return ((Zadanie) task).getStatus() == status;
                    }
                    else {
                        return false;
                    }
                };
                Predicate<Wydarzenie> afterTime = task -> {
                    if (task.getEndTime().getHour() == beforeArgs[0]) {
                        return task.getEndTime().getMinute() <= beforeArgs[1];
                    }
                    return task.getEndTime().getHour() <= beforeArgs[0];
                };
                ArrayList<Wydarzenie> tasks = calendar.filterEvents(matchWeekday.and(matchPriority).and(afterTime));
                displayEvents(tasks);
            }
            else {
                System.out.println("Invalid time constraint.\n");
            }
        }
        else {
            System.out.println("Invalid weekday or priority.\n");
        }
    }

    private static void handleAddPlaceholders(Kalendarz calendar) {
        calendar.addPlaceholders();
        System.out.println("Added placeholders.\n");
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
                    1. Add event
                    2. Delete event by ID
                    3. Display all events by type of event (meeting/task) and weekday (1-7)
                    4. Display all meetings of a given priority by weekday (1-7)
                    5. Display all tasks of a given status by weekday (1-7)
                    6. Display all meetings of a given priority before a given hour by weekday (1-7)
                    7. Display all tasks of a given status after a given hour by weekday (1-7)
                    8. Add 12 placeholder events.
                    9. Exit.
                    Please type the appropriate number and press Enter.""");
            String userInput = scan.nextLine();
            switch (userInput.trim()) {
                case "9" -> continueLoop = false;
                case "1" -> handleAdd(scan, calendar);
                case "2" -> handleDel(scan, calendar);
                case "3" -> handleDisplay(scan, calendar);
                case "4" -> handleDisplayPriority(scan, calendar);
                case "5" -> handleDisplayStatus(scan, calendar);
                case "6" -> handleDisplayPriorityAfter(scan, calendar);
                case "7" -> handleDisplayStatusBefore(scan, calendar);
                case "8" -> handleAddPlaceholders(calendar);

                default -> System.out.println("Invalid input.\n");
            }
        }
    }
}
