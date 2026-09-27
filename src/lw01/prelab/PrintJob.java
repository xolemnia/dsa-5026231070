package lw01.prelab;

public abstract class PrintJob implements Chargeable {
    private final String id;
    private final int pages;

    public PrintJob(String id, int pages) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("ID must not be empty");
        }
        if (pages <= 0 || pages > 100) {
            throw new IllegalArgumentException("Pages must be between 1 and 100");
        }
        this.id = id;
        this.pages = pages;
    }

    @Override
    public abstract int calculateCharge();

    public String label() {
        return "Print";
    }

    public String summary() {
        return id + " | " + label() + " | " + calculateCharge();
    }

    public int calculateCharge(int copies) {
        if (copies <= 0 || copies > 10) {
            throw new IllegalArgumentException("Copies must be between 1 and 10");
        }
        return copies * calculateCharge();
    }

    public String getId() {
        return id;
    }

    public int getPages() {
        return pages;
    }
}
