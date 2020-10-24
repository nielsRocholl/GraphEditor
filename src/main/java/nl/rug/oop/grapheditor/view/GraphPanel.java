package nl.rug.oop.grapheditor.view;

import nl.rug.oop.grapheditor.controller.SelectionController;
import nl.rug.oop.grapheditor.metadata.ViewModel;
import nl.rug.oop.grapheditor.io.LoadIcons;
import nl.rug.oop.grapheditor.model.GraphModel;
import nl.rug.oop.grapheditor.model.objects.Edge;
import nl.rug.oop.grapheditor.model.objects.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Observable;
import java.util.Observer;

/**
 * The overarching GraphPanel. It observers the GraphModel and the ViewModel
 */
public class GraphPanel extends JPanel implements Observer {

    private GraphModel model;
    private ViewModel vm;

    /**
     * The constructor for the GraphPanel. It observes the model and 
     * viewmodel
     * @param model The GraphModel
     * @param vm The ViewModel
     */
    GraphPanel(GraphModel model, ViewModel vm) {
        setBackground(new Color(43, 43, 43));

        this.model = model;
        model.addObserver(this);

        this.vm = vm;
        vm.addObserver(this);

        // Every node is also observed by the panel
        for(Node item: model.getNodes()) item.addObserver(this);

        // Adds the selection controller and loads the icons
        SelectionController controller = new SelectionController(model, vm);
        addMouseListener(controller);
        addMouseMotionListener(controller);
        LoadIcons.load(vm);
    }

    /**
     * Used to draw the sweet ass background.
     */
    private void img(Graphics g) {
        g.drawImage(LoadIcons.getImg(), 0, 0, null);
    }

    /**
     * A private function for drawing the nodes, given a Graphics object
     */
    private void drawNodes(Graphics g)  {
        // Creates a 2D Graphic object
        Graphics2D g2d = (Graphics2D) g.create();

        for (Node node : model.getNodes()) {
            int size = 1;
            // Draws the rectangle
            g.draw3DRect(node.getRectangle().x, node.getRectangle().y, node.getRectangle().width, node.getRectangle().height, true);
            
            // Sets the color depending on whether the node is selected
            if(vm.isSelected(node)) g.setColor(Color.red);
            else g.setColor(Color.orange);
            g.fill3DRect(node.getRectangle().x, node.getRectangle().y, node.getRectangle().width, node.getRectangle().height, true);
            
            // Writes the name of the node in a resizeable font
            g.setFont(new Font("ComicSans", Font.BOLD, node.getRectangle().width / size));
            Rectangle2D r = g.getFontMetrics().getStringBounds(node.getName(), g);
            while (r.getWidth() > (node.getRectangle().width * 0.9)) {
                g.setFont(new Font("ComicSans", Font.BOLD, node.getRectangle().width / size++));
                r = g.getFontMetrics().getStringBounds(node.getName(), g);
            }

            g.setColor(Color.black);
            g.drawString(node.getName(), node.getRectangle().x + ((node.getRectangle().width / 2) - ((int) r.getWidth() / 2)),
                    node.getRectangle().y + ((node.getRectangle().height / 2) + (int) r.getHeight() / 3));
            
            // If super mode is on, it loads the icons over the nodes
            if (vm.getMode() && vm.getLoad()){
                Image newImage = LoadIcons.getNewImage().getScaledInstance(node.getRectangle().width, node.getRectangle().height, Image.SCALE_DEFAULT);
                g2d.drawImage(newImage, node.getRectangle().x, node.getRectangle().y, this);
            }
        }
    }

    /**
     * A private method for drawing the edges
     * @param g A Graphics object
     */
    private void drawEdges(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(3));

        for (Edge edge: model.getEdges()){

            // The selected edge is green, the other ones are grau
            if(vm.isSelected(edge))
                g.setColor(Color.green);
            else g.setColor(Color.lightGray);

            // It will also mark as green the edge, opposite to the selected one
            if(vm.isEdgeSelected())
            {
                if(edge.isOppositeTo(vm.getSelectedEdge()))
                g.setColor(Color.green);
            }

            // The line is drawn between the centers of the corresponding node rectangles
            Rectangle nodeStart = edge.getStart().getRectangle();
            Rectangle nodeEnd = edge.getEnd().getRectangle();
            g2.drawLine( nodeStart.x + (nodeStart.width/2),
                        nodeStart.y + (nodeStart.height/2),
                        nodeEnd.x + (nodeEnd.width/2),
                        nodeEnd.y + (nodeEnd.height/2));
            
            // If the user has turned direction mode on, the arrow heads are drawn
            if(vm.isDirected()) Arrow.draw(g2, nodeStart, nodeEnd);
        }

        /* If the user is currently adding an edge, a line is drawn between the
         selected node and the mouse cursor
        */
        if(vm.getAddingEdge())
        {
            Rectangle selected = vm.getSelectedNode().getRectangle();
            Point cursor = vm.getCursor();
            g2.drawLine( selected.x + (selected.width/2),
                selected.y + (selected.height/2),
                (int)cursor.getX(),
                (int)cursor.getY());
        }
    }

    /**
     * Painting the component involves adding the loaded icons (if loaded
     * and needed for super mode) and drawing the nodes and edges. The edges
     * are drawn before the nodes so that they are placed under.
     */
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if (vm.getMode() && vm.getLoad())
            img(g);
        drawEdges(g);
        drawNodes(g);
    }

    /**
     * Everytime the observable components are updated, it observes any
     * newly added nodes. This ensures all nodes are observed.
     */
    @Override
    public void update(Observable observable, Object o) {
        for(Node item: model.getNodes()) {
            item.addObserver(this);
        }
        repaint();
    }
}
