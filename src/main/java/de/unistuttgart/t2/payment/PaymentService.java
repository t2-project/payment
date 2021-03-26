package de.unistuttgart.t2.payment;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import de.unistuttgart.t2.common.domain.Product;
import de.unistuttgart.t2.payment.repository.PaymentItem;
import de.unistuttgart.t2.payment.repository.PaymentRepository;

public class PaymentService {
	
	@Value("${t2.inventory.url}")
	private String inventoryUrl;
	
	@Autowired
	PaymentRepository paymentRepository;
	
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	public boolean handleSagaAction(String sessionId) {
		LOG.info("payment for order id " + sessionId);
		
		// get payment info
		if (!paymentRepository.existsById(sessionId)) {
			return false;
		}
		
		PaymentItem item = paymentRepository.findById(sessionId).get();
		
		
		// TODO : get total 
		//double total = calculateTotal();
		
		return true;
	}
	
	private double calculateTotal(Map<String, Integer> unitsPerProduct) {
		double rval = 0.0;
		
		for (String productId : unitsPerProduct.keySet()) {
			
			String ressourceUrl = inventoryUrl + "/get/" + productId;
			LOG.info("get from " + ressourceUrl);

			try {
				Product product = (new RestTemplate()).getForObject(ressourceUrl, Product.class);
				rval += unitsPerProduct.get(productId) * product.getPrice();
			} catch (RestClientException e) {
				// Uhm...??
			}
		}
		return rval; 
	}
	
}
