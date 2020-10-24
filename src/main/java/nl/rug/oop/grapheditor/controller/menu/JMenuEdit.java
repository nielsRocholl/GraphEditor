package nl.rug.oop.grapheditor.controller.menu;

import nl.rug.oop.grapheditor.metadata.ViewModel;
import nl.rug.oop.grapheditor.controller.actions.ActionCopyNode;
import nl.rug.oop.grapheditor.controller.actions.ActionModeOff;
import nl.rug.oop.grapheditor.controller.actions.ActionModeOn;
import nl.rug.oop.grapheditor.controller.actions.ActionPasteNode;
import nl.rug.oop.grapheditor.controller.actions.ActionRedo;
import nl.rug.oop.grapheditor.controller.actions.ActionUndo;
import nl.rug.oop.grapheditor.model.GraphModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

/**
 * One of three Jmenu's, this one holds the undo, redo, copy an pasye menu items.
 */
public class JMenuEdit extends JMenu implements Observer {
    private ViewModel vm;
    public JMenuEdit(String name, GraphModel model, ViewModel vm) {
        super(name);
        this.vm = vm;

        add(new AbstractMenuItem("UNDO", new ActionUndo(model), KeyEvent.VK_Z));
        add(new AbstractMenuItem("REDO", new ActionRedo(model), KeyEvent.VK_Y));addSeparator();

        add(new AbstractMenuItem("COPY", new ActionCopyNode(vm, model), KeyEvent.VK_C));
        add(new AbstractMenuItem( "PASTE", new ActionPasteNode(model), KeyEvent.VK_V));


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
        if (vm.getMode()) {
            setForeground(Color.black);
        } else {
            setForeground(Color.white);
        }
    }
}
