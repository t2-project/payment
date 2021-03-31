package de.unistuttgart.t2.payment;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.client.RestTemplate;

import de.unistuttgart.t2.common.domain.saga.CreditCardInfo;

public class PaymentService {
	
	@Autowired
	RestTemplate template;
	
	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	public void handleSagaAction(CreditCardInfo card, double total) throws Exception{
		if (card.getChecksum().equals("666")) 
			throw new Exception("forced failure");
		//TODO : random failure
		
		//TODO : timeout failure
		
	}
	
	/**
	 * 
	 * @param card
	 * @param total
	 */
	private void makeTimeoutedCallToExternalService(CreditCardInfo card, double total) {
		// TODO :X
	}
}
