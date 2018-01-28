package model;

import physics.LineSegment;

public class HorizontalLine {
    private int xpos;
    private int ypos;
    private int width;
    private LineSegment ls;

    public HorizontalLine(int x, int y, int w) {
        xpos = x;
        ypos = y;
        width = w;
        ls = new LineSegment(x, y, x + w, y);
    }

    public LineSegment getLineSeg() {
        return ls;
    }

    public int getX() {
        return xpos;
    }

    public int getY() {
        return ypos;
    }

    public int getWidth() {
        return width;
    }

}
