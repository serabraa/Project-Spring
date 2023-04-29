package com.company;

import java.util.Arrays;

public class ConverterFloat extends Converter{

        private final int integerBits;
        private final int fractionalBits;

        public ConverterFloat(int integerBits, int fractionalBits) {
            this.integerBits = integerBits;
            this.fractionalBits = fractionalBits;
        }

        @Override
        public Spring[] createSprings(boolean[] bits) {
            Spring[] integerSprings = createUnitSprings(integerBits, bits, 0);
            Spring[] fractionalSprings = createUnitSprings(fractionalBits, bits, integerBits);
            return concatenateArrays(integerSprings, fractionalSprings);
        }

        @Override
        public double evaluateDecimal(boolean[] bits, double[] amplitudes) {
            int integerPart = evaluateIntegerPart(bits, amplitudes);
            double fractionalPart = evaluateFractionalPart(bits, amplitudes);
            return integerPart + fractionalPart;
        }

        private int evaluateIntegerPart(boolean[] bits, double[] amplitudes) {
            int integerPart = 0;
            for (int i = 0; i < integerBits; i++) {
                if (bits[i]) {
                    integerPart += Math.pow(2, integerBits - i - 1) * amplitudes[i];
                }
            }
            return integerPart;
        }

        private double evaluateFractionalPart(boolean[] bits, double[] amplitudes) {
            double fractionalPart = 0.0;
            for (int i = 0; i < fractionalBits; i++) {
                if (bits[integerBits + i]) {
                    fractionalPart += Math.pow(2, -(i + 1)) * amplitudes[integerBits + i];
                }
            }
            return fractionalPart;
        }

        private Spring[] concatenateArrays(Spring[] first, Spring[] second) {
            Spring[] result = new Spring[first.length + second.length];
            System.arraycopy(first, 0, result, 0, first.length);
            System.arraycopy(second, 0, result, first.length, second.length);
            return result;
        }
    private Spring[] createUnitSprings(int numBits, boolean[] bits, int startBitIndex) {
        final Spring[] unitSprings = new Spring[numBits];
        for (int i = 0; i < numBits; i++) {
            unitSprings[i] = new Spring(bits[startBitIndex + i] ? 1 : 0);
        }
        return unitSprings;
    }
    public int convert(boolean[] bits) {
        int decimalValue = 0;
        for (int i = 0; i < numBits; i++) {
            if (bits[i]) {
                decimalValue += Math.pow(2, numBits - i - 1);
            }
        }
        return decimalValue;
    }
}
