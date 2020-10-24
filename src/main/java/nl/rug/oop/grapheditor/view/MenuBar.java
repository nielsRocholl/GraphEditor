package nl.rug.oop.grapheditor.view;

import nl.rug.oop.grapheditor.io.LoadIcons;
import nl.rug.oop.grapheditor.metadata.ViewModel;
import nl.rug.oop.grapheditor.controller.menu.JMenuEdit;
import nl.rug.oop.grapheditor.controller.menu.JMenuFile;
import nl.rug.oop.grapheditor.controller.menu.JMenuMode;
import nl.rug.oop.grapheditor.controller.menu.JMenuPreferences;
import nl.rug.oop.grapheditor.model.GraphModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

/**
 * A class that extends JMenuBar, standard menu bar
 */
public class MenuBar extends JMenuBar implements Observer {
    private GraphModel model;
    private ViewModel vm;

    public MenuBar(GraphModel model, ViewModel vm) {
        // Initializing and adding the menus
        this.model = model;
        this.vm = vm;
        setLayout(null);
        add(new JMenuFile("<HTML><U>FILE</U></HTML>", model, vm));
        add(new JMenuEdit("<HTML><U>EDIT</U></HTML>", model, vm));
        add(new JMenuMode("<HTML><U>MODE</U></HTML>", vm));
        add(new JMenuPreferences("<HTML><U>PREFERENCES</U></HTML>", model, vm));
        setButtonMenuProperties();
    }

    /**
     * Setting the default properties
     */
    public void setButtonMenuProperties(){
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setBackground(new Color(60, 63, 65));
    }

    /**
     * The background of the menu is changed if in a super mode
     */
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if (vm.getMode()){
            setBackground(new Color(1, 68, 121));
            g.drawImage(LoadIcons.getImage(),0,0,this);
        } else {
            setBackground(new Color(60, 63, 65));
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        doLayout();
        repaint();
        revalidate();
    }
}
