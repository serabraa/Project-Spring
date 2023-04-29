package com.company;


public class Spring {
    private double k;

    public Spring() {
        this.k = 1.0;
    }

    public Spring(double k) {
        this.k = k;
    }

    public double getStiffness() {
        return k;
    }

    private void setStiffness(double k) {
        this.k = k;
    }
    public double[] move(double t, double dt, double x0, double v0) {
        int steps = (int) (t / dt);
        double[] coordinates = new double[steps + 1];
        double x = x0;
        double v = v0;
        double omega = Math.sqrt(k);
        for (int i = 0; i <= steps; i++) {
            double time = i * dt;
            x = x0 * Math.cos(omega * time) + v0 / omega * Math.sin(omega * time);
            coordinates[i] = x;
        }
        return coordinates;
    }
    public double[] move(double t, double dt, double x0) {
        int steps = (int) (t / dt);
        double[] coordinates = new double[steps + 1];
        double x = x0;
        double v = 0.0;
        double omega = Math.sqrt(k);
        for (int i = 0; i <= steps; i++) {
            double time = i * dt;
            x = x0 * Math.cos(omega * time);
            coordinates[i] = x;
        }
        return coordinates;
    }
    public double[] move(double t0, double t1, double dt, double x0, double v0) {
        int steps = (int) ((t1 - t0) / dt);
        double[] coordinates = new double[steps + 1];
        double x = x0;
        double v = v0;
        double omega = Math.sqrt(k);
        for (int i = 0; i <= steps; i++) {
            double time = t0 + i * dt;
            x = x0 * Math.cos(omega * (time - t0)) + v0 / omega * Math.sin(omega * (time - t0));
            coordinates[i] = x;
        }
        return coordinates;
    }
    public double[] move(double t0, double t1, double dt, double x0, double v0, double m) {
        int steps = (int) ((t1 - t0) / dt);
        double[] coordinates = new double[steps + 1];
        double x = x0;
        double v = v0;
        double omega = Math.sqrt(k / m);
        for (int i = 0; i <= steps; i++) {
            double time = t0 + i * dt;
            x = x0 * Math.cos(omega * (time - t0)) + v0 / omega * Math.sin(omega * (time - t0));
            coordinates[i] = x;
        }
        return coordinates;
    }

    public Spring inSeries(Spring that) {
        double equivalentK = (that.getStiffness() * this.k) / (that.getStiffness() + this.k);
        return new Spring(equivalentK);
    }
    public Spring inParallel(Spring that) {
        double equivalentK = this.k + that.getStiffness();
        return new Spring(equivalentK);
    }
}
