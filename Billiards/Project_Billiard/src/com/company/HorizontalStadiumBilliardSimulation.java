package com.company;import java.util.*;

public class HorizontalStadiumBilliardSimulation {
    public static void main(String[] args) {

        double radius = 1.0;
        int numReflections = 10;

        int M = 10;
        int[] binCounts = new int[M];

        double[] LValues = {1.0, 2.0, 3.0};

        for (double L : LValues) {
            List<Double> reflectionX = new ArrayList<>();
            List<Double> reflectionY = new ArrayList<>();

            simulateStadiumBilliard(radius, L, numReflections, reflectionX, reflectionY);

            testUniformity(reflectionX, L, M, binCounts);
        }

        double mean = calculateMean(binCounts);
        double variance = calculateVariance(binCounts, mean);

        System.out.println("Mean: " + mean);
        System.out.println("Variance: " + variance);
    }

    public static void simulateStadiumBilliard(double radius, double L, int numReflections,
                                               List<Double> reflectionX, List<Double> reflectionY) {
        Random random = new Random();
        double x = -radius + 2 * radius * random.nextDouble();
        double y = Math.sqrt(radius * radius - x * x);

        double magnitude = 1.0;
        double angle = 2 * Math.PI * random.nextDouble();
        double px = magnitude * Math.cos(angle);
        double py = magnitude * Math.sin(angle);

        reflectionX.add(x);
        reflectionY.add(y);

        for (int i = 0; i < numReflections; i++) {
            String side;

            if (y >= 0 && Math.abs(x) <= L / 2) {
                side = "top";
            } else if (y <= 0 && Math.abs(x) <= L / 2) {
                side = "bottom";
            } else if (y > 0 && Math.abs(x) > L / 2) {
                side = "right";
            } else {
                side = "left";
            }

            double nextX, nextY;

            if (side.equals("top")) {
                nextX = x;
                nextY = Math.sqrt(radius * radius - nextX * nextX);
            } else if (side.equals("bottom")) {
                nextX = x;
                nextY = -Math.sqrt(radius * radius - nextX * nextX);
            } else if (side.equals("right")) {
                double xc = L / 2;
                nextX = xc + Math.sqrt(radius * radius - y * y);
                nextY = y;
            } else {
                double xc = -L / 2;
                nextX = xc - Math.sqrt(radius * radius - y * y);
                nextY = y;
            }

            reflectionX.add(nextX);
            reflectionY.add(nextY);

            x = nextX;
            y = nextY;

            if (side.equals("top") || side.equals("bottom")) {
                py = -py;
            } else {
                double xc = (side.equals("right")) ? L / 2 : -L / 2;
                double dx = x - xc;
                double dy = y;
                double factor = (dy * dy - dx * dx) / (dy * dy + dx * dx);

                double newPx = (dy * dy - dx * dx) * px - 2 * dx * dy * py;
                double newPy = -2 * dx * dy * px + (dx * dx - dy * dy) * py;

                px = newPx;
                py = newPy;
            }
        }
    }

    public static void testUniformity(List<Double> reflectionX, double L, int M, int[] binCounts) {
        int n = reflectionX.size();

        double binSize = L / M;

        Arrays.fill(binCounts, 0);
        for (double x : reflectionX) {
            int binIndex = (int) Math.floor((x + L / 2) / binSize);
            binCounts[binIndex]++;
        }

        int expectedCount = n / M;
        boolean isUniform = true;
        for (int count : binCounts) {
            if (Math.abs(count - expectedCount) > 1) {
                isUniform = false;
                break;
            }
        }

        System.out.println("Bin Counts:");
        for (int i = 0; i < M; i++) {
            System.out.println("Bin " + i + ": " + binCounts[i]);
        }

        if (isUniform) {
            System.out.println("The sequence is approximately uniform.");
        } else {
            System.out.println("The sequence is not uniform.");
        }
    }

    public static double calculateMean(int[] values) {
        int sum = 0;
        for (int value : values) {
            sum += value;
        }
        return (double) sum / values.length;
    }

    public static double calculateVariance(int[] values, double mean) {
        double sumSquaredDiff = 0.0;
        for (int value : values) {
            double diff = value - mean;
            sumSquaredDiff += diff * diff;
        }
        return sumSquaredDiff / values.length;
    }
}
