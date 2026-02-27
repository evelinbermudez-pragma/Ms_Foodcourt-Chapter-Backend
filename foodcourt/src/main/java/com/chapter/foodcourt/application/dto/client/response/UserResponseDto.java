package com.chapter.foodcourt.application.dto.client.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {
    private Integer id;
    private String name;
    private String lastName;
    private Integer roleId;
    private String phone;
    private String email;
}
