package nl.rug.oop.grapheditor.controller.undoableEdits;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import java.awt.Point;
import java.awt.Dimension;

import nl.rug.oop.grapheditor.metadata.ResizeOption;
import nl.rug.oop.grapheditor.metadata.ViewModel;
import nl.rug.oop.grapheditor.model.GraphModel;
import nl.rug.oop.grapheditor.model.objects.Node;

/**
 * This allows the program to keep track of the old and new location and
 * size of a freshly resized node. It could set the node back to the old
 * ones, if undone, or the new ones, if redone. The location is also kept
 * in track because more often than not, resizing a node involves moving it
 * as well (if it's not resized from the bottom right corner)
 */
public class UndoableEditResizeNode extends AbstractUndoableEdit {
    private GraphModel model;
    private ViewModel vm;
    private Node node;
    private Point newLocation;
    private Point oldLocation;
    private Dimension newSize;
    private Dimension oldSize;
    private ResizeOption option;

    public UndoableEditResizeNode(
        GraphModel model,
        ViewModel vm,
        Node node,
        Point newLocation,
        Point oldLocation,
        Dimension newSize,
        Dimension oldSize)
    {
        super();
        this.model = model;
        this.vm = vm;
        this.node = node;
        this.newLocation = newLocation;
        this.oldLocation = oldLocation;
        this.newSize = newSize;
        this.oldSize = oldSize;
    }

    public void redo() throws CannotRedoException
    {
        node.move(newLocation.x, newLocation.y);
        node.resize(newSize.width, newSize.height);
    }

    public void undo() throws CannotUndoException
    {
        node.move(oldLocation.x, oldLocation.y);
        node.resize(oldSize.width, oldSize.height);
    }
}