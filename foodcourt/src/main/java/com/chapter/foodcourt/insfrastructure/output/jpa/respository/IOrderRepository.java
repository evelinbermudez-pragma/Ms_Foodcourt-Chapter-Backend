package com.chapter.foodcourt.insfrastructure.output.jpa.respository;

import com.chapter.foodcourt.domain.model.Status;

import java.util.List;

public interface IOrderRepository {
    boolean existsByClientIdAndStatusIn(Integer clientId, List<Status> statuses);
}
