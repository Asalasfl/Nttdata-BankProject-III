package nttdata.com.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import nttdata.com.dto.CreditDTO;
import nttdata.com.dto.PaymentDTO;
import nttdata.com.model.Credit;
import nttdata.com.model.Payment;
import nttdata.com.repository.CreditRepository;
import nttdata.com.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import static nttdata.com.utils.Utils.*;

import java.util.List;

import static org.mockito.Mockito.verify;

public class CreditServiceImplTest {

    @Mock
    private CreditRepository creditRepository;

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private CreditServiceImpl creditServiceImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateCredit() {
        when(creditRepository.save(any(Credit.class))).thenReturn(generateCreditMono());

        Mono<CreditDTO> credit = creditServiceImpl.createCredit(generateCreditDTO());
    }

    @Test
    public void testFindByCreditId() {
        // Arrange
        String creditId = "123456";
        Credit credit = new Credit();
        // Set up credit properties

        when(creditRepository.findById(creditId)).thenReturn(Mono.just(credit));

        // Act
        Mono<CreditDTO> result = creditServiceImpl.findByCreditId(creditId);

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(dto -> {
                    // Add assertions for expected values in the CreditDTO object
                    return true;
                })
                .verifyComplete();

        verify(creditRepository, times(1)).findById(creditId);
    }

    @Test
    public void testAddPayment() {
        // Arrange
        String creditId = "123456";
        PaymentDTO paymentDTO = new PaymentDTO();
        // Set up paymentDTO properties

        Credit credit = new Credit();
        // Set up credit properties

        when(creditRepository.findById(creditId)).thenReturn(Mono.just(credit));
        when(creditRepository.save(any(Credit.class))).thenReturn(Mono.just(credit));

        // Act
        Mono<CreditDTO> result = creditServiceImpl.addPayment(creditId, paymentDTO);

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(dto -> {
                    // Add assertions for expected values in the CreditDTO object
                    return true;
                })
                .verifyComplete();

        verify(creditRepository, times(1)).findById(creditId);
        verify(paymentRepository, times(1)).save(any(Payment.class));
        verify(creditRepository, times(1)).save(any(Credit.class));
    }

    @Test
    public void testGetPaymentsByCreditId() {
        // Arrange
        String creditId = "123456";
        Payment payment1 = new Payment();
        // Set up payment1 properties
        Payment payment2 = new Payment();
        // Set up payment2 properties
        List<Payment> payments = List.of(payment1, payment2);

        when(paymentRepository.findByCreditId(creditId)).thenReturn(Flux.fromIterable(payments));

        // Act
        Flux<PaymentDTO> result = creditServiceImpl.getPaymentsByCreditId(creditId);

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(dto -> {
                    // Add assertions for expected values in the PaymentDTO object
                    return true;
                })
                .expectNextMatches(dto -> {
                    // Add assertions for expected values in the PaymentDTO object
                    return true;
                })
                .verifyComplete();

        verify(paymentRepository, times(1)).findByCreditId(creditId);
    }
}
