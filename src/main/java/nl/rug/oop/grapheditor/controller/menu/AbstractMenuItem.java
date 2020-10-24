package nl.rug.oop.grapheditor.controller.menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;

/**
 * This class is used to create all Jmenu Items.
 */
class AbstractMenuItem extends JMenuItem {
    AbstractMenuItem( String a, AbstractAction action, int key) {
        super(a);
        addActionListener(action);
        setAccelerator(KeyStroke.getKeyStroke(key, InputEvent.CTRL_MASK));
        setForeground(Color.white);
        setBackground(new Color(60, 63, 65));

    }
}
