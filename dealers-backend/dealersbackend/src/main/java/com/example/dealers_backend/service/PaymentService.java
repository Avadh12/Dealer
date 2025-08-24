package com.example.dealers_backend.service;

import com.example.dealers_backend.entity.Dealer;
import com.example.dealers_backend.entity.Payment;
import com.example.dealers_backend.repository.DealerRepository;
import com.example.dealers_backend.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final DealerRepository dealerRepository;

    public PaymentService(PaymentRepository paymentRepository, DealerRepository dealerRepository) {
        this.paymentRepository = paymentRepository;
        this.dealerRepository = dealerRepository;
    }

    @Transactional
    public Payment initiatePayment(Long dealerId, BigDecimal amount, Payment.Method method) {
        Dealer dealer = dealerRepository.findById(dealerId)
                .orElseThrow(() -> new RuntimeException("Dealer not found with id: " + dealerId));

        Payment payment = new Payment();
        payment.setDealer(dealer);
        payment.setAmount(amount);
        payment.setMethod(method);
        payment.setStatus(Payment.Status.PENDING);
        payment.setCreatedAt(LocalDateTime.now());

        payment = paymentRepository.save(payment);

        Long paymentId = payment.getId();
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(5000L);
                paymentRepository.findById(paymentId).ifPresent(p -> {
                    p.setStatus(Payment.Status.SUCCESS);
                    paymentRepository.save(p);
                });
            } catch (Exception e) {
                paymentRepository.findById(paymentId).ifPresent(p -> {
                    p.setStatus(Payment.Status.FAILED);
                    paymentRepository.save(p);
                });
            }
        });

        return payment;
    }

    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + id));
    }

    public List<Payment> getPaymentsByDealer(Long dealerId) {
        return paymentRepository.findByDealer_Id(dealerId);
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}
