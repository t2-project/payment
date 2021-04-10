package de.unistuttgart.t2.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

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
}
