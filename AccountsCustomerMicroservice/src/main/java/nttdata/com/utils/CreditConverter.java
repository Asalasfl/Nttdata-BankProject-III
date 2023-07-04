package nttdata.com.utils;

import nttdata.com.dto.CreditDTO;
import nttdata.com.dto.PaymentDTO;
import nttdata.com.model.Credit;
import nttdata.com.model.Payment;

import java.util.ArrayList;
import java.util.List;

import static nttdata.com.utils.PaymentConverter.paymentToPaymentDTO;

public class CreditConverter {

    public static CreditDTO creditToDTO(Credit entity) {
        CreditDTO dto = new CreditDTO();
        dto.setIdCredit(entity.getId());
        dto.setType(entity.getType());
        dto.setAmount(entity.getAmount());
        dto.setInterestRate(entity.getInterestRate());
        dto.setRemainingAmount(entity.getRemainingAmount());

        List<PaymentDTO> paymentDTOs = new ArrayList<>();
        if (entity.getPaymentReferences() != null) {
            for (Payment payment : entity.getPaymentReferences()) {
                PaymentDTO paymentDTO = paymentToPaymentDTO(payment);
                paymentDTOs.add(paymentDTO);
            }
        }
        dto.setPayments(paymentDTOs);

        return dto;
    }

    public static Credit DTOToCredit(CreditDTO dto) {
        Credit entity = new Credit();
        entity.setId(dto.getIdCredit());
        entity.setType(dto.getType());
        entity.setAmount(dto.getAmount());
        entity.setInterestRate(dto.getInterestRate());
        entity.setRemainingAmount(dto.getRemainingAmount());


        return entity;
    }


}