package model;

import physics.Angle;

import java.util.ArrayList;
import java.util.List;

import physics.Circle;
import physics.LineSegment;

import javax.sound.midi.SysexMessage;
import javax.sound.sampled.Line;

public abstract class StandardGizmo implements Collideable {
    protected int x; // X coordinates of upper left corner
    protected int y; // Y coordinates of upper left corner
    protected Angle rotation;
    protected int edgeLength; // edgeLength
    protected List<StandardGizmo> gizmos;
    protected boolean triggered;
    protected Type type;

    public abstract String toString(int i);

    public enum Type {
        SQUARE, CIRCLE, TRIANGLE, ABSORBER, LEFT_FLIPPER, RIGHT_FLIPPER, WALL
    };


    public StandardGizmo(int Lx, int Ly, Type type) {
        this.edgeLength = 1;
        triggered = false;
        x = Lx;
        y = Ly;
        rotation = Angle.ZERO;
        this.type = type;
        gizmos = new ArrayList<>();
    }

    public int getxCoordinate() {
        return x;
    }

    public void setxCoordinate(int x) { this.x = x; }

    public int getyCoordinate() {
        return y;
    }

    public void setyCoordinate(int y) { this.y = y; }

    public int getWidth() { return edgeLength; }

    public int getHeight() {return edgeLength; }

    public Angle getRotation() { return rotation; }

    public Type getType() { return type; }

    public boolean isTriggered() { return triggered; }

    public boolean isRotating() { return false; }

    public double getEdgeLength() {
        return edgeLength;
    }

    public abstract Collider getCollider();

    public boolean addGizmoTrigger(StandardGizmo gizmo) {
        if(!gizmo.equals(this)) {
            return gizmos.add(gizmo);
        }
        return false;
    }

    public boolean removeGizmoTrigger(StandardGizmo gizmo) {
        return gizmos.remove(gizmo);
    }

    public void trigger(Ball ball) {
        System.out.println(" this: " + this.getType());
        for(StandardGizmo gizmo : gizmos) {
            gizmo.action();
            System.out.println(gizmo + " " + gizmo.isTriggered());
        }
    };

    public void rotate(Angle angle) {
        //DO NOTHING AS DEFAULT
    }
    
    public void action() {
        triggered = !triggered;
    };

    public void update(double deltaT) {
        // do nothing as default
    };
}
