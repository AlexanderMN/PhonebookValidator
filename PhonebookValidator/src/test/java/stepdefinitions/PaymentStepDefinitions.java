package stepdefinitions;

import io.cucumber.java.ru.Дано;    // ← CORRECT
import io.cucumber.java.ru.Когда;   // ← CORRECT
import io.cucumber.java.ru.Тогда;   // ← CORRECT
import io.cucumber.java.ru.И;       // ← CORRECT
import static org.junit.Assert.*;

public class PaymentStepDefinitions {
    
    private BankCard userCard;
    private ShoppingCart cart;
    private PaymentService paymentService;
    private PaymentResult paymentResult;
    private Order order;
    
    @Дано("^на банковской карте пользователя имеется (\\d+) рублей$")
    public void наБанковскойКартеПользователяИмеетсяРублей(int amount) throws Throwable {
        userCard = new BankCard(amount);
        System.out.println("На карте пользователя имеется " + amount + " рублей");
    }
    
    @Дано("^суточный лимит по карте составляет (\\d+) рублей$")
    public void суточныйЛимитПоКартеСоставляетРублей(int limit) throws Throwable {
        userCard.setDailyLimit(limit);
        System.out.println("Суточный лимит по карте составляет " + limit + " рублей");
    }
    
    @Дано("^в корзине интернет-магазина есть товары на сумму (\\d+) рублей$")
    public void вКорзинеИнтернетМагазинаЕстьТоварыНаСуммуРублей(int amount) throws Throwable {
        cart = new ShoppingCart();
        cart.addItems(amount);
        order = new Order(cart);
        System.out.println("В корзине товары на сумму " + amount + " рублей");
    }
    
    @Когда("^пользователь выбирает оплату банковской картой$")
    public void пользовательВыбираетОплатуБанковскойКартой() throws Throwable {
        paymentService = new PaymentService();
        System.out.println("Пользователь выбрал оплату банковской картой");
    }
    
    @Когда("^вводит корректные данные карты$")
    public void вводитКорректныеДанныеКарты() throws Throwable {
        userCard.setValidData(true);
        System.out.println("Пользователь ввел корректные данные карты");
    }
    
    @Когда("^вводит некорректные данные карты \\(неверный номер/срок/ CVV\\)$")
    public void вводитНекорректныеДанныеКарты() throws Throwable {
        userCard.setValidData(false);
        System.out.println("Пользователь ввел некорректные данные карты");
    }
    
    @Когда("^подтверждает оплату$")
    public void подтверждаетОплату() throws Throwable {
        paymentResult = paymentService.processPayment(userCard, cart.getTotalAmount(), order);
        System.out.println("Пользователь подтвердил оплату");
    }
    
    @Тогда("^оплата должна быть успешно завершена$")
    public void оплатаДолжнаБытьУспешноЗавершена() throws Throwable {
        assertTrue("Оплата должна быть успешной", paymentResult.isSuccess());
        System.out.println("Проверка: оплата успешно завершена");
    }
    
    @Тогда("^с карты должно быть списано (\\d+) рублей$")
    public void сКартыДолжноБытьСписаноРублей(int amount) throws Throwable {
        assertEquals("Списана правильная сумма", amount, paymentResult.getChargedAmount());
        System.out.println("Проверка: с карты списано " + amount + " рублей");
    }
    
    @Тогда("^на карте должно остаться (\\d+) рублей$")
    public void наКартеДолжноОстатьсяРублей(int amount) throws Throwable {
        assertEquals("Остаток на карте верный", amount, userCard.getBalance());
        System.out.println("Проверка: на карте осталось " + amount + " рублей");
    }
    
    @Тогда("^заказ должен быть переведен в статус \"([^\"]*)\"$")
    public void заказДолженБытьПереведенВСтатус(String status) throws Throwable {
        assertEquals("Статус заказа обновлен", status, order.getStatus());
        System.out.println("Проверка: заказ переведен в статус \"" + status + "\"");
    }
    
    @Тогда("^должно появиться сообщение \"([^\"]*)\"$")
    public void должноПоявитьсяСообщение(String expectedMessage) throws Throwable {
        assertEquals("Сообщение об ошибке верное", expectedMessage, paymentResult.getErrorMessage());
        System.out.println("Проверка: появилось сообщение \"" + expectedMessage + "\"");
    }
    
    @Тогда("^оплата не должна быть выполнена$")
    public void оплатаНеДолжнаБытьВыполнена() throws Throwable {
        assertFalse("Оплата не должна быть выполнена", paymentResult.isSuccess());
        System.out.println("Проверка: оплата не выполнена");
    }
    
    @Тогда("^заказ должен остаться в статусе \"([^\"]*)\"$")
    public void заказДолженОстатьсяВСтатусе(String status) throws Throwable {
        assertEquals("Статус заказа не изменился", status, order.getStatus());
        System.out.println("Проверка: заказ остался в статусе \"" + status + "\"");
    }
    
    @Тогда("^средства с карты не должны быть списаны$")
    public void средстваСКартыНеДолжныБытьСписаны() throws Throwable {
        assertEquals("Средства не списаны", paymentResult.getChargedAmount(), 0);
        System.out.println("Проверка: средства с карты не списаны");
    }
}