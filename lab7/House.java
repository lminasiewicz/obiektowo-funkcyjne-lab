import java.time.LocalDate;

public final class House extends HousingUnit {
    private double plotArea;

    public House(String street, int houseNumber, String settlement, String postCode,
                 double area, double price, double plotArea, LocalDate expiryDate) {
        super(street, houseNumber, settlement, postCode, area, price, expiryDate);
        this.plotArea = plotArea;
    }

    public House(Integer id) {
        super(id);
        this.plotArea = -1;
    }

    @Override
    public String toString() {
        return "OFERTA ID = " + id +
                "\nDom: " + street + " " + houseNumber + ", " + postCode + " " + settlement +
                "\nPowierzchnia Dzialki: " + plotArea + "m^2, Powierzchnia Domu: " + area + "m^2" +
                "\nCena: " + String.format("%.2f", price) + "zł. Oferta wazna do: " + expiryDate + "\n";
    }
}
