package nl.rug.oop.grapheditor.controller.undoableEdits;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import nl.rug.oop.grapheditor.metadata.Clipboard;
import nl.rug.oop.grapheditor.metadata.ViewModel;
import nl.rug.oop.grapheditor.model.GraphModel;
import nl.rug.oop.grapheditor.model.objects.Node;

/**
 * This allows the program to undo pasting nodes. When redone, it does
 * not add a previously created node, instead it pastes the node directly
 * from the clipboard. This allows the user to copy-paste nodes from 
 * earlier edits
 */
public class UndoableEditPasteNode extends AbstractUndoableEdit {
    private GraphModel model;
    private Node node;

    public UndoableEditPasteNode(GraphModel model)
    {
        super();
        this.model = model;
    }

    public void redo() throws CannotRedoException
    {
        node = Clipboard.paste(model);
    }

    public void undo() throws CannotUndoException
    {
        model.removeNode(node);
    }
}