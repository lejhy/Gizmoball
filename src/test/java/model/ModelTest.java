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
    public void loadFromFile() throws Exception {
        assertEquals(model.getBalls().size(), 1);
        assertEquals(model.getGizmos().size(), 35);
        assertEquals(model.getKeyDownTriggers().size(), 4);
        assertEquals(model.getKeyUpTriggers().size(), 3);
    }

    @Test
    public void loadFromNonexistentFile(){
       model.setFilePath("lolgudkekm9");
       model.loadFromFile();
        assertEquals(model.getBalls().size(), 1);
        assertEquals(model.getGizmos().size(), 35);
        assertEquals(model.getKeyDownTriggers().size(), 4);
        assertEquals(model.getKeyUpTriggers().size(), 3);

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
    public void testAddBallOffGrid(){
        assertFalse(model.addBall(new Ball(0, 0, 0, 0, 0.5)));
        assertFalse(model.addBall(new Ball(1, 0, 0, 0, 0.5)));
        assertFalse(model.addBall(new Ball(0, 1, 0, 0, 0.5)));

        assertFalse(model.addBall(new Ball(-1, -1, 0, 0, 0.5)));
        assertFalse(model.addBall(new Ball(-1, 5, 0, 0, 0.5)));
        assertFalse(model.addBall(new Ball(5, -1, 0, 0, 0.5)));

        assertFalse(model.addBall(new Ball(20, 20, 0, 0, 0.5)));
        assertFalse(model.addBall(new Ball(1, 20, 0, 0, 0.5)));
        assertFalse(model.addBall(new Ball(20, 1, 0, 0, 0.5)));
    }

    @Test
    public void testAddBallToGizmo(){
        assertFalse(model.addBall(new Ball(19, 0, 0, 0, 0.5)));
        assertFalse(model.addBall(new Ball(1, 1, 0, 0, 0.5)));
    }

    @Test
    public void testGetBall(){
        model.clear();
        Ball ball = new Ball(1, 1, 0, 0, 0.5);
        model.addBall(ball);
        assertEquals(model.getBall(1, 1), ball);
    }

    @Test
    public void testGetNonexistingBall(){
        model.clear();
        model.addBall(new Ball(1, 1, 0, 0, 0.5));
        assertNull(model.getBall(1, 2));
    }

    @Test
    public void testRemoveBall(){
        Ball ball = new Ball(1, 4, 0, 0, 0.5);
        assertTrue(model.addBall(ball));
        int oldSize = model.getBalls().size();
        assertTrue(model.removeBall(ball));
        assertEquals(model.getBalls().size(), oldSize-1);
    }

    @Test
    public void testRemoveNonexistingBall(){
        Ball ball = new Ball(1, 4, 0, 0, 0.5);
        int oldSize = model.getBalls().size();
        assertFalse(model.removeBall(ball));
        assertEquals(model.getBalls().size(), oldSize);
    }

    @Test
    public void testGetGizmos() {
        assertNotNull(model.getGizmos());
    }

    @Test
    public void testGetGizmoWithinGrid() {
        assertNotNull(model.getGizmo(0, 2));
        assertTrue((model.getGizmo(0, 2) != null));
        assertNull(model.getGizmo(0, 0));
    }

    @Test
    public void testGetGizmoOutideGrid() {
        assertNull(model.getGizmo(-1, -1));
        System.out.println(model.getGizmo(-1, -1));
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
    public void testRemoveNonexistingGizmo(){
        StandardGizmo gizmo = new SquareBumper(0, 0);
        assertFalse(model.removeGizmo(gizmo));
    }

    @Test
    public void testAddAfterRemoveExistingGizmo() {
        StandardGizmo gizmo = model.getGizmo(0, 2);
        model.removeGizmo(gizmo);
        StandardGizmo triangle = new TriangularBumper(0, 2);
        assertTrue(model.addGizmo(triangle));
    }

    @Test
    public void testMoveGizmoInsideGrid() {
        StandardGizmo gizmo = model.getGizmo(0, 2);
        assertTrue(model.getGizmo(1, 3) == null);
        assertTrue(model.moveGizmo(gizmo, 1, 3));
        assertTrue(model.getGizmo(1, 3) != null);
    }

    @Test
    public void testMoveGizmoOutsideGrid() {
        StandardGizmo gizmo = model.getGizmo(0, 2);
        assertFalse(model.moveGizmo(gizmo, -1, -1));
    }

    @Test
    public void testMoveNonexisitingGizmo() {
        StandardGizmo gizmo = new SquareBumper(0, 0);
        assertFalse(model.moveGizmo(gizmo, 2, 2));
    }

    @Test
    public void testMoveGizmoToExisting() {
        StandardGizmo gizmo = model.getGizmo(0, 2);
        assertFalse(model.moveGizmo(gizmo, 1, 2));
    }

    @Test
    public void testMoveGizmoToBall() {
        StandardGizmo gizmo = model.getGizmo(0, 2);
        assertFalse(model.moveGizmo(gizmo, 1, 11));
        assertFalse(model.moveGizmo(gizmo, 0, 11));
        assertFalse(model.moveGizmo(gizmo, 1, 10));
        assertFalse(model.moveGizmo(gizmo, 0, 10));
    }

    @Test
    public void setGravityForce() {
        double gravF = model.getGravityForce(1);
        model.setGravityForce(0.0);
        assertTrue(model.getGravityForce(1) == 0.0);
        assertNotEquals(model.getGravityForce(1), gravF);
    }

    @Test
    public void setFrictionMU() {
        double mu1 = model.getFrictionMU(1);
        double mu2 = model.getFrictionMU(2);

        model.setFrictionMU(0.9001, 1);
        model.setFrictionMU(0.9002, 2);

        assertTrue(model.getFrictionMU(1) == 0.9001);
        assertTrue(model.getFrictionMU(2) == 0.9002);

        assertNotEquals(model.getFrictionMU(1), mu1);
        assertNotEquals(model.getFrictionMU(2), mu2);

        assertNotEquals(model.getFrictionMU(1), model.getFrictionMU(2));

        assertTrue(model.getFrictionMU(3) ==  0.0);
    }

    @Test
    public void testAddKeyTriggerUp(){
        StandardGizmo gizmo = model.getGizmo(19, 0);
        int oldSize = model.getKeyUpTriggers().size();
        model.addKeyUp(42, gizmo);

        assertEquals(model.getKeyUpTriggers().size(), oldSize+1);
    }

    @Test
    public void testAddKeyTriggerDown(){
        StandardGizmo gizmo = model.getGizmo(19, 0);
        int oldSize = model.getKeyDownTriggers().size();
        model.addKeyDown(42, gizmo);

        assertEquals(model.getKeyDownTriggers().size(), oldSize+1);
    }

    @Test
    public void testKeyTriggerUp(){
        StandardGizmo gizmo = model.getGizmo(19, 0);
        model.addKeyUp(42, gizmo);

        model.handleKeyUp(42);
        assertTrue(gizmo.isTriggered());
    }

    @Test
    public void testKeyTriggerDown(){
        StandardGizmo gizmo = model.getGizmo(19, 0);
        model.addKeyDown(42, gizmo);

        model.handleKeyDown(42);
        assertTrue(gizmo.isTriggered());
    }

    @Test
    public void testNonexistentKeyTriggerDown(){
        StandardGizmo gizmo = model.getGizmo(19, 0);

        model.handleKeyDown(42);
        assertFalse(gizmo.isTriggered());
    }

    @Test
    public void testNonexistentKeyTriggerUp(){
        StandardGizmo gizmo = model.getGizmo(19, 0);

        model.handleKeyUp(42);
        assertFalse(gizmo.isTriggered());
    }

    @Test
    public void testRemoveKeyTriggerDown(){
        StandardGizmo gizmo = model.getGizmo(19, 0);
        int oldSize = model.getKeyDownTriggers().size();
        model.addKeyDown(9001, gizmo);
        assertTrue(model.removeKeyDown(9001, gizmo));
        assertEquals(model.getKeyDownTriggers().get(9001).size(), 0);
    }

    @Test
    public void testRemoveKeyTriggerUp(){
        StandardGizmo gizmo = model.getGizmo(19, 0);
        int oldSize = model.getKeyUpTriggers().size();
        model.addKeyUp(9001, gizmo);
        assertTrue(model.removeKeyUp(9001, gizmo));
        assertEquals(model.getKeyUpTriggers().get(9001).size(), 0);
    }

    @Test
    public void testRemoveNonexistentKeyTrigger(){
        StandardGizmo gizmo = model.getGizmo(19, 0);

        assertFalse(model.removeKeyDown(9001, gizmo));
        assertNull(model.getKeyDownTriggers().get(9001));
    }

    // Note: save to file handles exception
    @Test
    public void saveToFIle() {

    }

    @Test
    public void setFilePath() {

    }

    @Test
    public void testTrigger(){
        StandardGizmo gizmo = model.getGizmo(0, 2);

        assertFalse(gizmo.isTriggered());

        gizmo.trigger(new Ball(1, 5, 0, 0, 0.5));
        assertTrue(gizmo.isTriggered());
    }

    @Test
    public void testGizmoTrigger(){
        StandardGizmo gizmo = model.getGizmo(0, 2);
        StandardGizmo gizmo1 = new SquareBumper(0, 0);
        model.addGizmo(gizmo1);
        gizmo.addGizmoTrigger(gizmo1);

        gizmo.trigger(new Ball(1, 5, 0, 0, 0.5));

        assertTrue(gizmo1.isTriggered());

        assertFalse(gizmo.addGizmoTrigger(gizmo));
        assertFalse(gizmo.removeGizmoTrigger(gizmo));
    }

    @Test
    public void testCollisionSquareTop(){
        model.clear();
        Ball ball = new Ball(1, 1, 0, 0, 0.5);
        SquareBumper squareBumper = new SquareBumper(1, 2);
        model.addBall(ball);
        model.addGizmo(squareBumper);
        for(int x = 0; x < 16; x++){
            model.tick(60);
        }
        assertTrue(squareBumper.isTriggered());
    }

    @After
    public void tearDown() throws Exception {

    }
}