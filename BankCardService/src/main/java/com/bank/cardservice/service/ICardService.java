package com.bank.cardservice.service;

import com.bank.cardservice.dto.CardDto;
import com.bank.cardservice.exception.ResourceNotFoundException;

public interface ICardService {

    /**
     * @param mobileNumber - Mobile number of the Customer
     */
    void createCard(String mobileNumber);

    /**
     *
     * @param mobileNumber - Mobile number of the customer
     * @return Card details based on the given number
     */
    CardDto fetchCard(String mobileNumber) throws ResourceNotFoundException;

    /**
     *
     * @param cardDto - CardDto Object
     * @return boolean indicating if the update of card details is successful or not
     */
    boolean updateCard(CardDto cardDto);

    /**
     *
     * @param mobileNumber - Input mobile number
     * @return boolean indicating if the card details is deleted successfully or not
     */
    boolean deleteCard(String mobileNumber);
}
