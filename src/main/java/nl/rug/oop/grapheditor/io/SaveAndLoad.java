package nl.rug.oop.grapheditor.io;

import com.fasterxml.jackson.databind.JsonNode;
import nl.rug.oop.grapheditor.model.GraphModel;
import nl.rug.oop.grapheditor.model.objects.Edge;
import nl.rug.oop.grapheditor.model.objects.Node;

import java.awt.*;
import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.Observable;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.JOptionPane;

/**
 * This is a custom save and load class, I discussed it with the previous TA and we agreed that it would be
 * nice to have readable files filled with the model data, instead of reusing the save and load files from
 * RPG, which would produce unreadable files. Therefore this class can save and load data to/from a json file.
 */
public class SaveAndLoad extends Observable {
    private static File file;
    private static String filepath;

    /**
     * gets all integers needed to save the model and writes it to a json file
     */
    public static void saveModel(GraphModel model, Boolean saveAs) throws IOException {
        int cnt = 1;
        if(file == null || saveAs) {           
            Selector select = new Selector();
            select.selectDirectory();
            PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:*.json");
            if( matcher.matches(Paths.get(select.getFc().getSelectedFile().getAbsolutePath()))){
                System.out.println("match");
            }
            filepath = select.getFc().getSelectedFile().getAbsolutePath() + ".json";
            System.out.println(filepath);
            file = new File(filepath); 
        }
        if(file.createNewFile()){
            System.out.println(filepath +" File Created");
        }else System.out.println("File "+ filepath +" already exists");

        JSONObject obj = new JSONObject();
        obj.put("N", model.getNodes().size());
        obj.put("E", model.getEdges().size());
        for (Node node : model.getNodes()) {
            obj.put("x" + cnt, node.getRectangle().x);
            obj.put("y" + cnt, node.getRectangle().y);
            obj.put("width" + cnt, node.getRectangle().width);
            obj.put("height" + cnt, node.getRectangle().height);
            obj.put("name" + cnt, node.getName());
            cnt++;
        }
        cnt = 1;
        for (Edge edge : model.getEdges()) {
            obj.put("start" + cnt, getIndex(edge.getStart(), model));
            obj.put("end" + cnt, getIndex(edge.getEnd(), model));
        }
        System.out.println(obj);
        FileWriter writer = new FileWriter(file);
        writer.write(obj.toJSONString());
        writer.flush();
        System.out.println("Successfully saved graph.");
        writer.close();
    }

    /**
     * Used to load the model from a json file which is selected using the selector (jfilchooser).
     * @return the loaded model
     */
    public static GraphModel loadModel(){
        Selector select = new Selector();
        select.selectFile();
        file = new File(select.getFc().getSelectedFile().getAbsolutePath());
        GraphModel newModel = new GraphModel();
        JSONParser parser = new JSONParser();

        try {
            return getModel(newModel, parser, file);
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Same as loadModel but this method uses a path given from the input arguments to load the model
     * @param filepath the path of the file
     * @return the new model
     */
    public static GraphModel loadModelFromFile(String filepath) {
        GraphModel model = new GraphModel();
        JSONParser parser = new JSONParser();

        try {
            file = new File(filepath);
            return getModel(model, parser, file);
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File does not exist. Try again.");
            loadModel();
            return null;
        } catch (IndexOutOfBoundsException e)
        {
            System.out.println("File is empty. Try again.");
            loadModel();
            return null;
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Does some operations which are used in the two loading methods
     * @param model graphmodel
     * @param parser the json parser
     * @param loadFile the file we want to load
     * @return model
     */
    private static GraphModel getModel(GraphModel model, JSONParser parser, File loadFile) throws IOException, ParseException {
        FileReader reader =  new FileReader(loadFile.getAbsolutePath());
        Object obj = parser.parse(reader);
        String source = obj.toString();
        JsonNode node = json.parse(source);
        loadNodes(model, node);
        loadEdges(model, node);
        return model;
    }

    /**
     * Loads all nodes by using the values in the values array.
     */
    private static void loadNodes(GraphModel newModel, JsonNode n) throws IndexOutOfBoundsException {
        Node node;
        int cnt = 1;
        for (int i = 0; i < n.get("N").asInt(); i++){
            Rectangle r = new Rectangle(n.get("x"+ cnt).asInt(), n.get("y"+cnt).asInt(),
                    n.get("height"+cnt).asInt(), n.get("width" + cnt).asInt());
            node = new Node(r,n.get("name"+cnt).asText());
            newModel.getNodes().add(node);
            cnt++;
        }
    }

    /**
     * Loads all edges, by going to index start in the values array
     * since this is where the values for the edges start. And from there
     * it uses get node to find the start en end node.
     */
    private static void loadEdges(GraphModel newModel, JsonNode n) throws IndexOutOfBoundsException {
        Edge edge;
        int cnt = 1;
        for (int i = 0; i < n.get("E").asInt(); i++){
            edge = new Edge(getNode(n.get("start"+cnt).asInt(), newModel), getNode(n.get("end"+cnt).asInt(),newModel));
            newModel.getEdges().add(edge);
        }
    }

    /**
     * Gets index of node
     * @return the index
     */
    private static int getIndex(Node node, GraphModel model){
        int cnt = 0;
        for (Node item: model.getNodes() ){
            if (item.equals(node)){
                return cnt;
            }
            cnt++;
        }
        throw new IllegalArgumentException("This node is not in the list (getindex)");
    }

    /**
     * Gets the node at index.
     * @param index the index of the particular node
     * @param model the graphmodel
     * @return model
     */
    private static Node getNode(int index, GraphModel model){
        int cnt = 0;
        for (Node item: model.getNodes()){
            if (cnt == index){
                return item;
            }
            cnt++;
        }
        throw new IllegalArgumentException("This node is not in the list (getnode) ");
    }

    public static Boolean isModelSaved(GraphModel model)
    {
        return !model.canUndo();
    }


    public static void optionalSave(GraphModel model) throws IOException
    {
        if(isModelSaved(model)) return;
        
        int reply = JOptionPane.showConfirmDialog(
            null, 
            "You have not saved your last changes. Do you want to save them now?", 
            "Unsaved changes", 
            JOptionPane.YES_NO_OPTION);
        
        if(reply == JOptionPane.YES_OPTION) saveModel(model, false);
    }

    public static void createNewFile()
    {
        file = null;
    }
}