import static org.junit.Assert.*;

import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TownGraphManagerStudentTest {
    private TownGraphManagerInterface townGraphManager;
    private String[] towns;

    @Before
    public void setUp() throws Exception {
        townGraphManager = new TownGraphManager();
        towns = new String[12];

        for (int i = 1; i < 12; i++) {
            towns[i] = "Town_" + i;
            townGraphManager.addTown(towns[i]);
        }

        townGraphManager.addRoad(towns[1], towns[2], 2, "Road_1");
        townGraphManager.addRoad(towns[1], towns[3], 4, "Road_2");
        townGraphManager.addRoad(towns[1], towns[5], 6, "Road_3");
        townGraphManager.addRoad(towns[3], towns[7], 1, "Road_4");
        townGraphManager.addRoad(towns[3], towns[8], 2, "Road_5");
        townGraphManager.addRoad(towns[4], towns[8], 3, "Road_6");
        townGraphManager.addRoad(towns[6], towns[9], 3, "Road_7");
        townGraphManager.addRoad(towns[9], towns[10], 4, "Road_8");
        townGraphManager.addRoad(towns[8], towns[10], 2, "Road_9");
        townGraphManager.addRoad(towns[5], towns[10], 5, "Road_10");
        townGraphManager.addRoad(towns[10], towns[11], 3, "Road_11");
        townGraphManager.addRoad(towns[2], towns[11], 6, "Road_12");
    }

    @After
    public void tearDown() throws Exception {
        townGraphManager = null;
    }

    @Test
    public void testAddRoad() {
        ArrayList<String> roads = townGraphManager.allRoads();
        assertEquals("Road_1", roads.get(0));
        assertEquals("Road_10", roads.get(1));
        assertEquals("Road_11", roads.get(2));
        assertEquals("Road_12", roads.get(3));
        townGraphManager.addRoad(towns[4], towns[11], 1, "Road_13");
        roads = townGraphManager.allRoads();
        assertEquals("Road_1", roads.get(0));
        assertEquals("Road_10", roads.get(1));
        assertEquals("Road_11", roads.get(2));
        assertEquals("Road_12", roads.get(3));
        assertEquals("Road_13", roads.get(4));
    }

    @Test
    public void testGetRoad() {
        assertEquals("Road_12", townGraphManager.getRoad(towns[2], towns[11]));
        assertEquals("Road_4", townGraphManager.getRoad(towns[3], towns[7]));
    }

    @Test
    public void testAddTown() {
        assertFalse(townGraphManager.containsTown("Town_12"));
        townGraphManager.addTown("Town_12");
        assertTrue(townGraphManager.containsTown("Town_12"));
    }

    @Test
    public void testDisjointGraph() {
        assertFalse(townGraphManager.containsTown("Town_12"));
        townGraphManager.addTown("Town_12");
        ArrayList<String> path = townGraphManager.getPath(towns[1], "Town_12");
        assertFalse(path.size() > 0);
    }

    @Test
    public void testContainsTown() {
        assertTrue(townGraphManager.containsTown("Town_2"));
        assertFalse(townGraphManager.containsTown("Town_12"));
    }

    @Test
    public void testContainsRoadConnection() {
        assertTrue(townGraphManager.containsRoadConnection(towns[2], towns[11]));
        assertFalse(townGraphManager.containsRoadConnection(towns[3], towns[5]));
    }

    @Test
    public void testAllRoads() {
        ArrayList<String> roads = townGraphManager.allRoads();
        assertEquals("Road_1", roads.get(0));
        assertEquals("Road_10", roads.get(1));
        assertEquals("Road_11", roads.get(2));
        assertEquals("Road_8", roads.get(10));
        assertEquals("Road_9", roads.get(11));
    }

    @Test
    public void testDeleteRoadConnection() {
        assertTrue(townGraphManager.containsRoadConnection(towns[2], towns[11]));
        townGraphManager.deleteRoadConnection(towns[2], towns[11], "Road_12");
        assertFalse(townGraphManager.containsRoadConnection(towns[2], towns[11]));
    }

    @Test
    public void testDeleteTown() {
        assertTrue(townGraphManager.containsTown("Town_2"));
        townGraphManager.deleteTown("Town_2");
        assertFalse(townGraphManager.containsTown("Town_2"));
    }

    @Test
    public void testAllTowns() {
        ArrayList<String> townsList = townGraphManager.allTowns();
        assertEquals("Town_1", townsList.get(0));
        assertEquals("Town_10", townsList.get(1));
        assertEquals("Town_11", townsList.get(2));
        assertEquals("Town_2", townsList.get(3));
        assertEquals("Town_8", townsList.get(9));
    }

    @Test
    public void testGetPath() {
        ArrayList<String> path = townGraphManager.getPath(towns[1], towns[11]);
        assertNotNull(path);
        assertTrue(path.size() > 0);
        assertEquals("Town_1 via Road_1 to Town_2 2 mi", path.get(0).trim());
        assertEquals("Town_2 via Road_12 to Town_11 6 mi", path.get(1).trim());
    }

    @Test
    public void testGetPathA() {
        ArrayList<String> path = townGraphManager.getPath(towns[1], towns[10]);
        assertNotNull(path);
        assertTrue(path.size() > 0);
        assertEquals("Town_1 via Road_2 to Town_3 4 mi", path.get(0).trim());
        assertEquals("Town_3 via Road_5 to Town_8 2 mi", path.get(1).trim());
        assertEquals("Town_8 via Road_9 to Town_10 2 mi", path.get(2).trim());
    }

    @Test
    public void testGetPathB() {
        ArrayList<String> path = townGraphManager.getPath(towns[1], towns[6]);
        assertNotNull(path);
        assertTrue(path.size() > 0);
        assertEquals("Town_1 via Road_2 to Town_3 4 mi", path.get(0).trim());
        assertEquals("Town_3 via Road_5 to Town_8 2 mi", path.get(1).trim());
        assertEquals("Town_8 via Road_9 to Town_10 2 mi", path.get(2).trim());
        assertEquals("Town_10 via Road_8 to Town_9 4 mi", path.get(3).trim());
        assertEquals("Town_9 via Road_7 to Town_6 3 mi", path.get(4).trim());
    }
}
