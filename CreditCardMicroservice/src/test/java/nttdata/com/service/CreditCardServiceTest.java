package nttdata.com.service;

import nttdata.com.dto.CreditCardDTO;
import nttdata.com.dto.TransactionDTO;
import nttdata.com.model.CreditCard;
import nttdata.com.model.Transaction;
import nttdata.com.repository.CreditCardRepository;
import nttdata.com.repository.TransactionRepository;
import nttdata.com.service.impl.CreditCardServiceImpl;
import nttdata.com.utils.CreditCardConverter;
import nttdata.com.utils.TransactionConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class CreditCardServiceTest {

    @Mock
    private CreditCardRepository creditCardRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private CreditCardServiceImpl creditCardService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateCreditCard() {
        // Arrange
        CreditCardDTO creditCardDTO = new CreditCardDTO();
        creditCardDTO.setCreditCardId("creditCardId");
        creditCardDTO.setCreditLimit(BigDecimal.valueOf(1000));

        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setTransactionId("transactionId");
        transactionDTO.setAmount(BigDecimal.valueOf(100));

        List<Transaction> transactions = new ArrayList<>();
        Transaction transaction = TransactionConverter.transactionDTOToTransaction(transactionDTO);
        transactions.add(transaction);

        when(creditCardRepository.save(any(CreditCard.class))).thenReturn(Mono.just(new CreditCard()));
        //when(transactionRepository.saveAll(any())).thenReturn(Flux.fromIterable(transactions));

        // Act & Assert
        creditCardService.createCreditCard(creditCardDTO)
                .as(StepVerifier::create)
                .expectNextMatches(createdCreditCard -> {
                    // Add assertions for the expected values in the created CreditCardDTO object
                    return true;
                })
                .verifyComplete();
    }

    @Test
    public void testFindByCreditCardId() {
        // Arrange
        String creditCardId = "creditCardId";

        CreditCard creditCard = new CreditCard();
        creditCard.setId(creditCardId);

        when(creditCardRepository.findById(anyString())).thenReturn(Mono.just(creditCard));

        // Act & Assert
        creditCardService.findByCreditCardId(creditCardId)
                .as(StepVerifier::create)
                .expectNextMatches(creditCardDTO -> {
                    // Add assertions for the expected values in the retrieved CreditCardDTO object
                    return true;
                })
                .verifyComplete();
    }

    @Test
    public void testAddTransaction() {
        // Arrange
        String creditCardId = "creditCardId";

        CreditCard creditCard = new CreditCard();
        creditCard.setId(creditCardId);
        creditCard.setCurrentBalance(BigDecimal.valueOf(500));

        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setTransactionId("transactionId");
        transactionDTO.setAmount(BigDecimal.valueOf(100));

        when(creditCardRepository.findById(anyString())).thenReturn(Mono.just(creditCard));
        when(creditCardRepository.save(any(CreditCard.class))).thenReturn(Mono.just(creditCard));

        // Act & Assert
        creditCardService.addTransaction(creditCardId, transactionDTO)
                .as(StepVerifier::create)
                .expectNextMatches(updatedCreditCardDTO -> {
                    // Add assertions for the expected values in the updated CreditCardDTO object
                    return true;
                })
                .verifyComplete();
    }

    @Test
    public void testGetTransactionsByCreditCardId() {
        // Arrange
        String creditCardId = "creditCardId";

        Transaction transaction1 = new Transaction();
        transaction1.setId(UUID.randomUUID().toString());

        Transaction transaction2 = new Transaction();
        transaction2.setId(UUID.randomUUID().toString());

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction1);
        transactions.add(transaction2);

        when(transactionRepository.findByCreditCardId(anyString())).thenReturn(Flux.fromIterable(transactions));

        // Act & Assert
        creditCardService.getTransactionsByCreditCardId(creditCardId)
                .as(StepVerifier::create)
                .expectNextCount(2)
                .verifyComplete();
    }
}
