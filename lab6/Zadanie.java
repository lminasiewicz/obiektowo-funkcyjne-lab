import java.time.LocalDate;
import java.time.LocalTime;

public final class Zadanie extends Wydarzenie {
    private Status status;

    public Zadanie(int day, int month, int year, int startHour, int startMinute, int endHour,
                     int endMinute, Status status, String name, int weekday) {
        super(name, LocalDate.of(year, month, day), LocalTime.of(startHour, startMinute),
                LocalTime.of(endHour, endMinute), weekday);
        this.status = status;
    }

    public Zadanie() {
        super();
        // PLACEHOLDER
        this.name = "PLACEHOLDER";
        this.date = LocalDate.of(0, 1, 1);
        this.startTime = LocalTime.of(0, 0);
        this.endTime = LocalTime.of(0, 0);
        this.status = Status.PLANNED;
        this.weekday = -1;
        this.id = -1;
    }

    public Status getStatus() {
        return this.status;
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
            return "Zadanie ID:" + this.id + "\non " + data + "\nPRIORITY: " + this.status.name();
        }
        else {
            return "Zadanie" + data + "\nPRIORITY: " + this.status.name();
        }
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Zadanie)) return false;
        if (((Zadanie) obj).id == null) return false;
        return ((Zadanie) obj).id == this.id;
    }
}
