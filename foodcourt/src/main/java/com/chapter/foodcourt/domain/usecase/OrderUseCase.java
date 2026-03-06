package com.chapter.foodcourt.domain.usecase;

import com.chapter.foodcourt.domain.api.IOrderServicePort;
import com.chapter.foodcourt.domain.exception.ClientHasActiveOrderException;
import com.chapter.foodcourt.domain.exception.DishNotFromRestaurantException;
import com.chapter.foodcourt.domain.exception.OrderNotPendingException;
import com.chapter.foodcourt.domain.model.*;
import com.chapter.foodcourt.domain.spi.*;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

public class OrderUseCase implements IOrderServicePort {
    private final IOrderPersistencePort orderPersistencePort;
    private final IDishPersistencePort dishPersistencePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final ISmsRepository smsRepository;
    private final IUserRepository userRepository;

    public OrderUseCase(IOrderPersistencePort orderPersistencePort, IDishPersistencePort dishPersistencePort, IRestaurantPersistencePort restaurantPersistencePort, ISmsRepository smsRepository, IUserRepository userRepository) {
         this.restaurantPersistencePort = restaurantPersistencePort;
        this.orderPersistencePort = orderPersistencePort;
        this.dishPersistencePort = dishPersistencePort;
        this.smsRepository = smsRepository;
        this.userRepository = userRepository;
    }
    @Override
    public void createOrder(Order order, Integer clientId) {

        if (orderPersistencePort.hasActiveOrders(clientId)) {
            throw new ClientHasActiveOrderException("You already have an active order");
        }
        for (OrderDish orderDish : order.getOrderDishes()) {
            Dish dish = dishPersistencePort.getDish(orderDish.getDishId());
            if (!dish.getRestaurantId().equals(order.getRestaurantId())) {
                throw new DishNotFromRestaurantException("Dish does not belong to this restaurant");
            }
        }
        order.setClientId(clientId);
        order.setStatus(Status.PENDING);
        order.setDate(LocalDateTime.now());
        orderPersistencePort.saveOrder(order);
    }

    @Override
    public Page<Order> listOrdersByStatus(Status status, Integer employeeId, int page, int size) {
        // busca el restaurante del empleado
        RestaurantEmployee restaurantEmployee = restaurantPersistencePort
                .getRestaurantOfEmployee(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not assigned to any restaurant"));

        return orderPersistencePort.listOrdersByStatus(
                status,
                restaurantEmployee.getRestaurantId(),
                page,
                size
        );
    }
    @Override
    public void assignOrderAndChangeStatus(Integer orderId, Integer employeeId) {
        Order order = orderPersistencePort.getOrder(orderId);

        // Solo se pueden asignar pedidos en PENDING
        if (!order.getStatus().equals(Status.PENDING)) {
            throw new OrderNotPendingException("Order is not in PENDING status");
        }

        order.setEmployeeId(employeeId);
        order.setStatus(Status.IN_PREPARATION);
        orderPersistencePort.saveOrder(order);
    }
    @Override
    public void notifyOrderReady(Integer orderId, Integer employeeId) {
        Order order = orderPersistencePort.getOrder(orderId);

        if (!order.getStatus().equals(Status.IN_PREPARATION)) {
            throw new RuntimeException("Order is not IN_PREPARATION");
        }

        // Genera pin de 4 dígitos
        String pin = String.valueOf((int)(Math.random() * 9000) + 1000);

        // Cambia estado y guarda pin
        order.setStatus(Status.READY);
        order.setSecurityPin(pin);
        orderPersistencePort.saveOrder(order);

        // Obtiene teléfono y envía SMS
        String clientPhone = userRepository.getUserPhone(order.getClientId());
        smsRepository.sendSms(
                clientPhone,
                "Tu pedido está listo. Pin de seguridad: " + pin
        );
    }
    @Override
    public void deliverOrder(Integer orderId, Integer employeeId, String pin) {
        Order order = orderPersistencePort.getOrder(orderId);

        if (!order.getStatus().equals(Status.READY)) {
            throw new RuntimeException("Order is not in READY status");
        }

        if (!order.getSecurityPin().equals(pin)) {
            throw new RuntimeException("Invalid security pin");
        }

        order.setStatus(Status.DELIVERED);
        orderPersistencePort.saveOrder(order);
    }
    @Override
    public void cancelOrder(Integer orderId, Integer clientId) {
        Order order = orderPersistencePort.getOrder(orderId);

        if (!order.getClientId().equals(clientId)) {
            throw new RuntimeException("Order does not belong to this client");
        }

        if (!order.getStatus().equals(Status.PENDING)) {
            smsRepository.sendSms(
                    userRepository.getUserPhone(clientId),
                    "Lo sentimos, tu pedido ya está en preparación y no puede cancelarse"
            );
            throw new RuntimeException("Order cannot be cancelled");
        }

        order.setStatus(Status.CANCELLED);
        orderPersistencePort.saveOrder(order);
    }
}
