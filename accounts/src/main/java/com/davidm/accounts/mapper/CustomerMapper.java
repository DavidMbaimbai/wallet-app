package com.davidm.accounts.mapper;
import com.davidm.accounts.dto.CustomerDto;
import com.davidm.accounts.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerMapper {

    Customer mapCustomer(CustomerDto customerDto, @MappingTarget Customer customer);

    CustomerDto mapCustomerDto(Customer customer, @MappingTarget CustomerDto customerDto);
}
