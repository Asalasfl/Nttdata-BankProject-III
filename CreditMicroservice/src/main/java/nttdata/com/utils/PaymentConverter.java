package nttdata.com.utils;

import nttdata.com.dto.CreditDTO;
import nttdata.com.dto.PaymentDTO;
import nttdata.com.model.Credit;
import nttdata.com.model.Payment;

public class PaymentConverter {

    public static PaymentDTO paymentToPaymentDTO(Payment entity, Credit credit) {
        PaymentDTO dto = new PaymentDTO();
        dto.setIdPayment(entity.getId());
        dto.setAmount(entity.getAmount());
        dto.setIdCredit(entity.getCreditId());
        dto.setTimestamp(entity.getTimestamp());

        return dto;
    }
    public static Payment paymentDTOToPayment(PaymentDTO dto, CreditDTO creditDTO) {
        Payment entity = new Payment();
        entity.setId(dto.getIdPayment());
        entity.setAmount(dto.getAmount());
        entity.setTimestamp(dto.getTimestamp());
        Credit creditEntity = CreditConverter.DTOToCredit(creditDTO);
        entity.setCreditId(dto.getIdCredit());
        return entity;
    }
}