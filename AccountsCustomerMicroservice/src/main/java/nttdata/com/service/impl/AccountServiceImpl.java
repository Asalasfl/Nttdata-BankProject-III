package nttdata.com.service.impl;

import lombok.AllArgsConstructor;
import nttdata.com.dto.AccountDTO;
import nttdata.com.dto.CreditCardDTO;
import nttdata.com.dto.TransactionDTO;
import nttdata.com.model.Account;
import nttdata.com.model.CreditCard;
import nttdata.com.model.Transaction;
import nttdata.com.repository.AccountRepository;
import nttdata.com.repository.TransactionRepository;
import nttdata.com.service.AccountService;
import nttdata.com.utils.AccountConverter;
import nttdata.com.utils.CreditCardConverter;
import nttdata.com.utils.TransactionConverter;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public Mono<AccountDTO> createSavingAccount(AccountDTO accountDTO, CreditCardDTO creditCardDTO) {
        Account account = AccountConverter.accountDTOToAccount(accountDTO);

        if (accountDTO.getType().equalsIgnoreCase("saving")) {
            account.setCommissionFree(true);
            account.setLimitMovement(true);
            account.setMaxMonthlyMovements(5);
        }

        List<Transaction> transactions = new ArrayList<>();
        if (accountDTO.getTransactions() != null) {
            for (TransactionDTO transactionDTO : accountDTO.getTransactions()) {
                Transaction transaction = TransactionConverter.transactionDTOToTransaction(transactionDTO, creditCardDTO, accountDTO);
                transactions.add(transaction);
            }
        }
        account.setTransactionReferences(transactions);

        Mono<Account> saveAccountMono = accountRepository.save(account);

        Flux<Transaction> saveTransactionsFlux = Flux.fromIterable(transactions)
                .flatMap(transactionRepository::save);

        return saveAccountMono
                .thenMany(saveTransactionsFlux)
                .collectList()
                .map(savedTransactions -> {
                    account.setTransactionReferences(savedTransactions);
                    return account;
                })
                .map(AccountConverter::accountToAccountDTO);
    }
    @Override
    public Mono<AccountDTO> createFixedTermAccount(AccountDTO accountDTO, CreditCardDTO creditCardDTO) {
        Account account = AccountConverter.accountDTOToAccount(accountDTO);

        if (accountDTO.getType().equalsIgnoreCase("FixedTerm")) {
            account.setCommissionFree(false);
            account.setLimitMovement(true);
            account.setMaxMonthlyMovements(1);
        }

        List<Transaction> transactions = new ArrayList<>();
        if (accountDTO.getTransactions() != null) {
            for (TransactionDTO transactionDTO : accountDTO.getTransactions()) {
                Transaction transaction = TransactionConverter.transactionDTOToTransaction(transactionDTO, creditCardDTO, accountDTO);
                transactions.add(transaction);
            }
        }
        account.setTransactionReferences(transactions);

        Mono<Account> saveAccountMono = accountRepository.save(account);

        Flux<Transaction> saveTransactionsFlux = Flux.fromIterable(transactions)
                .flatMap(transactionRepository::save);

        return saveAccountMono
                .thenMany(saveTransactionsFlux)
                .collectList()
                .map(savedTransactions -> {
                    account.setTransactionReferences(savedTransactions);
                    return account;
                })
                .map(AccountConverter::accountToAccountDTO);
    }
    @Override
    public Mono<AccountDTO> createCurrentAccount(AccountDTO accountDTO, CreditCardDTO creditCardDTO) {
        Account account = AccountConverter.accountDTOToAccount(accountDTO);

        if (accountDTO.getType().equalsIgnoreCase("CURRENT")) {
            account.setCommissionFree(true);
            account.setLimitMovement(false);
            account.setMaxMonthlyMovements(Integer.MAX_VALUE);
        }

        List<Transaction> transactions = new ArrayList<>();
        if (accountDTO.getTransactions() != null) {
            for (TransactionDTO transactionDTO : accountDTO.getTransactions()) {
                Transaction transaction = TransactionConverter.transactionDTOToTransaction(transactionDTO, creditCardDTO, accountDTO);
                transactions.add(transaction);
            }
        }
        account.setTransactionReferences(transactions);

        Mono<Account> saveAccountMono = accountRepository.save(account);

        Flux<Transaction> saveTransactionsFlux = Flux.fromIterable(transactions)
                .flatMap(transactionRepository::save);

        return saveAccountMono
                .thenMany(saveTransactionsFlux)
                .collectList()
                .map(savedTransactions -> {
                    account.setTransactionReferences(savedTransactions);
                    return account;
                })
                .map(AccountConverter::accountToAccountDTO);
    }
    @Override
    public Mono<AccountDTO> getAccountById(String id) {
        return accountRepository.findById(id)
                .map(AccountConverter::accountToAccountDTO);
    }

    public Mono<AccountDTO> updateAccount(String id, AccountDTO accountDTO, CreditCardDTO creditCardDTO) {
        return accountRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Account not found")))
                .flatMap(account -> {
                    account.setType(accountDTO.getType());
                    account.setBalance(accountDTO.getBalance());

                    List<Transaction> transactions = accountDTO.getTransactions().stream()
                            .map(transactionDTO -> TransactionConverter.transactionDTOToTransaction(transactionDTO, creditCardDTO, accountDTO))
                            .collect(Collectors.toList());

                    account.setTransactionReferences(transactions);

                    return accountRepository.save(account)
                            .map(AccountConverter::accountToAccountDTO);
                });

    }


    @Override
    public Mono<AccountDTO> addTransaction(String accountId, TransactionDTO transactionDTO, CreditCard creditCard) {
        return accountRepository.findById(accountId)
                .flatMap(account -> {
                    account.setBalance(account.getBalance().add(transactionDTO.getAmount()));

                    List<Transaction> transactions = account.getTransactionReferences();

                    transactions.add(TransactionConverter.transactionDTOToTransaction(transactionDTO,CreditCardConverter.creditCardToDTO(creditCard),AccountConverter.accountToAccountDTO(account)));
                    account.setTransactionReferences(transactions);
                    return accountRepository.save(account)
                            .map(AccountConverter::accountToAccountDTO);
                });
    }


    @Override
    public Flux<TransactionDTO> getTransactionsByAccountId(String accountId) {
        return transactionRepository.findByAccountId(accountId)
                .map(TransactionConverter::transactionToTransactionDTO);
    }

}
