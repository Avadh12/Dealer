package com.example.dealers_backend.repository;

import com.example.dealers_backend.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByDealer_Id(Long dealerId);
}
