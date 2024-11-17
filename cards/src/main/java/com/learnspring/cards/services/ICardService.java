package com.learnspring.cards.services;

import com.learnspring.cards.dtos.CardDto;

public interface ICardService {
    void createCard(String mobileNumber);
    CardDto fetchCard(String mobileNumber);
    boolean updateCard(CardDto cardDto);
    boolean deleteCard(String mobileNumber);
}
