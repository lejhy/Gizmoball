package view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import model.*;
import physics.Angle;
import physics.Circle;
import physics.LineSegment;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private Model model;
    private Canvas canvas;
    private GraphicsContext gc;

    private Image ballImg;
    private Image squareImg;
    private Image circleImg;
    private Image triangleImg0;
    private Image triangleImg90;
    private Image triangleImg180;
    private Image triangleImg270;
    private Image wallImg;

    private int boardSize = 800;
    private int LSize;

    public Board(Model model, Canvas canvas) {
        this.model = model;
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
        this.LSize = boardSize/model.getGridDimensions();

        ballImg = new Image(getClass().getResource("/Ball.png").toString());
        squareImg = new Image(getClass().getResource("/Square.png").toString());
        circleImg = new Image(getClass().getResource("/Circle.png").toString());
        triangleImg0 = new Image(getClass().getResource("/Triangle_0.png").toString());
        triangleImg90 = new Image(getClass().getResource("/Triangle_90.png").toString());
        triangleImg180 = new Image(getClass().getResource("/Triangle_180.png").toString());
        triangleImg270 = new Image(getClass().getResource("/Triangle_270.png").toString());
        wallImg = new Image(getClass().getResource("/Wall.png").toString());
    }

    public void paintBoard() {

        // Draw Background
        int gridDimensions = model.getGridDimensions();
        for (int i = 0; i < gridDimensions; i+= 6) {
            for (int j = 0; j < gridDimensions; j += 6) {
                gc.drawImage(wallImg, i*LSize, j*LSize, LSize*6, LSize*6);
            }
        }

        // Draw lines
        List<LineSegment> ls = model.getLines();
        if(ls.size() > 0) {
            for (LineSegment l : ls) {
                gc.setFill(Color.BLACK);
                gc.strokeLine(l.p1().x()*LSize, l.p1().y()*LSize, l.p2().x()*LSize, l.p2().y()*LSize);
            }
        }

        // Gizmos
        List<StandardGizmo> gizmos = model.getGizmos();
        for(StandardGizmo gizmo : gizmos) {
            double xCoord = gizmo.getxCoordinate()*LSize;
            double yCoord = gizmo.getyCoordinate()*LSize;
            double width = gizmo.getWidth()*LSize;
            double height = gizmo.getHeight()*LSize;
            Image image = null;
            switch (gizmo.getType()) {
                case SQUARE:
                    image = squareImg;
                    break;
                case CIRCLE:
                    image = circleImg;
                    break;
                case TRIANGLE:
                    Angle rotation = gizmo.getRotation();
                    if (gizmo.equals(Angle.ZERO)) {
                        image = triangleImg0;
                    } else if (rotation.equals(Angle.DEG_90)) {
                        image = triangleImg90;
                    } else if (rotation.equals(Angle.DEG_180)) {
                        image = triangleImg180;
                    } else if (rotation.equals(Angle.DEG_270)) {
                        image = triangleImg270;
                    } else {
                        image = triangleImg0;
                    }
                    break;
                case LEFT_FLIPPER:

                    break;
                case RIGHT_FLIPPER:

                    break;
                case WALL:

                    break;

                case ABSORBER:

                    break;
                default:
            }
            gc.drawImage(image, xCoord, yCoord, width, height);
        }

        // Draw ballImg
        Ball b = model.getBall();
        if (b != null) {
            double x = (b.getExactX() - b.getRadius());
            double y = (b.getExactY() - b.getRadius());
            double width = (2 * b.getRadius());
            //gc.setFill(b.getColour());
            //gc.fillOval(x, y, width, width);
            gc.drawImage(ballImg, x*LSize, y*LSize, width*LSize, width*LSize);
        }

        //paintGrid();

    }

    private void paintGrid() {
        int p1x = 0;
        int p1y = 0;
        int p2x = 0;
        int p2y = 0;

        for(int i = 0; i < 20; i++) {
            gc.strokeLine(p1x + i*40, p1y, p2x + i*40, 800);
        }

        for(int i = 0; i < 20; i++) {
            gc.strokeLine(p1x, p1y + i*40, 800, p2y + i*40);
        }
    }
}