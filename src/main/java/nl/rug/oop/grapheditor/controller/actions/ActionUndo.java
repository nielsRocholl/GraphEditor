package nl.rug.oop.grapheditor.controller.actions;

import javax.swing.AbstractAction;
import javax.swing.undo.CannotRedoException;

import nl.rug.oop.grapheditor.metadata.ViewModel;
import nl.rug.oop.grapheditor.model.GraphModel;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;


public class ActionUndo extends AbstractAction implements Observer {
    private GraphModel model;
    /**
     * This class allows is user to to trigger the undo method,
     * by clicking a button
     */
    public ActionUndo(GraphModel model) {
        super("Undo");
        this.model = model;
        //putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
        setEnabled(false);
        model.addObserver(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if(model.canUndo())
            model.undo();
    }

    @Override
    public void update(Observable o, Object arg)
    {
        setEnabled(model.canUndo());
    }
}