package com.company;

public abstract class Converter {

    protected Spring[] springs;
    protected int numBits;

    public abstract Spring[] createSprings(boolean[] bits);
    public abstract double evaluateDecimal(boolean[] bits, double[] amplitudes);



    // no concrete method was implemented, only Abstrac ones
}

