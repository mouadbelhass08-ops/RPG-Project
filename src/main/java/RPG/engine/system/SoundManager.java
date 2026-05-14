package RPG.engine.system;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.AudioClip;

public class SoundManager {
    private static MediaPlayer currentOst;

    public static void playOst(String OST) {
        stopOst();
        Media media=AssetManager.getOST().get(OST);
        if (media!=null) {
            currentOst=new MediaPlayer(media);
            currentOst.setCycleCount(MediaPlayer.INDEFINITE);
            currentOst.play();
        }
    }
    public static void playCue(String cuekey) {
        AudioClip cue=AssetManager.getCues().get(cuekey);
        if (cue!=null) {
            cue.play();
        }
    }
    public static void stopOst() {
        if (currentOst!=null) {
            currentOst.stop();
            currentOst.dispose();
            currentOst=null;
        }
    }
}