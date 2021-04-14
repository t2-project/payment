package de.unistuttgart.t2.payment;


import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.unistuttgart.t2.payment.saga.CreditCardInfo;

public class PaymentService {
	
	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	/**
	 * executes the payment.
	 * 
	 * always fails on with checksum "bad" and always succeeds with checksum "good".
	 * if checksum is neither if randomly fails.
	 * 
	 * @param card information about the creditcard
	 * @param total money to be payed
	 * @throws Exception if payment failed
	 */
	public void handleSagaAction(CreditCardInfo card, double total) throws Exception{
		if (card.getChecksum().equals("bad")) 
			throw new Exception("forced failure");
		if (card.getChecksum().equals("good"))
			return;
		
		if (new Random().nextInt(10) == 0) {
			throw new Exception("random failure");
		}
	}
}
