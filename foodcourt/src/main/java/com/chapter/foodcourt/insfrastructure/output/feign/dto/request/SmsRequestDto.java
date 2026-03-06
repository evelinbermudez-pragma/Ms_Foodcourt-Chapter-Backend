package com.chapter.foodcourt.insfrastructure.output.feign.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmsRequestDto {
    private String phone;
    private String message;
}
