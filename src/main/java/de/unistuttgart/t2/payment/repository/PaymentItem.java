package de.unistuttgart.t2.payment.repository;

import org.springframework.data.annotation.Id;

public class PaymentItem {
	@Id
	private String sessionId;
	private String cardNumber;
	private String cardOwner;
	private String checksum;
	
	public PaymentItem() {
		super();
	}

	public PaymentItem(String sessionId, String cardNumber, String cardOwner, String checksum) {
		super();
		this.sessionId = sessionId;
		this.cardNumber = cardNumber;
		this.cardOwner = cardOwner;
		this.checksum = checksum;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCardOwner() {
		return cardOwner;
	}

	public void setCardOwner(String cardOwner) {
		this.cardOwner = cardOwner;
	}

	public String getChecksum() {
		return checksum;
	}

	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}
}
