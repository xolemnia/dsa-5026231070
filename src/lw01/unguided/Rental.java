package lw01.unguided;

public abstract class Rental implements Chargeable {
    private final String id;
    private final int days;

    public Rental(String id, int days) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("ID must not be empty");
        }
        if (days <= 0 || days > 30) {
            throw new IllegalArgumentException("Days must be between 1 and 30");
        }
        this.id = id;
        this.days = days;
    }

    @Override
    public abstract int calculateCharge();

    public String label() {
        return "Rental";
    }

    public String summary() {
        return id + " | " + label() + " | " + calculateCharge();
    }

    public int calculateCharge(int units) {
        if (units <= 0 || units > 10) {
            throw new IllegalArgumentException("Units must be between 1 and 10");
        }
        return units * calculateCharge();
    }

    public String getId() {
        return id;
    }

    public int getDays() {
        return days;
    }
}
