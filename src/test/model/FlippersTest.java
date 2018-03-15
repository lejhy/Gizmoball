package model;

import org.junit.Before;
import org.junit.Test;
import physics.Angle;

import static org.junit.Assert.*;

public class FlippersTest {
    Model model;
    StandardGizmo gizmo;

    @Before
    public void setUp(){
        model = new Model();
    }

    @Test
    public void testRightFlipperTrigger(){
        gizmo = new RightFlipper(12, 12);
        SquareBumper sBumper = new SquareBumper(5, 5);
        sBumper.addGizmoTrigger(gizmo);
        model.addGizmo(gizmo);
        model.addGizmo(sBumper);
        model.addBall(new Ball(5, 4, 0, 0, 0.5));
        assertEquals(gizmo.getRotation(), Angle.ZERO);
        for(int x = 0; x < 16; x++){
            model.tick(60);
        }
        assertTrue(gizmo.isTriggered());
    }

    @Test
    public void testLeftFlipperTrigger(){
        gizmo = new LeftFlipper(12, 12);
        SquareBumper sBumper = new SquareBumper(5, 5);
        sBumper.addGizmoTrigger(gizmo);
        model.addGizmo(gizmo);
        model.addGizmo(sBumper);
        model.addBall(new Ball(5, 4, 0, 0, 0.5));
        assertEquals(gizmo.getRotation(), Angle.ZERO);
        for(int x = 0; x < 16; x++){
            model.tick(60);
        }
        assertTrue(gizmo.isTriggered());
    }

    @Test
    public void testRotate(){
        gizmo = new LeftFlipper(10, 10);
        gizmo.rotate(Angle.DEG_180);
        //replace with similar trigger example from above
        assertEquals(gizmo.getRotation(), Angle.DEG_90);
        //not sure why 90 is right
        gizmo = new RightFlipper(10, 10);
        gizmo.rotate(Angle.DEG_180);
        assertEquals(gizmo.getRotation(), Angle.DEG_90);
    }
}
