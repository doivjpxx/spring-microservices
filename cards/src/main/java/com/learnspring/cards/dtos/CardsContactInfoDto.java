package com.learnspring.cards.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "cards")
@Getter @Setter
public class CardsContactInfoDto {
    private String message;
    private Map<String, String> contactDetails;
}