package model;

public abstract class StandardGizmo  {
    protected int x; // X coordinates of upper left corner
    protected int y; // Y coordinates of upper left corner
    protected Model model; // reference to the model
    protected int edgeLength; // edgeLength

    public StandardGizmo(int Lx, int Ly, Model model) {
        this.model = model;
        this.edgeLength = model.getL();
        x = edgeLength * Lx;
        y = edgeLength * Ly;
    }

    public abstract void addGizmo();

    public int getxCoordinate() {
        return x;
    }

    public int getyCoordinate() {
        return y;
    }

    public int getEdgeLength() {
        return edgeLength;
    }
}
