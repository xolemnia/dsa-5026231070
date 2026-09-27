package lw01.unguided;

import java.io.File;
import java.util.Scanner;

public class Main {
    private static File findRentalsFile() {
        String[] candidates = {
            "src/lw01/unguided/rentals.txt",
            "src\\lw01\\unguided\\rentals.txt"
        };
        for (String c : candidates) {
            File f = new File(c);
            if (f.exists()) return f;
        }

        File dir = new File(System.getProperty("user.dir"));
        for (int i = 0; i < 10; i++) {
            File f = new File(dir, "rentals.txt");
            if (f.exists()) return f;
            File rf = new File(new File(dir, "src"), "rentals.txt");
            if (rf.exists()) return rf;
            dir = dir.getParentFile();
            if (dir == null) break;
        }
        return null;
    }

    public static void main(String[] args) {
        File rentalsFile = findRentalsFile();
        if (rentalsFile == null) {
            System.err.println("rentals.txt not found");
            System.exit(1);
        }

        Rental[] rentals;

        try (Scanner scanner = new Scanner(rentalsFile)) {
            int count = scanner.nextInt();
            rentals = new Rental[count];

            for (int i = 0; i < count; i++) {
                String type = scanner.next();
                String id = scanner.next();
                int days = scanner.nextInt();

                if ("LAPTOP".equals(type)) {
                    rentals[i] = new LaptopRental(id, days);
                } else if ("PROJECTOR".equals(type)) {
                    rentals[i] = new ProjectorRental(id, days);
                } else {
                    throw new IllegalArgumentException("Invalid rental type: " + type);
                }
            }
        } catch (Exception e) {
            System.err.println("Error reading rentals.txt: " + e.getMessage());
            return;
        }

        for (Rental rental : rentals) {
            System.out.println(rental.summary());
        }
    }
}
