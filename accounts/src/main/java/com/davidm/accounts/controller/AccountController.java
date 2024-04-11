package com.davidm.accounts.controller;


import com.davidm.accounts.constants.AccountsConstants;
import com.davidm.accounts.dto.*;
import com.davidm.accounts.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "CRUD REST APIs for Accounts in EasyBank",
        description = "CRUD REST APIs in EasyBank to CREATE, UPDATE, FETCH AND DELETE accounts details"
)
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
public class AccountController {

    private final CustomerService customerService;

    private final Environment environment;

    private final AccountsContactInfoDto accountsContactInfoDto;

    @Value("${build.version}")
    private String buildVersion;

    @Operation(
            summary = "Create Account REST API",
            description = "REST API to create new Customer & Account inside EasyBank"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))
            )
    })
    @PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody final CustomerDto accountDto) {
        customerService.createAccount(accountDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseDto.builder()
                        .status(AccountsConstants.STATUS_201)
                        .statusMessage(AccountsConstants.MESSAGE_201)
                        .build());
    }

    @Operation(
            summary = "Fetch Account Details REST API",
            description = "REST API to fetch Customer & Account details based on a mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))
            )
    })
    @GetMapping(path = "/fetch", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerDto> fetchCustomerDetails(@RequestParam
                                                                @Pattern(regexp = "^$|[0-9]{10}", message = "Mobile number must be 10 digits long")
                                                                String mobileNumber) {
        CustomerDto customerDto = customerService.fetchCustomerDetails(mobileNumber);
        return ResponseEntity.ok(customerDto);
    }

    @Operation(
            summary = "Update Account Details REST API",
            description = "REST API to update Customer & Account details based on an account number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))
            )
    })
    @PutMapping(path = "/update", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto> updateCustomerDetails(@Valid @RequestBody final CustomerDto customerDto) {
        final boolean updated = customerService.updateCustomerDetails(customerDto);
        if (updated) {
            return ResponseEntity.ok(ResponseDto.builder()
                    .statusMessage(AccountsConstants.MESSAGE_200)
                    .status(AccountsConstants.STATUS_200)
                    .build());
        }
        return ResponseEntity.internalServerError().body(ResponseDto.builder()
                .status(AccountsConstants.STATUS_417)
                .statusMessage(AccountsConstants.EXPECTATION_FAILED)
                .build());
    }

    @Operation(
            summary = "Delete Account Details REST API",
            description = "REST API to delete Customer & Account details based on an account number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))
            )
    })
    @DeleteMapping(path = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto> deleteCustomerDetails(@RequestParam
                                                                 @Pattern(regexp = "^$|[0-9]{10}", message = "Mobile number must be 10 digits long")
                                                                 final String mobileNumber) {
        final boolean deleted = customerService.deleteCustomerDetails(mobileNumber);
        if (deleted) {
            return ResponseEntity.ok(ResponseDto.builder()
                    .statusMessage(AccountsConstants.MESSAGE_200)
                    .status(AccountsConstants.STATUS_200)
                    .build());
        }
        return ResponseEntity.internalServerError().body(ResponseDto.builder()
                .status(AccountsConstants.STATUS_417)
                .statusMessage(AccountsConstants.EXPECTATION_FAILED)
                .build());
    }
    @Operation(
            summary = "Credit Account REST API",
            description = "REST API to credit Customer & Account inside EasyBank"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))
            )
    })
    @PostMapping("credit")
    public ResponseDto creditAccount(@RequestBody CreditDebitRequest request){
        return customerService.creditAccount(request);
    }
    @Operation(
            summary = "Debit Account REST API",
            description = "REST API to debit Customer & Account inside EasyBank"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))
            )
    })
    @PostMapping("debit")
    public ResponseDto debitAccount(@RequestBody CreditDebitRequest request){
        return customerService.debitAccount(request);
    }

    @GetMapping("/java-home")
    public ResponseEntity<String> getJavaHome() {
        return ResponseEntity.ok(environment.getProperty("JAVA_HOME"));
    }

    @GetMapping("/build-version")
    public ResponseEntity<String> getVersion() {
        return ResponseEntity.ok(buildVersion);
    }

    @GetMapping("/contact-info")
    public ResponseEntity<AccountsContactInfoDto> getContactInfo() {
        return ResponseEntity.ok(accountsContactInfoDto);
    }
}
