package com.walletapp.authentication.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        name = "Accounts",
        description = "Schema to hold Account information"
)
public class LoginDto {
    private String usernameOrEmail;
    private String password;
}
