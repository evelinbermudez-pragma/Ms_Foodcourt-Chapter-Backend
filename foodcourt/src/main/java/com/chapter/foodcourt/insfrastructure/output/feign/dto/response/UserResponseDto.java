package com.chapter.foodcourt.insfrastructure.output.feign.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {
    private Integer id;
    private String name;
    private String lastName;
    @JsonProperty("role")
    private Integer roleId;
    private String phone;
    private String email;
}
