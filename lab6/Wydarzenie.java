import java.time.LocalDate;
import java.time.LocalTime;

abstract sealed class Wydarzenie permits Spotkanie, Zadanie {
    private static final LocalTime EARLIEST_TIME = LocalTime.of(7, 30);
    protected Integer id;
    protected String name;
    protected LocalDate date;
    protected LocalTime startTime;
    protected LocalTime endTime;
    protected int weekday;

    protected Wydarzenie(String name, LocalDate date, LocalTime startTime, LocalTime endTime, int weekday) {
        this.name = name;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.weekday = weekday;
    }

    protected Wydarzenie() {}

    public static LocalTime getEarliestTime() { return EARLIEST_TIME; }

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

    public int getId() {
        return this.id;
    }

    void setId(int id) {
        this.id = id;
    }

    public int getWeekday() { return this.weekday; }

}
