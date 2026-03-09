package com.chapter.foodcourt.insfrastructure.output.feign.client;

import com.chapter.foodcourt.insfrastructure.output.feign.config.FeignConfiguration;
import com.chapter.foodcourt.insfrastructure.output.feign.dto.request.TrazabilityRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "trazability",
        url = "${ms-traceability.url}",
        configuration = FeignConfiguration.class
)
public interface ITrazabilityClient {

    @PostMapping("/traceability")
    void saveLog(@RequestBody TrazabilityRequestDto dto);
}
