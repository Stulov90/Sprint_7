import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import order.Order;
import order.OrderSteps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final int rentTime;
    private final String deliveryDate;
    private final String comment;
    private final String[] color;

    public CreateOrderTest(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, String[] color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }
    @Parameterized.Parameters
    public static Object[][] createOrderTestData() {
        return new Object[][]{
                {"Гарри", "Поттер", "ул. Тисовая, д. 4", "Таганская", "62426242624", 1, "2024-05-10", "Позвоните за час до приезда", new String[]{"Black"}},
                {"Фродо", "Бэггинс", "Ривенделл, д. 567", "Тульская", "89057899955", 2, "2024-06-10", "Не звоните в дверь, так как собака дома и она будет гавкать", new String[]{"GRAY"}},
                {"Тирион", "Ланнистер", "Утес Кастерли, д. 333", "Арбатская", "89657738822", 3, "2024-04-10", "Ланнистеры всегда платят свои долги", new String[]{"BLACK", "GRAY"}},
                {"Джон", "Сноу", "Ночной дозор, д. 754", "Маяковская", "89054567908", 4, "2024-03-10", "Север помнит", new String[]{""}},
        };
    }

    @Test
    @DisplayName("Создание заказа")
public void createOrderTest(){
        Order order = new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        Response response = OrderSteps.createOrderTest(order);
        response.then().log().all().assertThat().statusCode(201).
                and().body("track", notNullValue());
    }
}
