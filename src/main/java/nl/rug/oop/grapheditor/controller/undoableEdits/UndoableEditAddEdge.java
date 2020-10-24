package nl.rug.oop.grapheditor.controller.undoableEdits;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import nl.rug.oop.grapheditor.metadata.ViewModel;
import nl.rug.oop.grapheditor.model.GraphModel;
import nl.rug.oop.grapheditor.model.objects.Edge;

/**
 * This class allows the user to add an edge and remove the same edge,
 * if the action is undone
 */
public class UndoableEditAddEdge extends AbstractUndoableEdit {
    private GraphModel model;
    private ViewModel vm;
    private Edge edge;

    public UndoableEditAddEdge(GraphModel model, ViewModel vm, Edge edge)
    {
        super();
        this.model = model;
        this.vm = vm;
        this.edge = edge;
    }

    public void redo() throws CannotRedoException
    {
        model.addEdge(edge);
        vm.setAddingEdge(false);
    }

    public void undo() throws CannotUndoException
    {
        model.removeEdge(edge);
    }
}