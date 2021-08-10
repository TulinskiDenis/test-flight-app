package com.senla.testapp.flight.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.senla.testapp.flight.dto.ServiceResponse;
import com.senla.testapp.flight.service.FlightService;
import com.senla.testapp.flight.utils.Direction;

@RestController
@RequestMapping("api/v1/flight")
public class FlightController {
    private static final String NOT_FOUND = "Flights not found";
    private static final String SYSTEM_ERROR = "System error";
    private final static Logger logger = LoggerFactory.getLogger(FlightController.class);

    @Autowired
    private FlightService flightService;

    @ExceptionHandler(Exception.class)
    public ServiceResponse<Void> handleException(Exception ex) {
        logger.error(ex.getMessage());
        return new ServiceResponse.Builder<Void>()
                .setResponseCode(9)
                .setResponseMessage(SYSTEM_ERROR)
                .build();
    }

    @GetMapping("/{flightNumber}/{date}")
    public ServiceResponse<Long> getFlightId(@PathVariable Long flightNumber, @PathVariable String date) {

        Long flightId = flightService.getFlightByNumberAndDate(flightNumber, date);

        if (flightId == null) {
            return new ServiceResponse.Builder<Long>()
                    .setResponseCode(2)
                    .setResponseMessage(NOT_FOUND)
                    .build();
        }

        return new ServiceResponse.Builder<Long>()
                .setData(flightId)
                .build();
    }

    @GetMapping("/airport/{iataAirportCode}/{direction}/{date}")
    public ServiceResponse<List<Long>> getFlightIdsByAirport(@PathVariable String iataAirportCode,
            @PathVariable String direction, @PathVariable String date) {

        List<Long> flights = flightService
                .getFlightIdsByAirport(iataAirportCode, Direction.valueOf(direction.toUpperCase()), date);

        if (flights.isEmpty()) {
            return new ServiceResponse.Builder<List<Long>>()
                    .setResponseCode(2)
                    .setResponseMessage(NOT_FOUND)
                    .build();
        }

        return new ServiceResponse.Builder<List<Long>>()
                .setData(flights)
                .build();
    }

}
