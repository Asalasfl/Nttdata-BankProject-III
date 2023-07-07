package nttdata.com.controller;

import lombok.AllArgsConstructor;
import nttdata.com.dto.CreditDTO;
import nttdata.com.dto.PaymentDTO;
import nttdata.com.service.CreditServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
@AllArgsConstructor
@RestController
@RequestMapping("/api/credits")
public class CreditController {
    private final CreditServiceImpl creditServiceImpl;

    @PostMapping(value = "/credits",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<ResponseEntity<CreditDTO>> createCredit(@RequestBody CreditDTO creditDTO) {
        return creditServiceImpl.createCredit(creditDTO)
                .map(createdCredit -> ResponseEntity.created(URI.create("/credits/" + createdCredit.getIdCredit()))
                        .body(createdCredit))
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }
    @GetMapping(value = "/{id}",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<CreditDTO> findCreditById(@PathVariable("id") String creditId) {
        return creditServiceImpl.findByCreditId(creditId);
    }
    @PostMapping(value = "/{id}/payments", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<CreditDTO> addPayment(@PathVariable("id") String creditId,
                                      @RequestBody PaymentDTO paymentDTO) {
        return creditServiceImpl.addPayment(creditId, paymentDTO);
    }
    @GetMapping(value = "/payments/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<PaymentDTO> getPaymentsByCreditId(@PathVariable ("id") String creditId) {
        return creditServiceImpl.getPaymentsByCreditId(creditId);
    }
}