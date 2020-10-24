package nl.rug.oop.grapheditor.io;

import nl.rug.oop.grapheditor.metadata.ViewModel;
import nl.rug.oop.grapheditor.model.GraphModel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;

public class LoadIcons {
    private static BufferedImage myImage;
    private static Image img;
    private static Image image;
    private static File musicPath;

    /**
     * This class loads all images and audio files, and check if something went wrong during loading.
     * If so the option to engage super mode is unavailable.
     * @param vm view model.
     */
    public static void load(ViewModel vm) {
        boolean flag = true;
        String dir = Paths.get("").toAbsolutePath().toString();
        System.out.println("dir:  " + dir);
        File cat = new File(dir + File.separator +"icons" + File.separator + "cat.png");
        File background =  new File(dir + File.separator +"icons" + File.separator + "background.png");
        File rainbow = new File(dir + File.separator +"icons" + File.separator + "rainbow.png");
        try {
            musicPath = new File(dir + File.separator +"sounds" + File.separator + "cat.wav");
            myImage = ImageIO.read(cat);
            img = new ImageIcon(String.valueOf(background)).getImage();
            image = Toolkit.getDefaultToolkit().getImage(String.valueOf(rainbow));

        } catch (IOException e) {
            flag = false;
        }
        if (!musicPath.exists() || !cat.exists() || !background.exists()){
            flag =  false;
            System.out.println("Could not load data for super mode");
        }
        vm.setLoad(flag);
    }

    public static BufferedImage getNewImage() {
        return myImage;
    }

    public static Image getImg() {
        return img;
    }

    public static File getMusicPath() { return musicPath; }

    public static Image getImage() { return image; }
}
