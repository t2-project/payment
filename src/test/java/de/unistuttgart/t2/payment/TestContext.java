package de.unistuttgart.t2.payment;

import org.springframework.context.annotation.*;
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
