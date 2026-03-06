package com.chapter.foodcourt.insfrastructure.input.rest;

import com.chapter.foodcourt.application.dto.request.OrderRequestDto;
import com.chapter.foodcourt.application.handler.interfaces.IOrderHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderRestController {

    private final IOrderHandler orderHandler;

    @PostMapping
    public ResponseEntity<Void> createOrder(@Valid @RequestBody OrderRequestDto orderRequestDto, HttpServletRequest request) {
        Integer clientId = (Integer) request.getAttribute("userId");
        orderHandler.createOrder(orderRequestDto, clientId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
