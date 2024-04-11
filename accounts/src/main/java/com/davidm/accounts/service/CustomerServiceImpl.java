package com.davidm.accounts.service;


import com.davidm.accounts.constants.AccountsConstants;
import com.davidm.accounts.dto.*;
import com.davidm.accounts.entity.Account;
import com.davidm.accounts.entity.AccountTransactions;
import com.davidm.accounts.entity.Customer;
import com.davidm.accounts.exceptions.CustomerAlreadyExistsException;
import com.davidm.accounts.exceptions.ResourceNotFoundException;
import com.davidm.accounts.mapper.AccountMapper;
import com.davidm.accounts.mapper.AccountTransactionMapper;
import com.davidm.accounts.mapper.CustomerMapper;
import com.davidm.accounts.repository.AccountRepository;
import com.davidm.accounts.repository.AccountTransactionsRepository;
import com.davidm.accounts.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final AccountRepository accountRepository;
    private final AccountTransactionsRepository accountTransactionsRepository;
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final AccountMapper accountMapper;
    private final AccountTransactionMapper accountTransactionMapper;

    /**
     * Method for creating an account on EasyBank
     *
     * @param customerDto - CustomerDto Object
     */
    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = customerMapper.mapCustomer(customerDto, new Customer());
        Optional<Customer> optionalCustomer =
                customerRepository.findCustomerByMobileNumber(customerDto.getMobileNumber());
        if (optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer with mobile number %s already registered"
                    .formatted(customer.getMobileNumber()));
        }
        log.info("Customer {}", customer);
        Customer savedCustomer = customerRepository.save(customer);
        accountRepository.save(createAccount(savedCustomer));
    }

    /**
     * Method to fetch the customer by the customer mobile number
     *
     * @param mobileNumber - Customer Mobile Number
     * @return CustomerDetailsDto
     */
    @Override
    public CustomerDto fetchCustomerDetails(final String mobileNumber) {
        Optional<Customer> optionalCustomer = customerRepository.findCustomerByMobileNumber(mobileNumber);
        if (optionalCustomer.isEmpty()) {
            throw new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber);
        }
        Customer customer = optionalCustomer.get();
        Optional<Account> optionalAccount = accountRepository.findAccountByCustomerId(customer.getCustomerId());
        if (optionalAccount.isEmpty()) {
            throw new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString());
        }
        Optional<AccountTransactions> optionalAccountTransactions = accountTransactionsRepository.findAccountByCustomerId(customer.getCustomerId());
        Account account = optionalAccount.get();
        AccountTransactions accountTransactions = optionalAccountTransactions.get();
        CustomerDto customerDto = customerMapper.mapCustomerDto(customer, new CustomerDto());
        customerDto.setAccountDto(accountMapper.mapAccountDtoFromAccount(account, new AccountDto()));
        customerDto.setAccountTransactionDto(accountTransactionMapper.mapAccountTransactionDtoFromAccountTransactions(accountTransactions, new AccountTransactionDto()));
        return customerDto;
    }

    /**
     * @param customerDto - CustomerDto Object
     * @return - returns whether it has deleted or not
     */
    @Override
    public boolean updateCustomerDetails(final CustomerDto customerDto) {
        boolean updated = false;
        final AccountDto accountDto = customerDto.getAccountDto();
        if (Objects.nonNull(customerDto.getAccountDto())) {
            Account account = accountRepository.findById(customerDto.getAccountDto().getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "AccountNumber", accountDto.getAccountNumber().toString()));
            Account updatedAccount = accountMapper.mapAccountFromDto(accountDto, account);
            accountRepository.save(updatedAccount);

            Customer customer = customerRepository.findById(updatedAccount.getCustomerId()).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "CustomerId", updatedAccount.getCustomerId().toString()));
            Customer updatedCustomer = customerMapper.mapCustomer(customerDto, customer);
            customerRepository.save(updatedCustomer);
            updated = true;
        }

        return updated;
    }

    /**
     * @param mobileNumber - Customer mobile number
     * @return - returns whether it has deleted or not
     */
    @Override
    public boolean deleteCustomerDetails(final String mobileNumber) {
        final Customer customer = customerRepository.findCustomerByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "MobileNumber", mobileNumber));
        accountRepository.deleteAccountByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }


    @Override
    public ResponseDto creditAccount(CreditDebitRequest request) {
        //checking if the account exists
        boolean isAccountExist = customerRepository.existsByAccountNumber(request.getAccountNumber());
        if (!isAccountExist){
            return ResponseDto.builder()
                    .status(AccountsConstants.ACCOUNT_NOT_EXIST_CODE)
                    .statusMessage(AccountsConstants.ACCOUNT_NOT_EXIST_MESSAGE)
                    .build();
        }

        Customer customerToCredit = customerRepository.findByAccountNumber(request.getAccountNumber());
        customerToCredit.setAccountBalance(customerToCredit.getAccountBalance().add(request.getAmount()));
        customerRepository.save(customerToCredit);

        return ResponseDto.builder()
                .status(AccountsConstants.ACCOUNT_CREDITED_SUCCESS)
                .statusMessage(AccountsConstants.ACCOUNT_CREDITED_SUCCESS_MESSAGE)
                .accountInfo(AccountsConstants.builder()
                        .accountName(customerToCredit.getFirstName() + " " + customerToCredit.getLastName() + " " + customerToCredit.getOtherName())
                        .accountBalance(customerToCredit.getAccountBalance())
                        .accountNumber(request.getAccountNumber())
                        .build())
                .build();
    }

    @Override
    public ResponseDto debitAccount(CreditDebitRequest request) {
        //check if the account exists
        //check if the amount you intend to withdraw is not more than the current account balance
        boolean isAccountExist = customerRepository.existsByAccountNumber(request.getAccountNumber());
        if (!isAccountExist){
            return ResponseDto.builder()
                    .status(AccountsConstants.ACCOUNT_NOT_EXIST_CODE)
                    .statusMessage(AccountsConstants.ACCOUNT_NOT_EXIST_MESSAGE)
                    .build();
        }

        Customer customerToDebit = customerRepository.findByAccountNumber(request.getAccountNumber());
        Long availableBalance =customerToDebit.getAccountBalance();
        BigInteger debitAmount = request.getAmount().toBigInteger();
        if ( availableBalance.intValue() < debitAmount.intValue()){
            return ResponseDto.builder()
                    .status(AccountsConstants.INSUFFICIENT_BALANCE_CODE)
                    .statusMessage(AccountsConstants.INSUFFICIENT_BALANCE_MESSAGE)
                    .build();
        }
        else {
            customerToDebit.setAccountBalance(customerToDebit.getAccountBalance().subtract(request.getAmount()));
            customerRepository.save(customerToDebit);
            return ResponseDto.builder()
                    .status(AccountsConstants.ACCOUNT_DEBITED_SUCCESS)
                    .statusMessage(AccountsConstants.ACCOUNT_DEBITED_MESSAGE)
                    .accountInfo(ResponseDto.builder()
                            .accountNumber(request.getAccountNumber())
                            .accountName(customerToDebit.getFirstName() + " " + userToDebit.getLastName() + " " + userToDebit.getOtherName())
                            .accountBalance(customerToDebit.getAccountBalance())
                            .build())
                    .build();
        }

    }
    private Account createAccount(final Customer savedCustomer) {
        Long accountNumber = 100_000_000_000L + new Random().nextLong(90_000_000_000L);
        Account account = new Account();
        account.setAccountNumber(accountNumber);
        account.setCustomerId(savedCustomer.getCustomerId());
        account.setAccountType(AccountsConstants.SAVINGS);
        account.setBranchAddress(AccountsConstants.ADDRESS);
        return account;
    }
}
