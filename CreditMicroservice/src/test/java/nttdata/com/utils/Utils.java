package nttdata.com.utils;

import nttdata.com.model.Credit;
import nttdata.com.model.Payment;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static Credit generateCredit() {
        Payment payment = Payment.builder()
                .id("1212")
                .creditId("12345")
                .amount(BigDecimal.valueOf(123.000))
                .timestamp(LocalDateTime.now())
                .build();
        Payment payment2 = Payment.builder()
                .id("1212")
                .creditId("12345")
                .amount(BigDecimal.valueOf(123.000))
                .timestamp(LocalDateTime.now())
                .build();
        List<Payment> listPayments = null;
        Credit credit1 = Credit.builder()
                .id("12")
                .type("Personal")
                .amount(BigDecimal.valueOf(123.000))
                .interestRate(BigDecimal.valueOf(0.5))
                .remainingAmount(BigDecimal.valueOf(10.0))
                .paymentReferences(listPayments)
                .build();

        listPayments = new ArrayList<>();
        listPayments.add(payment);
        listPayments.add(payment2);
        return credit1;
    }
}
