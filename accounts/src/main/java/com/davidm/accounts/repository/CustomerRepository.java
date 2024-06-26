package com.davidm.accounts.repository;


import com.davidm.accounts.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findCustomerByMobileNumber(final String mobileNumber);

    boolean existsByAccountNumber(Long accountNumber);

    Customer findByAccountNumber(Long accountNumber);
}
