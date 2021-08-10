package com.senla.testapp.flight.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.senla.testapp.flight.dao.FlightRepository;
import com.senla.testapp.flight.model.Flight;
import com.senla.testapp.flight.utils.Direction;

@Service
public class FlightService {

    @Autowired
    private FlightRepository repository;
    
    public Long getFlightByNumberAndDate(Long flightNumber, String date) {
        Optional<Flight> flight = repository.findByFlightNumberAndDepartureDate(flightNumber, date);
        if (flight.isPresent()) {
            return flight.get().getFlightId();
        }
        return null;
    }

    public List<Long> getFlightIdsByAirport(String iataAirportCode, Direction direction, String date) {
        List<Flight> flights = new ArrayList<>();
        flights = direction == Direction.ARR
                ? repository.findByArrivalAirportIATACode(iataAirportCode) // no arrival date in test data
                : repository.findByDepartureAirportIATACodeAndDepartureDate(iataAirportCode, date);

        return flights.stream()
                .map(Flight::getFlightId)
                .collect(Collectors.toList());
    }

}
