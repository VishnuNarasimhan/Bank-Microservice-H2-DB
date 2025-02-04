package com.bank.accountservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
    name = "Accounts",
    description = "Schema to hold Account information"
)
public class AccountDto {

    @Schema(
        description = "Account Number of Bank Service", example = "3913333747"
    )
    @NotEmpty(message = "AccountNumber cannot be null or empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "account number should be valid")
    private Long accountNumber;

    @Schema(
        description = "Account type of Bank Service", example = "SAVINGS"
    )
    @NotEmpty(message = "AccountType cannot be null or empty")
    private String accountType;

    @Schema(
        description = "Bank address of Account provider", example = "New York Branch"
    )
    @NotEmpty(message = "bankAddress cannot be null or empty")
    private String bankAddress;
}
