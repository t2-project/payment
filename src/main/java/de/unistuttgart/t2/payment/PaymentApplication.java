package de.unistuttgart.t2.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

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
	public RestTemplate template() {	
//		int timeout = 5000;
//	    HttpComponentsClientHttpRequestFactory clientHttpRequestFactory
//	      = new HttpComponentsClientHttpRequestFactory();
//	    clientHttpRequestFactory.setConnectTimeout(timeout);
//		
//		return new RestTemplate(clientHttpRequestFactory);
		return new RestTemplate();
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
