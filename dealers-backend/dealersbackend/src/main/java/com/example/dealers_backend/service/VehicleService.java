package com.example.dealers_backend.service;

import com.example.dealers_backend.entity.Dealer;
import com.example.dealers_backend.entity.Vehicle;
import com.example.dealers_backend.repository.DealerRepository;
import com.example.dealers_backend.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final DealerRepository dealerRepository;

    public VehicleService(VehicleRepository vehicleRepository, DealerRepository dealerRepository) {
        this.vehicleRepository = vehicleRepository;
        this.dealerRepository = dealerRepository;
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public Vehicle getVehicleById(Long id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
    }

    public Vehicle createVehicle(Vehicle vehicle) {
        Dealer dealer = dealerRepository.findById(vehicle.getDealer().getId())
                .orElseThrow(() -> new RuntimeException("Dealer not found"));
        vehicle.setDealer(dealer);  // attach managed Dealer entity
        return vehicleRepository.save(vehicle);
    }

    public Vehicle updateVehicle(Long id, Vehicle updatedVehicle) {
        Vehicle vehicle = getVehicleById(id);

        Dealer dealer = dealerRepository.findById(updatedVehicle.getDealer().getId())
                .orElseThrow(() -> new RuntimeException("Dealer not found"));

        vehicle.setModel(updatedVehicle.getModel());
        vehicle.setPrice(updatedVehicle.getPrice());
        vehicle.setStatus(updatedVehicle.getStatus());
        vehicle.setDealer(dealer);

        return vehicleRepository.save(vehicle);
    }

    public void deleteVehicle(Long id) {
        vehicleRepository.deleteById(id);
    }

    public List<Vehicle> getVehiclesByPremiumDealers() {
        return vehicleRepository.findByDealer_SubscriptionType(Dealer.SubscriptionType.PREMIUM);
    }
}
