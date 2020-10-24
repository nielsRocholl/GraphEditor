package nl.rug.oop.grapheditor.controller.actions;

import nl.rug.oop.grapheditor.io.Mp3Player;
import nl.rug.oop.grapheditor.metadata.ViewModel;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * This class allows is user to to trigger the setMode method,
 * by clicking a button or the keyboard shortcut
 */
public class ActionModeOff  extends AbstractAction {
    private ViewModel vm;
    public ActionModeOff(ViewModel vm) {
        this.vm = vm;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Mp3Player.stopMusic();
        vm.setMode(false);
    }
}
