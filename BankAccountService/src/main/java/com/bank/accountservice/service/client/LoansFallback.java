package com.bank.accountservice.service.client;

import com.bank.accountservice.dto.LoansDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class LoansFallback implements LoansFeignClient{
    @Override
    public ResponseEntity<LoansDto> fetchLoanDetails(String mobileNumber, String correlationId) {
        return null;
    }
}
