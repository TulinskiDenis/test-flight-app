package com.senla.testapp.flight.model;


import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Flight {

    @Id
    private Long flightId;
    private Long flightNumber;
    private String departureAirportIATACode;
    private String arrivalAirportIATACode;
    private String departureDate;

}
