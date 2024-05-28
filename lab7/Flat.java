import java.time.LocalDate;

public final class Flat extends HousingUnit {
    private int flatNumber;
    private int floor;

    public Flat(String street, int houseNumber, int flatNumber, String settlement, String postCode,
                double area, int floor, double price, LocalDate expiryDate) {
        super(street, houseNumber, settlement, postCode, area, price, expiryDate);
        this.flatNumber = flatNumber;
        this.floor = floor;
    }

    public Flat(Integer id) {
        super(id);
        this.flatNumber = -1;
        this.floor = -1;
    }

    public int getFloor() {
        return this.floor;
    }

    @Override
    public String toString() {
        return "OFERTA ID = " + id +
                "\nMieszkanie: " + street + " " + houseNumber + "/" + flatNumber + " (Piętro " + floor + "),\n" +
                postCode + " " + settlement +
                "\nPowierzchnia Mieszkania: " + area + "m^2" +
                "\nCena: " + String.format("%.2f", price) + "zł. Oferta wazna do: " + expiryDate + "\n";
    }
}