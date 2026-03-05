package com.chapter.foodcourt.insfrastructure.configuration;

import com.chapter.foodcourt.domain.api.IDishServicePort;
import com.chapter.foodcourt.domain.api.IOrderServicePort;
import com.chapter.foodcourt.domain.api.IRestaurantServicePort;
import com.chapter.foodcourt.domain.spi.IDishPersistencePort;
import com.chapter.foodcourt.domain.spi.IOrderPersistencePort;
import com.chapter.foodcourt.domain.spi.IRestaurantPersistencePort;
import com.chapter.foodcourt.domain.spi.IUserRepository;
import com.chapter.foodcourt.domain.usecase.DishUseCase;
import com.chapter.foodcourt.domain.usecase.OrderUseCase;
import com.chapter.foodcourt.domain.usecase.RestaurantUseCase;
import com.chapter.foodcourt.insfrastructure.output.feign.client.IUserClient;
import com.chapter.foodcourt.insfrastructure.output.feign.repository.UserRepositoryImpl;
import com.chapter.foodcourt.insfrastructure.output.jpa.adapter.DishJpaAdapter;
import com.chapter.foodcourt.insfrastructure.output.jpa.adapter.RestaurantJpaAdapter;
import com.chapter.foodcourt.insfrastructure.output.jpa.mapper.DishEntityMapper;
import com.chapter.foodcourt.insfrastructure.output.jpa.mapper.RestaurantEmployeeEntityMapper;
import com.chapter.foodcourt.insfrastructure.output.jpa.mapper.RestaurantEntityMapper;
import com.chapter.foodcourt.insfrastructure.output.jpa.respository.IDishRepository;
import com.chapter.foodcourt.insfrastructure.output.jpa.respository.IRestaurantEmployeeRepository;
import com.chapter.foodcourt.insfrastructure.output.jpa.respository.IRestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IRestaurantRepository restaurantRepository;
    private final RestaurantEntityMapper restaurantEntityMapper;
    private final IRestaurantEmployeeRepository restaurantEmployeeRepository;
    private final RestaurantEmployeeEntityMapper restaurantEmployeeEntityMapper;
    private final IDishRepository dishRepository;
    private final DishEntityMapper dishEntityMapper;
    private final IUserClient userClient;

    @Bean
    public IUserRepository userRepository() {
        return new UserRepositoryImpl(userClient);
    }

    @Bean
    public IRestaurantPersistencePort restaurantPersistencePort() {
        return new RestaurantJpaAdapter(
                restaurantRepository,
                restaurantEntityMapper,
                restaurantEmployeeRepository,
                restaurantEmployeeEntityMapper
        );
    }

    @Bean
    public IRestaurantServicePort restaurantServicePort() {
        return new RestaurantUseCase(
                restaurantPersistencePort(),
                userRepository()
        );
    }
    @Bean
    public IDishPersistencePort dishPersistencePort() {
        return new DishJpaAdapter(dishRepository, dishEntityMapper);
    }

    @Bean
    public IDishServicePort dishServicePort() {
        return new DishUseCase(dishPersistencePort(), restaurantPersistencePort());
    }
    @Bean
    public IOrderServicePort orderServicePort(IOrderPersistencePort orderPersistencePort, IDishPersistencePort dishPersistencePort) {
        return new OrderUseCase(orderPersistencePort, dishPersistencePort);
    }
}