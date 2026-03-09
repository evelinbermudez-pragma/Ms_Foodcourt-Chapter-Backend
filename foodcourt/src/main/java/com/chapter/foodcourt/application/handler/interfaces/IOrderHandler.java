package com.chapter.foodcourt.application.handler.interfaces;

import com.chapter.foodcourt.application.dto.request.OrderRequestDto;
import com.chapter.foodcourt.application.dto.response.OrderResponseDto;
import com.chapter.foodcourt.domain.model.Status;
import org.springframework.data.domain.Page;

public interface IOrderHandler {
    void createOrder(OrderRequestDto orderRequestDto);
    Page<OrderResponseDto> listOrdersByStatus(Status status, int page, int size);
    void assignOrderAndChangeStatus(Integer orderId);
    void notifyOrderReady(Integer orderId);
    void deliverOrder(Integer orderId, String pin);
    void cancelOrder(Integer orderId);
}
