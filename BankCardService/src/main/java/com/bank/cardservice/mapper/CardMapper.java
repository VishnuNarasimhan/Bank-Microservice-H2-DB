package com.bank.cardservice.mapper;

import com.bank.cardservice.dto.CardDto;
import com.bank.cardservice.entity.Card;

public class CardMapper {
    public static CardDto maptoCardDto(Card card, CardDto cardDto){
        cardDto.setCardNumber(card.getCardNumber());
        cardDto.setMobileNumber(card.getMobileNumber());
        cardDto.setCardType(card.getCardType());
        cardDto.setTotalLimit(card.getTotalLimit());
        cardDto.setAmountUsed(card.getAmountUsed());
        cardDto.setAvailableAmount(card.getAvailableAmount());
        return cardDto;
    }

    public static Card maptoCard(CardDto cardDto, Card card){
        card.setMobileNumber(cardDto.getMobileNumber());
        card.setCardType(cardDto.getCardType());
        card.setTotalLimit(cardDto.getTotalLimit());
        card.setAmountUsed(cardDto.getAmountUsed());
        card.setAvailableAmount(cardDto.getAvailableAmount());
        return card;
    }
}
