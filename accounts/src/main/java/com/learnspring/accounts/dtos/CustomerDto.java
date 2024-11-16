package com.learnspring.accounts.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        name = "Customer",
        description = "Schema for Customer and Account information"
)
public class CustomerDto {
    @Schema(
            name = "Name",
            example = "Phong Hy"
    )
    @NotEmpty(message = "Name cannot be empty")
    @Size(min = 2, max = 50, message = "Name should be between 2 and 50 characters")
    private String name;

    @Schema(
            name = "Email",
            example = "phonghy@x.com"
    )
    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Email should be valid")
    private String email;

    @Schema(
            name = "Mobile Number",
            example = "1234567890"
    )
    @NotEmpty(message = "Mobile number cannot be empty")
    @Size(min = 10, max = 10, message = "Mobile number should be 10 digits")
    @Pattern(regexp = "^[0-9]*$", message = "Mobile number should be numeric")
    private String mobileNumber;

    private AccountDto accountDto;
}
