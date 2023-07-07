
package nttdata.com.controller;

import nttdata.com.dto.CreditDTO;
import nttdata.com.dto.PaymentDTO;
import nttdata.com.service.CreditServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

        class CreditControllerTest {
            @Mock
            private CreditServiceImpl creditService;

            @InjectMocks
            private CreditController creditController;

            @BeforeEach
            void setUp() {
                MockitoAnnotations.openMocks(this);
            }

            @Test
            void testCreateCredit() {
                // Datos de prueba
                CreditDTO creditDTO = new CreditDTO();
                creditDTO.setIdCredit("1");
                ResponseEntity<CreditDTO> expectedResponse = ResponseEntity.created(URI.create("/credits/1"))
                        .body(creditDTO);

                // Configurar el comportamiento del servicio mock
                when(creditService.createCredit((CreditDTO) any())).thenReturn(Mono.just(creditDTO));

                // Ejecutar el método del controlador y verificar el resultado
                Mono<ResponseEntity<CreditDTO>> result = creditController.createCredit(creditDTO);
                StepVerifier.create(result)
                        .expectNext(expectedResponse)
                        .verifyComplete();
            }

            @Test
            void testFindCreditById() {
                // Datos de prueba
                String creditId = "1";
                CreditDTO expectedCredit = new CreditDTO();
                expectedCredit.setIdCredit(creditId);

                // Configurar el comportamiento del servicio mock
                when(creditService.findByCreditId(creditId)).thenReturn(Mono.just(expectedCredit));

                // Ejecutar el método del controlador y verificar el resultado
                Mono<CreditDTO> result = creditController.findCreditById(creditId);
                StepVerifier.create(result)
                        .expectNext(expectedCredit)
                        .verifyComplete();
            }

            @Test
            void testAddPayment() {
                // Datos de prueba
                String creditId = "1";
                PaymentDTO paymentDTO = new PaymentDTO();
                CreditDTO expectedCredit = new CreditDTO();
                expectedCredit.setIdCredit(creditId);

                // Configurar el comportamiento del servicio mock
                when(creditService.addPayment(eq(creditId), any())).thenReturn(Mono.just(expectedCredit));

                // Ejecutar el método del controlador y verificar el resultado
                Mono<CreditDTO> result = creditController.addPayment(creditId, paymentDTO);
                StepVerifier.create(result)
                        .expectNext(expectedCredit)
                        .verifyComplete();
            }

            @Test
            void testGetPaymentsByCreditId() {
                // Datos de prueba
                String creditId = "1";
                List<PaymentDTO> expectedPayments = new ArrayList<>();

                // Configurar el comportamiento del servicio mock
                when(creditService.getPaymentsByCreditId(creditId)).thenReturn(Flux.fromIterable(expectedPayments));

                // Ejecutar el método del controlador y verificar el resultado
                Flux<PaymentDTO> result = creditController.getPaymentsByCreditId(creditId);
                StepVerifier.create(result)
                        .expectNextCount(expectedPayments.size())
                        .verifyComplete();
            }
        }
