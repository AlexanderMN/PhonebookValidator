package stepdefinitions;

public class ShoppingCart {
    private int totalAmount;
    
    public void addItems(int amount) {
        this.totalAmount = amount;
    }
    
    public int getTotalAmount() {
        return totalAmount;
    }
}