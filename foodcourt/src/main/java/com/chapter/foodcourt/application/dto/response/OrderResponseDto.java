package com.chapter.foodcourt.application.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrderResponseDto {
    private Integer id;
    private Integer clientId;
    private Integer restaurantId;
    private LocalDateTime date;
    private String status;
    private List<OrderDishResponseDto> orderDishes;
}
