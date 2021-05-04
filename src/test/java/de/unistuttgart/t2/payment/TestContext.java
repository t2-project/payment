package de.unistuttgart.t2.payment;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;

@Configuration
@Profile("test")
public class TestContext {

    @Bean
    public RestTemplate template() {
        return new RestTemplate();
    }

    @Bean
    public PaymentService service() {
        return new PaymentService();
    }
}
