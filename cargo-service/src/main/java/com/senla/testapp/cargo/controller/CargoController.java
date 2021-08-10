package com.senla.testapp.cargo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.senla.testapp.cargo.dto.ServiceResponse;
import com.senla.testapp.cargo.service.CargoService;
import com.senla.testapp.cargo.utils.CargoItemsType;
import com.senla.testapp.cargo.utils.WeightUnit;

@RestController
@RequestMapping("api/v1/cargo")
public class CargoController {
    private static final String SYSTEM_ERROR = "System error";
    private final static Logger logger = LoggerFactory.getLogger(CargoController.class);

    @Autowired
    private CargoService cargoService;

    @ExceptionHandler(Exception.class)
    public ServiceResponse<Void> handleException(Exception ex) {
        logger.error(ex.getMessage());
        return new ServiceResponse.Builder<Void>()
                .setResponseCode(9)
                .setResponseMessage(SYSTEM_ERROR)
                .build();
    }

    @GetMapping("/weight/{flightId}/{weightUnit}/{itemsType}")
    public ServiceResponse<Double> getWeight(@PathVariable Long flightId, @PathVariable String weightUnit,
            @PathVariable String itemsType) {

        Double weight = cargoService.getFlightWeight(flightId,
                WeightUnit.valueOf(weightUnit.toUpperCase()),
                CargoItemsType.valueOf(itemsType.toUpperCase()));

        return new ServiceResponse.Builder<Double>()
                .setData(weight)
                .build();
    }

    @GetMapping("/pieces/baggage/{flightIds}")
    public ServiceResponse<Integer> getCargoItemsQuantity(@PathVariable List<Long> flightIds) {
        return new ServiceResponse.Builder<Integer>()
                .setData(cargoService.getCargoItemsQuantity(flightIds, CargoItemsType.BAGGAGE))
                .build();
    }
}
