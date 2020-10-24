package nl.rug.oop.grapheditor.controller.buttons;

import javax.swing.*;
import java.awt.*;

public class AbstractButton extends JButton {
    /**
     * This class is used to create all buttons. it takes in:
     * @param a an abstract action
     * @param tooltip tooltip i.e explanation
     */
    public AbstractButton(AbstractAction a, String tooltip ) {
        super(a);
        setButtonProperties(tooltip);
    }

    public void setButtonProperties(String tooltip){
        setLayout(null);
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setToolTipText(tooltip);
        setPreferredSize(new Dimension(230,35));
        setForeground(Color.white);
        setBackground(new Color(91, 132, 164));
    }
}
