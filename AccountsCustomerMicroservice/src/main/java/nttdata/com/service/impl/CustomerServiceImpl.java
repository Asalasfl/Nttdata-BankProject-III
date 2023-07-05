package nttdata.com.service.impl;

import lombok.AllArgsConstructor;
import nttdata.com.dto.*;
import nttdata.com.client.feign.CreditCardClient;
import nttdata.com.client.feign.CreditClient;
import nttdata.com.model.*;
import nttdata.com.repository.AccountRepository;
import nttdata.com.repository.CustomerRepository;
import nttdata.com.service.CustomerService;
import nttdata.com.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
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

            List<Account> accounts = new ArrayList<>();
            if (customerDTO.getAccounts() != null) {
                for (AccountDTO accountDTO : customerDTO.getAccounts()) {
                    Account account = AccountConverter.accountDTOToAccount(accountDTO);
                    if (account.getId() == null) {
                        // Asignar un ID válido a la cuenta
                        account.setId(generateAccountId());
                    }
                    if (accountDTO.getType().equalsIgnoreCase("saving")) {
                        // Aplicar las restricciones para la cuenta de tipo "saving"
                        account.setCommissionFree(true);
                        account.setLimitMovement(true);
                        account.setMaxMonthlyMovements(5);
                    }
                    accounts.add(account);
                }
            }
            customer.setAccountReferences(accounts);

            Mono<Customer> saveCustomerMono = customerRepository.save(customer);

            Flux<Account> saveAccountsFlux = Flux.fromIterable(accounts)
                    .flatMap(accountRepository::save);

            // Utilizar el método createCredit del microservicio CreditClient
            Mono<CreditDTO> createCreditMono = creditClient.createCredit(customerDTO.getCredits().get(0));

            // Utilizar el método createCreditCard del microservicio CreditCardClient
            Mono<CreditCardDTO> createCreditCardMono = creditCardClient.createCreditCard(customerDTO.getCreditCards().get(0));

            return saveCustomerMono
                    .thenMany(saveAccountsFlux)
                    .collectList()
                    .flatMap(savedAccounts -> {
                        customer.setAccountReferences(savedAccounts);
                        return Mono.zip(createCreditMono, createCreditCardMono);
                    })
                    .map(tuple -> {
                        CreditDTO creditDTO = tuple.getT1();
                        CreditCardDTO creditCardDTO = tuple.getT2();

                        // Actualizar las referencias de crédito y tarjeta de crédito en el cliente
                        customer.setCreditReferences(Collections.singletonList(CreditConverter.DTOToCredit(creditDTO)));
                        customer.setCreditCardReferences(Collections.singletonList(CreditCardConverter.DTOToCreditCard(creditCardDTO)));

                        return customer;
                    })
                    .flatMap(customerRepository::save)
                    .map(CustomerConverter::customerToCustomerDTO);
        }

    private String generateAccountId() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
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