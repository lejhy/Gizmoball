package model;

import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import physics.Angle;

import java.io.FileNotFoundException;

import static org.junit.Assert.*;

public class FileIOTest {
    FileIO fileInOut;
    Model model;
    String filePath;

    @Before
    public void setUp() throws Exception {
        model = new Model();
        fileInOut = new FileIO(model);
        filePath = "saveFile";
    }



    @Test
    public void testSetFilePath() throws Exception {
        fileInOut.setFilePath(filePath);
        assertEquals(filePath, fileInOut.getFilePath());
    }

    @Test
    public void loadFromExistingFile() throws Exception {
        fileInOut.setFilePath(filePath);
        Model newModel = fileInOut.loadFromFile();
        assertEquals(newModel.getGizmos().size(), 35);
    }

    /* gizmos have wrong coordinates */
    @Test
    public void loadFromFileWithWrongGizmoCoordinates() throws Exception {
        fileInOut.setFilePath("src/wrongCoordinates");
        fileInOut.loadFromFile();
    }

    // Note: different exception catching doesn't work
    @Test  (expected = RuntimeException.class)
    public void loadFromFileWithWrongConnects() throws Exception {
        fileInOut.setFilePath("src/wrongConnects");
        fileInOut.loadFromFile();
    }

    ExpectedException exception = ExpectedException.none();

    @Test
    public void loadFromNonExistingFile() throws Exception {
        fileInOut.setFilePath("src/wrong");
        exception.expect(FileNotFoundException.class);
    }

    @Test
    public void testSaveToFile() throws Exception {
        Model saveTestModel = new Model();
        saveTestModel.setFilePath("saveTest");


        StandardGizmo gizmo = new SquareBumper(0, 0);
        StandardGizmo gizmo2 = new SquareBumper(1, 1);

        gizmo.addGizmoTrigger(gizmo2);


        saveTestModel.addGizmo(gizmo);
        saveTestModel.addGizmo(gizmo2);
        saveTestModel.addGizmo(new CircularBumper(10, 10));
        saveTestModel.addGizmo(new TriangularBumper(15, 0));
        saveTestModel.getGizmo(15, 0).rotate(Angle.DEG_270);
        saveTestModel.addGizmo(new RightFlipper(0, 15));
        saveTestModel.addGizmo(new LeftFlipper(3, 3));
        saveTestModel.addBall(new Ball(2, 4, 0, 0, 0.5));

        saveTestModel.addKeyDown(34, gizmo);
        saveTestModel.addKeyUp(34, gizmo);


        saveTestModel.setFilePath("saveTest");
        saveTestModel.saveToFile();

        saveTestModel = new Model();
        saveTestModel.setFilePath("saveTest");
        saveTestModel.loadFromFile();
        assertEquals(saveTestModel.getGizmos().size(), 6);

    }

    @Test
    public void testSaveNonexisitingFile(){
        model.setFilePath("fakeDirectory/notAFilePath.fakeExtension");
        model.saveToFile();
        exception.expect(FileNotFoundException.class);
    }



}