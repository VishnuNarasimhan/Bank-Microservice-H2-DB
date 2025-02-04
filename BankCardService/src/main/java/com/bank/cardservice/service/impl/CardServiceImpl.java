package com.bank.cardservice.service.impl;

import com.bank.cardservice.constants.CardConstants;
import com.bank.cardservice.dto.CardDto;
import com.bank.cardservice.entity.Card;
import com.bank.cardservice.exception.CardAlreadyExistsException;
import com.bank.cardservice.exception.ResourceNotFoundException;
import com.bank.cardservice.mapper.CardMapper;
import com.bank.cardservice.repository.CardRepository;
import com.bank.cardservice.service.ICardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class CardServiceImpl implements ICardService {
    private CardRepository cardRepository;

    /**
     * @param mobileNumber - Mobile number of the Customer
     */
    @Override
    public void createCard(String mobileNumber) {
        Optional<Card> optionalCard = cardRepository.findByMobileNumber(mobileNumber);
        if (optionalCard.isPresent()) {
            throw new CardAlreadyExistsException("Card already registered with given mobileNumber " + mobileNumber);
        }
        cardRepository.save(createNewCard(mobileNumber));
    }

    /**
     * @param mobileNumber - Mobile Number of the Customer
     * @return the new card details
     */
    private Card createNewCard(String mobileNumber) {
        Card card = new Card();
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        card.setCardNumber(Long.toString(randomCardNumber));
        card.setMobileNumber(mobileNumber);
        card.setCardType(CardConstants.CREDIT_CARD);
        card.setTotalLimit(CardConstants.NEW_CARD_LIMIT);
        card.setAmountUsed(0);
        card.setAvailableAmount(CardConstants.NEW_CARD_LIMIT);
        return card;
    }

    /**
     * @param mobileNumber - Mobile number of the customer
     * @return Card details based on the given number
     */
    @Override
    public CardDto fetchCard(String mobileNumber) {
        Card card = cardRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card", "Mobile Number", mobileNumber)
        );
        return CardMapper.maptoCardDto(card, new CardDto());
    }

    /**
     * @param cardDto - CardDto Object
     * @return boolean indicating if the update of card details is successful or not
     */
    @Override
    public boolean updateCard(CardDto cardDto) {
        boolean isUpdated = false;

        if (cardDto != null) {
            Card card = cardRepository.findByCardNumber(cardDto.getCardNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Card", "Card Number", cardDto.getCardNumber())
            );

            CardMapper.maptoCard(cardDto, card);
            cardRepository.save(card);

            isUpdated = true;
        }
        return isUpdated;
    }

    /**
     * @param mobileNumber - Input mobile number
     * @return boolean indicating if the card details is deleted successfully or not
     */
    @Override
    public boolean deleteCard(String mobileNumber) {
        Card card = cardRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card", "Card Number", mobileNumber)
        );
        cardRepository.deleteById(card.getCardId());
        return true;
    }

}
