package nl.rug.oop.grapheditor.controller.menu;

import nl.rug.oop.grapheditor.metadata.ViewModel;
import nl.rug.oop.grapheditor.controller.actions.ActionModeOff;
import nl.rug.oop.grapheditor.controller.actions.ActionModeOn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

/**
 * One of three Jmenu's, this one hold the Super Mode and Dark Mode jmenu items
 */
public class JMenuMode extends JMenu implements Observer {
    private ViewModel vm;
    public JMenuMode(String name, ViewModel vm) {
        super(name);
        this.vm = vm;
        setEnabled(vm.getLoad());

        add(new AbstractMenuItem("SUPER MODE", new ActionModeOn(vm), KeyEvent.VK_1));
        add(new AbstractMenuItem("DARK MODE", new ActionModeOff(vm), KeyEvent.VK_2));

        setProperties();
        setLocation(100,100);
        vm.addObserver(this);
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
