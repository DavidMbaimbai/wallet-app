package com.davidm.accounts.repository;


import com.davidm.accounts.entity.AccountTransactions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountTransactionsRepository extends JpaRepository<AccountTransactions, Long> {

    Optional<AccountTransactions> findAccountByCustomerId(Long customerId);
}
