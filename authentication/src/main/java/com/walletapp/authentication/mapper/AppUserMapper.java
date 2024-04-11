package com.walletapp.authentication.mapper;

import com.walletapp.authentication.dto.UserDto;
import com.walletapp.authentication.entity.AppUser;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AppUserMapper {
    AppUser mapAppUserFromDto(UserDto userDto, @MappingTarget AppUser appUser);
    UserDto mapUserDtoFromAppUser(AppUser appUser, @MappingTarget UserDto userDto);
}
