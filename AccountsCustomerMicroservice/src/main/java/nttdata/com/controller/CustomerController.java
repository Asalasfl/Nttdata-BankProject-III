package nttdata.com.controller;

import lombok.AllArgsConstructor;
import nttdata.com.dto.AccountDTO;
import nttdata.com.dto.CustomerDTO;
import nttdata.com.service.CustomerServiceImpl;
import nttdata.com.service.CustomerServiceMediator;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
@AllArgsConstructor
@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerServiceImpl customerServiceImpl;
    private final CustomerServiceMediator customerServiceMediator;

    @PostMapping("/{customerId}/accounts")
    public Mono<Void> addAccountToCustomer(@PathVariable String customerId, @RequestBody AccountDTO accountDTO) {
        return customerServiceMediator.addAccountToCustomer(customerId, accountDTO).then();
    }
    @PostMapping(value = "/createCustomer", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDTO) {
        return customerServiceImpl.createCustomer(customerDTO);
    }
    @GetMapping(value = "/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<CustomerDTO> getCustomerById(@PathVariable String id) {
        return customerServiceImpl.getCustomerById(id);
    }
    @PutMapping(value = "/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<CustomerDTO> updateCustomer(@PathVariable String id, @RequestBody CustomerDTO customerDTO) {
        return customerServiceImpl.updateCustomer(id, customerDTO);
    }
}