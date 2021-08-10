package com.senla.testapp.cargo.utils;

public class WeightConverter {

    public static double convertWeight(int weight, WeightUnit targetWeightUnit) {
        return weight * targetWeightUnit.getConvertionRate();
    }
}
