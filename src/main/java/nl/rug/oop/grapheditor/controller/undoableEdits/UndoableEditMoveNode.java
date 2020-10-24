package nl.rug.oop.grapheditor.controller.undoableEdits;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import java.awt.Point;

import nl.rug.oop.grapheditor.metadata.ViewModel;
import nl.rug.oop.grapheditor.model.GraphModel;
import nl.rug.oop.grapheditor.model.objects.Edge;
import nl.rug.oop.grapheditor.model.objects.Node;

/**
 * This class allows the program to keep track of the old and new
 * location of a freshly moved node. It can move it to the new one, if 
 * redone or the old one if undone.
 */
public class UndoableEditMoveNode extends AbstractUndoableEdit {
    private GraphModel model;
    private ViewModel vm;
    private Node node;
    private Point newLocation;
    private Point oldLocation;

    public UndoableEditMoveNode(
        GraphModel model,
        ViewModel vm,
        Node node,
        Point newLocation,
        Point oldLocation)
    {
        super();
        this.model = model;
        this.vm = vm;
        this.node = node;
        this.newLocation = newLocation;
        this.oldLocation = oldLocation;
    }

    public void redo() throws CannotRedoException
    {
        node.move(newLocation.x, newLocation.y);
    }

    public void undo() throws CannotUndoException
    {
        node.move(oldLocation.x, oldLocation.y);
    }
}