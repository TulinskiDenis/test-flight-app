package com.senla.flightapp.gateway.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.senla.flightapp.gateway.client.CargoServiceClient;
import com.senla.flightapp.gateway.client.FlightServiceClient;
import com.senla.flightapp.gateway.dto.ServiceResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("api/v1/gateway")
public class GatewayController {
    private final static Logger logger = LoggerFactory.getLogger(GatewayController.class);
    private static final String SYSTEM_ERROR = "System error";

    @Autowired
    private FlightServiceClient flightClient;

    @Autowired
    private CargoServiceClient cargoClient;

    @ExceptionHandler(Exception.class)
    protected ServiceResponse<Void> handleException(Exception ex) {
        logger.error(ex.getMessage());
        return new ServiceResponse.Builder<Void>()
                .setResponseCode(9)
                .setResponseMessage(SYSTEM_ERROR)
                .build();
    }

    @ApiOperation(value = "Get weight of cargo items by flight number + date + weight unit + items type", response = ServiceResponse.class)
    @GetMapping("weight/{flightNumber}/{date}/{weightUnit}/{itemsType}")
    public ServiceResponse<?> getFlightCargoWeight(
            @ApiParam(value = "Number", example = "3919") @PathVariable Long flightNumber,
            @ApiParam(value = "Date string", example = "2018-12-21T09:18:42 -03:00") @PathVariable String date,
            @ApiParam(value = "kg / lb", example = "kg") @PathVariable String weightUnit,
            @ApiParam(value = "cargo / baggage / total", example = "total") @PathVariable String itemsType) {

        ServiceResponse<Long> flightIdResponse = flightClient.getFlightId(flightNumber, date);
        if (flightIdResponse.isSuccess()) {
            return cargoClient.getFlightWeight(flightIdResponse.getData(), weightUnit, itemsType);
        } else {
            return flightIdResponse;
        }
    }

    @ApiOperation(value = "Get number of flights by IATA Airport Code + direction + date", response = ServiceResponse.class)
    @GetMapping("flights/{iataAirportCode}/{direction}/{date}")
    public ServiceResponse<?> getNumberOfFlights(
            @ApiParam(value = "IATA Airport Code", example = "YYZ") @PathVariable String iataAirportCode,
            @ApiParam(value = "ARR(arrival) / DEP(departure)", example = "ARR") @PathVariable String direction,
            @ApiParam(value = "Date string", example = "2018-12-21T09:18:42 -03:00") @PathVariable String date) {

        ServiceResponse<List<Long>> flightIds = flightClient.getFlightIdsByAirport(iataAirportCode, direction, date);
        if (!flightIds.isSuccess())
            return flightIds;

        return new ServiceResponse.Builder<Integer>()
                .setData(flightIds.getData().size())
                .build();
    }

    @ApiOperation(value = "Get number of pieces by IATA Airport Code + direction + date", response = ServiceResponse.class)
    @GetMapping("pieces/{iataAirportCode}/{direction}/{date}")
    public ServiceResponse<?> getNumberOfCargoPieces(
            @ApiParam(value = "IATA Airport Code", example = "YYZ") @PathVariable String iataAirportCode,
            @ApiParam(value = "ARR(arrival) / DEP(departure)", example = "ARR") @PathVariable String direction,
            @ApiParam(value = "Date string", example = "2018-12-21T09:18:42 -03:00") @PathVariable String date) {

        ServiceResponse<List<Long>> flightIdsResponse = flightClient
                .getFlightIdsByAirport(iataAirportCode, direction, date);
        if (flightIdsResponse.isSuccess()) {
            return cargoClient.getNumberOfPiecesByFlights(flightIdsResponse.getData());
        } else {
            return flightIdsResponse;
        }
    }
}
