package com.UserModel.UserModel.Roles;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Long> {

    @Query("SELECT s from Roles s where s.roleName = ?1")
    Optional<Roles> findRolesByName(String roleName);
}
