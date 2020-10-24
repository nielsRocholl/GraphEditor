package nl.rug.oop.grapheditor.controller.actions;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

import nl.rug.oop.grapheditor.io.SaveAndLoad;
import nl.rug.oop.grapheditor.model.GraphModel;

public class ActionCreateNew extends AbstractAction {
    private GraphModel model;

    public ActionCreateNew(GraphModel model)
    {
        super("CREATE NEW");
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        try {
            SaveAndLoad.optionalSave(model);
        } catch(IOException ex)
        {
            System.out.println("Something went wrong while saving the file");
            System.out.println("Check if you have presented a valid directory");
            ex.printStackTrace();
        } catch (NullPointerException ex){
//            System.out.println("");
        }
        model.startNew();
        SaveAndLoad.createNewFile();
        model.resetManager();
    }
}