package view;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import model.*;
import physics.Angle;
import physics.Circle;
import physics.LineSegment;

import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.List;

public class Board {
    private Model model;
    private Canvas canvas;
    private GraphicsContext gc;

    private Image ballImg;
    private Image squareImg;
    private Image squareTriggeredImg;
    private Image circleImg;
    private Image circleTriggeredImg;
    private Image triangleImg0;
    private Image triangleTriggeredImg0;
    private Image triangleImg90;
    private Image triangleTriggeredImg90;
    private Image triangleImg180;
    private Image triangleTriggeredImg180;
    private Image triangleImg270;
    private Image triangleTriggeredImg270;
    private List<Image> leftFlipperImg;
    private List<Image> rightFlipperImg;
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
        squareTriggeredImg = new Image(getClass().getResource("/Square_triggered.png").toString());
        circleImg = new Image(getClass().getResource("/Circle.png").toString());
        circleTriggeredImg = new Image(getClass().getResource("/Circle_triggered.png").toString());
        triangleImg0 = new Image(getClass().getResource("/Triangle_0.png").toString());
        triangleTriggeredImg0 = new Image(getClass().getResource("/Triangle_0_triggered.png").toString());
        triangleImg90 = new Image(getClass().getResource("/Triangle_90.png").toString());
        triangleTriggeredImg90 = new Image(getClass().getResource("/Triangle_90_triggered.png").toString());
        triangleImg180 = new Image(getClass().getResource("/Triangle_180.png").toString());
        triangleTriggeredImg180 = new Image(getClass().getResource("/Triangle_180_triggered.png").toString());
        triangleImg270 = new Image(getClass().getResource("/Triangle_270.png").toString());
        triangleTriggeredImg270 = new Image(getClass().getResource("/Triangle_270_triggered.png").toString());
        leftFlipperImg = new ArrayList<>();
        rightFlipperImg = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            leftFlipperImg.add(new Image(getClass().getResource("/LeftFlipper_" + String.format("%02d", i) + ".png").toString()));
            rightFlipperImg.add(new Image(getClass().getResource("/RightFlipper_" + String.format("%02d", i) + ".png").toString()));
        }
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
                    if (gizmo.isTriggered()) image = squareTriggeredImg;
                    else image = squareImg;
                    break;
                case CIRCLE:
                    if (gizmo.isTriggered()) image = circleTriggeredImg;
                    else image = circleImg;
                    break;
                case TRIANGLE:
                    Angle rotation = gizmo.getRotation();
                    if (gizmo.equals(Angle.ZERO)) {
                        if (gizmo.isTriggered()) image = triangleTriggeredImg0;
                        else image = triangleImg0;
                    } else if (rotation.equals(Angle.DEG_90)) {
                        if (gizmo.isTriggered()) image = triangleTriggeredImg90;
                        else image = triangleImg90;
                    } else if (rotation.equals(Angle.DEG_180)) {
                        if (gizmo.isTriggered()) image = triangleTriggeredImg180;
                        else image = triangleImg180;
                    } else if (rotation.equals(Angle.DEG_270)) {
                        if (gizmo.isTriggered()) image = triangleTriggeredImg270;
                        else image = triangleImg270;
                    } else {
                        if (gizmo.isTriggered()) image = triangleTriggeredImg0;
                        else image = triangleImg0;
                    }
                    break;
                case LEFT_FLIPPER:
                    int leftFlipperImgNumber = (int) Math.round(gizmo.getRotation().radians()*2/Math.PI * 14);
                    image = leftFlipperImg.get(leftFlipperImgNumber);
                    break;
                case RIGHT_FLIPPER:
                    int rightFlipperImgNumber = (int) Math.round(gizmo.getRotation().radians()*2/Math.PI * 14);
                    image = rightFlipperImg.get(rightFlipperImgNumber);
                    break;
                case WALL:
                    //TODO Could add a semi transparent image with ambient occlusion around the edges
                    break;
                case ABSORBER:
                    //TODO Image will probably be deformed for non-default absorber sizes... Possibly could be made out od LxL tiles?
                    break;
                default:
            }
            gc.drawImage(image, xCoord, yCoord, width, height);
        }

        // Draw ballImg
        for (Ball ball : model.getBalls()) {
            if (ball != null) {
                double x = (ball.getExactX() - ball.getRadius());
                double y = (ball.getExactY() - ball.getRadius());
                double width = (2 * ball.getRadius());
                //gc.setFill(b.getColour());
                //gc.fillOval(x, y, width, width);
                gc.drawImage(ballImg, x * LSize, y * LSize, width * LSize, width * LSize);
            }
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

    public void addMouseEventHandler(EventHandler<MouseEvent> e) {
        canvas.addEventHandler(MouseEvent.ANY, e);
    }

    public void removeMouseEventHandler(EventHandler<MouseEvent> e) {
        if (e!=null) {
            canvas.removeEventHandler(MouseEvent.ANY, e);
        }
    }

    public double getLPos (double x) {
        return x/LSize;
    }
}