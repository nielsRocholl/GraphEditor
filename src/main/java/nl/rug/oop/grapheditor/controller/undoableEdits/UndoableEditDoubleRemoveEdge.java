package nl.rug.oop.grapheditor.controller.undoableEdits;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import nl.rug.oop.grapheditor.metadata.ViewModel;
import nl.rug.oop.grapheditor.model.GraphModel;
import nl.rug.oop.grapheditor.model.objects.Edge;

/**
 * This class allows the program to remove two edges at the same time
 * This is used when two edges connecting the same nodes in the opposite
 * directions are to be deleted. There is a separate undoable edit for 
 * that since the deletion of the two edges should be undoable as a single
 * action
 */
public class UndoableEditDoubleRemoveEdge extends AbstractUndoableEdit {
    private GraphModel model;
    private Edge edgeForward, edgeBack;

    public UndoableEditDoubleRemoveEdge(GraphModel model,
        Edge edgeForward,
        Edge edgeBack)
    {
        super();
        this.model = model;
        this.edgeForward = edgeForward;
        this.edgeBack = edgeBack;
    }

    public void redo() throws CannotRedoException
    {
        model.removeEdge(edgeForward);
        model.removeEdge(edgeBack);
    }

    public void undo() throws CannotUndoException
    {
        model.addEdge(edgeForward);
        model.addEdge(edgeBack);
    }
}