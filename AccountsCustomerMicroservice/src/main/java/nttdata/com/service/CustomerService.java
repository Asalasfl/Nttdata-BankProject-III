package nttdata.com.service;

import nttdata.com.dto.AccountDTO;
import nttdata.com.dto.CreditCardDTO;
import nttdata.com.dto.CustomerDTO;
import reactor.core.publisher.Mono;

public interface CustomerService {
    Mono<CustomerDTO> createCustomer(CustomerDTO CustomerDTO);

    Mono<CustomerDTO> updateCustomer(String customerId, CustomerDTO customerDTO);

    Mono<CustomerDTO> getCustomerById(String customerId);
    //Mono<CustomerService> addAccountToCustomer(String customerId, AccountDTO accountDTO);
}
