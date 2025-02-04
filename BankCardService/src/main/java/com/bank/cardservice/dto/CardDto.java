package com.bank.cardservice.dto;

import lombok.Data;

@Data
public class CardDto {
    private String cardNumber;
    private String mobileNumber;
    private String cardType;
    private int totalLimit;
    private int amountUsed;
    private int availableAmount;
}
