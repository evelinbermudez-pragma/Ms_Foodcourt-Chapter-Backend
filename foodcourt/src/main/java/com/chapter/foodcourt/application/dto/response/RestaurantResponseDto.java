package com.chapter.foodcourt.application.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantResponseDto {
    private Integer OwnerId;
    private String name;
    private String email;
    private String phone;
}
