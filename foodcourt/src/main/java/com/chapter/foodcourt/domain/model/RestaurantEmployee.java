package com.chapter.foodcourt.domain.model;

public class RestaurantEmployee {

    private Integer id;
    private String employeeEmail;
    private Integer employeeId;
    private Integer restaurantId;

    public RestaurantEmployee() {
    }

    public RestaurantEmployee(Integer id, String employeeEmail, Integer employeeId, Integer restaurantId) {
        this.id = id;
        this.employeeEmail = employeeEmail;
        this.employeeId = employeeId;
        this.restaurantId = restaurantId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }
}
