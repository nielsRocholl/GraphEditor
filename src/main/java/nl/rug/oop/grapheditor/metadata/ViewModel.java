package nl.rug.oop.grapheditor.metadata;

import java.util.Observable;
import java.awt.Point;
import java.awt.Dimension;

import nl.rug.oop.grapheditor.model.GraphModel;
import nl.rug.oop.grapheditor.model.objects.Edge;
import nl.rug.oop.grapheditor.model.objects.Node;

/**
 * This class contains all placeholders that describe the
 * user-program interactions. They are observed by the view and 
 * manipulated by the controller, just like the regular model. However,
 * the information those placeholders bear does not belong to the model
 * semantically as it relates to user interactions
 */
public class ViewModel extends Observable {
    // Currently selected node or edge. If none is selected, they equal null
    private Node selectedNode;
    private Edge selectedEdge;

    // Flags indicating whether the user is currently adding/removing an edge
    private Boolean addingEdge;
    private Boolean removingEdge;

    // Flags indicating whether the user is currently moving/resizing a node
    private Boolean movingNode;
    private Boolean resizingNode;

    // The current location of the mouse cursor. Only updated when adding an edge
    private Point cursor;

    // The last fixed locations and sizes of the currently moved/resized node
    // If none is selected, they equal null
    private Point movingNodeLocation;
    private Point resizingNodeLocation;
    private Dimension resizingNodeDimension;

    // If the user is currently resizing a node, this holds from which corner/side, that is
    private ResizeOption resizeOption;

    // The size of the JFrame. It is currently static, as we do not plan to have multiple windows
    private static Dimension frameSize;

    // Flags indicating the theme/modes the user has currently selected
    private boolean directed;
    private boolean mode;

    /* A flag indicating whether all icons/sound files have loaded correctly
     It is initialized once in the beginning of the program, when it first
     loads them
    */
    private boolean iconsLoaded;
    
    // Default values for every placeholder
    public ViewModel(Dimension frameSize) 
    {
        selectedNode = null;
        selectedEdge = null;
        addingEdge = false;
        removingEdge = false;
        movingNode = false;
        resizingNode = false;
        movingNodeLocation = new Point(0,0);
        resizingNodeDimension = new Dimension(0,0);
        resizeOption = ResizeOption.NO_RESIZE;
        cursor = new Point(0, 0);
        // TODO address this in a static way
        this.frameSize = frameSize;
        mode = false;
        directed = true;
    }

    // All the methods of this class are some form of getters or setters

    /**
     * Selects a node
     * @param node The node to be selected
     */
    public void selectNode(Node node)
    {
        selectedNode = node;
        update();
    }

    /**
     * Deselects a node
     */
    public void deselectNode()
    {
        selectedNode = null;
        update();
    }

    

    /**
     * Selects an edge
     * @param edge The edge to be selected
     */
    public void selectEdge(Edge edge)
    {
        selectedEdge = edge;
        update();
    }

    /**
     * Deselects a node
     */
    public void deselectEdge()
    {
        selectedEdge = null;
        update();
    }

    /**
     * Checks if there are any selected nodes
     */
    public Boolean isNodeSelected()
    {
        return selectedNode != null;
    }

    /**
     * Checks if there are any selected edges
     */
    public Boolean isEdgeSelected()
    {
        return selectedEdge != null;
    }

    /**
     * Checks if a certain node is selected
     * @param node The node to be checked
     * @return a boolean flag
     */
    public Boolean isSelected(Node node)
    {
        if(!isNodeSelected()) return false;
        return node == selectedNode;
    }

    /**
     * Checks if a certain edge is selected
     * @param edge The edge to be checked
     * @return a boolean flag
     */
    public Boolean isSelected(Edge edge)
    {
        if(!isEdgeSelected()) return false;
        return edge == selectedEdge;
    }

    public Boolean isNodeMoving() { return movingNode; }

    public void setNodeMoving(Boolean flag) { movingNode = flag; }

    public Point getMovingNodeLocation() { return movingNodeLocation; }

    public void setMovingNodeLocation(Point newLoc) { movingNodeLocation = newLoc; }

    public Boolean isNodeResizing() { return resizingNode; }

    public void setNodeResizing(Boolean flag) { resizingNode = flag; }

    public Dimension getResizingNodeDimension() { return resizingNodeDimension; }

    public Point getResizingNodeLocation() { return resizingNodeLocation; }

    public void setResizingNodeLocation(Point newLoc) { resizingNodeLocation = newLoc; }

    public void setResizingNodeDimension(Dimension newSize) 
        { resizingNodeDimension = newSize; }
        
    public ResizeOption getResizingOption() { return resizeOption; }
    
    public void setResizingOption(ResizeOption option) 
        { resizeOption = option; }

    public Node getSelectedNode() { return selectedNode; }
    
    public Edge getSelectedEdge() { return selectedEdge; }

    public Boolean getAddingEdge() { return addingEdge; }

    public void setAddingEdge(Boolean flag) { addingEdge = flag; update(); }

    public Boolean getRemovingEdge() { return removingEdge; }

    public void setRemovingEdge(Boolean flag) { removingEdge = flag; update(); }

    public Point getCursor() { return cursor; }

    public void setCursor(Point location) { cursor = location; update(); }

    public static Dimension getFrameSize() { return frameSize; }

    public boolean getMode() { return mode; }

    public boolean isDirected() { return directed; }

    public boolean getLoad() { return iconsLoaded; }
    
    public void setMode(boolean mode){
        this.mode = mode;
        update();
    }

    public void setDirected(Boolean flag)
    {
        this.directed = flag;
        update();
    }

    public void setLoad(boolean load) { this.iconsLoaded = load; }

    /* The ViewModel is observed by several other components, so we
    implemented a function to automatically notify observers
    */
    public void update()
    {
        setChanged();
        notifyObservers();
    }
}