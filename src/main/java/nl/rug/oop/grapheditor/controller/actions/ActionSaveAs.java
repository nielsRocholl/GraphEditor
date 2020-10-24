package nl.rug.oop.grapheditor.controller.actions;

import nl.rug.oop.grapheditor.io.SaveAndLoad;
import nl.rug.oop.grapheditor.model.GraphModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class ActionSaveAs extends AbstractAction {
    private GraphModel model;
    /**
     * This class allows is user to to trigger the static saveModel method,
     * by clicking a button or the keyboard shortcut
     */
    public ActionSaveAs(GraphModel model) {
        super("SAVE AS        Ctrl+A");
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            SaveAndLoad.saveModel(model, true);
            model.resetManager();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (NullPointerException ex){
            System.out.println("Cancel selected");
        }
    }
}
