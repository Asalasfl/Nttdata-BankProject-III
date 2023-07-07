package  nttdata.com.service;
import nttdata.com.dto.AccountDTO;
import nttdata.com.dto.CustomerDTO;
import nttdata.com.model.Account;
import nttdata.com.model.Customer;
import nttdata.com.repository.AccountRepository;
import nttdata.com.repository.CustomerRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateCustomer() {
        // Arrange
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setIdCustomer("customerId");
        customerDTO.setName("John Doe");

        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setIdAccount("accountId");
        accountDTO.setType("saving");
        accountDTO.setBalance(BigDecimal.valueOf(1000));

        List<Account> accounts = new ArrayList<>();
        Account account = new Account();
        account.setId(accountDTO.getIdAccount());
        account.setType(accountDTO.getType());
        account.setBalance(accountDTO.getBalance());
        accounts.add(account);

        when(customerRepository.save(any(Customer.class))).thenReturn(Mono.just(new Customer()));
       // when(accountRepository.saveAll(any())).thenReturn(Flux.fromIterable(accounts));

        // Act & Assert
        customerService.createCustomer(customerDTO)
                .block();
        // Add assertions for the expected behavior
    }

    @Test
    public void testUpdateCustomer() {
        // Arrange
        String customerId = "customerId";

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setIdCustomer(customerId);
        customerDTO.setName("John Doe");

        Customer customer = new Customer();
        customer.setId(customerId);
        // Set up customer properties

        when(customerRepository.findById(anyString())).thenReturn(Mono.just(customer));
        when(customerRepository.save(any(Customer.class))).thenReturn(Mono.just(customer));

        // Act & Assert
        customerService.updateCustomer(customerId, customerDTO)
                .block();
        // Add assertions for the expected behavior
    }

    @Test
    public void testGetCustomerById() {
        // Arrange
        String customerId = "customerId";

        Customer customer = new Customer();
        customer.setId(customerId);
        // Set up customer properties

        when(customerRepository.findById(anyString())).thenReturn(Mono.just(customer));

        // Act & Assert
        customerService.getCustomerById(customerId)
                .block();
        // Add assertions for the expected behavior
    }
}
