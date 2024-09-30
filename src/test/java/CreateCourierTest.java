import courier.Courier;
import courier.CourierSteps;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class CreateCourierTest {
    String id;

    @After
    public void deleteCourier() {
        if (id != null) {
            CourierSteps.deleteCourier(id);
        }
    }

    @Test
    @DisplayName("Создание курьера")
    public void createCourierTest() {
        Courier courier = CourierSteps.createCourierWithDefaultData();
        Response response = CourierSteps.createCourier(courier);
        id = CourierSteps.getId(courier);
        response.then().log().all().assertThat().statusCode(201).
                and().body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Создание курьера с уже имеющимся в системе login")
    public void createDuplicateCourierTest() {
        Courier courier = CourierSteps.createCourierWithDefaultData();
        CourierSteps.createCourier(courier);
        id = CourierSteps.getId(courier);
        Response response = CourierSteps.createCourier(courier);
        response.then().log().all().assertThat().statusCode(409).
                and().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Создание курьера без поля login")
    public void createCourierWithoutLoginTest() {
        Courier courier = CourierSteps.createCourierWithDefaultData();
        courier.setLogin(null);
        Response response = CourierSteps.createCourier(courier);
        id = CourierSteps.getId(courier);
        response.then().assertThat().statusCode(400).
                and().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без поля password")
    public void createCourierWithoutPasswordTest() {
        Courier courier = CourierSteps.createCourierWithDefaultData();
        courier.setPassword(null);
        Response response = CourierSteps.createCourier(courier);
        id = CourierSteps.getId(courier);
        response.then().assertThat().statusCode(400).
                and().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    // В этом тесте баг, так как идет несоответствие ожидаемого результата с фактическим (ждем: 400 BadRequest, получаем: 201 Created).
    @Test
    @DisplayName("Создание курьера без поля firstName")
    public void createCourierWithoutFirstNameTest() {
        Courier courier = CourierSteps.createCourierWithDefaultData();
        courier.setFirstName(null);
        Response response = CourierSteps.createCourier(courier);
        id = CourierSteps.getId(courier);
        response.then().assertThat().statusCode(400).
                and().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
}
