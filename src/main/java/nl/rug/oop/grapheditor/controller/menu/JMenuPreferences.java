package nl.rug.oop.grapheditor.controller.menu;

import nl.rug.oop.grapheditor.metadata.ViewModel;
import nl.rug.oop.grapheditor.controller.actions.ActionModeOff;
import nl.rug.oop.grapheditor.controller.actions.ActionModeOn;
import nl.rug.oop.grapheditor.controller.actions.ActionShowDirections;
import nl.rug.oop.grapheditor.controller.actions.ActionShowDirectionsOff;
import nl.rug.oop.grapheditor.model.GraphModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

public class JMenuPreferences extends JMenu implements Observer{
    private ViewModel vm;
    public JMenuPreferences(String name, GraphModel model, ViewModel vm) {
        super(name);
        this.vm = vm;

        add(new AbstractMenuItem(
            "DIRECTED GRAPH VIEW",
            new ActionShowDirections(vm),
            KeyEvent.VK_8));
        add(new AbstractMenuItem(
            "UNDIRECTED GRAPH VIEW",
            new ActionShowDirectionsOff(vm),
            KeyEvent.VK_9));
        vm.addObserver(this);
        setProperties();
        setLocation(100,100);
    }

    private void setProperties(){
        setForeground(Color.white);
        setBackground(new Color(60, 63, 65));
        Font bigFont = new Font( null, Font.BOLD, 16 );
        setFont(bigFont);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (vm.getMode())
            setForeground(Color.black);
        else
            setForeground(Color.white);
    }
}
