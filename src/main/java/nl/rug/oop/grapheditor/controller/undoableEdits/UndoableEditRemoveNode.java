package nl.rug.oop.grapheditor.controller.undoableEdits;

import java.util.ArrayList;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import nl.rug.oop.grapheditor.metadata.ViewModel;
import nl.rug.oop.grapheditor.model.GraphModel;
import nl.rug.oop.grapheditor.model.objects.Edge;
import nl.rug.oop.grapheditor.model.objects.Node;

/**
 * This allows the program to remove a node with all its associated
 * nodes and add the node and edges back to the model, if needed.
 */
public class UndoableEditRemoveNode extends AbstractUndoableEdit {
    private GraphModel model;
    private Node node;
    private ArrayList<Edge> edges;

    public UndoableEditRemoveNode(GraphModel model, Node node)
    {
        super();
        this.model = model;
        this.node = node;
        this.edges = new ArrayList<Edge>();
    }

    public void redo() throws CannotRedoException
    {
        edges.clear();
        for(Edge item: model.getEdges())
        {
            if(item.getStart() == node || item.getEnd() == node)
                edges.add(item);
        }
        model.removeNode(node);
    }

    public void undo() throws CannotUndoException
    {
        model.addNode(node);
        model.addEdges(edges);
    }
}