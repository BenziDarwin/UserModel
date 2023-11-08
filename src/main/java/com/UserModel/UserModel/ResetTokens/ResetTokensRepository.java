package com.UserModel.UserModel.ResetTokens;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResetTokensRepository extends JpaRepository<ResetTokens, Long> {
    @Query("Select s from ResetTokens s where s.token = ?1 and s.email = ?2")
    public Optional<ResetTokens> findResetTokensWithTokenAndEmail(String token, String email);
}
