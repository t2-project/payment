package de.unistuttgart.t2.payment.saga;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import de.unistuttgart.t2.common.saga.SagaData;
import de.unistuttgart.t2.common.saga.commands.ActionCommand;
import de.unistuttgart.t2.common.saga.commands.SagaCommand;
import de.unistuttgart.t2.payment.PaymentService;
import io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder;
import io.eventuate.tram.commands.consumer.CommandHandlers;
import io.eventuate.tram.commands.consumer.CommandMessage;
import io.eventuate.tram.messaging.common.Message;
import io.eventuate.tram.sagas.participant.SagaCommandHandlersBuilder;

public class PaymentCommandHandler {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	@Autowired
	private PaymentService paymentService;

	public CommandHandlers commandHandlers() {
		return SagaCommandHandlersBuilder.fromChannel(SagaCommand.payment)
				.onMessage(ActionCommand.class, this::doAction).build();
	}

	/**
	 * the message remains in the incoming queue, until _this_ method returns it's
	 * message. if the service crashes midway, and no message is returned, then the
	 * incoming message remains in the queue!!
	 * 
	 * @param message the command 
	 * @return reply message, either success of failure
	 */
	public Message doAction(CommandMessage<ActionCommand> message) {
		LOG.info("payment received action");
		SagaData sagaData = message.getCommand().getData();
		
		CreditCardInfo info = new CreditCardInfo(sagaData.getCardNumber(), sagaData.getCardOwner(), sagaData.getChecksum());
		
		try {
			paymentService.handleSagaAction(info, sagaData.getTotal());
			return CommandHandlerReplyBuilder.withSuccess();
		} catch (Exception e) {
			LOG.error("payment failed with : " + e.getMessage());
			return CommandHandlerReplyBuilder.withFailure();
		}
	}
}
