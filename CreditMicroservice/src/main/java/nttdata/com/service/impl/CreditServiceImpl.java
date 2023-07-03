package nttdata.com.service.impl;

import lombok.AllArgsConstructor;
import nttdata.com.dto.CreditDTO;
import nttdata.com.dto.PaymentDTO;
import nttdata.com.model.Credit;
import nttdata.com.model.Payment;
import nttdata.com.repository.CreditRepository;
import nttdata.com.repository.PaymentRepository;
import nttdata.com.service.CreditService;
import nttdata.com.utils.CreditConverter;
import nttdata.com.utils.PaymentConverter;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class CreditServiceImpl implements CreditService {
    private final CreditRepository creditRepository;
    private final PaymentRepository paymentRepository;

    @Override
    public Mono<CreditDTO> createCredit(CreditDTO creditDTO) {
        Credit credit = CreditConverter.DTOToCredit(creditDTO);

        List<Payment> payments = new ArrayList<>();
        if (creditDTO.getPayments() != null) {
            for (PaymentDTO paymentDTO : creditDTO.getPayments()) {
                Payment payment = PaymentConverter.paymentDTOToPayment(paymentDTO, creditDTO);
                payments.add(payment);
            }
        }
        credit.setPaymentReferences(payments);

        Mono<Credit> saveCreditMono = creditRepository.save(credit);

        Flux<Payment> savePaymentsFlux = Flux.fromIterable(payments)
                .flatMap(paymentRepository::save);

        return saveCreditMono
                .thenMany(savePaymentsFlux)
                .collectList()
                .map(savedPayments -> {
                    credit.setPaymentReferences(savedPayments);
                    return credit;
                })
                .map(CreditConverter::creditToDTO);
    }



    @Override
    public Mono<CreditDTO> findByCreditId(String creditId) {
        return creditRepository.findById(creditId)
                .map(CreditConverter::creditToDTO)
                .switchIfEmpty(Mono.just(new CreditDTO("El crédito no existe.")));
    }

    @Override
    public Mono<CreditDTO> addPayment(String creditId, PaymentDTO paymentDTO) {
        return creditRepository.findById(creditId)
                .flatMap(credit -> {
                    credit.setRemainingAmount(credit.getRemainingAmount().subtract(paymentDTO.getAmount()));
                    Payment payment = PaymentConverter.paymentDTOToPayment(paymentDTO, CreditConverter.creditToDTO(credit));

                    List<Payment> paymentReferences = credit.getPaymentReferences();
                    paymentReferences.add(payment);
                    credit.setPaymentReferences(paymentReferences);

                    return creditRepository.save(credit)
                            .map(CreditConverter::creditToDTO);
                });
    }


    @Override
    public Flux<PaymentDTO> getPaymentsByCreditId(String creditId) {
        return (Flux<PaymentDTO>) paymentRepository.findByCreditId(creditId)
                .map(PaymentConverter::paymentToPaymentDTO);
    }

}
