package com.chapter.foodcourt.application.handler;

import com.chapter.foodcourt.application.dto.request.DishRequestDto;
import com.chapter.foodcourt.application.dto.request.UpdateDishRequestDto;
import com.chapter.foodcourt.application.dto.response.DishResponseDto;
import com.chapter.foodcourt.application.handler.interfaces.IDishHandler;
import com.chapter.foodcourt.application.mapper.request.DishRequestMapper;
import com.chapter.foodcourt.application.mapper.request.UpdateDishRequestMapper;
import com.chapter.foodcourt.application.mapper.response.DishResponseMapper;
import com.chapter.foodcourt.domain.api.IDishServicePort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class DishHandler implements IDishHandler {

    private final IDishServicePort dishServicePort;
    private final DishRequestMapper dishRequestMapper;
    private final DishResponseMapper dishResponseMapper;
    private final UpdateDishRequestMapper updateDishRequestMapper;

    @Override
    public void saveDish(DishRequestDto dishRequestDto, Integer userId) {
        dishServicePort.saveDish(dishRequestMapper.toDish(dishRequestDto),userId);}

    @Override
    public DishResponseDto getDish(Integer id) {
        return dishResponseMapper.toDishResponseDto(dishServicePort.getDish(id));
    }
    @Override
    public void updateDish(UpdateDishRequestDto updateDishRequestDto, Integer id, Integer userId) {
        dishServicePort.updateDish(updateDishRequestMapper.toDish(updateDishRequestDto),id,userId);
    }
}
