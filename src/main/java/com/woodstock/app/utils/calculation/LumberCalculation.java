package com.woodstock.app.utils.calculation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

public class LumberCalculation {


    public static double getVolumePerCubic(double avgDiameter, double length){
        return (0.7854 * (avgDiameter * avgDiameter) * length)/10000;
    }

    public static double getAvgDiameter(double... values){

        if (values.length == 0) return 0;

        double avg = 0;

        for (double value : values){
            avg +=  value;
        }

        return avg/values.length;
    }

    public static double roundDown(double values) {
        return new BigDecimal(values).setScale(3, RoundingMode.HALF_DOWN).doubleValue();
    }

}
