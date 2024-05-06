import java.time.LocalDate;
import java.util.ArrayList;
import java.time.LocalTime;
import java.time.LocalDate;

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

    public void addMeeting(int weekday, Spotkanie meeting) {
        if (weekday >= 0 && weekday <= 6) {
            meeting.setId(nextId);
            this.nextId += 1;
            this.meetings[weekday].add(meeting);
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

    public ArrayList<Spotkanie> getMeetingsByDay(int weekday) {
        return new ArrayList<Spotkanie>(this.meetings[weekday]);
    }

    public ArrayList<Spotkanie> getMeetingsByDayPriority(int weekday, Priority priority) {
        ArrayList<Spotkanie> result = new ArrayList<Spotkanie>();
        this.meetings[weekday].forEach((meeting) -> {
           if (meeting.getPriority() == priority) {
               result.add(meeting);
           }
        });
        return result;
    }

    public ArrayList<Spotkanie> getMeetingsByDayBefore(int weekday, int hour, int minute) {
        ArrayList<Spotkanie> result = new ArrayList<Spotkanie>();
        LocalTime threshold = LocalTime.of(hour, minute);
        this.meetings[weekday].forEach((meeting) -> {
            if (meeting.getStartTime().isBefore(threshold)) {
                result.add(meeting);
            }
        });
        return result;
    }

    public void addSevenPlaceholders() {
        this.addMeeting(1, new Spotkanie(25, 4, 2024, 8, 30, 9, 30, Priority.HIGH, "Test1"));
        this.addMeeting(1, new Spotkanie(25, 4, 2024, 9, 30, 10, 30, Priority.LOW, "Test2"));
        this.addMeeting(1, new Spotkanie(25, 4, 2024, 11, 00, 12, 00, Priority.LOW, "Test3"));
        this.addMeeting(1, new Spotkanie(25, 4, 2024, 15, 30, 16, 00, Priority.HIGHEST, "Test4"));
        this.addMeeting(1, new Spotkanie(25, 4, 2024, 14, 30, 15, 30, Priority.HIGH, "Test5"));
        this.addMeeting(1, new Spotkanie(25, 4, 2024, 8, 20, 9, 20, Priority.LOW, "Test6"));
        this.addMeeting(1, new Spotkanie(25, 4, 2024, 11, 30, 12, 10, Priority.HIGHEST, "Test7"));
    }
}
