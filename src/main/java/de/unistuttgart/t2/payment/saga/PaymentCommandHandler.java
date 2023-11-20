package de.unistuttgart.t2.payment.saga;

import de.unistuttgart.t2.common.saga.SagaData;
import de.unistuttgart.t2.common.saga.commands.ActionCommand;
import de.unistuttgart.t2.common.saga.commands.SagaCommand;
import de.unistuttgart.t2.payment.PaymentService;
import io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder;
import io.eventuate.tram.commands.consumer.CommandHandlers;
import io.eventuate.tram.commands.consumer.CommandMessage;
import io.eventuate.tram.messaging.common.Message;
import io.eventuate.tram.sagas.participant.SagaCommandHandlersBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * handles messages for the payment service. listens to the {@code payment} queue. executes payment upon receiving a
 * {@link de.unistuttgart.t2.common.saga.commands.ActionCommand ActionCommand} or rejects an does not listen for
 * {@link de.unistuttgart.t2.common.saga.commands.CompensationCommand CompensationCommand} because the payment service
 * has the pivot transaction (i.e. needs no compensation)
 *
 * @author stiesssh
 */
public class PaymentCommandHandler {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired
    private PaymentService paymentService;

    public CommandHandlers commandHandlers() {
        return SagaCommandHandlersBuilder.fromChannel(SagaCommand.payment)
            .onMessage(ActionCommand.class, this::doAction).build();
    }

    /**
     * do some payment.
     *
     * @param message the command
     * @return reply message, either success of failure
     */
    public Message doAction(CommandMessage<ActionCommand> message) {
        LOG.info("payment received action");
        SagaData sagaData = message.getCommand().getData();

        try {
            paymentService.handleSagaAction(sagaData);
            return CommandHandlerReplyBuilder.withSuccess();
        } catch (Exception e) {
            LOG.error("payment failed with : " + e.getMessage());
            return CommandHandlerReplyBuilder.withFailure();
        }
    }
}
