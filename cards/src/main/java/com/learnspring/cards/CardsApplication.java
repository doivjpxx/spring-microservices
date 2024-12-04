package com.learnspring.cards;

import com.learnspring.cards.dtos.LoansContactInfoDto;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@OpenAPIDefinition(
		info = @io.swagger.v3.oas.annotations.info.Info(
				title = "Cards API",
				version = "1.0",
				description = "Documentation Cards API v1.0"
		)
)
@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(value = { LoansContactInfoDto.class })
public class CardsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CardsApplication.class, args);
	}

}
