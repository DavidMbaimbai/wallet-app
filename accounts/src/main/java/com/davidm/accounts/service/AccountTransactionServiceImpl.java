package com.davidm.accounts.service;


import com.davidm.accounts.constants.TransactionType;
import com.davidm.accounts.entity.Account;
import com.davidm.accounts.entity.AccountTransactions;
import com.davidm.accounts.repository.AccountRepository;
import com.davidm.accounts.repository.AccountTransactionsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
@Slf4j
public class AccountTransactionServiceImpl implements AccountTransactionService {

    private final AccountTransactionsRepository accountTransactionsRepository;
    private final AccountRepository accountRepository;

    public AccountTransactions saveTransaction(AccountTransactions accountTransactions) throws AccountNotFoundException {
        accountTransactions.setTransactionDate(LocalDateTime.now());
        //retrieve account using accountNumber
        Account account = accountRepository.findById(accountTransactions.getAccountNumber()).orElseThrow(AccountNotFoundException::new);
        if (accountTransactions.getTransactionType().equals(TransactionType.DEPOSIT)) {
            BigDecimal add = account.getAccountBalance().add(accountTransactions.getAmount());
            account.setAccountBalance(add);
        } else if (accountTransactions.getTransactionType().equals(TransactionType.WITHDRAWAL)) {
            BigDecimal subtract = account.getAccountBalance().subtract(accountTransactions.getAmount());
            account.setAccountBalance(subtract);
        }
        accountRepository.save(account);
        return accountTransactionsRepository.save(accountTransactions);
    }
}
