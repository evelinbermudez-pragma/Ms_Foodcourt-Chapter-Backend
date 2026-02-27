package com.chapter.foodcourt.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantRequestDto {

    @NotBlank(message = "The name is required")
    @Pattern(regexp = ".*[a-zA-Z].*", message = "El nombre no puede ser solo números")
    private String name;

    @NotBlank(message = "The NIT is required")
    @Pattern(regexp = "\\d+", message = "The NIT must be only with number characters")
    private String nit;

    @NotBlank(message = "The address is required")
    private String address  ;

    @NotBlank(message = "The phone is required")
    @Pattern(regexp = "\\+?\\d{1,13}", message = "The phone must be only with 13 characters and can start with +")
    private String phone;

    @NotBlank(message = "The URL of the logo is required")
    private String urlLogo;

    @NotNull(message = "The id of the owner is required")
    private Integer ownerId;
}
