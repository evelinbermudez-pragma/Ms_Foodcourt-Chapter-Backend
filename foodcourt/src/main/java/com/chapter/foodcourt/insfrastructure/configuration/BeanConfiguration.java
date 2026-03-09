package com.chapter.foodcourt.insfrastructure.configuration;

import com.chapter.foodcourt.domain.api.IDishServicePort;
import com.chapter.foodcourt.domain.api.IOrderServicePort;
import com.chapter.foodcourt.domain.api.IRestaurantServicePort;
import com.chapter.foodcourt.domain.spi.*;
import com.chapter.foodcourt.domain.usecase.DishUseCase;
import com.chapter.foodcourt.domain.usecase.OrderUseCase;
import com.chapter.foodcourt.domain.usecase.RestaurantUseCase;
import com.chapter.foodcourt.insfrastructure.output.feign.client.ISmsClient;
import com.chapter.foodcourt.insfrastructure.output.feign.client.ITrazabilityClient;
import com.chapter.foodcourt.insfrastructure.output.feign.client.IUserClient;
import com.chapter.foodcourt.insfrastructure.output.feign.repository.SmsRepositoryImpl;
import com.chapter.foodcourt.insfrastructure.output.feign.repository.TrazabilityRepositoryImpl;
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
    public IOrderServicePort orderServicePort(IOrderPersistencePort orderPersistencePort, IDishPersistencePort dishPersistencePort, IRestaurantPersistencePort restaurantPersistencePort, ISmsRepository smsRepository, IUserRepository userRepository, ITrazabilityRepository traceabilityRepository, IAuthenticationPort authenticationPort) {
        return new OrderUseCase(orderPersistencePort, dishPersistencePort, restaurantPersistencePort, smsRepository, userRepository, traceabilityRepository, authenticationPort);
    }
    @Bean
    public ISmsRepository smsPersistencePort(ISmsClient smsClient) {
        return new SmsRepositoryImpl(smsClient);
    }
    @Bean
    public ITrazabilityRepository traceabilityPersistencePort(ITrazabilityClient traceabilityClient) {
        return new TrazabilityRepositoryImpl(traceabilityClient);
    }
}