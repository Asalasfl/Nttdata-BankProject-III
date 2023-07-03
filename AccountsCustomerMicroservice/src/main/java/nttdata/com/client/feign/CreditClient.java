package nttdata.com.client.feign;

import nttdata.com.model.Credit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Flux;

//@Configuration
@FeignClient(name = "micro-credit")
public interface CreditClient {

    @GetMapping("/api/credits/{id}")
   public Flux<Credit> findByCreditId(@PathVariable("id") String id);
}
