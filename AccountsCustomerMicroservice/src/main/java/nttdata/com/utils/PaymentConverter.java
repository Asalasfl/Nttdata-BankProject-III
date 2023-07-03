package nttdata.com.utils;

import nttdata.com.dto.CreditDTO;
import nttdata.com.dto.PaymentDTO;
import nttdata.com.model.Credit;
import nttdata.com.model.Payment;

public class PaymentConverter {

    /*
    public static List<PaymentDTO> paymentListToPaymentListDTO(List<Payment> entity) {
        return entity.stream()
                .map(payment -> {
                    PaymentDTO dto = new PaymentDTO();
                    dto.setIdPayment(payment.getId());
                    dto.setAmount(payment.getAmount());
                    dto.setTimestamp(payment.getTimestamp());
                    return dto;
                })
                .collect(Collectors.toList());
    }
    */
    public static PaymentDTO paymentToPaymentDTO(Payment entity) {
        PaymentDTO dto = new PaymentDTO();
        dto.setIdPayment(entity.getId());
        dto.setAmount(entity.getAmount());
        dto.setIdCredit(entity.getCreditId().getId());
        dto.setTimestamp(entity.getTimestamp());

        return dto;
    }
    public static Payment paymentDTOToPayment(PaymentDTO dto, CreditDTO creditDTO) {
        Payment entity = new Payment();
        entity.setId(dto.getIdPayment());
        entity.setAmount(dto.getAmount());
        entity.setTimestamp(dto.getTimestamp());
        Credit creditEntity = CreditConverter.DTOToCredit(creditDTO);
        entity.setCreditId(creditEntity);
        return entity;
    }
}