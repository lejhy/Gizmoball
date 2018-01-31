package model;

import java.util.ArrayList;
import java.util.List;

public abstract class StandardGizmo  {
    protected double x; // X coordinates of upper left corner
    protected double y; // Y coordinates of upper left corner
    protected Model model; // reference to the model
    protected double edgeLength; // edgeLength
    protected List<StandardGizmo> gizmos;

    public StandardGizmo(int Lx, int Ly, Model model) {
        this.model = model;
        this.edgeLength = 1;
        x = Lx;
        y = Ly;
        gizmos = new ArrayList<>();
    }

    public abstract void addGizmo();

    public double getxCoordinate() {
        return x;
    }

    public double getyCoordinate() {
        return y;
    }

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

    public abstract void trigger();
}
