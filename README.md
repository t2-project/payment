# Payment Service

This service is part of the T2-Project.
It is responsible for contacting the payment provider.

In a more real situation, a payment service would contact different payment providers, e.g. paypal or a certain credit institute based on which payment method a user chose.
However this payment service knows only one payment provider and always contact that one.

The payment provider can be e.g. the [Credit Institute Service](https://github.com/t2-project/creditinstitute) or the [e2e Test Service](https://github.com/t2-project/e2etest).

## Build and Run

Refer to the [Documentation](https://t2-documentation.readthedocs.io/en/latest/guides/deploy.html) on how to build, run or deploy the T2-Project services.

## Usage

This service listens to incoming messages on a queue named 'payment'.
The [orchestrator](https://github.com/t2-project/orchestrator) sends messages to that queue.

Normally you do not want to interact directly with the payment service.
However it might by useful to run it locally for debugging.

## Application Properties

| property | read from env var | description |
| -------- | ----------------- | ----------- |
| t2.payment.provider.dummy.url | T2_PAYMENT_PROVIDER_DUMMY_URL | url of the payment provider. |
| t2.payment.provider.timeout | T2_PAYMENT_PROVIDER_TIMEOUT | timeout in seconds. the payment service waits this long for an reply from the payment provider. |

#### Properties for the CDC

See [eventuate tram cdc](https://eventuate.io/docs/manual/eventuate-tram/latest/getting-started-eventuate-tram.html) for explanations.

| property | read from env var |
| -------- | ----------------- |
| spring.datasource.url | SPRING_DATASOURCE_URL |
| spring.datasource.username | SPRING_DATASOURCE_USERNAME |
| spring.datasource.password | SPRING_DATASOURCE_PASSWORD |
| spring.datasource.driver-class-name | SPRING_DATASOURCE_DRIVER_CLASS_NAME |
| eventuatelocal.kafka.bootstrap.servers | EVENTUATELOCAL_KAFKA_BOOTSTRAP_SERVERS |
| eventuatelocal.zookeeper.connection.string | EVENTUATELOCAL_ZOOKEEPER_CONNECTION_STRING |
