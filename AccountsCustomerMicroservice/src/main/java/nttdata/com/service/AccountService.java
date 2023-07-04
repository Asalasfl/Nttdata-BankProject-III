package nttdata.com.service;

import nttdata.com.dto.AccountDTO;
import nttdata.com.dto.CreditCardDTO;
import nttdata.com.dto.TransactionDTO;
import nttdata.com.model.CreditCard;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountService {
    Mono<AccountDTO> getAccountById(String id);
    Mono<AccountDTO> updateAccount(String id, AccountDTO accountDTO,CreditCardDTO creditCardDTO);
    Mono<AccountDTO> addTransaction(String accountId, TransactionDTO transactionDTO, CreditCard creditCard);

    Flux<TransactionDTO> getTransactionsByAccountId(String accountId);
}
