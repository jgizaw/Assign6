import static org.junit.Assert.*;

import org.junit.Test;

public class RoadTest_Student {

    @Test
    public void testConstructor() {
        Town town1 = new Town("Town_1");
        Town town2 = new Town("Town_2");
        Road road = new Road(town1, town2, 5, "Road_1");

        assertEquals(town1, road.getSource());
        assertEquals(town2, road.getDestination());
        assertEquals(5, road.getWeight());
        assertEquals("Road_1", road.getName());
    }

    @Test
    public void testCopyConstructor() {
        Town town1 = new Town("Town_1");
        Town town2 = new Town("Town_2");
        Road road = new Road(town1, town2, "Road_2");

        assertEquals(town1, road.getSource());
        assertEquals(town2, road.getDestination());
        assertEquals(1, road.getWeight());
        assertEquals("Road_2", road.getName());
    }

    @Test
    public void testSettersAndGetters() {
        Town town1 = new Town("Town_1");
        Town town2 = new Town("Town_2");
        Road road = new Road(town1, town2, 3, "Road_3");

        road.setSource(town2);
        road.setDestination(town1);
        road.setWeight(10);
        road.setName("Road_10");

        assertEquals(town2, road.getSource());
        assertEquals(town1, road.getDestination());
        assertEquals(10, road.getWeight());
        assertEquals("Road_10", road.getName());
    }

    @Test
    public void testCompareTo() {
        Town town1 = new Town("Town_1");
        Town town2 = new Town("Town_2");
        Road road1 = new Road(town1, town2, 5, "Road_1");
        Road road2 = new Road(town1, town2, 10, "Road_2");

        assertTrue(road1.compareTo(road2) < 0);
        assertTrue(road2.compareTo(road1) > 0);
        assertTrue(road1.compareTo(road1) == 0);
    }

    @Test
    public void testEquals() {
        Town town1 = new Town("Town_1");
        Town town2 = new Town("Town_2");
        Road road1 = new Road(town1, town2, 5, "Road_1");
        Road road2 = new Road(town1, town2, 5, "Road_1"); 

        assertTrue(road1.equals(road2));
    }

    @Test
    public void testHashCode() {
        Town town1 = new Town("Town_1");
        Town town2 = new Town("Town_2");
        Road road = new Road(town1, town2, 5, "Road_1");

        int expectedHashCode = town1.hashCode() + town2.hashCode();
        assertEquals(expectedHashCode, road.hashCode());
    }

    @Test
    public void testContains() {
        Town town1 = new Town("Town_1");
        Town town2 = new Town("Town_2");
        Road road = new Road(town1, town2, 5, "Road_1");

        assertTrue(road.contains(town1));
        assertTrue(road.contains(town2));
        assertFalse(road.contains(new Town("Town_3")));
    }

    @Test
    public void testToString() {
        Town town1 = new Town("Town_1");
        Town town2 = new Town("Town_2");
        Road road = new Road(town1, town2, 5, "Road_1");

        String expected = "Road: Road_1 connects towns: Town_1 and Town_2";
        assertEquals(expected, road.toString());
    }
}
