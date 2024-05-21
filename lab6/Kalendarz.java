import java.time.LocalDate;
import java.util.ArrayList;
import java.util.function.Predicate;

public class Kalendarz {
    private final ArrayList<Wydarzenie>[] events;
    private int nextId;
    private final LocalDate START_DATE;

    public Kalendarz() {
        this.nextId = 0;
        this.events = new ArrayList[7];
        for (int i = 0; i < 7; i++) {
            this.events[i] = new ArrayList<>();
        }
        this.START_DATE = LocalDate.now();
    }

    public LocalDate getStartDate() {
        return this.START_DATE;
    }

    public void addEvent(Wydarzenie event) {
        if (event.getWeekday() >= 0 && event.getWeekday() <= 6) {
            event.setId(nextId);
            this.nextId += 1;
            this.events[event.getWeekday()].add(event);
        }
    }

    public boolean deleteEvent(int id) {
        Spotkanie placeholder_m = new Spotkanie();
        Zadanie placeholder_t = new Zadanie();
        placeholder_m.setId(id);
        placeholder_t.setId(id);
        boolean removed = false;
        for (int weekday = 0; weekday < 7; weekday++) {
            int finalWeekday = weekday;
            if (this.events[finalWeekday].remove(placeholder_m)) removed = true;
            if (this.events[finalWeekday].remove(placeholder_t)) removed = true;
        }
        return removed;
    }

    public ArrayList<Wydarzenie> filterEvents(Predicate<Wydarzenie> predicate) {
        ArrayList<Wydarzenie> result = new ArrayList<>();
        for (int weekday = 0; weekday < 7; weekday++) {
            this.events[weekday].forEach((event) -> {
                if (predicate.test(event)) {
                    result.add(event);
                }
            });
        }
        return result;
    }


    public void addPlaceholders() {
        this.addEvent(new Spotkanie(25, 4, 2024, 8, 30, 9, 30, Priority.HIGH, "Test1", 0));
        this.addEvent(new Spotkanie(25, 4, 2024, 9, 30, 10, 30, Priority.LOW, "Test2", 0));
        this.addEvent(new Spotkanie(25, 4, 2024, 11, 00, 12, 00, Priority.LOW, "Test3", 0));
        this.addEvent(new Spotkanie(25, 4, 2024, 15, 30, 16, 00, Priority.HIGHEST, "Test4", 0));
        this.addEvent(new Spotkanie(25, 4, 2024, 14, 30, 15, 30, Priority.HIGH, "Test5", 0));
        this.addEvent(new Zadanie(25, 4, 2024, 8, 20, 9, 20, Status.CONFIRMED, "Test6", 0));
        this.addEvent(new Zadanie(25, 4, 2024, 11, 30, 12, 10, Status.PLANNED, "Test7", 0));
        this.addEvent(new Zadanie(25, 4, 2024, 13, 00, 14, 00, Status.IN_PROGRESS, "Test8", 0));
        this.addEvent(new Zadanie(25, 4, 2024, 12, 30, 14, 00, Status.FINISHED, "Test9", 0));
        this.addEvent(new Zadanie(25, 4, 2024, 18, 30, 19, 30, Status.CONFIRMED, "Test10", 0));
        this.addEvent(new Zadanie(25, 4, 2024, 20, 20, 21, 20, Status.PLANNED, "Test11", 0));
        this.addEvent(new Zadanie(25, 4, 2024, 10, 00, 12, 20, Status.FINISHED, "Test12", 0));
    }
}
