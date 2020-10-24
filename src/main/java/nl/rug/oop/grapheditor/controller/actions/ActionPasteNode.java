package nl.rug.oop.grapheditor.controller.actions;

import nl.rug.oop.grapheditor.controller.undoableEdits.UndoableEditPasteNode;
import nl.rug.oop.grapheditor.metadata.Clipboard;
import nl.rug.oop.grapheditor.model.GraphModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;
/**
 * This class allows is user to to trigger the addEdit method,
 * by clicking a button or the keyboard shortcut
 */
public class ActionPasteNode extends AbstractAction implements Observer {
    private GraphModel model;

    public ActionPasteNode(GraphModel model) {
        super("PASTE NODE        Ctrl+V");
        this.model = model;
        model.addObserver(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(Clipboard.isEmpty()) return;

        UndoableEditPasteNode editPasteNode = 
            new UndoableEditPasteNode(model);
        editPasteNode.redo();
        model.addEdit(editPasteNode);
    }

    @Override
    public void update(Observable o, Object arg)
    {
        setEnabled(Clipboard.isEmpty());
    }
}
