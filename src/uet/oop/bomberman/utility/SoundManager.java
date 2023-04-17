package uet.oop.bomberman.utility;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.*;
import java.net.URL;

public class SoundManager extends JFrame {
    public String path;

    public double volume;

    public Clip audioClip;

    public boolean isPlay = false;

    public SoundManager(String path, double volume) {
        this.path = path;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            URL inputStream = this.getClass().getClassLoader().getResource(path);
            assert inputStream != null;
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(inputStream);
            audioClip = AudioSystem.getClip();
            audioClip.open(audioStream);
            FloatControl gainControl =
                    (FloatControl) audioClip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue((float) -volume);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void play(int time) {
        if (!isPlay) {
            audioClip.loop((time > 0) ? time - 1 : -1);
        }
        isPlay = true;
    }

    public void stop() {
        if (isPlay) {
            audioClip.stop();
        }
        isPlay = false;
    }

}
