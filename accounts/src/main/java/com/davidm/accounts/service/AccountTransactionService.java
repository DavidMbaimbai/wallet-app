package com.davidm.accounts.service;




import com.davidm.accounts.dto.AccountTransactionDto;
import com.davidm.accounts.dto.CustomerDto;
import com.davidm.accounts.entity.AccountTransactions;

import javax.security.auth.login.AccountNotFoundException;

public interface AccountTransactionService {
    void saveTransaction(final AccountTransactionDto AccountTransactionDto);
}
