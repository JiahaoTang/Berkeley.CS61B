import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.*;
import java.lang.Long;

/**
 * Graph for storing all of the intersection (vertex) and road (edge) information.
 * Uses your GraphBuildingHandler to convert the XML files into a graph. Your
 * code must include the vertices, adjacent, distance, closest, lat, and lon
 * methods. You'll also need to include instance variables and methods for
 * modifying the graph (e.g. addNode and addEdge).
 *
 * @author Alan Yao, Josh Hug
 */
public class GraphDB {
    public final Map<Long, Node> nodes = new HashMap<>();
    public final Map<Long, Way> ways = new HashMap<>();
    public final Map<Long, Relation> relations = new HashMap<>();

    /** Your instance variables for storing the graph. You should consider
     * creating helper classes, e.g. Node, Edge, etc. */
    /*Node class is used for storing nodes in the map.*/
    static class Node{
        Long id;
        double lat;
        double lon;
        Map<String, String> extraInfo;

        Node(Long id, double lon, double lat){
            this.id = id;
            this.lat = lat;
            this.lon = lon;
            extraInfo = new HashMap<>();
        }
    }
    /*Way class is used for storing ways in the map.*/
    static class Way{
        Long id;
        Set<Long> nodeSet;
        Map<String, String> extraInfo;

        Way(Long id){
            this.id = id;
            nodeSet = new HashSet<>();
            extraInfo = new HashMap<>();
        }
    }
    /*Relation class is used for storint relationships in the map.*/
    static class Relation{
        Long id;
        Map<Node, String> nodeMembers;
        Map<Way, String> wayMembers;
        Map<String, String> extraInfo;

        Relation(Long id){
            this.id = id;
            nodeMembers = new HashMap<>();
            wayMembers = new HashMap<>();
            extraInfo = new HashMap<>();
        }
    }
    /**
     * Example constructor shows how to create and start an XML parser.
     * You do not need to modify this constructor, but you're welcome to do so.
     * @param dbPath Path to the XML file to be parsed.
     */
    public GraphDB(String dbPath) {
        try {
            File inputFile = new File(dbPath);
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            GraphBuildingHandler gbh = new GraphBuildingHandler(this);
            saxParser.parse(inputFile, gbh);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        clean();
    }

    public void addNode(Node node){
        nodes.put(node.id, node);
    }

    public void addWay(Way way){
        ways.put(way.id, way);
    }

    /**
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

    /**
     *  Remove nodes with no connections from the graph.
     *  While this does not guarantee that any two nodes in the remaining graph are connected,
     *  we can reasonably assume this since typically roads are connected.
     */
    private void clean() {
        // TODO: Your code here.
        Set<Long> connectedNodes = new HashSet<>();
        for(Long wayId:ways.keySet()){
            for(Long nodeId:ways.get(wayId).nodeSet){
                connectedNodes.add(nodeId);
            }
        }
//        System.out.println("Nodes size is " + nodes.size());
//        System.out.println("Ways size is " + ways.size());
        /*
        for(Long relationId:relations.keySet()){
            for(Node node:relations.get(relationId).nodeMembers.keySet()){
                connectedNodes.add(node.id);
            }
        }*/
        HashMap<Long,Node> newNodes = new HashMap<>();
//        System.out.println("Connected set size is " + connectedNodes.size());
        for(Long nodeId: nodes.keySet()){
            if(connectedNodes.contains(nodeId)){
                newNodes.put(nodeId, nodes.get(nodeId));
            }
        }
        nodes.clear();
        nodes.putAll(newNodes);
    }

    /** Returns an iterable of all vertex IDs in the graph. */
    Iterable<Long> vertices() {
        //TODO: YOUR CODE HERE, this currently returns only an empty list.
        return nodes.keySet();
    }

    /** Returns ids of all vertices adjacent to v. */
    Iterable<Long> adjacent(long v) {
        return null;
    }

    /** Returns the Euclidean distance between vertices v and w, where Euclidean distance
     *  is defined as sqrt( (lonV - lonV)^2 + (latV - latV)^2 ). */
    double distance(long v, long w) {
        Node nodeV = nodes.get(v);
        Node nodeW = nodes.get(w);
        return Math.sqrt(Math.pow(nodeV.lat * 364000 - nodeW.lat * 364000, 2) + Math.pow(nodeV.lon * 288000 - nodeW.lon * 288000, 2));
    }

    /** Returns the vertex id closest to the given longitude and latitude. */
    long closest(double lon, double lat) {
        double minDis = Double.MAX_VALUE;
        long res = 1L;
        for(Long nodesId : nodes.keySet()){
            double dis = Math.sqrt(Math.pow(lat * 364000 - nodes.get(nodesId).lat * 364000, 2) + Math.pow(lon * 288000 - nodes.get(nodesId).lon * 288000, 2));
            if(dis < minDis){
                minDis = dis;
                res = nodesId;
            }
        }
        return res;
    }

    /** Longitude of vertex v. */
    double lon(long v) {
        return nodes.get(v).lon;
    }

    /** Latitude of vertex v. */
    double lat(long v) {
        return nodes.get(v).lat;
    }
}
