package model;

import physics.Vect;

public class CollisionDetails {
    private double tuc;
    private Vect velo;
    private StandardGizmo colide;

    public CollisionDetails(double t, Vect v, StandardGizmo cG) {
        tuc = t;
        velo = v;
        colide = cG;
    }

    public double getTimeUntilCollision() {
        return tuc;
    }

    public Vect getVelo() {
        return velo;
    }

    public StandardGizmo getColiding() {
        return colide;
    }

}
