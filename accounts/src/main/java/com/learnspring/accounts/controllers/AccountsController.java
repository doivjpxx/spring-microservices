package com.learnspring.accounts.controllers;

import com.learnspring.accounts.constants.AccountsConstants;
import com.learnspring.accounts.dtos.AccountContactInfoDto;
import com.learnspring.accounts.dtos.CustomerDto;
import com.learnspring.accounts.dtos.ResponseDto;
import com.learnspring.accounts.services.IAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name="CRUD for accounts",
        description = "This API is used to perform CRUD operations on accounts"
)
@RestController
@Validated
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
public class AccountsController {

    @Autowired
    private IAccountService accountService;

    @Autowired
    private AccountContactInfoDto accountContactInfoDto;

    @Autowired
    private Environment environment;

    @Operation(
            summary = "Create an account",
            description = "This API is used to create an account"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Account created successfully"
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {
        accountService.createAccount(customerDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
    }

    @Operation(
            summary = "Fetch an account via mobileNumber",
            description = "This API is used to fetch an account based on the provided mobileNumber"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Account fetched successfully"
    )
    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchAccount(@RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "AccountNumber must be 10 digits") String mobileNumber) {
        CustomerDto customerDto = accountService.getAccount(mobileNumber);

        return ResponseEntity.status(HttpStatus.OK)
                .body(customerDto);

    }

    @Operation(
            summary = "Update an account",
            description = "This API is used to update an account"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Account updated successfully"
    )
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccount(@RequestBody CustomerDto customerDto) {
        boolean isUpdated = accountService.updateAccount(customerDto);

        if (!isUpdated) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_UPDATE));
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
    }

    @Operation(
            summary = "Delete an account",
            description = "This API is used to delete an account"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Account deleted successfully"
    )
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccountDetails(@RequestParam
                                                            @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                            String mobileNumber) {
        boolean isDeleted = accountService.deleteAccount(mobileNumber);
        if(isDeleted) {
            return ResponseEntity
                .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_DELETE));
        }
    }

    @GetMapping("/version")
    public ResponseEntity<String> getBuildInfo() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(environment.getProperty("build.version"));
    }

    @GetMapping("/contact")
    public ResponseEntity<AccountContactInfoDto> getContactInfo() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(accountContactInfoDto);
    }
}
