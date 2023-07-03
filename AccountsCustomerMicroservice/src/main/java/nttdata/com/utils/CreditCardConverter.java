package nttdata.com.utils;

import nttdata.com.dto.CreditCardDTO;
import nttdata.com.dto.TransactionDTO;
import nttdata.com.model.CreditCard;
import nttdata.com.model.Transaction;

import java.util.ArrayList;
import java.util.List;

import static nttdata.com.utils.TransactionConverter.transactionToTransactionDTO;


public class    CreditCardConverter {

    public static CreditCardDTO creditCardToDTO(CreditCard entity) {
        CreditCardDTO dto = new CreditCardDTO();
        dto.setCreditCardId(entity.getId());
        dto.setCreditLimit(entity.getCreditLimit());
        dto.setCurrentBalance(entity.getCurrentBalance());

        List<TransactionDTO> transactionDTOs = new ArrayList<>();
        if (entity.getTransactionReferences()!= null) {
            for (Transaction transaction : entity.getTransactionReferences()) {
                TransactionDTO transactionDTO = transactionToTransactionDTO(transaction);
                transactionDTOs.add(transactionDTO);
            }
        }
        dto.setTransactions(transactionDTOs);
        return dto;
    }

    public static CreditCard DTOToCreditCard(CreditCardDTO dto) {
        CreditCard entity = new CreditCard();
        entity.setId(dto.getCreditCardId());
        entity.setCreditLimit(dto.getCreditLimit());
        entity.setCurrentBalance(dto.getCurrentBalance());


        return entity;
    }


}