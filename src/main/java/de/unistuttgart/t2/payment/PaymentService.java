package de.unistuttgart.t2.payment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import de.unistuttgart.t2.payment.provider.PaymentData;
import de.unistuttgart.t2.payment.saga.CreditCardInfo;

public class PaymentService {
	
	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	@Value("${payment.provider.dummy.url}")
	protected String providerUrl;
	
	@Autowired 
	RestTemplate template;
	
	/**
	 * contact some payment provider to execute the payment. 
	 * 
	 * the call might either timeout, or the payment itself might fail, or it is successful.
	 * 
	 * @param card information about the credit card
	 * @param total money to be payed
	 */
	public void handleSagaAction(CreditCardInfo card, double total) {
		if (card.getChecksum().equals("tolazytostartprovider")) {
			return;
		}
		LOG.info("post to " + providerUrl);
		template.postForObject(providerUrl, new PaymentData(card.getCardNumber(), card.getCardOwner(), card.getChecksum(), total), Void.class);
	}
}
