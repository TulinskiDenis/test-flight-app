package com.senla.testapp.cargo.model;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class CargoItem {

    @Id
    private long id;
    private int weight;
    private String weightUnit;
    private int pieces;

}
