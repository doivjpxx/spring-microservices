package com.learnspring.cards.services.impl;

import com.learnspring.cards.CardsConstants.CardsConstants;
import com.learnspring.cards.dtos.CardDto;
import com.learnspring.cards.entities.Card;
import com.learnspring.cards.exceptions.CardAlreadyExistsException;
import com.learnspring.cards.mappers.CardsMapper;
import com.learnspring.cards.repositories.CardsRepository;
import com.learnspring.cards.services.ICardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class CardServiceImpl implements ICardService {
    private CardsRepository cardsRepository;

    @Override
    public void createCard(String mobileNumber) {
        Optional<Card> cardDtoOptional = cardsRepository.findByMobileNumber(mobileNumber);
        if (cardDtoOptional.isPresent()) {
            throw new CardAlreadyExistsException("Card already registered with given mobileNumber " + mobileNumber);
        }
        Card newCard = new Card();
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        newCard.setCardNumber(Long.toString(randomCardNumber));
        newCard.setMobileNumber(mobileNumber);
        newCard.setCardType(CardsConstants.CREDIT_CARD);
        newCard.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
        newCard.setAmountUsed(0);
        newCard.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);
        cardsRepository.save(newCard);
    }

    @Override
    public CardDto fetchCard(String mobileNumber) {
        Card card = cardsRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new RuntimeException("Card not found for the given mobile number"));

        return CardsMapper.mapToCardDto(card, new CardDto());
    }

    @Override
    public boolean updateCard(CardDto cardDto) {
        Card card = cardsRepository.findByCardNumber(cardDto.getCardNumber())
                .orElseThrow(() -> new RuntimeException("Card not found for the given card number"));

        CardsMapper.mapToCard(cardDto, card);
        cardsRepository.save(card);

        return true;
    }

    @Override
    public boolean deleteCard(String mobileNumber) {
        Card card = cardsRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new RuntimeException("Card not found for the given mobile number"));

        cardsRepository.delete(card);

        return true;
    }
}
