package model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FileIOTest {
    FileIO fileInOut;
    Model model;

    @Before
    public void setUp() throws Exception {
        model = new Model();
        fileInOut = new FileIO(model);
    }

    @Test
    public void setFilePath() throws Exception {
        String filePath = "src/newFile";
        fileInOut.setFilePath(filePath);
        assertEquals(filePath, fileInOut.getFilePath());
    }

    @Test
    public void saveToFile() throws Exception {

    }

    @Test
    public void loadFromFile() throws Exception {

    }

}