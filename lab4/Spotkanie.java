import java.time.LocalDate;
import java.time.LocalTime;

public class Spotkanie {
    private static final LocalTime earliestTime = LocalTime.of(7, 30);
    private Integer id;
    private final String name;
    private final LocalDate date;
    private final LocalTime startTime;
    private final LocalTime endTime;
    private final Priority priority;

    public Spotkanie(int day, int month, int year, int startHour, int startMinute, int endHour,
                     int endMinute, Priority priority, String name) {
        this.name = name;
        this.date = LocalDate.of(year, month, day);
        this.startTime = LocalTime.of(startHour, startMinute);
        this.endTime = LocalTime.of(endHour, endMinute);
        this.priority = priority;
    }

    public Spotkanie() {
        // PLACEHOLDER
        this.name = "PLACEHOLDER";
        this.date = LocalDate.of(0, 1, 1);
        this.startTime = LocalTime.of(0, 0);
        this.endTime = LocalTime.of(0, 0);
        this.priority = Priority.LOW;
        this.id = -1;
    }

    public static LocalTime getEarliestTime() {
        return earliestTime;
    }

    public LocalTime getStartTime() {
        return this.startTime;
    }

    public String getName() {
        return this.name;
    }

    public LocalTime getEndTime() {
        return this.endTime;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public Priority getPriority() {
        return this.priority;
    }

    public int getId() {
        return this.id;
    }

    void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        String data = this.date.getDayOfMonth() + "." + this.date.getMonth() + "." + this.date.getYear() +
                " at " + this.startTime.getHour() + ":" + this.startTime.getMinute() + " - " +
                this.endTime.getHour() + ":" + this.endTime.getMinute() + "\nNazwa: " + this.name;
        if (this.id != null) {
            return "Spotkanie ID:" + this.id + "\non " + data + "\nPRIORITY: " + this.getPriority().name();
        }
        else {
            return "Spotkanie" + data + "\nPRIORITY: " + this.getPriority().name();
        }
    }

    public boolean equals(Spotkanie obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (obj.id == null) return false;
        return obj.id == this.id;
    }
}
