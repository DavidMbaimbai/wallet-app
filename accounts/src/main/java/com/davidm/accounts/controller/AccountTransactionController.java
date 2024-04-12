package com.davidm.accounts.controller;


import com.davidm.accounts.constants.AccountsConstants;
import com.davidm.accounts.dto.CustomerDto;
import com.davidm.accounts.dto.ErrorDto;
import com.davidm.accounts.dto.ResponseDto;
import com.davidm.accounts.entity.AccountTransactions;
import com.davidm.accounts.service.AccountTransactionService;
import com.davidm.accounts.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

@Tag(
        name = "CRUD REST APIs for Accounts in EasyBank",
        description = "CRUD REST APIs in EasyBank to CREATE, UPDATE, FETCH AND DELETE accounts details"
)
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
public class AccountTransactionController {

    private final AccountTransactionService accountTransactionService;
    private final CustomerService customerService;

    private final Environment environment;

    @Value("${build.version}")
    private String buildVersion;
    @Operation(
            summary = "Create AccountTransaction REST API",
            description = "REST API to create new AccountTransaction inside EasyBank"
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

    @PostMapping(path = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountTransactions> transact(@RequestBody AccountTransactions accountTransactions) throws AccountNotFoundException {
        var savedAccountTransactions = accountTransactionService.saveTransaction(accountTransactions);
        return ResponseEntity.ok(savedAccountTransactions);
    }
    @GetMapping("/api/java-home-info")
    public ResponseEntity<String> getJavaHomeInfo() {
        return ResponseEntity.ok(environment.getProperty("JAVA_HOME"));
    }

    @GetMapping("/transact/build-version")
    public ResponseEntity<String> getBuildVersion() {
        return ResponseEntity.ok(buildVersion);
    }

}
