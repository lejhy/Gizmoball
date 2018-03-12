package model;

import org.junit.Before;
import org.junit.Test;
import physics.Vect;

import static org.junit.Assert.*;

public class BallTest {
    Ball ball;
    Model model;
    double FPS;
    double deltaT;
    double diameter;
    double initialPosX;
    double initialPosY;

    @Before
    public void setUp() throws Exception {
        model = new Model();
        model.clear();

        FPS = 60;
        deltaT = 1/FPS;

        initialPosX = 1.0;
        initialPosY = 11.0;
        diameter = 0.5;

        ball = new Ball(initialPosX, initialPosY , 0, 0, diameter);
        model.addBall(ball);
    }

    @Test
    public void testGetCollider() {
        assertEquals(1, ball.getCollider().getCircles().size());
        assertEquals(0, ball.getCollider().getLines().size());
        assertEquals(1.0, ball.getCollider().getCenter().x(), 0.001);
        assertEquals(11.0, ball.getCollider().getCenter().y(), 0.001);
        assertEquals(0.0, ball.getCollider().getAngVelocity(), 0.001);
    }

    @Test
    public void testGetBallVeloXAfterTick() {
        assertEquals(applyForcesFormula(1, 0, 0).x(), ball.getVelo().x(), 0.001);
    }

    // TODO: PARAMETRISE THESE TESTS
    // TEST BALL Y VELOCITY IN FREE FALL

    @Test
    public void testGetBallVeloYAfterTick() {
        assertEquals(0.0, ball.getVelo().y(), 0.001);
        assertEquals(applyForcesFormula(1, 0, 0).y(), ball.getVelo().y(), 0.001);
    }

    @Test
    public void testGetBallVeloYAfterTwoTicks() {
        assertEquals(applyForcesFormula(2, 0, 0).y(), ball.getVelo().y(), 0.001);
    }

    @Test
    public void testGetBallVeloYAfterThreeTicks() {
        assertEquals(applyForcesFormula(3, 0, 0).y(), ball.getVelo().y(), 0.001);
    }

    // TEST BALL POSITION IN FREE FALL
    // POS X

    @Test
    public void getExactXAfterOneTick() {
        Vect vel = applyForcesFormula(1, 0, 0);
        assertEquals(ball.getExactX(), getPosition(initialPosX, initialPosY, vel.x(), vel.y()).x(), 0.001);
    }

    @Test
    public void getExactXAfterTwoTicks() {
        Vect vel = applyForcesFormula(2, 0, 0);
        assertEquals(ball.getExactX(), getPosition(initialPosX, initialPosY, vel.x(), vel.y()).x(), 0.001);
    }

    @Test
    public void getExactXAfterThreeTicks() {
        Vect vel = applyForcesFormula(3, 0, 0);
        assertEquals(ball.getExactX(), getPosition(initialPosX, initialPosY, vel.x(), vel.y()).x(), 0.001);
    }

    // POS Y

    @Test
    public void getExactYAfterOneTick() {
        Vect vel = applyForcesFormula(1, 0, 0);
        assertEquals(ball.getExactY(), getPosition(initialPosX, initialPosY, vel.x(), vel.y()).y(), 0.001);
    }

    @Test
    public void getExactYAfterTwoTicks() {
        Vect vel = applyForcesFormula(2, 0, 0);
        assertEquals(ball.getExactY(), getPosition(initialPosX, initialPosY, vel.x(), vel.y()).y(), 0.001);
    }

    @Test
    public void getExactYAfterThreeTicks() {
        Vect vel = applyForcesFormula(3, 0, 0);
        assertEquals(ball.getExactY(), getPosition(initialPosX, initialPosY, vel.x(), vel.y()).y(), 0.001);
    }

    @Test
    public void tesSetVelo() {
        Vect newVelocity = new Vect(2, 2);
        ball.setVelo(newVelocity);
        assertEquals(applyForcesFormula(1, newVelocity.x(), newVelocity.y()).y(), ball.getVelo().y(), 0.001);
    }

    @Test
    public void testGetRadius() {
        assertEquals(ball.getRadius(), diameter/2, 0.001);
    }

    @Test
    public void testGetCircle() {
        assertEquals(ball.getCircle().getCenter().x(), initialPosX, 0.001);
        assertEquals(ball.getCircle().getCenter().y(), initialPosY, 0.001);
        assertEquals(ball.getCircle().getRadius(), diameter/2, 0.001);
    }

    @Test
    public void tesSetExactX() {
        ball.setExactX(2);
        assertEquals(2, ball.getExactX(), 0.001);
    }

    @Test
    public void testSetExactY() {
        ball.setExactY(2);
        assertEquals(2, ball.getExactY(), 0.001);
    }

//    @Test
//    public void tesSetExactXOutOfBoundaries() {
//        // implement
//    }
//
//    @Test
//    public void testSetExactYOutOfBoundaries() {
//        // implement
//    }
//
//    @Test
//    public void start() {
//
//    }
//
//    @Test
//    public void stop() {
//    }
//
//
//
//    @Test
//    public void stopped() {
//    }
//
//    @Test
//    public void getColour() {
//    }


    public Vect applyForcesFormula(int loopCount, double velX, double velY) {
        Vect velocity = null;

        for (int i = 0; i < loopCount; i++) {
            velocity = new Vect(velX, velY + 25*deltaT);
            velocity = velocity.times(1 - (0.025 * deltaT) - (0.025 * Math.abs(velocity.length()) * deltaT));
            model.tick(FPS);
        }

        return velocity;
    }

    public Vect getPosition(double posX, double posY, double dx, double dy) {
        return new Vect(posX + dx*deltaT, posY + dy*deltaT);
    }
}