import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.Iterator;
import java.util.Scanner;

/**
 * The purpose of TownGraphManager is to serve as the main utility class
 * for the manager program, which uses the methods and constructors of
 * Graph to achieve functionality.
 * 
 * @author Joshua Gizaw
 */
public class TownGraphManager implements TownGraphManagerInterface {

    private Graph graph = new Graph();

    /**
     * Adds a road with 2 towns and a road name.
     * @param town1 name of town 1
     * @param town2 name of town 2
     * @param weight weight of the road
     * @param roadName name of road
     * @return true if the road was added successfully
     */
    @Override
    public boolean addRoad(String town1, String town2, int weight, String roadName) {
        Town source = new Town(town1);
        Town destination = new Town(town2);
        graph.addVertex(source);
        graph.addVertex(destination);
        Road result = graph.addEdge(source, destination, weight, roadName);
        return result != null;
    }

    /**
     * Returns the name of the road that both towns are connected through.
     * @param town1 name of town 1
     * @param town2 name of town 2
     * @return name of road if town 1 and town2 are connected, returns null if not
     */
    @Override
    public String getRoad(String town1, String town2) {
        Road result = graph.getEdge(new Town(town1), new Town(town2));
        return result != null ? result.getName() : null;
    }

    /**
     * Adds a town to the graph.
     * @param v the town's name
     * @return true if the town was successfully added, false if not
     */
    @Override
    public boolean addTown(String v) {
        return graph.addVertex(new Town(v));
    }

    /**
     * Gets a town with a given name.
     * @param name the town's name
     * @return the Town specified by the name, or null if town does not exist
     */
    @Override
    public Town getTown(String name) {
        Set<Town> vertices = graph.vertexSet();
        Iterator<Town> iterator = vertices.iterator();
        Town target = new Town(name);
        while (iterator.hasNext()) {
            Town current = iterator.next();
            if (current.equals(target)) return current;
        }
        return null;
    }

    /**
     * Determines if a town is already in the graph.
     * @param v the town's name
     * @return true if the town is in the graph, false if not
     */
    @Override
    public boolean containsTown(String v) {
        return graph.containsVertex(new Town(v));
    }

    /**
     * Determines if a road is in the graph.
     * @param town1 name of town 1
     * @param town2 name of town 2
     * @return true if the road is in the graph, false if not
     */
    @Override
    public boolean containsRoadConnection(String town1, String town2) {
        Town source = new Town(town1);
        Town destination = new Town(town2);
        return graph.containsEdge(source, destination);
    }

    /**
     * Creates an arraylist of all road titles in sorted order by road name.
     * @return an arraylist of all road titles in sorted order by road name
     */
    @Override
    public ArrayList<String> allRoads() {
        Set<Road> roads = graph.edgeSet();
        ArrayList<String> result = new ArrayList<>();
        for (Road r : roads) result.add(r.getName());
        Collections.sort(result);
        return result;
    }

    /**
     * Deletes a road from the graph.
     * @param town1 name of town 1
     * @param town2 name of town 2
     * @param roadName the road name
     * @return true if the road was successfully deleted, false if not
     */
    @Override
    public boolean deleteRoadConnection(String town1, String town2, String roadName) {
        Town source = new Town(town1);
        Town destination = new Town(town2);
        Road target = graph.getEdge(source, destination);
        if (target == null) return false;
        graph.removeEdge(source, destination, target.getWeight(), roadName);
        return true;
    }

    /**
     * Deletes a town from the graph.
     * @param v name of town
     * @return true if the town was successfully deleted, false if not
     */
    @Override
    public boolean deleteTown(String v) {
        return graph.removeVertex(new Town(v));
    }

    /**
     * Creates an arraylist of all towns in alphabetical order.
     * @return an arraylist of all towns in alphabetical order
     */
    @Override
    public ArrayList<String> allTowns() {
        Set<Town> towns = graph.vertexSet();
        ArrayList<String> result = new ArrayList<>();
        for (Town t : towns) result.add(t.getName());
        Collections.sort(result);
        return result;
    }

    /**
     * Returns the shortest path from town 1 to town 2.
     * @param town1 name of town 1
     * @param town2 name of town 2
     * @return an ArrayList of roads connecting the two towns, null if no path exists
     */
    @Override
    public ArrayList<String> getPath(String town1, String town2) {
        Town source = new Town(town1);
        Town destination = new Town(town2);
        if (graph.containsVertex(source) && graph.containsVertex(destination)) {
            ArrayList<String> result = graph.shortestPath(source, destination);
            return result != null ? result : new ArrayList<>();
        }
        return new ArrayList<>();
    }

    /**
     * Populates the graph with the roads provided in a file.
     * The file extension must be included in the file name.
     * @param fileName the file name
     */
    public void readFile(String fileName) {
        try (Scanner inFile = new Scanner(new File(fileName))) {
            while (inFile.hasNext()) {
                String currentLine = inFile.nextLine();
                String[] parts = currentLine.split(";");
                String name = parts[0].substring(0, parts[0].indexOf(','));
                int weight = Integer.parseInt(parts[0].substring(parts[0].indexOf(',') + 1));
                String source = parts[1];
                String destination = parts[2];

                Town sourceTown = new Town(source);
                Town destinationTown = new Town(destination);
                graph.addVertex(sourceTown);
                graph.addVertex(destinationTown);
                graph.addEdge(sourceTown, destinationTown, weight, name);
            }
        } catch (Exception e) {
            System.out.println("Error in readFile");
            e.printStackTrace();
        }
    }

    /**
     * Populates the graph with the roads provided in a file.
     * @param selectedFile the selected file
     * @throws FileNotFoundException if the file is not found
     * @throws IOException if an I/O error occurs
     */
    public void populateTownGraph(File selectedFile) throws FileNotFoundException, IOException {
        try (Scanner inFile = new Scanner(selectedFile)) {
            while (inFile.hasNextLine()) {
                String currentLine = inFile.nextLine();
                String[] roadData = currentLine.split(";");
                String roadName = roadData[0];
                int weight = Integer.parseInt(roadData[1]);
                String source = roadData[2];
                String destination = roadData[3];

                addTown(source);
                addTown(destination);
                addRoad(source, destination, weight, roadName);
            }
        } catch (Exception e) {
            System.out.println("Error in populateTownGraph");
            e.printStackTrace();
        }
    }
}
