package ru.praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.model.Courier;
import ru.praktikum.steps.CourierSteps;

public class CourierCreationTests {
    private CourierSteps courierSteps = new CourierSteps();
    Courier courier;

    @Before
    public void setUp() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

        courier = new Courier();
        courier.setLogin(RandomStringUtils.randomAlphabetic(10));
        courier.setPassword(RandomStringUtils.randomAlphabetic(10));
    }

    @Test
    @DisplayName("Check if returns 201 status code and \"True\" when create a Courier")
    public void shouldReturnOkTrueWhenCreate(){
        courierSteps
                .createCourier(courier)
                .statusCode(201)
                .body("ok", Matchers.is(true));
    }

    @Test
    @DisplayName("Check if on attempt to create a Courier with an already existing login the 409 status code and an error is returned")
    public void shouldReturnErrorWhenCreateUsingExistingLogin(){
        courierSteps
                .createCourier(courier);
        courierSteps
                .createCourier(courier)
                .statusCode(409)
                .body("message", Matchers.is("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Check if on attempt to create a Courier with no Login the 400 status code and error is returned")
    public void shouldReturnErrorWhenCreateWithNullLogin(){
        courier.setLogin(null);
        courierSteps
                .createCourier(courier)
                .statusCode(400)
                .body("message", Matchers.is("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Check if on attempt to create a Courier with no Password the 400 status code and error is returned")
    public void shouldReturnErrorWhenCreateWithNullPassword(){
        courier.setPassword(null);
        courierSteps
                .createCourier(courier)
                .statusCode(400)
                .body("message", Matchers.is("Недостаточно данных для создания учетной записи"));
    }


    @After
    public void tearDown() {
        if (courier.getLogin() != null && courier.getPassword() != null) {
            Integer id = courierSteps.login(courier)
                    .extract().body().path("id");
            if (id != null) {
                courier.setId(id);
                courierSteps.delete(courier);
            }
        }
    }
}
