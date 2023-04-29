package com.company;

import java.util.Arrays;

public class ConverterInt extends Converter {

    private final int BITS_PER_UNIT = 8;

    @Override
    public Spring[] createSprings(boolean[] bits) {
        int n = bits.length;
        Spring[] springs = new Spring[1];
        springs[0] = new Spring(0);

        for (int i = 0; i < n; i++) {
            Spring[] unitSprings = createUnitSprings(new boolean[] {bits[i]});
            Spring[] inSeries = new Spring[springs.length + unitSprings.length];
            System.arraycopy(springs, 0, inSeries, 0, springs.length);
            System.arraycopy(unitSprings, 0, inSeries, springs.length, unitSprings.length);
            springs = inSeries;
        }

        return springs;
    }
    @Override
    public double evaluateDecimal(boolean[] bits, double[] amplitudes) {
        double decimalValue = 0;
        int numSprings = (int) Math.ceil(bits.length / (double) BITS_PER_UNIT);

        for (int i = 0; i < numSprings; i++) {
            int startIndex = i * BITS_PER_UNIT;
            int endIndex = Math.min(startIndex + BITS_PER_UNIT, bits.length);
            boolean[] unitBits = Arrays.copyOfRange(bits, startIndex, endIndex);
            double unitValue = evaluateUnitDecimal(unitBits, amplitudes);
            decimalValue += unitValue * Math.pow(2, i * BITS_PER_UNIT);
        }

        return decimalValue;
    }

    public Spring[] createUnitSprings(boolean[] unitBits) {
        int n = unitBits.length;
        Spring[] springs = new Spring[n];
        for (int i = 0; i < n; i++) {
            springs[i] = new Spring(1);
        }
        return springs;
    }

    private double evaluateUnitDecimal(boolean[] unitBits, double[] amplitudes) {
        double unitValue = 0;

        for (int i = 0; i < unitBits.length; i++) {
            if (unitBits[i])  {
                unitValue += amplitudes[i];
            }
        }

        return unitValue;
    }

}