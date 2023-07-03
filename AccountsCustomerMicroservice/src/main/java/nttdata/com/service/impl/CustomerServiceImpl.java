package nttdata.com.service.impl;

import lombok.AllArgsConstructor;
import nttdata.com.dto.AccountDTO;
import nttdata.com.dto.CreditCardDTO;
import nttdata.com.dto.CreditDTO;
import nttdata.com.dto.CustomerDTO;
import nttdata.com.client.feign.CreditCardClient;
import nttdata.com.client.feign.CreditClient;
import nttdata.com.model.Account;
import nttdata.com.model.Credit;
import nttdata.com.model.CreditCard;
import nttdata.com.model.Customer;
import nttdata.com.repository.AccountRepository;
import nttdata.com.repository.CustomerRepository;
import nttdata.com.service.CustomerService;
import nttdata.com.utils.AccountConverter;
import nttdata.com.utils.CreditCardConverter;
import nttdata.com.utils.CreditConverter;
import nttdata.com.utils.CustomerConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    @Autowired
    private CreditClient creditClient;
    @Autowired
    private CreditCardClient creditCardClient;

    @Override
    public Mono<CustomerDTO> createCustomer(CustomerDTO customerDTO) {
        Customer customer = CustomerConverter.customerDTOToCustomer(customerDTO);
        return customerRepository.save(customer)
                .map(CustomerConverter::customerToCustomerDTO);
    }

    @Override
    public Mono<CustomerDTO> updateCustomer(String id, CustomerDTO customerDTO) {
        return customerRepository.findById(id)
                .flatMap(customer -> {
                    customer.setName(customerDTO.getName());
                    customer.setType(customerDTO.getType());

                    List<Account> accounts = customerDTO.getAccounts().stream()
                            .map(AccountConverter::accountDTOToAccount)
                            .collect(Collectors.toList());

                    customer.setAccountReferences(accounts);

                    List<Credit> credits = customerDTO.getCredits().stream()
                            .map(CreditConverter::DTOToCredit)
                            .collect(Collectors.toList());

                    customer.setCreditReferences(credits);

                    List<CreditCard> creditCards = customerDTO.getCreditCards().stream()
                            .map(CreditCardConverter::DTOToCreditCard)
                            .collect(Collectors.toList());

                    customer.setCreditCardReferences(creditCards);

                    return customerRepository.save(customer)
                            .map(CustomerConverter::customerToCustomerDTO);
                })
                .switchIfEmpty(Mono.error(new RuntimeException("Customer not found")));
    }

    @Override
    public Mono<CustomerDTO> getCustomerById(String id) {
        return customerRepository.findById(id)
                .map(CustomerConverter::customerToCustomerDTO);
    }
}