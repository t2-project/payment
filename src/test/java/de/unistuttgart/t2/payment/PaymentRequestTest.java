package de.unistuttgart.t2.payment;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.client.*;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;
import org.springframework.web.client.RestTemplate;

import de.unistuttgart.t2.common.saga.SagaData;

/**
 * Test whether Payment service makes requests and retries as it should.
 *
 * @author maumau
 */
@ExtendWith(MockitoExtension.class)
@SpringJUnitConfig(TestContext.class)
@ActiveProfiles("test")
public class PaymentRequestTest {

    @Autowired
    PaymentService service;

    @Autowired
    private RestTemplate template;

    private MockRestServiceServer mockServer;
    private final String testurl = "http://foo.bar/pay";

    SagaData data = new SagaData("cardNumber", "cardOwner", "checksum", "sessionId", 1234.5);

    @BeforeEach
    public void setUp() {
        mockServer = MockRestServiceServer.createServer(template);
        service.providerUrl = testurl;
    }

    @Test
    public void testRequestRetry() {
        mockServer.expect(ExpectedCount.twice(), requestTo(testurl))
            .andRespond(withStatus(HttpStatus.INTERNAL_SERVER_ERROR));

        // execute
        assertThrows(InternalServerError.class, () -> {
            service.handleSagaAction(data);
        });
        mockServer.verify();
    }

    @Test
    public void testRequest() throws Exception {
        mockServer.expect(ExpectedCount.once(), requestTo(testurl)).andExpect(method(HttpMethod.POST))
            .andRespond(withStatus(HttpStatus.OK));

        // execute
        service.handleSagaAction(data);
        mockServer.verify();
    }
}
