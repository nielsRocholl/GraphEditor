package nl.rug.oop.grapheditor.controller.actions;

import javax.swing.AbstractAction;
import javax.swing.undo.CannotRedoException;

import nl.rug.oop.grapheditor.model.GraphModel;

import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;


public class ActionRedo extends AbstractAction implements Observer {
    private GraphModel model;
    /**
     * This class allows is user to to trigger the redo method,
     * by clicking a button or the keyboard shortcut
     */
    public ActionRedo(GraphModel model) {
        super("Redo");
        this.model = model;
        setEnabled(false);
        model.addObserver(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        // TODO Figure out why the model can't check if the edits are redoable
        try{
            model.redo();
        }
        catch (CannotRedoException ex)
        {
            ex.printStackTrace();
        }
            
    }

    @Override
    public void update(Observable o, Object arg)
    {
        // TODO change the enability of undo/redo
        setEnabled(model.canRedo());
    }
}