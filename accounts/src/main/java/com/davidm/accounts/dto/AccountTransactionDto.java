package com.davidm.accounts.dto;

import com.davidm.accounts.constants.TransactionType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        name = "Accounts",
        description = "Schema to hold Account information"
)
public class AccountTransactionDto {
    @Schema(description = "Account Number of Eazy Bank account", example = "5476454742")
    @Pattern(regexp = "^$|[0-9]{10}", message = "Account number must be 10 digits long")
    private Long accountNumber;
    @Schema(description = "Account type of Eazy Bank account", example = "Savings")
    @NotEmpty(message = "Account Type cannot be null or empty")
    private TransactionType transactionType;
    private BigDecimal amount;
    private LocalDateTime transactionDate;

}
