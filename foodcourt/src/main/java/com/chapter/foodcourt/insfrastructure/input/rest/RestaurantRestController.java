package com.chapter.foodcourt.insfrastructure.input.rest;

import com.chapter.foodcourt.application.dto.request.RestaurantEmployeeRequestDto;
import com.chapter.foodcourt.application.dto.request.RestaurantRequestDto;
import com.chapter.foodcourt.application.dto.response.RestaurantEmployeeResponseDto;
import com.chapter.foodcourt.application.dto.response.RestaurantResponseDto;
import com.chapter.foodcourt.application.handler.interfaces.IRestaurantHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurant")
@RequiredArgsConstructor
public class RestaurantRestController {
    private final IRestaurantHandler restaurantHandler;

    @PostMapping("/create")
    public ResponseEntity<Void> saveRestaurant(
            @Valid @RequestBody RestaurantRequestDto restaurantRequestDto) {
        restaurantHandler.saveRestaurant(restaurantRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponseDto> getRestaurant(@PathVariable("id") Integer id, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(restaurantHandler.getRestaurant(id));
    }
    @PostMapping("/create/employee")
    public ResponseEntity<Void> saveRestaurantEmployee(@Valid @RequestBody RestaurantEmployeeRequestDto restaurantEmployeeRequestDto) {
        restaurantHandler.saveRestaurantEmployee(restaurantEmployeeRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping("/employee/{id}")
    public ResponseEntity<RestaurantEmployeeResponseDto> getRestaurantEmployee(@PathVariable("id") Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(restaurantHandler.getRestaurantEmployee(id));
    }
}
