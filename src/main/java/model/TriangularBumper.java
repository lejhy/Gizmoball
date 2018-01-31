package model;

import physics.Circle;
import physics.LineSegment;
import physics.Vect;

public class TriangularBumper extends StandardGizmo {
    private Vect topCorner;
    private Vect leftCorner;
    private Vect rightCorner;
    private Model model;
    private int testX, testY;

    public TriangularBumper(int xCoordinate, int yCoordinate, Model model) {
        super(xCoordinate, yCoordinate, model);
        this.testX = xCoordinate;
        this.testY = yCoordinate;
        this.model = model;
        topCorner = new Vect(x + edgeLength/2, y);
        leftCorner = new Vect(x, y + edgeLength);
        rightCorner = new Vect(x + edgeLength, y + edgeLength);
        addGizmo();
    }

    @Override
    public void addGizmo() {
        // Add circles at the ends of lines
        model.addCircle(new Circle(topCorner.x(), topCorner.y(), 0), this); // top corner
        model.addCircle(new Circle(leftCorner.x(),leftCorner.y(), 0), this); // left corner
        model.addCircle(new Circle(rightCorner.x(), rightCorner.y(), 0), this); // right corner

        // Add base line
        model.addLine(new LineSegment(x, y + edgeLength, x + edgeLength, y + edgeLength), this);

        // Add inclined lines
        model.addLine(new LineSegment(x + edgeLength/2, y, x, y + edgeLength), this); // connect left corner to peak
        model.addLine(new LineSegment(x + edgeLength/2, y, x + edgeLength, y + edgeLength), this); // connect right corner to peak

        model.addTriangle(this);
    }

    @Override
    public void trigger() {
        //Uncomment below for graphical trigger demonstration
        /*
        model.addCircle(new Circle(x, y, 0), this); // top left corner
        model.addCircle(new Circle(x + edgeLength, y, 0), this); // top right corner
        model.addCircle(new Circle(x,y + edgeLength, 0), this); // bottom left corner
        model.addCircle(new Circle(x + edgeLength, y + edgeLength, 0), this); // bottom right corner

        // Add horizontal lines
        model.addLine(new LineSegment(x, y, x + edgeLength, y), this); // connect top corners
        model.addLine(new LineSegment(x, y + edgeLength, x + edgeLength, y + edgeLength), this); // connect bottom corners

        // Add vertical lines
        model.addLine(new LineSegment(x, y, x, y+ edgeLength), this); // connect left corners
        model.addLine(new LineSegment(x + edgeLength, y, x + edgeLength, y + edgeLength), this); // connect right corners

        // Add square to fill with colour
        model.addSquare(new SquareBumper(testX, testY, model));
        */
        System.out.println("Triangle triggered");
        System.out.println("sorry");
        System.out.println("i couldnt help myself");
        System.out.println("go comment out line 66 in triangle class");
        System.exit(0);
    }

    public Vect getTopCorner() {
        return topCorner;
    }

    public Vect getLeftCorner() {
        return leftCorner;
    }

    public Vect getRightCorner() {
        return rightCorner;
    }

    public void rotate() {
        /* TODO: transformation */
    }
}
