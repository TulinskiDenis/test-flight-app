package com.senla.testapp.cargo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.senla.testapp.cargo.dao.CargoRepository;
import com.senla.testapp.cargo.model.Cargo;
import com.senla.testapp.cargo.model.CargoItem;
import com.senla.testapp.cargo.utils.CargoItemsType;
import com.senla.testapp.cargo.utils.WeightConverter;
import com.senla.testapp.cargo.utils.WeightUnit;

@Service
public class CargoService {

    @Autowired
    private CargoRepository repository;

    public Double getFlightWeight(Long flightId, WeightUnit weightUnit, CargoItemsType itemsType) {
        Optional<Cargo> cargoOpt = repository.findById(flightId);
        if (cargoOpt.isEmpty())
            return 0.00;

        Cargo cargo = cargoOpt.get();
        Double quantity = cargo.getCargoItemsByType(itemsType)
                .stream()
                .map(item -> convertWeight(item, weightUnit))
                .reduce(0.00, Double::sum);

        return quantity;
    }

    public Integer getCargoItemsQuantity(List<Long> flightIds, CargoItemsType itemsType) {
        List<Cargo> cargoes = (List<Cargo>) repository.findAllById(flightIds);
        if (cargoes.isEmpty())
            return 0;

        Integer quantity = cargoes.stream()
                .flatMap(cargo -> cargo.getCargoItemsByType(itemsType).stream())
                .map(item -> item.getPieces())
                .reduce(0, Integer::sum);

        return quantity;
    }

    private Double convertWeight(CargoItem item, WeightUnit targetWeightUnit) {
        if (item.getWeightUnit().equals(targetWeightUnit.getLowercase())) {
            return (double) item.getWeight();
        } else {
            return WeightConverter.convertWeight(item.getWeight(), targetWeightUnit);
        }
    }
}
