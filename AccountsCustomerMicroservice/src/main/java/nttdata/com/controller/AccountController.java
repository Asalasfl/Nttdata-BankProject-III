package nttdata.com.controller;

import lombok.AllArgsConstructor;
import nttdata.com.dto.AccountDTO;
import nttdata.com.dto.CreditCardDTO;
import nttdata.com.dto.TransactionDTO;
import nttdata.com.model.CreditCard;
import nttdata.com.service.impl.AccountServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountServiceImpl accountServiceImpl;

    @GetMapping(value= "/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<AccountDTO> getAccountById(@PathVariable String id) {
        return accountServiceImpl.getAccountById(id);
    }

    @PostMapping(value = "/{accountId}/transactions", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<AccountDTO> addTransaction(@PathVariable String accountId,
                                           @RequestBody TransactionDTO transactionDTO,@RequestBody CreditCard creditCard) {
        return accountServiceImpl.addTransaction(accountId, transactionDTO,creditCard);
    }

    @PutMapping(value = "/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<AccountDTO> updateAccount(@PathVariable String id, @RequestBody AccountDTO accountDTO,@RequestBody CreditCardDTO creditCardDTO) {
        return accountServiceImpl.updateAccount(id, accountDTO, creditCardDTO);
    }
    @GetMapping(value = "/{accountId}/transactions", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<TransactionDTO> getTransactionsByAccountId(@PathVariable String accountId) {
        return accountServiceImpl.getTransactionsByAccountId(accountId);
    }
}