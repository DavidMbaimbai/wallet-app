package com.davidm.accounts.mapper;

import com.davidm.accounts.dto.AccountDto;
import com.davidm.accounts.dto.AccountTransactionDto;
import com.davidm.accounts.entity.Account;
import com.davidm.accounts.entity.AccountTransactions;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountTransactionMapper {
    AccountTransactions AccountTransactionDto(AccountTransactionDto accountTransactionDto, @MappingTarget AccountTransactions accountTransactions);

    AccountTransactionDto mapAccountTransactionDtoFromAccountTransactions(AccountTransactions accountTransactions, @MappingTarget AccountTransactionDto accountTransactionDto);

}
