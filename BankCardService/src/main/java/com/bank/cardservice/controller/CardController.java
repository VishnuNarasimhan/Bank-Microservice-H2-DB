package com.bank.cardservice.controller;

import com.bank.cardservice.constants.CardConstants;
import com.bank.cardservice.dto.CardDto;
import com.bank.cardservice.dto.CardsContactInfoDto;
import com.bank.cardservice.dto.ResponseDto;
import com.bank.cardservice.service.ICardService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
//@AllArgsConstructor
public class CardController {
    private static final Logger logger = LoggerFactory.getLogger(CardController.class);

    private final ICardService iCardService;

    private CardController(ICardService iCardService) {
        this.iCardService = iCardService;
    }

    @Value("${build.version}")
    private String buildVersion;

    @Autowired
    private CardsContactInfoDto cardsContactInfoDto;

    /**
     *
     * @param mobileNumber - Mobile number of the customer
     * @return ResponseDto
     */
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createCard(@RequestParam String mobileNumber) {
        iCardService.createCard(mobileNumber);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(CardConstants.STATUS_201, CardConstants.MESSAGE_201));
    }

    /**
     *
     * @param mobileNumber - Mobile number of the customer
     * @return ResponseDto
     */
    @GetMapping("/fetch")
    public ResponseEntity<CardDto> getCard(@RequestParam String mobileNumber,
                                           @RequestHeader("banks-correlation-id") String correlationId) {

        logger.debug("fetchCardDetails method started");
        CardDto cardDto = iCardService.fetchCard(mobileNumber);
        logger.debug("fetchCardDetails method ended");

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cardDto);
    }

    /**
     *
     * @param cardDto - CustomerDto Object
     * @return boolean indicating if the update of Card details is successful or not
     */
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateCard(@RequestBody CardDto cardDto) {
        boolean isUpdated = iCardService.updateCard(cardDto);

        if (isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(CardConstants.STATUS_200, CardConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(CardConstants.STATUS_417, CardConstants.MESSAGE_417_UPDATE));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteCard(@RequestParam String mobileNumber) {
        boolean isDeleted = iCardService.deleteCard(mobileNumber);

        if (isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(CardConstants.STATUS_200, CardConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(CardConstants.STATUS_417, CardConstants.MESSAGE_417_DELETE));
        }
    }

    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(buildVersion);
    }

    @GetMapping("/contact-info")
    public ResponseEntity<CardsContactInfoDto> getContactInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cardsContactInfoDto);
    }

}
