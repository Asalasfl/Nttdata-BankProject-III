package nttdata.com.repository;

import nttdata.com.model.Payment;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface PaymentRepository extends ReactiveMongoRepository<Payment, String> {
    Flux<Payment> findByCreditId(String creditId);
}
