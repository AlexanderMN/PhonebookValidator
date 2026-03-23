package stepdefinitions;

public class PaymentResult {
    private boolean success;
    private String errorMessage;
    private int chargedAmount;
    
    public boolean isSuccess() {
        return success;
    }
    
    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
    
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    public int getChargedAmount() {
        return chargedAmount;
    }
    
    public void setChargedAmount(int chargedAmount) {
        this.chargedAmount = chargedAmount;
    }
}