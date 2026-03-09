package com.chapter.foodcourt.application.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDishResponseDto {
    private Integer dishId;
    private Integer quantity;
    private String name;
    private String description;
    private Double price;
    private String category;
    private String imageUrl;
}
