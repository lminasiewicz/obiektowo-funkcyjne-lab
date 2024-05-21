import java.time.LocalDate;
import java.time.LocalTime;

public final class Spotkanie extends Wydarzenie {
    private Priority priority;

    public Spotkanie(int day, int month, int year, int startHour, int startMinute, int endHour,
                   int endMinute, Priority priority, String name, int weekday) {
        super(name, LocalDate.of(year, month, day), LocalTime.of(startHour, startMinute),
                LocalTime.of(endHour, endMinute), weekday);
        this.priority = priority;
    }

    public Spotkanie() {
        super();
        // PLACEHOLDER
        this.name = "PLACEHOLDER";
        this.date = LocalDate.of(0, 1, 1);
        this.startTime = LocalTime.of(0, 0);
        this.endTime = LocalTime.of(0, 0);
        this.priority = Priority.LOW;
        this.weekday = -1;
        this.id = -1;
    }

    public Priority getPriority() {
        return this.priority;
    }

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
                this.endTime.getHour() + ":" + endMinute + "\nOpis: " + this.name;
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
        if (!(obj instanceof Spotkanie)) return false;
        if (((Spotkanie) obj).id == null) return false;
        return ((Spotkanie) obj).id == this.id;
    }
}
