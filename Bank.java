import java.io.*;
import java.util.*;

public class Bank {
    private LinkedList<Account> accounts = new LinkedList<>();
    private HashMap<String, Account> accountMap = new HashMap<>();
    private Queue<ServiceRequest> requests = new LinkedList<>();
    private final String FILE_NAME = "bank_accounts.txt";
    private int nextId = 101;

    public Bank() {
        loadAccounts();
    }

    // ---------- Account operations ----------
    public Account createAccount(String name, double balance) {
        if (name == null || name.trim().isEmpty()) {
            System.out.println("Invalid name. Name cannot be blank.");
            return null;
        }
        if (balance < 0) {
            System.out.println("Invalid opening balance.");
            return null;
        }
        String id = "ACC" + nextId++;
        Account acc = new Account(id, name.trim(), balance);
        accounts.add(acc);          // Linked List
        accountMap.put(id, acc);    // Hashing
        System.out.println("Account created: " + acc);
        return acc;
    }

    public Account searchAccount(String id) {
        Account acc = accountMap.get(id.toUpperCase());
        if (acc == null) {
            System.out.println("Account " + id.toUpperCase() + " not found.");
        }
        return acc;
    }

    public void checkBalance(String id) {
        Account acc = searchAccount(id);
        if (acc != null) {
            System.out.println("Balance: " + (long) acc.getBalance());
        }
    }

    public boolean deposit(String id, double amount) {
        Account acc = searchAccount(id);
        if (acc == null) return false;
        if (amount <= 0) {
            System.out.println("Invalid amount. Amount cannot be negative.");
            return false;
        }
        acc.setBalance(acc.getBalance() + amount);
        acc.getHistory().push(new Transaction("Deposit", amount));
        System.out.println("Deposited " + (long) amount + ". Balance: " + (long) acc.getBalance());
        return true;
    }

    public boolean withdraw(String id, double amount) {
        Account acc = searchAccount(id);
        if (acc == null) return false;
        if (amount <= 0) {
            System.out.println("Invalid amount. Amount cannot be negative.");
            return false;
        }
        if (amount > acc.getBalance()) {
            System.out.println("Insufficient balance. Withdrawal rejected.");
            return false;
        }
        acc.setBalance(acc.getBalance() - amount);
        acc.getHistory().push(new Transaction("Withdraw", amount));
        System.out.println("Withdrew " + (long) amount + ". Balance: " + (long) acc.getBalance());
        return true;
    }

    // ---------- Queue of pending service requests ----------
    public void addRequest(ServiceRequest r) {
        requests.offer(r);
        System.out.println("Request queued: " + r.getType() + " " + (long) r.getAmount()
                + " for " + r.getAccountId() + " (pending: " + requests.size() + ")");
    }

    public void processRequests() {
        if (requests.isEmpty()) {
            System.out.println("No pending requests.");
            return;
        }
        while (!requests.isEmpty()) {
            ServiceRequest r = requests.poll();          // FIFO
            System.out.println("Processing: " + r.getType() + " " + (long) r.getAmount()
                    + " for " + r.getAccountId());
            if (r.getType().equals("Deposit")) deposit(r.getAccountId(), r.getAmount());
            else withdraw(r.getAccountId(), r.getAmount());
        }
    }

    // ---------- Stack of recent transactions ----------
    public void showTransactions(String id) {
        Account acc = searchAccount(id);
        if (acc == null) return;
        Stack<Transaction> h = acc.getHistory();
        if (h.isEmpty()) {
            System.out.println("No transactions yet.");
            return;
        }
        StringBuilder sb = new StringBuilder("Recent transactions: ");
        for (int i = h.size() - 1; i >= 0; i--) {         // latest first
            sb.append(h.get(i));
            if (i > 0) sb.append(", ");
        }
        System.out.println(sb);
    }

    public boolean undoLast(String id) {
        Account acc = searchAccount(id);
        if (acc == null) return false;
        if (acc.getHistory().isEmpty()) {
            System.out.println("Nothing to undo.");
            return false;
        }
        Transaction t = acc.getHistory().pop();
        if (t.getType().equals("Deposit")) acc.setBalance(acc.getBalance() - t.getAmount());
        else acc.setBalance(acc.getBalance() + t.getAmount());
        System.out.println("Undone: " + t + ". Balance: " + (long) acc.getBalance());
        return true;
    }

    public void displayAccounts() {
        System.out.println("--- ALL ACCOUNTS ---");
        for (Account a : accounts) System.out.println(a);
    }

    // ---------- File handling ----------
    public void saveAccounts() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Account a : accounts) {
                bw.write(a.getAccountId() + "|" + a.getName() + "|" + (long) a.getBalance());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    public void loadAccounts() {
        File f = new File(FILE_NAME);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split("\\|");
                if (p.length != 3) continue;
                Account a = new Account(p[0], p[1], Double.parseDouble(p[2]));
                accounts.add(a);
                accountMap.put(p[0], a);
                int num = Integer.parseInt(p[0].substring(3));
                if (num >= nextId) nextId = num + 1;
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
    }
}