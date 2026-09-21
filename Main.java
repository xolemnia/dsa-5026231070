package lw01.prelab;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static File findJobsFile() {
        String[] candidates = {
            "src/lw01/prelab/jobs.txt",
            "src\\lw01\\prelab\\jobs.txt"
        };
        for (String c : candidates) {
            File f = new File(c);
            if (f.exists()) return f;
        }

        File dir = new File(System.getProperty("user.dir"));
        for (int i = 0; i < 10; i++) {
            File f = new File(dir, "jobs.txt");
            if (f.exists()) return f;
            File sf = new File(new File(dir, "src"), "jobs.txt");
            if (sf.exists()) return sf;
            dir = dir.getParentFile();
            if (dir == null) break;
        }
        return null;
    }

    public static void main(String[] args) {
        List<PrintJob> jobs = new ArrayList<>();

        File jobsFile = findJobsFile();
        if (jobsFile == null) {
            System.err.println("jobs.txt not found");
            System.exit(1);
        }

        try (Scanner scanner = new Scanner(jobsFile)) {
            while (scanner.hasNext()) {
                String type = scanner.next();
                String id = scanner.next();
                int pages = scanner.nextInt();

                PrintJob job;
                if ("MONO".equals(type)) {
                    job = new MonoPrint(id, pages);
                } else if ("COLOUR".equals(type)) {
                    job = new ColourPrint(id, pages);
                } else {
                    throw new IllegalArgumentException("Invalid job type: " + type);
                }

                jobs.add(job);
            }
        } catch (Exception e) {
            System.err.println("Error reading jobs.txt: " + e.getMessage());
            return;
        }

        for (PrintJob job : jobs) {
            System.out.println(job.summary());
        }
    }
}
