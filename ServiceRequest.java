public class ServiceRequest {
    private String accountId;
    private String type;      // "Deposit" or "Withdraw"
    private double amount;

    public ServiceRequest(String accountId, String type, double amount) {
        this.accountId = accountId;
        this.type = type;
        this.amount = amount;
    }
    public String getAccountId() { return accountId; }
    public String getType()      { return type; }
    public double getAmount()    { return amount; }
}