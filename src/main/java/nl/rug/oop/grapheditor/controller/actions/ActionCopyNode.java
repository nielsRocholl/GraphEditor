package nl.rug.oop.grapheditor.controller.actions;

import nl.rug.oop.grapheditor.metadata.Clipboard;
import nl.rug.oop.grapheditor.metadata.ViewModel;
import nl.rug.oop.grapheditor.model.GraphModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

/**
 * This class allows is user to to trigger the copy method,
 * by clicking a button or the keyboard shortcut
 */
public class ActionCopyNode extends AbstractAction implements Observer {
    private ViewModel vm;
    private GraphModel model;

    public ActionCopyNode(ViewModel vm, GraphModel model) {
        super("COPY NODE        Ctrl+C");
        this.model = model;
        this.vm = vm;
        vm.addObserver(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(!vm.isNodeSelected()) return;

        Clipboard.copy(vm.getSelectedNode(), model);
    }

    @Override
    public void update(Observable o, Object arg)
    {
        setEnabled(vm.isNodeSelected());
    }
}
