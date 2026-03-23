package stepdefinitions;

public class Order {
    private String status;
    private ShoppingCart cart;
    
    public Order(ShoppingCart cart) {
        this.cart = cart;
        this.status = "Ожидает оплаты";
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public ShoppingCart getCart() {
        return cart;
    }
}