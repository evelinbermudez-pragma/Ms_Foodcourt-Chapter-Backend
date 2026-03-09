package com.chapter.foodcourt.domain.model;

public class OrderDish {
    private Integer id;
    private Integer orderId;
    private Integer dishId;
    private Integer quantity;
    private String dishName;
    private String dishDescription;
    private Double dishPrice;
    private String dishCategory;
    private String dishImageUrl;

    public OrderDish(Integer id, Integer orderId, Integer dishId, Integer quantity, String dishName, String dishDescription, Double dishPrice, String dishCategory, String dishImageUrl) {
        this.id = id;
        this.orderId = orderId;
        this.dishId = dishId;
        this.quantity = quantity;
        this.dishName = dishName;
        this.dishDescription = dishDescription;
        this.dishPrice = dishPrice;
        this.dishCategory = dishCategory;
        this.dishImageUrl = dishImageUrl;
    }

    public OrderDish() {
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getDishDescription() {
        return dishDescription;
    }

    public void setDishDescription(String dishDescription) {
        this.dishDescription = dishDescription;
    }

    public Double getDishPrice() {
        return dishPrice;
    }

    public void setDishPrice(Double dishPrice) {
        this.dishPrice = dishPrice;
    }

    public String getDishCategory() {
        return dishCategory;
    }

    public void setDishCategory(String dishCategory) {
        this.dishCategory = dishCategory;
    }

    public String getDishImageUrl() {
        return dishImageUrl;
    }

    public void setDishImageUrl(String dishImageUrl) {
        this.dishImageUrl = dishImageUrl;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDishId() {
        return dishId;
    }

    public void setDishId(Integer dishId) {
        this.dishId = dishId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
