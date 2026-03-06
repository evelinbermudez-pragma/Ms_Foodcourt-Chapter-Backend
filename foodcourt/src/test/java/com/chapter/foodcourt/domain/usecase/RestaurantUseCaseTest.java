package com.chapter.foodcourt.domain.usecase;

import com.chapter.foodcourt.domain.model.User;
import com.chapter.foodcourt.domain.spi.IUserRepository;
import com.chapter.foodcourt.domain.exception.InvalidRoleException;
import com.chapter.foodcourt.domain.exception.RestaurantNotFoundException;
import com.chapter.foodcourt.domain.model.Restaurant;
import com.chapter.foodcourt.domain.model.RestaurantEmployee;
import com.chapter.foodcourt.domain.spi.IRestaurantPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.chapter.foodcourt.insfrastructure.configuration.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantUseCaseTest {

    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;

    @Mock
    private IUserRepository userRepository;

    @Captor
    private ArgumentCaptor<Restaurant> restaurantCaptor;

    @Captor
    private ArgumentCaptor<RestaurantEmployee> restaurantEmployeeCaptor;

    private RestaurantUseCase restaurantUseCase;

    @BeforeEach
    void setUp() {
        restaurantUseCase = new RestaurantUseCase(restaurantPersistencePort, userRepository);
    }


    private User buildUser(Integer id, Integer roleId) {
        User user = new User();
        user.setId(id);
        user.setRoleId(roleId);
        return user;
    }


    @Test
    void saveRestaurant_whenUserIsOwner_shouldSaveRestaurant() {
        Integer ownerId = 5;

        Restaurant restaurant = new Restaurant();
        restaurant.setName("My Restaurant");
        restaurant.setNit("123456789");
        restaurant.setAddress("Street 123");
        restaurant.setPhone("+573001234567");
        restaurant.setUrlLogo("http://logo.com/logo.png");
        restaurant.setOwnerId(ownerId);


        when(userRepository.getUserById(ownerId))
                .thenReturn(Optional.of(buildUser(ownerId, OWNER_ROLE_ID)));

        restaurantUseCase.saveRestaurant(restaurant);

        verify(restaurantPersistencePort).saveRestaurant(restaurantCaptor.capture());
        Restaurant saved = restaurantCaptor.getValue();

        assertEquals("My Restaurant", saved.getName());
        assertEquals("123456789", saved.getNit());
        assertEquals("Street 123", saved.getAddress());
        assertEquals(ownerId, saved.getOwnerId());
    }

    @Test
    void saveRestaurant_whenUserIsNotOwner_shouldThrowException() {
        Integer userId = 5;

        Restaurant restaurant = new Restaurant();
        restaurant.setOwnerId(userId);

        when(userRepository.getUserById(userId))
                .thenReturn(Optional.of(buildUser(userId, CLIENT_ROLE_ID)));

        InvalidRoleException exception = assertThrows(
                InvalidRoleException.class,
                () -> restaurantUseCase.saveRestaurant(restaurant)
        );

        assertTrue(exception.getMessage().contains("not owner"));
        verify(restaurantPersistencePort, never()).saveRestaurant(any());
    }

    @Test
    void saveRestaurant_withRoleClient_shouldThrowException() {
        Integer userId = 5;

        Restaurant restaurant = new Restaurant();
        restaurant.setOwnerId(userId);

        when(userRepository.getUserById(userId))
                .thenReturn(Optional.of(buildUser(userId, CLIENT_ROLE_ID)));

        assertThrows(InvalidRoleException.class,
                () -> restaurantUseCase.saveRestaurant(restaurant));
    }

    @Test
    void saveRestaurant_withRoleEmployee_shouldThrowException() {
        Integer userId = 5;

        Restaurant restaurant = new Restaurant();
        restaurant.setOwnerId(userId);

        when(userRepository.getUserById(userId))
                .thenReturn(Optional.of(buildUser(userId, EMPLOYEE_ROLE_ID)));

        assertThrows(InvalidRoleException.class,
                () -> restaurantUseCase.saveRestaurant(restaurant));
    }

    @Test
    void saveRestaurant_withAllRequiredFields_shouldSaveSuccessfully() {
        Integer ownerId = 5;

        Restaurant restaurant = new Restaurant();
        restaurant.setName("Complete Restaurant");
        restaurant.setNit("987654321");
        restaurant.setAddress("Main Avenue 456");
        restaurant.setPhone("+573009876543");
        restaurant.setUrlLogo("http://example.com/logo.jpg");
        restaurant.setOwnerId(ownerId);

        when(userRepository.getUserById(ownerId))
                .thenReturn(Optional.of(buildUser(ownerId, OWNER_ROLE_ID)));

        restaurantUseCase.saveRestaurant(restaurant);

        verify(userRepository).getUserById(ownerId);
        verify(restaurantPersistencePort).saveRestaurant(restaurant);
    }


    @Test
    void getRestaurant_whenRestaurantExists_shouldReturnRestaurant() {
        Integer restaurantId = 1;

        Restaurant restaurantExpected = new Restaurant();
        restaurantExpected.setId(restaurantId);
        restaurantExpected.setName("My Restaurant");

        when(restaurantPersistencePort.getRestaurant(restaurantId))
                .thenReturn(restaurantExpected);

        Restaurant result = restaurantUseCase.getRestaurant(restaurantId);

        assertNotNull(result);
        assertEquals(restaurantId, result.getId());
        assertEquals("My Restaurant", result.getName());
    }

    @Test
    void getRestaurant_whenRestaurantNotExists_shouldReturnNull() {
        Integer restaurantId = 999;

        when(restaurantPersistencePort.getRestaurant(restaurantId)).thenReturn(null);

        Restaurant result = restaurantUseCase.getRestaurant(restaurantId);

        assertNull(result);
    }

    @Test
    void getRestaurant_shouldCallPersistencePort() {
        Integer restaurantId = 1;
        when(restaurantPersistencePort.getRestaurant(restaurantId)).thenReturn(new Restaurant());

        restaurantUseCase.getRestaurant(restaurantId);

        verify(restaurantPersistencePort).getRestaurant(restaurantId);
    }

    @Test
    void saveRestaurantEmployee_whenValidEmployeeAndRestaurant_shouldSave() {
        Integer restaurantId = 1;
        Integer employeeId = 10;
        String employeeEmail = "employee@test.com";

        RestaurantEmployee restaurantEmployee = new RestaurantEmployee();
        restaurantEmployee.setEmployeeEmail(employeeEmail);
        restaurantEmployee.setRestaurantId(restaurantId);

        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);


        when(userRepository.getUserByEmail(employeeEmail))
                .thenReturn(Optional.of(buildUser(employeeId, EMPLOYEE_ROLE_ID)));
        when(restaurantPersistencePort.getRestaurant(restaurantId)).thenReturn(restaurant);

        restaurantUseCase.saveRestaurantEmployee(restaurantEmployee);

        verify(restaurantPersistencePort).saveRestaurantEmployee(restaurantEmployeeCaptor.capture());
        RestaurantEmployee saved = restaurantEmployeeCaptor.getValue();

        assertEquals(employeeId, saved.getEmployeeId());
        assertEquals(restaurantId, saved.getRestaurantId());
        assertEquals(employeeEmail, saved.getEmployeeEmail());
    }

    @Test
    void saveRestaurantEmployee_whenUserIsNotEmployee_shouldThrowException() {
        Integer restaurantId = 1;
        String employeeEmail = "client@test.com";

        RestaurantEmployee restaurantEmployee = new RestaurantEmployee();
        restaurantEmployee.setEmployeeEmail(employeeEmail);
        restaurantEmployee.setRestaurantId(restaurantId);

        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);

        when(userRepository.getUserByEmail(employeeEmail))
                .thenReturn(Optional.of(buildUser(10, CLIENT_ROLE_ID)));
        when(restaurantPersistencePort.getRestaurant(restaurantId)).thenReturn(restaurant);

        InvalidRoleException exception = assertThrows(
                InvalidRoleException.class,
                () -> restaurantUseCase.saveRestaurantEmployee(restaurantEmployee)
        );

        assertTrue(exception.getMessage().contains("not employee"));
        verify(restaurantPersistencePort, never()).saveRestaurantEmployee(any());
    }

    @Test
    void saveRestaurantEmployee_whenRestaurantNotExists_shouldThrowException() {
        Integer restaurantId = 999;
        String employeeEmail = "employee@test.com";

        RestaurantEmployee restaurantEmployee = new RestaurantEmployee();
        restaurantEmployee.setEmployeeEmail(employeeEmail);
        restaurantEmployee.setRestaurantId(restaurantId);

        when(userRepository.getUserByEmail(employeeEmail))
                .thenReturn(Optional.of(buildUser(10, EMPLOYEE_ROLE_ID)));
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
        Integer restaurantId = 1;
        String employeeEmail = "owner@test.com";

        RestaurantEmployee restaurantEmployee = new RestaurantEmployee();
        restaurantEmployee.setEmployeeEmail(employeeEmail);
        restaurantEmployee.setRestaurantId(restaurantId);

        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);

        when(userRepository.getUserByEmail(employeeEmail))
                .thenReturn(Optional.of(buildUser(10, OWNER_ROLE_ID)));
        when(restaurantPersistencePort.getRestaurant(restaurantId)).thenReturn(restaurant);

        assertThrows(InvalidRoleException.class,
                () -> restaurantUseCase.saveRestaurantEmployee(restaurantEmployee));
    }

    @Test
    void saveRestaurantEmployee_withRoleAdmin_shouldThrowException() {
        Integer restaurantId = 1;
        String employeeEmail = "admin@test.com";

        RestaurantEmployee restaurantEmployee = new RestaurantEmployee();
        restaurantEmployee.setEmployeeEmail(employeeEmail);
        restaurantEmployee.setRestaurantId(restaurantId);

        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);

        when(userRepository.getUserByEmail(employeeEmail))
                .thenReturn(Optional.of(buildUser(10, ADMIN_ROLE_ID)));
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

        when(restaurantPersistencePort.getRestaurantOfEmployee(employeeId))
                .thenReturn(Optional.of(employeeExpected));

        RestaurantEmployee result = restaurantUseCase.getRestaurantEmployee(employeeId);

        assertNotNull(result);
        assertEquals(employeeId, result.getEmployeeId());
        assertEquals("employee@test.com", result.getEmployeeEmail());
    }

    @Test
    void getRestaurantEmployee_whenNotExists_shouldThrowException() {
        Integer employeeId = 999;

        when(restaurantPersistencePort.getRestaurantOfEmployee(employeeId))
                .thenReturn(Optional.empty());

        assertThrows(RestaurantNotFoundException.class,
                () -> restaurantUseCase.getRestaurantEmployee(employeeId));
    }

    @Test
    void getRestaurantEmployee_shouldCallPersistencePort() {
        Integer employeeId = 10;

        RestaurantEmployee employee = new RestaurantEmployee();
        employee.setEmployeeId(employeeId);

        when(restaurantPersistencePort.getRestaurantOfEmployee(employeeId))
                .thenReturn(Optional.of(employee));

        restaurantUseCase.getRestaurantEmployee(employeeId);

        verify(restaurantPersistencePort).getRestaurantOfEmployee(employeeId);
    }
}
