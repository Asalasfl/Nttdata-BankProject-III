package nttdata.com.controller;

import nttdata.com.dto.AccountDTO;
import nttdata.com.dto.CreditCardDTO;
import nttdata.com.dto.TransactionDTO;
import nttdata.com.model.CreditCard;
import nttdata.com.service.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class AccountControllerTest {
    @Mock
    private AccountServiceImpl accountService;

    @InjectMocks
    private AccountController accountController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAccountById() {
        // Datos de prueba
        String accountId = "1";
        AccountDTO expectedAccount = new AccountDTO();
        expectedAccount.setIdAccount(accountId);

        // Configurar el comportamiento del servicio mock
        when(accountService.getAccountById(accountId)).thenReturn(Mono.just(expectedAccount));

        // Ejecutar el método del controlador y verificar el resultado
        Mono<AccountDTO> result = accountController.getAccountById(accountId);
        StepVerifier.create(result)
                .expectNext(expectedAccount)
                .verifyComplete();
    }

    @Test
    void testAddTransaction() {
        // Datos de prueba
        String accountId = "1";
        TransactionDTO transactionDTO = new TransactionDTO();
        CreditCard creditCard = new CreditCard();
        AccountDTO expectedAccount = new AccountDTO();
        expectedAccount.setIdAccount(accountId);

        // Configurar el comportamiento del servicio mock
        when(accountService.addTransaction(eq(accountId), any(), any())).thenReturn(Mono.just(expectedAccount));

        // Ejecutar el método del controlador y verificar el resultado
        Mono<AccountDTO> result = accountController.addTransaction(accountId, transactionDTO, creditCard);
        StepVerifier.create(result)
                .expectNext(expectedAccount)
                .verifyComplete();
    }

    @Test
    void testUpdateAccount() {
        // Datos de prueba
        String accountId = "1";
        AccountDTO accountDTO = new AccountDTO();
        CreditCardDTO creditCardDTO = new CreditCardDTO();
        AccountDTO expectedAccount = new AccountDTO();
        expectedAccount.setIdAccount(accountId);

        // Configurar el comportamiento del servicio mock
        when(accountService.updateAccount(eq(accountId), any(), any())).thenReturn(Mono.just(expectedAccount));

        // Ejecutar el método del controlador y verificar el resultado
        Mono<AccountDTO> result = accountController.updateAccount(accountId, accountDTO, creditCardDTO);
        StepVerifier.create(result)
                .expectNext(expectedAccount)
                .verifyComplete();
    }

    @Test
    void testGetTransactionsByAccountId() {
        // Datos de prueba
        String accountId = "1";
        List<TransactionDTO> expectedTransactions = new ArrayList<>();

        // Configurar el comportamiento del servicio mock
        when(accountService.getTransactionsByAccountId(accountId)).thenReturn(Flux.fromIterable(expectedTransactions));

        // Ejecutar el método del controlador y verificar el resultado
        Flux<TransactionDTO> result = accountController.getTransactionsByAccountId(accountId);
        StepVerifier.create(result)
                .expectNextCount(expectedTransactions.size())
                .verifyComplete();
    }
}
