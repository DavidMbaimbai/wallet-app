package com.davidm.accounts.mapper;
import com.davidm.accounts.dto.AccountDto;
import com.davidm.accounts.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountMapper {

    Account mapAccountFromDto(AccountDto accountDto, @MappingTarget Account account);

    AccountDto mapAccountDtoFromAccount(Account account, @MappingTarget AccountDto accountDto);

}
