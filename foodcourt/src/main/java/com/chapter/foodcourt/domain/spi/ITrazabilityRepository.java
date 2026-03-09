package com.chapter.foodcourt.domain.spi;

public interface ITrazabilityRepository {
    void saveLog(Integer orderId, Integer clientId, Integer employeeId, Integer restaurantId,
                 String previousStatus, String newStatus);
}
