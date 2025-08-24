package com.example.dealers_backend.repository;

import com.example.dealers_backend.entity.Dealer;
import com.example.dealers_backend.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {


    List<Vehicle> findByDealer_SubscriptionType(Dealer.SubscriptionType subscriptionType);

}
