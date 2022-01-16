package com.polarbookshop.quotefunction;

import com.polarbookshop.quotefunction.domain.Quote;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.nativex.hint.TypeAccess;
import org.springframework.nativex.hint.TypeHint;

@SpringBootApplication
@TypeHint(
		types = Quote.class,
		access = TypeAccess.DECLARED_METHODS
)
public class QuoteFunctionApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuoteFunctionApplication.class, args);
	}

}
