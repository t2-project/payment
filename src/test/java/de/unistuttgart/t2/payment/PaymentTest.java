package de.unistuttgart.t2.payment;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import de.unistuttgart.t2.payment.saga.CreditCardInfo;

/**
 * i dont even know what to test here...
 * @author maumau
 *
 */
@ExtendWith(MockitoExtension.class)
@SpringJUnitConfig(TestContext.class)
@ActiveProfiles("test")
public class PaymentTest {
	
	@Autowired
	PaymentService service;

	@Autowired
	private RestTemplate template;

	private MockRestServiceServer mockServer;
	
	CreditCardInfo info = new CreditCardInfo("cardNumber", "cardOwner", "checksum");
	double total = 1234.5;

	@BeforeEach
	public void setUp() {
		mockServer = MockRestServiceServer.createServer(template);
		service.providerUrl = "http://provider-cs/";
	}
	
	@Test
	public void testTest() throws Exception {	
		mockServer.expect(ExpectedCount.once(), requestTo(service.providerUrl)).andExpect(method(HttpMethod.POST))
				.andRespond(withStatus(HttpStatus.OK));

		// execute
		service.handleSagaAction(info, total);
		mockServer.verify();
	}
}
