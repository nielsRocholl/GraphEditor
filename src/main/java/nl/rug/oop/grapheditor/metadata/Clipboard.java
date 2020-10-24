package nl.rug.oop.grapheditor.metadata;

import java.util.ArrayList;

import nl.rug.oop.grapheditor.model.GraphModel;
import nl.rug.oop.grapheditor.model.objects.Edge;
import nl.rug.oop.grapheditor.model.objects.Node;

/**
 * This class holds the currently copied node and pastes it if needed.
 */
public class Clipboard {
    /*
     The clipboard is shared by all components of the program, so it does
     not need to be instantiated. Therefore, its methods are static
    */
    private static Node node;
    private static ArrayList<Edge> edges;

    /**
     * Copies an item by holding it in the placeholder node and all
     * its associated edges
     * @param item The node to be copied
     * @param model The overarching model
     */
    public static void copy(Node item, GraphModel model)
    {
        node = item;
        edges = new ArrayList<Edge>();

        for(Edge edge: model.getEdges())
        {
            if(edge.getStart() == node || edge.getEnd() == node) 
                edges.add(edge);
        }
    }

    /**
     * This method pastes the copied node by creating a temporary node
     * in a certain location (so it's not pasted at the same location
     * as the copied one) and sets its name to the copied node + "copy"
     * It then adds all the edges of the copied node.
     * @param model
     * @return
     */
    public static Node paste(GraphModel model)
    {
        Node temp = model.createNode();
        temp.setName(node.getName() + " copy");
        model.addNode(temp);

        for(Edge edge: edges)
        {
            if(edge.getStart() == node) 
                model.addEdge(new Edge(temp, edge.getEnd()));
            if(edge.getEnd() == node)
                model.addEdge(new Edge(edge.getEnd(), temp));
        }

        return temp;
    }

    // A getter of the copied node
    public static Node getNode() { return node; }

    // This checkes whether the clipboard is empty
    public static Boolean isEmpty() { return node == null; }
}