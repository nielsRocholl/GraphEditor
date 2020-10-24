package nl.rug.oop.grapheditor.controller.actions;

import nl.rug.oop.grapheditor.metadata.ViewModel;
import nl.rug.oop.grapheditor.io.LoadIcons;
import nl.rug.oop.grapheditor.io.Mp3Player;

import javax.swing.*;
import java.awt.event.ActionEvent;
/**
 * This class allows is user to to trigger the setmode method,
 * by clicking a button or the keyboard shortcut
 */
public class ActionModeOn extends AbstractAction {
    private ViewModel vm;
    public ActionModeOn(ViewModel vm) {
        this.vm = vm;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(!vm.getLoad()) return;
        vm.setMode(true);
        Mp3Player.stopMusic();
        Mp3Player.playMusic(LoadIcons.getMusicPath());
    }
}
