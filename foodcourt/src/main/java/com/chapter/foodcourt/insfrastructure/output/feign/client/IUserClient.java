package com.chapter.foodcourt.insfrastructure.output.feign.client;

import com.chapter.foodcourt.insfrastructure.output.feign.dto.response.UserResponseDto;
import com.chapter.foodcourt.insfrastructure.output.feign.config.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "user",
        url = "${ms-user.url}",
        configuration = FeignConfiguration.class
)
public interface IUserClient {

    @GetMapping(value = "/admin/owner/{id}")
    UserResponseDto getUser(@PathVariable("id") Integer id);

    @GetMapping(value = "/owner/employee/{email}")
    UserResponseDto getEmployee(@PathVariable("email") String email);

    @GetMapping(value = "/client/{userId}/phone")
    String getUserPhone(@PathVariable Integer userId);
}
