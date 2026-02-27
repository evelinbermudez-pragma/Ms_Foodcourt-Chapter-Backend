package com.chapter.foodcourt.application.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateDishRequestDto {

    @NotNull(message= "The name is required")
    @Min(value = 1, message = "The name must be more than cero")
    private String price;

    @NotBlank(message = "The description is required")
    public String description;
}
