package de.unistuttgart.t2.payment.provider;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * All the informations usually found on a creditcard.
 * 
 * part of the SagaData
 * 
 * @author maumau
 *
 */
public class PaymentData {
	@JsonProperty("cardNumber")
	private String cardNumber;
	@JsonProperty("cardOwner")
	private String cardOwner;
	@JsonProperty("checksum")
	private String checksum;
	@JsonProperty("total")
	private double total;
	
	public PaymentData() {
		super();
	}
	
	@JsonCreator
	public PaymentData(String cardNumber, String cardOwner, String checksum, double total) {
		super();
		this.cardNumber = cardNumber;
		this.cardOwner = cardOwner;
		this.checksum = checksum;
		this.total = total;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public String getCardOwner() {
		return cardOwner;
	}
	public String getChecksum() {
		return checksum;
	}
}
