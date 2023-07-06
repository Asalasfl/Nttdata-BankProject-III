package nttdata.com.utils;

import nttdata.com.dto.CreditDTO;
import nttdata.com.dto.PaymentDTO;
import nttdata.com.model.Credit;
import nttdata.com.model.Payment;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PaymentConverterTest {

    @Test
    public void testPaymentToPaymentDTO() {
        Payment payment = new Payment();
        payment.setId("1");
        payment.setAmount(BigDecimal.valueOf(100));
        payment.setTimestamp(LocalDateTime.now());

        Credit credit = mock(Credit.class);
        when(credit.getId()).thenReturn("1");

        payment.setCreditId("12");

        PaymentDTO dto = PaymentConverter.paymentToPaymentDTO(payment,credit);

        assertEquals("1", dto.getIdPayment());
        assertEquals(BigDecimal.valueOf(100), dto.getAmount());
        assertEquals("1", dto.getIdCredit());
        // Verificar valores en dto
    }

    @Test
    public void testPaymentDTOToPayment() {
        PaymentDTO dto = new PaymentDTO();
        dto.setIdPayment("1");
        dto.setAmount(BigDecimal.valueOf(100));
        dto.setTimestamp(LocalDateTime.now());

        CreditDTO creditDTO = new CreditDTO();
        creditDTO.setIdCredit("1");

        Payment entity = PaymentConverter.paymentDTOToPayment(dto, creditDTO);

        assertEquals("1", entity.getId());
        assertEquals(BigDecimal.valueOf(100), entity.getAmount());
        assertEquals("1", entity.getCreditId());
        // Verificar valores en entity
    }
}