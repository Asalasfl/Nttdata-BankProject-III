package nttdata.com.utils;

import nttdata.com.dto.CreditDTO;
import nttdata.com.model.Credit;
import nttdata.com.model.Payment;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreditConverterTest {

    @Test
    public void testCreditToDTO() {
        Credit credit = new Credit();
        credit.setId("1");
        credit.setType("personal");
        credit.setAmount(BigDecimal.valueOf(1000));
        credit.setInterestRate(BigDecimal.valueOf(0.05));
        credit.setRemainingAmount(BigDecimal.valueOf(500));
        // Setear valores en credit

        Payment payment = new Payment();
        payment.setId("1");
        payment.setAmount(BigDecimal.valueOf(100));
        payment.setCreditId("12");
        payment.setTimestamp(LocalDateTime.now());

        List<Payment> paymentReferences = new ArrayList<>();
        paymentReferences.add(payment);
        credit.setPaymentReferences(paymentReferences);

        CreditDTO dto = CreditConverter.creditToDTO(credit);

        assertEquals("1", dto.getIdCredit());
        assertEquals("personal",dto.getType());
        assertEquals(BigDecimal.valueOf(1000), dto.getAmount());
        assertEquals(BigDecimal.valueOf(0.05), dto.getInterestRate());
        assertEquals(BigDecimal.valueOf(500), dto.getRemainingAmount());
        // Verificar valores en dto
    }

    @Test
    public void testDTOToCredit() {
        CreditDTO dto = new CreditDTO();
        dto.setIdCredit("1");
        dto.setType("personal");
        dto.setAmount(BigDecimal.valueOf(1000));
        dto.setInterestRate(BigDecimal.valueOf(0.05));
        dto.setRemainingAmount(BigDecimal.valueOf(500));
        // Setear valores en dto

        Credit entity = CreditConverter.DTOToCredit(dto);

        assertEquals("1", entity.getId());
        assertEquals(BigDecimal.valueOf(1000), entity.getAmount());
        assertEquals("Personal", entity.getType());
        assertEquals(BigDecimal.valueOf(0.05), entity.getInterestRate());
        assertEquals(BigDecimal.valueOf(500), entity.getRemainingAmount());
        // Verificar valores en entity
    }
}
