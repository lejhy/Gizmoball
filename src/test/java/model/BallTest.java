package model;

import org.junit.Before;
import org.junit.Test;
import physics.Vect;

import static org.junit.Assert.*;

public class BallTest {
    Ball ball;
    Model model;
    final double FPS = 60;
    final double deltaT = 1/FPS;
    double diameter;
    double initialPosX;
    double initialPosY;

    @Before
    public void setUp() throws Exception {
        model = new Model();
        initialPosX = 1.0;
        initialPosY = 11.0;
        diameter = 0.5;
        ball = new Ball(initialPosX, initialPosY , 0, 0, diameter);
        model.addBall(ball);
    }

    @Test
    public void testIntance() {
        assertNotNull("Ball instantiated", ball);
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

    @Test
    public void testStart() {
        ball.start();
        assertFalse(ball.stopped());
    }

    @Test
    public void testStop() {
        ball.stop();
        assertTrue(ball.stopped());
    }

    @Test
    public void testToString(){
        assertEquals(ball.toString(), "Ball B 1.0 11.0 0.0 0.0");
    }

  //  @Test
  //  public void tesSetExactXOutOfBoundaries() {
  //      ball.setExactX(0);
  //      ball.setExactX(-21);
  //      assertTrue(ball.getExactX() == 0);
  //  }
    //I see why these are commented out now
    //if the setX and setY methods are public, should they sanity check the input?
//
//    @Test
//    public void testSetExactYOutOfBoundaries() {
//        // implement
//    }
//

    @Test
    public void testTooManyBalls(){
       Model tooManyBallsModel = new Model();
       tooManyBallsModel.addGizmo(new SquareBumper(1, 2));
       for(int balls = 0; balls < 100; balls++){
           tooManyBallsModel.addBall(new Ball(1, 1, 0,0, 0.5));
       }
        for(int balls = 0; balls < 100; balls++){
       //     tooManyBallsModel.tick(60);
            //covers the branch that handles exceptional collisions
            //but how to test for it?
        }
    }

    @Test
    public void testCollideWithBall(){
        double oldX = 1;
        double oldY = 19.725;
        Model colideTest = new Model();
        Ball ball1 = new Ball(oldX, oldY, 0, 0, 0.5);
        Ball ball2 = new Ball(oldX, 18, 0, 0, 0.5);
        colideTest.addBall(ball1);
        colideTest.addBall(ball2);
        for(int x = 0; x < 20; x++){
            colideTest.tick(60);
        }
        assertTrue(ball1.getExactX() == oldX);
        assertTrue(ball2.getExactX() == oldX);
        assertTrue(ball2.getExactY() != 18);
        assertTrue(ball1.getExactY() != oldY);
        //should calculate new values and test for these
        //easy once we have the position after tick test working
    }


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