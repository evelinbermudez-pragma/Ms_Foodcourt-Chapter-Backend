package com.chapter.foodcourt.insfrastructure.output.feign.client;


import com.chapter.foodcourt.insfrastructure.output.feign.config.FeignConfiguration;
import com.chapter.foodcourt.insfrastructure.output.feign.dto.request.SmsRequestDto;
import com.chapter.foodcourt.insfrastructure.output.feign.dto.response.UserResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "twilio",
        url = "http://localhost:8083",
        configuration = FeignConfiguration.class
)
public interface ISmsClient {

    @PostMapping(value = "/sms/send")
    void sendSms(@RequestBody SmsRequestDto smsRequest);
}
