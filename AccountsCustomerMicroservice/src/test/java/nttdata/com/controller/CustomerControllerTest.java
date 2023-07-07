package nttdata.com.controller;

import nttdata.com.dto.AccountDTO;
import nttdata.com.dto.CustomerDTO;
import nttdata.com.service.CustomerServiceImpl;
import nttdata.com.service.CustomerServiceMediator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class CustomerControllerTest {
    @Mock
    private CustomerServiceImpl customerService;

    @Mock
    private CustomerServiceMediator customerServiceMediator;

    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddAccountToCustomer() {
        // Datos de prueba
        String customerId = "1";
        AccountDTO accountDTO = new AccountDTO();

        // Configurar el comportamiento del servicio mock
        when(customerServiceMediator.addAccountToCustomer(eq(customerId), any())).thenReturn(Mono.empty());

        // Ejecutar el método del controlador y verificar el resultado
        Mono<Void> result = customerController.addAccountToCustomer(customerId, accountDTO);
        StepVerifier.create(result)
                .expectComplete()
                .verify();
    }

    @Test
    void testCreateCustomer() {
        // Datos de prueba
        CustomerDTO customerDTO = new CustomerDTO();
        CustomerDTO expectedCustomer = new CustomerDTO();

        // Configurar el comportamiento del servicio mock
        when(customerService.createCustomer((CustomerDTO) any())).thenReturn(Mono.just(expectedCustomer));

        // Ejecutar el método del controlador y verificar el resultado
        Mono<CustomerDTO> result = customerController.createCustomer(customerDTO);
        StepVerifier.create(result)
                .expectNext(expectedCustomer)
                .verifyComplete();
    }

    @Test
    void testGetCustomerById() {
        // Datos de prueba
        String customerId = "1";
        CustomerDTO expectedCustomer = new CustomerDTO();

        // Configurar el comportamiento del servicio mock
        when(customerService.getCustomerById(customerId)).thenReturn(Mono.just(expectedCustomer));

        // Ejecutar el método del controlador y verificar el resultado
        Mono<CustomerDTO> result = customerController.getCustomerById(customerId);
        StepVerifier.create(result)
                .expectNext(expectedCustomer)
                .verifyComplete();
    }

    @Test
    void testUpdateCustomer() {
        // Datos de prueba
        String customerId = "1";
        CustomerDTO customerDTO = new CustomerDTO();
        CustomerDTO expectedCustomer = new CustomerDTO();

        // Configurar el comportamiento del servicio mock
        when(customerService.updateCustomer(eq(customerId), any())).thenReturn(Mono.just(expectedCustomer));

        // Ejecutar el método del controlador y verificar el resultado
        Mono<CustomerDTO> result = customerController.updateCustomer(customerId, customerDTO);
        StepVerifier.create(result)
                .expectNext(expectedCustomer)
                .verifyComplete();
    }
}
