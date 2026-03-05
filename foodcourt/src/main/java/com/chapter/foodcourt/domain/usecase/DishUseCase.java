package com.chapter.foodcourt.domain.usecase;

import com.chapter.foodcourt.domain.api.IDishServicePort;
import com.chapter.foodcourt.domain.exception.DishNotFoundException;
import com.chapter.foodcourt.domain.exception.UserIsNotOwnerException;
import com.chapter.foodcourt.domain.model.Category;
import com.chapter.foodcourt.domain.model.Dish;
import com.chapter.foodcourt.domain.model.Restaurant;
import com.chapter.foodcourt.domain.spi.IDishPersistencePort;
import com.chapter.foodcourt.domain.spi.IRestaurantPersistencePort;
import org.springframework.data.domain.Page;

public class DishUseCase implements IDishServicePort {

    private final IDishPersistencePort dishPersistencePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;

    public DishUseCase(IDishPersistencePort dishPersistencePort, IRestaurantPersistencePort restaurantPersistencePort) {
         this.restaurantPersistencePort = restaurantPersistencePort;
        this.dishPersistencePort = dishPersistencePort;
    }
    @Override
    public void saveDish(Dish dish, Integer userId) {
        //validacion
        userIsNotOwner(dish, userId);
        dish.setActive(true);
        dishPersistencePort.saveDish(dish);
    }
    @Override
    public Dish getDish(Integer id) {
        return dishPersistencePort.getDish(id);
    }
    @Override
    public void updateDish(Dish dishModel, Integer userId, Integer dishId) {
        Dish dish = dishPersistencePort.getDish(dishId);
        userIsNotOwner(dish , userId);
        dish.setPrice(dishModel.getPrice());
        dish.setDescription(dishModel.getDescription());
        dishPersistencePort.saveDish(dish);

    }
    @Override
    public void toggleDish(Integer dishId, Boolean active, Integer userId) {
        Dish dish = dishPersistencePort.getDish(dishId);
        userIsNotOwner(dish , userId);
        dish.setActive(active);
        dishPersistencePort.saveDish(dish);
    }
    @Override
    public Page<Dish> listDishesByRestaurant(Integer restaurantId, Category category, int page, int size) {
        return dishPersistencePort.listDishesByRestaurant(restaurantId, category, page, size);
    }


    private void userIsNotOwner(Dish dish, Integer userId) {
        Restaurant restaurant = restaurantPersistencePort.getRestaurant(dish.getRestaurantId());
        if (!restaurant.getOwnerId().equals(userId)) {
            throw new UserIsNotOwnerException("User isn't the owner of the restaurant");
        }
    }


}
