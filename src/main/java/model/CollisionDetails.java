package model;

import physics.Vect;

public class CollisionDetails {
    private double tuc;
    private Vect velo;
    private Object colide;

    public CollisionDetails(double t, Vect v, Object cG) {
        tuc = t;
        velo = v;
        colide = cG;
    }

    public double getTuc() {
        return tuc;
    }

    public Vect getVelo() {
        return velo;
    }

    public Object getColiding() {
        return colide;
    }

}
