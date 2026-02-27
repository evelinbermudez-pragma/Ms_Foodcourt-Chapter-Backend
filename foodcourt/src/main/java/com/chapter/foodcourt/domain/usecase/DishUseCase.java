package com.chapter.foodcourt.domain.usecase;

import com.chapter.foodcourt.domain.api.IDishServicePort;
import com.chapter.foodcourt.domain.exception.DishNotFoundException;
import com.chapter.foodcourt.domain.exception.UserIsNotOwnerException;
import com.chapter.foodcourt.domain.model.Dish;
import com.chapter.foodcourt.domain.model.Restaurant;
import com.chapter.foodcourt.domain.spi.IDishPersistencePort;
import com.chapter.foodcourt.domain.spi.IRestaurantPersistencePort;

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
        Dish dish = dishPersistencePort.getDish(id);
        if(dish == null){
            throw new DishNotFoundException("Dish not found");
        }
        return dish;
    }
    @Override
    public void updateDish(Dish dishModel, Integer userId, Integer dishId) {
        Dish dish = dishPersistencePort.getDish(dishId);
        userIsNotOwner(dish , userId);
        dish.setPrice(dishModel.getPrice());
        dish.setDescription(dishModel.getDescription());
        dishPersistencePort.saveDish(dish);

    }
    private void userIsNotOwner(Dish dish, Integer userId){
        Restaurant restaurant = restaurantPersistencePort.getRestaurant(dish.getRestaurantId());
        if(!restaurant.getOwnerId().equals(userId)){
            throw new UserIsNotOwnerException("User isn´t the owner of the restaurant dah");
        }

    }

}
