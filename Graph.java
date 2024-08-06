/**
* The purpose of Graph is to serve as
* the main data structure to be used
* in TownGraphManager, being able to
* store vertices and edges, and even
* find the shortest path between
* vertices.
*
* @author Joshua Gizaw
*/

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Queue;

public class Graph implements GraphInterface<Town, Road>{
private HashSet<Town> vertices;
private HashSet<Road> edges;
private ArrayList<String> shortestPath;

public Graph(){
vertices = new HashSet<Town>();
edges = new HashSet<Road>();
}

/**
* Returns an edge connecting source vertex to target vertex if such
* vertices and such edge exist in this graph. Otherwise returns
* null. 
* If any of the specified vertices is null
* returns null
*
*
* @param sourceVertex source vertex of the edge.
* @param destinationVertex target vertex of the edge.
*
* @return an edge connecting source vertex to target vertex.
*/
@Override
public Road getEdge(Town sourceVertex, Town destinationVertex) {
if(sourceVertex == null || destinationVertex == null) return null;

else{
Iterator<Road> iterator = edges.iterator();
Road current;
while(iterator.hasNext()) {
current = iterator.next();
if(current.contains(sourceVertex) && current.contains(destinationVertex))
return current;
}
}
return null;
}

/**
* Creates a new edge in this graph, going from the source vertex to the
* target vertex, and returns the created edge.
*
* The source and target vertices must already be contained in this
* graph. If they are not found in graph IllegalArgumentException is
* thrown.
*
*
* @param sourceVertex source vertex of the edge.
* @param destinationVertex target vertex of the edge.
* @param weight weight of the edge
* @param description description for edge
*
* @return The newly created edge if added to the graph, otherwise null.
*
* @throws IllegalArgumentException if source or target vertices are not
* found in the graph.
* @throws NullPointerException if any of the specified vertices is null.
*/
@Override
public Road addEdge(Town sourceVertex, Town destinationVertex, int weight, String description)
throws IllegalArgumentException, NullPointerException{


if(sourceVertex == null || destinationVertex == null)
throw new NullPointerException("Cannot add an edge that uses a null");

if(!containsVertex(sourceVertex) || !containsVertex(destinationVertex))
throw new IllegalArgumentException("One or both vertices is not contained in the graph");


Road newRoad = new Road(sourceVertex, destinationVertex, weight, description);
boolean result = edges.add(newRoad);

sourceVertex.addNeighbor(destinationVertex);
destinationVertex.addNeighbor(sourceVertex);
if(!result) return null;
return newRoad;
}

/**
* Adds the specified vertex to this graph if not already present.
*
* @param v vertex to be added to this graph.
*
* @return true if this graph did not already contain the specified
* vertex.
*
* @throws NullPointerException if the specified vertex is null.
*/
@Override
public boolean addVertex(Town v) throws NullPointerException{

if(v == null) throw new NullPointerException("Cannot add a null");
else {
if(vertices.contains(v)) return false;
else {
vertices.add(v);


return true;
}
}
}

/**
* Returns true if and only if this graph contains an edge going
* from the source vertex to the target vertex.
*
* @param sourceVertex source vertex of the edge.
* @param destinationVertex target vertex of the edge.
*
* @return true if this graph contains the specified edge.
*/
@Override
public boolean containsEdge(Town sourceVertex, Town destinationVertex) {


Road test = new Road(sourceVertex, destinationVertex, "for searching purposes");
if(edges.contains(test)) return true;
return false;
}

/**
* Returns true if this graph contains the specified vertex. More
* formally, returns true if and only if this graph contains a
* vertex u such that u.equals(v). If the
* specified vertex is null returns false.
*
* @param v vertex whose presence in this graph is to be tested.
*
* @return true if this graph contains the specified vertex.
*/
@Override
public boolean containsVertex(Town v) {

//Iterates through the vertex set to find the desired town
Iterator<Town> iterator = vertices.iterator();
Town current;
while(iterator.hasNext()) {
current = iterator.next();
if(current.equals(v)) return true;
}
return false;
}

/**
* Returns a set of the edges contained in this graph. The set is backed by
* the graph, so changes to the graph are reflected in the set. 
*
*
* @return a set of the edges contained in this graph.
*/
@Override
public Set<Road> edgeSet() {
return edges;
}

/**
* Returns a set of all edges touching the specified vertex (also
* referred to as adjacent vertices). If no edges are
* touching the specified vertex returns an empty set.
*
* @param vertex the vertex for which a set of touching edges is to be
* returned.
*
* @return a set of all edges touching the specified vertex.
*
* @throws IllegalArgumentException if vertex is not found in the graph.
* @throws NullPointerException if vertex is null.
*/
@Override
public Set<Road> edgesOf(Town vertex) throws NullPointerException, IllegalArgumentException{


if(vertex == null) throw new NullPointerException("Cannot find a vertex that contains a null");
if(!containsVertex(vertex)) throw new IllegalArgumentException("Vertex does not exist in the graph");


HashSet<Road> result = new HashSet<Road>();
Iterator<Road> iterator = edges.iterator();
Road current;

while(iterator.hasNext()) {
current = iterator.next();
if(current.contains(vertex)) result.add(current);
}
return result;
}

/**
* Removes an edge going from source vertex to target vertex, if such
* vertices and such edge exist in this graph.
*
* Returns the edge if removed
* or null otherwise.
*
* @param sourceVertex source vertex of the edge.
* @param destinationVertex target vertex of the edge.
* @param weight weight of the edge
* @param description description of the edge
*
* @return The removed edge, or null if no edge removed.
*/
@Override
public Road removeEdge(Town sourceVertex, Town destinationVertex, int weight, String description) {
Road removedRoad = new Road(sourceVertex, destinationVertex, weight, description);
Iterator<Road> iterator = edges.iterator();
Road current;


while(iterator.hasNext()) {
current = iterator.next();

if(current.equals(removedRoad)) {

if((weight > -1 && weight == current.getWeight()) || weight == -1) {

if((description != null && description.equals(current.getName())) || description == null) {

edges.remove(current);

sourceVertex.removeNeighbor(destinationVertex);
destinationVertex.removeNeighbor(sourceVertex);
return current;
}
}
}
}
return null;
}

/**
* @param v vertex to be removed from this graph, if present.
*
* @return true if the graph contained the specified vertex;
* false otherwise.
*/
@Override
public boolean removeVertex(Town v) {
HashSet<Road> edgesToBeRemoved = new HashSet<Road>();
if(!vertices.contains(v)) return false;
else {
vertices.remove(v);
Iterator<Road> iterator = edges.iterator();
Road current;

while(iterator.hasNext()) {
current = iterator.next();
if(current.contains(v)) edgesToBeRemoved.add(current);
}
for(Road r: edgesToBeRemoved) edges.remove(r);
return true;
}
}

/**
* Returns a set of the vertices contained in this graph.
*
*
* @return a set view of the vertices contained in this graph.
*/
@Override
public Set<Town> vertexSet() {
return vertices;
}

/**
* Find the shortest path from the sourceVertex to the destinationVertex
* call the dijkstraShortestPath with the sourceVertex
* @param sourceVertex starting vertex
* @param destinationVertex ending vertex
* @return An arraylist of Strings that describe the path from sourceVertex
* to destinationVertex
*/
@Override
public ArrayList<String> shortestPath(Town sourceVertex, Town destinationVertex) {
dijkstraShortestPath(sourceVertex);
ArrayList<String> result = new ArrayList<String>();

//Find the path from the source to the destination
Stack<String> path = new Stack<String>();
String currentNode = "";
boolean pathExists = false;

for(int i = 0; i < shortestPath.size(); i++) {
if(shortestPath.get(i).contains("to " + destinationVertex.getName())) {
path.push(shortestPath.get(i));
currentNode = shortestPath.get(i).substring(0, shortestPath.get(i).indexOf(" via"));
pathExists = true;
break;
}
}
if(!pathExists) return null;
while(!currentNode.equals(sourceVertex.getName())) {
for(int i = 0; i < shortestPath.size(); i++) {
if(shortestPath.get(i).contains("to " + currentNode)) {
path.push(shortestPath.get(i));
currentNode = shortestPath.get(i).substring(0, shortestPath.get(i).indexOf(" via"));
break;
}
}
}

while(!path.empty()) {
result.add(path.pop());
}

return result;
}

/**
* Dijkstra's Shortest Path Method. 
* 
* @param sourceVertex the vertex to find shortest path from
*
*/
@Override
public void dijkstraShortestPath(Town sourceVertex) {


shortestPath = new ArrayList<String>();
HashSet<Town> containedVertices = new HashSet<Town>();
HashSet<Road> containedEdges = new HashSet<Road>();
HashSet<Road> possibleEdges = new HashSet<Road>();
HashSet<Road> currentEdges = new HashSet<Road>();
HashSet<Town> toBeRemoved = new HashSet<Town>();
Queue<Town> vertexQueue = new PriorityQueue<Town>();
Town current;

shortestPath.add(sourceVertex.getName() + " via NONE to " + sourceVertex.getName() + " 0 mi");
containedVertices.add(sourceVertex);
vertexQueue.add(sourceVertex);


currentEdges = (HashSet<Road>) edgesOf(sourceVertex);
for(Road r: currentEdges) {
Town destination;
if(r.getSource().equals(sourceVertex)) destination = r.getDestination();
else destination = r.getSource();
shortestPath.add(sourceVertex.getName() + " via " + r.getName() +
" to " + destination.getName() + " " + r.getWeight() + " mi");
}

while((containedVertices.size() != vertices.size()) && vertexQueue.size() >= 0) {


for(Town t: vertexQueue) {
currentEdges = (HashSet<Road>) edgesOf(t);
int minWeight = 999999999;
Road minimum = null;


for(Road r: currentEdges) {

if((r.getWeight() < minWeight) && !containedEdges.contains(r)
&& !(containedVertices.contains(r.getSource())
&& containedVertices.contains(r.getDestination()))) {
minimum = r;
minWeight = r.getWeight();
}
}
if(minimum != null) {
if(minimum.getSource().equals(t)) possibleEdges.add(minimum);
else possibleEdges.add(new Road(minimum.getDestination(), minimum.getSource(),
minimum.getWeight(), minimum.getName()));
}
else toBeRemoved.add(t);
}

for(Town t: toBeRemoved) vertexQueue.remove(t);


int min = 999999;
Road minimum = null;

for(Road r: possibleEdges) {
if(r.getWeight() < min) {
minimum = r;
min = r.getWeight();
}
}

if(minimum != null) {

containedEdges.add(minimum);
Town newNode = minimum.getDestination();
containedVertices.add(newNode);
vertexQueue.add(newNode);

currentEdges = (HashSet<Road>) edgesOf(newNode);

for(Road r: currentEdges) {

String nextNodeName;
if(r.getSource().equals(newNode))
nextNodeName = r.getDestination().getName();
else nextNodeName = r.getSource().getName();

int notationIndex = -1;
for(int i = 0; i < shortestPath.size(); i++) {
if(shortestPath.get(i).contains("to " + nextNodeName)) {
notationIndex = i;
break;
}
}

if(notationIndex == -1) {

int newNodeWeight = -1;
for(int i = 0; i < shortestPath.size(); i++) {
if(shortestPath.get(i).contains("to " + newNode.getName())) {
newNodeWeight = getTotalWeight(shortestPath.get(i), sourceVertex);
break;
}
}

shortestPath.add(newNode.getName() + " via " + r.getName() + " to " + nextNodeName + " "
+ (r.getWeight()) + " mi");
}
else {

int neighborWeight = getTotalWeight(shortestPath.get(notationIndex), sourceVertex);
int newNodeWeight = -1;
for(int i = 0; i < shortestPath.size(); i++) {
if(shortestPath.get(i).contains("to " + newNode.getName())) {
newNodeWeight = getTotalWeight(shortestPath.get(i), sourceVertex);
break;
}
}

if(newNodeWeight + r.getWeight() < neighborWeight) {
shortestPath.remove(notationIndex);
shortestPath.add(newNode.getName() + " via " + r.getName() +
" to " + nextNodeName + " " + (r.getWeight()) + " mi");
}
}
}
}
else break;
possibleEdges.clear();
toBeRemoved.clear();
}
}

/**
* Returns the weight value contained in the notation String provided.
*
* @param str
* @return the weight value contained in the notation String provided
*/
private int getInteger(String str) {
for(int i = str.indexOf("mi") -2; i > -1; i--) {
if(str.charAt(i) == ' ') return Integer.parseInt(str.substring(i + 1, str.indexOf("mi") - 1));
}
return -1;
}

/**
* @param str - the notation of the last step before reaching the destination.
* @param sourceVertex
* @return the total cost of traversal for the shortest path from the source vertex
* to the destination
*/
private int getTotalWeight(String str, Town sourceVertex) {
String currentNode = str.substring(0, str.indexOf(" via"));
Stack <String> path = new Stack<String>();
int weight = 0;
path.push(str);

while(!currentNode.equals(sourceVertex.getName())) {
for(int i = 0; i < shortestPath.size(); i++) {
if(shortestPath.get(i).contains("to " + currentNode)) {
path.push(shortestPath.get(i));
currentNode = shortestPath.get(i).substring(0, shortestPath.get(i).indexOf(" via"));
break;
}
}
}
while(!path.empty())
weight += getInteger(path.pop());
return weight;
}

}