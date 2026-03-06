package com.chapter.foodcourt.application.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequestDto {
    @NotNull(message = "Restaurant is required")
    private Integer restaurantId;

    @NotEmpty(message = "Order must have at least one dish")
    private List<OrderDishRequestDto> dishes;
}
