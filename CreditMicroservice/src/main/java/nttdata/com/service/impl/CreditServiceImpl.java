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
import java.util.stream.Collectors;

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

                    Payment payment = new Payment();
                    payment.setId(paymentDTO.getIdPayment());
                    payment.setAmount(paymentDTO.getAmount());
                    payment.setTimestamp(paymentDTO.getTimestamp());
                    payment.setCreditId(paymentDTO.getIdCredit());

                    credit.getPaymentReferences().add(payment);

                    return creditRepository.save(credit)
                            .map(savedCredit -> {
                                CreditDTO creditDTO = new CreditDTO();
                                creditDTO.setIdCredit(savedCredit.getId());
                                creditDTO.setType(savedCredit.getType());
                                creditDTO.setAmount(savedCredit.getAmount());
                                creditDTO.setInterestRate(savedCredit.getInterestRate());
                                creditDTO.setRemainingAmount(savedCredit.getRemainingAmount());
                                creditDTO.setPayments(savedCredit.getPaymentReferences().stream()
                                        .map(payment1 -> PaymentConverter.paymentToPaymentDTO(payment, savedCredit)) // Pasar savedCredit como segundo argumento
                                        .collect(Collectors.toList()));
                                return creditDTO;
                            });
                });
    }


    @Override
    public Flux<PaymentDTO> getPaymentsByCreditId(String creditId) {
        return paymentRepository.findByCreditId(creditId)
                .flatMap(payment -> creditRepository.findById(creditId)
                        .map(credit -> PaymentConverter.paymentToPaymentDTO(payment, credit))); // Pasar credit como segundo argumento
    }


}
