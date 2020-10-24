package nl.rug.oop.grapheditor.model;

import nl.rug.oop.grapheditor.metadata.ViewModel;
import nl.rug.oop.grapheditor.io.SaveAndLoad;
import nl.rug.oop.grapheditor.model.objects.Edge;
import nl.rug.oop.grapheditor.model.objects.Node;
import nl.rug.oop.grapheditor.view.GraphFrame;

import java.awt.Dimension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.UndoManager;

/**
 * The model that keeps track of all edges and nodes within our graph.
 */
public class GraphModel extends Observable {

    private ArrayList<Edge> edges;
    private ArrayList<Node> nodes;
    private UndoManager manager;
    private int offSetX;
    private int offSetY;
    private static int paddingX;
    private static int paddingY;
    private static int defaultOffSetX;
    private static int defaultOffSetY;

    public GraphModel() {
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
        manager = new UndoManager();
        defaultOffSetX = 25;
        defaultOffSetY = 10;
        offSetX = 25;
        offSetY = 10;
        paddingX = 150;
        paddingY = 140;
    }

    /**
     * Immediately load a graph from file.
     * @param  filepath the path of the file we want to load
     */
    public GraphModel(String filepath) throws IOException {
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
        manager = new UndoManager();

        GraphModel newModel = SaveAndLoad.loadModelFromFile(filepath);
        checkAndReplaceModel(newModel);
        new GraphFrame(this);
    }

    public void addEdit(AbstractUndoableEdit edit)
    {
        manager.addEdit(edit);
    }

    public void undo()
    {
        manager.undo();
        update();
    }

    public void redo()
    {
        manager.redo();
        update();
    }

    public Boolean canUndo()
    {
        return manager.canUndo();
    }

    public Boolean canRedo()
    {
        return manager.canRedo();
    }

    // Kills all the undoable edits
    public void resetManager()
    {
        manager.die();
    }
    /**
     * @param newModel the newModel
     */
    public void checkAndReplaceModel(GraphModel newModel){
        if(newModel.nodes.size() > 0) {
            startNew();
            this.nodes.addAll(newModel.nodes);
            this.edges.addAll(newModel.edges);
            update();
        } else {
            System.out.println("New model is corrupted. ");
        }
    }

    public void startNew()
    {
        this.nodes.clear();
        this.edges.clear();
        update();
    }

    /**
     * Creates a default node
     */
    public Node createNode(){
        checkXY();
        Node node = new Node(offSetX, offSetY);
        offSetX = 25;
        offSetY = 10;
        node.setName("Node " + nodes.size());
        return node;
    }

    /**
     * Adds a default node to the list of nodes.
     */
    public void addNode(Node node){
        nodes.add(node);
        update();
    }

    /**
     * Creates appropriate x and y locations for the nodes,
     * this way they wont spawn on top of one another.
     */
    public void checkXY(){
        Dimension frameSize = ViewModel.getFrameSize();

        for(int i = 0; i< nodes.size(); i++){
            for (Node item: nodes){
                if (item.getRectangle().y ==  offSetY && item.getRectangle().x == offSetX){
                    if (offSetX > frameSize.width){
                        if (offSetY > frameSize.height){
                            offSetY = defaultOffSetY;
                        }
                        offSetY += paddingY;
                        offSetX = defaultOffSetX;
                    }else {
                        offSetX += paddingX;
                    }
                    break;
                }
            }
        }
    }


    /**
     * Adds an edge to the list of edges
     * @param edge The edge to be added
     */
    public void addEdge(Edge edge){
        edges.add(edge);
        update();
    }

    /**
     * Adds a collection of edges to the list of edges
     * @param listEdges The edges to be added
     */
    public void addEdges(Collection<Edge> listEdges){
        edges.addAll(listEdges);
        update();
    }

    /**
     * Checks if the edge argument is exists in the list
     * @param edge The edge to be checke
     */
    public Boolean edgesContain(Edge edge){
        for(Edge item: edges) {
            if(item.getStart() == edge.getStart() && 
                item.getEnd() == edge.getEnd())
                return true;
        }

        return false;

    }

    /**
     * Removes the node and all the edges associated with it
     * @param node The node to be removed
     */
    public void removeNode(Node node){
        ArrayList<Edge> forRemoval = new ArrayList<Edge>();
        for(Edge item: edges)
        {
            if(item.getStart() == node || item.getEnd() == node) forRemoval.add(item);
        }
        for(Edge item: forRemoval)
        {
            edges.remove(item);
        }
        nodes.remove(node);
        update();
    }

    /**
     * Removes the edge from the list of edges.
     * @param edge the edge to be removed
     * */
    public void removeEdge(Edge edge){
        edges.remove(edge);
        update();
    }

    /**
     * Notifying the observers
     */
    public void update(){
        setChanged();
        notifyObservers();
    }

    public ArrayList<Edge> getEdges() { return edges; }

    public ArrayList<Node> getNodes() { return nodes; }


}
