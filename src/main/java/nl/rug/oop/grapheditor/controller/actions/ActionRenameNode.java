package nl.rug.oop.grapheditor.controller.actions;

import nl.rug.oop.grapheditor.metadata.ViewModel;
import nl.rug.oop.grapheditor.controller.undoableEdits.UndoableEditRenameNode;
import nl.rug.oop.grapheditor.model.GraphModel;
import nl.rug.oop.grapheditor.model.objects.Node;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Observer;
import java.util.Observable;

public class ActionRenameNode extends AbstractAction implements Observer {
    private GraphModel model;
    private ViewModel vm;
    /**
     * This class allows is user to to trigger the methods that are involved in the process of renaming a node,
     * by clicking a button or the keyboard shortcut
     */
    public ActionRenameNode(GraphModel model, ViewModel vm) {
        super("RENAME NODE     Alt+R");
        this.model = model;
        this.vm = vm;
        putValue(Action.MNEMONIC_KEY, KeyEvent.VK_R);
        setEnabled(false);
        model.addObserver(this);
        vm.addObserver(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String newName = "", oldName = "";
        if(!vm.isNodeSelected()) return;
        Node node = vm.getSelectedNode();
        try {
            newName = JOptionPane.showInputDialog("Insert new name for the node");
            if (!newName.isEmpty()){
                oldName = node.getName();
                UndoableEditRenameNode editRenameNode = new UndoableEditRenameNode(
                    model, 
                    vm, 
                    node,
                    oldName,
                    newName);
                editRenameNode.redo();
                model.addEdit(editRenameNode);
                vm.deselectNode();
            }
        }catch (NullPointerException ex){
            System.out.println("Cancel selected");
        }
    }
    
    @Override
    public void update(Observable o, Object arg)
    {
        setEnabled(vm.isNodeSelected());
    }
}
