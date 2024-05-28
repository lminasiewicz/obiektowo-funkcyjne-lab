import java.time.LocalDate;
import java.util.Objects;

public abstract sealed class HousingUnit permits House, Flat {
    protected Integer id;
    protected String street;
    protected int houseNumber;
    protected String settlement;
    protected String postCode;
    protected double area;
    protected double price;
    protected LocalDate expiryDate;

    public HousingUnit(String street, int houseNumber, String settlement, String postCode,
                       double area, double price, LocalDate expiryDate) {
        this.street = street;
        this.houseNumber = houseNumber;
        this.settlement = settlement;
        this.postCode = postCode;
        this.area = area;
        this.price = price;
        this.expiryDate = expiryDate;
    }

    public HousingUnit(Integer id) {
        this.street = "";
        this.houseNumber = -1;
        this.settlement = "";
        this.postCode = "";
        this.area = -1;
        this.price = -1;
        this.expiryDate = LocalDate.of(1, 1, 1);
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getArea() {
        return this.area;
    }

    public double getPrice() {
        return this.price;
    }

    public LocalDate getExpiryDate() {
        return this.expiryDate;
    }

    public String getSettlement() {
        return this.settlement;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof HousingUnit)) return false;
        if (((HousingUnit) obj).id == null) return false;
        return Objects.equals(((HousingUnit) obj).id, this.id);
    }

}
