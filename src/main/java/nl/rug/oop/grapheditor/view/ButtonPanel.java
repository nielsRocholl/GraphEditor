package nl.rug.oop.grapheditor.view;

import nl.rug.oop.grapheditor.metadata.ViewModel;
import nl.rug.oop.grapheditor.controller.actions.*;
import nl.rug.oop.grapheditor.controller.buttons.AbstractButton;
import nl.rug.oop.grapheditor.model.GraphModel;

import javax.swing.*;
import java.awt.*;

/**
 * A class that extends JPanel, standard button panel
 */
public class ButtonPanel extends JPanel  {
    private GraphModel model;
    private ViewModel vm;

    public ButtonPanel(GraphModel model, ViewModel vm) {
        setBackground(new Color(43, 43, 43));
        this.model = model;
        this.vm = vm;
        addButtons();
    }

    private void addButtons() {
        
        // Adding all the buttons and their tooltips
        add(new AbstractButton(new ActionAddNode(model), "Add a new node to the model"));
        add(new AbstractButton(new ActionRemoveNode(model, vm), "Remove selected node"));
        add(new AbstractButton(new ActionAddEdge(model, vm), "Add an edge"));
        add(new AbstractButton(new ActionRemoveEdge(model, vm), "Remove selected edge"));
        add(new AbstractButton(new ActionRenameNode(model, vm), "Rename selected node"));
    }

}
