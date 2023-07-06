package nttdata.com.repository;

import nttdata.com.model.Credit;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CreditRepository extends ReactiveMongoRepository<Credit, String> {

    Mono<Credit> findByIdAndType(String id, String type);
}
