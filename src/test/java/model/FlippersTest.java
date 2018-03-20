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

        assertEquals(((RightFlipper)gizmo).getFlipperRotation(), Angle.ZERO);

        for(int x = 0; x < 16; x++){
            model.tick(60);
        }

        assertTrue(((RightFlipper)gizmo).getFlipperRotation().compareTo(Angle.ZERO) > 0);
        assertTrue(gizmo.isTriggered());

        for(int x = 0; x < 33; x++){//no of ticks to hit gizmo again
            model.tick(60);
        }

        assertEquals(((RightFlipper)gizmo).getFlipperRotation().compareTo(Angle.ZERO) , 0);
        assertTrue(!gizmo.isTriggered());
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

        for(int x = 0; x < 16; x++){//no of hits to hit gizmo
            model.tick(60);
        }

        assertEquals(((LeftFlipper)gizmo).getFlipperRotation().compareTo(Angle.ZERO), 1);
        assertTrue(gizmo.isTriggered());

        for(int x = 0; x < 33; x++){//no of ticks to hit gizmo again
            model.tick(60);
        }

        assertEquals(((LeftFlipper)gizmo).getFlipperRotation().compareTo(Angle.ZERO) , 0);
        assertTrue(!gizmo.isTriggered());
    }

    @Test
    public void testFlipperRotate(){
        gizmo = new LeftFlipper(10, 10);
        assertEquals(gizmo.getRotation(), Angle.ZERO);
        gizmo.rotate(Angle.DEG_180);
        assertEquals(gizmo.getRotation(), Angle.DEG_180);


        gizmo = new RightFlipper(10, 10);
        assertEquals(gizmo.getRotation(), Angle.ZERO);
        gizmo.rotate(Angle.DEG_180);
        assertEquals(gizmo.getRotation(), Angle.DEG_180);
    }

    @Test
    public void testLeftFlipperCollision(){
        model.clear();

        gizmo = new LeftFlipper(15, 18);

        model.addGizmo(gizmo);
        model.addBall(new Ball(16, 17, 0, 0, 0.5));

        for(int x = 0; x< 29; x++){
            model.tick(60);
        }

        assertTrue(model.getBalls().get(0).getExactX() == 16);
        assertTrue(model.getBalls().get(0).getExactY() > 18);

        gizmo.action();
        assertTrue(gizmo.isTriggered());

        model.tick(60);
        model.tick(60);
        model.tick(60);
        model.tick(60);
        
        assertNotEquals((int)model.getBalls().get(0).getExactX() , 16);

    }

    @Test
    public void testRightFlipperCollision(){
        model.clear();

        gizmo = new RightFlipper(15, 18);

        model.addGizmo(gizmo);
        model.addBall(new Ball(16, 17, 0, 0, 0.5));

        for(int x = 0; x< 29; x++){
            model.tick(60);
        }

        assertTrue(model.getBalls().get(0).getExactX() == 16);
        assertTrue(model.getBalls().get(0).getExactY() > 18);

        gizmo.action();
        assertTrue(gizmo.isTriggered());

        model.tick(60);
        model.tick(60);
        model.tick(60);

        assertNotEquals((int)model.getBalls().get(0).getExactX() , 16);

    }

}
