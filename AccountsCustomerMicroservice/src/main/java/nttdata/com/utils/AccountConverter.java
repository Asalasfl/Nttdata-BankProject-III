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

        if (dto.getType().equalsIgnoreCase("saving")) {
            entity.setCommissionFree(true);
            entity.setLimitMovement(true);
            entity.setMaxMonthlyMovements(5);
        }
        else if (dto.getType().equalsIgnoreCase("FixedTerm")) {
            entity.setCommissionFree(false);
            entity.setLimitMovement(true);
            entity.setMaxMonthlyMovements(1);
        }
        else if (dto.getType().equalsIgnoreCase("CURRENT")) {
            entity.setCommissionFree(true);
            entity.setLimitMovement(false);
            entity.setMaxMonthlyMovements(Integer.MAX_VALUE);
        }
        else {
            throw new IllegalArgumentException("Type account is not correct(saving,current,fixedterm");
        }

        return entity;
    }
}