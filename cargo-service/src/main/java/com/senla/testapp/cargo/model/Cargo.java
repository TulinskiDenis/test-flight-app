package com.senla.testapp.cargo.model;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.data.annotation.Id;

import com.senla.testapp.cargo.utils.CargoItemsType;

import lombok.Data;

@Data
public class Cargo {

    @Id
    private Long flightId;
    private List<CargoItem> baggage;
    private List<CargoItem> cargo;

    public List<CargoItem> getCargoItemsByType(CargoItemsType type) {
        switch (type) {
        case BAGGAGE:
            return getBaggage();
        case CARGO:
            return getCargo();
        case TOTAL:
            return Stream.concat(getBaggage().stream(), getCargo().stream())
                    .collect(Collectors.toList());
        default:
            return null;
        }
    }

}
