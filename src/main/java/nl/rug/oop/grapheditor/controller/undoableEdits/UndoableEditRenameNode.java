package nl.rug.oop.grapheditor.controller.undoableEdits;

import javax.swing.JOptionPane;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import java.awt.Point;

import nl.rug.oop.grapheditor.metadata.ViewModel;
import nl.rug.oop.grapheditor.model.GraphModel;
import nl.rug.oop.grapheditor.model.objects.Edge;
import nl.rug.oop.grapheditor.model.objects.Node;

/**
 * This allows the program to keep track of the new and old name of a
 * freshly renamed node. If undone, it could set it back to the old name
 * or the new name, if redone.
 */
public class UndoableEditRenameNode extends AbstractUndoableEdit {
    private GraphModel model;
    private ViewModel vm;
    private Node node;
    private String newName;
    private String oldName;

    public UndoableEditRenameNode(
        GraphModel model,
        ViewModel vm, 
        Node node,
        String oldName,
        String newName)
    {
        super();
        this.model = model;
        this.vm = vm;
        this.node = node;
        this.oldName = oldName;
        this.newName = newName;
    }

    @Override
    public void redo() throws CannotRedoException
    {
        node.setName(newName);
    }

    public void undo() throws CannotUndoException
    {
        node.setName(oldName);
    }
}