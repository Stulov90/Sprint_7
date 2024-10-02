import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import order.OrderSteps;
import org.junit.Test;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;

public class GetOrderListTest {
    @Test
    @DisplayName("Получение списка заказов")
    public void getOrderListTest() {
        Response response = OrderSteps.getOrderList();
        response.then().log().all().assertThat().statusCode(200)
                .and().body("orders", not(empty()));
    }
}
