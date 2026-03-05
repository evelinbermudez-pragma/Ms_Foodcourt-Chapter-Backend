package com.chapter.foodcourt.insfrastructure.input.rest;

import com.chapter.foodcourt.application.dto.request.OrderRequestDto;
import com.chapter.foodcourt.application.dto.response.OrderResponseDto;
import com.chapter.foodcourt.application.handler.interfaces.IOrderHandler;
import com.chapter.foodcourt.domain.model.Status;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/state/{state}")
    public ResponseEntity<Page<OrderResponseDto>> listOrdersByStatus(
            @PathVariable Status state,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request) {
        Integer employeeId = (Integer) request.getAttribute("userId");
        return ResponseEntity.ok(
                orderHandler.listOrdersByStatus(state, employeeId, page, size));
    }
}
