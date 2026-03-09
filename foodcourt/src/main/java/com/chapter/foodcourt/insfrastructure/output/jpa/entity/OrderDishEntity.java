package com.chapter.foodcourt.insfrastructure.output.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "order_dish")
@Getter
@Setter
public class OrderDishEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;
    private Integer dishId;
    private Integer quantity;
    private String dishName;
    private String dishDescription;
    private Double dishPrice;
    private String dishCategory;
    private String dishImageUrl;
}