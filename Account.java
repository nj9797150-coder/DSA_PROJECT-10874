import java.util.Stack;

public class Account {
    private String accountId;
    private String name;
    private double balance;
    private Stack<Transaction> history = new Stack<>();

    public Account(String accountId, String name, double balance) {
        this.accountId = accountId;
        this.name = name;
        this.balance = balance;
    }
    public String getAccountId()          { return accountId; }
    public String getName()               { return name; }
    public double getBalance()            { return balance; }
    public void setBalance(double b)      { this.balance = b; }
    public Stack<Transaction> getHistory(){ return history; }

    @Override
    public String toString() {
        return accountId + " | " + name + " | Balance " + (long) balance;
    }
}