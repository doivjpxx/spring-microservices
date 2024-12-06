package com.learnspring.cards.dtos;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "cards")
public record CardsContactInfoDto(String message, Map<String, String> contactDetails) {
}