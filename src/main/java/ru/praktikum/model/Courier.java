package ru.praktikum.model;

import io.qameta.allure.internal.shadowed.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
public class Courier {
    private Integer id;
    private String login;
    private String password;
    private String firstName;
}

