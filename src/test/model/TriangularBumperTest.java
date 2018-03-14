package model;

import org.junit.Before;
import org.junit.Test;
import physics.Angle;
import physics.Vect;

import static org.junit.Assert.*;


public class TriangularBumperTest {
    Model model;
    StandardGizmo gizmo;

    @Before
    public void setUp(){
        model = new Model();
        gizmo = new TriangularBumper(0, 0);
    }

    @Test
    public void testGetX(){
        assertEquals(gizmo.getxCoordinate(), 0);
    }

    @Test
    public void testGetY(){
        assertEquals(gizmo.getyCoordinate(), 0);
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
        assertEquals(gizmo.getCollider().lines.size(), 3 );
    }

    @Test
    public void testGetCircles(){
        assertEquals(gizmo.getCollider().circles.size(), 3);
    }

    @Test
    public void testGetCenter(){
        assertTrue(gizmo.getCollider().center.equals(new Vect(0.5, 0.5)));
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
    public void testRotate90Deg(){
        gizmo.rotate(Angle.DEG_90);
        assertEquals(gizmo.getRotation(), Angle.DEG_90);

    }

    @Test
    public void testRotate45Deg(){
        gizmo.rotate(Angle.DEG_45);
        assertNotEquals(gizmo.getRotation(), Angle.DEG_45);
    }

    @Test
    public void testToString(){
        assertEquals(gizmo.toString(0), "Triangle T0 0 0");
    }

}
