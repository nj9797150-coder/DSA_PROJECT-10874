import java.util.Scanner;

public class Main {
    static double readAmount(Scanner sc) {
        try { return Double.parseDouble(sc.nextLine().trim()); }
        catch (NumberFormatException e) { return -1; }
    }

    public static void main(String[] args) {
        Bank bank = new Bank();
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            System.out.println("====== Bank Management System ======");
            System.out.println("1. Create Account");
            System.out.println("2. Search Account (Hashing)");
            System.out.println("3. Check Balance");
            System.out.println("4. Deposit");
            System.out.println("5. Withdraw");
            System.out.println("6. Add Service Request (Queue)");
            System.out.println("7. Process Service Requests");
            System.out.println("8. View Recent Transactions (Stack)");
            System.out.println("9. Undo Last Transaction");
            System.out.println("10. Display All Accounts");
            System.out.println("0. Exit");
            System.out.println("--------------------------------------------");
            System.out.print("Enter your choice: ");
            try { choice = Integer.parseInt(sc.nextLine().trim()); }
            catch (NumberFormatException e) { choice = -1; }

            switch (choice) {
                case 1:
                    System.out.print("Enter account holder name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter opening balance: ");
                    bank.createAccount(name, readAmount(sc));
                    break;
                case 2:
                    System.out.print("Enter account ID: ");
                    Account a = bank.searchAccount(sc.nextLine().trim());
                    if (a != null) System.out.println("Found: " + a);
                    break;
                case 3:
                    System.out.print("Enter account ID: ");
                    bank.checkBalance(sc.nextLine().trim());
                    break;
                case 4:
                    System.out.print("Enter account ID: ");
                    String d = sc.nextLine().trim();
                    System.out.print("Enter amount: ");
                    bank.deposit(d, readAmount(sc));
                    break;
                case 5:
                    System.out.print("Enter account ID: ");
                    String w = sc.nextLine().trim();
                    System.out.print("Enter amount: ");
                    bank.withdraw(w, readAmount(sc));
                    break;
                case 6:
                    System.out.print("Enter account ID: ");
                    String rid = sc.nextLine().trim();
                    System.out.print("Request type (Deposit/Withdraw): ");
                    String type = sc.nextLine().trim();
                    System.out.print("Enter amount: ");
                    double amt = readAmount(sc);
                    if (amt <= 0 || !(type.equals("Deposit") || type.equals("Withdraw")))
                        System.out.println("Invalid request.");
                    else bank.addRequest(new ServiceRequest(rid.toUpperCase(), type, amt));
                    break;
                case 7:
                    bank.processRequests();
                    break;
                case 8:
                    System.out.print("Enter account ID: ");
                    bank.showTransactions(sc.nextLine().trim());
                    break;
                case 9:
                    System.out.print("Enter account ID: ");
                    bank.undoLast(sc.nextLine().trim());
                    break;
                case 10:
                    bank.displayAccounts();
                    break;
                case 0:
                    bank.saveAccounts();
                    System.out.println("Accounts saved to bank_accounts.txt. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 0);
        sc.close();
    }
}