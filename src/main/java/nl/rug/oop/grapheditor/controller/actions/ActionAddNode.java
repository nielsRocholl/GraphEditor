package nl.rug.oop.grapheditor.controller.actions;

import nl.rug.oop.grapheditor.controller.undoableEdits.UndoableEditAddNode;
import nl.rug.oop.grapheditor.model.GraphModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
/**
 * This class allows is user to to trigger the add Node method,
 * by clicking a button or the keyboard shortcut
 */
public class ActionAddNode extends AbstractAction {
    private GraphModel model;

    public ActionAddNode(GraphModel model) {
        super("ADD NODE        Alt+N");
        this.model = model;
        putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        UndoableEditAddNode editAddNode = new UndoableEditAddNode(model);
        editAddNode.redo();
        model.addEdit(editAddNode);
    }
}
