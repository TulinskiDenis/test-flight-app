package com.senla.flightapp.gateway.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.senla.flightapp.gateway.dto.ServiceResponse;

@FeignClient(url = "flight-service:8001/api/v1/flight", name = "flight-service")
public interface FlightServiceClient {

    @GetMapping("{flightNumber}/{date}")
    public ServiceResponse<Long> getFlightId(@PathVariable Long flightNumber, @PathVariable String date);

    @GetMapping("/airport/{iataAirportCode}/{direction}/{date}")
    public ServiceResponse<List<Long>> getFlightIdsByAirport(@PathVariable String iataAirportCode, @PathVariable String direction,
            @PathVariable String date);

}
