package com.company;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CircularBilliardSimulation {
    public static void main(String[] args) {
        // Constants
        double radius = 1.0;
        double initialVelocity = 1.0;
        int numReflections = 5;
        double delta = 0.001;

        Random random = new Random();
        double x0 = -radius + 2 * radius * random.nextDouble();
        double y0 = Math.sqrt(radius * radius - x0 * x0);

        double angle = 2 * Math.PI * random.nextDouble();
        double px0 = initialVelocity * Math.cos(angle);
        double py0 = initialVelocity * Math.sin(angle);

        System.out.println("Initial position: (" + x0 + ", " + y0 + ")");
        System.out.println("Initial momentum: (" + px0 + ", " + py0 + ")");

        double x = x0;
        double y = y0;
        double px = px0;
        double py = py0;

        List<double[]> reflectionPoints = new ArrayList<>();

        for (int i = 0; i < numReflections; i++) {
            double t = (-x * px - y * py + Math.sqrt((x * px + y * py) * (x * px + y * py) - (px * px + py * py) * (x * x + y * y - radius * radius))) / (px * px + py * py);
            double nextX = x + t * px;
            double nextY = y + t * py;

            reflectionPoints.add(new double[]{nextX, nextY});

            x = nextX;
            y = nextY;

            double tangentAngle = Math.atan2(y, x);
            double reflectionAngle = 2 * tangentAngle - Math.atan2(py, px);

            double pxPrime = (y * y - x * x) * px - 2 * x * y * py;
            double pyPrime = -2 * x * y * px + (x * x - y * y) * py;

            px = pxPrime;
            py = pyPrime;
        }

        double reversedPX = -px;
        double reversedPY = -py;

        x = x0;
        y = y0;

        List<double[]> reversedPath = new ArrayList<>();
        reversedPath.add(new double[]{x, y});

        for (int i = numReflections - 1; i >= 0; i--) {
            double[] reflectionPoint = reflectionPoints.get(i);

            x -= reversedPX;
            y -= reversedPY;

            double deviation = Math.sqrt((x - reflectionPoint[0]) * (x - reflectionPoint[0]) + (y - reflectionPoint[1]) * (y - reflectionPoint[1]));

            reversedPath.add(new double[]{x, y});

            if (deviation > delta) {
                System.out.println("Paths deviate after " + (i + 1) + " reflections.");
                break;
            }
        }

        for (int i = 0; i < reflectionPoints.size(); i++) {
            double[] point = reflectionPoints.get(i);
            System.out.println("Reflection point " + (i + 1) + ": (" + point[0] + ", " + point[1] + ")");
        }

        System.out.println("\nReversed Path:");
        for (int i = 0; i < reversedPath.size(); i++) {
            double[] point = reversedPath.get(i);
            System.out.println("Point " + (i + 1) + ": (" + point[0] + ", " + point[1] + ")");
        }
    }
}
