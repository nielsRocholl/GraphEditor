package nl.rug.oop.grapheditor.controller.actions;

import nl.rug.oop.grapheditor.metadata.ViewModel;
import nl.rug.oop.grapheditor.io.LoadIcons;
import nl.rug.oop.grapheditor.io.Mp3Player;
import nl.rug.oop.grapheditor.model.GraphModel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ActionShowDirectionsOff extends AbstractAction {
    private ViewModel vm;
    public ActionShowDirectionsOff(ViewModel vm) {
        this.vm = vm;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO make the modes undoable
        vm.setDirected(false);
    }
}
