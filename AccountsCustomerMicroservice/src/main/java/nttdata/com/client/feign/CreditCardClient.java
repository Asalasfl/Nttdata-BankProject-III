package nttdata.com.client.feign;

import nttdata.com.dto.CreditCardDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Flux;


@ReactiveFeignClient(name = "micro-credit-card")
public interface CreditCardClient {

    @GetMapping("/api/credit-cards/credits-card")
    public Flux<CreditCardDTO> createCreditCard(@RequestBody CreditCardDTO creditCardDTO);

}
