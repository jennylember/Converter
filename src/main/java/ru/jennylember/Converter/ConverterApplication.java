package ru.jennylember.Converter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.jennylember.Converter.repository.CurrencyRepository;

@EnableJpaRepositories(basePackageClasses = CurrencyRepository.class)
@EnableAutoConfiguration

@SpringBootApplication
public class ConverterApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConverterApplication.class, args);


	}

}
