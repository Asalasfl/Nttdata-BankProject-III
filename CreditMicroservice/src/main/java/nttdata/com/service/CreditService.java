package nttdata.com.service;

import nttdata.com.dto.CreditDTO;
import nttdata.com.dto.PaymentDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreditService {

    Mono<CreditDTO> findByCreditId(String customerId);
    Mono<CreditDTO> createCredit(CreditDTO creditDTO);

    Mono<CreditDTO> addPayment(String creditId, PaymentDTO paymentDTO);

    Flux<PaymentDTO> getPaymentsByCreditId(String creditId);
}
