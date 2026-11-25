public class Transaction {
    private String type;      // "Deposit" or "Withdraw"
    private double amount;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }
    public String getType()    { return type; }
    public double getAmount()  { return amount; }

    @Override
    public String toString() {
        return type + " " + (long) amount;
    }
}