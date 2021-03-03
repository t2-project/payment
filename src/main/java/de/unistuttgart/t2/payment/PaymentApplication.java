package de.unistuttgart.t2.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import de.unistuttgart.t2.payment.saga.PaymentCommandHandler;
import io.eventuate.tram.sagas.participant.SagaCommandDispatcher;
import io.eventuate.tram.sagas.participant.SagaCommandDispatcherFactory;
import io.eventuate.tram.sagas.spring.participant.SagaParticipantConfiguration;
import io.eventuate.tram.spring.consumer.kafka.EventuateTramKafkaMessageConsumerConfiguration;
import io.eventuate.tram.spring.messaging.producer.jdbc.TramMessageProducerJdbcConfiguration;

@Import({SagaParticipantConfiguration.class,
	TramMessageProducerJdbcConfiguration.class,
    EventuateTramKafkaMessageConsumerConfiguration.class})//, OptimisticLockingDecoratorConfiguration.class})
@EnableJpaRepositories
@EnableAutoConfiguration
@SpringBootApplication
public class PaymentApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentApplication.class, args);
	}

	@Bean
	public PaymentService paymentService() {
		return new PaymentService();
	}

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
