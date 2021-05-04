package de.unistuttgart.t2.payment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import de.unistuttgart.t2.payment.provider.PaymentData;
import de.unistuttgart.t2.payment.saga.CreditCardInfo;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;

/**
 * This service contacts the payment provider, e.g. some credit institute, to
 * execute the payment.
 * 
 * @author maumau
 *
 */
public class PaymentService {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Value("${t2.payment.provider.dummy.url}")
    protected String providerUrl;

    @Autowired
    RestTemplate template;

    // retry stuff
    RetryConfig config = RetryConfig.custom().maxAttempts(2).build();
    RetryRegistry registry = RetryRegistry.of(config);
    Retry retry = registry.retry("why_does_a_retry_need_a_name?");

    /**
     * contact some payment provider to execute the payment.
     * 
     * the call might either timeout, or the payment itself might fail, or it is
     * successful.
     * 
     * @param card  information about the credit card
     * @param total money to be payed
     */
    public void handleSagaAction(CreditCardInfo card, double total) {
        if (card.getChecksum().equals("tolazytostartprovider")) {
            return;
        }
        LOG.info("post to " + providerUrl);

        Retry.decorateSupplier(retry, () -> template.postForObject(providerUrl,
                new PaymentData(card.getCardNumber(), card.getCardOwner(), card.getChecksum(), total), Void.class))
                .get();
    }
}
