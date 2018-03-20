package view;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import model.*;
import physics.Angle;

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
    private List<Image> flipperImg;
    private Image wallImg;
    private Image absorberImg;

    private int boardSize = 800;
    private int LSize;

    private boolean paintGrid = true;

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
        flipperImg = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            flipperImg.add(new Image(getClass().getResource("/Flipper_" + String.format("%02d", i) + ".png").toString()));
        }
        wallImg = new Image(getClass().getResource("/Wall.png").toString());
        absorberImg = new Image(getClass().getResource("/Absorber.png").toString());
    }

    public void setPaintGrid(boolean paintGrid){
        this.paintGrid = paintGrid;
    }

    public void paintBoard() {

        // Draw Background
        int gridDimensions = model.getGridDimensions();
        for (int i = 0; i < gridDimensions; i+= 6) {
            for (int j = 0; j < gridDimensions; j += 6) {
                gc.drawImage(wallImg, i*LSize, j*LSize, LSize*6, LSize*6);
            }
        }
        if(paintGrid){
            paintGrid();
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
                    if (rotation.equals(Angle.ZERO)) {
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
                    int leftFlipperRotationOffset = 59 + (int) Math.round(gizmo.getRotation().radians()*2/Math.PI * 15);
                    if (leftFlipperRotationOffset > 59) leftFlipperRotationOffset = leftFlipperRotationOffset - 60;
                    int leftFlipperRotation = (int) Math.round(((LeftFlipper)gizmo).getFlipperRotation().radians()*2/Math.PI * 14);
                    int leftFlipperImgNumber = leftFlipperRotationOffset + leftFlipperRotation;
                    image = flipperImg.get(leftFlipperImgNumber);
                    break;
                case RIGHT_FLIPPER:
                    int rightFlipperRotationOffset = (int) Math.round(gizmo.getRotation().radians()*2/Math.PI * 15);
                    if (rightFlipperRotationOffset < 0) rightFlipperRotationOffset = 60 + rightFlipperRotationOffset;
                    int rightFlipperRotation = (int) Math.round(((RightFlipper)gizmo).getFlipperRotation().radians()*2/Math.PI * 14);
                    int rightFlipperImgNumber = rightFlipperRotationOffset + rightFlipperRotation;
                    image = flipperImg.get(rightFlipperImgNumber);
                    break;
                case WALL:
                    //TODO Could add a semi transparent image with ambient occlusion around the edges
                    break;
                case ABSORBER:
                    image = absorberImg;
                    int absorberHeight = gizmo.getHeight();
                    int absorberWidth = gizmo.getWidth();
                    for (int i = 0; i < absorberWidth; i++) {
                        for (int j = 0; j < absorberHeight; j++) {
                            int xOffset = i * LSize;
                            int yOffset = j * LSize;
                            gc.drawImage(image, xCoord + xOffset, yCoord + yOffset, LSize, LSize);
                        }
                    }
                    width = LSize;
                    height = LSize;
                    xCoord = (gizmo.getxCoordinate() + gizmo.getWidth() - 1) * LSize;
                    yCoord = gizmo.getyCoordinate() * LSize;
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