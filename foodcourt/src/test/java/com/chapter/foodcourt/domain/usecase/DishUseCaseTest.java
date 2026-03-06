package com.chapter.foodcourt.domain.usecase;

import com.chapter.foodcourt.domain.exception.DishNotFoundException;
import com.chapter.foodcourt.domain.exception.UserIsNotOwnerException;
import com.chapter.foodcourt.domain.model.Category;
import com.chapter.foodcourt.domain.model.Dish;
import com.chapter.foodcourt.domain.model.Restaurant;
import com.chapter.foodcourt.domain.spi.IDishPersistencePort;
import com.chapter.foodcourt.domain.spi.IRestaurantPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DishUseCaseTest {
    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;

    @Mock
    private IDishPersistencePort dishPersistencePort;

    @Captor
    private ArgumentCaptor<Dish> dishCaptor;

    private DishUseCase dishUseCase;

    @BeforeEach
    void setUp() {
        dishUseCase = new DishUseCase(dishPersistencePort, restaurantPersistencePort);
    }

    @Test
    void saveDish_whenUserIsOwner_shouldSaveDishWithActiveTrue() {
        Integer userId = 5;
        Integer restaurantId = 1;

        Dish dish = new Dish();
        dish.setName("Pizza Margherita");
        dish.setPrice(25000.00);
        dish.setDescription("Pizza italiana tradicional");
        dish.setRestaurantId(restaurantId);
        dish.setCategory(Category.FAST_FOOD);

        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);
        restaurant.setOwnerId(userId);

        when(restaurantPersistencePort.getRestaurant(restaurantId)).thenReturn(restaurant);

        // Act
        dishUseCase.saveDish(dish, userId);

        // Assert
        verify(dishPersistencePort).saveDish(dishCaptor.capture());
        Dish dishSaved = dishCaptor.getValue();

        assertTrue(dishSaved.isActive());
        assertEquals("Pizza Margherita", dishSaved.getName());
        assertEquals(25000, dishSaved.getPrice());
    }

    @Test
    void saveDish_whenUserIsNotOwner_shouldThrowException() {
        // Arrange
        Integer userId = 5;
        Integer restaurantId = 1;

        Dish dish = new Dish();
        dish.setRestaurantId(restaurantId);

        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);
        restaurant.setOwnerId(999); // Different owner

        when(restaurantPersistencePort.getRestaurant(restaurantId)).thenReturn(restaurant);

        // Act & Assert
        UserIsNotOwnerException exception = assertThrows(
                UserIsNotOwnerException.class,
                () -> dishUseCase.saveDish(dish, userId)
        );

        assertTrue(exception.getMessage().contains("isn't"));
        verify(dishPersistencePort, never()).saveDish(any());
    }

    @Test
    void saveDish_withAllCategories_shouldSaveSuccessfully() {
        // Arrange
        Integer userId = 5;
        Integer restaurantId = 1;

        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);
        restaurant.setOwnerId(userId);

        when(restaurantPersistencePort.getRestaurant(restaurantId)).thenReturn(restaurant);

        for (Category category : Category.values()) {
            Dish dish = new Dish();
            dish.setName("Dish " + category.name());
            dish.setRestaurantId(restaurantId);
            dish.setCategory(category);

            // Act
            dishUseCase.saveDish(dish, userId);
        }

        // Assert
        verify(dishPersistencePort, times(Category.values().length)).saveDish(any(Dish.class));
    }

    @Test
    void updateDish_whenUserIsOwner_shouldUpdatePriceAndDescription() {
        // Arrange
        Integer userId = 5;
        Integer dishId = 10;
        Integer restaurantId = 1;

        Dish existingDish = new Dish();
        existingDish.setId(dishId);
        existingDish.setName("Original Pizza");
        existingDish.setPrice(20000.0);
        existingDish.setDescription("Original description");
        existingDish.setRestaurantId(restaurantId);
        existingDish.setActive(true);

        Dish dishModel = new Dish();
        dishModel.setPrice(25000.0);
        dishModel.setDescription("New description");

        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);
        restaurant.setOwnerId(userId);

        when(dishPersistencePort.getDish(dishId)).thenReturn(existingDish);
        when(restaurantPersistencePort.getRestaurant(restaurantId)).thenReturn(restaurant);

        // Act
        dishUseCase.updateDish(dishModel, userId, dishId);

        // Assert
        verify(dishPersistencePort).saveDish(dishCaptor.capture());
        Dish updatedDish = dishCaptor.getValue();

        assertEquals(25000.0, updatedDish.getPrice());
        assertEquals("New description", updatedDish.getDescription());
        assertEquals("Original Pizza", updatedDish.getName()); // Name doesn't change
        assertTrue(updatedDish.isActive()); // Active doesn't change
    }

    @Test
    void updateDish_whenUserIsNotOwner_shouldThrowException() {
        // Arrange
        Integer userId = 5;
        Integer dishId = 10;
        Integer restaurantId = 1;

        Dish existingDish = new Dish();
        existingDish.setId(dishId);
        existingDish.setRestaurantId(restaurantId);

        Dish dishModel = new Dish();
        dishModel.setPrice(25000.0);

        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);
        restaurant.setOwnerId(999); // Different owner

        when(dishPersistencePort.getDish(dishId)).thenReturn(existingDish);
        when(restaurantPersistencePort.getRestaurant(restaurantId)).thenReturn(restaurant);

        // Act & Assert
        UserIsNotOwnerException exception = assertThrows(
                UserIsNotOwnerException.class,
                () -> dishUseCase.updateDish(dishModel, userId, dishId)
        );

        assertTrue(exception.getMessage().contains("isn't"));
    }

    @Test
    void updateDish_shouldOnlyUpdatePriceAndDescription() {
        // Arrange
        Integer userId = 5;
        Integer dishId = 10;
        Integer restaurantId = 1;

        Dish existingDish = new Dish();
        existingDish.setId(dishId);
        existingDish.setName("Original Pizza");
        existingDish.setPrice(20000.0);
        existingDish.setDescription("Original description");
        existingDish.setImageUrl("http://image.com/pizza.jpg");
        existingDish.setCategory(Category.FAST_FOOD);
        existingDish.setRestaurantId(restaurantId);
        existingDish.setActive(true);

        Dish dishModel = new Dish();
        dishModel.setPrice(30000.0);
        dishModel.setDescription("Updated new description");
        dishModel.setName("New Name"); // Try to change name
        dishModel.setCategory(Category.DESSERTS); // Try to change category

        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);
        restaurant.setOwnerId(userId);

        when(dishPersistencePort.getDish(dishId)).thenReturn(existingDish);
        when(restaurantPersistencePort.getRestaurant(restaurantId)).thenReturn(restaurant);

        // Act
        dishUseCase.updateDish(dishModel, userId, dishId);

        // Assert
        verify(dishPersistencePort).saveDish(dishCaptor.capture());
        Dish savedDish = dishCaptor.getValue();

        assertEquals(30000.0, savedDish.getPrice());
        assertEquals("Updated new description", savedDish.getDescription());
        assertEquals("Original Pizza", savedDish.getName()); // Name didn't change
        assertEquals(Category.FAST_FOOD, savedDish.getCategory()); // Category didn't change
        assertEquals("http://image.com/pizza.jpg", savedDish.getImageUrl()); // URL didn't change
    }

    @Test
    void getDish_whenDishExists_shouldReturnDish() {
        // Arrange
        Integer dishId = 10;

        Dish expectedDish = new Dish();
        expectedDish.setId(dishId);
        expectedDish.setName("Burger");

        when(dishPersistencePort.getDish(dishId)).thenReturn(expectedDish);

        // Act
        Dish result = dishUseCase.getDish(dishId);

        // Assert
        assertNotNull(result);
        assertEquals(dishId, result.getId());
        assertEquals("Burger", result.getName());
    }

    @Test
    void getDish_whenDishNotExists_shouldThrowException() {
        // Arrange
        Integer dishId = 999;

        when(dishPersistencePort.getDish(dishId)).thenReturn(null);

        // Act & Assert
        DishNotFoundException exception = assertThrows(
                DishNotFoundException.class,
                () -> dishUseCase.getDish(dishId)
        );

        assertTrue(exception.getMessage().contains("not found"));
    }

}
