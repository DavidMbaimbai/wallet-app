package com.davidm.accounts.service;



import com.davidm.accounts.entity.AccountTransactions;

import javax.security.auth.login.AccountNotFoundException;

public interface AccountTransactionService {
    AccountTransactions saveTransaction(AccountTransactions accountTransactions) throws AccountNotFoundException;
}
