package de.unistuttgart.t2.payment.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "payment", itemResourceRel = "payment", collectionResourceRel = "payment")
public interface PaymentRepository extends MongoRepository<PaymentItem, String> {

}
