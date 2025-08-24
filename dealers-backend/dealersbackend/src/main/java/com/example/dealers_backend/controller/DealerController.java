package com.example.dealers_backend.controller;

import com.example.dealers_backend.entity.Dealer;
import com.example.dealers_backend.service.DealerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dealers")
public class DealerController {

    private final DealerService dealerService;

    public DealerController(DealerService dealerService) {
        this.dealerService = dealerService;
    }

    @GetMapping
    public List<Dealer> getAllDealers() {
        return dealerService.getAllDealers();
    }

    @GetMapping("/{id}")
    public Dealer getDealerById(@PathVariable Long id) {
        return dealerService.getDealerById(id);
    }

    @PostMapping
    public Dealer createDealer(@RequestBody Dealer dealer) {
        return dealerService.createDealer(dealer);
    }

    @PutMapping("/{id}")
    public Dealer updateDealer(@PathVariable Long id, @RequestBody Dealer dealer) {
        return dealerService.updateDealer(id, dealer);
    }

    @DeleteMapping("/{id}")
    public void deleteDealer(@PathVariable Long id) {
        dealerService.deleteDealer(id);
    }
}
