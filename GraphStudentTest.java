import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GraphStudentTest {
    private GraphInterface<Town, Road> graph;
    private Town[] towns;

    @Before
    public void setUp() throws Exception {
        graph = new Graph();
        towns = new Town[12];
        
        for (int i = 1; i < 12; i++) {
            towns[i] = new Town("Town_" + i);
            graph.addVertex(towns[i]);
        }
        
        graph.addEdge(towns[1], towns[2], 2, "Road_1");
        graph.addEdge(towns[1], towns[3], 4, "Road_2");
        graph.addEdge(towns[1], towns[5], 6, "Road_3");
        graph.addEdge(towns[3], towns[7], 1, "Road_4");
        graph.addEdge(towns[3], towns[8], 2, "Road_5");
        graph.addEdge(towns[4], towns[8], 3, "Road_6");
        graph.addEdge(towns[6], towns[9], 3, "Road_7");
        graph.addEdge(towns[9], towns[10], 4, "Road_8");
        graph.addEdge(towns[8], towns[10], 2, "Road_9");
        graph.addEdge(towns[5], towns[10], 5, "Road_10");
        graph.addEdge(towns[10], towns[11], 3, "Road_11");
        graph.addEdge(towns[2], towns[11], 6, "Road_12");
    }

    @After
    public void tearDown() throws Exception {
        graph = null;
    }

    @Test
    public void testGetEdge() {
        assertEquals(new Road(towns[2], towns[11], 6, "Road_12"), graph.getEdge(towns[2], towns[11]));
        assertEquals(new Road(towns[3], towns[7], 1, "Road_4"), graph.getEdge(towns[3], towns[7]));
    }

    @Test
    public void testAddEdge() {
        assertFalse(graph.containsEdge(towns[3], towns[5]));
        graph.addEdge(towns[3], towns[5], 1, "Road_13");
        assertTrue(graph.containsEdge(towns[3], towns[5]));
    }

    @Test
    public void testAddVertex() {
        Town newTown = new Town("Town_12");
        assertFalse(graph.containsVertex(newTown));
        graph.addVertex(newTown);
        assertTrue(graph.containsVertex(newTown));
    }

    @Test
    public void testContainsEdge() {
        assertTrue(graph.containsEdge(towns[2], towns[11]));
        assertFalse(graph.containsEdge(towns[3], towns[5]));
    }

    @Test
    public void testContainsVertex() {
        assertTrue(graph.containsVertex(new Town("Town_2")));
        assertFalse(graph.containsVertex(new Town("Town_12")));
    }

    @Test
    public void testEdgeSet() {
        Set<Road> roads = graph.edgeSet();
        ArrayList<String> roadArrayList = new ArrayList<>();
        for (Road road : roads)
            roadArrayList.add(road.getName());
        Collections.sort(roadArrayList);
        assertEquals("Road_1", roadArrayList.get(0));
        assertEquals("Road_10", roadArrayList.get(1));
        assertEquals("Road_11", roadArrayList.get(2));
        assertEquals("Road_12", roadArrayList.get(3));
        assertEquals("Road_2", roadArrayList.get(4));
        assertEquals("Road_8", roadArrayList.get(10));
    }

    @Test
    public void testEdgesOf() {
        Set<Road> roads = graph.edgesOf(towns[1]);
        ArrayList<String> roadArrayList = new ArrayList<>();
        for (Road road : roads)
            roadArrayList.add(road.getName());
        Collections.sort(roadArrayList);
        assertEquals("Road_1", roadArrayList.get(0));
        assertEquals("Road_2", roadArrayList.get(1));
        assertEquals("Road_3", roadArrayList.get(2));
    }

    @Test
    public void testRemoveEdge() {
        assertTrue(graph.containsEdge(towns[2], towns[11]));
        graph.removeEdge(towns[2], towns[11], 6, "Road_12");
        assertFalse(graph.containsEdge(towns[2], towns[11]));
    }

    @Test
    public void testRemoveVertex() {
        assertTrue(graph.containsVertex(towns[2]));
        graph.removeVertex(towns[2]);
        assertFalse(graph.containsVertex(towns[2]));
    }

    @Test
    public void testVertexSet() {
        Set<Town> vertexes = graph.vertexSet();
        assertTrue(vertexes.contains(towns[1]));
        assertTrue(vertexes.contains(towns[10]));
        assertTrue(vertexes.contains(towns[11]));
        assertTrue(vertexes.contains(towns[2]));
        assertTrue(vertexes.contains(towns[3]));
    }

    @Test
    public void testTown_1ToTown_11() {
        String beginTown = "Town_1", endTown = "Town_11";
        Town beginIndex = null, endIndex = null;
        Set<Town> townsSet = graph.vertexSet();
        Iterator<Town> iterator = townsSet.iterator();
        while (iterator.hasNext()) {
            Town town = iterator.next();
            if (town.getName().equals(beginTown))
                beginIndex = town;
            if (town.getName().equals(endTown))
                endIndex = town;
        }
        if (beginIndex != null && endIndex != null) {
            ArrayList<String> path = graph.shortestPath(beginIndex, endIndex);
            assertNotNull(path);
            assertTrue(path.size() > 0);
            assertEquals("Town_1 via Road_1 to Town_2 2 mi", path.get(0).trim());
            assertEquals("Town_2 via Road_12 to Town_11 6 mi", path.get(1).trim());
        } else {
            fail("Town names are not valid");
        }
    }

    @Test
    public void testTown_1ToTown_10() {
        String beginTown = "Town_1", endTown = "Town_10";
        Town beginIndex = null, endIndex = null;
        Set<Town> townsSet = graph.vertexSet();
        Iterator<Town> iterator = townsSet.iterator();
        while (iterator.hasNext()) {
            Town town = iterator.next();
            if (town.getName().equals(beginTown))
                beginIndex = town;
            if (town.getName().equals(endTown))
                endIndex = town;
        }
        if (beginIndex != null && endIndex != null) {
            ArrayList<String> path = graph.shortestPath(beginIndex, endIndex);
            assertNotNull(path);
            assertTrue(path.size() > 0);
            assertEquals("Town_1 via Road_2 to Town_3 4 mi", path.get(0).trim());
            assertEquals("Town_3 via Road_5 to Town_8 2 mi", path.get(1).trim());
            assertEquals("Town_8 via Road_9 to Town_10 2 mi", path.get(2).trim());
        } else {
            fail("Town names are not valid");
        }
    }

    @Test
    public void testTown_4ToTown_11() {
        String beginTown = "Town_4", endTown = "Town_11";
        Town beginIndex = null, endIndex = null;
        Set<Town> townsSet = graph.vertexSet();
        Iterator<Town> iterator = townsSet.iterator();
        while (iterator.hasNext()) {
            Town town = iterator.next();
            if (town.getName().equals(beginTown))
                beginIndex = town;
            if (town.getName().equals(endTown))
                endIndex = town;
        }
        if (beginIndex != null && endIndex != null) {
            ArrayList<String> path = graph.shortestPath(beginIndex, endIndex);
            assertNotNull(path);
            assertTrue(path.size() > 0);
            assertEquals("Town_4 via Road_6 to Town_8 3 mi", path.get(0).trim());
            assertEquals("Town_8 via Road_9 to Town_10 2 mi", path.get(1).trim());
            assertEquals("Town_10 via Road_11 to Town_11 3 mi", path.get(2).trim());
        } else {
            fail("Town names are not valid");
        }
    }
}
