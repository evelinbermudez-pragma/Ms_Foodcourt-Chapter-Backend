package com.chapter.foodcourt.insfrastructure.input.rest;

import com.chapter.foodcourt.application.dto.request.DishRequestDto;
import com.chapter.foodcourt.application.dto.request.UpdateDishRequestDto;
import com.chapter.foodcourt.application.dto.response.DishResponseDto;
import com.chapter.foodcourt.application.handler.interfaces.IDishHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dish")
@RequiredArgsConstructor
public class DishRestController {
    private final IDishHandler dishHandler;

    @PostMapping("/create")
    public ResponseEntity<Void> saveDish(@Valid @RequestBody DishRequestDto dishRequestDto, HttpServletRequest request) {
       Integer userId = (Integer) request.getAttribute("userId");
        dishHandler.saveDish(dishRequestDto , userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<DishResponseDto> getDish(@PathVariable("id") Integer id){
        return ResponseEntity.status(HttpStatus.OK).body(dishHandler.getDish(id));
    }
    @PatchMapping("/update/{id}")
    public ResponseEntity <Void> updateDish(@PathVariable Integer id, @Valid @RequestBody UpdateDishRequestDto updateDishRequestDto, HttpServletRequest request){
        Integer userId = (Integer) request.getAttribute("userId");
        dishHandler.updateDish(updateDishRequestDto, id , userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
     }
    @PatchMapping("/{id}/toggle")
    public ResponseEntity<Void> toggleDish(
            @PathVariable("id") Integer dishId,
            @RequestParam Boolean active,
            HttpServletRequest request) {

        Integer userId = (Integer) request.getAttribute("userId");
        dishHandler.toggleDish(dishId, active, userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
