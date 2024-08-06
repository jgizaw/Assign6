/**
* The purpose of Road is to serve as the edge structure for the Graph
* and TownGraphManager classes, using Town as the vertexes.
*
*/
public class Road implements Comparable<Road>{
private Town source;
private Town destination;
private String name;
private int weight;

/**
*
* @param source
* @param destination
* @param degrees (The weight)
* @param name
*/
public Road(Town source, Town destination, int degrees, String name) {
this.source = source;
this.destination = destination;
weight = degrees;
this.name = name;
}

/**
* Copy constructor which sets the weight to 1 by default
* @param source
* @param destination
* @param name
*/
public Road(Town source, Town destination, String name) {
this.source = source;
this.destination = destination;
this.name = name;
weight = 1;
}

/**
* @return the Town at the first end of this Road
*/
public Town getSource() {
return source;
}

/**
* Sets the Town at the first end of this Road to the desired reference.
* @param source - The reference which will become the new Town
*/
public void setSource(Town source) {
this.source = source;
}

/**
* @return the second town on the road
*/
public Town getDestination() {
return destination;
}

/**
* Sets the second town on the road to the desired reference.
* @param destination - the desired reference which will become the new destination.
*/
public void setDestination(Town destination) {
this.destination = destination;
}

/**
* @return the name of the Road
*/
public String getName() {
return name;
}

/**
* Sets the name of the Road to the desired String
* @param name - the desired name
*/
public void setName(String name) {
this.name = name;
}

/**
* Returns the weight (length) of the road
* @return the weight (length) of the road
*/
public int getWeight() {
return weight;
}

/**
* Sets the weight (length) of the road to the desired value
* @param weight
*/
public void setWeight(int weight) {
this.weight = weight;
}

/**
* Compares the roads based on weight, and returns the following:
* @return a positive number if weight 1 is greater than weight 2, a negative
* number if weight 2 is greater than weight 1, or 0 if they are equal
*/
@Override
public int compareTo(Road arg0) {
return this.getWeight() - arg0.getWeight();
}

/**
* Compares two roads based on their towns
* @return true if they have the same towns, false otherwise
*/
@Override
public boolean equals(Object o) {
Road o2;
if(o instanceof Road) {
o2 = (Road) o;
return (o2.contains(this.getSource()) && o2.contains(this.getDestination()));
}
return false;
}

/**
* @return the hash code of the road, which is based on its two towns added together
*/
@Override
public int hashCode() {
return getSource().hashCode() + getDestination().hashCode();
}

/**
* Checks if the road contains the desired town
* @param town - the town to be checked for
* @return true if the road contains the town, false otherwise
*/
public boolean contains(Town town) {
return (source.equals(town) || destination.equals(town));
}

/**
* @return a String representation of the road
*/
@Override
public String toString() {
return "Road: " + getName() + " connects towns: " + getSource().getName()
+ " and " + getDestination().getName();
}

}