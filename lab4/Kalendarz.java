import java.time.LocalDate;
import java.util.ArrayList;
import java.util.function.Predicate;

public class Kalendarz {
    private final ArrayList<Spotkanie>[] meetings;
    private int nextId;
    private final LocalDate START_DATE;

    public Kalendarz() {
        this.nextId = 0;
        this.meetings = new ArrayList[7];
        for (int i = 0; i < 7; i++) {
            this.meetings[i] = new ArrayList<Spotkanie>();
        }
        this.START_DATE = LocalDate.now();
    }

    public LocalDate getStartDate() {
        return this.START_DATE;
    }

    public void addMeeting(Spotkanie meeting) {
        if (meeting.getWeekday() >= 0 && meeting.getWeekday() <= 6) {
            meeting.setId(nextId);
            this.nextId += 1;
            this.meetings[meeting.getWeekday()].add(meeting);
        }
    }

    public void deleteMeeting(int id) {
        Spotkanie placeholder = new Spotkanie();
        placeholder.setId(id);
        for (int weekday = 0; weekday < 7; weekday++) {
            int finalWeekday = weekday;
            this.meetings[finalWeekday].remove(placeholder);
        }
    }

    public ArrayList<Spotkanie> filterMeetings(Predicate<Spotkanie> predicate) {
        ArrayList<Spotkanie> result = new ArrayList<Spotkanie>();
        for (int weekday = 0; weekday < 7; weekday++) {
            this.meetings[weekday].forEach((meeting) -> {
                if (predicate.test(meeting)) {
                    result.add(meeting);
                }
            });
        }
        return result;
    }


    public void addSevenPlaceholders() {
        this.addMeeting(new Spotkanie(25, 4, 2024, 8, 30, 9, 30, Priority.HIGH, "Test1", 0));
        this.addMeeting(new Spotkanie(25, 4, 2024, 9, 30, 10, 30, Priority.LOW, "Test2", 0));
        this.addMeeting(new Spotkanie(25, 4, 2024, 11, 00, 12, 00, Priority.LOW, "Test3", 0));
        this.addMeeting(new Spotkanie(25, 4, 2024, 15, 30, 16, 00, Priority.HIGHEST, "Test4", 0));
        this.addMeeting(new Spotkanie(25, 4, 2024, 14, 30, 15, 30, Priority.HIGH, "Test5", 0));
        this.addMeeting(new Spotkanie(25, 4, 2024, 8, 20, 9, 20, Priority.LOW, "Test6", 0));
        this.addMeeting(new Spotkanie(25, 4, 2024, 11, 30, 12, 10, Priority.HIGHEST, "Test7", 0));
    }
}
