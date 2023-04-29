package com.company;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        //Project-Spring By Sergey Abrahamyan
        //Everything was done by using books mentioned, some internet forums and AI tools such as ChatGPT3.
        System.out.println("********start of the test for 8 bit converter********");
        Converter8Bit converterForBits = new Converter8Bit();
        boolean[] bits = {true, false, true, false, true, false, true, false};
        Spring[] springs = converterForBits.createSprings(bits);
        double[] amplitudes = {1, 2, 4, 8, 16, 32, 64, 128};
        double value = converterForBits.evaluateDecimal(bits, amplitudes);
        System.out.println("Binary: " + Arrays.toString(bits));
        System.out.println("Amplitudes: " + Arrays.toString(amplitudes));
        System.out.println("Decimal value: " + value);
        System.out.println("********end of the test for 8 bit converter********");



        System.out.println("********start of the test for int converter********");
        ConverterInt converterForInt = new ConverterInt();
        boolean[] bits1 = { true, true, false, true};
        double[] amplitudes1 = { 8.0, 4.0, 2.0, 1.0 };
        double result1 = converterForInt.evaluateDecimal(bits1, amplitudes1);
        System.out.println("Binary number: " + Arrays.toString(bits1));
        System.out.println("Decimal number: " + result1);
        System.out.println("********end of the test for int converter********");


        System.out.println("********start of the test for float converter********");
        boolean[] bits2 = {true, false, true, false, false, false, false, false, true, true};
        ConverterFloat converterFloat = new ConverterFloat(5, 4);
        double result2 = converterFloat.convert(bits1);
        System.out.println("Test Float is: " + Arrays.toString(bits1) + " = " + result1);

        System.out.println("********end of the test for float converter********");

    }
}
