package ru.praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.model.Courier;
import ru.praktikum.steps.CourierSteps;

import static org.hamcrest.Matchers.notNullValue;

public class CourierLoginTests {
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
    @DisplayName("Check is status code 200 is returned on Login action")
    public void shouldReturnStatusCode200WhenLogin() {
        courierSteps
                .createCourier(courier);
        courierSteps
                .login(courier)
                .statusCode(200);
    }

    @Test
    @DisplayName("Check if status code 400 is returned on attempt to login with no Login")
    public void shouldReturnStatusCode400WhenLoginWithNullLogin() {
        courierSteps
                .createCourier(courier);
        courier.setLogin(null);
        courierSteps
                .login(courier)
                .statusCode(400);
    }

    @Test
    @DisplayName("Check if status code 400 is returned on attempt to login with no Password")
    public void shouldReturnStatusCode404WhenLoginWithInvalidPassword() {
        courierSteps
                .createCourier(courier);
        courier.setPassword(courier.getPassword()+"invalid");
        courierSteps
                .login(courier)
                .statusCode(404);
    }

    @Test
    @DisplayName("Check if 404 status code returned on attempt to login with non-existent Login")
    public void shouldReturnStatusCode404WhenLoginWithNonexistentLogin() {
        courierSteps
                .createCourier(courier);
        Integer id = courierSteps.login(courier)
                .extract().body().path("id");
        courier.setId(id);
        courierSteps.delete(courier);
        courierSteps
                .login(courier)
                .statusCode(404);
    }

    @Test
    @DisplayName("Check if ID is returned on Login action")
    public void shouldReturnIDWhenLogin() {
        courierSteps
                .createCourier(courier);
        courierSteps
                .login(courier)
                .body("id", notNullValue());
    }

    @After
    public void tearDown() {
            Integer id = courierSteps.login(courier)
                    .extract().body().path("id");

            if (id != null) {
                courier.setId(id);
                courierSteps.delete(courier);
            }
        }
}
