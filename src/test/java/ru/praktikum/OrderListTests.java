package ru.praktikum;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import ru.praktikum.steps.OrderSteps;

import static org.junit.Assert.assertTrue;

public class OrderListTests {

    @Test
    @DisplayName("Verifying the status code 200 and Orders List size for GET request")
    @Step("GET Orders list API test")
    public void testGetOrders() {
        OrderSteps orderSteps = new OrderSteps();
        ValidatableResponse response = orderSteps.getOrders();
        response.statusCode(200);
        assertTrue(response.extract().jsonPath().getList("orders").size() > 0);
    }
}