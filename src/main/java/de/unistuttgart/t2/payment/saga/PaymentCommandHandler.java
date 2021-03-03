package de.unistuttgart.t2.payment.saga;

import org.springframework.beans.factory.annotation.Autowired;

import de.unistuttgart.t2.common.commands.CheckCreditCommand;
import de.unistuttgart.t2.payment.PaymentService;
import io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder;
import io.eventuate.tram.commands.consumer.CommandHandlers;
import io.eventuate.tram.commands.consumer.CommandMessage;
import io.eventuate.tram.messaging.common.Message;
import io.eventuate.tram.sagas.participant.SagaCommandHandlersBuilder;



public class PaymentCommandHandler {

	@Autowired
	private PaymentService paymentService;
	
	public CommandHandlers commandHandlers() {
		return SagaCommandHandlersBuilder.fromChannel("payment").onMessage(CheckCreditCommand.class, this::checkCredit).build();
	}
	
	public Message checkCredit(CommandMessage<CheckCreditCommand> cm) {
		CheckCreditCommand ccc = cm.getCommand();
		if (paymentService.checkCredit(ccc.getTotal())) {
			return CommandHandlerReplyBuilder.withSuccess();
		} else {
			return CommandHandlerReplyBuilder.withFailure();
		}
	}
}
