package com.davidm.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
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
public class AccountDto {

    @Schema(description = "Account Number of Eazy Bank account", example = "5476454742")
    @Pattern(regexp = "^$|[0-9]{10}", message = "Account number must be 10 digits long")
    private Long accountNumber;

    @Schema(description = "Account type of Eazy Bank account", example = "Savings")
    @NotEmpty(message = "Account Type cannot be null or empty")
    private String accountType;

    @Schema(description = "Eazy Bank branch address", example = "247 Pine Avenue Ferndale")
    @NotEmpty(message = "Branch Address cannot be null or empty")
    private String branchAddress;
}
