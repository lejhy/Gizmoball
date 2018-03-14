package model;

import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

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
        filePath = "src/saveFile";
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

    }
}