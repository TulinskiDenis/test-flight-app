package com.senla.flightapp.gateway.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.senla.flightapp.gateway.dto.ServiceResponse;

@FeignClient(url = "cargo-service:8002/api/v1/cargo", name = "cargo-service")
public interface CargoServiceClient {

    @GetMapping("/weight/{flightId}/{weightUnit}/{itemsType}")
    public ServiceResponse<Double> getFlightWeight(@PathVariable Long flightId, @PathVariable String weightUnit, @PathVariable String itemsType);

    @GetMapping("/pieces/baggage/{flightIds}")
    public ServiceResponse<Integer> getNumberOfPiecesByFlights(@PathVariable List<Long> flightIds);
}
