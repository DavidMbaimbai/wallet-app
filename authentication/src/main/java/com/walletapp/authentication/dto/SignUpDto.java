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
        name = "SignUp",
        description = "Schema to hold SignUp information"
)
public class SignUpDto {
    private String name;
    private String username;
    private String email;
    private String password;
}
