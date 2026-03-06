package com.chapter.foodcourt.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantEmployeeRequestDto {

    @NotBlank(message = "The email of employee is required")
    private String employeeEmail;

    @NotNull(message = "The restaurant id is required")
    private Integer restaurantId;
}
