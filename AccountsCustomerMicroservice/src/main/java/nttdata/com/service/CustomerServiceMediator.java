package nttdata.com.service;

import lombok.AllArgsConstructor;
import nttdata.com.dto.AccountDTO;
import nttdata.com.service.AccountService;
import nttdata.com.service.CustomerService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
@AllArgsConstructor
@Service
public class CustomerServiceMediator {

    private final CustomerService customerService;
    private final AccountService accountService;

    public Mono<CustomerService> addAccountToCustomer(String customerId, AccountDTO accountDTO) {
       return null;
   }
}
