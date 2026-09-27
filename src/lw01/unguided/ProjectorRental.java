package lw01.unguided;

public class ProjectorRental extends Rental {
    public ProjectorRental(String id, int days) {
        super(id, days);
    }

    @Override
    public int calculateCharge() {
        return getDays() * 60000 + 20000;
    }

    @Override
    public String label() {
        return "Projector";
    }
}
