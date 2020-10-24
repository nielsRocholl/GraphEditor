package nl.rug.oop.grapheditor.controller;

import java.awt.event.*;
import java.awt.Rectangle;
import java.awt.Dimension;
import java.awt.Point;
import java.util.Collections;

import javax.swing.JOptionPane;
import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import nl.rug.oop.grapheditor.controller.undoableEdits.UndoableEditAddEdge;
import nl.rug.oop.grapheditor.controller.undoableEdits.UndoableEditMoveNode;
import nl.rug.oop.grapheditor.controller.undoableEdits.UndoableEditRemoveEdge;
import nl.rug.oop.grapheditor.controller.undoableEdits.UndoableEditResizeNode;
import nl.rug.oop.grapheditor.metadata.ResizeOption;
import nl.rug.oop.grapheditor.metadata.ViewModel;

//import org.w3c.dom.events.MouseEvent;

import nl.rug.oop.grapheditor.model.GraphModel;
import nl.rug.oop.grapheditor.model.objects.Edge;
import nl.rug.oop.grapheditor.model.objects.Node;

public class SelectionController extends MouseAdapter {
    private GraphModel model;
    private ViewModel vm;
    
    /**
     * Constructor for the controller
     * @param model
     */
    public SelectionController(GraphModel model, ViewModel vm)
    {
        this.model = model;
        this.vm = vm;
    }

    /**
     * When the mouse is clicked, the corresponding node/edge is selected.
     * If no node/edge is selected but the mouse has clicked,
     * the selected node/edge gets deselected
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        
        /* Iterate the nodes backwards (so that the ones on the front are selected)
         If the cursor is inside a box, represented by a node, it is selected
         */
        for(int i = model.getNodes().size() - 1; i >= 0; i--)
        {
            Node item = model.getNodes().get(i);
            if(item.getRectangle().contains(e.getPoint()))
            {
                /* If the iterated node is the one clicked upon,
                 the algorithm checks if the user is adding or removing an edge.
                 If not, it selects the node. This is done so that, the process
                 of adding/removing edge is not interrupted as it depends on the
                 pre-selected node (we would not want the program to select a new node
                 and lose track of the pre-selected node).  Either way, selecting a node
                 will deselect the edge, if one is selected
                */
                if(!vm.getAddingEdge() && !vm.getRemovingEdge()) vm.selectNode(item);
                vm.deselectEdge();
                return;
            }
        }
        /* If the user has clicked outside of any node, adding the edge is
        cancelled and the current node is deselected */
        vm.deselectNode();
        vm.setAddingEdge(false);

        /* The same process is repeated for edges */
        for(int i = model.getEdges().size() - 1; i >= 0; i--)
        {
            Edge item = model.getEdges().get(i);
            if(item.contains(e.getPoint()))
            {
                vm.selectEdge(item);
                return;
            }
        }
        /* If no edge is selected either, the currently selected
        edge is deselected */
        vm.deselectEdge();
    }

    /**
     * When the mouse is dragged, it moves the corresponding node
     * If the mouse is dragged over a place, not occupied by a node,
     * nothing happens. If the node moving flag is not raised, the moving
     * node location is updated for undoability reasons
     */
    @Override
    public void mouseDragged(MouseEvent e)
    {
        super.mouseDragged(e);

        // If no node is selected, the moving/resizing is not done
        if(!vm.isNodeSelected()) return;

        Node nodePressed = vm.getSelectedNode();

        
        // Gets the resize option to check if the dragging is meant for resizing
        ResizeOption option = resizeOption(
            nodePressed.getRectangle(), 
            e.getPoint());

        /* The algorithm prioritizes resizing over moving, if the dragging starts from the right place
         If the user has dragged the node from one of the corners or sides, the resize
         option will not be no_resize. If that happens and the ViewModel does not
         say that a node is being resized, that means the user has just started resizing.
         When that happens, it sets the current location and size of the node in the 
         ViewModel and does not change them until the mouse is released. This is
         for sake of undoability. The resize option is also saved, so that it does
         not change until the resizing is complete
        */
        if(option != ResizeOption.NO_RESIZE && 
            !vm.isNodeResizing() && 
            !vm.isNodeMoving())
        {
            vm.setNodeResizing(true);
            vm.setResizingOption(option);
            vm.setResizingNodeDimension(nodePressed.getRectangle().getSize());
            vm.setResizingNodeLocation(nodePressed.getRectangle().getLocation());
        }

        /* After the user has started resizing, the node is resized with the
        corresponding option
        */
        if(vm.isNodeResizing())
        {
            nodePressed.resizeWithOption(vm.getResizingOption(), e.getPoint());
            return;
        }

        // 
        if(!vm.isNodeMoving() && 
            !nodePressed.getRectangle().contains(
                e.getPoint()
            )) return;

        /* If the node is not being resized, it must be moving. If it has 
        just started moving (a.k.a., the viewmodel parameter has not updated yet),
        then it sets the location in the ViewModel for undoability
        */
        if(!vm.isNodeMoving() && !vm.isNodeResizing()) {
            vm.setNodeMoving(true);
            vm.setMovingNodeLocation(nodePressed
                .getRectangle()
                .getLocation());
        }

        /*
        Either way the algorithm must move the node to the corresponding location
        */
        int marginX = nodePressed.getRectangle().width / 2;
        int marginY = nodePressed.getRectangle().height / 2;
        
        // The margins are so that the user always holds the dragged node by the center
        int x = e.getX() - marginX;
        int y = e.getY() - marginY;

        // The node could not be moved outside of the frame
        if(x < 0 || y < 0) return;

        Dimension frameSize = ViewModel.getFrameSize();
        if(x + 2 * marginX > frameSize.width || y + 2 * marginY > frameSize.height)
            return;
        
        /* The whole rectangle that the newly moved node is going to occupy,
        is saved so that the controller can check if it overlaps another node 
        */
        Rectangle newLocation = new Rectangle(x, y, nodePressed.getRectangle().width, nodePressed.getRectangle().height);
        for(Node item: model.getNodes()) 
        {
            if(item == nodePressed) continue;
            // The nodes cannot move on top of each other
            if(item.overlaps(newLocation)) return;
        }
        vm.getSelectedNode().move(x, y);
    }

    /**
     * When the mouse is pressed, if the user is adding or removing an edge,
     * it completes the procedure.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        
        /* Iterate the nodes backwards (so that the ones on the front are selected)
         If the cursor is inside a box, represented by a node, it is selected
         */
        for(int i = model.getNodes().size() - 1; i >= 0; i--)
        {
            Node item = model.getNodes().get(i);
            if(item.getRectangle().contains(e.getPoint()))
            { 
                /* If the user is currently adding an edge, an edge is 
                created between the currently selected node and the one that is 
                clicked upon */
                if(vm.getAddingEdge() && item != vm.getSelectedNode()) {
                    Edge edge = new Edge(vm.getSelectedNode(), item);

                    /* It will only create the edge if this edge does not exist
                    (but it could add an edge between the same nodes if it is
                    in opposite direction)
                    */
                    if(!model.edgesContain(edge)) {
                        UndoableEditAddEdge editAddEdge = new UndoableEditAddEdge(model, vm, edge);
                        editAddEdge.redo();
                        model.addEdit(editAddEdge);
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "The edge, you are trying to " + 
                        "add, already exists!"); 
                    }
                } 

                /* If the user is currently removing an edge, the edge connecting
                the selected nodes and the one clicked upon is removed */
                if(vm.getRemovingEdge() && item != vm.getSelectedNode()) {
                    for(Edge edge: model.getEdges())
                    {
                        if(edge.getEnd() == item && edge.getStart() == vm.getSelectedNode())
                        {
                            /*
                            It will only remove the edge following the user-defined direction
                            */
                            UndoableEditRemoveEdge editRemoveEdge = new UndoableEditRemoveEdge(
                                model, 
                                edge);
                            editRemoveEdge.redo();
                            model.addEdit(editRemoveEdge);
                            vm.deselectNode();
                            break;
                        }
                    }
                    // TODO Place this at the right spot
                    /*JOptionPane.showMessageDialog(null, "In order to remove an edge," + 
                        "you must select a node, click the \"Remove edge\" button" + 
                        "and click a the other node that connects to the edge. " + 
                        "Alternatively, you can simply click the edge and press the button");*/
                    /*
                    The user completes the removing edge procedure
                    */
                    vm.setRemovingEdge(false);
                }
                /* If no such process finishes, the mouse pressed for a 
                simple node selection. However, unlike mouseClicked, 
                it will not deselect a node if no node was clicked */
                vm.selectNode(item);
            }
        }
        vm.setNodeMoving(false);
    }

    /**
     * When a mouse is moved while adding an edge, 
     * the line follows the cursor
     */
    @Override
    public void mouseMoved(MouseEvent e)
    {
        /*
        for(int i = model.getNodes().size() - 1; i >= 0; i--)
        {
            Node item = model.getNodes().get(i);
            if(item.getRectangle().contains(e.getPoint()))
            {
                // TODO change mouse cursor
            }
        }*/

        if(!vm.getAddingEdge()) return;

        vm.setCursor(e.getPoint());
        
    }

    /**
     * When a mouse is released after moving a node, it turns off the movingNode flag
     * and creates an undoable edit for moving that node. 
     * If a node was not moving beforehand, nothing happens. 
     */
    @Override
    public void mouseReleased(MouseEvent e)
    {
        super.mouseReleased(e);

        // If no node is selected, nothing happens
        if(!vm.isNodeSelected()) return;
        Node node = vm.getSelectedNode();
        
        /* If a node was moving, it creates an undoable edit with the
        previous location (saved in the ViewModel) and the current one.
        This sets it in place */
        if(vm.isNodeMoving())
        {
            vm.setNodeMoving(false);
    
            UndoableEditMoveNode editMoveNode = new UndoableEditMoveNode(
            model,
            vm, 
            node, 
            node.getRectangle().getLocation(),
            vm.getMovingNodeLocation());
            editMoveNode.redo();

            model.addEdit(editMoveNode);   
        } 

        /* If a node was resizing, it creates an undoable edit with the
        previous location and size (saved in the ViewModel) and the current one.
        This sets it in place */
        if(vm.isNodeResizing())
        {
            vm.setNodeResizing(false);
    
            UndoableEditResizeNode editResizeNode = new UndoableEditResizeNode(
            model,
            vm, 
            node, 
            node.getRectangle().getLocation(),
            vm.getResizingNodeLocation(),
            node.getRectangle().getSize(),
            vm.getResizingNodeDimension());
            editResizeNode.redo();

            model.addEdit(editResizeNode);
        }
    }

    /**
     * Checks if the cursor is close enough to any of the corners of
     * the rectangle of the node to start resizing, and if so, 
     * which resizing option
     * @param rect The rectangle of the node
     * @param p The point of the cursor
     */
    private ResizeOption resizeOption(Rectangle rect, Point p)
    {
        // Sets up how close to the corner/side you have to be to start resizing
        int THRESHOLD = 10;

        // Compares the cursor point to all possible corners
        Point bottomRight = new Point(rect.x + rect.width, rect.y + rect.height);
        Point topRight = new Point(rect.x + rect.width, rect.y);
        Point bottomLeft = new Point(rect.x, rect.y + rect.height);
        Point topLeft = new Point(rect.x, rect.y);


        if(Math.abs(p.x - bottomRight.x) < THRESHOLD) {
            if(Math.abs(p.y - bottomRight.y) < THRESHOLD) 
                return ResizeOption.BOTTOM_RIGHT_CORNER;
            if(Math.abs(p.y - topRight.y) < THRESHOLD)
                return ResizeOption.TOP_RIGHT_CORNER;
                
            return ResizeOption.RIGHT_SIDE;
        }

        if(Math.abs(p.x - topLeft.x) < THRESHOLD) {
            if(Math.abs(p.y - topLeft.y) < THRESHOLD) 
                return ResizeOption.TOP_LEFT_CORNER;
            if(Math.abs(p.y - bottomLeft.y) < THRESHOLD) 
                return ResizeOption.BOTTOM_LEFT_CORNER;

            return ResizeOption.LEFT_SIDE;
        }

        if(Math.abs(p.y - topRight.y) < THRESHOLD)
            return ResizeOption.TOP_SIDE;

        if(Math.abs(p.y - bottomLeft.y) < THRESHOLD) 
            return ResizeOption.BOTTOM_SIDE;

        return ResizeOption.NO_RESIZE;
    }
}
