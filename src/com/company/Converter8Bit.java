package com.company;

public class Converter8Bit extends Converter{
    private static final int BIT_COUNT = 8;

    private static final Spring[] ZERO_SPRINGS = {new Spring(0)};
    private static final Spring[] ONE_SPRINGS = {new Spring(1)};

    @Override
    public Spring[] createSprings(boolean[] bits) {
        if (bits.length != BIT_COUNT) {
            throw new IllegalArgumentException("Bits array must have 8 elements");
        }
        Spring[] springs = new Spring[BIT_COUNT];
        for (int i = 0; i < BIT_COUNT; i++) {
            springs[i] = bits[i] ? ONE_SPRINGS[0] : ZERO_SPRINGS[0];
        }
        return springs;
    }

    @Override
    public double evaluateDecimal(boolean[] bits, double[] amplitudes) {
        if (bits.length != BIT_COUNT) {
            throw new IllegalArgumentException("Bits array must have 8 elements");
        }
        if (amplitudes.length != BIT_COUNT) {
            throw new IllegalArgumentException("Amplitudes array must have 8 elements");
        }
        double value = 0;
        for (int i = 0; i < BIT_COUNT; i++) {
            double bitValue = bits[i] ? 1 : 0;
            value += bitValue * amplitudes[i];
        }
        return value;
    }

}
