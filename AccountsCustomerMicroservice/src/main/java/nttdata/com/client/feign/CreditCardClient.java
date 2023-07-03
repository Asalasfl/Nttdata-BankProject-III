package nttdata.com.client.feign;

import nttdata.com.dto.CreditCardDTO;
import nttdata.com.model.CreditCard;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//@Configuration
@FeignClient(name = "micro-credit-card")
public interface CreditCardClient {

    @GetMapping("/api/credit-cards/credits-card")
    public Mono<CreditCardDTO> createCreditCard(@RequestBody CreditCardDTO creditCardDTO);
}
