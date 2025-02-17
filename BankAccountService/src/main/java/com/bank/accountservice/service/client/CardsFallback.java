package com.bank.accountservice.service.client;

import com.bank.accountservice.dto.CardDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CardsFallback implements CardsFeignClient{
    @Override
    public ResponseEntity<CardDto> getCard(String mobileNumber, String correlationId) {
        return null;
    }
}
