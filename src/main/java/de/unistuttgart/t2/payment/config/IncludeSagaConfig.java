package de.unistuttgart.t2.payment.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import de.unistuttgart.t2.payment.saga.PaymentCommandHandler;
import io.eventuate.tram.sagas.participant.SagaCommandDispatcher;
import io.eventuate.tram.sagas.participant.SagaCommandDispatcherFactory;
import io.eventuate.tram.sagas.spring.participant.SagaParticipantConfiguration;
import io.eventuate.tram.spring.consumer.kafka.EventuateTramKafkaMessageConsumerConfiguration;
import io.eventuate.tram.spring.messaging.producer.jdbc.TramMessageProducerJdbcConfiguration;

/**
 * Configuration to run application with saga. use when cdc is up and running
 * somewhere.
 * 
 * @author maumau
 *
 */
@Import({ SagaParticipantConfiguration.class, TramMessageProducerJdbcConfiguration.class,
		EventuateTramKafkaMessageConsumerConfiguration.class }) // , OptimisticLockingDecoratorConfiguration.class})
@EnableJpaRepositories
@EnableAutoConfiguration
@Profile("!test")
@Configuration
public class IncludeSagaConfig {

	@Bean
	public PaymentCommandHandler paymentCommandHandler() {
		return new PaymentCommandHandler();
	}

	@Bean
	public SagaCommandDispatcher paymentCommandDispatcher(PaymentCommandHandler target,
			SagaCommandDispatcherFactory sagaCommandDispatcherFactory) {

		return sagaCommandDispatcherFactory.make("paymentCommandDispatcher", target.commandHandlers());
	}
}
