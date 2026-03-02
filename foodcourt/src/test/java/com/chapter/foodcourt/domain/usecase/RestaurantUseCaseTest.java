package com.chapter.foodcourt.domain.usecase;

import com.chapter.foodcourt.application.dto.client.response.UserResponseDto;
import com.chapter.foodcourt.domain.exception.InvalidRoleException;
import com.chapter.foodcourt.domain.exception.RestaurantNotFoundException;
import com.chapter.foodcourt.domain.model.Restaurant;
import com.chapter.foodcourt.domain.model.RestaurantEmployee;
import com.chapter.foodcourt.domain.spi.IRestaurantPersistencePort;
import com.chapter.foodcourt.insfrastructure.client.IUserClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.chapter.foodcourt.insfrastructure.configuration.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantUseCaseTest {

    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;

    @Mock
    private IUserClient userClient;

    @Captor
    private ArgumentCaptor<Restaurant> restaurantCaptor;

    @Captor
    private ArgumentCaptor<RestaurantEmployee> restaurantEmployeeCaptor;

    private RestaurantUseCase restaurantUseCase;

    @BeforeEach
    void setUp() {
        restaurantUseCase = new RestaurantUseCase(restaurantPersistencePort, userClient);
    }

    @Test
    void saveRestaurant_whenUserIsOwner_shouldSaveRestaurant() {
        // Arrange
        Integer ownerId = 5;

        Restaurant restaurant = new Restaurant();
        restaurant.setName("My Restaurant");
        restaurant.setNit("123456789");
        restaurant.setAddress("Street 123");
        restaurant.setPhone("+573001234567");
        restaurant.setUrlLogo("http://logo.com/logo.png");
        restaurant.setOwnerId(ownerId);

        UserResponseDto userDto = new UserResponseDto();
        userDto.setId(ownerId);
        userDto.setRoleId(OWNER_ROLE_ID); // Owner Role

        when(userClient.getUser(ownerId)).thenReturn(userDto);

        // Act
        restaurantUseCase.saveRestaurant(restaurant);

        // Assert
        verify(restaurantPersistencePort).saveRestaurant(restaurantCaptor.capture());
        Restaurant restaurantSaved = restaurantCaptor.getValue();

        assertEquals("My Restaurant", restaurantSaved.getName());
        assertEquals("123456789", restaurantSaved.getNit());
        assertEquals("Street 123", restaurantSaved.getAddress());
        assertEquals(ownerId, restaurantSaved.getOwnerId());
    }

    @Test
    void saveRestaurant_whenUserIsNotOwner_shouldThrowException() {
        // Arrange
        Integer userId = 5;

        Restaurant restaurant = new Restaurant();
        restaurant.setOwnerId(userId);

        UserResponseDto userDto = new UserResponseDto();
        userDto.setId(userId);
        userDto.setRoleId(CLIENT_ROLE_ID); // Different role than Owner (2)

        when(userClient.getUser(userId)).thenReturn(userDto);

        // Act & Assert
        InvalidRoleException exception = assertThrows(
                InvalidRoleException.class,
                () -> restaurantUseCase.saveRestaurant(restaurant)
        );

        assertTrue(exception.getMessage().contains("not owner"));
        verify(restaurantPersistencePort, never()).saveRestaurant(any());
    }

    @Test
    void saveRestaurant_withRoleClient_shouldThrowException() {
        // Arrange
        Integer userId = 5;

        Restaurant restaurant = new Restaurant();
        restaurant.setOwnerId(userId);

        UserResponseDto userDto = new UserResponseDto();
        userDto.setId(userId);
        userDto.setRoleId(CLIENT_ROLE_ID); // Client Role

        when(userClient.getUser(userId)).thenReturn(userDto);

        // Act & Assert
        assertThrows(InvalidRoleException.class,
                () -> restaurantUseCase.saveRestaurant(restaurant));
    }

    @Test
    void saveRestaurant_withRoleEmployee_shouldThrowException() {
        // Arrange
        Integer userId = 5;

        Restaurant restaurant = new Restaurant();
        restaurant.setOwnerId(userId);

        UserResponseDto userDto = new UserResponseDto();
        userDto.setId(userId);
        userDto.setRoleId(EMPLOYEE_ROLE_ID); // Employee Role

        when(userClient.getUser(userId)).thenReturn(userDto);

        // Act & Assert
        assertThrows(InvalidRoleException.class,
                () -> restaurantUseCase.saveRestaurant(restaurant));
    }

    @Test
    void saveRestaurant_withAllRequiredFields_shouldSaveSuccessfully() {
        // Arrange
        Integer ownerId = 5;

        Restaurant restaurant = new Restaurant();
        restaurant.setName("Complete Restaurant");
        restaurant.setNit("987654321");
        restaurant.setAddress("Main Avenue 456");
        restaurant.setPhone("+573009876543");
        restaurant.setUrlLogo("http://example.com/logo.jpg");
        restaurant.setOwnerId(ownerId);

        UserResponseDto userDto = new UserResponseDto();
        userDto.setId(ownerId);
        userDto.setRoleId(OWNER_ROLE_ID);

        when(userClient.getUser(ownerId)).thenReturn(userDto);

        // Act
        restaurantUseCase.saveRestaurant(restaurant);

        // Assert
        verify(userClient).getUser(ownerId);
        verify(restaurantPersistencePort).saveRestaurant(restaurant);
    }

    // ==================== TESTS FOR getRestaurant ====================

    @Test
    void getRestaurant_whenRestaurantExists_shouldReturnRestaurant() {
        // Arrange
        Integer restaurantId = 1;

        Restaurant restaurantExpected = new Restaurant();
        restaurantExpected.setId(restaurantId);
        restaurantExpected.setName("My Restaurant");

        when(restaurantPersistencePort.getRestaurant(restaurantId)).thenReturn(restaurantExpected);

        // Act
        Restaurant result = restaurantUseCase.getRestaurant(restaurantId);

        // Assert
        assertNotNull(result);
        assertEquals(restaurantId, result.getId());
        assertEquals("My Restaurant", result.getName());
    }

    @Test
    void getRestaurant_whenRestaurantNotExists_shouldReturnNull() {
        // Arrange
        Integer restaurantId = 999;

        when(restaurantPersistencePort.getRestaurant(restaurantId)).thenReturn(null);

        // Act
        Restaurant result = restaurantUseCase.getRestaurant(restaurantId);

        // Assert
        assertNull(result);
    }

    @Test
    void getRestaurant_shouldCallPersistencePort() {
        // Arrange
        Integer restaurantId = 1;
        when(restaurantPersistencePort.getRestaurant(restaurantId)).thenReturn(new Restaurant());

        // Act
        restaurantUseCase.getRestaurant(restaurantId);

        // Assert
        verify(restaurantPersistencePort).getRestaurant(restaurantId);
    }

    @Test
    void saveRestaurantEmployee_whenValidEmployeeAndRestaurant_shouldSave() {
        // Arrange
        Integer restaurantId = 1;
        Integer employeeId = 10;
        String employeeEmail = "employee@test.com";

        RestaurantEmployee restaurantEmployee = new RestaurantEmployee();
        restaurantEmployee.setEmployeeEmail(employeeEmail);
        restaurantEmployee.setRestaurantId(restaurantId);

        UserResponseDto userDto = new UserResponseDto();
        userDto.setId(employeeId);
        userDto.setRoleId(EMPLOYEE_ROLE_ID); // Employee Role

        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);

        when(userClient.getEmployee(employeeEmail)).thenReturn(userDto);
        when(restaurantPersistencePort.getRestaurant(restaurantId)).thenReturn(restaurant);

        // Act
        restaurantUseCase.saveRestaurantEmployee(restaurantEmployee);

        // Assert
        verify(restaurantPersistencePort).saveRestaurantEmployee(restaurantEmployeeCaptor.capture());
        RestaurantEmployee saved = restaurantEmployeeCaptor.getValue();

        assertEquals(employeeId, saved.getEmployeeId());
        assertEquals(restaurantId, saved.getRestaurantId());
        assertEquals(employeeEmail, saved.getEmployeeEmail());
    }

    @Test
    void saveRestaurantEmployee_whenUserIsNotEmployee_shouldThrowException() {
        // Arrange
        Integer restaurantId = 1;
        String employeeEmail = "client@test.com";

        RestaurantEmployee restaurantEmployee = new RestaurantEmployee();
        restaurantEmployee.setEmployeeEmail(employeeEmail);
        restaurantEmployee.setRestaurantId(restaurantId);

        UserResponseDto userDto = new UserResponseDto();
        userDto.setId(10);
        userDto.setRoleId(CLIENT_ROLE_ID); // Client Role, not Employee (4)

        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);

        when(userClient.getEmployee(employeeEmail)).thenReturn(userDto);
        when(restaurantPersistencePort.getRestaurant(restaurantId)).thenReturn(restaurant);

        // Act & Assert
        InvalidRoleException exception = assertThrows(
                InvalidRoleException.class,
                () -> restaurantUseCase.saveRestaurantEmployee(restaurantEmployee)
        );

        assertTrue(exception.getMessage().contains("not employee"));
        verify(restaurantPersistencePort, never()).saveRestaurantEmployee(any());
    }

    @Test
    void saveRestaurantEmployee_whenRestaurantNotExists_shouldThrowException() {
        // Arrange
        Integer restaurantId = 999;
        String employeeEmail = "employee@test.com";

        RestaurantEmployee restaurantEmployee = new RestaurantEmployee();
        restaurantEmployee.setEmployeeEmail(employeeEmail);
        restaurantEmployee.setRestaurantId(restaurantId);

        UserResponseDto userDto = new UserResponseDto();
        userDto.setId(10);
        userDto.setRoleId(EMPLOYEE_ROLE_ID);

        when(userClient.getEmployee(employeeEmail)).thenReturn(userDto);
        when(restaurantPersistencePort.getRestaurant(restaurantId)).thenReturn(null);

        RestaurantNotFoundException exception = assertThrows(
                RestaurantNotFoundException.class,
                () -> restaurantUseCase.saveRestaurantEmployee(restaurantEmployee)
        );
        assertTrue(exception.getMessage().contains("doesn't exist"));
        verify(restaurantPersistencePort, never()).saveRestaurantEmployee(any());
    }

    @Test
    void saveRestaurantEmployee_withRoleOwner_shouldThrowException() {
        // Arrange
        Integer restaurantId = 1;
        String employeeEmail = "owner@test.com";

        RestaurantEmployee restaurantEmployee = new RestaurantEmployee();
        restaurantEmployee.setEmployeeEmail(employeeEmail);
        restaurantEmployee.setRestaurantId(restaurantId);

        UserResponseDto userDto = new UserResponseDto();
        userDto.setId(10);
        userDto.setRoleId(OWNER_ROLE_ID);

        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);

        when(userClient.getEmployee(employeeEmail)).thenReturn(userDto);
        when(restaurantPersistencePort.getRestaurant(restaurantId)).thenReturn(restaurant);

        assertThrows(InvalidRoleException.class,
                () -> restaurantUseCase.saveRestaurantEmployee(restaurantEmployee));
    }

    @Test
    void saveRestaurantEmployee_withRoleAdmin_shouldThrowException() {
        // Arrange
        Integer restaurantId = 1;
        String employeeEmail = "admin@test.com";

        RestaurantEmployee restaurantEmployee = new RestaurantEmployee();
        restaurantEmployee.setEmployeeEmail(employeeEmail);
        restaurantEmployee.setRestaurantId(restaurantId);

        UserResponseDto userDto = new UserResponseDto();
        userDto.setId(10);
        userDto.setRoleId(ADMIN_ROLE_ID);

        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);

        when(userClient.getEmployee(employeeEmail)).thenReturn(userDto);
        when(restaurantPersistencePort.getRestaurant(restaurantId)).thenReturn(restaurant);

        assertThrows(InvalidRoleException.class,
                () -> restaurantUseCase.saveRestaurantEmployee(restaurantEmployee));
    }

    @Test
    void getRestaurantEmployee_whenExists_shouldReturnRestaurantEmployee() {
        Integer employeeId = 10;
        RestaurantEmployee employeeExpected = new RestaurantEmployee();
        employeeExpected.setId(1);
        employeeExpected.setEmployeeId(employeeId);
        employeeExpected.setRestaurantId(1);
        employeeExpected.setEmployeeEmail("employee@test.com");

        when(restaurantPersistencePort.getRestaurantEmployee(employeeId)).thenReturn(employeeExpected);

        RestaurantEmployee result = restaurantUseCase.getRestaurantEmployee(employeeId);

        assertNotNull(result);
        assertEquals(employeeId, result.getEmployeeId());
        assertEquals("employee@test.com", result.getEmployeeEmail());
    }

    @Test
    void getRestaurantEmployee_whenNotExists_shouldReturnNull() {
        Integer employeeId = 999;
        when(restaurantPersistencePort.getRestaurantEmployee(employeeId)).thenReturn(null);
        RestaurantEmployee result = restaurantUseCase.getRestaurantEmployee(employeeId);
        assertNull(result);
    }

    @Test
    void getRestaurantEmployee_shouldCallPersistencePort() {
        Integer employeeId = 10;
        when(restaurantPersistencePort.getRestaurantEmployee(employeeId)).thenReturn(null);
        restaurantUseCase.getRestaurantEmployee(employeeId);
        verify(restaurantPersistencePort).getRestaurantEmployee(employeeId);
    }
}
