import java.util.ArrayList;
import java.util.function.Predicate;
import java.time.LocalDate;
public class OfferList {
    private static final int OFFER_LIFETIME_DAYS = 60;
    private ArrayList<HousingUnit> offers;
    private int nextId;

    public OfferList() {
        this.offers = new ArrayList<>();
        this.nextId = 1;
    }

    public int getNextId() {
        return this.nextId;
    }

    public void incrementNextId() {
        this.nextId++;
    }

    public ArrayList<HousingUnit> getOffers() {
        return this.offers;
    }

    public static int getOfferLifetime() {
        return OFFER_LIFETIME_DAYS;
    }

    public void addOffer(HousingUnit offer) {
        offer.setId(this.nextId);
        this.incrementNextId();
        this.offers.add(offer);
    }

    public boolean deleteOffer(Integer id) {
        House placeholder = new House(id);
        boolean removed = this.offers.remove(placeholder);
        return removed;
    }

    public ArrayList<HousingUnit> filterOffers(Predicate<HousingUnit> predicate) {
        ArrayList<HousingUnit> result = new ArrayList<>();
        this.offers.forEach((event) -> {
            if (predicate.test(event)) {
                result.add(event);
            }
        });
        return result;
    }

    public void addPlaceholders() {
        this.addOffer(new House("Przykładowa", 1, "Przykładowo Dolne", "12-345", 73.45, 600500.50, 100.00, LocalDate.of(2024, 5, 29)));
        this.addOffer(new House("Przykładowa", 2, "Przykładowo Dolne", "12-346", 70, 520000, 90.00, LocalDate.of(2024, 5, 30)));
        this.addOffer(new House("Śmieszna", 12, "Przykładowo Dolne", "13-345", 77.5, 600500.50, 110.50, LocalDate.of(2024, 5, 27)));
        this.addOffer(new House("Śmieszna", 50, "Przykładowo Dolne", "14-345", 85.5, 835500.50, 150.35, LocalDate.of(2024, 6, 2)));
        this.addOffer(new House("Przykładowa", 3, "Przykładowo Górne", "12-347", 40, 435000, 60.80, LocalDate.of(2024, 6, 8)));

        this.addOffer(new Flat("Przykładowa", 10, 6, "Przykładowo Dolne", "54-321", 50, 2, 250000.60, LocalDate.of(2024, 5, 29)));
        this.addOffer(new Flat("Przykładowa", 10, 7, "Przykładowo Dolne", "54-320", 40, 3, 225000, LocalDate.of(2024, 5, 25)));
        this.addOffer(new Flat("Przykładowa", 32, 1, "Przykładowo Górne", "53-321", 45, 0, 244000.50, LocalDate.of(2024, 5, 30)));
        this.addOffer(new Flat("Przykładowa", 32, 3, "Przykładowo Górne", "52-321", 42.5, 1, 2480000.85, LocalDate.of(2024, 6, 1)));
        this.addOffer(new Flat("Śmieszna", 16, 10, "Przykładowo Górne", "44-321", 48, 3, 284000.60, LocalDate.of(2024, 6, 3)));
        this.addOffer(new Flat("Śmieszna", 16, 12, "Przykładowo Górne", "34-321", 62.5, 4, 315000, LocalDate.of(2024, 6, 5)));
        this.addOffer(new Flat("Śmieszna", 17, 6, "Przykładowo Górne", "24-321", 60, 2, 305005.55, LocalDate.of(2024, 6, 6)));
    }
}
