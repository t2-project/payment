package de.unistuttgart.t2.payment.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.*;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.*;

import io.eventuate.tram.spring.consumer.jdbc.TramConsumerJdbcAutoConfiguration;
import io.eventuate.tram.spring.messaging.common.TramMessagingCommonAutoConfiguration;

/**
 * Configuration that excludes saga related things. Supposed to be use in case i want to run the payment without the
 * other components. Tram* are from the actual saga and Data* and Hibernate are from the db, that the CDC needs.
 *
 * @author maumau
 */
@EnableAutoConfiguration(exclude = { TramMessagingCommonAutoConfiguration.class,
                                     TramConsumerJdbcAutoConfiguration.class, DataSourceAutoConfiguration.class,
                                     DataSourceTransactionManagerAutoConfiguration.class,
                                     HibernateJpaAutoConfiguration.class })
@Profile("test")
@Configuration
public class ExcludeSagaConfig {

}
