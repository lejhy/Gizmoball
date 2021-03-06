package model;

import org.junit.Before;
import org.junit.Test;
import physics.Vect;

import static org.junit.Assert.*;


public class CircularBumperTest {
    Model model;
    StandardGizmo gizmo;

    @Before
    public void setUp(){
        model = new Model();
        gizmo = new CircularBumper(2, 2);
    }

    @Test
    public void testGetX(){
        assertEquals(gizmo.getxCoordinate(), 2);
    }

    @Test
    public void testGetY(){
        assertEquals(gizmo.getyCoordinate(), 2);
    }

    @Test
    public void testGetWidth(){
        assertEquals(gizmo.getWidth(), 1);
    }

    @Test
    public void testGetHeight(){
        assertEquals(gizmo.getHeight(), 1);
    }

    @Test
    public void testGetLines(){
        assertEquals(gizmo.getCollider().lines.size(), 0);
    }

    @Test
    public void testGetCircles(){
        assertEquals(gizmo.getCollider().circles.size(), 1);
    }

    @Test
    public void testGetCenter(){
        assertEquals(gizmo.getCollider().center, (new Vect(2 + 1/2, 2 + 1/2)));
    }

    @Test
    public void testAngularVelocity(){
        assertTrue(gizmo.getCollider().angVelocity == 0);
    }

    @Test
    public void testAction(){
        gizmo.trigger(new Ball(0, 0, 0, 0, 0.5));
        gizmo.action();
        assertFalse(gizmo.isTriggered());
    }

    @Test
    public void testTrigger(){
        model.addGizmo(gizmo);
        model.addBall(new Ball(2, 1, 0, 0, 0.5));
        while(!gizmo.isTriggered()){
            model.tick(60);
        }

        assertTrue(gizmo.isTriggered());
    }

    @Test
    public void testToString(){
        assertEquals(gizmo.toString(0), "Circle C0 2 2");
    }

}
