package model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AbsorberTest {
    Model model;
    Absorber absorber;
    int xPos0;
    int yPos0;
    int xPos1;
    int yPos1;

    @Before
    public void setUp() throws Exception {
        model = new Model();
        xPos0 = 0;
        yPos0 = 19;
        xPos1 = 20;
        yPos1 = 20;

        absorber = new Absorber(xPos0, yPos0, xPos1, yPos1);
        model.addGizmo(absorber);
    }

    @Test
    public void testIntance() {
        assertNotNull("Absorber instantiated", absorber);
    }

    @Test
    public void testGetCollider() {
        assertEquals(0, absorber.getCollider().getCircles().size());
        assertEquals(1, absorber.getCollider().getLines().size());
    }

    @Test
    public void testSetXCoordinate() {
        absorber.setxCoordinate(1);
        assertEquals(absorber.getxCoordinate(), 1);
    }

    @Test
    public void setyCoordinate() {
        absorber.setyCoordinate(1);
        assertEquals(absorber.getyCoordinate(), 1);
    }

    @Test
    public void testGetWidth() {
        assertEquals(absorber.getWidth(), 20);
    }

    @Test
    public void testGetHeight() {
        assertEquals(absorber.getHeight(), 1);
    }

    @Test
    public void testSetFeasibleWidth() {
        assertTrue(absorber.setWidth(5));
        assertEquals(absorber.getWidth(), 5);
    }

    @Test
    public void setFeasibleHeight() {
        assertTrue(absorber.setHeight(5));
        assertEquals(absorber.getHeight(), 5);
    }

    // boundary check is handled in file parser
    @Test
    public void testSetImpossibleWidth() {
        assertFalse(absorber.setWidth(200));
    }

    // boundary check is handled in file parser
    @Test
    public void setImpossibleHeight() {
        assertFalse(absorber.setHeight(200));
    }

    @Test
    public void testSetLessThanZeroWidth() {
        assertFalse(absorber.setWidth(-1));
    }

    @Test
    public void testSetLessThanZeroHeight() {
        assertFalse(absorber.setHeight(-1));
    }

    @Test
    public void testGetLines() {
        assertEquals(absorber.getLines().size(), 1);
        assertEquals(absorber.getLines().get(0).p1().x(), xPos0, 0.001);
        assertEquals(absorber.getLines().get(0).p1().y(), yPos0, 0.001);
        assertEquals(absorber.getLines().get(0).p2().x(), xPos1, 0.001);
        assertEquals(absorber.getLines().get(0).p2().y(), yPos0, 0.001);
    }

    @Test
    public void testGetCircles() {
        assertEquals(absorber.getCircles().size(), 0);
    }

    @Test
    public void testAbsorbedBallPosition() {
        Ball ball = new Ball(0, 0, 0, 0, 0.5);
        absorber.trigger(ball);
        assertEquals(ball.getExactX(), xPos1-ball.getRadius()*2, 0.001);
        assertEquals(ball.getExactX(), yPos0+ball.getRadius()*2, 0.001);
    }

    @Test
    public void testAbsorbedBallVelocity() {
        Ball ball = new Ball(0, 0, 0, 0, 0.5);
        absorber.trigger(ball);
        assertTrue(ball.stopped());
        assertEquals(ball.getVelo().x(), 0.0,0.001);
        assertEquals(ball.getVelo().y(), 0.0,0.001);
    }

    @Test
    public void testBallVelocityOnAction() {
        Ball ball = new Ball(0, 0, 0, 0, 0.5);
        absorber.trigger(ball);
        absorber.action();
        assertFalse(ball.stopped());
        assertEquals(ball.getVelo().x(), 0,0.001);
        assertEquals(ball.getVelo().y(), -40,0.001);
    }

    @Test
    public void testIsTriggered() {
        Ball ball = new Ball(0, 0, 0, 0, 0.5);
        assertFalse(absorber.isTriggered());
        absorber.trigger(ball);
        assertTrue(absorber.isTriggered());
    }
}