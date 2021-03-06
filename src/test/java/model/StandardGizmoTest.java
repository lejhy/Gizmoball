package model;

import org.junit.Before;
import org.junit.Test;
import physics.Angle;
import physics.Vect;

import static org.junit.Assert.*;


public class StandardGizmoTest {
    Model model;
    StandardGizmo gizmo;

    @Before
    public void setUp(){
        model = new Model();
        gizmo = new TriangularBumper(0, 0);
    }

    @Test
    public void testGetTriggersEmpty(){
        assertEquals(gizmo.getTriggers().size(), 0);
    }

    @Test
    public void testGetTriggers(){
        gizmo.addGizmoTrigger(new CircularBumper(0,0));
        assertEquals(gizmo.getTriggers().size(), 1);
    }



    @Test
    public void testGetX(){
        assertEquals(gizmo.getxCoordinate(), 0);
    }

    @Test
    public void testSetX(){
        gizmo.setxCoordinate(1);
        assertEquals(gizmo.getxCoordinate(), 1);
    }

    @Test
    public void testGetY(){
        assertEquals(gizmo.getyCoordinate(), 0);
    }

    @Test
    public void testSetY(){
        gizmo.setyCoordinate(1);
        assertEquals(gizmo.getyCoordinate(), 1);
    }

    @Test
    public void testGetRotation(){
        assertEquals(gizmo.getRotation(), Angle.ZERO);
    }

    @Test
    public void testRotate(){
        gizmo.rotate(Angle.DEG_90);
        assertEquals(gizmo.getRotation(), Angle.DEG_90);
        gizmo.rotate(Angle.DEG_90);
        assertEquals(gizmo.getRotation(), Angle.DEG_180);
        SquareBumper squareBumper = new SquareBumper(4, 18);
        squareBumper.rotate(Angle.DEG_90);
        assertEquals(squareBumper.getRotation(), Angle.ZERO);
    }

    @Test
    public void testGetType(){
        assertEquals(gizmo.getType(), StandardGizmo.Type.TRIANGLE);
    }

    @Test
    public void testIsRotating(){
        assertFalse(gizmo.isRotating());
    }

    @Test
    public void testGetEdgeLength(){
        assertTrue(gizmo.getEdgeLength() == 1);
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
        assertTrue(gizmo.getCollider().center.equals(new Vect(gizmo.x + gizmo.edgeLength/2, gizmo.y + gizmo.edgeLength/2)));
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
    public void testToString(){
        assertEquals(gizmo.toString(0), "Triangle T0 0 0");
    }

    @Test
    public void testColiderVelocity(){
        assertEquals(gizmo.getCollider().getVelocity(), Vect.ZERO);
    }

    @Test
    public void testCollision(){
        model.addGizmo(new SquareBumper(5, 5));
        model.addBall(new Ball(4.5, 4.5, 1, 1, 0.5));
        //model.tick(60);

    }

}
