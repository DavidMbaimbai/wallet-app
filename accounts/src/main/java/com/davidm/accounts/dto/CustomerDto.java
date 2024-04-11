package com.davidm.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Schema(
        name = "Customer",
        description = "Schema to hold Customer and Account information"
)
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {

    @Schema(description = "Name of the customer", example = "Eazy Bytes")
    @NotEmpty(message = "Name cannot be null or empty")
    @Size(message = "Size must be between 5 and 30.", max = 30, min = 5)
    private String name;

    @Schema(description = "Email of the customer", example = "davymbaimbai@gmail.com")
    @NotEmpty(message = "Email cannot be null or empty")
    @Email
    private String email;

    @Schema(description = "Phone number of the customer", example = "0785708498")
    @Pattern(regexp = "^$|[0-9]{10}", message = "Mobile number must be 10 digits long")
    @NotEmpty(message = "Mobile number cannot be null or empty")
    private String mobileNumber;

    @Schema(description = "Account details of the Customer")
    private AccountDto accountDto;

    @Schema(description = "Transaction details of the Customer")
    private AccountTransactionDto accountTransactionDto;
}
