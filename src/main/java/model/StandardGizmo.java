package model;

import physics.Angle;

import java.util.ArrayList;
import java.util.List;

import physics.Circle;
import physics.LineSegment;

import javax.sound.midi.SysexMessage;

public abstract class StandardGizmo  {
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

    public List<LineSegment> getLines() { return new ArrayList<>(); };

    public List<Circle> getCircles() { return new ArrayList<>(); };

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

    public void trigger(Ball ball) {
        for(StandardGizmo gizmo : gizmos) {
            gizmo.action();
        }
    };

    public void rotate(Angle angle) {
        //DO NOTHING AS DEFAULT
    }
    
    public void action() {
        if (triggered) {
            triggered = false;
        } else {
            triggered = true;
        }
    };

    public void update(double deltaT) {
        // do nothing as default
    };
}
