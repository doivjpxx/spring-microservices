package com.learnspring.accounts.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Schema(
        name = "Account",
        description = "Schema for Account information"
)
@Data
public class AccountDto {
    @Schema(
            name = "AccountNumber",
            example = "1234567890"
    )
    @NotEmpty(message = "AccountNumber can not be a null or empty")
    @Pattern(regexp= "(^$|[0-9]{10})", message = "AccountNumber must be 10 digits")
    private Long accountNumber;

    @Schema(
            name = "AccountType",
            example = "Savings"
    )
    @NotEmpty(message = "AccountType can not be a null or empty")
    private String accountType;

    @Schema(
            name = "BranchAddress",
            example = "123, ABC Street, XYZ City"
    )
    @NotEmpty(message = "BranchAddress can not be a null or empty")
    private String branchAddress;
}
