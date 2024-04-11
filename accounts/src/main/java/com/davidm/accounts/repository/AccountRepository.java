package com.davidm.accounts.repository;


import com.davidm.accounts.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findAccountByCustomerId(final Long customerId);

    @Transactional
    @Modifying
    void deleteAccountByCustomerId(final Long customerId);
}
