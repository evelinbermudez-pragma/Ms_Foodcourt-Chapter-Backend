package com.chapter.foodcourt.application.dto.response;
import com.chapter.foodcourt.domain.model.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DishResponseDto {
    private String name;
    private String description;
    private Double price;
    private Category category;
    private String imageUrl;

}
