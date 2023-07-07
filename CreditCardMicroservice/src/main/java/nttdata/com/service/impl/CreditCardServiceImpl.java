package nttdata.com.service.impl;

import lombok.AllArgsConstructor;
import nttdata.com.dto.CreditCardDTO;
import nttdata.com.dto.TransactionDTO;
import nttdata.com.model.Credit;
import nttdata.com.model.CreditCard;
import nttdata.com.model.Payment;
import nttdata.com.model.Transaction;
import nttdata.com.repository.CreditCardRepository;
import nttdata.com.repository.TransactionRepository;
import nttdata.com.service.CreditCardService;
import nttdata.com.utils.CreditCardConverter;
import nttdata.com.utils.TransactionConverter;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@AllArgsConstructor
@Service
public class CreditCardServiceImpl implements CreditCardService {
    
    private final CreditCardRepository creditCardRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public Mono<CreditCardDTO> createCreditCard(CreditCardDTO creditCardDTO) {
        CreditCard creditCard = CreditCardConverter.DTOToCreditCard(creditCardDTO);

        List<Transaction> transactions = new ArrayList<>();
        if (creditCardDTO.getTransactions() != null) {
            for (TransactionDTO transactionDTO : creditCardDTO.getTransactions()) {
                Transaction transaction = TransactionConverter.transactionDTOToTransaction(transactionDTO);
                transactions.add(transaction);
            }
        }
        creditCard.setTransactionReferences(transactions);

        if (creditCard.getId() == null) {
            // Asignar un ID válido a la tarjeta de crédito
            creditCard.setId(generateCreditCardId());
        }

        Mono<CreditCard> saveCreditCardMono = creditCardRepository.save(creditCard);

        Flux<Transaction> saveTransactionsFlux = Flux.fromIterable(transactions)
                .flatMap(transactionRepository::save);

        return saveCreditCardMono
                .thenMany(saveTransactionsFlux)
                .collectList()
                .map(savedTransactions -> {
                    creditCard.setTransactionReferences(savedTransactions);
                    return creditCard;
                })
                .map(CreditCardConverter::creditCardToDTO);
    }
    private String generateCreditCardId() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
    @Override
    public Mono<CreditCardDTO> findByCreditCardId(String creditCardId) {
        return creditCardRepository.findById(creditCardId)
                .map(CreditCardConverter::creditCardToDTO)
                .switchIfEmpty(Mono.just(new CreditCardDTO("La tarjeta de crédito no existe.")));
    }
    @Override
    public Mono<CreditCardDTO> addTransaction(String creditCardId, TransactionDTO transactionDTO) {
        return creditCardRepository.findById(creditCardId)
                .flatMap(creditCard -> {
                    creditCard.setCurrentBalance(creditCard.getCurrentBalance().add(transactionDTO.getAmount()));
                    Transaction transaction = TransactionConverter.transactionDTOToTransaction(transactionDTO);

                    List<Transaction> transactionReferences = creditCard.getTransactionReferences();
                    transactionReferences.add(transaction);
                    creditCard.setTransactionReferences(transactionReferences);

                    return creditCardRepository.save(creditCard)
                            .map(CreditCardConverter::creditCardToDTO);
                });
    }

    @Override
    public Flux<TransactionDTO> getTransactionsByCreditCardId(String creditCardId) {
        return transactionRepository.findByCreditCardId(creditCardId)
                .map(TransactionConverter::transactionToTransactionDTO);
    }
}
