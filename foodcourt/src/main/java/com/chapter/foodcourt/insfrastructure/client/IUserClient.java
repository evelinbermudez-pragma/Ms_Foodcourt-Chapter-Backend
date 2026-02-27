package com.chapter.foodcourt.insfrastructure.client;

import com.chapter.foodcourt.application.dto.client.response.UserResponseDto;
import com.chapter.foodcourt.insfrastructure.configuration.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "user",
        url = "http://localhost:8081",
        configuration = FeignConfiguration.class
)
public interface IUserClient {

    @GetMapping("/user/admin/owner/{id}")
    UserResponseDto getOwner(@PathVariable("id") Integer id);
}
