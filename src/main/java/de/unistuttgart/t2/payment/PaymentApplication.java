package de.unistuttgart.t2.payment;

import java.time.Duration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

import de.unistuttgart.t2.payment.config.ExcludeSagaConfig;
import de.unistuttgart.t2.payment.config.IncludeSagaConfig;

@Import({IncludeSagaConfig.class, ExcludeSagaConfig.class})
@EnableAutoConfiguration
@SpringBootApplication
public class PaymentApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentApplication.class, args);
	}

	@Bean
	public PaymentService paymentService() {
		return new PaymentService();
	}
	
	@Bean
	public RestTemplate template(
	        RestTemplateBuilder restTemplateBuilder) {

	    return restTemplateBuilder
	            .setConnectTimeout(Duration.ofSeconds(10))
	            .setReadTimeout(Duration.ofSeconds(10))
	            .build();
	}
}
