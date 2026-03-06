package com.chapter.foodcourt.insfrastructure.output.feign.repository;


import com.chapter.foodcourt.domain.model.User;
import com.chapter.foodcourt.domain.spi.IUserRepository;
import com.chapter.foodcourt.insfrastructure.output.feign.client.IUserClient;
import com.chapter.foodcourt.insfrastructure.output.feign.dto.response.UserResponseDto;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UserRepositoryImpl implements IUserRepository {
    private final IUserClient usuarioClient;

    @Override
    public Optional<User> getUserById(Integer id) {
        UserResponseDto dto = usuarioClient.getUser(id);
        return Optional.ofNullable(toModel(dto));
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        UserResponseDto dto = usuarioClient.getEmployee(email);
        return Optional.ofNullable(toModel(dto));
    }

    private User toModel(UserResponseDto dto) {
        if (dto == null) return null;
        User user = new User();
        user.setId(dto.getId());
        user.setRoleId(dto.getRoleId());
        return user;
    }
    @Override
    public String getUserPhone(Integer userId) {
        return usuarioClient.getUserPhone(userId);
    }
}
