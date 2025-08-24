package com.example.dealers_backend.service;


import com.example.dealers_backend.entity.Dealer;
import com.example.dealers_backend.repository.DealerRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DealerService {

    private final DealerRepository dealerRepository;

    public DealerService(DealerRepository dealerRepository) {
        this.dealerRepository = dealerRepository;
    }

    public List<Dealer> getAllDealers() {
        return dealerRepository.findAll();
    }

    public Dealer getDealerById(Long id) {
        return dealerRepository.findById(id).orElseThrow(() -> new RuntimeException("Dealer not found"));
    }

    public Dealer createDealer(Dealer dealer) {
        return dealerRepository.save(dealer);
    }

    public Dealer updateDealer(Long id, Dealer updatedDealer) {
        Dealer dealer = getDealerById(id);
        dealer.setName(updatedDealer.getName());
        dealer.setEmail(updatedDealer.getEmail());
        dealer.setSubscriptionType(updatedDealer.getSubscriptionType());
        return dealerRepository.save(dealer);
    }

    public void deleteDealer(Long id) {
        dealerRepository.deleteById(id);
    }
}
