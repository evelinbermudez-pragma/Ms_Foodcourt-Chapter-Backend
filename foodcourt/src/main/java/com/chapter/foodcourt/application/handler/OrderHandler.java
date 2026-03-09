package com.chapter.foodcourt.application.handler;

import com.chapter.foodcourt.application.dto.request.OrderRequestDto;
import com.chapter.foodcourt.application.dto.response.OrderResponseDto;
import com.chapter.foodcourt.application.handler.interfaces.IOrderHandler;
import com.chapter.foodcourt.application.mapper.request.OrderRequestMapper;
import com.chapter.foodcourt.application.mapper.response.OrderResponseMapper;
import com.chapter.foodcourt.domain.api.IOrderServicePort;
import com.chapter.foodcourt.domain.model.Order;
import com.chapter.foodcourt.domain.model.Status;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderHandler implements IOrderHandler {

    private final IOrderServicePort orderServicePort;
    private final OrderRequestMapper orderRequestMapper;
    private final OrderResponseMapper orderResponseMapper;

    @Override
    public void createOrder(OrderRequestDto orderRequestDto) {
        Order order = orderRequestMapper.toOrder(orderRequestDto);
        orderServicePort.createOrder(order);
    }
    @Override
    public Page<OrderResponseDto> listOrdersByStatus(Status status, int page, int size) {
        return orderServicePort
                .listOrdersByStatus(status, page, size)
                .map(orderResponseMapper::toOrderResponseDto);
    }
    @Override
    public void assignOrderAndChangeStatus(Integer orderId) {
        orderServicePort.assignOrderAndChangeStatus(orderId);
    }
    @Override
    public void notifyOrderReady(Integer orderId) {
        orderServicePort.notifyOrderReady(orderId);
    }
    @Override
    public void deliverOrder(Integer orderId, String pin) {
        orderServicePort.deliverOrder(orderId, pin);
    }
    @Override
    public void cancelOrder(Integer orderId) {
        orderServicePort.cancelOrder(orderId);
    }

    }


