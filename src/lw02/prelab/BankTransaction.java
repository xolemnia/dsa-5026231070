import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;
import java.io.File;
import java.io.FileNotFoundException;

public class BankTransaction {

    public static void main(String[] args) throws FileNotFoundException {

        // Step 1: Read and store transactions in a LinkedList
        LinkedList<String[]> transactionsList = new LinkedList<>();
        Scanner scanner = new Scanner(new File("transactions.txt"));
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (!line.isEmpty()) {
                String[] parts = line.split("\\s+");
                transactionsList.add(parts);
            }
        }
        scanner.close();

        // Step 2: Create customer data in another LinkedList
        LinkedList<String[]> customers = new LinkedList<>();
        for (String[] txn : transactionsList) {
            String name = txn[0];
            boolean found = false;
            for (String[] cust : customers) {
                if (cust[0].equals(name)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                String[] newCustomer = new String[]{name, "0"};
                customers.add(newCustomer);
            }
        }

        // Step 3: Move transactions from LinkedList to Queue and process
        Queue<String[]> transactionQueue = new LinkedList<>();
        for (String[] txn : transactionsList) {
            transactionQueue.offer(txn);
        }

        Stack<String[]> failedStack = new Stack<>();

        while (!transactionQueue.isEmpty()) {
            String[] txn = transactionQueue.poll();
            String name = txn[0];
            String type = txn[1];
            int amount = Integer.parseInt(txn[2]);

            // Find customer in the list
            for (String[] cust : customers) {
                if (cust[0].equals(name)) {
                    int balance = Integer.parseInt(cust[1]);

                    if ("DEPOSIT".equals(type)) {
                        cust[1] = String.valueOf(balance + amount);
                    } else if ("WITHDRAW".equals(type)) {
                        if (amount > balance) {
                            // Failed withdrawal - push to stack
                            failedStack.push(txn);
                        } else {
                            cust[1] = String.valueOf(balance - amount);
                        }
                    }
                    break;
                }
            }
        }

        // Step 5: Display final balances
        System.out.println("=== Final Balances ===");
        for (String[] cust : customers) {
            System.out.println(cust[0] + " : " + cust[1]);
        }

        // Step 6: Display failed transactions (LIFO order from stack)
        System.out.println("\n=== Failed Transactions ===");
        while (!failedStack.isEmpty()) {
            String[] txn = failedStack.pop();
            System.out.println(txn[0] + " " + txn[1] + " " + txn[2]);
        }
    }
}
