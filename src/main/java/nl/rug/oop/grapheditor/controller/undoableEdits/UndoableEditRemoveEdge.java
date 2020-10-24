package nl.rug.oop.grapheditor.controller.undoableEdits;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import nl.rug.oop.grapheditor.metadata.ViewModel;
import nl.rug.oop.grapheditor.model.GraphModel;
import nl.rug.oop.grapheditor.model.objects.Edge;

/**
 * This allows the program to remove an edge, or add it back to
 * the model, if undone
 */
public class UndoableEditRemoveEdge extends AbstractUndoableEdit {
    private GraphModel model;
    private Edge edge;

    public UndoableEditRemoveEdge(GraphModel model, Edge edge)
    {
        super();
        this.model = model;
        this.edge = edge;
    }

    public void redo() throws CannotRedoException
    {
        model.removeEdge(edge);
    }

    public void undo() throws CannotUndoException
    {
        model.addEdge(edge);
    }
}