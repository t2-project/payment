package de.unistuttgart.t2.payment.saga;

import org.springframework.beans.factory.annotation.Autowired;

import de.unistuttgart.t2.common.commands.payment.PaymentAction;
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
		return SagaCommandHandlersBuilder.fromChannel("payment").onMessage(PaymentAction.class, this::checkCredit)
				.build();
	}

	/**
	 * the message remains in the incoming queue, until _this_ method returns it's
	 * message. if the service crashes midway, and no message is returned, then the incoming message remains in the queue!!
	 * 
	 * @param cm
	 * @return
	 */
	public Message checkCredit(CommandMessage<PaymentAction> cm) {
		PaymentAction ccc = cm.getCommand();
		if (paymentService.handleSagaAction(ccc.getSessionId())) {
			return CommandHandlerReplyBuilder.withSuccess();
		} else {
			return CommandHandlerReplyBuilder.withFailure();
		}
	}
}
