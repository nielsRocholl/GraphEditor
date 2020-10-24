package nl.rug.oop.grapheditor.view;

import nl.rug.oop.grapheditor.metadata.ViewModel;
import nl.rug.oop.grapheditor.model.GraphModel;
import javax.swing.*;
import java.awt.*;

/**
 * The standard GraphFrame, observing the JFrame
 */
public class GraphFrame extends JFrame {

    // Setting all the basic properties of the JFrame
    public GraphFrame(GraphModel model) {
        super("GRAPH EDITOR");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        Dimension frameSize = new Dimension(1200, 900);
        setPreferredSize(frameSize);

        ViewModel vm = new ViewModel(frameSize);
        GraphPanel panel = new GraphPanel(model, vm);
        add(panel);

        ButtonPanel bp = new ButtonPanel(model, vm);
        add(bp, BorderLayout.PAGE_START);
        pack();

        setJMenuBar(new MenuBar(model, vm));

        setLocationRelativeTo(null);
        setVisible(true);
    }

}
