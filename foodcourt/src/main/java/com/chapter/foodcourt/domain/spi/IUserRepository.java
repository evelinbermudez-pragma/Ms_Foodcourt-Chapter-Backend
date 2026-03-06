package com.chapter.foodcourt.domain.spi;


import com.chapter.foodcourt.domain.model.User;

import java.util.Optional;

public interface IUserRepository {
   Optional<User> getUserById(Integer id);
   Optional<User> getUserByEmail(String email);

}
