import courier.Courier;
import courier.CourierDefaultData;
import courier.CourierSteps;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LoginCourierTest extends CourierDefaultData {
    String id;

    @After
    public void deleteCourier() {
        if (id != null) {
            CourierSteps.deleteCourier(id);
        }
    }

    @Test
    @DisplayName("Авторизация курьера в системе с существующими login и password")
    public void loginCourierTest() {
        Courier courier = CourierSteps.createCourierWithDefaultData();
        CourierSteps.createCourier(courier);
        courier.setFirstName(null);
        id = CourierSteps.getId(courier);
        Response response = CourierSteps.loginCourier(courier);
        response.then().log().all().assertThat().statusCode(200).
                and().body("id", notNullValue());
    }

    @Test
    @DisplayName("Авторизация курьера в системе с указанием неправильного login")
    public void loginCourierWithWrongLoginTest() {
        Courier courier = CourierSteps.createCourierWithDefaultData();
        CourierSteps.createCourier(courier);
        courier.setFirstName(null);
        id = CourierSteps.getId(courier);
        courier.setLogin(COURIER_WRONG_LOGIN);
        Response response = CourierSteps.loginCourier(courier);
        response.then().log().all().assertThat().statusCode(404).
                and().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Авторизация курьера в системе с указанием неправильного password")
    public void loginCourierWithWrongPasswordTest() {
        Courier courier = CourierSteps.createCourierWithDefaultData();
        CourierSteps.createCourier(courier);
        courier.setFirstName(null);
        id = CourierSteps.getId(courier);
        courier.setPassword(COURIER_WRONG_PASSWORD);
        Response response = CourierSteps.loginCourier(courier);
        response.then().log().all().assertThat().statusCode(404).
                and().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Авторизация курьера в системе с указанием неправильных login и password")
    public void loginCourierWithWrongDataTest() {
        Courier courier = CourierSteps.createCourierWithDefaultData();
        CourierSteps.createCourier(courier);
        courier.setFirstName(null);
        id = CourierSteps.getId(courier);
        courier.setLogin(COURIER_WRONG_LOGIN);
        courier.setPassword(COURIER_WRONG_PASSWORD);
        Response response = CourierSteps.loginCourier(courier);
        response.then().log().all().assertThat().statusCode(404).
                and().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Логин в системе без указания login")
    public void loginCourierWithoutLoginTest() {
        Courier courier = CourierSteps.createCourierWithDefaultData();
        CourierSteps.createCourier(courier);
        courier.setFirstName(null);
        id = CourierSteps.getId(courier);
        courier.setLogin("");
        Response response = CourierSteps.loginCourier(courier);
        response.then().log().all().assertThat().statusCode(400).
                and().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Логин в системе без указания password")
    public void loginCourierWithoutPasswordTest() {
        Courier courier = CourierSteps.createCourierWithDefaultData();
        CourierSteps.createCourier(courier);
        courier.setFirstName(null);
        id = CourierSteps.getId(courier);
        courier.setPassword("");
        Response response = CourierSteps.loginCourier(courier);
        response.then().log().all().assertThat().statusCode(400).
                and().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Логин в системе без указания login и password")
    public void loginCourierWithoutDataTest() {
        Courier courier = CourierSteps.createCourierWithDefaultData();
        CourierSteps.createCourier(courier);
        courier.setFirstName(null);
        id = CourierSteps.getId(courier);
        courier.setLogin("");
        courier.setPassword("");
        Response response = CourierSteps.loginCourier(courier);
        response.then().log().all().assertThat().statusCode(400).
                and().body("message", equalTo("Недостаточно данных для входа"));
    }
}
