package nl.rug.oop.grapheditor.io;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

/**
 * A class for the sound effects
 */
public class Mp3Player {
    private static Clip clip;
    /**
     * Plays music
     * @param musicPath The path of the .wav file
     */
    public static void playMusic(File musicPath) {
        try {
            if (musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
            } else {
                System.out.println("Cannot find the file");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopMusic(){
        try {
            clip.stop();
        }catch (NullPointerException ignored){

        }
    }
}