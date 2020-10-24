package nl.rug.oop.grapheditor.controller.actions;

import javax.swing.*;
import java.awt.event.ActionEvent;

import java.awt.event.KeyEvent;
import java.util.Observer;
import java.util.Observable;

import nl.rug.oop.grapheditor.metadata.ViewModel;
import nl.rug.oop.grapheditor.controller.undoableEdits.UndoableEditDoubleRemoveEdge;
import nl.rug.oop.grapheditor.controller.undoableEdits.UndoableEditRemoveEdge;
import nl.rug.oop.grapheditor.model.GraphModel;
import nl.rug.oop.grapheditor.model.objects.Edge;

public class ActionRemoveEdge extends AbstractAction implements Observer {
    private GraphModel model;
    private ViewModel vm;
    /**
     * This class allows is user to to trigger the action required to remove an edge,
     * by clicking a button or the keyboard shortcut
     */
    public ActionRemoveEdge(GraphModel model, ViewModel vm) {
        super("REMOVE EDGE     Alt+Minus");
        this.model = model;
        this.vm = vm;
        putValue(Action.MNEMONIC_KEY, KeyEvent.VK_MINUS);
        setEnabled(false);
        model.addObserver(this);
        vm.addObserver(this);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(vm.isEdgeSelected()) {
            for(Edge item: model.getEdges())
            {
                if(item.getStart() == vm.getSelectedEdge().getEnd() ||
                    item.getEnd() == vm.getSelectedEdge().getStart()) 
                {
                    UndoableEditDoubleRemoveEdge editRemoveEdge = new UndoableEditDoubleRemoveEdge(
                    model, 
                    vm.getSelectedEdge(),
                    item);

                    editRemoveEdge.redo();
                    model.addEdit(editRemoveEdge);
                    vm.deselectNode();
                    vm.deselectEdge();

                    return;
                }
            }
            UndoableEditRemoveEdge editRemoveEdge = new UndoableEditRemoveEdge(
                model, 
                vm.getSelectedEdge());
            editRemoveEdge.redo();
            model.addEdit(editRemoveEdge);
            vm.deselectNode();
            vm.deselectEdge();
        } 
        else if(vm.isNodeSelected()) {
            vm.setRemovingEdge(true);
        }
    }

    @Override
    public void update(Observable o, Object arg)
    {
        setEnabled(vm.isEdgeSelected() || (vm.isNodeSelected() && model.getEdges().size() > 0));
    }
    
}