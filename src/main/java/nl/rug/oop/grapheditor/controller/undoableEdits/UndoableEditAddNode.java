package nl.rug.oop.grapheditor.controller.undoableEdits;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import nl.rug.oop.grapheditor.metadata.ViewModel;
import nl.rug.oop.grapheditor.model.GraphModel;
import nl.rug.oop.grapheditor.model.objects.Node;

/**
 * This class allows the user to add a node and remove the same
 * node if the action is undone
 */
public class UndoableEditAddNode extends AbstractUndoableEdit {
    private GraphModel model;
    private Node node;

    // This is in order to keep track of whether the node is initialized
    private Boolean nodeCreated;

    public UndoableEditAddNode(GraphModel model)
    {
        /* The node is not initialized in the constructors. Only when the
         action is performed for the first time, the program creates a node
         and initializes it.
        */
        super();
        this.model = model;
        this.nodeCreated = false;
    }

    public void redo() throws CannotRedoException
    {
        /* 
        If the node was not created before, it creates it. Otherwise, that 
        would mean that the use manually selected redo which just adds the
        previously created node
        */
        if(!nodeCreated)
        {
            node = model.createNode();
            nodeCreated = true;
        }
        model.addNode(node);;
    }

    public void undo() throws CannotUndoException
    {
        model.removeNode(node);
    }
}