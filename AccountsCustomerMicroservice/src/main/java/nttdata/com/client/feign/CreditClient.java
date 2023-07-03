package nttdata.com.client.feign;

import nttdata.com.dto.CreditDTO;
import nttdata.com.model.Credit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//@Configuration
@FeignClient(name = "micro-credit")
public interface CreditClient {

    @GetMapping("/api/credits/{id}")
   public Mono<Credit> createCredit(@RequestBody CreditDTO creditDTO);
}
