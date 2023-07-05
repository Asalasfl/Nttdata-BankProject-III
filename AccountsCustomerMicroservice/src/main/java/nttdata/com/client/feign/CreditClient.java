package nttdata.com.client.feign;

import nttdata.com.dto.CreditDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ReactiveFeignClient(name = "micro-credit")
public interface CreditClient {

    @GetMapping("/api/credits/credits")
   public Flux<CreditDTO> createCredit(@RequestBody CreditDTO creditDTO);
}
