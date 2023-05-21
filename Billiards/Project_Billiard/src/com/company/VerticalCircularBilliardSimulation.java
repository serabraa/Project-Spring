package com.company;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
//I guess this one is not working proper
public class VerticalCircularBilliardSimulation {
    public static void main(String[] args) {
        double radius = 1.0;
        double g = 10.0;
        int numReflections = 5;

        Random random = new Random();
        double x0 = -radius + 2 * radius * random.nextDouble();
        double y0 = Math.sqrt(radius * radius - x0 * x0);

        double minMagnitude = 5.0;
        double maxMagnitude = 10.0;
        double magnitude = minMagnitude + (maxMagnitude - minMagnitude) * random.nextDouble();
        double angle = 2 * Math.PI * random.nextDouble();
        double px0 = magnitude * Math.cos(angle);
        double py0 = magnitude * Math.sin(angle);

        System.out.println("Initial position: (" + x0 + ", " + y0 + ")");
        System.out.println("Initial momentum: (" + px0 + ", " + py0 + ")");

        double x = x0;
        double y = y0;
        double px = px0;
        double py = py0;

        List<double[]> reflectionPoints = new ArrayList<>();

        for (int i = 0; i < numReflections; i++) {

            double a = px * px + py * py - g * y;
            double b = 2 * (x * px + y * py);
            double c = x * x + y * y - radius * radius;
            double discriminant = b * b - 4 * a * c;

            double t;
            if (discriminant >= 0) {
                double t1 = (-b + Math.sqrt(discriminant)) / (2 * a);
                double t2 = (-b - Math.sqrt(discriminant)) / (2 * a);
                t = Math.max(t1, t2);
            } else {
                t = 0;
            }

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

            py -= g;
        }

        System.out.println("Reflection points:");
        for (int i = 0; i < reflectionPoints.size(); i++) {
            double[] point = reflectionPoints.get(i);
            System.out.println("Reflection point " + (i + 1) + ": (" + point[0] + ", " + point[1] + ")");
        }

        boolean isReversible = true;
        double delta = 0.001;

        for (int i = numReflections - 1; i >= 0; i--) {
            double[] point = reflectionPoints.get(i);
            double pxReversed = (point[1] * point[1] - point[0] * point[0]) * px - 2 * point[0] * point[1] * py;
            double pyReversed = -2 * point[0] * point[1] * px + (point[0] * point[0] - point[1] * point[1]) * py;

            px = pxReversed;
            py = pyReversed;

            py -= g;

            double a = px * px + py * py - g * point[1];
            double b = 2 * (point[0] * px + point[1] * py);
            double c = point[0] * point[0] + point[1] * point[1] - radius * radius;
            double discriminant = b * b - 4 * a * c;

            double t;
            if (discriminant >= 0) {
                double t1 = (-b + Math.sqrt(discriminant)) / (2 * a);
                double t2 = (-b - Math.sqrt(discriminant)) / (2 * a);
                t = Math.max(t1, t2);
            } else {
                t = 0;
            }

            double nextX = point[0] + t * px;
            double nextY = point[1] + t * py;

            if (Math.abs(nextX - x0) > delta || Math.abs(nextY - y0) > delta) {
                isReversible = false;
                System.out.println("The paths deviate after " + (numReflections - i) + " reflections.");
                break;
            }
        }

        if (isReversible) {
            System.out.println("The paths coincide.");
        }
    }
}
