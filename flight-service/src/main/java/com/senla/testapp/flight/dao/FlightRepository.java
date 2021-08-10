package com.senla.testapp.flight.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.senla.testapp.flight.model.Flight;

public interface FlightRepository extends MongoRepository<Flight, Long> {

    Optional<Flight> findByFlightNumberAndDepartureDate(Long number, String date);

    List<Flight> findByDepartureAirportIATACodeAndDepartureDate(String iataAirportCode, String date);

    List<Flight> findByArrivalAirportIATACode(String iataAirportCode);

}
