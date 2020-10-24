package nl.rug.oop.grapheditor.model.objects;

import java.awt.Point;
import java.awt.Rectangle;
import java.lang.Math;

/**
 * This class represents an Edge, which is characterized by a starting node and an ending node.
 */
public class Edge {
    private Node start;
    private Node end;

    public Edge(Node start, Node end) {
        this.start = start;
        this.end = end;
    }

    public Node getStart() { return start; }

    public Node getEnd() { return end; }

    public void setStart(Node start) { this.start = start; }

    public void setEnd(Node end) { this.end = end; }

    public Boolean isOppositeTo(Edge edge)
    {
        return start == edge.getEnd() && end == edge.getStart();
    }

    public Boolean contains(Point p)
    {
        double THRESHOLD = 2.0;

        Rectangle startRect = start.getRectangle();
        Rectangle endRect = end.getRectangle();

        double x1 = startRect.getX() + startRect.getWidth() / 2;
        double y1 = startRect.getY() + startRect.getHeight() / 2;

        double x2 = endRect.getX() + endRect.getWidth() / 2;
        double y2 = endRect.getY() + endRect.getHeight() / 2;

        double x0 = p.getX();
        double y0 = p.getY();

        double num = (y2-y1) * x0 - (x2-x1) * y0 + x2 * y1 - y2 * x1;
        double denum = (y2-y1)*(y2-y1) + (x2-x1)*(x2-x1);

        double dist = Math.abs(num) / Math.sqrt(denum);

        if(dist < THRESHOLD) return true;
        return false;
    }

}
