/**
 * The function of Town is to serve as the data structure for Road and Graph,
 * as well as to be used in TownGraphManager in certain places.
 * 
 * @author Joshua Gizaw
 */
import java.util.LinkedHashSet;
import java.util.Iterator;

public class Town implements Comparable<Town> {
    private String name;
    private LinkedHashSet<Town> neighbors;

    public Town(String name) {
        this.name = name;
        neighbors = new LinkedHashSet<Town>();
    }

    /**
     * Copy constructor
     * @param templateTown the town to copy
     */
    public Town(Town templateTown) {
        this.setName(templateTown.getName());
        neighbors = new LinkedHashSet<Town>();
        for (Town t : templateTown.getNeighbors())
            this.neighbors.add(t);
    }

    /**
     * @return the name of this town
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this town to the desired String
     * @param name the new name of the town
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return a LinkedHashSet of neighboring towns
     */
    public LinkedHashSet<Town> getNeighbors() {
        return neighbors;
    }

    /**
     * Sets the neighbors of this Town to the desired LinkedHashSet.
     * @param neighbors the new set of neighbors
     */
    public void setNeighbors(LinkedHashSet<Town> neighbors) {
        this.neighbors = neighbors;
    }

    /**
     * Adds a Town to the list of neighboring towns to this one.
     * @param newNeighbor the new neighbor to add
     * @return true if the element was not already in the set
     */
    public boolean addNeighbor(Town newNeighbor) {
        return neighbors.add(newNeighbor);
    }

    /**
     * Removes the desired town from the set of neighboring towns if it is present in the set.
     * @param t the town to be removed
     * @return true if the element was in the set and removed
     */
    public boolean removeNeighbor(Town t) {
        return neighbors.remove(t);
    }

    /**
     * Checks two Town objects to see if they are equal.
     * NOTE: the strings must be exactly the same to be considered equal.
     * @param o the Town to be compared with this instance
     * @return true if the two Town objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Town) {
            Town o2 = (Town) o;
            return this.getName().equals(o2.getName());
        } else {
            return false;
        }
    }

    /**
     * @return the hash code of this Town
     */
    @Override
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * Compares this Town with another based on their names
     * @param arg0 the Town to be compared with this instance
     * @return 0 if they are equal, a positive number if this instance is bigger than arg0, or a negative number if arg0 is bigger than this instance
     */
    @Override
    public int compareTo(Town arg0) {
        return this.getName().compareTo(arg0.getName());
    }

    @Override
    public String toString() {
        String str = getName() + " with neighbors ";
        Iterator<Town> i = getNeighbors().iterator();
        while (i.hasNext()) {
            str += i.next().getName() + ", ";
        }
        str = str.substring(0, str.length() - 2);
        return str;
    }
}
