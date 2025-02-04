package com.bank.accountservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        name = "CustomerDetails",
        description = "Schema to hold Customer, Account, Card and Loan information"
)
public class CustomerDetailsDto {

    @Schema(
            description = "Name of the customer", example = "Vishnu"
    )
    @NotEmpty(message = "Name cannot be null or empty")
    @Size(min = 2, max = 30, message = "The length of the customer name should be between 5 and 30")
    private String name;

    @Schema(
            description = "Email address of the customer", example = "vishnunarasim20@gmail.com"
    )
    @NotEmpty(message = "Email address cannot be null or empty")
    @Email(message = "Email should be valid")
    private String mail;

    @Schema(
            description = "Mobile number of the customer", example = "9090912123"
    )
    @NotEmpty(message = "Mobile number cannot be null or empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number cannot be null or empty")
    private String mobileNumber;

    @Schema(
            description = "Account details of the Customer"
    )
    private AccountDto accountDetails;

    @Schema(
            description = "Cards details of the Customer"
    )
    private CardDto cardDetails;

    @Schema(
            description = "Loans details of the Customer"
    )
    private LoansDto loansDetails;

}
