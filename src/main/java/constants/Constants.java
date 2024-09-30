package constants;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class Constants {
    public static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";
    public static final String BASE_PATH = "api/v1";

    public static RequestSpecification spec() {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .basePath(BASE_PATH);
    }
}
