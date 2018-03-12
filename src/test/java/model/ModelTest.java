package model;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.Assert.*;

public class ModelTest {
    Model model;
    double FPS;
    double deltaT;

    @Before
    public void setUp() throws Exception {
        model = new Model();
        model.setFilePath("src/saveFile");
        model.loadFromFile();
        FPS = 60;
        deltaT = 1/FPS;
    }

    @Test
    public void testModel() {
        assertNotNull("Model instantiated", model);
    }

    @Test
    public void testClear() {
        model.clear();
        assertEquals(0, model.getBalls().size());
        assertEquals(0, model.getGizmos().size());
    }

    @Test
    public void testInitialBallPosition() {
        assertEquals(1.00, model.getBalls().get(0).getExactX(), 0.001);
        assertEquals(11.0, model.getBalls().get(0).getExactY(), 0.001);
    }

    @Test
    public void testInitialBallVelocity() {
        assertEquals(0.0, model.getBalls().get(0).getVelo().x(), 0.001);
        assertEquals(0.0, model.getBalls().get(0).getVelo().y(), 0.001);
    }

    @Test
    public void testBallPositionAfterTick() {
        model.tick(FPS);
        Ball ball = model.getBalls().get(0);

        double ballPositionX = ball.getExactX() + ball.getVelo().x() * deltaT;
        double ballPositionY = ball.getExactY() + ball.getVelo().y() * deltaT;

        assertEquals(ballPositionX, model.getBalls().get(0).getExactX(), 0.001);
        assertEquals(ballPositionY, model.getBalls().get(0).getExactY(), 0.001);
    }

    @Test
    public void testBallVelocityAfterTick() {
        model.tick(FPS);
        double deltaT = 1/FPS;

        double velocityAfterGravity = 25*deltaT;
        double velocityAfterFriciton = velocityAfterGravity*(1 - (0.025 * deltaT) - (0.025 * Math.abs(velocityAfterGravity) * deltaT));

        System.out.println(model.getBalls().get(0).getVelo().y());
        assertEquals(0.0, model.getBalls().get(0).getVelo().x(), 0.001); //  velocity in x direction unchanged
        assertEquals(velocityAfterFriciton, model.getBalls().get(0).getVelo().y(), 0.001); // velocity in y direction after forces
    }

    @Test
    public void testGetGridDimensions() {
        assertEquals(20, model.getGridDimensions());
        model.clear();
        assertEquals(20, model.getGridDimensions());
    }

    @Test
    public void testGetBalls() {
        assertEquals(1, model.getBalls().size());
        assertNotNull(model.getBalls().get(0));
    }

    @Test
    public void testAddBall() {
        model.clear();
        double posX = 1;
        double posY = 1;
        assertTrue(model.addBall(new Ball(posX, posY, 0, 0, 0.5)));
        assertEquals(1, model.getBalls().size());
        assertNotNull(model.getBalls().get(0));
        assertEquals(0.0, model.getBalls().get(0).getVelo().x(), 0.001);
        assertEquals(0.0, model.getBalls().get(0).getVelo().y(), 0.001);
        assertEquals(posX, model.getBalls().get(0).getExactX(), 0.001);
        assertEquals(posY, model.getBalls().get(0).getExactY(), 0.001);
    }

    @Test
    public void testAddBallToTheSamePosition() {
        model.clear();
        double posX = 1;
        double posY = 1;
        assertTrue(model.addBall(new Ball(posX, posY, 0, 0, 0.5)));
        assertFalse(model.addBall(new Ball(posX, posY, 0, 0, 0.5)));
    }

    @Test
    public void testGetGizmos() {
        assertNotNull(model.getGizmos());
    }

    @Test
    public void testGetGizmoWithinGrid() {
        assertNotNull(model.getGizmo(0, 2));
        assertTrue((model.getGizmo(0, 2) instanceof StandardGizmo));
        assertNull(model.getGizmo(0, 0));
    }

    @Test
    public void testGetGizmoOutideGrid() {
        assertNull(model.getGizmo(-1, -1));
        assertNull(model.getGizmo(21, 21));
    }

    @Test
    public void testCountGizmos() {
        assertEquals(35, model.getGizmos().size());
    }

    @Test
    public void testAddGizmoWitinGrid() {
        StandardGizmo triangle = new TriangularBumper(0, 0);
        model.addGizmo(triangle);
        assertEquals(36, model.getGizmos().size());
        assertSame(triangle, model.getGizmo(0, 0));
    }

    @Test
    public void testAddGizmoOutsideGrid() {
        model.clear();
        assertFalse(model.addGizmo(new TriangularBumper(-1, -1)));
        assertFalse(model.addGizmo(new TriangularBumper(21, 21)));
        assertFalse(model.addGizmo(new TriangularBumper(16, 24)));
    }

    @Test
    public void testAddGizmoToExisting() {
        StandardGizmo existing = model.getGizmo(0, 2);
        StandardGizmo triangle = new TriangularBumper(0, 2);
        assertFalse(model.addGizmo(triangle));
        assertSame(existing, model.getGizmo(0, 2));
    }

    @Test
    public void testRemoveExistingGizmo() {
        StandardGizmo gizmo = model.getGizmo(0, 2);
        model.removeGizmo(gizmo);
        assertEquals(34, model.getGizmos().size());
    }

    @Test
    public void testAddAfterRemoveExistingGizmo() {
        StandardGizmo gizmo = model.getGizmo(0, 2);
        model.removeGizmo(gizmo);
        StandardGizmo triangle = new TriangularBumper(0, 2);
        assertTrue(model.addGizmo(triangle));
    }

    @Test
    public void moveGizmo() {
    }

    @Test
    public void setGravityForce() {

    }

    @Test
    public void setFrictionMU() {

    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    // Note: load from file doesn't handle exception
    @Test
    public void loadFromFile() {
        exception.expect(FileNotFoundException.class);
        exception.expectMessage("File Not Found");
        model.setFilePath("src/saveFile111");
    }

    // Note: save to file handles exception
    @Test
    public void saveToFIle() {

    }

    @Test
    public void setFilePath() {
    }

    @After
    public void tearDown() throws Exception {

    }
}