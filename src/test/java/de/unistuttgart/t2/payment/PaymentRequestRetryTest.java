package de.unistuttgart.t2.payment;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;

import de.unistuttgart.t2.payment.saga.CreditCardInfo;

/**
 * Test whether Payment service retries requests as it should.
 * 
 * 
 * @author maumau
 *
 */
@ExtendWith(MockitoExtension.class)
@SpringJUnitConfig(TestContext.class)
@ActiveProfiles("test")
public class PaymentRequestRetryTest {

    @Autowired
    PaymentService service;

    @Autowired
    private RestTemplate template;

    private MockRestServiceServer mockServer;
    private String testurl = "http://foo.bar/pay";

    @BeforeEach
    public void setUp() {
        mockServer = MockRestServiceServer.createServer(template);
        service.providerUrl = testurl;
    }

    @Test
    public void testRequestPayment() {
        mockServer.expect(ExpectedCount.twice(), requestTo(testurl))
                .andRespond(withStatus(HttpStatus.INTERNAL_SERVER_ERROR));

        // execute
        assertThrows(InternalServerError.class, () -> {
            service.handleSagaAction(new CreditCardInfo("cardNumber", "cardOwner", "checksum"), 42.0);
        });
        mockServer.verify();
    }
}
