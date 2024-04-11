package com.walletapp.authentication.repository;

import com.walletapp.authentication.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser,Long> {
    Optional<AppUser> findByEmail(String email);
    Optional<AppUser> findByUsernameOrEmail(String username, String email);
    Optional<AppUser> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
