package nl.rug.oop.grapheditor.model.objects;

import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import nl.rug.oop.grapheditor.metadata.ResizeOption;

/**
 * This class represents a rectangle, which is characterized by a rectangle and a name
 */
public class Node extends Observable {
    private Rectangle rectangle;
    private String name;

    private ArrayList<Integer> values;

    public Node(Rectangle rectangle, String name) {
        this.rectangle = rectangle;
        this.name = name;
        values = new ArrayList<>();
        setValues();
    }

    /**
     * Default constructor
     */
    public Node(int x, int y){
        this.rectangle = new Rectangle(x,y,100,100);
        this.name = "Node";;
        values = new ArrayList<>();
        setValues();
    }

    /**
     * Constructor with coordinates
     * 
     * @param x - location on the x-coordinate
     * @param y - location on the y-coordinate
     * @param w - width
     * @param h - height
     */
    public Node(int x, int y, int w, int h)
    {
        this.rectangle = new Rectangle(x, y, w, h);
        this.name = "Node";
        values = new ArrayList<>();
        setValues();
    }

    /**
     * Used for easy access of values.
     */
    public void setValues(){
        values.add(rectangle.x);
        values.add(rectangle.y);
        values.add(rectangle.width);
        values.add(rectangle.height);
    }

    public ArrayList<Integer> getValues() { return values; }

    public Rectangle getRectangle(){ return rectangle; }

    public void setName(String name) 
    { 
        this.name = name;
        update();
    }

    public String getName() { return name; }

    /**
     * Moving the node to a new location
     * @param x x-location
     * @param y y-location
     */
    public void move(int x, int y)
    {
        this.rectangle.setLocation(x, y);
        update();
    }

    /**
     * Resizing the node
     * @param w the new width
     * @param h the new height
     */
    public void resize(int w, int h)
    {
        this.rectangle.setSize(w, h);
        update();
    }

    /**
     * Resizing from different sides/corner requires different operations,
     * so there is a method for resizing with a given option
     * @param option The option
     * @param point The location of the cursor
     */
    public void resizeWithOption(ResizeOption option, Point point)
    {
        if(option == ResizeOption.NO_RESIZE) return;

        // It executes the logic for all options
        if(option == ResizeOption.BOTTOM_RIGHT_CORNER)
        {
            if(point.x <= rectangle.x) return;
            if(point.y <= rectangle.y) return;

            int width = point.x - rectangle.x;
            int height = point.y - rectangle.y;

            resize(width, height);
        }

        if(option == ResizeOption.BOTTOM_LEFT_CORNER)
        {
            if(point.x >= rectangle.x + rectangle.width) return;
            if(point.y <= rectangle.y) return;

            int width = rectangle.x + rectangle.width - point.x;
            int height = point.y - rectangle.y;

            move(point.x, rectangle.y);
            resize(width, height);
        }

        if(option == ResizeOption.TOP_RIGHT_CORNER)
        {
            if(point.x <= rectangle.x) return;
            if(point.y >= rectangle.y + rectangle.height) return;
            
            int width = point.x - rectangle.x;
            int height = rectangle.y + rectangle.height - point.y;

            move(rectangle.x, point.y);
            resize(width, height);
        }

        if(option == ResizeOption.TOP_LEFT_CORNER)
        {
            if(point.x >= rectangle.x + rectangle.width) return;
            if(point.y >= rectangle.y + rectangle.height) return;
            
            int width = rectangle.x + rectangle.width - point.x;
            int height = rectangle.y + rectangle.height - point.y;

            move(point.x, point.y);
            resize(width, height);
        }

        if(option == ResizeOption.TOP_SIDE)
        {
            if(point.y >= rectangle.y + rectangle.height) return;
            
            int height = rectangle.y + rectangle.height - point.y;

            move(rectangle.x, point.y);
            resize(rectangle.width, height);
        }

        if(option == ResizeOption.LEFT_SIDE)
        {
            if(point.x >= rectangle.x + rectangle.width) return;
            
            int width = rectangle.x + rectangle.width - point.x;

            move(point.x, rectangle.y);
            resize(width, rectangle.height);
        }

        if(option == ResizeOption.BOTTOM_SIDE)
        {
            if(point.y <= rectangle.y) return;

            int height = point.y - rectangle.y;

            resize(rectangle.width, height);
        }

        if(option == ResizeOption.RIGHT_SIDE)
        {
            if(point.x <= rectangle.x) return;

            int width = point.x - rectangle.x;

            resize(width, rectangle.height);
        }
    }

    public Boolean overlaps(Rectangle rect)
    {
        return rect.intersects(this.rectangle);
    }

    /**
     * Notifying the observers
     */
    public void update(){
        setChanged();
        notifyObservers();
    }
}
