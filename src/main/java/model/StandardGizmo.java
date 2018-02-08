package model;

import physics.Angle;

import java.util.ArrayList;
import java.util.List;

import physics.Circle;
import physics.LineSegment;

import javax.sound.midi.SysexMessage;

public abstract class StandardGizmo  {
    protected double x; // X coordinates of upper left corner
    protected double y; // Y coordinates of upper left corner
    protected Angle rotation;
    protected double edgeLength; // edgeLength
    protected List<StandardGizmo> gizmos;
    protected Type type;
    public enum Type {
        SQUARE, CIRCLE, TRIANGLE, ABSORBER, LEFT_FLIPPER, RIGHT_FLIPPER, WALL
    };


    public StandardGizmo(int Lx, int Ly, Type type) {
        this.edgeLength = 1;
        x = Lx;
        y = Ly;
        rotation = Angle.ZERO;
        this.type = type;
        gizmos = new ArrayList<>();
    }

    public abstract List<LineSegment> getLines();

    public abstract List<Circle> getCircles();

    public double getxCoordinate() {
        return x;
    }

    public double getyCoordinate() {
        return y;
    }

    public double getWidth() { return edgeLength; }

    public double getHeight() {return edgeLength; }

    public Angle getRotation() { return rotation; }

    public Type getType() { return type; }

    public double getEdgeLength() {
        return edgeLength;
    }

    public void addGizmoTrigger(StandardGizmo gizmo) {
        if(!gizmo.equals(this)) {
            gizmos.add(gizmo);
        }
    }

    public void removeGizmoTrigger(StandardGizmo gizmo) {
        gizmos.remove(gizmo);
    }

    public void trigger() {
        for(StandardGizmo gizmo : gizmos) {
            gizmo.action();
        }
    };

    public void rotate() {
        //DO NOTHING AS DEFAULT
    }
    
    public void action() {
        System.out.println ("Standard gizmo triggered. x: " + x + " y: " + y);
    }
}
