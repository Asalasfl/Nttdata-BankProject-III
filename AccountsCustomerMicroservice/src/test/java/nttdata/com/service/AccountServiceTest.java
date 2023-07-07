package  nttdata.com.service;

import nttdata.com.dto.AccountDTO;
import nttdata.com.dto.CreditCardDTO;
import nttdata.com.dto.TransactionDTO;
import nttdata.com.model.Account;
import nttdata.com.model.CreditCard;
import nttdata.com.model.Transaction;
import nttdata.com.repository.AccountRepository;
import nttdata.com.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAccountById() {
        // Arrange
        Account account = new Account();
        account.setId("accountId");
        account.setType("saving");
        account.setBalance(BigDecimal.valueOf(1000));

        when(accountRepository.findById(any(String.class))).thenReturn(Mono.just(account));

        // Act & Assert
        accountService.getAccountById("accountId")
                .as(StepVerifier::create)
                .expectNextMatches(accountDTO -> {
                    // Add assertions for expected values in the AccountDTO object
                    return accountDTO.getIdAccount().equals(account.getId())
                            && accountDTO.getType().equals(account.getType())
                            && accountDTO.getBalance().equals(account.getBalance());
                })
                .verifyComplete();
    }

    @Test
    public void testUpdateAccount() {
        // Arrange
        Account account = new Account();
        account.setId("accountId");
        account.setType("saving");
        account.setBalance(BigDecimal.valueOf(1000));

        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setIdAccount("accountId");
        accountDTO.setType("saving");
        accountDTO.setBalance(BigDecimal.valueOf(2000));

        CreditCardDTO creditCardDTO = new CreditCardDTO();
        creditCardDTO.setCreditCardId("creditCardId");

        when(accountRepository.findById(any(String.class))).thenReturn(Mono.just(account));
        when(accountRepository.save(any(Account.class))).thenReturn(Mono.just(account));

        // Act & Assert
        accountService.updateAccount("accountId", accountDTO, creditCardDTO)
                .as(StepVerifier::create)
                .expectNextMatches(updatedAccountDTO -> {
                    // Add assertions for expected values in the updated AccountDTO object
                    return updatedAccountDTO.getIdAccount().equals(accountDTO.getIdAccount())
                            && updatedAccountDTO.getType().equals(accountDTO.getType())
                            && updatedAccountDTO.getBalance().equals(accountDTO.getBalance());
                })
                .verifyComplete();
    }

    @Test
    public void testAddTransaction() {
        // Arrange
        Account account = new Account();
        account.setId("accountId");
        account.setType("saving");
        account.setBalance(BigDecimal.valueOf(1000));

        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setTransactionId("transactionId");
        transactionDTO.setIdAccount("accountId");
        transactionDTO.setType("deposit");
        transactionDTO.setAmount(BigDecimal.valueOf(500));
        transactionDTO.setTimestamp(LocalDateTime.now());

        CreditCard creditCard = new CreditCard();
        creditCard.setId("creditCardId");

        when(accountRepository.findById(any(String.class))).thenReturn(Mono.just(account));
        when(accountRepository.save(any(Account.class))).thenReturn(Mono.just(account));

        // Act & Assert
        accountService.addTransaction("accountId", transactionDTO, creditCard)
                .as(StepVerifier::create)
                .expectNextMatches(updatedAccountDTO -> {
                    // Add assertions for expected values in the updated AccountDTO object
                    return updatedAccountDTO.getIdAccount().equals(account.getId())
                            && updatedAccountDTO.getType().equals(account.getType())
                            && updatedAccountDTO.getBalance().equals(account.getBalance().add(transactionDTO.getAmount()));
                })
                .verifyComplete();
    }

    @Test
    public void testGetTransactionsByAccountId() {
        // Arrange
        Transaction transaction1 = new Transaction();
        transaction1.setId("transactionId1");
        transaction1.setAccountId("accountId");
        transaction1.setType("deposit");
        transaction1.setAmount(BigDecimal.valueOf(500));
        transaction1.setTimestamp(LocalDateTime.now());

        Transaction transaction2 = new Transaction();
        transaction2.setId("transactionId2");
        transaction2.setAccountId("accountId");
        transaction2.setType("withdrawal");
        transaction2.setAmount(BigDecimal.valueOf(200));
        transaction2.setTimestamp(LocalDateTime.now());

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction1);
        transactions.add(transaction2);

        when(transactionRepository.findByAccountId(any(String.class))).thenReturn(Flux.fromIterable(transactions));

        // Act & Assert
        accountService.getTransactionsByAccountId("accountId")
                .as(StepVerifier::create)
                .expectNextCount(2)
                .verifyComplete();
    }
}
