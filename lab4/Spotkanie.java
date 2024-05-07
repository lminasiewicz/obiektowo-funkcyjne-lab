import java.time.LocalDate;
import java.time.LocalTime;

public class Spotkanie {
    private static final LocalTime EARLIEST_TIME = LocalTime.of(7, 30);
    private Integer id;
    private String name;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private Priority priority;
    private int weekday;

    public Spotkanie(int day, int month, int year, int startHour, int startMinute, int endHour,
                     int endMinute, Priority priority, String name, int weekday) {
        this.name = name;
        this.date = LocalDate.of(year, month, day);
        this.startTime = LocalTime.of(startHour, startMinute);
        this.endTime = LocalTime.of(endHour, endMinute);
        this.priority = priority;
        this.weekday = weekday;
    }

    public Spotkanie() {
        // PLACEHOLDER
        this.name = "PLACEHOLDER";
        this.date = LocalDate.of(0, 1, 1);
        this.startTime = LocalTime.of(0, 0);
        this.endTime = LocalTime.of(0, 0);
        this.priority = Priority.LOW;
        this.weekday = -1;
        this.id = -1;
    }

    public static LocalTime getEarliestTime() {
        return EARLIEST_TIME;
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

    public int getWeekday() { return this.weekday; }

    @Override
    public String toString() {
        String startMinute = String.valueOf(this.startTime.getMinute());
        String endMinute = String.valueOf(this.endTime.getMinute());
        if (startMinute.equals("0")) {
            startMinute = "00";
        }
        if (endMinute.equals("0")) {
            endMinute = "00";
        }
        String data = this.date.getDayOfMonth() + "." + this.date.getMonth() + "." + this.date.getYear() +
                " at " + this.startTime.getHour() + ":" + startMinute + " - " +
                this.endTime.getHour() + ":" + endMinute + "\nNazwa: " + this.name;
        if (this.id != null) {
            return "Spotkanie ID:" + this.id + "\non " + data + "\nPRIORITY: " + this.priority.name();
        }
        else {
            return "Spotkanie" + data + "\nPRIORITY: " + this.priority.name();
        }
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (obj instanceof Spotkanie && ((Spotkanie) obj).id == null) return false;
        return ((Spotkanie) obj).id == this.id;
    }
}
