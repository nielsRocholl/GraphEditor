package nl.rug.oop.grapheditor.view;

import java.awt.*;
import java.awt.geom.*;
import java.lang.Object;

import javax.swing.*;

import nl.rug.oop.grapheditor.model.objects.Node;

public class Arrow {

    /** 
     * An encapsulated class, designed to hold 2D vectors
     * Unlike point, it can hold floating point locations,
     * which is important when working with geometric transformations
     */
    private static class Vector2d
    {
        private double x, y;

        // Constructors for integers and floating point values
        public Vector2d(int x, int y)
        {
            this.x = (double)x;
            this.y = (double)y;
        }

        public Vector2d(double x, double y)
        {
            this.x = x;
            this.y = y;
        }

        // Getters and setters
        public double getX() { return x; }

        public double getY() { return y; }

        public void setX(double a) { x = a; }

        public void setY(double a) { y = a; }

        /**
         * Retrieves the length of the vector
         */
        private double length()
        {
            return Math.sqrt(x*x + y*y);
        }

        /**
         * Scales the vector TO a certain length. It does not scale it
         * by a certain factor
         * @param s The new size of the vector
         * @return
         */
        private Vector2d scale(double s)
        {
            double length = this.length();
            double vecUnitX = x / length;
            double vecUnitY = y / length;

            return new Vector2d(vecUnitX * s, vecUnitY * s);

        }

        /**
         * It uses basic CG logic to derive a vector perpendicular to
         * the current one with a size s
         * @param s The size of the returned vector
         * @return
         */
        private Vector2d perp(double s)
        {
            if(x == 0.0) return new Vector2d(s, 0.0);
            if(y == 0.0) return new Vector2d(0.0, s);
            Vector2d vecUnit = this.scale(1.0);
            double vecPerpY = -vecUnit.getX()/vecUnit.getY();
            Vector2d perp = new Vector2d(1.0, vecPerpY);

            return perp.scale(s);
        }

    }

    /**
     * A static function used to draw arrows from one rectangle to 
     * another
     * @param g The Graphics2D object
     * @param rectStart the starting rectangle
     * @param rectEnd the ending rectangle
     */
    public static void draw (Graphics2D g, Rectangle rectStart, Rectangle rectEnd)
    {
        // Paramaters of the arrow head
        double offsetSide = 5.0;
        double offsetTop = 7.0;

        /* The line is already drawn in the panel, so just the arrow head
        needs to be drawn.*/

        /* The tip is the place of intesection between the edge connecting
        the two rectangle centers and the ending rectangle
        */
        Point tip = intersect(rectStart, rectEnd);

        /* The starting point is the center of the starting rectangle */
        Point start = new Point(
            rectStart.x + rectStart.width/2,
            rectStart.y + rectStart.height/2);

        /* 
        Using three vectors to find the other three points of the arrow head
        polygon.
        */
        Vector2d vecDir = new Vector2d(tip.x - start.x, tip.y - start.y);
        Vector2d vecUnitPerp = vecDir.perp(offsetSide);
        Vector2d centralVec = vecDir.scale(vecDir.length() - offsetTop);

        /* Finding the three points and storing them as vectors */
        Vector2d undertip = new Vector2d(
            centralVec.x + start.x, 
            centralVec.y + start.y);

        Vector2d left = new Vector2d(
            undertip.x + vecUnitPerp.x, 
            undertip.y + vecUnitPerp.y);
    
        Vector2d right = new Vector2d(
            undertip.x - vecUnitPerp.x, 
            undertip.y - vecUnitPerp.y);

        /* Creating a polygon with the points, converted back to 
        integer coordinates */
        Polygon arrowHead = new Polygon();  
        arrowHead.addPoint(
            (int)Math.round(undertip.x), 
            (int)Math.round(undertip.y));

        arrowHead.addPoint(
            (int)Math.round(left.x), 
            (int)Math.round(left.y));

        arrowHead.addPoint(
            (int)Math.round(tip.x), 
            (int)Math.round(tip.y));

        arrowHead.addPoint(
            (int)Math.round(right.x), 
            (int)Math.round(right.y));

        // Drawing and filling the newly-created polygon
        g.draw(arrowHead);
        g.fill(arrowHead);
    }

    /**
     * Using geometric transformation to get the intersection point of the
     * line between the centers of the two rectangles (a.k.a, the drawn edge)
     * and the second rectangle. This is where the tip of the arrow head should be
     * @param rectStart The rectangle of the first node
     * @param rectEnd The rectangle of the second node
     * @return the point of intersection in integer coordinates
     */
    private static Point intersect(Rectangle rectStart, Rectangle rectEnd)
    {
        Point a = new Point(
            rectStart.x + rectStart.width/2,
            rectStart.y + rectStart.height/2);
        Point b = new Point(
            rectEnd.x + rectEnd.width/2,
            rectEnd.y + rectEnd.height/2);

        double xIntersect = -1.0, yIntersect = -1.0;

        if(a.x == b.x)
        {
            if(a.y > b.y) return new Point(a.x, b.y + rectEnd.height / 2);
            else return new Point(a.x, b.y - rectEnd.height / 2);
        }

        if(a.y == b.y)
        {
            if(a.x > b.x) return new Point(b.x + rectEnd.width / 2, a.y);
            else return new Point(b.x - rectEnd.width / 2, a.y);
        }

        double slope = (double)(a.y - b.y) / (a.x - b.x);

        if(-rectEnd.height / 2.0 <= slope * rectEnd.width / 2.0 && 
            rectEnd.height / 2.0 >= slope * rectEnd.width / 2.0)
        {
            // Right edge
            if(a.x > b.x) {
                xIntersect = b.x + rectEnd.width / 2.0;
                yIntersect = b.y + slope * rectEnd.width / 2.0;
            } 
            // Left edge
            else {
                xIntersect = b.x - rectEnd.width / 2.0;
                yIntersect = b.y - slope * rectEnd.width / 2.0;
            } 

        }
        if(-rectEnd.width / 2.0 <= (1.0/slope) * rectEnd.height / 2.0 && 
            rectEnd.width / 2.0 >= (1.0/slope) * rectEnd.height / 2.0)
        {
            // Bottom edge
            if(a.y > b.y) {
                yIntersect = b.y + rectEnd.height / 2.0;
                xIntersect = b.x + (1.0/slope) * rectEnd.height / 2.0;
            } 
            // Top edge
            else {
                yIntersect = b.y - rectEnd.height / 2.0; 
                xIntersect = b.x - (1.0/slope) * rectEnd.height / 2.0;
            }

        }

        return new Point(
            (int)Math.round(xIntersect), 
            (int)Math.round(yIntersect));
    }
}