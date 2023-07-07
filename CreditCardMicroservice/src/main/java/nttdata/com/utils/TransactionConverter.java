package nttdata.com.utils;

import nttdata.com.dto.CreditCardDTO;
import nttdata.com.dto.TransactionDTO;
import nttdata.com.model.Credit;
import nttdata.com.model.CreditCard;
import nttdata.com.model.Transaction;

public class TransactionConverter {

    public static TransactionDTO transactionToTransactionDTO(Transaction entity) {
        TransactionDTO dto = new TransactionDTO();
        dto.setTransactionId(entity.getId());
        dto.setType(entity.getType());
        dto.setAmount(entity.getAmount());
        dto.setIdCreditCard(entity.getCreditCardId());
        dto.setTimestamp(entity.getTimestamp());

        return dto;
    }
    public static Transaction transactionDTOToTransaction(TransactionDTO dto) {
        Transaction entity = new Transaction();
        entity.setId(dto.getTransactionId());
        entity.setType(dto.getType());
        entity.setAmount(dto.getAmount());
        entity.setTimestamp(dto.getTimestamp());
        entity.setCreditCardId(dto.getIdCreditCard());

        return entity;
    }
}