import static org.junit.Assert.*;

import java.util.LinkedHashSet;

import org.junit.Test;

public class TownTest_Student {

    @Test
    public void testConstructor() {
        Town town = new Town("Town_1");
        assertEquals("Town_1", town.getName());
        assertTrue(town.getNeighbors().isEmpty());
    }

    @Test
    public void testCopyConstructor() {
        Town town1 = new Town("Town_1");
        Town town2 = new Town("Town_2");
        town1.addNeighbor(town2);

        Town copiedTown = new Town(town1);
        assertEquals(town1.getName(), copiedTown.getName());
        assertTrue(copiedTown.getNeighbors().contains(town2));
    }

    @Test
    public void testSettersAndGetters() {
        Town town = new Town("Town_1");
        town.setName("Town_2");

        LinkedHashSet<Town> neighbors = new LinkedHashSet<>();
        Town neighbor = new Town("Town_3");
        neighbors.add(neighbor);
        town.setNeighbors(neighbors);

        assertEquals("Town_2", town.getName());
        assertTrue(town.getNeighbors().contains(neighbor));
    }

    @Test
    public void testAddNeighbor() {
        Town town1 = new Town("Town_1");
        Town town2 = new Town("Town_2");

        assertTrue(town1.addNeighbor(town2));
        assertFalse(town1.addNeighbor(town2));  // Adding the same neighbor again should return false
    }

    @Test
    public void testRemoveNeighbor() {
        Town town1 = new Town("Town_1");
        Town town2 = new Town("Town_2");
        town1.addNeighbor(town2);

        assertTrue(town1.removeNeighbor(town2));
        assertFalse(town1.removeNeighbor(town2));  // Removing again should return false
    }

    @Test
    public void testEquals() {
        Town town1 = new Town("Town_1");
        Town town2 = new Town("Town_1");
        Town town3 = new Town("Town_2");

        assertTrue(town1.equals(town2));
        assertFalse(town1.equals(town3));
    }

    @Test
    public void testHashCode() {
        Town town = new Town("Town_1");
        assertEquals("Town_1".hashCode(), town.hashCode());
    }

    @Test
    public void testCompareTo() {
        Town town1 = new Town("Town_1");
        Town town2 = new Town("Town_2");

        assertTrue(town1.compareTo(town2) < 0);
        assertTrue(town2.compareTo(town1) > 0);
        assertTrue(town1.compareTo(new Town("Town_1")) == 0);
    }

    @Test
    public void testToString() {
        Town town = new Town("Town_1");
        Town neighbor = new Town("Town_2");
        town.addNeighbor(neighbor);

        String expected = "Town_1 with neighbors Town_2";
        assertEquals(expected, town.toString());
    }
}
