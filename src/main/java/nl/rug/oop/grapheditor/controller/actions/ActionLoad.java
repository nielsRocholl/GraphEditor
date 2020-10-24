package nl.rug.oop.grapheditor.controller.actions;

import nl.rug.oop.grapheditor.io.SaveAndLoad;
import nl.rug.oop.grapheditor.model.GraphModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ActionLoad extends AbstractAction {
    private GraphModel model;
    private GraphModel newModel;

    /**
     * This class allows is user to to trigger the load method,
     * by clicking a button or the keyboard shortcut
     */
    public ActionLoad(GraphModel model) {
        super("LOAD");
        this.model = model;
    }


    /**
     * a few check to farify that a proper model is loaded.
     * @param e action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            SaveAndLoad.optionalSave(model);
            newModel = SaveAndLoad.loadModel();
            if(newModel != null) {
                model.checkAndReplaceModel(newModel);
                model.resetManager();
            }else {
                model.checkAndReplaceModel(model);
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Something went wrong while saving the file");
            System.out.println("Check if you have presented a valid directory");
            ex.printStackTrace();
        } catch (NullPointerException ex){
            System.out.println("Cancel selected");
        }
    }
}
