package nl.rug.oop.grapheditor;

import nl.rug.oop.grapheditor.model.GraphModel;
import nl.rug.oop.grapheditor.view.GraphFrame;

import java.io.FileNotFoundException;
import java.io.IOException;

public class GraphEditor {

    public static void main(String[] args) throws IOException {
        GraphModel model;
        if(args.length == 0) {
            model = new GraphModel();
        }else {
            model = new GraphModel(args[0]);
        }
        new GraphFrame(model);
    }
}