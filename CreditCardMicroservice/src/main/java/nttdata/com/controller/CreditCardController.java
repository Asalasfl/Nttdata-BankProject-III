package nttdata.com.controller;


import lombok.AllArgsConstructor;
import nttdata.com.dto.CreditCardDTO;
import nttdata.com.dto.TransactionDTO;
import nttdata.com.service.impl.CreditCardServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@AllArgsConstructor
@RestController
@RequestMapping("/api/credit-cards")
public class CreditCardController {
    private final CreditCardServiceImpl creditCardServiceImpl;

    @PostMapping(value = "/credits-card",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<ResponseEntity<CreditCardDTO>> createCreditCard(@RequestBody CreditCardDTO creditCardDTO) {
        return creditCardServiceImpl.createCreditCard(creditCardDTO)
                .map(createdCreditCard -> ResponseEntity.created(URI.create("/credits-card/" + createdCreditCard.getCreditCardId()))
                        .body(createdCreditCard))
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }
    @GetMapping(value = "/{id}",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<CreditCardDTO> findCreditCardById(@PathVariable ("id")  String id) {
        return creditCardServiceImpl.findByCreditCardId(id);
    }

    @PostMapping(value = "/{id}/transactions", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<CreditCardDTO> addTransaction(@PathVariable ("id") String creditCardId,
                                              @RequestBody TransactionDTO transactionDTO) {
        return creditCardServiceImpl.addTransaction(creditCardId, transactionDTO);
    }

    @GetMapping(value = "/{creditCardId}/transactions", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<TransactionDTO> getTransactionsByCreditCardId(@PathVariable String creditCardId) {
        return creditCardServiceImpl.getTransactionsByCreditCardId(creditCardId);
    }
}