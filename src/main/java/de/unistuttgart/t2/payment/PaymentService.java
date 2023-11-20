package de.unistuttgart.t2.payment;

import de.unistuttgart.t2.common.saga.SagaData;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

/**
 * Contacts a payment provider, e.g. some credit institute, to execute the payment.
 *
 * @author maumau
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
    Retry retry = registry.retry("paymentRetry");

    /**
     * contact some payment provider to execute the payment. the call might either timeout, or the payment itself might
     * fail, or it is successful.
     *
     * @param data information about payment
     */
    public void handleSagaAction(SagaData data) {
        LOG.info("post to " + providerUrl);

        Retry.decorateSupplier(retry, () -> template.postForObject(providerUrl,
            new PaymentData(data.getCardNumber(), data.getCardOwner(), data.getChecksum(), data.getTotal()),
            Void.class))
            .get();
    }
}
