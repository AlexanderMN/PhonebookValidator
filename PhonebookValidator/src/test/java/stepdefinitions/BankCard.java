package stepdefinitions;

public class BankCard {
    private int balance;
    private int dailyLimit;
    private boolean validData;
    private int dailySpent;
    
    public BankCard(int initialBalance) {
        this.balance = initialBalance;
        this.dailyLimit = Integer.MAX_VALUE;
        this.dailySpent = 0;
    }
    
    public int getBalance() {
        return balance;
    }
    
    public void setDailyLimit(int limit) {
        this.dailyLimit = limit;
    }
    
    public void setValidData(boolean valid) {
        this.validData = valid;
    }
    
    public boolean hasValidData() {
        return validData;
    }
    
    public boolean hasSufficientFunds(int amount) {
        return balance >= amount;
    }
    
    public boolean withinDailyLimit(int amount) {
        return (dailySpent + amount) <= dailyLimit;
    }
    
    public boolean deductAmount(int amount) {
        if (hasSufficientFunds(amount) && withinDailyLimit(amount)) {
            balance -= amount;
            dailySpent += amount;
            return true;
        }
        return false;
    }
}