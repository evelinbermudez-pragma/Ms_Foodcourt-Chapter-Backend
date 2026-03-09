package com.chapter.foodcourt.insfrastructure.input.rest;

import com.chapter.foodcourt.application.dto.request.OrderRequestDto;
import com.chapter.foodcourt.application.dto.response.OrderResponseDto;
import com.chapter.foodcourt.application.handler.interfaces.IOrderHandler;
import com.chapter.foodcourt.domain.model.Status;
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
    public ResponseEntity<Void> createOrder(@Valid @RequestBody OrderRequestDto orderRequestDto) {
        orderHandler.createOrder(orderRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping("/state/{state}")
    public ResponseEntity<Page<OrderResponseDto>> listOrdersByStatus(
            @PathVariable Status state,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(orderHandler.listOrdersByStatus(state, page, size));
    }

    @PatchMapping("/assign/{orderId}")
    public ResponseEntity<Void> assignOrder(@PathVariable Integer orderId) {
        orderHandler.assignOrderAndChangeStatus(orderId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/ready/{orderId}")
    public ResponseEntity<Void> notifyOrderReady(@PathVariable Integer orderId) {
        orderHandler.notifyOrderReady(orderId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/deliver/{orderId}")
    public ResponseEntity<Void> deliverOrder(
            @PathVariable Integer orderId,
            @RequestParam String pin) {
        orderHandler.deliverOrder(orderId, pin);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/cancel/{orderId}")
    public ResponseEntity<Void> cancelOrder(@PathVariable Integer orderId) {
        orderHandler.cancelOrder(orderId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
