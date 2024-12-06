package com.learnspring.cards.controllers;

import com.learnspring.cards.CardsConstants.CardsConstants;
import com.learnspring.cards.dtos.CardDto;
import com.learnspring.cards.dtos.CardsContactInfoDto;
import com.learnspring.cards.dtos.ResponseDto;
import com.learnspring.cards.services.ICardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "CRUD for cards",
        description = "This API is used to perform CRUD operations on cards"
)
@Validated
@RestController
@RequestMapping(value = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
public class CardsController {

    @Autowired
    private ICardService cardService;

    @Autowired
    private CardsContactInfoDto cardsContactInfoDto;

    @Autowired
    private Environment environment;

    @Operation(
            summary = "Create a card",
            description = "This API is used to create a card"
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createCard(@Valid @RequestParam
                                                  @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
                                                  String mobileNumber) {
        cardService.createCard(mobileNumber);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(CardsConstants.STATUS_201, CardsConstants.MESSAGE_201));

    }

    @Operation(
            summary = "Fetch card",
            description = "This API is used to fetch a card"
    )
    @GetMapping("/fetch")
    public ResponseEntity<CardDto> fetchCard(@Valid @RequestParam
                                             @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
                                             String mobileNumber) {
        CardDto card = cardService.fetchCard(mobileNumber);

        return ResponseEntity.status(HttpStatus.OK).body(card);
    }

    @Operation(
            summary = "Update card",
            description = "This API is used to update a card"
    )
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateCard(@Valid @RequestBody CardDto cardDto) {
        cardService.updateCard(cardDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto(CardsConstants.STATUS_200, CardsConstants.MESSAGE_200));
    }

    @Operation(
            summary = "Delete card",
            description = "This API is used to delete a card"
    )
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteCard(@Valid @RequestParam
                                                  @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
                                                  String mobileNumber) {
        cardService.deleteCard(mobileNumber);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto(CardsConstants.STATUS_200, CardsConstants.MESSAGE_200));
    }

    @GetMapping("/version")
    public ResponseEntity<String> getVersion() {
        return ResponseEntity.status(HttpStatus.OK).body(environment.getProperty("build.version"));
    }

    @GetMapping("/contact")
    public ResponseEntity<CardsContactInfoDto> getContactInfo() {
        return ResponseEntity.status(HttpStatus.OK).body(cardsContactInfoDto);
    }

}
