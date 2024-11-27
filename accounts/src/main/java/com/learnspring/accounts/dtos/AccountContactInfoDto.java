package com.learnspring.accounts.dtos;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "accounts")
public record AccountContactInfoDto(String message, Map<String, String> contactDetails) {

}
