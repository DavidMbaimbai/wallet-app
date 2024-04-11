package com.walletapp.authentication.repository;

import com.walletapp.authentication.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository  extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);
}
