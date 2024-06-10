package ru.praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.praktikum.model.Order;
import ru.praktikum.steps.OrderSteps;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertNotNull;

@RunWith(Parameterized.class)
public class OrderCreationTests {

    private final List<String> colors;

    public OrderCreationTests(List<String> colors) {
        this.colors = colors;
    }

    @Parameterized.Parameters
    public static Object[][] data() {
        return new Object[][] {
                { Collections.singletonList("BLACK") },
                { Collections.singletonList("GREY") },
                { Arrays.asList("BLACK", "GREY") },
                { Collections.emptyList() }
        };
    }

    @Test
    @DisplayName("Test creating orders with different colors chosen")
    public void testCreateOrderWithColors() {
        Order order = new Order("John", "Doe", "123 Main St", "1", "+1234567890", 5, "2024-06-10", "Leave at door", colors);
        OrderSteps orderSteps = new OrderSteps();
        ValidatableResponse response = orderSteps.createOrder(order);

        response.statusCode(201);
        assertNotNull(response.extract().path("track"));
    }
}
