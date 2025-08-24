package com.example.dealers_backend.repository;


import com.example.dealers_backend.entity.Dealer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DealerRepository extends JpaRepository<Dealer, Long> {
}
