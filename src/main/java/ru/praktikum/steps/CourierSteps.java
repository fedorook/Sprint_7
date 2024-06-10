package ru.praktikum.steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import ru.praktikum.model.Courier;

import static io.restassured.RestAssured.given;
import static ru.praktikum.config.RestConfig.HOST;

public class CourierSteps {
    @Step("Collecting the response of Create Courier Endpoint")
    public ValidatableResponse createCourier(Courier courier) {

        return given()
                .contentType(ContentType.JSON)
                .baseUri(HOST)
                .body(courier)
                .when()
                .post("/api/v1/courier")
                .then();
    }

    @Step("Collecting the response of Login Courier Endpoint")
    public ValidatableResponse login(Courier courier) {

        return given()
                .contentType(ContentType.JSON)
                .baseUri(HOST)
                .body(courier)
                .when()
                .post("/api/v1/courier/login")
                .then();
    }

    @Step("Collecting the response of Delete Courier Endpoint")
    public ValidatableResponse delete(Courier courier) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(HOST)
                .pathParam("id", courier.getId())
                .when()
                .delete("/api/v1/courier/{id}")
                .then();
    }
}
