package nl.rug.oop.grapheditor.io;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

/**
 * This class uses Jfile chooser in order to let a user select a location to save the model.
 */
class Selector extends JFileChooser {
    private JButton open;
    private JFileChooser fc;

    Selector(){
        open = new JButton();
        fc = new JFileChooser();
    }

    /**
     * allows the user to select a directory where the file should be saved and the user can enter a file name.
     */
    void selectDirectory(){
        fc.setCurrentDirectory(new java.io.File( "saved" + File.separator));
        fc.setDialogTitle("FILE CHOOSER!");
        FileNameExtensionFilter xmlfilter = new FileNameExtensionFilter(
                "json files (*.json)", "json");
        fc.setFileFilter(xmlfilter);
        int response = fc.showSaveDialog(this);
        if (response == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fc.getSelectedFile();
            System.out.println("Save as file: " + selectedFile.getAbsolutePath());
        } else if (response == JFileChooser.CANCEL_OPTION) {
            System.out.println("Cancel was selected");
        }
        System.out.println(fc.getSelectedFile().getAbsolutePath());
    }

    /**
     * Allows the user to select A file which should be loaded.
     */
    void selectFile(){
        JLabel lblFileName = new JLabel();
        fc.setCurrentDirectory(new java.io.File("saved" + File.separator));
        fc.setDialogTitle("FILE CHOOSER");
        FileNameExtensionFilter xmlfilter = new FileNameExtensionFilter(
                "json files (*.json)", "json");
        fc.setFileFilter(xmlfilter);
        int response = fc.showOpenDialog(this);
        if (response == JFileChooser.APPROVE_OPTION) {
            lblFileName.setText(fc.getSelectedFile().toString());
        }else {
            lblFileName.setText("the file operation was cancelled");
        }
        System.out.println(fc.getSelectedFile().getAbsolutePath());
    }

    JFileChooser getFc() { return fc; }


}
