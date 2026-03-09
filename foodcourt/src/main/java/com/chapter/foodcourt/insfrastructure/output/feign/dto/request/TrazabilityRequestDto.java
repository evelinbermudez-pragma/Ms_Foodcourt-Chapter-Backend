package com.chapter.foodcourt.insfrastructure.output.feign.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrazabilityRequestDto {
    private Integer orderId;
    private Integer clientId;
    private Integer employeeId;
    private String previousStatus;
    private String newStatus;
    private Integer restaurantId;
}
