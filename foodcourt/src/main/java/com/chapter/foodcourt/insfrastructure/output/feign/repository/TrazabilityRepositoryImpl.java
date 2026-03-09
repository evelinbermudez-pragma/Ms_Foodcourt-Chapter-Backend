package com.chapter.foodcourt.insfrastructure.output.feign.repository;

import com.chapter.foodcourt.domain.spi.ITrazabilityRepository;
import com.chapter.foodcourt.insfrastructure.output.feign.client.ITrazabilityClient;
import com.chapter.foodcourt.insfrastructure.output.feign.dto.request.TrazabilityRequestDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TrazabilityRepositoryImpl implements ITrazabilityRepository {

    private final ITrazabilityClient traceabilityClient;

    @Override
    public void saveLog(Integer orderId, Integer clientId, Integer employeeId,
                        Integer restaurantId, String previousStatus, String newStatus) {
        traceabilityClient.saveLog(new TrazabilityRequestDto(
                orderId, clientId, employeeId, previousStatus, newStatus, restaurantId));
    }
}
