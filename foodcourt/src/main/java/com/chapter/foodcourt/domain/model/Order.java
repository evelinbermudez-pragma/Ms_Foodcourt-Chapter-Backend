package com.chapter.foodcourt.domain.model;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private Integer id;
    private Integer clientId;
    private Integer restaurantId;
    private LocalDateTime date;
    private Status status;
    private Integer employeeId;
    private String securityPin;
    private List<OrderDish> orderDishes;

    public Order(Integer id, Integer restaurantId, Integer clientId, LocalDateTime date, Integer employeeId, Status status, String securityPin, List<OrderDish> orderDishes) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.clientId = clientId;
        this.date = date;
        this.employeeId = employeeId;
        this.status = status;
        this.securityPin = securityPin;
        this.orderDishes = orderDishes;
    }
    public Order(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public List<OrderDish> getOrderDishes() {
        return orderDishes;
    }

    public void setOrderDishes(List<OrderDish> orderDishes) {
        this.orderDishes = orderDishes;
    }

    public String getSecurityPin() {
        return securityPin;
    }

    public void setSecurityPin(String securityPin) {
        this.securityPin = securityPin;
    }
}
