package model;

import physics.Circle;
import physics.LineSegment;
import physics.Vect;

import java.util.ArrayList;
import java.util.List;

public class Walls implements Collideable{
    private int xpos1;
    private int ypos1;
    private int ypos2;
    private int xpos2;

    public Walls(int x1, int y1, int x2, int y2) {
        xpos1 = x1;
        ypos1 = y1;
        xpos2 = x2;
        ypos2 = y2;
    }

    public ArrayList<LineSegment> getLineSegments() {
        ArrayList<LineSegment> ls = new ArrayList<LineSegment>();
        LineSegment l1 = new LineSegment(xpos1, ypos1, xpos2, ypos1);
        LineSegment l2 = new LineSegment(xpos1, ypos1, xpos1, ypos2);
        LineSegment l3 = new LineSegment(xpos2, ypos1, xpos2, ypos2);
        LineSegment l4 = new LineSegment(xpos1, ypos2, xpos2, ypos2);
        ls.add(l1);
        ls.add(l2);
        ls.add(l3);
        ls.add(l4);
        return ls;
    }

    @Override
    public Collider getCollider() {
        List<LineSegment> lines = getLineSegments();
        List<Circle> circles = new ArrayList<>();
        Vect center = new Vect((xpos1 + xpos2)/2, (ypos1 + ypos2)/2);
        return new Collider(lines, circles, center, 0, Vect.ZERO);
    }

}
