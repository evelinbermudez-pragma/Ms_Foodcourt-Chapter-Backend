package com.chapter.foodcourt.application.handler.interfaces;

import com.chapter.foodcourt.application.dto.request.OrderRequestDto;

public interface IOrderHandler {
    void createOrder(OrderRequestDto orderRequestDto, Integer clientId);
}
