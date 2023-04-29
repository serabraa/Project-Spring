package com.company;
import java.util.Arrays;

public class FT {
    private int n;
    private double[] real;
    private double[] imag;

    public FT(double[] coordinates) {
        this.n = coordinates.length;
        this.real = new double[n];
        this.imag = new double[n];

        for (int k = 0; k < n; k++) {
            for (int t = 0; t < n; t++) {
                double angle = 2 * Math.PI * k * t / n;
                real[k] += coordinates[t] * Math.cos(angle);
                imag[k] -= coordinates[t] * Math.sin(angle);
            }
            real[k] /= n;
            imag[k] /= n;
        }
    }

    public double[] getAmplitudes() {
        double[] amplitudes = new double[n / 2 + 1];
        amplitudes[0] = Math.abs(real[0]);
        for (int k = 1; k <= n / 2; k++) {
            double magnitude = Math.sqrt(real[k] * real[k] + imag[k] * imag[k]);
            amplitudes[k] = 2 * magnitude;
        }
        return amplitudes;
    }

    public double[] getFrequencies(double dt) {
        double[] frequencies = new double[n / 2 + 1];
        frequencies[0] = 0;
        for (int k = 1; k <= n / 2; k++) {
            frequencies[k] = (double) k / (n * dt);
        }
        return frequencies;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        double[] amplitudes = getAmplitudes();
        double[] frequencies = getFrequencies(1.0);
        for (int i = 0; i < amplitudes.length; i++) {
            sb.append(String.format("f=%.3f Hz, A=%.3f\n", frequencies[i], amplitudes[i]));
        }
        return sb.toString();
    }
}
