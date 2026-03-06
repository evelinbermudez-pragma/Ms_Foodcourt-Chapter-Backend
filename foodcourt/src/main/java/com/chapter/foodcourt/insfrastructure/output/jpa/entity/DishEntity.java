package com.chapter.foodcourt.insfrastructure.output.jpa.entity;

import com.chapter.foodcourt.domain.model.Category;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "dish")
@Getter
@Setter
public class DishEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;
    private String name;
    private String description;
    private Double price;
    private String imageUrl;
    private Integer restaurantId;
    private boolean active;
    @Enumerated(EnumType.STRING)
    private Category category;
}
