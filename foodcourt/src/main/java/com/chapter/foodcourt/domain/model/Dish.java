package com.chapter.foodcourt.domain.model;

public class Dish {
    private Integer id;
    private String name;
    private String description;
    private Double price;
    private String imageUrl;
    private Category category;
    private boolean active;
    private Integer restaurantId;

    public Dish() {
    }
    public Dish(Integer id, String name, String description, Double price, String imageUrl, Category category, Integer restaurantId, boolean active) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
        this.active = active;
        this.restaurantId = restaurantId;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;}
    public String getName() {
        return name;}
    public void setName(String name) {
        this.name = name;}
    public String getDescription() {
        return description;}
    public void setDescription(String description) {
        this.description = description;}
    public Double getPrice() {
        return price;}
    public void setPrice(Double price) {
        this.price = price;}
    public String getImageUrl() {
        return imageUrl;}
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;}
    public Category getCategory() {
        return category;}
    public void setCategory(Category category) {
        this.category = category;}
    public Integer getRestaurantId() {
        return restaurantId;}
    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }
}
