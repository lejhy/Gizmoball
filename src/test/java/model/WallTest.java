package model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WallTest {
    Model model;
    Walls walls;

    @Before
    public void setUp(){
        model = new Model();
        walls = new Walls(0, 0, 0, 0);
    }

    @Test
    public void testGetCollider(){
        assertEquals(walls.getCollider().getLines().size(), 4);
    }
}
