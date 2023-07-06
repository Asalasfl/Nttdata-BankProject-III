package nttdata.com.feign;

import nttdata.com.dto.CustomerDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Flux;
@Component
@ReactiveFeignClient(name = "micro-customer-account")
public interface CustomerClient {

    @GetMapping("/api/customers/{id}")
   public Flux<CustomerDTO> getCustomerById(@PathVariable("id")  String customerId);
}
