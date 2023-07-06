package nttdata.com.service;

import nttdata.com.dto.CreditCardDTO;
import nttdata.com.dto.TransactionDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreditCardService {

    Mono<CreditCardDTO> createCreditCard(CreditCardDTO creditDTO);
    Mono<CreditCardDTO> findByCreditCardId(String creditCardId);

    Mono<CreditCardDTO> addTransaction(String creditCardId, TransactionDTO transactionDTO);

    Flux<TransactionDTO> getTransactionsByCreditCardId(String creditCardId);
}