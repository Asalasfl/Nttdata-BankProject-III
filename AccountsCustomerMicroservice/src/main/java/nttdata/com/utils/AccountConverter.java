package nttdata.com.utils;

import nttdata.com.dto.AccountDTO;
import nttdata.com.dto.TransactionDTO;
import nttdata.com.model.Account;
import nttdata.com.model.Transaction;

import java.util.ArrayList;
import java.util.List;

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

        if (dto == null) {
            throw new IllegalArgumentException("AccountDTO cannot be null");
        }
        Account entity = new Account();
        entity.setId(dto.getIdAccount());
        entity.setType(dto.getType());
        entity.setBalance(dto.getBalance());
        entity.setCommissionFree(dto.isCommissionFree());
        entity.setLimitMovement(dto.isLimitMovement());
        entity.setMaxMonthlyMovements(dto.getMaxMonthlyMovements());

        return entity;
    }
}