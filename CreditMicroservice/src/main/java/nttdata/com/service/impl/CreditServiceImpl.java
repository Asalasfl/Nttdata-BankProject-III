package nttdata.com.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nttdata.com.dto.CreditDTO;
import nttdata.com.dto.PaymentDTO;
import nttdata.com.feign.CustomerClient;
import nttdata.com.model.Credit;
import nttdata.com.model.Payment;
import nttdata.com.repository.CreditRepository;
import nttdata.com.repository.PaymentRepository;
import nttdata.com.service.CreditService;
import nttdata.com.utils.CreditConverter;
import nttdata.com.utils.PaymentConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class CreditServiceImpl implements CreditService {
    private final CreditRepository creditRepository;
    private final PaymentRepository paymentRepository;
    @Autowired
    private CustomerClient customerClient;
    @Override
    public Mono<CreditDTO> createCredit(CreditDTO creditDTO) {

            Credit credit = CreditConverter.DTOToCredit(creditDTO);
            credit.setCustomerId(String.valueOf(customerClient.getCustomerById(credit.getCustomerId())));
            List<Payment> payments = new ArrayList<>();
            BigDecimal totalPaymentAmount = BigDecimal.ZERO; // Variable para almacenar la suma de los montos de los pagos

            if (creditDTO.getPayments() != null) {
                for (PaymentDTO paymentDTO : creditDTO.getPayments()) {
                    Payment payment = PaymentConverter.paymentDTOToPayment(paymentDTO);
                    payments.add(payment);
                    totalPaymentAmount = totalPaymentAmount.add(payment.getAmount()); // Sumar el monto del pago al totalPaymentAmount
                }
            }


            credit.setPaymentReferences(payments);
            credit.setInterestRate(BigDecimal.valueOf(0.005));
            credit.setRemainingAmount(credit.getAmount().add(credit.getAmount().multiply(credit.getInterestRate())).subtract(totalPaymentAmount)); // Restar el totalPaymentAmount del remainingAmount

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
    public Mono<CreditDTO> addPayment(String idCredit, PaymentDTO paymentDTO) {
        return creditRepository.findById(idCredit)
                .flatMap(credit -> {
                    if (credit == null) {
                        return Mono.error(new RuntimeException("El crédito no existe."));
                    }

                    BigDecimal remainingAmount = credit.getRemainingAmount();
                    BigDecimal paymentAmount = paymentDTO.getAmount();
                    paymentDTO.setIdPayment(credit.getId());
                    if (remainingAmount.compareTo(paymentAmount) <= 0) {
                        return Mono.just(new CreditDTO("El monto total ya esta pagado o no coincide con el monto que falta pagar."));
                    }
                    credit.setRemainingAmount(remainingAmount.subtract(paymentAmount));

                    Payment payment = PaymentConverter.paymentDTOToPayment(paymentDTO);
                    payment.setCreditId(credit.getId());

                    credit.getPaymentReferences().add(payment);

                    return creditRepository.save(credit)
                            .map(savedCredit -> {
                                CreditDTO creditDTO = CreditConverter.creditToDTO(savedCredit);
                                log.info("Pago añadido correctamente. ID del crédito: {}, ID del pago: {}",
                                        savedCredit.getId(), payment.getId());
                                return creditDTO;
                            });
                })
                .onErrorResume(error -> {
                    log.error("Error al añadir el pago: {}", error.getMessage());
                    return Mono.error(error);
                });
    }

    @Override
    public Flux<PaymentDTO> getPaymentsByCreditId(String creditId) {
        return creditRepository.findById(creditId)
                .flatMapIterable(Credit::getPaymentReferences)
                .map(payment -> PaymentConverter.paymentToPaymentDTO(payment))
                .switchIfEmpty(Mono.error(new RuntimeException("El crédito no existe o no tiene pagos.")));
    }
}
