package com.example.dealers_backend.controller;

import com.example.dealers_backend.entity.Payment;
import com.example.dealers_backend.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public static class PaymentRequest {
        private Long dealerId;
        private BigDecimal amount;
        private String method;

        public Long getDealerId() { return dealerId; }
        public void setDealerId(Long dealerId) { this.dealerId = dealerId; }

        public BigDecimal getAmount() { return amount; }
        public void setAmount(BigDecimal amount) { this.amount = amount; }

        public String getMethod() { return method; }
        public void setMethod(String method) { this.method = method; }
    }

    @PostMapping("/initiate")
    public ResponseEntity<Payment> initiatePayment(@RequestBody PaymentRequest request) {
        if (request.getDealerId() == null || request.getAmount() == null || request.getMethod() == null) {
            return ResponseEntity.badRequest().build();
        }

        Payment.Method method;
        switch (request.getMethod().trim().toLowerCase()) {
            case "upi": method = Payment.Method.UPI; break;
            case "card": case "cardpayment": method = Payment.Method.CARD; break;
            case "netbanking": case "net-banking": case "net banking": method = Payment.Method.NETBANKING; break;
            default: return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(paymentService.initiatePayment(request.getDealerId(), request.getAmount(), method));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPayment(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.getPaymentById(id));
    }

    @GetMapping("/dealer/{dealerId}")
    public ResponseEntity<List<Payment>> getPaymentsByDealer(@PathVariable Long dealerId) {
        return ResponseEntity.ok(paymentService.getPaymentsByDealer(dealerId));
    }

    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }
}
