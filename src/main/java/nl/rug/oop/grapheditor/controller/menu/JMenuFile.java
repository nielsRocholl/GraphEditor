package nl.rug.oop.grapheditor.controller.menu;

import nl.rug.oop.grapheditor.metadata.ViewModel;
import nl.rug.oop.grapheditor.controller.actions.ActionCreateNew;
import nl.rug.oop.grapheditor.controller.actions.ActionLoad;
import nl.rug.oop.grapheditor.controller.actions.ActionSave;
import nl.rug.oop.grapheditor.controller.actions.*;
import nl.rug.oop.grapheditor.model.GraphModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

/**
 * One of three Jmenu's, this one holds the load, save, create new, an save as menuItems.
 */
public class JMenuFile extends JMenu implements Observer {
    private ViewModel vm;
    public JMenuFile(String name, GraphModel model, ViewModel vm) {
        super(name);
        this.vm = vm;
    
        add(new AbstractMenuItem("LOAD", new ActionLoad(model), KeyEvent.VK_L));
        add(new AbstractMenuItem("SAVE", new ActionSave(model), KeyEvent.VK_S));
        add(new AbstractMenuItem("CREATE NEW", new ActionCreateNew(model), KeyEvent.VK_N));
        add(new AbstractMenuItem("SAVE AS", new ActionSaveAs(model), KeyEvent.VK_A));

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
        if (vm.getMode()){
            setForeground(Color.black);
        } else {
            setForeground(Color.white);
        }
    }
}
