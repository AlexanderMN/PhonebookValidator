package stepdefinitions;

public class PaymentService {
    
    public PaymentResult processPayment(BankCard card, int amount, Order order) {
        PaymentResult result = new PaymentResult();
        
        // Проверка данных карты
        if (!card.hasValidData()) {
            result.setSuccess(false);
            result.setErrorMessage("Недействительные данные карты");
            result.setChargedAmount(0);
            return result;
        }
        
        // Проверка достаточности средств
        if (!card.hasSufficientFunds(amount)) {
            result.setSuccess(false);
            result.setErrorMessage("Недостаточно средств на карте");
            result.setChargedAmount(0);
            return result;
        }
        
        // Проверка суточного лимита
        if (!card.withinDailyLimit(amount)) {
            result.setSuccess(false);
            result.setErrorMessage("Превышен лимит операции по карте");
            result.setChargedAmount(0);
            return result;
        }
        
        // Списание средств
        boolean deducted = card.deductAmount(amount);
        
        if (deducted) {
            result.setSuccess(true);
            result.setChargedAmount(amount);
            order.setStatus("Оплачен");
        } else {
            result.setSuccess(false);
            result.setErrorMessage("Ошибка при списании средств");
            result.setChargedAmount(0);
        }
        
        return result;
    }
}