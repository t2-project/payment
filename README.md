# payment

payment service for the t2 store.
contacts some credit institute to execute payment.

## application properties

property | read from env var | description |
-------- | ----------------- | ----------- |
t2.payment.provider.dummy.url | T2_PAYMENT_PROVIDER_DUMMY_URL | url of the credit institute
t2.payment.provider.timeout | T2_PAYMENT_PROVIDER_TIMEOUT | timeout in seconds



properties for the CDC:
see (eventuate tram cdc)[https://eventuate.io/docs/manual/eventuate-tram/latest/getting-started-eventuate-tram.html] for explanations.

property | read from env var | description |
-------- | ----------------- | ----------- |
spring.datasource.url | SPRING_DATASOURCE_URL |
spring.datasource.username | SPRING_DATASOURCE_USERNAME |
spring.datasource.password | SPRING_DATASOURCE_PASSWORD |
spring.datasource.driver-class-name | SPRING_DATASOURCE_DRIVER_CLASS_NAME |
eventuatelocal.kafka.bootstrap.servers | EVENTUATELOCAL_KAFKA_BOOTSTRAP_SERVERS |
eventuatelocal.zookeeper.connection.string | EVENTUATELOCAL_ZOOKEEPER_CONNECTION_STRING |