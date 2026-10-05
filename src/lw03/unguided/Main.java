import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Map<String, Integer> enrollment = new LinkedHashMap<>();
        List<String> checks = new ArrayList<>();
        int rejected = 0;

        Scanner scanner = new Scanner(new File("enrollment.txt"));

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) {
                continue;
            }

            String[] parts = line.split("\\s+");
            String op = parts[0];
            String code = parts[1];

            if ("REGISTER".equals(op)) {
                int count = Integer.parseInt(parts[2]);
                if (count <= 0) {
                    rejected++;
                } else if (enrollment.containsKey(code)) {
                    enrollment.put(code, enrollment.get(code) + count);
                } else {
                    enrollment.put(code, count);
                }
            } else if ("WITHDRAW".equals(op)) {
                int count = Integer.parseInt(parts[2]);
                if (count <= 0 || !enrollment.containsKey(code) || enrollment.get(code) < count) {
                    rejected++;
                } else {
                    enrollment.put(code, enrollment.get(code) - count);
                }
            } else if ("CHECK".equals(op)) {
                if (enrollment.containsKey(code)) {
                    checks.add(code + ": " + enrollment.get(code) + " students");
                } else {
                    checks.add(code + ": Not found");
                }
            }
        }
        scanner.close();

        System.out.println("===== Enrollment Checks =====");
        for (String check : checks) {
            System.out.println(check);
        }

        System.out.println();
        System.out.println("===== Final Enrollment =====");
        for (Map.Entry<String, Integer> entry : enrollment.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " students");
        }

        System.out.println();
        System.out.println("Rejected operations: " + rejected);
    }
}
