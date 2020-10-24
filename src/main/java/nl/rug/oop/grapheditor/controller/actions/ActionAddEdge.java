package nl.rug.oop.grapheditor.controller.actions;

import nl.rug.oop.grapheditor.metadata.ViewModel;
import nl.rug.oop.grapheditor.model.GraphModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Observer;
import java.util.Observable;

/**
 * This class allows is user to to trigger the add edge method,
 * by clicking a button or the keyboard shortcut
 */
public class ActionAddEdge extends AbstractAction implements Observer {
    private GraphModel model;
    private ViewModel vm;

    public ActionAddEdge(GraphModel model, ViewModel vm) {
        super("ADD EDGE        Alt+E");
        this.model = model;
        this.vm = vm;
        putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
        setEnabled(false);
        model.addObserver(this);
        vm.addObserver(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(vm.isNodeSelected()) {
            vm.setAddingEdge(true);
        }
    }
    
    @Override
    public void update(Observable o, Object arg)
    {
        setEnabled(vm.isNodeSelected() && model.getNodes().size() > 1);
    }
}
