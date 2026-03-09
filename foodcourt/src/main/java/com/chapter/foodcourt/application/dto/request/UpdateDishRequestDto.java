package com.chapter.foodcourt.application.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateDishRequestDto {

    @Min(value = 1, message = "The price must be more than zero")
    private Double price;

    @NotBlank(message = "The description is required")
    public String description;
}
