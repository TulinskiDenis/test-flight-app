package com.senla.testapp.cargo.utils;

public enum WeightUnit {

    KG(2.2046),
    LB(0.4536);

    private double convertionRate;

    WeightUnit(double convertionRate) {
        this.convertionRate = convertionRate;
    }

    public double getConvertionRate() {
        return convertionRate;
    }

    public String getLowercase() {
        return this.name().toLowerCase();
    }

}
