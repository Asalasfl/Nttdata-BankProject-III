package nttdata.com.utils;

import nttdata.com.dto.AccountDTO;
import nttdata.com.dto.TransactionDTO;
import nttdata.com.model.Account;
import nttdata.com.model.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static nttdata.com.utils.TransactionConverter.transactionToTransactionDTO;

public class AccountConverter {

    public static AccountDTO accountToAccountDTO(Account entity) {
        AccountDTO dto = new AccountDTO();
        dto.setIdAccount(entity.getId());
        dto.setType(entity.getType());
        dto.setBalance(entity.getBalance());
        dto.setCommissionFree(entity.isCommissionFree());
        dto.setLimitMovement(entity.isLimitMovement());
        dto.setMaxMonthlyMovements(entity.getMaxMonthlyMovements());
        dto.setMinimumOpeningAmount(entity.getGetMinimumOpeningAmount());
        List<TransactionDTO> transactionDTOs = new ArrayList<>();
        if (entity.getTransactionReferences() != null) {
            for (Transaction transaction : entity.getTransactionReferences()) {
                TransactionDTO transactionDTO = transactionToTransactionDTO(transaction);
                transactionDTOs.add(transactionDTO);
            }
        }
        dto.setTransactions(transactionDTOs);
        return dto;
    }

    public static Account accountDTOToAccount(AccountDTO dto) {

                return Optional.ofNullable(dto)
                        .map(accountDto -> {
                            Account entity = new Account();
                            entity.setId(accountDto.getIdAccount());
                            entity.setType(accountDto.getType());
                            entity.setBalance(accountDto.getBalance());

                            String accountType = accountDto.getType().toLowerCase();

                            switch (accountType) {
                                case "saving":
                                    entity.setCommissionFree(true);
                                    entity.setLimitMovement(true);
                                    entity.setMaxMonthlyMovements(5);
                                    break;
                                case "fixedterm":
                                    entity.setCommissionFree(false);
                                    entity.setLimitMovement(true);
                                    entity.setMaxMonthlyMovements(1);
                                    break;
                                case "current":
                                    entity.setCommissionFree(true);
                                    entity.setLimitMovement(false);
                                    entity.setMaxMonthlyMovements(Integer.MAX_VALUE);
                                    break;
                                default:
                                    throw new IllegalArgumentException("Type account is not correct (saving, fixedterm, current)");
                            }

                            return entity;
                        })
                        .orElseThrow(() -> new IllegalArgumentException("AccountDTO cannot be null"));
            }
}