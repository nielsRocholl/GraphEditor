package nl.rug.oop.grapheditor.controller.actions;

import nl.rug.oop.grapheditor.metadata.ViewModel;
import nl.rug.oop.grapheditor.controller.undoableEdits.UndoableEditRemoveNode;
import nl.rug.oop.grapheditor.model.GraphModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Observer;
import java.util.Observable;

public class ActionRemoveNode extends AbstractAction implements Observer {
    GraphModel model;
    private ViewModel vm;
    /**
     * This class allows is user to to trigger the action to remove a node,
     * by clicking a button or the keyboard shortcut
     */
    public ActionRemoveNode(GraphModel model, ViewModel vm) {
        super("REMOVE NODE     Alt+Delete");
        this.model = model;
        this.vm = vm;
        putValue(Action.MNEMONIC_KEY, KeyEvent.VK_DELETE);
        setEnabled(false);
        model.addObserver(this);
        vm.addObserver(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(vm.isNodeSelected()) {
            UndoableEditRemoveNode editRemoveNode = new UndoableEditRemoveNode(
                model, 
                vm.getSelectedNode());
            editRemoveNode.redo();
            model.addEdit(editRemoveNode);
            vm.deselectNode();
        }
    }

    @Override
    public void update(Observable o, Object arg)
    {
        setEnabled(vm.isNodeSelected());
    }
}
