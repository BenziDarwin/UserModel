package com.UserModel.UserModel.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {

    @Query("SELECT s from User s where s.email = ?1")
    Optional<User> findUserByEmail(String email);
}
